package retrofit;


import java.util.List;

import model.CategoryModel;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;


public interface APIInterface {

    @GET("get_category")
    Call<CategoryModel> getCategoryList();
}
