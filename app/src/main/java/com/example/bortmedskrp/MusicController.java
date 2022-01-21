package com.example.bortmedskrp;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.util.Log;

import static android.content.ContentValues.TAG;

/**
 * Created by  on 2021-10-25.
 */

class MusicController {

    SaveTheOcean saveTheOcean;
    MediaPlayer musicOcean;
    MediaPlayer musicApplause;

    public MusicController(SaveTheOcean saveTheOcean){
        this.saveTheOcean = saveTheOcean;

    }

    public void startBackgroundOcean(){
        Log.d(TAG, "startBackgroundOcean: " + musicOcean);
        musicOcean = MediaPlayer.create(saveTheOcean, R.raw.backgrund_music);
        musicOcean.start();
        Log.d(TAG, "startBackgroundOcean: ");
    }

    public void stopBackgroundOcean(){
        musicOcean.stop();
    }

    public void startAnimalClickSound(){
        MediaPlayer musicAnimal = MediaPlayer.create(saveTheOcean, R.raw.animal_plop);
        musicAnimal.start();
    }

    public void startTrashClickSound(){
        MediaPlayer musicTrash = MediaPlayer.create(saveTheOcean, R.raw.trash_bling);
        musicTrash.start();
    }

    public void startFinishApplause(){
        musicApplause = MediaPlayer.create(saveTheOcean, R.raw.applause);
        musicApplause.start();
    }

    public void stopApplause(){
        musicApplause.stop();
    }

}
