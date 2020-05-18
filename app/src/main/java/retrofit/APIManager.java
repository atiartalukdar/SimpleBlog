package retrofit;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;
import java.util.concurrent.TimeUnit;

import bp.MyApplication;

import model.ArticleModel;
import model.CategoryModel;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class APIManager {
    public static final String BASE_URL = "http://athkarapplication.com/blogadmin/api/";

    private final APIInterface api;
    private Context _context;

    public APIManager(){

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
                .connectTimeout(5*60, TimeUnit.SECONDS)
                .readTimeout(2*60, TimeUnit.SECONDS)
                .writeTimeout(20*60, TimeUnit.SECONDS)
                .build();

        _context = MyApplication.getContext();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();
        api = retrofit.create(APIInterface.class);
    }

    public void getCategoryList(RequestListener<List<CategoryModel>> listener) {
        api.getCategoryList().enqueue(new rettrofit.APICallback<List<CategoryModel>>(_context,listener));
    }

    public void getArticleList(String ctgID, RequestListener<List<ArticleModel>> listener) {
        api.getArticleList(ctgID).enqueue(new rettrofit.APICallback<List<ArticleModel>>(_context,listener));
    }

    public void getFeatureImage(RequestListener<String> listener) {
        api.getFeatureImge().enqueue(new rettrofit.APICallback<String>(_context,listener));
    }

}
