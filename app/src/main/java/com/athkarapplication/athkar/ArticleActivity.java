package com.athkarapplication.athkar;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.media.AudioManager;
import android.media.ToneGenerator;
import android.os.Build;
import android.os.Bundle;
import android.os.Vibrator;
import android.text.Html;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.kaopiz.kprogresshud.KProgressHUD;

import java.util.ArrayList;
import java.util.List;

import bp.BP;
import bp.ObjectBox;
import bp.OnSwipeTouchListener;
import io.objectbox.Box;
import model.ArticleModel;
import model.ArticleModel_;
import model.CategoryModel;
import retrofit.APIManager;
import retrofit.RequestListener;

public class ArticleActivity extends AppCompatActivity {
    private final String TAG = getClass().getName() + " Atiar - ";
    APIManager _apiManager;
    List<ArticleModel> articleModelList = new ArrayList<>();;
    Vibrator v;
    ToneGenerator tg;


    WebView _articleWebview;
    TextView _counter, _numberOfPage;
    String ctgID;
    int position=0;
    private Context context;

    Box<ArticleModel> articleModelBox = ObjectBox.get().boxFor(ArticleModel.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article);
        final Switch aSwitch =  findViewById(R.id.simpleSwitch1);
        Intent intent = getIntent();
        ctgID = intent.getStringExtra("id");
        final String ctg = intent.getStringExtra("ctg");
        final String ctgO = intent.getStringExtra("ctgO");

        if (BP.getCurrentLanguage()==BP.ENGLISH){
            aSwitch.setChecked(false);
            setTitle(ctg);
        }else {
            aSwitch.setChecked(true);
            setTitle(ctgO);
        }

        _articleWebview = findViewById(R.id.articleWebView);
        _articleWebview.getSettings().getJavaScriptEnabled();
        _counter = findViewById(R.id.counter);
        _numberOfPage = findViewById(R.id.numberOfPage);
        context = this;
        _apiManager = new APIManager();

        v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        tg = new ToneGenerator(AudioManager.STREAM_NOTIFICATION, 100);

