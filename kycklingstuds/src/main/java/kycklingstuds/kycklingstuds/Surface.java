package kycklingstuds.kycklingstuds;


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

public class Surface extends SurfaceView implements SurfaceHolder.Callback, OnTouchListener, Runnable {
    private Canvas mCanvas;
    private SurfaceHolder mHolder;
    private Paint mPaint;
    private Paint mPaintPC; // Peronal Record Paint
    private Game game;
    private boolean running;
    private int pollCount;
    private View root;

    private Thread thread;
    private Paint splashPaint;
    private RectF splashPos;
    private boolean shouldDrawText, hasInitializedText;

    private Splashtext levelSplashText;

    public Surface(Context context, AttributeSet attrs) {
        super(context, attrs);

        mHolder = this.getHolder();
        mHolder.addCallback(this);
        mPaint = new Paint(Color.BLACK);
        mPaint.setColor(Color.BLACK);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setTextSize(50);

        mPaintPC = new Paint(Color.RED);
        mPaintPC.setColor(Color.RED);
        mPaintPC.setTextSize(25);


    }

    public void restartSurface() {
        running = true;
        shouldDrawText = true;
        hasInitializedText = false;
    }

    public void setGame(Game g) {
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
        game.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }

    @Override
    public void onDraw(Canvas c) {
        Resources.DEFAULT_BACKGROUND.draw(c);

        for (int i = 0; i < game.getLivesLeft(); ++i) {
            c.drawBitmap(Resources.LIFE_LEFT, 50 + (50 * i), 50, null);
        }
        c.drawText(Integer.toString(game.getScore()) + "p", getWidth() - 155, 100, mPaint);
        c.drawText("Personal best: " + Integer.toString(Resources.HIGHSCORE.getHighscore().getPoints()) + "p", getWidth() - 230, 135, mPaintPC);
        c.drawText("Level: " + Integer.toString(game.getLevel()), getWidth() - 230, 45, mPaint);

       /* if (shouldDrawText) {
            drawText(c, "Hejsan Morja, fallerallera");
        }*/

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
        // c.drawLine(0, 250, 2000, 250, mPaint);
       /* for (int i = 0; i < game.pathSize(); ++i){
            c.drawOval(game.getWaypoint(i), mPaint);
        }*/


    }

/*
    public void initText(String splashText) {
        Rect areaRect = new Rect(0, 0, getWidth(), getHeight());

        splashPos = new RectF(areaRect);
        splashPos.right = splashPaint.measureText(splashText, 0, splashText.length());
        splashPos.bottom = splashPaint.descent() - splashPaint.ascent();

        splashPos.left += (areaRect.width() - splashPos.right) / 2.0f;
        splashPos.top += ((areaRect.height() - splashPos.bottom) / 2.0f) * 0.40f;
    }

    public void drawText(Canvas c, String splashText) {
        if (!hasInitializedText) {
            initText(splashText);
            hasInitializedText = true;
        }
        c.drawText(splashText, splashPos.left, splashPos.top - splashPaint.ascent(), splashPaint);
    }

*/
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
        }
    }

}
