package bp;

import android.app.Application;
import android.content.Context;

public class MyApplication extends Application {
    private static Context context;
    private final String tag = MyApplication.class.getSimpleName() + "Atiar= ";
    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
    }

    public static Context getContext (){return context; }
}
