package com.example.bortmedskrp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;

import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;


public class SaveTheOcean extends AppCompatActivity {

    final static int minSpanDelayItem = 500;
    final static int maxSpanDelayItem = 2000;
    final static int displayDividerFishWidth = 3;
    final static int displayDividerItemWidth = 6;
    static int goalPoints;

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
    Handler handlerCounter;
    Runnable runnableGame;
    Runnable runnableCounter;

    int counterCountDown;
    int points;
    int displayHeight;
    int displayWidth;
    int fishWidthHeight;
    int itemWidthHeight;

    boolean ifGameFinish;


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

        counterCountDown = 3;
        points = 0;
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
        //countDownStartGame();
    }

    /**
     * Start a animation and then the game starts. Starts from onResume.
     * When animations is done method randomSpanTimeItem starts from class AnimationsImage.
     */
    public void countDownStartGame(){

        handlerCounter = new Handler();
        runnableCounter = new Runnable() {
            @Override
            public void run() {
                if(counterCountDown == 3){
                    countDownView.setImageResource(R.drawable.start_count_down_3);
                }else if(counterCountDown == 2){
                    countDownView.setImageResource(R.drawable.start_count_down_2);
                }else if(counterCountDown == 1){
                    countDownView.setImageResource(R.drawable.start_count_down_1);
                }else if(counterCountDown == 0){
                    countDownView.setImageResource(R.drawable.start_count_down_start);
                }else{
                    countDownView.setVisibility(View.INVISIBLE);
                    randomSpanTimeItem();
                    return;
                }
                counterCountDown --;
                handlerCounter.postDelayed(this, 1000);
            }
        };
        handlerCounter.post(runnableCounter);
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
        super.onBackPressed();
    }

    @Override
    protected void onPause(){
        super.onPause();

        if(handlerGame != null){
            handlerGame.removeCallbacks(runnableGame);
        }
        if(handlerCounter != null){
            handlerCounter.removeCallbacks(runnableCounter);
        }

        for (Item i : itemsInGame){
            i.removeHandlerItem();
        }

        if (musicController.backgroundMusicGame != null){
            musicController.stopBackgroundOcean();
            if (isFinishing()){
                musicController.stopBackgroundOcean();
                musicController.backgroundMusicGame.release();
                musicController.backgroundMusicGame = null;
            }
        }
        if (musicController.soundApplause != null){
            musicController.stopApplause();
            if (isFinishing()){
                musicController.stopApplause();
                musicController.soundApplause.release();
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        //Adds movement on Item in arraylist itemsInGame.
        for (Item i : itemsInGame) {
            i.moveItemDown();
        }

        countDownStartGame();

        musicController.startBackgroundOcean();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

}