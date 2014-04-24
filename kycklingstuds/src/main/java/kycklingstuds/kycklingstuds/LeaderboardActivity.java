package kycklingstuds.kycklingstuds;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

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
                new String[] { "name", "score" }, new int[] {
                R.id.name,R.id.score});
        list.setAdapter(adapter);

    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
