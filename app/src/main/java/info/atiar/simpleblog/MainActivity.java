package info.atiar.simpleblog;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import adapter.CategoryAdapter;
import bp.BP;
import model.CategoryModel;
import retrofit.APIInterface;
import retrofit.APIManager;
import retrofit.RequestListener;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {
    private final String TAG = getClass().getName() + " Atiar - ";

    ListView _listView;
    ImageView _topBanner;
    ShimmerFrameLayout container;

    APIManager _apiManager;
    CategoryAdapter categoryAdapter;
    List<CategoryModel> categoryModelList = new ArrayList<>();;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbarTop = findViewById(R.id.toolbar_top);
        //TextView mTitle = toolbarTop.findViewById(R.id.toolbar_title);
        setSupportActionBar(toolbarTop);
        getSupportActionBar().setTitle("");


        _listView = findViewById(R.id.listview);
        _topBanner = findViewById(R.id.topBanner);
        container = findViewById(R.id.shimmer_view_container);
        container.startShimmer();
        _apiManager = new APIManager();
        categoryAdapter = new CategoryAdapter(this, categoryModelList);
        _listView.setAdapter(categoryAdapter);
        loadFeatureImage();
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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_share_app:
                break;
            case R.id.menu_contact_us:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        BP.removeAllItem();
        super.onResume();
    }

}
