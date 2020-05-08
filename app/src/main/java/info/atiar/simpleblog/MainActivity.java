package info.atiar.simpleblog;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import com.facebook.shimmer.ShimmerFrameLayout;

import java.util.ArrayList;
import java.util.List;

import adapter.CategoryAdapter;
import model.CategoryModel;
import retrofit.APIInterface;
import retrofit.APIManager;
import retrofit.RequestListener;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {
    private final String TAG = getClass().getName() + " Atiar - ";

    ListView _listView;
    ShimmerFrameLayout container;

    APIManager _apiManager;
    CategoryAdapter categoryAdapter;
    List<CategoryModel> categoryModelList = new ArrayList<>();;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        _listView = findViewById(R.id.listview);
        container = findViewById(R.id.shimmer_view_container);
        container.startShimmer();
        _apiManager = new APIManager();
        categoryAdapter = new CategoryAdapter(this, categoryModelList);
        _listView.setAdapter(categoryAdapter);

        loadCategoryListFromServer();

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
                    container.stopShimmer();
                    container.setVisibility(View.GONE);
                    categoryAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onError(Throwable t) {

            }
        });
    }
}
