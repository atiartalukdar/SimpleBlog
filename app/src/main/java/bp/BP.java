package bp;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.text.Html;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import java.awt.font.TextAttribute;

public class BP {
    private static final String PREFS_NAME = "pref";
    private static final String PREFS_NAME1 = "pref1";
    public static final String ImageURL = "http://athkarapplication.com/blogadmin/images/";
    public static String languageKey = "language";
    public static int ENGLISH = 100;
    public static int ARABIC = 101;

    public static void fromHtml(EditText textview, String htmlText){

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            textview.setText(Html.fromHtml(htmlText, Html.FROM_HTML_MODE_COMPACT));
        } else {
            textview.setText(Html.fromHtml(htmlText));
        }
    }

    public static int getReadCount(String ctgID, String articleID){
        try {
            return getPreference(PREFS_NAME,ctgID+"-"+articleID);
        }catch (Exception e){
            e.printStackTrace();
            return 0;
        }
    }

    public static void setReadCount(String ctgID, String articleID, int value){
        setPreference(PREFS_NAME,ctgID+"-"+articleID,value);
    }

    //language change state
    public static int getCurrentLanguage(){
        Log.e("BP Atiar = ", getPreference(PREFS_NAME1,languageKey)+"");
        return getPreference(PREFS_NAME1,languageKey);
    }
    public static void setCurrentLanguage(int languageInt){
        setPreference(PREFS_NAME1,languageKey,languageInt);
    }
    //SharedPreferences
    private static boolean setPreference(String prefName, String key, int value) {
        SharedPreferences settings = MyApplication.getContext().getSharedPreferences(prefName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putInt(key, value);
        return editor.commit();
    }
    private static int getPreference(String prefName, String key) {
        SharedPreferences settings = MyApplication.getContext().getSharedPreferences(prefName, Context.MODE_PRIVATE);
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
