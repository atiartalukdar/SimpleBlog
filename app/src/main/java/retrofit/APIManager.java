package retrofit;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;
import java.util.concurrent.TimeUnit;

import bp.MyApplication;

import model.AllArticleModel;
import model.ArticleModel;
import model.CategoryModel;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class APIManager {
    //public static final String BASE_URL = "https://athkarapplication.com/blogadmin/";
    public static final String BASE_URL = "https://atiar.info/blogadmin/";
    public static final String BASE_URL_API = BASE_URL+"api/";

    private final APIInterface api;
    private Context _context = MyApplication.getContext();;

    int cacheSize = 100 * 1024 * 1024; // 100 MB
    Cache cache = new Cache(_context.getCacheDir(), cacheSize);

    public APIManager(){

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
                .cache(cache)
                .connectTimeout(5*60, TimeUnit.SECONDS)
                .readTimeout(2*60, TimeUnit.SECONDS)
                .writeTimeout(20*60, TimeUnit.SECONDS)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL_API)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();
        api = retrofit.create(APIInterface.class);
    }

    public void getCategoryList(RequestListener<List<CategoryModel>> listener) {
        api.getCategoryList().enqueue(new retrofit.APICallback<List<CategoryModel>>(_context,listener));
    }

    public void getArticleList(String ctgID, RequestListener<List<ArticleModel>> listener) {
        api.getArticleList(ctgID).enqueue(new retrofit.APICallback<List<ArticleModel>>(_context,listener));
    }
    public void getAllArticleList(String ctgID, RequestListener<List<AllArticleModel>> listener) {
        api.getAllArticleList(ctgID).enqueue(new retrofit.APICallback<List<AllArticleModel>>(_context,listener));
    }

    public void getFeatureImage(RequestListener<String> listener) {
        api.getFeatureImge().enqueue(new retrofit.APICallback<String>(_context,listener));
    }

}
