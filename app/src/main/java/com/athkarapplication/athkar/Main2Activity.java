package com.athkarapplication.athkar;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import android.util.Log;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.core.app.ShareCompat;
import androidx.core.view.GravityCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.work.BackoffPolicy;
import androidx.work.Constraints;
import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.NetworkType;
import androidx.work.OneTimeWorkRequest;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import android.view.Menu;
import android.widget.CompoundButton;
import android.widget.Switch;

import bp.BP;
import bp.BackgroundService;
import bp.SyncDataWorker;
import io.objectbox.android.AndroidObjectBrowser;
import model.MyObjectBox;

import com.athkarapplication.athkar.ui.home.HomeFragment;

import java.util.concurrent.TimeUnit;

public class Main2Activity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private final String TAG = getClass().getName() + " Atiar - ";
    private AppBarConfiguration mAppBarConfiguration;
    Switch drawerSwitch;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        fetchData();

       /* //loading all the articles from background.
        Intent intent  = new Intent(this, BackgroundService.class);
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                this.startForegroundService(intent);
            } else {
                this.startService(intent);
            }
        }catch (Exception e){
            e.printStackTrace();
        }

*/
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
        navigationView.setNavigationItemSelectedListener(this);
        drawerSwitch = (Switch) navigationView.getMenu().findItem(R.id.nav_other_language).getActionView();

        if (BP.getCurrentLanguage()==BP.ENGLISH){
            drawerSwitch.setChecked(false);
        }else {
            drawerSwitch.setChecked(true);
        }

        drawerSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    Log.e(TAG, "switch checked");
                    HomeFragment.ctgInArabic();
                    BP.setCurrentLanguage(BP.ARABIC);
                } else {
                    Log.e(TAG, "switch not checked");
                    HomeFragment.ctgInEnglish();
                    BP.setCurrentLanguage(BP.ENGLISH);
                }
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main2, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    protected void onResume() {
        BP.removeAllItem();
        super.onResume();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int id = menuItem.getItemId();

        switch (id) {
            case R.id.nav_other_language:
                return false;
            case R.id.nav_share:

                ShareCompat.IntentBuilder.from(this)
                        .setType("text/plain")
                        .setChooserTitle(R.string.app_name)
                        .setText("http://play.google.com/store/apps/details?id=" + getPackageName())
                        .startChooser();
                break;

            case R.id.nav_send_whatsapp:
                Intent telegram = new Intent(Intent.ACTION_VIEW, Uri.parse("https://wa.me/16572008997"));
                startActivity(telegram);
                break;

            case R.id.nav_send_telegram:
                Intent whatsapp = new Intent(Intent.ACTION_VIEW, Uri.parse("https://t.me/AthkarApplication"));
                startActivity(whatsapp);
                break;

            case R.id.nav_send:
                Intent i = new Intent(Intent.ACTION_SENDTO);
                i.setData(Uri.parse("mailto:athkar.application@gmail.com"));
                i.putExtra(Intent.EXTRA_SUBJECT, "Feedback/Support");
                startActivity(Intent.createChooser(i, "Send feedback"));
                break;
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void fetchData() {

        // Create Network constraint
        Constraints constraints = new Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build();


        OneTimeWorkRequest oneTimeWorkRequest =
                new OneTimeWorkRequest.Builder(SyncDataWorker.class)
                        .setConstraints(constraints)
                        // setting a backoff on case the work needs to retry
                        .setBackoffCriteria(BackoffPolicy.LINEAR, OneTimeWorkRequest.MIN_BACKOFF_MILLIS, TimeUnit.MILLISECONDS)
                        .build();

        WorkManager.getInstance().enqueue(oneTimeWorkRequest);

    }
}
