package kycklingstuds.kycklingstuds;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;


public class PlayActivity extends FragmentActivity {

    private Surface mSurface;
    private Game mGame;

    static LinearLayout mRetryContainer;


    private void init(){
        PlayActivity.mRetryContainer = (LinearLayout) findViewById(R.id.retryContainer);
        PlayActivity.mRetryContainer.setVisibility(View.GONE);
        mGame = new Game(this);

        mSurface = (Surface)findViewById(R.id.gameCanvas);
        mSurface.setOnTouchListener(mSurface);
        mSurface.setGame(mGame);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_play);
        System.out.println("DEBUG: ONCREATE PLAY");
        init();
    }



     public void restartGame(View v){
        mGame.restartGame();
        mSurface.restartSurface();
        PlayActivity.mRetryContainer.setVisibility(View.GONE);
    }

    public void exitToMenu(View v){
        finish();
    }

    @Override
    protected void onStop(){
        super.onStop();
        mGame.itIsTimeToExit();
        finish();
    }
}
