package kycklingstuds.kycklingstuds;

import android.graphics.RectF;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class Game implements Runnable{

    // Instance Variables
    private Surface mSurface;
    private int mPlayerID;
    private int score;
    private int highscore; // Load from local database or sync with server DB?
    private boolean mPaused;
    private int livesLeft;

    private float mBoardXPos;
    private float mBoardYPos;
    private float mBoardWidth;
    private float mBoardHeight;

    private float mBoardMinXPos;
    private float mBoardMaxXPos;

    private float mStartX;
    private float mStartY;
    private ArrayList<RectF> path;
    private ArrayList<RectF> deathPoints;
    private int firstDeath, secondDeath, thirdDeath,finishPoint;

    private Queue<Bouncy> activeBouncies;
    private Queue<Spawn> spawnPool;
    private int maxSpawnTime;
    private int minSpawnTime;
    private int ticks;

    private int difficulty;
    private int passedChunks;


    //public Bouncy b;
    Thread thread;
    private int requiredPassedChunks;
    private int firstDeathPoint;

    // ADD SOME KIND OF DATASTRUCTURE WHICH CONTAINS ALL BOUNCIES!


    // Constructor
    public Game(){
        this.livesLeft = 4;
        this.score = 0;
        this.mBoardXPos = 310;
        this.mBoardYPos = 615;
        this.mBoardHeight = 72;
        this.mBoardWidth = 130;
        this.mBoardMinXPos = 270;
        this.mBoardMaxXPos = 990;

        this.mStartX = -10;
        this.mStartY = 400;

        this.mPaused = false;

        this.passedChunks = 0;
        this.difficulty = 0;
        this.requiredPassedChunks = 2;

        //b = new Bouncy(this);

        setPath();
        this.thread = new Thread(this);

        this.activeBouncies = new LinkedList<Bouncy>();
        this.spawnPool = new LinkedList<Spawn>();
        fillSpawnPool();

    }

    private void fillSpawnPool() {
        spawnPool = chunkFour();
        if (true) return ;
        int randomChunk = randomNrRange(1,3) + (3*this.difficulty);
        switch (randomChunk){
        // DIFFICULT 0
            case 1:
                spawnPool = chunkOne();
                break;
            case 2:
                spawnPool = chunkTwo();
                break;
            case 3:
                spawnPool = chunkThird();
                break;
        // DIFFICULT 1
            case 4:
                spawnPool = chunkOne();
                break;
            case 5:
                spawnPool = chunkTwo();
                break;
            case 6:
                spawnPool = chunkThird();
                break;
        // DIFFICULT 2
            case 7:
                spawnPool = chunkOne();
                break;
            case 8:
                spawnPool = chunkTwo();
                break;
            case 9:
                spawnPool = chunkThird();
                break;
            default:
                spawnPool = chunkOne();
                break;
        }
    }

    private Queue<Spawn> chunkOne(){
        Queue<Spawn> sp = new LinkedList<Spawn>();


        sp.add(new Spawn(this, 50));
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
        System.out.println("DEBUG: Size: " + sp.size());
        return sp;

    }

    private Queue<Spawn> chunkTwo(){
        Queue<Spawn> sp = new LinkedList<Spawn>();

        sp.add(new Spawn(this, 100));
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

    private Queue<Spawn> chunkThird(){
        Queue<Spawn> sp = new LinkedList<Spawn>();

        sp.add(new Spawn(this, 100));
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

    private Queue<Spawn> chunkFour(){
        Queue<Spawn> sp = new LinkedList<Spawn>();


        sp.add(new Spawn(this, 50));
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

    private int randomNrRange(int min, int max){
        return min + (int)(Math.random() * ((max - min) + 1));
    }

    public void start(){
        thread.start();
    }

    public void setPath(){
        path = new ArrayList<RectF>();
        float x = mStartX, y = mStartY, yVelo = 0f, xVelo = 0.50f;

        for (int i = 0; i <= 120; ++i) {
            x = x+2;
            path.add(new RectF(x, y, x+30, y+30));
        }
        for (int i = 0; i <= 100; ++i) {
            y = y-3f+yVelo;
            x = x + xVelo;
            yVelo += (3f/100);
            path.add(new RectF(x, y, x+30, y+30));
        }

        for (int i = 0; i <= 235; ++i) {
            y = y+3f-yVelo;
            x = x + xVelo;
            yVelo -= (3f/235f);
            path.add(new RectF(x, y, x+30, y+30));
        }
        firstDeath = this.path.size()-1;
        yVelo = 0;
        for (int i = 0; i <= 235; ++i) {
            y = y-3f+yVelo;
            x = x + xVelo;
            yVelo += (3f/235f);
            path.add(new RectF(x, y, x+30, y+30));
        }
        for (int i = 0; i <= 235; ++i) {
            y = y+3f-yVelo;
            x = x + xVelo;
            yVelo -= (3f/235f);
            path.add(new RectF(x, y, x+30, y+30));
        }
        secondDeath = this.path.size()-1;
        yVelo = 0;
        for (int i = 0; i <= 235; ++i) {
            y = y-3f+yVelo;
            x = x + xVelo;
            yVelo += (3f/235f);
            path.add(new RectF(x, y, x+30, y+30));
        }
        for (int i = 0; i <= 235; ++i) {
            y = y+3f-yVelo;
            x = x + xVelo;
            yVelo -= (3f/235f);
            path.add(new RectF(x, y, x+30, y+30));
        }
        thirdDeath = this.path.size()-1;
        yVelo = 0;
        for (int i = 0; i <= 235; ++i) {
            y = y-3f+yVelo;
            x = x + xVelo;
            yVelo += (3f/235f);
            path.add(new RectF(x, y, x+30, y+30));
        }
        for (int i = 0; i <= 100; ++i) {
            y = y+3f-yVelo;
            x = x + xVelo;
            yVelo -= (3f/100f);
            path.add(new RectF(x, y, x+30, y+30));
        }
        finishPoint = this.path.size()-1;
        for (int i = 0; i <= 120; ++i) {
            x = x+2;
            path.add(new RectF(x, y, x+30, y+30));
        }
        
        deathPoints = new ArrayList<RectF>();
        RectF first = this.getWaypoint(firstDeath);
        RectF firstDeathRect = new RectF();
        firstDeathRect.set(first.left, first.top+50, first.right, first.bottom);
        deathPoints.add(firstDeathRect);

        RectF second = this.getWaypoint(secondDeath);
        RectF secondDeathRect = new RectF();
        secondDeathRect.set(second.left, second.top+50, second.right, second.bottom);
        deathPoints.add(secondDeathRect);

        RectF third = this.getWaypoint(thirdDeath);
        RectF thirdDeathRect = new RectF();
        thirdDeathRect.set(third.left, third.top+50, third.right, third.bottom);
        deathPoints.add(thirdDeathRect);
        System.out.println("DEBUG: First: " + firstDeath +". Second: " + secondDeath +". Third: "+ thirdDeath);
    }

    public void setBoardXPos(float mBoardXPos) {
        if (mBoardXPos-mBoardWidth / 2 <= mBoardMinXPos)
        {
            this.mBoardXPos = mBoardMinXPos;
        }
        else if(mBoardXPos >= mBoardMaxXPos-mBoardWidth / 2)
        {
            this.mBoardXPos = mBoardMaxXPos-mBoardWidth;
        }
        else
        {
            this.mBoardXPos = mBoardXPos - mBoardWidth / 2;
        }
    }

    public void setBoardYPos(float mBoardYPos) {
        //Game.mBoardYPos = mBoardYPos;
    }

    public int getScore() {
        return score;
    }

    public float getBoardXPos() {
        return this.mBoardXPos;
    }

    public float getBoardYPos() {
        return this.mBoardYPos;
    }

    public float getBoardWidthPos(){
        return getBoardXPos() + mBoardWidth;
    }

    public float getBoardHeightPos(){
        return getBoardYPos() + mBoardHeight;
    }

    public boolean getPaused(){
        return mPaused;
    }

    public void setPaused(boolean paused) {
        mPaused = paused;
    }

    public RectF getWaypoint(int i){
        return path.get(i);
    }

    public int pathSize(){
        return path.size();
    }

    public RectF getDeathPoint(int i){
        return this.deathPoints.get(i);
    }

    public Queue<Bouncy> getActiveBouncies(){
        return this.activeBouncies;
    }

    public int getLivesLeft(){
        return this.livesLeft;
    }

    @Override
    public void run() {
        while (true){
            synchronized (activeBouncies) {
                for (Bouncy b : activeBouncies) {
                    b.move();
                }
            }
            ++ticks;
            if (spawnPool.size() > 0) {
                if (ticks == spawnPool.peek().get_ticks()) {
                    synchronized (activeBouncies) {
                        activeBouncies.add(spawnPool.poll().getBouncy());
                    }
                    ticks = 0;
                }
            }else if(activeBouncies.size() == 0){
                if (++this.passedChunks >= this.requiredPassedChunks)
                    ++this.difficulty;
                fillSpawnPool();
            }
            try {
                thread.sleep(6);
            }catch(Exception e){
                System.out.println("DEBUG: Exception Sleep: " + e);
            }
        }
    }

    public boolean checkCollision(int pathIndex) {
        int bounceXLeft = (int) this.path.get(pathIndex).left;
        int bounceXRight = (int) this.path.get(pathIndex).right;
        return (bounceXLeft >= getBoardXPos() && bounceXLeft <= getBoardWidthPos() ||
                (bounceXRight >= getBoardXPos() && bounceXRight <= getBoardWidthPos()));
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

    public void lifeLost(){
        if(--this.livesLeft <= 0){
            System.out.println("DEBUG: DU FÖRLORADE!!!");
        }
    }
}
