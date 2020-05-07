package retrofit;

import android.content.Context;
import java.util.concurrent.TimeUnit;

import bp.MyApplication;

import model.CategoryModel;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class APIManager {
    public static final String BASE_URL = "https://atiar.info/blogadmin/api/";

    private final APIInterface api;
    private Context _context;

    public APIManager(){

        OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
                .connectTimeout(5*60, TimeUnit.SECONDS)
                .readTimeout(2*60, TimeUnit.SECONDS)
                .writeTimeout(20*60, TimeUnit.SECONDS)
                .build();

        _context = MyApplication.getContext();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();
        api = retrofit.create(APIInterface.class);
    }

    public void getCategoryList(RequestListener<CategoryModel> listener) {
        api.getCategoryList().enqueue(new APICallback<CategoryModel>(_context,listener));
    }

}