        _articleWebview.setOnTouchListener(new OnSwipeTouchListener(ArticleActivity.this) {

            public void onSwipeRight() {
                position--;
                Log.e(TAG, "onSwipeRight= "+position+"");
                if (position<=0){
                    position=0;
                }
                if (aSwitch.isChecked()){
                    _articleWebview.loadDataWithBaseURL(null, articleModelList.get(position).getArticleOtherLan()+"", "text/html", "utf-8", null);
                }else {
                    _articleWebview.loadDataWithBaseURL(null, articleModelList.get(position).getArtical()+"", "text/html", "utf-8", null);
                }
                _counter.setText(BP.getReadCount(ctgID, articleModelList.get(position).getArticleId()+"")+"");
                _numberOfPage.setText( (position+1) + " / " + articleModelList.size());
               /* if (position>0){
                    SlideAnimationUtil.slideOutToRight(context, _articleWebview);
                }*/

            }

            public void onSwipeLeft() {
                Log.e(TAG, "onSwipeLeft= "+position+"");
                position++;
                if (position>=articleModelList.size()-1){
                    position = articleModelList.size()-1;
                }

                if (aSwitch.isChecked()){
                    _articleWebview.loadDataWithBaseURL(null, articleModelList.get(position).getArticleOtherLan()+"", "text/html", "utf-8", null);
                }else {
                    _articleWebview.loadDataWithBaseURL(null, articleModelList.get(position).getArtical()+"", "text/html", "utf-8", null);
                }
                _counter.setText(BP.getReadCount(ctgID, articleModelList.get(position).getArticleId()+""));
                _numberOfPage.setText( (position+1) + " / " + articleModelList.size());

               /* if (position<articleModelList.size()-1){
                    SlideAnimationUtil.slideOutToLeft(context, _articleWebview);
                }*/
            }

            @Override
            public void onClick() {
                _counter.performClick();
                super.onClick();
            }
        });
        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    setTitle(ctgO);
                    _articleWebview.loadDataWithBaseURL(null, articleModelList.get(position).getArticleOtherLan()+"", "text/html", "utf-8", null);
                } else {
                    setTitle(ctg);
                    _articleWebview.loadDataWithBaseURL(null, articleModelList.get(position).getArtical()+"", "text/html", "utf-8", null);
                }
            }
        });

        loadArticles(ctgID);
    }

    private void loadArticles(final String ctgID) {
       /* final KProgressHUD kProgressHUD = KProgressHUD.create(ArticleActivity.this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("Please wait")
                .setDetailsLabel("Downloading data")
                .setCancellable(false)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f)
                .show();



        _apiManager.getArticleList(ctgID, new RequestListener<List<ArticleModel>>() {
            @Override
            public void onSuccess(List<ArticleModel> response) {
                if (response!=null && response.size()>0){
                    for (ArticleModel article : response){
                        if (article.getStatus().equals("1")){
                            articleModelList.add(article);
                        }
                    }
                    position=0;

                    if (BP.getCurrentLanguage()==BP.ENGLISH){
                        _articleWebview.loadDataWithBaseURL(null, articleModelList.get(position).getArtical()+"", "text/html", "utf-8", null);
                    }else {
                        _articleWebview.loadDataWithBaseURL(null, articleModelList.get(position).getArticleOtherLan()+"", "text/html", "utf-8", null);
                    }
                    _counter.setText(BP.getReadCount(ctgID, articleModelList.get(position).getArticleId() +""));
                    _numberOfPage.setText( (position+1) + " / " + articleModelList.size());
                }
                kProgressHUD.dismiss();
            }

            @Override
            public void onError(Throwable t) {
                kProgressHUD.dismiss();
            }
        });

*/
        Log.e("Atiar - categoryID - ",  ctgID+" ");
        List<ArticleModel> localArticles = articleModelBox.query().equal(ArticleModel_.categoryId, ctgID).build().find();
        Log.e("Atiar - size - ",  localArticles.size()+" ");
        articleModelList.addAll(localArticles);

        position=0;

        if (BP.getCurrentLanguage()==BP.ENGLISH){
            _articleWebview.loadDataWithBaseURL(null, articleModelList.get(position).getArtical()+"", "text/html", "utf-8", null);
        }else {
            _articleWebview.loadDataWithBaseURL(null, articleModelList.get(position).getArticleOtherLan()+"", "text/html", "utf-8", null);
        }
        _counter.setText(BP.getReadCount(ctgID, articleModelList.get(position).getArticleId() +"")+"");
        _numberOfPage.setText( (position+1) + " / " + articleModelList.size());
    }

    public void counterFunction(View view) {
        if (BP.getReadCount(ctgID, articleModelList.get(position).getArticleId()+"") >= Integer.parseInt(articleModelList.get(position).getMaxRead())){
            v.vibrate(500);
            tg.startTone(ToneGenerator.TONE_PROP_BEEP2);

        }else{
            BP.setReadCount(ctgID,articleModelList.get(position).getArticleId()+"",BP.getReadCount(ctgID,articleModelList.get(position).getArticleId()+"")+1);
            _counter.setText(BP.getReadCount(ctgID, articleModelList.get(position).getArticleId()+""));
        }
    }

    // Activity
    public void setTitle(String title){
        //https://stackoverflow.com/questions/12387345/how-to-center-align-the-actionbar-title-in-android
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        title = title.replace("style=\"color:#","");
        TextView textView = new TextView(this);
        textView.setTextColor(Color.WHITE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            textView.setText(Html.fromHtml(title, Html.FROM_HTML_MODE_COMPACT));
        } else {
            textView.setText(Html.fromHtml(title));
        }
        textView.setMaxLines(1);
        textView.setTextSize(20);
        textView.setTypeface(null, Typeface.BOLD);
        textView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        textView.setGravity(Gravity.CENTER);
        textView.setTextColor(getResources().getColor(R.color.white));
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(textView);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.custom_back_button, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_custom_back_button:
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
