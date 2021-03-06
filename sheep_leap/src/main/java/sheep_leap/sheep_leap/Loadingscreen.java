package sheep_leap.sheep_leap;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.FragmentActivity;
import android.util.AttributeSet;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;


public class Loadingscreen extends FragmentActivity {

    private boolean resourcesLoaded;
    private boolean databaseLoaded;
    private boolean backgroundCreated;
    private boolean soundLoaded;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loadingscreen);

        resourcesLoaded = false;
        databaseLoaded = false;
        backgroundCreated = false;
        soundLoaded = false;

        loadSound();
        loadLocalDb();
        loadResources(); // Initializes resources such as images, sounds etc


        new CountDownTimer(3000,1000){
            @Override
            public void onTick(long millisUntilFinished){}

            @Override
            public void onFinish(){
                //set the new Content of your activity
                if(resourcesLoaded && databaseLoaded && backgroundCreated && soundLoaded) {
                    Intent intent = new Intent(Loadingscreen.this, MenuActivity.class);
                    startActivity(intent);
                }
                else{
                    System.out.println("DEBUG: ERROR: Resources not loaded..");
                    recreate();
                }
            }
        }.start();

    }

    private void loadResources() {
       // Resources.DEFAULT_BACKGROUND = getResources().getDrawable(R.drawable.background_s3);
       // Resources.WHALE = getResources().getDrawable(R.drawable.whale);

        Display display = getWindowManager().getDefaultDisplay();
        int screen_width = display.getWidth();  // deprecated
        int screen_height = display.getHeight();  // deprecated
        if(screen_height > screen_width){
            int prev_width = screen_width;
            screen_width = screen_height;
            screen_height = prev_width;
        }
        //Resources.RAW_BACKGROUND = getResources().getDrawable(R.drawable.background_sky_mountains);

        Resources.RAW_BACKGROUND = BitmapFactory.decodeResource(getResources(), R.drawable.background_sky_mountains);
        Resources.RAW_BACKGROUND = Bitmap.createScaledBitmap(Resources.RAW_BACKGROUND, screen_width, screen_height, false);

        Resources.PILLAR_LEFT = BitmapFactory.decodeResource(getResources(), R.drawable.plateu_left);
        Resources.PILLAR_LEFT = Bitmap.createScaledBitmap(Resources.PILLAR_LEFT, 300, 600, false);

        Resources.PILLAR_RIGHT = BitmapFactory.decodeResource(getResources(), R.drawable.plateu_right);
        Resources.PILLAR_RIGHT = Bitmap.createScaledBitmap(Resources.PILLAR_RIGHT, 300, 500, false);

        Resources.WATER = BitmapFactory.decodeResource(getResources(), R.drawable.water_mass);
        Resources.WATER = Bitmap.createScaledBitmap(Resources.WATER, 1000, 400, false);

        Resources.WHALE = BitmapFactory.decodeResource(getResources(), R.drawable.whale);
        Resources.WHALE = Bitmap.createScaledBitmap(Resources.WHALE, 132, 84, false);

        Resources.LIFE_LEFT = BitmapFactory.decodeResource(getResources(), R.drawable.heart);
        Resources.LIFE_LEFT = Bitmap.createScaledBitmap(Resources.LIFE_LEFT, 40, 34, false);

        Resources.SHEEP_ONE = BitmapFactory.decodeResource(getResources(), R.drawable.sheep1);
        Resources.SHEEP_ONE = Bitmap.createScaledBitmap(Resources.SHEEP_ONE, 100, 100, false);

        Resources.SHEEP_TWO = BitmapFactory.decodeResource(getResources(), R.drawable.sheep2);
        Resources.SHEEP_TWO = Bitmap.createScaledBitmap(Resources.SHEEP_TWO, 100, 100, false);

        Resources.SHEEP_THREE = BitmapFactory.decodeResource(getResources(), R.drawable.sheep3);
        Resources.SHEEP_THREE = Bitmap.createScaledBitmap(Resources.SHEEP_THREE, 100, 100, false);

        Resources.SHEEP_FOUR = BitmapFactory.decodeResource(getResources(), R.drawable.sheep4);
        Resources.SHEEP_FOUR = Bitmap.createScaledBitmap(Resources.SHEEP_FOUR, 100, 100, false);

        Resources.SHEEP_FIVE = BitmapFactory.decodeResource(getResources(), R.drawable.sheep5);
        Resources.SHEEP_FIVE = Bitmap.createScaledBitmap(Resources.SHEEP_FIVE, 100, 100, false);

        Resources.SHEEP_SIX = BitmapFactory.decodeResource(getResources(), R.drawable.sheep6);
        Resources.SHEEP_SIX = Bitmap.createScaledBitmap(Resources.SHEEP_SIX, 100, 100, false);

        Resources.SHEEP_SEVEN = BitmapFactory.decodeResource(getResources(), R.drawable.sheep7);
        Resources.SHEEP_SEVEN = Bitmap.createScaledBitmap(Resources.SHEEP_SEVEN, 100, 100, false);

        Resources.SHEEP_EIGTH = BitmapFactory.decodeResource(getResources(), R.drawable.sheep8);
        Resources.SHEEP_EIGTH = Bitmap.createScaledBitmap(Resources.SHEEP_EIGTH, 100, 100, false);

        Resources.DEFAULT_BACKGROUND =  createBackground(screen_width, screen_height);
        resourcesLoaded = true;
    }

    private Drawable createBackground(int screen_width, int screen_height){
        Bitmap raw_background = Resources.RAW_BACKGROUND;
        Bitmap pillar_left = Resources.PILLAR_LEFT;
        Bitmap pillar_right = Resources.PILLAR_RIGHT;
        Bitmap water = Resources.WATER;
        try {

            Canvas canvas = new Canvas(raw_background);
            // JUST CHANGE TO DIFFERENT Bitmaps and coordinates .
            canvas.drawBitmap(water, 230, screen_height-200, null);
            canvas.drawBitmap(pillar_left, 0, screen_height-380, null);
            canvas.drawBitmap(pillar_right, screen_width-300, screen_height-380, null);

        } catch (Exception e) {
            e.printStackTrace();
        }
        backgroundCreated = true;
        return new BitmapDrawable(getResources(),raw_background);
    }

    private void loadSound(){
        Resources.soundManager = new SoundManager(this);
        soundLoaded = true;
    }

    private void loadLocalDb(){

        Resources.HIGHSCORE = new Highscore(this);
        Resources.HIGHSCORE.open();
        databaseLoaded = true;
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
