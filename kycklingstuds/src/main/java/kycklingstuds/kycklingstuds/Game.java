package kycklingstuds.kycklingstuds;

import android.graphics.RectF;
import android.support.v4.app.FragmentActivity;
import android.view.View;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class Game implements Runnable {

    // Instance Variables
    private int playerID;
    private int score;
    private int highscore; // Load from local database or sync with server DB?
    private boolean paused;
    private int livesLeft;

    private float boardXPos;
    private float boardYPos;
    private float boardWidth;
    private float boardHeight;

    private float boardMinXPos;
    private float boardMaxXPos;

    private int bouncySize;

    private float mStartX;
    private float mStartY;
    private ArrayList<RectF> path;
    private ArrayList<RectF> deathPoints;
    private int firstDeath, secondDeath, thirdDeath, finishPoint;

    private Queue<Bouncy> activeBouncies;
    private Queue<Spawn> spawnPool;

    private int gameTicks;
    private int boardAnimationStep;
    private int boardAnimationTimes;
    private int boardAnimationState;
    private boolean boardAnimationDownwards;

    private int difficulty;
    private int passedChunks;


    //public Bouncy b;
    Thread thread;
    private int requiredPassedChunks;
    private boolean lostGame;

    private FragmentActivity playActivity;
    private boolean timeToExit;

    // Constructor
    public Game(FragmentActivity fa) {
        this.playActivity = fa;
        this.mStartX = -10;
        this.mStartY = 300;

        this.bouncySize = 75;

        this.timeToExit = false;
        // Resources.soundManager.playBackgroundMusic(SoundManager.BACKGROUND_MUSIC );
        setPath();
        this.thread = new Thread(this);

        this.activeBouncies = new LinkedList<Bouncy>();
        this.spawnPool = new LinkedList<Spawn>();

        restartGame();

    }

    public void restartGame() {
        this.lostGame = false;

        this.livesLeft = 4;
        this.score = 0;
        this.boardXPos = 310;
        this.boardYPos = 555;
        this.boardHeight = 72;
        this.boardWidth = 130;
        this.boardMinXPos = 270;
        this.boardMaxXPos = 990;

        this.boardAnimationDownwards = false;
        this.boardAnimationState = 0;
        this.boardAnimationStep = 1;
        this.boardAnimationTimes = 20;


        this.paused = false;

        this.passedChunks = 0;
        this.difficulty = 0;
        this.requiredPassedChunks = 2;

        this.spawnPool.clear();
        this.activeBouncies.clear();

        this.gameTicks = 0;

        System.out.println("DEBUG: RestrartGame");

        fillSpawnPool();
    }

    private void fillSpawnPool() {
        int randomChunk = randomNrRange(1, 3) + (3 * this.difficulty);
        switch (randomChunk) {
            // DIFFICULT 0
            case 1:
                this.spawnPool = chunkOne();
                break;
            case 2:
                this.spawnPool = chunkOne();
                break;
            case 3:
                this.spawnPool = chunkOne();
                break;
            // DIFFICULT 1
            case 4:
                this.spawnPool = chunkFour();
                break;
            case 5:
                this.spawnPool = chunkFour();
                break;
            case 6:
                this.spawnPool = chunkFour();
                break;
            // DIFFICULT 2
            case 7:
                this.spawnPool = chunkFour();
                break;
            case 8:
                this.spawnPool = chunkFour();
                break;
            case 9:
                this.spawnPool = chunkFour();
                break;
            default:
                this.spawnPool = chunkFour();
                break;
        }
    }

    private Queue<Spawn> chunkOne() {
        Queue<Spawn> sp = new LinkedList<Spawn>();


        sp.add(new Spawn(this, 100));
        sp.add(new Spawn(this, 100));
        sp.add(new Spawn(this, 100));
        sp.add(new Spawn(this, 150));

        sp.add(new Spawn(this, 1300));
        sp.add(new Spawn(this, 30));
        sp.add(new Spawn(this, 30));
        sp.add(new Spawn(this, 30));

        sp.add(new Spawn(this, 600));
        sp.add(new Spawn(this, 100));
        sp.add(new Spawn(this, 50));

        sp.add(new Spawn(this, 1100));
        sp.add(new Spawn(this, 200));
        sp.add(new Spawn(this, 200));
        sp.add(new Spawn(this, 200));
        sp.add(new Spawn(this, 200));
        sp.add(new Spawn(this, 200));
        sp.add(new Spawn(this, 230));
        sp.add(new Spawn(this, 200));
        sp.add(new Spawn(this, 200));
        sp.add(new Spawn(this, 200));

        sp.add(new Spawn(this, 1150));
        sp.add(new Spawn(this, 200));
        sp.add(new Spawn(this, 200));
        sp.add(new Spawn(this, 200));
        sp.add(new Spawn(this, 230));
        sp.add(new Spawn(this, 200));
        sp.add(new Spawn(this, 200));
        sp.add(new Spawn(this, 200));
        sp.add(new Spawn(this, 230));
        sp.add(new Spawn(this, 200));

        sp.add(new Spawn(this, 1150));
        sp.add(new Spawn(this, 200));
        sp.add(new Spawn(this, 200));
        sp.add(new Spawn(this, 200));
        sp.add(new Spawn(this, 230));
        sp.add(new Spawn(this, 200));
        sp.add(new Spawn(this, 200));
        sp.add(new Spawn(this, 200));
        sp.add(new Spawn(this, 230));
        sp.add(new Spawn(this, 200));
        sp.add(new Spawn(this, 200));
        sp.add(new Spawn(this, 200));
        sp.add(new Spawn(this, 230));
        sp.add(new Spawn(this, 200));
        sp.add(new Spawn(this, 200));
        sp.add(new Spawn(this, 200));
        sp.add(new Spawn(this, 200));
        sp.add(new Spawn(this, 200));
        sp.add(new Spawn(this, 200));


        //sp.add(new Spawn(this, 50));
        //sp.add(new Spawn(this, 50));


       /* sp.add(new Spawn(this, 100));
        sp.add(new Spawn(this, 20));
        sp.add(new Spawn(this, 20));
        sp.add(new Spawn(this, 20));
        sp.add(new Spawn(this, 20));
        sp.add(new Spawn(this, 20));
        sp.add(new Spawn(this, 100));
        sp.add(new Spawn(this, 200));
        sp.add(new Spawn(this, 300));
        sp.add(new Spawn(this, 200));
        sp.add(new Spawn(this, 250));
        sp.add(new Spawn(this, 300));
        sp.add(new Spawn(this, 100));
        sp.add(new Spawn(this, 200));
        sp.add(new Spawn(this, 300));
        sp.add(new Spawn(this, 100));
        sp.add(new Spawn(this, 200));
        sp.add(new Spawn(this, 300));
        sp.add(new Spawn(this, 100));
        sp.add(new Spawn(this, 200));
        sp.add(new Spawn(this, 300));
        sp.add(new Spawn(this, 100));
        sp.add(new Spawn(this, 200));
        sp.add(new Spawn(this, 300));
        sp.add(new Spawn(this, 100));
        sp.add(new Spawn(this, 200));
        sp.add(new Spawn(this, 300));
        sp.add(new Spawn(this, 100));
        sp.add(new Spawn(this, 200));
        sp.add(new Spawn(this, 300));
        sp.add(new Spawn(this, 100));
        sp.add(new Spawn(this, 200));
        sp.add(new Spawn(this, 300));
        sp.add(new Spawn(this, 20));
        sp.add(new Spawn(this, 20));
        sp.add(new Spawn(this, 20));
        sp.add(new Spawn(this, 20));
        sp.add(new Spawn(this, 20));
        sp.add(new Spawn(this, 100));
        sp.add(new Spawn(this, 200));
        sp.add(new Spawn(this, 300));
        sp.add(new Spawn(this, 100));
        sp.add(new Spawn(this, 200));
        sp.add(new Spawn(this, 300));
        sp.add(new Spawn(this, 100));
        sp.add(new Spawn(this, 200));
        sp.add(new Spawn(this, 300));
        sp.add(new Spawn(this, 100));
        sp.add(new Spawn(this, 200));
        sp.add(new Spawn(this, 300));
        sp.add(new Spawn(this, 100));
        sp.add(new Spawn(this, 200));
        sp.add(new Spawn(this, 300));*/
        return sp;

    }

    private Queue<Spawn> chunkTwo() {
        Queue<Spawn> sp = new LinkedList<Spawn>();

        sp.add(new Spawn(this, 900));
        sp.add(new Spawn(this, 20));
        sp.add(new Spawn(this, 20));
        sp.add(new Spawn(this, 20));
        sp.add(new Spawn(this, 20));
        sp.add(new Spawn(this, 20));
        sp.add(new Spawn(this, 100));
        sp.add(new Spawn(this, 200));
        sp.add(new Spawn(this, 300));
        sp.add(new Spawn(this, 100));
        sp.add(new Spawn(this, 200));
        sp.add(new Spawn(this, 300));
        sp.add(new Spawn(this, 100));
        sp.add(new Spawn(this, 200));
        sp.add(new Spawn(this, 300));
        sp.add(new Spawn(this, 100));
        sp.add(new Spawn(this, 200));
        sp.add(new Spawn(this, 300));
        sp.add(new Spawn(this, 100));
        sp.add(new Spawn(this, 200));
        sp.add(new Spawn(this, 300));
        sp.add(new Spawn(this, 100));
        sp.add(new Spawn(this, 200));
        sp.add(new Spawn(this, 300));
        sp.add(new Spawn(this, 100));
        sp.add(new Spawn(this, 200));
        sp.add(new Spawn(this, 300));
        sp.add(new Spawn(this, 100));
        sp.add(new Spawn(this, 200));
        sp.add(new Spawn(this, 300));
        sp.add(new Spawn(this, 100));
        sp.add(new Spawn(this, 200));
        sp.add(new Spawn(this, 300));
        sp.add(new Spawn(this, 20));
        sp.add(new Spawn(this, 20));
        sp.add(new Spawn(this, 20));
        sp.add(new Spawn(this, 20));
        sp.add(new Spawn(this, 20));
        sp.add(new Spawn(this, 100));
        sp.add(new Spawn(this, 200));
        sp.add(new Spawn(this, 300));
        sp.add(new Spawn(this, 100));
        sp.add(new Spawn(this, 200));
        sp.add(new Spawn(this, 300));
        sp.add(new Spawn(this, 100));
        sp.add(new Spawn(this, 200));
        sp.add(new Spawn(this, 300));
        sp.add(new Spawn(this, 100));
        sp.add(new Spawn(this, 200));
        sp.add(new Spawn(this, 300));
        sp.add(new Spawn(this, 100));
        sp.add(new Spawn(this, 200));
        sp.add(new Spawn(this, 300));
        return sp;

    }

    private Queue<Spawn> chunkThird() {
        Queue<Spawn> sp = new LinkedList<Spawn>();

        sp.add(new Spawn(this, 900));
        sp.add(new Spawn(this, 20));
        sp.add(new Spawn(this, 20));
        sp.add(new Spawn(this, 20));
        sp.add(new Spawn(this, 20));
        sp.add(new Spawn(this, 20));
        sp.add(new Spawn(this, 100));
        sp.add(new Spawn(this, 200));
        sp.add(new Spawn(this, 300));
        sp.add(new Spawn(this, 100));
        sp.add(new Spawn(this, 200));
        sp.add(new Spawn(this, 300));
        sp.add(new Spawn(this, 100));
        sp.add(new Spawn(this, 200));
        sp.add(new Spawn(this, 300));
        sp.add(new Spawn(this, 100));
        sp.add(new Spawn(this, 200));
        sp.add(new Spawn(this, 300));
        sp.add(new Spawn(this, 100));
        sp.add(new Spawn(this, 200));
        sp.add(new Spawn(this, 300));
        sp.add(new Spawn(this, 100));
        sp.add(new Spawn(this, 200));
        sp.add(new Spawn(this, 300));
        sp.add(new Spawn(this, 100));
        sp.add(new Spawn(this, 200));
        sp.add(new Spawn(this, 300));
        sp.add(new Spawn(this, 100));
        sp.add(new Spawn(this, 200));
        sp.add(new Spawn(this, 300));
        sp.add(new Spawn(this, 100));
        sp.add(new Spawn(this, 200));
        sp.add(new Spawn(this, 300));
        sp.add(new Spawn(this, 20));
        sp.add(new Spawn(this, 20));
        sp.add(new Spawn(this, 20));
        sp.add(new Spawn(this, 20));
        sp.add(new Spawn(this, 20));
        sp.add(new Spawn(this, 100));
        sp.add(new Spawn(this, 200));
        sp.add(new Spawn(this, 300));
        sp.add(new Spawn(this, 100));
        sp.add(new Spawn(this, 200));
        sp.add(new Spawn(this, 300));
        sp.add(new Spawn(this, 100));
        sp.add(new Spawn(this, 200));
        sp.add(new Spawn(this, 300));
        sp.add(new Spawn(this, 100));
        sp.add(new Spawn(this, 200));
        sp.add(new Spawn(this, 300));
        sp.add(new Spawn(this, 100));
        sp.add(new Spawn(this, 200));
        sp.add(new Spawn(this, 300));
        return sp;

    }

    private Queue<Spawn> chunkFour() {
        Queue<Spawn> sp = new LinkedList<Spawn>();


        sp.add(new Spawn(this, 900));
        sp.add(new Spawn(this, 100));
        sp.add(new Spawn(this, 100));
        sp.add(new Spawn(this, 150));
        sp.add(new Spawn(this, 50));
        sp.add(new Spawn(this, 250));
        sp.add(new Spawn(this, 100));
        sp.add(new Spawn(this, 150));

        sp.add(new Spawn(this, 900));
        sp.add(new Spawn(this, 250));
        sp.add(new Spawn(this, 90));
        sp.add(new Spawn(this, 150));

       /* sp.add(new Spawn(this, 600));
        sp.add(new Spawn(this, 100));
        sp.add(new Spawn(this, 50));

        sp.add(new Spawn(this, 1100));
        sp.add(new Spawn(this, 200));
        sp.add(new Spawn(this, 200));
        sp.add(new Spawn(this, 200));
        sp.add(new Spawn(this, 200));
        sp.add(new Spawn(this, 200));
        sp.add(new Spawn(this, 230));
        sp.add(new Spawn(this, 200));
        sp.add(new Spawn(this, 200));
        sp.add(new Spawn(this, 200));

        sp.add(new Spawn(this, 1150));
        sp.add(new Spawn(this, 200));
        sp.add(new Spawn(this, 200));
        sp.add(new Spawn(this, 200));
        sp.add(new Spawn(this, 230));
        sp.add(new Spawn(this, 200));
        sp.add(new Spawn(this, 200));
        sp.add(new Spawn(this, 200));
        sp.add(new Spawn(this, 230));
        sp.add(new Spawn(this, 200));

        sp.add(new Spawn(this, 1150));
        sp.add(new Spawn(this, 200));
        sp.add(new Spawn(this, 200));
        sp.add(new Spawn(this, 200));
        sp.add(new Spawn(this, 230));
        sp.add(new Spawn(this, 200));
        sp.add(new Spawn(this, 200));
        sp.add(new Spawn(this, 200));
        sp.add(new Spawn(this, 230));
        sp.add(new Spawn(this, 200));
        sp.add(new Spawn(this, 200));
        sp.add(new Spawn(this, 200));
        sp.add(new Spawn(this, 230));
        sp.add(new Spawn(this, 200));
        sp.add(new Spawn(this, 200));
        sp.add(new Spawn(this, 200));
        sp.add(new Spawn(this, 200));
        sp.add(new Spawn(this, 200));
        sp.add(new Spawn(this, 200));*/

        System.out.println("DEBUG: Size: " + sp.size());
        return sp;

    }

    private int randomNrRange(int min, int max) {
        return min + (int) (Math.random() * ((max - min) + 1));
    }

    public void start() {
        thread.start();
    }

    public void setPath() {
        path = new ArrayList<RectF>();
        float x = mStartX, y = mStartY, yVelo = 0f, xVelo = 0.50f;

        for (int i = 0; i <= 120; ++i) {
            x = x + 2;
            path.add(new RectF(x, y, x + this.bouncySize, y + this.bouncySize));
        }
        for (int i = 0; i <= 100; ++i) {
            y = y - 3f + yVelo;
            x = x + xVelo;
            yVelo += (3f / 100);
            path.add(new RectF(x, y, x + this.bouncySize, y + this.bouncySize));
        }

        for (int i = 0; i <= 235; ++i) {
            y = y + 3f - yVelo;
            x = x + xVelo;
            yVelo -= (3f / 235f);
            path.add(new RectF(x, y, x + this.bouncySize, y + this.bouncySize));
        }
        firstDeath = this.path.size() - 1;
        yVelo = 0;
        for (int i = 0; i <= 235; ++i) {
            y = y - 3f + yVelo;
            x = x + xVelo;
            yVelo += (3f / 235f);
            path.add(new RectF(x, y, x + this.bouncySize, y + this.bouncySize));
        }
        for (int i = 0; i <= 235; ++i) {
            y = y + 3f - yVelo;
            x = x + xVelo;
            yVelo -= (3f / 235f);
            path.add(new RectF(x, y, x + this.bouncySize, y + this.bouncySize));
        }
        secondDeath = this.path.size() - 1;
        yVelo = 0;
        for (int i = 0; i <= 235; ++i) {
            y = y - 3f + yVelo;
            x = x + xVelo;
            yVelo += (3f / 235f);
            path.add(new RectF(x, y, x + this.bouncySize, y + this.bouncySize));
        }
        for (int i = 0; i <= 235; ++i) {
            y = y + 3f - yVelo;
            x = x + xVelo;
            yVelo -= (3f / 235f);
            path.add(new RectF(x, y, x + this.bouncySize, y + this.bouncySize));
        }
        thirdDeath = this.path.size() - 1;
        yVelo = 0;
        for (int i = 0; i <= 235; ++i) {
            y = y - 3f + yVelo;
            x = x + xVelo;
            yVelo += (3f / 235f);
            path.add(new RectF(x, y, x + this.bouncySize, y + this.bouncySize));
        }
        for (int i = 0; i <= 100; ++i) {
            y = y + 3f - yVelo;
            x = x + xVelo;
            yVelo -= (3f / 100f);
            path.add(new RectF(x, y, x + this.bouncySize, y + this.bouncySize));
        }
        finishPoint = this.path.size() - 1;
        for (int i = 0; i <= 120; ++i) {
            x = x + 2;
            path.add(new RectF(x, y, x + this.bouncySize, y + this.bouncySize));
        }

        deathPoints = new ArrayList<RectF>();
        RectF first = this.getWaypoint(firstDeath);
        RectF firstDeathRect = new RectF();
        firstDeathRect.set(first.left, first.top + 50, first.right, first.bottom);
        deathPoints.add(firstDeathRect);

        RectF second = this.getWaypoint(secondDeath);
        RectF secondDeathRect = new RectF();
        secondDeathRect.set(second.left, second.top + 50, second.right, second.bottom);
        deathPoints.add(secondDeathRect);

        RectF third = this.getWaypoint(thirdDeath);
        RectF thirdDeathRect = new RectF();
        thirdDeathRect.set(third.left, third.top + 50, third.right, third.bottom);
        deathPoints.add(thirdDeathRect);
    }

    public void setBoardXPos(float boardXPos) {
        if (boardXPos - boardWidth / 2 <= boardMinXPos) {
            this.boardXPos = boardMinXPos;
        } else if (boardXPos >= boardMaxXPos - boardWidth / 2) {
            this.boardXPos = boardMaxXPos - boardWidth;
        } else {
            this.boardXPos = boardXPos - boardWidth / 2;
        }
    }

    public void setBoardYPos(float boardYPos) {
        //Game.boardYPos = boardYPos;
    }

    public int getScore() {
        return score;
    }

    public float getBoardXPos() {
        return this.boardXPos;
    }

    public float getBoardYPos() {
        return this.boardYPos + (this.boardAnimationState * this.boardAnimationStep);
    }

    public float getBoardWidthPos() {
        return getBoardXPos() + boardWidth;
    }

    public float getBoardHeightPos() {
        return getBoardYPos() + boardHeight;
    }

    public boolean isPaused() {
        return this.paused;
    }

    public void setPaused(boolean p) {
        this.paused = p;
    }

    public RectF getWaypoint(int i) {
        return path.get(i);
    }

    public int pathSize() {
        return path.size();
    }

    public RectF getDeathPoint(int i) {
        return this.deathPoints.get(i);
    }

    public Queue<Bouncy> getActiveBouncies() {
        return this.activeBouncies;
    }

    public int getLivesLeft() {
        return this.livesLeft;
    }

    private void animateBoard(){
        if (this.boardAnimationState > 0) {
            if (this.boardAnimationState == this.boardAnimationTimes)
                this.boardAnimationDownwards = false;
            if (this.boardAnimationDownwards)
                ++this.boardAnimationState;
            else
                --this.boardAnimationState;
            updateBoardPosition();
        }
    }

    private void moveActiveBouncies(){
        synchronized (this.activeBouncies) {
            for (Bouncy b : this.activeBouncies) {
                b.move();
            }
        }
    }

    private void spawnBouncie(){
        if (this.spawnPool.size() > 0) {
            if (this.gameTicks == this.spawnPool.peek().get_ticks()) {

                synchronized (this.activeBouncies) {

                    this.activeBouncies.add(this.spawnPool.poll().getBouncy());
                }
                gameTicks = 0;
            }
        } else {
            if (++this.passedChunks >= this.requiredPassedChunks)
                ++this.difficulty;
            fillSpawnPool();
        }
    }

    private void wait(int x){
        try {
            thread.sleep(x);
        } catch (Exception e) {
            System.out.println("DEBUG: Exception Sleep: " + e);
        }
    }

    @Override
    public void run() {

        while (!this.timeToExit ) {
             while (!this.lostGame) {
                ++this.gameTicks;

                moveActiveBouncies();

                animateBoard();

                spawnBouncie();


                wait(6);
            }
        }
    }

    public boolean checkCollision(int pathIndex) {
        int bounceXLeft = (int) this.path.get(pathIndex).left;
        int bounceXRight = (int) this.path.get(pathIndex).right;
        boolean c = (bounceXLeft >= getBoardXPos() && bounceXLeft <= getBoardWidthPos() ||
                (bounceXRight >= getBoardXPos() && bounceXRight <= getBoardWidthPos()));

        if (c) {
            ++this.boardAnimationState;
            this.boardAnimationDownwards = true;
            Resources.soundManager.playSound(SoundManager.BOUNCE_SOUND);
        }
        return c;
    }

    public void receiveScore() {
        ++this.score;
    }

    public int getFirstDeathPoint() {
        return this.firstDeath;
    }

    public int getSecondDeathPoint() {
        return this.secondDeath;
    }

    public int getThirdDeathPoint() {
        return this.thirdDeath;
    }

    public void lifeLost() {
        Resources.soundManager.playSound(SoundManager.SPLASH_SOUND);
        if (--this.livesLeft <= 0) {
            gameOver();
        }
    }

    public void gameOver(){
        this.lostGame = true;
        this.paused = true;
        Resources.soundManager.playSound(SoundManager.GAMEOVER_SOUND);
        if (this.score > 0) {
            Resources.HIGHSCORE.createScore(this.score);
            Resources.HIGHSCORE.newHighScore();
        }

        playActivity.runOnUiThread(new Runnable() {
            public void run() {
                PlayActivity.mRetryContainer.setVisibility(View.VISIBLE);
            }
        });

    }


    public void updateBoardPosition() {
        Resources.WHALE.setBounds((int) getBoardXPos(), (int) getBoardYPos(), (int) getBoardWidthPos(), (int) getBoardHeightPos());
    }

    public boolean isTimeToExit() {
        return this.timeToExit;
    }

    public void itIsTimeToExit() {
        this.timeToExit = true;
        this.lostGame = true;
        this.paused = true;
        Resources.soundManager.stopSound();
    }
}
