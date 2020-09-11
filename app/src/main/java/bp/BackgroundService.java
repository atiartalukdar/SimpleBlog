package bp;

import android.app.Service;
import android.content.Context;
import android.content.*;
import android.os.*;
import android.util.Log;
import android.widget.Toast;

import java.util.List;

import io.objectbox.Box;
import model.AllArticleModel;
import model.ArticleModel;
import model.ArticleModel_;
import retrofit.APIManager;
import retrofit.RequestListener;

public class BackgroundService extends Service {

    public Context context = this;
    public Handler handler = null;
    public static Runnable runnable = null;
    APIManager _apiManager;
    Box<ArticleModel> articleModelBox = ObjectBox.get().boxFor(ArticleModel.class);

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        //Toast.makeText(this, "Service created!", Toast.LENGTH_LONG).show();
        _apiManager = new APIManager();

        handler = new Handler();
        runnable = new Runnable() {
            public void run() {
                //Toast.makeText(context, "Service is still running", Toast.LENGTH_LONG).show();
                loadAllArticles();
               // handler.postDelayed(runnable, 10000);
            }
        };

        handler.postDelayed(runnable, 5000);
        stopForeground(true);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        /* IF YOU WANT THIS SERVICE KILLED WITH THE APP THEN UNCOMMENT THE FOLLOWING LINE */
        //handler.removeCallbacks(runnable);
        Toast.makeText(this, "Service stopped", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onStart(Intent intent, int startid) {
        //Toast.makeText(this, "Service started by user.", Toast.LENGTH_LONG).show();
    }

    private void loadAllArticles() {

        Log.e("Atiar - ctg - " , "about to hit AllArticle");
        _apiManager.getAllArticleList(BP.lastUpdateTime(), new RequestListener<List<AllArticleModel>>() {
            @Override
            public void onSuccess(List<AllArticleModel> response) {

                if (response != null && response.size()>0 ){
                    for (AllArticleModel singleArticleModel :response){
                        ArticleModel serverArticle = new ArticleModel(singleArticleModel);
                        ArticleModel localArticle = articleModelBox.query().equal(ArticleModel_.articleId, serverArticle.getArticleId()).build().findFirst();
                        if(serverArticle.getStatus().equals("1")){
                            if (localArticle==null){
                                //Log.e("Atiar - Article - ", "New Article Insertedd");
                                articleModelBox.put(serverArticle);
                            }else {
                                localArticle.setCategoryId(serverArticle.getCategoryId());
                                localArticle.setArtical(serverArticle.getArtical());
                                localArticle.setArticleOtherLan(serverArticle.getArticleOtherLan());
                                localArticle.setMaxRead(serverArticle.getMaxRead());
                                localArticle.setStatus(serverArticle.getStatus());
                                localArticle.setUpdatedAt(serverArticle.getUpdatedAt());
                                localArticle.setSortingIndex(serverArticle.getSortingIndex());
                                articleModelBox.put(localArticle);
                                //Log.e("Atiar - Article - ", " Article Updated");
                            }
                        }
                    }
                }

                Log.e("Atiar - Article - " , articleModelBox.getAll().size()+"");

            }

            @Override
            public void onError(Throwable t) {
                Log.e("Atiar - cat -", t.getMessage());
            }
        });


    }

}