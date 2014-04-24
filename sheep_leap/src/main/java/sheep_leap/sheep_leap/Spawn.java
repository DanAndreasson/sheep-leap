package sheep_leap.sheep_leap;


public class Spawn {
    private int ticks_until_spawn;
    private Bouncy bouncy;
    private Game game;

    public Spawn(Game g, int t){
        this.ticks_until_spawn = t;
        this.game = g;
        this.bouncy = new Bouncy(this.game);
    }

    public Bouncy getBouncy(){
        return this.bouncy;
    }

    public int get_ticks(){
        return ticks_until_spawn;
    }
}
