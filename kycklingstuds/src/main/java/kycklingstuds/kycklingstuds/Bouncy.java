package kycklingstuds.kycklingstuds;

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

    public Bouncy(Game g) {
        pathIndex = 0;
        game = g;
        dead = false;
        finished = false;
        deathPoint = -1;

        paintYellow.setColor(Color.YELLOW);
        paint = paintYellow;
    }

    public void move() {
        if (dead || finished) return;
        ++pathIndex;
      /*  if (pathIndex >= game.getFirstDeathPoint()-20 && pathIndex <= game.getFirstDeathPoint()) {

            if (game.checkCollision(game.getFirstDeathPoint())) {
                pathIndex += game.getFirstDeathPoint() - pathIndex;
                return ;
            }
            if (pathIndex == game.getFirstDeathPoint() ){
                die(0);
            }
        }*/
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
}
