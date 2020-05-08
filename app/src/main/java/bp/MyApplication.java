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
    }

    public static Context getContext (){return context; }

    public static void fromHtml(TextView textview, String htmlText){

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            textview.setText(Html.fromHtml("<h2>Title</h2><br><p>Description here</p>", Html.FROM_HTML_MODE_COMPACT));
        } else {
            textview.setText(Html.fromHtml("<h2>Title</h2><br><p>Description here</p>"));
        }
    }
}
