package kycklingstuds.kycklingstuds;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Activity;
import android.view.View;
import android.widget.TextView;

public class Splashtext extends Thread {
    private int size;
    private int color;
    private int time;
    private TextView mTextView;
    private Activity mActivity;

    public Splashtext(int size, int color, int time, Activity activity) {
        this.size = size;
        this.time = time;
        this.color = color;
        mActivity = activity;
        System.out.println("DEBUG: Created new splashtext");
    }

    @TargetApi(14)
    private void animateText() {


        mTextView.animate()
                .alpha(0f)
                .setDuration(time)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        mTextView.setVisibility(View.INVISIBLE);
                    }
                });
    }


    public void drawText(final String text) {

        mActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mTextView = (TextView) PlayActivity.mSplashTextContainer.findViewById(R.id.splashTextView);
                mTextView.setAlpha(1.0f);
                mTextView.setTextSize(size);
                mTextView.setTextColor(color);
                mTextView.setVisibility(View.VISIBLE);
                mTextView.setText(text);
                System.out.println("DEBUG: Drawing splashtext");
                animateText();
            }
        });


    }


}

