package rettrofit;

import android.content.Context;

import retrofit.RequestListener;
import retrofit2.Call;
import retrofit2.Callback;

public class APICallback<T> implements Callback<T> {
    protected RequestListener<T> listener;
    protected Context context;

    public APICallback(Context context, RequestListener<T> listener) {
        this.listener = listener;
        this.context = context;
    }

    @Override
    public void onResponse(Call<T> call, retrofit2.Response<T> response) {
        listener.onSuccess(response.body());

    }

    @Override
    public void onFailure(Call<T> call, Throwable t) {
        listener.onError(t);
    }
}
