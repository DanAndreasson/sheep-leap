package kycklingstuds.kycklingstuds;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;

public class SoundManager {


    private SoundPool mSoundPool;
    private boolean mSoundLoaded = true;
    private float mVolume = 0.99f;
    static int BACKGROUND_MUSIC;
    static int BOUNCE_SOUND;
    static int BAA_SOUND;
    static int SPLASH_SOUND;
    static int GAMEOVER_SOUND;

    static int BACKGROUND_MUSIC_STREAMID;

    static float BACKGROUND_MUSIC_VOLUME = 0.50f;
    public SoundManager(Context mContext) {


        // Load the sound
        mSoundPool = new SoundPool(10, AudioManager.STREAM_MUSIC, 0);
        mSoundPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
            @Override
            public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {

                    mSoundLoaded = true;

            }
        });

        BACKGROUND_MUSIC = mSoundPool.load(mContext, R.raw.backgroundmusic, 1);
        BOUNCE_SOUND = mSoundPool.load(mContext, R.raw.bouncesound, 1);
        BAA_SOUND = mSoundPool.load(mContext, R.raw.sheepbaa, 1);
        SPLASH_SOUND = mSoundPool.load(mContext, R.raw.splash, 1);
        GAMEOVER_SOUND = mSoundPool.load(mContext, R.raw.gameover, 1);
        // Getting the user sound settings
       /* AudioManager audioManager = (AudioManager)mContext.getSystemService(Context.AUDIO_SERVICE);
        float actualVolume = (float) audioManager
                .getStreamVolume(AudioManager.STREAM_MUSIC);
        float maxVolume = (float) audioManager
                .getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        mVolume = actualVolume / maxVolume;*/
    }



    public void playBackgroundMusic(int soundId) {
        if(mSoundLoaded) {
            BACKGROUND_MUSIC_STREAMID = mSoundPool.play(soundId, BACKGROUND_MUSIC_VOLUME, BACKGROUND_MUSIC_VOLUME, 1, -1, 1f);
        }
    }

    public void playSound(int soundId) {
        if(mSoundLoaded) {
            mSoundPool.play(soundId, mVolume, mVolume, 2, 0, 1f);
        }
    }

    public void stopSound() {
        if(mSoundLoaded) {
            mSoundPool.stop(BACKGROUND_MUSIC_STREAMID);
        }
    }

}
    /*
    public static int SOUNDPOOLSND_BACKGROUNDMUSIC  = 0;

    public static boolean isSoundTurnedOff;

    private static SoundManager mSoundManager;
    boolean loaded = false;
    private SoundPool mSoundPool;
    private SparseArray<Integer> mSoundPoolMap;
    private AudioManager mAudioManager;

    public static final int maxSounds = 4;

    public static SoundManager getInstance(Context context)
    {
        if (mSoundManager == null){
            mSoundManager = new SoundManager(context);
        }

        return mSoundManager;
    }

    public SoundManager(Context mContext)
    {
        mAudioManager = (AudioManager)mContext.getSystemService(Context.AUDIO_SERVICE);
        mSoundPool = new SoundPool(maxSounds, AudioManager.STREAM_MUSIC, 0);

        mSoundPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
            public void onLoadComplete(SoundPool soundPool, int sampleId,int status) {
               loaded = true;
            }
        });

        mSoundPoolMap = new SparseArray<Integer>();
        mSoundPoolMap.put(SOUNDPOOLSND_BACKGROUNDMUSIC, mSoundPool.load(mContext, R.raw.backgroundmusic, 1));




    }

    public void playSound(int index) {
        if (isSoundTurnedOff)
            return;

        int streamVolume = mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        if(loaded) {
            mSoundPool.play(mSoundPoolMap.get(index), streamVolume, streamVolume, 1, 1, 1f);
            System.out.println("DEBUG: Trying to play backgroundmusic");
        }
    }

    public static void clear()
    {
        if (mSoundManager != null){
            mSoundManager.mSoundPool = null;
            mSoundManager.mAudioManager = null;
            mSoundManager.mSoundPoolMap = null;
        }
        mSoundManager = null;
    }
}*/