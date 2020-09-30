package com.athkarapplication.athkar.ui.home;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
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
import io.objectbox.query.QueryBuilder;
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
    //ShimmerFrameLayout Loading;
    private static Activity activity;
    APIManager _apiManager;
    static CategoryAdapter categoryAdapter;
    private static List<CategoryModel> categoryModelList = new ArrayList<>();

    private HomeViewModel homeViewModel;
    ImageView imageView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        activity = getActivity();
        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();

        _listView = root.findViewById(R.id.listview);
        _topBanner = root.findViewById(R.id.topBanner);
        imageView = root.findViewById(R.id.loadingImageView);
        imageView.setVisibility(View.VISIBLE);
        //Loading = root.findViewById(R.id.shimmer_view_container);
        //Loading.startShimmer();
        _apiManager = new APIManager();

        if (BP.getCurrentLanguage() == BP.ENGLISH){
            categoryAdapter = new CategoryAdapter(getActivity(), categoryModelList,1);
        }else {
            categoryAdapter = new CategoryAdapter(getActivity(), categoryModelList,0);
        }
        _listView.setAdapter(categoryAdapter);
        loadFeatureImage();
        loadCategoryListFromServer();
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
        _apiManager.getCategoryList(new RequestListener<List<CategoryModel>>() {
            @Override
            public void onSuccess(List<CategoryModel> response) {
                if (response!=null){
                    categoryModelList.clear();
                    for (CategoryModel ctg: response){
                        if (ctg.getStatus().equals("1")){
                            categoryModelList.add(ctg);
                        }
                    }
                    //Loading.stopShimmer();
                   // Loading.setVisibility(View.GONE);
                    imageView.setVisibility(View.GONE);
                    _listView.setVisibility(View.VISIBLE);
                    ((AppCompatActivity) getActivity()).getSupportActionBar().show();

                    categoryAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onError(Throwable t) {

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

    }


}