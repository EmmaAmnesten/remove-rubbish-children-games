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

    MusicController musicController;
    AnimationsImage animationsImage;
    ItemsType itemsType;

    ArrayList<Item> itemsInGame;
    ArrayList<Star> starList;

    ConstraintLayout constraintLayout;
    ImageView fishView;
    ImageView countDownView;
    ProgressBar progressBarPoints;

    Handler handlerGame;
    Runnable runnableGame;

    int points;
    int displayHeight;
    int displayWidth;
    int fishWidthHeight;
    int itemWidthHeight;

    Boolean ifCountdownDone;
    Boolean ifGameFinish;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save_the_ocean);

        goalPoints = Integer.parseInt(getIntent().getStringExtra("EXTRA_TEXT_LEVEL"));

        constraintLayout = findViewById(R.id.constraintLayout);
        countDownView = findViewById(R.id.downCounterView);
        fishView = findViewById(R.id.fishView);
        progressBarPoints = findViewById(R.id.progressBarPoints);

        musicController = new MusicController(this);
        animationsImage = new AnimationsImage(fishView);
        itemsType = new ItemsType();
        itemsInGame = new ArrayList<>();
        starList = new ArrayList<>();

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        displayHeight = displayMetrics.heightPixels;
        displayWidth = displayMetrics.widthPixels;

        points = 0;
        ifCountdownDone = false;
        ifGameFinish = false;
        itemsInGame.clear();

        setUpStartScreen();
    }

    /**
     * Sets up a start view .
     */
    private void setUpStartScreen(){
        fishWidthHeight = displayWidth/displayDividerFishWidth;
        itemWidthHeight = displayWidth/displayDividerItemWidth;
        animationsImage.setStartFish();
        fishView.getLayoutParams().width = fishWidthHeight;
        fishView.getLayoutParams().height = fishWidthHeight;

        progressBarPoints.setMax(goalPoints);
        progressBarPoints.setProgress(0);
        progressBarPoints.setScaleY(4f);

        musicController.startBackgroundOcean();
        countDownStartGame();
    }

    /**
     * Start a animation and then the game starts.
     * When animations is done method randomSpanTimeItem starts from class AnimationsImage.
     */
    private void countDownStartGame(){
        animationsImage.countDownAnimation(this, countDownView);
    }

    /**
     * This method starts when animation countDownStartGame is finish. Starts from class AnimationsImage.
     * Runnable with random delay to add new Item.
     */
    public void randomSpanTimeItem() {
        handlerGame = new Handler();
        runnableGame = new Runnable() {
            @Override
            public void run() {
                addNewItem();
                handlerGame.postDelayed(this, randomInt());
            }
        };
        handlerGame.post(runnableGame);
    }

    /**
     * Adds new Item to game.
     */
    private void addNewItem(){
        Item item = new Item(this, itemsType, constraintLayout, displayHeight, displayWidth, itemWidthHeight);
        itemsInGame.add(item);
        item.setOnClickListener(v -> itemClicked(item));
        item.moveItemDown();
    }

    /**
     * When Item is clicked:
     * If rubbish points +1, if animal points -1.
     * Music and fish animation.
     * Progressbar is update.
     * Item removes from game.
     *
     * @param item can be rubbish or not (Boolean).
     */
    private void itemClicked(Item item){
        animationsImage.fishEyeUp();
        musicController.soundItemClick(item);

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
        animationsImage.finishRainbow(this, displayWidth);
        musicController.startFinishApplause();
        TextView musicCredit = findViewById(R.id.textviewCredit);
        musicCredit.setVisibility(View.VISIBLE);
        for(int i = 0; i < 50; i++){
            addStar();
        }

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

        if(starList.size() < 1){
            goToMainActivity();
        }
    }

    private int randomInt(){
        return new Random().nextInt(maxSpanDelayItem - minSpanDelayItem) + minSpanDelayItem;
    }

    private void goToMainActivity(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onPause(){
        super.onPause();

        Log.d(TAG, "onPause: " + ifCountdownDone);
        Log.d(TAG, "onPause: " + animationsImage.customAnimation);

        animationsImage.stopCountDownAnimation();

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

        Log.d(TAG, "onResume: " + ifCountdownDone);

        //Adds movement on Item in arraylist itemsInGame.
        for (Item i : itemsInGame) {
            i.moveItemDown();
        }

        //If game not over, add Item from array itemInGame to game.
        //
        //Check if game have had countdown.
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
    }

}