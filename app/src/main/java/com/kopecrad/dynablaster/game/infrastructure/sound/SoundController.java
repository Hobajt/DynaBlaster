package com.kopecrad.dynablaster.game.infrastructure.sound;

import android.content.Context;
import android.media.MediaPlayer;

import com.kopecrad.dynablaster.R;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SoundController {

    public static boolean muted;

    private MediaPlayer bgPlayer;
    private Map<SoundType, MediaPlayer> players;

    public SoundController(Context context, SoundType ... sounds) {
        this(null, context, sounds);
    }

    public SoundController(SoundType bgSound, Context context, SoundType ... sounds) {
        if(bgSound != null) {
            setupBackgroundSound(context, bgSound);
        }
        players= new HashMap<>();
        for(SoundType type : sounds) {
            players.put(type, MediaPlayer.create(context, type.getResID()));
        }
    }

    public void playSound(SoundType type) {
        if(!players.containsKey(type))
            return;

        MediaPlayer pl= players.get(type);
        if(pl != null && !muted) {
            pl.start();
        }
    }

    private void setupBackgroundSound(Context context, SoundType bgSound) {
        bgPlayer = MediaPlayer.create(context, bgSound.getResID());
        bgPlayer.setLooping(true);
        if(!muted)
            bgPlayer.start();
    }

    public void release() {
        if(bgPlayer != null) {
            bgPlayer.release();
            bgPlayer= null;
        }

        for(MediaPlayer pl : players.values()) {
            pl.release();
            pl= null;
        }
    }

    public static void setMuted(boolean value) {
        muted= value;
    }

    public void mutedStateChange() {
        if(muted) {
            if(bgPlayer.isPlaying())
                bgPlayer.stop();
        }
        else {
            try {
                bgPlayer.prepare();
            } catch (IOException e) {
                e.printStackTrace();
            }
            bgPlayer.start();
        }
    }
}
