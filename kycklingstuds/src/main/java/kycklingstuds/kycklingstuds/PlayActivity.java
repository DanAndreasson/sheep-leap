package kycklingstuds.kycklingstuds;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;


public class PlayActivity extends FragmentActivity {

    private static Context mContext;
    private Surface mSurface;
    private Game mGame;
    public static Activity playActivity;
    static LinearLayout mRetryContainer;
    static LinearLayout mSplashTextContainer;
    public static Context getContext() {
        //  return instance.getApplicationContext();
        return mContext;
    }
    
    private void init() {
        PlayActivity.mRetryContainer = (LinearLayout) findViewById(R.id.retryContainer);
        PlayActivity.mSplashTextContainer = (LinearLayout) findViewById(R.id.splashTextContainer);
        PlayActivity.mRetryContainer.setVisibility(View.GONE);
        mGame = new Game(this);

        playActivity = this;
        mSurface = (Surface) findViewById(R.id.gameCanvas);
        mSurface.setOnTouchListener(mSurface);
        mSurface.setGame(mGame);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getApplicationContext();
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_play);
        System.out.println("DEBUG: ONCREATE PLAY");
        init();
    }

    @Override
    public void onBackPressed() {
        mGame.itIsTimeToExit();
        finish();
    }


    public void restartGame(View v) {
        mGame.restartGame();
        mSurface.restartSurface();
        PlayActivity.mRetryContainer.setVisibility(View.GONE);
    }

    public void exitToMenu(View v) {
        finish();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mGame.itIsTimeToExit();
        finish();
    }
}
