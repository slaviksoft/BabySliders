package com.slaviksoft.games.babysliders;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

/**
 * Created by Slavik on 25.07.2015.
 */
public class appPreferences {

    private static String PREFS_NAME = "options1";
    private static String PREFS_SOUND = "soundVolume1";
    private static String PREFS_LEVEL = "level";


    static public float getSoundVolume() {
        Preferences pref = Gdx.app.getPreferences(PREFS_NAME);
        float val = pref.getInteger(PREFS_SOUND, 70);
        Gdx.app.log("PREF", "val="+val);
        return val;
    }

    static public void setSoundVolume(float soundVolume) {
        Preferences pref = Gdx.app.getPreferences(PREFS_NAME);
        pref.putInteger(PREFS_SOUND, (int) soundVolume);
        pref.flush();
    }

    static public float getSoundVolumeValue() {
        return getSoundVolume()/100;
    }

    static public String getSoundVolumeFormatted() {
        return String.format("%d", (int)getSoundVolume());
    }

    static public int getBugsLevel(){
        Preferences pref = Gdx.app.getPreferences(PREFS_NAME);
        int val = pref.getInteger(PREFS_LEVEL, 1);
        Gdx.app.log("PREF LEVEL", "val="+val);
        return val;
    }

    static public void setBugsLevel(int level){
        Preferences pref = Gdx.app.getPreferences(PREFS_NAME);
        pref.putInteger(PREFS_LEVEL, level);
        pref.flush();
    }

}

