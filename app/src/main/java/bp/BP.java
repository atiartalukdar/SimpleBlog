package bp;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.text.Html;
import android.widget.EditText;
import android.widget.TextView;

public class BP {
    private static final String PREFS_NAME = "pref";
    public static final String ImageURL = "https://atiar.info/blogadmin/images/";

    public static void fromHtml(EditText textview, String htmlText){

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            textview.setText(Html.fromHtml(htmlText, Html.FROM_HTML_MODE_COMPACT));
        } else {
            textview.setText(Html.fromHtml(htmlText));
        }
    }

    public static int getReadCount(String ctgID, String articleID){
        try {
            return getPreference(ctgID+"-"+articleID);
        }catch (Exception e){
            e.printStackTrace();
            return 0;
        }
    }

    public static void setReadCount(String ctgID, String articleID, int value){
        setPreference(ctgID+"-"+articleID,value);
    }


    //SharedPreferences
    private static boolean setPreference(String key, int value) {
        SharedPreferences settings = MyApplication.getContext().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putInt(key, value);
        return editor.commit();
    }

    private static int getPreference(String key) {
        SharedPreferences settings = MyApplication.getContext().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        return settings.getInt(key, 0);
    }

    private static void removeSingleItem(String keyToRemove) {
        SharedPreferences settings = MyApplication.getContext().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        settings.edit().remove(keyToRemove).commit();
    }

    public static void removeAllItem() {
        SharedPreferences settings = MyApplication.getContext().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        settings.edit().clear().commit();
    }
}
