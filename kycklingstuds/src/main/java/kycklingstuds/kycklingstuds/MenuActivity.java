package kycklingstuds.kycklingstuds;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;


public class MenuActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);


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
