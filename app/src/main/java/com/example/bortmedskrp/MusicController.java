package com.example.bortmedskrp;

import android.content.Context;
import android.media.MediaPlayer;


/**
 * Created by  on 2021-10-25.
 */

class MusicController {

    Context context;
    MediaPlayer backgroundMusicGame;
    MediaPlayer soundApplause;
    int backgroundCurrentPosition;

    public MusicController(Context context){
        this.context = context;
        backgroundCurrentPosition = 0;
    }

    public void startBackgroundOcean(){
        if (backgroundMusicGame == null || !backgroundMusicGame.isPlaying()){
            backgroundMusicGame = MediaPlayer.create(context, R.raw.music_backgrund_game_buddy);
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
        soundApplause = MediaPlayer.create(context, R.raw.sound_end_applause);
        soundApplause.setOnCompletionListener(mp -> soundApplause.stop());
        soundApplause.start();
    }

    public void stopApplause(){
        soundApplause.stop();
    }

    public void soundItemClick(Item item){
        MediaPlayer soundItem;

        if(item.getIsTrash()){
            soundItem = MediaPlayer.create(context, R.raw.sound_item_trash_onclick_pling);
        }else{
            soundItem = MediaPlayer.create(context, R.raw.sound_item_animal_onclick_plop);
        }

        soundItem.setOnCompletionListener(mp -> soundItem.release());
        soundItem.start();
    }

    // TODO: 2022-03-01 sound effects life lost.

}
