package retrofit;

import android.content.Context;

import model.CategoryModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class APICallback<T> implements Callback<T> {
    protected RequestListener<T> listener;
    protected Context context;

    public APICallback(Context context, RequestListener<T> listener) {
        this.listener = listener;
        this.context = context;
    }

    @Override
    public void onResponse(Call<T> call, Response<T> response) {
        listener.onSuccess(response.body());

    }

    @Override
    public void onFailure(Call<T> call, Throwable t) {
        listener.onError(t);
    }
}
