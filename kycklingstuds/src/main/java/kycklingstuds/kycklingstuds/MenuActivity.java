package kycklingstuds.kycklingstuds;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.facebook.AppEventsLogger;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class MenuActivity extends FragmentActivity {
    private MainFragment mainFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    "kycklingstuds.kycklingstuds",
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                System.out.println("KeyHash: " + (Base64.encodeToString(md.digest(),Base64.DEFAULT)));
            }
        } catch (PackageManager.NameNotFoundException e) {
            System.out.println("EXCEPTION: " + e);
        } catch (NoSuchAlgorithmException e) {
            System.out.println("EXCEPTION: " + e);
        }


        if (savedInstanceState == null) {
            // Add the fragment on initial activity setup
            mainFragment = new MainFragment();
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(android.R.id.content, mainFragment)
                    .commit();
        } else {
            // Or set the fragment from restored state info
            mainFragment = (MainFragment) getSupportFragmentManager()
                    .findFragmentById(android.R.id.content);
        }
    }


    @Override
    protected void onResume() {
        super.onResume();

        // Call the 'activateApp' method to log an app event for use in analytics and advertising reporting.  Do so in
        // the onResume methods of the primary Activities that an app may be launched into.
        AppEventsLogger.activateApp(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void onPlayBtnClicked(View v) {
        Intent intent = new Intent(this, PlayActivity.class);
        startActivity(intent);
    }


    public void onOptionsBtnClicked(View v) {
        System.out.println("DEBUG: onOptionsBtnClicked");



        OptionsFragment optionsFragment = new OptionsFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();

        ft.add(R.id.menu, optionsFragment);
        ft.commit();


    }

    public void onLeaderboardBtnClicked(View v) {
        System.out.println("DEBUG: onLeaderboardBtnClicked");


    }



}
