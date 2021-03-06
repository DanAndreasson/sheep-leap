package sheep_leap.sheep_leap;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnTouchListener;

import java.text.DecimalFormat;

public class Surface extends SurfaceView implements SurfaceHolder.Callback, OnTouchListener, Runnable {
    private Canvas mCanvas;
    private SurfaceHolder mHolder;
    private Paint mPaint;
    private Paint scorePaint;
    private Paint mPaintPC; // Peronal Record Paint
    private Game game;
    private boolean running;
    private int pollCount;
    private View root;

    private Thread thread;
    private boolean hasLoaded;


    public Surface(Context context, AttributeSet attrs) {
        super(context, attrs);

        mHolder = this.getHolder();
        mHolder.addCallback(this);
        mPaint = new Paint(Color.BLACK);
        mPaint.setColor(Color.WHITE);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setTextSize(50);

        scorePaint = new Paint(Color.BLACK);
        scorePaint.setColor(Color.WHITE);
        scorePaint.setTextSize(70);

        mPaintPC = new Paint(Color.BLACK);
        mPaintPC.setColor(Color.WHITE);
        mPaintPC.setTextSize(25);


    }

    public void restartSurface() {
        running = true;

    }

    public void setGame(Game g) {
        hasLoaded = false;
        game = g;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        game.setBoardXPos(event.getX());
        game.updateBoardPosition();

        return true;
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        Resources.DEFAULT_BACKGROUND.setBounds(0, 0, getWidth(), getHeight());
        // Resources.WHALE.setBounds((int) game.getBoardXPos(), (int) game.getBoardYPos(), (int) game.getBoardWidthPos(), (int) game.getBoardHeightPos());
        thread = new Thread(this);
        thread.start();
        restartSurface();
        //game.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }


    @Override
    public void onDraw(Canvas c) {
        if (!hasLoaded){
            hasLoaded = true;
            game.start();
        }
        Resources.DEFAULT_BACKGROUND.draw(c);

        for (int i = 0; i < game.getLivesLeft(); ++i) {
            c.drawBitmap(Resources.LIFE_LEFT, 50 + (50 * i), 50, null);
        }

        c.drawText(Integer.toString(game.getScore()) + "p", (getWidth()/2) - 40, 100, scorePaint);
        c.drawText("Personal best: " + Integer.toString(Resources.HIGHSCORE.getHighscore().getPoints()) + "p", getWidth() - 230, 100, mPaintPC);
        c.drawText("Level: " + Integer.toString(game.getLevel()), getWidth() - 230, 45, mPaint);


        pollCount = 0;
        synchronized (game.getActiveBouncies()) {
            for (Bouncy b : game.getActiveBouncies()) {
                if (b.isFinished()) {
                    ++pollCount;
                    continue;
                }
                RectF r = b.getPos();
                c.drawBitmap(b.getSprite(), r.left, r.top, mPaint);
            }
            for (int i = 0; i < pollCount; ++i) {
                game.getActiveBouncies().poll();
            }
        }

        c.drawBitmap(Resources.WHALE, game.getBoardXPos(), game.getBoardYPos(), null);



    }


    @Override
    public void run() {
        System.out.println("DEBUG: TimetoExit: " + game.isTimeToExit() + " running: " + running + ". paused? " + game.isPaused());
        while (!game.isTimeToExit()) {
            while (running && !game.isPaused()) {

                try {
                    mCanvas = mHolder.lockCanvas();

                    synchronized (mHolder) {
                        postInvalidate();
                        //onDraw(mCanvas);
                    }
                } finally {
                    if (mCanvas != null) {
                        mHolder.unlockCanvasAndPost(mCanvas);
                    }
                }

            }
            try {
                Thread.sleep(10);
            } catch(InterruptedException e) {}
        }
    }


}
