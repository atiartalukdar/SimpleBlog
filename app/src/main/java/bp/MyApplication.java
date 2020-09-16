package bp;

import android.app.Application;
import android.content.Context;
import android.os.Build;
import android.text.Html;
import android.util.Log;
import android.widget.TextView;

import com.athkarapplication.athkar.BuildConfig;

import io.objectbox.android.AndroidObjectBrowser;
import model.MyObjectBox;

public class MyApplication extends Application {
    private static Context context;
    private final String tag = MyApplication.class.getSimpleName() + "Atiar= ";
    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        ObjectBox.init(this);

        if (BuildConfig.DEBUG) {
            boolean started = new AndroidObjectBrowser(ObjectBox.get()).start(this);
            Log.e("Atiar - ObjectBrowser", "Started: " + started);
        }
    }

    public static Context getContext (){return context; }
}
