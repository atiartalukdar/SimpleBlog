package info.atiar.simpleblog.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import adapter.CategoryAdapter;
import bp.BP;
import info.atiar.simpleblog.R;
import model.CategoryModel;
import retrofit.APIManager;
import retrofit.RequestListener;

public class HomeFragment extends Fragment {
    private final String TAG = getClass().getName() + " Atiar - ";
    ListView _listView;
    ImageView _topBanner;
    ShimmerFrameLayout Loading;

    APIManager _apiManager;
    CategoryAdapter categoryAdapter;
    List<CategoryModel> categoryModelList = new ArrayList<>();

    private HomeViewModel homeViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);


        _listView = root.findViewById(R.id.listview);
        _topBanner = root.findViewById(R.id.topBanner);
        Loading = root.findViewById(R.id.shimmer_view_container);
        Loading.startShimmer();
        _apiManager = new APIManager();
        categoryAdapter = new CategoryAdapter(getActivity(), categoryModelList,0);
        _listView.setAdapter(categoryAdapter);
        loadFeatureImage();
        loadCategoryListFromServer();


        /*final TextView textView = root.findViewById(R.id.text_home);
        homeViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });*/
        _listView.addHeaderView(new View(getContext()));
        _listView.addFooterView(new View(getContext()));

        Switch aSwitch =  root.findViewById(R.id.simpleSwitch1);
        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // The toggle is enabled
                    categoryAdapter = new CategoryAdapter(getActivity(), categoryModelList,0);
                    _listView.setAdapter(categoryAdapter);
                    categoryAdapter.notifyDataSetChanged();
                } else {
                    // The toggle is disabled
                    categoryAdapter = new CategoryAdapter(getActivity(), categoryModelList,1);
                    _listView.setAdapter(categoryAdapter);
                    categoryAdapter.notifyDataSetChanged();
                }
            }
        });
        return root;
    }


    private void loadCategoryListFromServer() {
        _apiManager.getCategoryList(new RequestListener<List<CategoryModel>>() {
            @Override
            public void onSuccess(List<CategoryModel> response) {
                if (response!=null){
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
}