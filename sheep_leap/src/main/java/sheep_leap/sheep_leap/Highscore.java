package sheep_leap.sheep_leap;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class Highscore {

    // Database fields
    private SQLiteDatabase database;
    private HighScoreDbHelper dbHelper;
    private String[] allColumns = { HighScoreDbHelper.COLUMN_ID,
            HighScoreDbHelper.COLUMN_SCORE };

    private Score highest;

    public Highscore(Context context) {
        dbHelper = new HighScoreDbHelper(context);
        highest = null;
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public Score getHighscore(){
        if (this.highest == null){
            this.highest = getHighestHighscore();
            if (this.highest == null){
                this.highest = new Score(0);
            }
        }
        return this.highest;
    }

    private Score getHighestHighscore(){
        Cursor cursor = database.query(true, HighScoreDbHelper.TABLE_HIGHSCORES, allColumns,
                null, null, null, null, HighScoreDbHelper.COLUMN_SCORE +" DESC", null);
        cursor.moveToFirst();
        if (cursor.isAfterLast())
            return null;
        Score h = cursorToScore(cursor);
        cursor.close();
        return h;
    }

    public void newHighScore(){
        this.highest = null;
    }

    public Score createScore(int point) {
        ContentValues values = new ContentValues();
        values.put(HighScoreDbHelper.COLUMN_SCORE, point);
        long insertId = database.insert(HighScoreDbHelper.TABLE_HIGHSCORES, null,
                values);
        Cursor cursor = database.query(HighScoreDbHelper.TABLE_HIGHSCORES,
                allColumns, HighScoreDbHelper.COLUMN_ID + " = " + insertId, null,
                null, null, null);
        cursor.moveToFirst();
        Score newScore = cursorToScore(cursor);
        cursor.close();
        return newScore;
    }

    public void deleteScore(Score score) {
        long id = score.getID();
        database.delete(HighScoreDbHelper.TABLE_HIGHSCORES, HighScoreDbHelper.COLUMN_ID
                + " = " + id, null);
    }

    public List<Score> getAllScores() {
        List<Score> scores = new ArrayList<Score>();

        Cursor cursor = database.query(HighScoreDbHelper.TABLE_HIGHSCORES,
                allColumns, null, null, null, null, HighScoreDbHelper.COLUMN_SCORE + " DESC");

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Score score = cursorToScore(cursor);
            scores.add(score);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return scores;
    }

    private Score cursorToScore(Cursor cursor) {

        Score score = new Score(cursor.getInt(1));
        score.setID(cursor.getLong(0));
        return score;
    }
    private class HighScoreDbHelper extends SQLiteOpenHelper {
        public static final String TABLE_HIGHSCORES = "highscores";
        public static final String COLUMN_ID = "_id";
        public static final String COLUMN_SCORE = "score";

        private static final String DATABASE_NAME = "highscores.db";
        private static final int DATABASE_VERSION = 2;

        // Database creation sql statement
        private static final String DATABASE_CREATE = "create table "
                + TABLE_HIGHSCORES + "(" + COLUMN_ID
                + " integer primary key autoincrement, " + COLUMN_SCORE
                + " integer not null);";

        public HighScoreDbHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase database) {
            database.execSQL(DATABASE_CREATE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            System.out.println(HighScoreDbHelper.class.getName() +
                    ": Upgrading database from version " + oldVersion + " to "
                    + newVersion + ", which will destroy all old data");
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_HIGHSCORES);
            onCreate(db);
        }
    }
}

