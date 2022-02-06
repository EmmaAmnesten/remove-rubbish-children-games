package com.example.bortmedskrp;

import android.content.Context;
import android.media.MediaPlayer;
import android.provider.MediaStore;
import android.util.Log;

import static android.content.ContentValues.TAG;

/**
 * Created by  on 2021-10-25.
 */

class MusicController {

    Context context;
    MediaPlayer backgroundMusicGame;
    MediaPlayer soundAnimal;
    MediaPlayer soundTrash;
    MediaPlayer soundItem;
    MediaPlayer soundApplause;

    public MusicController(Context context){
        this.context = context;
    }

    public void startBackgroundOcean(){
        if (backgroundMusicGame == null){
            backgroundMusicGame = MediaPlayer.create(context, R.raw.bensoundbuddy);
        }
        backgroundMusicGame.setLooping(true);
        backgroundMusicGame.start();
    }

    public void stopBackgroundOcean(){
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
