package com.example.bortmedskrp;

import android.media.MediaPlayer;

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
        musicOcean = MediaPlayer.create(saveTheOcean, R.raw.backgrund_music_game);
        musicOcean.start();
    }

    public void stopBackgroundOcean(){
        musicOcean.stop();
    }

    public void startAnimalClickSound(){
        MediaPlayer musicAnimal = MediaPlayer.create(saveTheOcean, R.raw.item_animal_oncklick_plop);
        musicAnimal.start();
    }

    public void startTrashClickSound(){
        MediaPlayer musicTrash = MediaPlayer.create(saveTheOcean, R.raw.item_trash_onclick_bling);
        musicTrash.start();
    }

    public void startFinishApplause(){
        musicApplause = MediaPlayer.create(saveTheOcean, R.raw.end_applause);
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
