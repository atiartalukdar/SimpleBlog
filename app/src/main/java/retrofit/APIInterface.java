package retrofit;


import java.util.List;

import model.ArticleModel;
import model.CategoryModel;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;


public interface APIInterface {

    @GET("get_category")
    Call<List<CategoryModel>> getCategoryList();

    @GET("get_article/{id}")
    Call<List<ArticleModel>> getArticleList(@Path("id") String id);

    @GET("feature_image")
    Call<List<CategoryModel>> getFeatureImge();
}
