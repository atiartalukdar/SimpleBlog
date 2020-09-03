package com.athkarapplication.athkar.ui.home;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import adapter.CategoryAdapter;
import bp.BP;
import com.athkarapplication.athkar.R;

import bp.ObjectBox;
import io.objectbox.Box;
import model.AllArticleModel;
import model.ArticleModel;
import model.ArticleModel_;
import model.CategoryModel;
import model.CategoryModel_;
import retrofit.APIManager;
import retrofit.RequestListener;

public class HomeFragment extends Fragment {
    private final  String TAG = getClass().getName() + " Atiar - ";
    private static ListView _listView;
    ImageView _topBanner;
    ShimmerFrameLayout Loading;
    private static Activity activity;
    APIManager _apiManager;
    static CategoryAdapter categoryAdapter;
    private static List<CategoryModel> categoryModelList = new ArrayList<>();

    private HomeViewModel homeViewModel;

    Box<ArticleModel> articleModelBox = ObjectBox.get().boxFor(ArticleModel.class);
    Box<CategoryModel> categoryModelBox = ObjectBox.get().boxFor(CategoryModel.class);


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        activity = getActivity();

        _listView = root.findViewById(R.id.listview);
        _topBanner = root.findViewById(R.id.topBanner);
        Loading = root.findViewById(R.id.shimmer_view_container);
        Loading.startShimmer();
        _apiManager = new APIManager();
        loadFeatureImage();

        categoryModelList.clear();
        categoryModelList.addAll(categoryModelBox.getAll());

        if (BP.getCurrentLanguage() == BP.ENGLISH){
            categoryAdapter = new CategoryAdapter(getActivity(), categoryModelList,1);
        }else {
            categoryAdapter = new CategoryAdapter(getActivity(), categoryModelList,0);
        }
        _listView.setAdapter(categoryAdapter);
        if (BP.getOpenCounter()<1){
            loadCategoryListFromServer();
        }
        _listView.addHeaderView(new View(getContext()));
        _listView.addFooterView(new View(getContext()));
        return root;
    }

    public static void ctgInArabic(){
        categoryAdapter = new CategoryAdapter(activity, categoryModelList,0);
        _listView.setAdapter(categoryAdapter);
        categoryAdapter.notifyDataSetChanged();
    }

    public static void ctgInEnglish(){
        Log.e("Atiar - ", "ctgInEnglish");
        categoryAdapter = new CategoryAdapter(activity, categoryModelList,1);
        _listView.setAdapter(categoryAdapter);
        categoryAdapter.notifyDataSetChanged();
    }

    private void loadCategoryListFromServer() {
       /* _apiManager.getCategoryList(new RequestListener<List<CategoryModel>>() {
            @Override
            public void onSuccess(List<CategoryModel> response) {
                if (response!=null){
                    categoryModelList.clear();
                    for (CategoryModel ctg: response){
                        if (ctg.getStatus().equals("1")){
                            categoryModelList.add(ctg);
                        }
                    }
                    Loading.stopShimmer();
                    Loading.setVisibility(View.GONE);
                    categoryAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onError(Throwable t) {

            }
        });*/

        Log.e("Atiar - ctg - " , "about to hit AllArticle");
        _apiManager.getAllArticleList(BP.lastUpdateTime(), new RequestListener<List<AllArticleModel>>() {
            @Override
            public void onSuccess(List<AllArticleModel> response) {

                if (response != null ){
                    for (AllArticleModel singleArticleModel :response){
                        ArticleModel serverArticle = new ArticleModel(singleArticleModel);
                        CategoryModel serverCategory = singleArticleModel.getCategory();

                        ArticleModel localArticle = articleModelBox.query().equal(ArticleModel_.articleId, serverArticle.getArticleId()).build().findFirst();
                        CategoryModel localCategory = categoryModelBox.query().equal(CategoryModel_.categoryId, serverCategory.getCategoryId()).build().findFirst();

                        if(serverArticle.getStatus().equals("1")){
                            if (localArticle==null){
                                //Log.e("Atiar - Article - ", "New Article Insertedd");
                                articleModelBox.put(serverArticle);
                            }else {
                                localArticle.setCategoryId(serverArticle.getCategoryId());
                                localArticle.setArtical(serverArticle.getArtical());
                                localArticle.setArticleOtherLan(serverArticle.getArticleOtherLan());
                                localArticle.setMaxRead(serverArticle.getMaxRead());
                                localArticle.setStatus(serverArticle.getStatus());
                                localArticle.setUpdatedAt(serverArticle.getUpdatedAt());
                                localArticle.setSortingIndex(serverArticle.getSortingIndex());
                                articleModelBox.put(localArticle);
                                //Log.e("Atiar - Article - ", " Article Updated");
                            }

                            if (localCategory==null){
                                categoryModelBox.put(serverCategory);
                                //Log.e("Atiar - Category - ", "New Category Insertedd");
                            }else {
                                localCategory.setCategory(serverCategory.getCategory());
                                localCategory.setCatOtherLan(serverCategory.getCatOtherLan());
                                localCategory.setCatColorCode(serverCategory.getCatColorCode());
                                localCategory.setStatus(serverCategory.getStatus());
                                localCategory.setUpdatedAt(serverCategory.getUpdatedAt());
                                categoryModelBox.put(localCategory);
                                //Log.e("Atiar - Category - ", " Category Updated");
                            }
                        }
                    }
                }

                Log.e("Atiar - Article - " , articleModelBox.getAll().size()+"");
                Log.e("Atiar - category - " , categoryModelBox.getAll().size()+"");

                Loading.stopShimmer();
                Loading.setVisibility(View.GONE);
                categoryModelList.clear();
                categoryModelList.addAll(categoryModelBox.getAll());
                categoryAdapter.notifyDataSetChanged();
            }

            @Override
            public void onError(Throwable t) {
                Log.e("Atiar - cat -", t.getMessage());
            }
        });
    }

    private void loadFeatureImage(){
        _apiManager.getFeatureImage(new RequestListener<String>() {
            @Override
            public void onSuccess(String response) {
                if (response != null){
                    Log.e(TAG, "Image path = "+ BP.ImageURL+response);
                    Picasso.get()
                            .load(BP.ImageURL+response)
                            .centerCrop()
                            .fit()
                            .into(_topBanner);
                }
            }

            @Override
            public void onError(Throwable t) {
                Log.e(TAG,t.getMessage());
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.e(TAG, "fragment onResume");
        if (BP.getOpenCounter()>1){
            Loading.stopShimmer();
            Loading.setVisibility(View.GONE);
            loadCategoryListFromServer();
        }
    }


}