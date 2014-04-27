package sheep_leap.sheep_leap;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;


public class PlayActivity extends FragmentActivity {

    private static Context mContext;
    private Surface mSurface;
    private Game mGame;
    public static Activity playActivity;
    static LinearLayout mRetryContainer;
    static LinearLayout mSplashTextContainer;

    boolean is_paused = false;

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
        System.out.println("DEBUG: ONCREATE PLAY");
    }

    @Override
    protected void onResume() {
        super.onResume();
        System.out.println("DEBUG: PLAY ONRESUME");
        setContentView(R.layout.activity_play);
        init();
        //setContentView(R.layout.activity_loadingscreen);
        /*new CountDownTimer(1500,1000){
            @Override
            public void onTick(long millisUntilFinished){}

            @Override
            public void onFinish(){
                setContentView(R.layout.activity_play);
                init();
            }
        }.start();
*/
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    public void onBackPressed() {
        mGame.itIsTimeToExit();
        finish();
    }

    @TargetApi(14)
    public void pauseBtnClicked(View v){
        Button pauseBtn = (Button)findViewById(R.id.pauseButton);
        if(!is_paused && !mGame.isGameOver()){
            pauseBtn.setBackground((getResources().getDrawable(android.R.drawable.ic_media_play)));
            mGame.pauseNplayGame();
            is_paused = true;
        }
        else if (!mGame.isGameOver()){
            pauseBtn.setBackground((getResources().getDrawable(android.R.drawable.ic_media_pause)));
            mGame.pauseNplayGame();
            is_paused = false;
        }

    }

    @TargetApi(14)
    public void retryBtnClicked(View v){
        if(!is_paused && !mGame.isGameOver()){
            mGame.restartGame();
        }

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
