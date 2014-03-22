package kycklingstuds.kycklingstuds;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;

public class Bouncy {
    private int pathIndex;
    private Game game;
    private boolean dead;
    private int deathPoint;
    public Paint paintYellow = new Paint(Color.YELLOW);
    public Paint paint;

    private boolean finished;

    private int animationTick;
    private int animationState;

    public Bouncy(Game g) {
        pathIndex = 0;
        game = g;
        dead = false;
        finished = false;
        deathPoint = -1;

        paintYellow.setColor(Color.YELLOW);
        paint = paintYellow;

        animationTick = 0;
        animationState = 0;
    }

    public void move() {
        if (dead || finished) return;
        ++pathIndex;
        if (pathIndex == game.getFirstDeathPoint()) {
            if (!game.checkCollision(pathIndex)) {
                die(0);
            }
        }
        else if (pathIndex == game.getSecondDeathPoint()) {
            if (!game.checkCollision(pathIndex)) {
                die(1);
            }
        }
        if (pathIndex == game.getThirdDeathPoint()) {
            if (!game.checkCollision(pathIndex)) {
                die(2);
            }
        }

        if (++animationTick >= 15){
            animationState = (animationState+1)%4;
            animationTick = 0;
        }
    }

    public void die(int deathIndex) {
        deathPoint = deathIndex;
        dead = true;
        game.lifeLost();
    }

    public void finished() {
        game.receiveScore();
        finished = true;
    }

    public boolean isFinished() {
        return finished;
    }

    public RectF getPos() {
        if (dead) {
            return game.getDeathPoint(deathPoint);
        }
        if (game.pathSize() <= pathIndex) {
            finished();
            return game.getWaypoint(game.pathSize() - 1);
        }
        return game.getWaypoint(pathIndex);
    }

    public Bitmap getSprite() {

        switch (animationState) {
            case 0:
                return Resources.SHEEP_ONE;
            case 1:
                return Resources.SHEEP_TWO;
            case 2:
                return Resources.SHEEP_THREE;
            case 3:
                return Resources.SHEEP_FOUR;
            case 4:
                return Resources.SHEEP_FIVE;
            default:
                System.out.println("DEBUG: Bouncy animation error!");
                return Resources.SHEEP_ONE;
        }
    }
}
