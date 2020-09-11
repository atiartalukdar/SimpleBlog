package bp;

import android.app.Application;
import android.content.Context;
import android.os.Build;
import android.text.Html;
import android.widget.TextView;

public class MyApplication extends Application {
    private static Context context;
    private final String tag = MyApplication.class.getSimpleName() + "Atiar= ";
    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        ObjectBox.init(this);
    }

    public static Context getContext (){return context; }
}
