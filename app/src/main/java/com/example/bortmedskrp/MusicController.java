package com.example.bortmedskrp;

import android.content.Context;
import android.media.MediaPlayer;
import android.util.Log;

import static android.content.ContentValues.TAG;

/**
 * Created by  on 2021-10-25.
 */

class MusicController {

    Context context;
    MediaPlayer backgroundMusicGame;
    MediaPlayer soundItem;
    MediaPlayer soundApplause;
    int backgroundCurrentPosition;

    public MusicController(Context context){
        this.context = context;
        backgroundCurrentPosition = 0;
    }

    public void startBackgroundOcean(){
        if (backgroundMusicGame == null || !backgroundMusicGame.isPlaying()){
            backgroundMusicGame = MediaPlayer.create(context, R.raw.backgrund_music_buddy);
        }

        backgroundMusicGame.setLooping(true);
        backgroundMusicGame.seekTo(backgroundCurrentPosition);
        backgroundMusicGame.start();
    }

    public void stopBackgroundOcean(){
        backgroundCurrentPosition = backgroundMusicGame.getCurrentPosition();
        backgroundMusicGame.stop();
    }

    public void startFinishApplause(){
        soundApplause = MediaPlayer.create(context, R.raw.end_applause);
        soundApplause.setOnCompletionListener(mp -> soundApplause.stop());
        soundApplause.start();
    }

    public void stopApplause(){
        soundApplause.stop();
    }

    public void soundItemClick(Item item){
        if (soundItem != null && soundItem.isPlaying()){
            soundItem.release();
        }
        if(item.getIsTrash()){
            soundItem = MediaPlayer.create(context, R.raw.item_trash_onclick_pling);
        }else{
            soundItem = MediaPlayer.create(context, R.raw.item_animal_onclick_plop);
        }

        soundItem.start();
    }

}
