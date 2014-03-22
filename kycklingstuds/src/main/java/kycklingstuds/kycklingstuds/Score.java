package kycklingstuds.kycklingstuds;


public class Score {
    private long id;
    private int points;

    public Score(int p){
        this.points = p;
    }

    public long getID(){
        return this.id;
    }
    public void setID(long i){
        this.id = i;
    }

    public int getPoints(){
        return this.points;
    }


}
