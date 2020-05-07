package retrofit;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class RetrofitClientInstance {
    private static Retrofit retrofit;
    //private static final String BASE_URL = "http://ictsoft.sfdw.org:901/sfdw/";
    //private static final String BASE_URL = "http://ictsoft.sfdw.org:8091/sme_api/";
    //private static final String BASE_URL_NEW = "http://192.168.0.50:8081/";
    //private static final String BASE_URL_NEW = "http://116.193.221.99:8081/";
    public static final String BASE_URL = "https://atiar.info/blogadmin/api/";


    public static Retrofit getRetrofitInstanceNew() {

        OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
                .connectTimeout(5*60, TimeUnit.SECONDS)
                .readTimeout(2*60, TimeUnit.SECONDS)
                .writeTimeout(20*60, TimeUnit.SECONDS)
                .build();

        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
