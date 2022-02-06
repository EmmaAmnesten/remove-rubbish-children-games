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

    public void startAnimalClickSound(){
        MediaPlayer soundAnimal = MediaPlayer.create(context, R.raw.item_animal_onclick_plop);
        soundAnimal.setOnCompletionListener(mp -> soundAnimal.stop());
        soundAnimal.start();
    }

    public void startTrashClickSound(){
        MediaPlayer soundTrash = MediaPlayer.create(context, R.raw.item_trash_onclick_pling);
        soundTrash.setOnCompletionListener(mp -> soundTrash.stop());
        soundTrash.start();
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
        if(item.getIsTrash()) {
            startTrashClickSound();
        }else{
            startAnimalClickSound();
        }
    }

}
