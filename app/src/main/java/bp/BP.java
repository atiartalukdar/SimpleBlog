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
import java.io.IOException;
import java.net.InetAddress;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;

import retrofit.APIManager;

public class BP {
    private final static String tag = "BP - Atiar - ";
    private static final String PREFS_NAME = "pref";
    private static final String PREFS_NAME1 = "pref1";
    //public static final String ImageURL = "https://athkarapplication.com/blogadmin/images/";
    //public static final String ImageURL = "https://atiar.info/blogadmin/images/";
    public static final String ImageURL = APIManager.BASE_URL+"images/";
    public static String languageKey = "language";
    public static String openCounterKey = "openCounterKey";
    public static String lastUpdateKey = "lastUpdateKey";
    public static int ENGLISH = 100;
    public static int ARABIC = 101;

    public static void fromHtml(EditText textview, String htmlText){

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            textview.setText(Html.fromHtml(htmlText, Html.FROM_HTML_MODE_COMPACT));
        } else {
            textview.setText(Html.fromHtml(htmlText));
        }
    }

    public static int getReadCount(String ctgID, int articleID){
        try {
            return getPreference(PREFS_NAME,ctgID+"-"+articleID);
        }catch (Exception e){
            e.printStackTrace();
            return 0;
        }
    }

    public static void setReadCount(String ctgID, int articleID, int value){
        setPreference(PREFS_NAME,ctgID+"-"+articleID,value);
    }

    //is first time user or not
    public static int getOpenCounter(){
        int cnt = getPreference(PREFS_NAME1,openCounterKey);
        setPreference(PREFS_NAME1,openCounterKey,cnt+1);
        Log.e(tag, "total opened - " + cnt);
        return cnt;
    }

    //Stroing last update time.
    public static String lastUpdateTime(){
        String lastupdateTime = getPreferenceString(PREFS_NAME1,lastUpdateKey);

        Log.e(tag, "time in millSec - " + lastupdateTime);
        Calendar calendar = Calendar.getInstance();
        long timeMilliSeconds = calendar.getTimeInMillis();
        long seconds = TimeUnit.MILLISECONDS.toSeconds(timeMilliSeconds);
        setPreference(PREFS_NAME1,lastUpdateKey,seconds+"");

        return lastupdateTime;
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
    private static boolean setPreference(String prefName, String key, String value) {
        SharedPreferences settings = MyApplication.getContext().getSharedPreferences(prefName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(key, value);
        return editor.commit();
    }
    private static int getPreference(String prefName, String key) {
        SharedPreferences settings = MyApplication.getContext().getSharedPreferences(prefName, Context.MODE_PRIVATE);
        return settings.getInt(key, 0);
    }
    private static String getPreferenceString(String prefName, String key) {
        SharedPreferences settings = MyApplication.getContext().getSharedPreferences(prefName, Context.MODE_PRIVATE);
        return settings.getString(key, "0");
    }

    private static void removeSingleItem(String keyToRemove) {
        SharedPreferences settings = MyApplication.getContext().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        settings.edit().remove(keyToRemove).commit();
    }

    public static void removeAllItem() {
        SharedPreferences settings = MyApplication.getContext().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        settings.edit().clear().commit();
    }

    public static boolean isInternetAvailable() {
        try {
            return isConnected();

        } catch (Exception e) {
            return false;
        }
    }

    private static boolean isConnected() throws InterruptedException, IOException {
        final String command = "ping -c 1 google.com";
        return Runtime.getRuntime().exec(command).waitFor() == 0;
    }

    public static void setImagePath(String imagePathComplete){
        setPreference(PREFS_NAME1,"imgUrl",imagePathComplete);
    }

    public static String getImagePath(){
        return getPreferenceString(PREFS_NAME1,"imgUrl");
    }
}
