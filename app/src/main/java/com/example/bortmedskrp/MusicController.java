package com.example.bortmedskrp;

import android.media.MediaPlayer;
import android.util.Log;

import static android.content.ContentValues.TAG;

/**
 * Created by  on 2021-10-25.
 */

class MusicController {

    SaveTheOcean saveTheOcean;
    MediaPlayer backgroundMusicGame;
    MediaPlayer musicAnimal;
    MediaPlayer musicTrash;
    MediaPlayer musicApplause;

    public MusicController(SaveTheOcean saveTheOcean){
        Log.d(TAG, "MusicController: ++++++++++++++++++");
        this.saveTheOcean = saveTheOcean;
        backgroundMusicGame = MediaPlayer.create(saveTheOcean, R.raw.backgrund_music_bensound_smile);
        musicAnimal = MediaPlayer.create(saveTheOcean, R.raw.item_animal_oncklick_plop);
        musicTrash = MediaPlayer.create(saveTheOcean, R.raw.item_trash_onclick_bling);
        musicApplause = MediaPlayer.create(saveTheOcean, R.raw.end_applause);
    }

    public void startBackgroundOcean(){

        backgroundMusicGame.start();
    }

    public void stopBackgroundOcean(){
        backgroundMusicGame.stop();
        //backgroundMusicGame.release();
    }

    public void startAnimalClickSound(){

        musicAnimal.start();
    }

    public void startTrashClickSound(){

        Log.d(TAG, "startTrashClickSound: " + musicTrash);
        musicTrash.start();
    }

    public void startFinishApplause(){

        musicApplause.start();
    }

    public void stopApplause(){
        musicApplause.stop();
    }

    public void soundItemClick(Item item){
        if(item.getIsTrash()) {
            startTrashClickSound();
        }else{
            startAnimalClickSound();
        }
    }

}
