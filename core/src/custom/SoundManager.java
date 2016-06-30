package custom;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.slaviksoft.games.babysliders.appPreferences;

/**
 * Created by Slavik on 22.07.2015.
 */
public class SoundManager {

    private boolean isPlaying;

    private Sound sound1;
    private Music music1;

    public SoundManager(){
        sound1 = Gdx.audio.newSound(Gdx.files.internal("sound/beetle.mp3"));
        music1 = Gdx.audio.newMusic(Gdx.files.internal("sound/background.mp3"));
        music1.setLooping(true);
        music1.setVolume(appPreferences.getSoundVolumeValue());
    }

    public void play(){
        if (isPlaying) return;
        sound1.loop(appPreferences.getSoundVolumeValue());
        isPlaying = true;
        Gdx.app.log(SoundManager.class.getSimpleName(), "volume value="+appPreferences.getSoundVolumeValue());
    }

    public void stop(){
        sound1.stop();
        isPlaying = false;
    }


    public void playMusic(){
        music1.play();
    }

    public void stopMusic(){
        music1.stop();
    }


}
