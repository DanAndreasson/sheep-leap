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


    private Thread thread;

    public Surface(Context context, AttributeSet attrs) {
        super(context, attrs);
        mHolder = this.getHolder();
        mHolder.addCallback(this);
        mPaint = new Paint(Color.BLACK);
        mPaint.setColor(Color.BLACK);
        mPaint.setTextSize(50);

        mPaintPC = new Paint(Color.RED);
        mPaintPC.setColor(Color.RED);
        mPaintPC.setTextSize(25);

    }

    public void restartSurface() {
        running = true;
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
        Resources.WHALE.setBounds((int) game.getBoardXPos(), (int) game.getBoardYPos(), (int) game.getBoardWidthPos(), (int) game.getBoardHeightPos());
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
            c.drawBitmap(Resources.LIFE_LEFT, 50 + (50 * i), 50, mPaint);
        }
        c.drawText(Integer.toString(game.getScore()) + "p", getWidth() - 155, 100, mPaint);
        c.drawText("Personal best: " + Integer.toString(Resources.HIGHSCORE.getHighscore().getPoints()) + "p", getWidth() - 230, 135, mPaintPC);

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

        Resources.WHALE.draw(c);

        // c.drawLine(0, 250, 2000, 250, mPaint);
       /* for (int i = 0; i < game.pathSize(); ++i){
            c.drawOval(game.getWaypoint(i), mPaint);
        }*/


    }


    @Override
    public void run() {
        System.out.println("DEBUG: TimetoExit: "+ game.isTimeToExit()+" running: " + running + ". paused? " + game.isPaused());
        while (!game.isTimeToExit() ) {
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
