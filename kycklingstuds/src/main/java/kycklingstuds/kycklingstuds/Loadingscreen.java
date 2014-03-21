package kycklingstuds.kycklingstuds;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;


public class Loadingscreen extends FragmentActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loadingscreen);

        loadResources(); // Initializes resources such as images, sounds etc

        Intent intent = new Intent(this, MenuActivity.class);
        startActivity(intent);
    }

    private void loadResources() {
        Resources.DEFAULT_BACKGROUND = getResources().getDrawable(R.drawable.background);
        Resources.WHALE = getResources().getDrawable(R.drawable.whale);

        Resources.LIFE_LEFT = BitmapFactory.decodeResource(getResources(), R.drawable.heart);
        Resources.LIFE_LEFT = Bitmap.createScaledBitmap(Resources.LIFE_LEFT, 40, 34, false);

        Resources.SHEEP_ONE = BitmapFactory.decodeResource(getResources(), R.drawable.sheep1);
        Resources.SHEEP_ONE = Bitmap.createScaledBitmap(Resources.SHEEP_ONE, 70, 70, false);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.loadingscreen, menu);
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

}
