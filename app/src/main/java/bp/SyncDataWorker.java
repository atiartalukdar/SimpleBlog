package bp;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import java.util.List;

import io.objectbox.Box;
import model.AllArticleModel;
import model.ArticleModel;
import model.ArticleModel_;
import retrofit.APIManager;
import retrofit.RequestListener;

public class SyncDataWorker  extends Worker {
    private static final String TAG = SyncDataWorker.class.getSimpleName() + " Atiar - ";
    APIManager _apiManager;
    Box<ArticleModel> articleModelBox = ObjectBox.get().boxFor(ArticleModel.class);

    public SyncDataWorker(@NonNull Context appContext, @NonNull WorkerParameters workerParams) {
        super(appContext, workerParams);
        _apiManager = new APIManager();

    }

    @NonNull
    @Override
    public Result doWork() {

        Context applicationContext = getApplicationContext();
        //simulate slow work
        // WorkerUtils.makeStatusNotification("Fetching Data", applicationContext);
        Log.e(TAG, "Fetching Data from Remote host");
        //WorkerUtils.sleep();

        try {
            //create a call to network
            loadAllArticles();
            return Result.success();
        } catch (Throwable e) {
            e.printStackTrace();
            // Technically WorkManager will return Result.failure()
            // but it's best to be explicit about it.
            // Thus if there were errors, we're return FAILURE
            Log.e(TAG, "Error fetching data", e);
            return Result.failure();
        }
    }

    @Override
    public void onStopped() {
        super.onStopped();
        Log.i(TAG, "OnStopped called for this worker");
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
                                Log.e("Atiar - Article - ", "New Article Insertedd");
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
                                Log.e("Atiar - new Article", serverArticle.toString());
                                Log.e("Atiar - Article - ", " Article Updated");
                            }
                        }
                    }
                }

                try {
                    Log.e("Atiar - Article - " , response.size()+"");
                }catch (Exception e){
                    e.printStackTrace();
                }

            }

            @Override
            public void onError(Throwable t) {
                Log.e("Atiar - cat -", t.getMessage());
            }
        });


    }

}