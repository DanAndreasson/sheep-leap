package sheep_leap.sheep_leap;


import android.app.Activity;
import android.os.Bundle;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

//--------------------------------------------------------------------------Activity
public class LeaderboardActivity extends Activity {
    ListView list;
    ArrayList<HashMap<String, String>> newhighscoreList = new ArrayList<HashMap<String, String>>();

    private JSONArray highscores = new JSONArray();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboard);
        newhighscoreList = new ArrayList<HashMap<String, String>>();
        highscores = HttpAPI.getLeaderboard(50);
        System.out.println("DEBUG: IN LEADERBOARD " + highscores);
        fillList();


    }

    private void fillList(){
        try {
            for (int i = 0; i < highscores.length(); i++) {
                JSONObject c = highscores.getJSONObject(i);
                String name = c.getString("name");
                int score = c.getInt("score");
                HashMap<String, String> map = new HashMap<String, String>();
                map.put("name", name);
                map.put("score", Integer.toString(score));
                map.put("position", Integer.toString(i+1));
                newhighscoreList.add(map);
            }
        }
        catch (JSONException e){
            System.out.println("DEBUG: " + e);
        }
        System.out.println("DEBUG: HIGHSCORELIST " + newhighscoreList);

        list=(ListView)findViewById(R.id.leaderboard);
        ListAdapter adapter = new SimpleAdapter(LeaderboardActivity.this, newhighscoreList,
                R.layout.leaderboard,
                new String[] { "name", "score", "position" }, new int[] {
                R.id.name,R.id.score, R.id.position});
        list.setAdapter(adapter);

    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
