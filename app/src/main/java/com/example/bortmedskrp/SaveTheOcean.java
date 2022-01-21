package com.example.bortmedskrp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

import static android.content.ContentValues.TAG;

public class SaveTheOcean extends AppCompatActivity {

    final static int minSpanDelayItem = 500;
    final static int maxSpanDelayItem = 2000;
    final static int displayDividerFishWidth = 3;
    final static int displayDividerItemWidth = 6;
    static int goalPoints = 5;


    ConstraintLayout constraintLayout;
    ImageView fishView;
    ImageView countDownView;

    ProgressBar progressBarPoints;
    int points;

    int displayHeight;
    int displayWidth;
    int fishWidthHeight;
    int itemWidthHeight;

    Handler handlerGame;
    Runnable runnableGame;

    MusicController musicController;
    AnimationsImage animationsImage;
    ItemsType itemsType;
    ArrayList<Item> itemsInGame;
    ArrayList<Star> starList;

    Boolean ifCountdownDone;
    Boolean ifGameFinish;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save_the_ocean);

        goalPoints = Integer.parseInt(getIntent().getStringExtra("EXTRA_TEXT"));

        constraintLayout = findViewById(R.id.constraintLayout);
        countDownView = findViewById(R.id.downCounterView);
        fishView = findViewById(R.id.fishView);
        progressBarPoints = findViewById(R.id.progressBarPoints);
        points = 0;
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        displayHeight = displayMetrics.heightPixels;
        displayWidth = displayMetrics.widthPixels;
        fishWidthHeight = displayWidth/displayDividerFishWidth;
        itemWidthHeight = displayWidth/displayDividerItemWidth;



        Log.d(TAG, "onCreate: brädd " + displayWidth);
        Log.d(TAG, "onCreate: brädd item " + displayWidth/displayDividerItemWidth);
        Log.d(TAG, "onCreate: density " + getResources().getDisplayMetrics().density);
        Log.d(TAG, "onCreate: densitydpi " + getResources().getDisplayMetrics().densityDpi);

        musicController = new MusicController(this);
        animationsImage = new AnimationsImage(fishView);
        itemsType = new ItemsType();
        itemsInGame = new ArrayList<>();
        starList = new ArrayList<>();

        ifCountdownDone = false;
        ifGameFinish = false;

        Log.d(TAG, "onCreate: " + ifCountdownDone);

        setUpStartScreen();
    }

    /**
     * Sätter upp en start vy.
     */
    private void setUpStartScreen(){
        animationsImage.setStartFish();
        fishView.getLayoutParams().width = fishWidthHeight;
        fishView.getLayoutParams().height = fishWidthHeight;
        progressBarPoints.setMax(goalPoints);
        progressBarPoints.setProgress(0);
        progressBarPoints.setScaleY(4f);

        musicController.startBackgroundOcean();
        countDownStartGame();
        itemsInGame.clear();
    }

    /**
     * Startar spelet med att räkna ner med en animaton. och sen startar spelet.
     * Metoden randomSpanTimeItem startas från klassen AnimationsImage när animationen är slut.
     */
    private void countDownStartGame(){
        animationsImage.countDownStartGame(this, countDownView);
    }

    /**
     * Körs när animation contDownStartGame är klar. Startas från AnimationsImage klass
     * Olika lång tid mellan att det läggs till nytt objekt till spelet.
     * Slutar när spelet är slut.
     */
    public void randomSpanTimeItem() {
        handlerGame = new Handler();
        runnableGame = new Runnable() {
            @Override
            public void run() {
                addNewItem();
                handlerGame.postDelayed(this, randomInt(minSpanDelayItem, maxSpanDelayItem));
            }
        };
        handlerGame.post(runnableGame);
    }

    /**
     * Lägger till nya objekt till spelet.
     */
    private void addNewItem(){
        Item item = new Item(this, itemsType, constraintLayout, displayHeight, displayWidth, itemWidthHeight);
        itemsInGame.add(item);
        for (Item i : itemsInGame){
            Log.d(TAG, "addNewItem: " + i.getName());
        }
        item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemClicked(item);
            }
        });
        item.moveItemDown();
    }

    /**
     * När objektet är klickat händer:
     * Om skräp:
     *  -Räkna upp points +1
     *  -Progressbar indekerar antal poäng förhållande till mål poäng.
     *  -Objektet tas bort.
     * Om djur:
     *  -Animation
     *  -Räkna upp points -1
     *  -Progressbar indekerar antal poäng förhållande till mål poäng.
     * @param item kan vara skräp eller inte (Boolean).
     */
    private void itemClicked(Item item){
        animationsImage.fishEyeUp();
        if(item.isTrash) {
            musicController.startTrashClickSound();
        }else{
            musicController.startAnimalClickSound();
        }
        if(item.getIsTrash() && !ifGameFinish){
            points = points + 1;
            progressBarPoints.setProgress(points);
        }else if(!item.getIsTrash() && points > 0 && !ifGameFinish){
            points = points - 1;
            progressBarPoints.setProgress(points);
        }
        if(points == goalPoints && !ifGameFinish){
            ifGameFinish = true;
            gameOver();
        }
        removeItemFromGame(item);
    }

    public void removeItemFromGame(Item item){
        constraintLayout.removeView(item);
        itemsInGame.remove(item);
        item.removeHandlerItem();
    }

    private void gameOver(){
        Log.d(TAG, "gameOver: ");
        animationsImage.finishRainbow(this, displayWidth);
        musicController.startFinishApplause();
        TextView musicCredit = findViewById(R.id.textviewCredit);
        musicCredit.setVisibility(View.VISIBLE);
        addStar();

        if(handlerGame != null){
            handlerGame.removeCallbacks(runnableGame);
        }
    }

    private void addStar() {
        Star star = new Star(this, itemsType, constraintLayout, displayHeight, displayWidth);
        starList.add(star);
        star.moveStar();
    }

    public void removeStarFromGame(Star star){
        constraintLayout.removeView(star);
        starList.remove(star);
        star.removeHandlerStar();
    }

    private int randomInt(int minRandom, int maxRandom){
        int ranInt = new Random().nextInt(maxRandom - minRandom) + minRandom;
        return ranInt;
    }


    @Override
    protected void onPause(){
        super.onPause();
        Log.d(TAG, "onPause: ");
        //gameOver();

        if(handlerGame != null){
            handlerGame.removeCallbacks(runnableGame);
        }

        for (Item i : itemsInGame){
            i.removeHandlerItem();
        }

        if (musicController.musicOcean != null){
            musicController.stopBackgroundOcean();
            if (isFinishing()){
                musicController.stopBackgroundOcean();
                musicController.musicOcean.release();
            }
        }
        if (musicController.musicApplause != null){
            musicController.stopApplause();
            if (isFinishing()){
                musicController.stopApplause();
                musicController.musicApplause.release();
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        Log.d(TAG, "onResume: " + itemsInGame.size());

        //Sätter förflyttning på objekten som finns i arraylistan.
        for (Item i : itemsInGame) {
            i.moveItemDown();
        }

        //Om spelet inte är slut, ska mera objekt läggas till.
        //Kollar om spelet är startat, genoma att kolla om spelet har haft nedräkning när det startas första gången.
        if (!ifGameFinish){
            if (ifCountdownDone) {
                randomSpanTimeItem();
            }
        }

        if (musicController.musicOcean != null) {
            if (!musicController.musicOcean.isPlaying()) {
                musicController.startBackgroundOcean();
            }
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop: ");
    }
}