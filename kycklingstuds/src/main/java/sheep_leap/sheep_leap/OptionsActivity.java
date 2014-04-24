package sheep_leap.sheep_leap;


import android.app.Activity;
import android.os.Bundle;
import android.widget.SeekBar;
import android.widget.TextView;

public class OptionsActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);
        System.out.println("DEBUG: ONCREATE OPTIONS");
        SeekBar backgroundMusicVolume = (SeekBar) findViewById(R.id.bg_volume);
        backgroundMusicVolume.setProgress((int) SoundManager.BACKGROUND_MUSIC_VOLUME * 100);
        final TextView backgroundMusicText = (TextView) findViewById(R.id.bg_volume_value);
        backgroundMusicVolume.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                SoundManager.BACKGROUND_MUSIC_VOLUME = (float) progress / 100;
                backgroundMusicText.setText(String.valueOf(progress));
                seekBar.setProgress(Math.round(SoundManager.BACKGROUND_MUSIC_VOLUME * 100));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });


        }
    @Override
    public void onBackPressed() {
        finish();
    }

}
