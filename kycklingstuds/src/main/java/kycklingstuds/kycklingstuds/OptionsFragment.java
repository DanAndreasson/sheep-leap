package kycklingstuds.kycklingstuds;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;

public class OptionsFragment extends  Fragment {
        @Override
        public View onCreateView(LayoutInflater inflater,
                                 ViewGroup container, Bundle savedInstanceState) {
            View v = inflater.inflate(R.layout.options_fragment, container, false);

            SeekBar backgroundMusicVolume = (SeekBar) v.findViewById(R.id.bg_volume);
            backgroundMusicVolume.setProgress((int) SoundManager.BACKGROUND_MUSIC_VOLUME * 100);
            final TextView backgroundMusicText = (TextView) v.findViewById(R.id.bg_volume_value);

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

            return v;
        }

}
