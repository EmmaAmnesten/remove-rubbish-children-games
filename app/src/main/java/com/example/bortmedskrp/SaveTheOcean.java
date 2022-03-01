package com.example.bortmedskrp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;


public class SaveTheOcean extends AppCompatActivity {

    final static int startMinSpanDelayItem = 500;
    final static int startMaxSpanDelayItem = 1500;
    final static int displayDividerFishWidth = 3;
    final static int displayDividerItemWidth = 6;
    final static int displayDividerLifeWidth = 9;

    static int gameNumber;
    static int goalPoints;

    MusicController musicController;
    AnimationsImage animationsImage;
    ItemsType itemsType;
    ItemsType.gameType gameType;

    ArrayList<Item> itemsInGameList;
    ArrayList<Star> starList;
    ArrayList<ImageView> lifeImageList;

    ConstraintLayout constraintLayout;
    TextView textViewCounterType;
    TextView textViewNumCounter;
    ImageView fishView;
    ImageView countDownView;
    ProgressBar progressBarPoints;
    LinearLayout lifeContainer;

    Handler handlerGame;
    //Handler handlerCounter;
    Runnable runnableGame;
    //Runnable runnableCounter;

    //int counterCountDown;
    int gamePoints;
    int gameLevel;
    int displayHeight;
    int displayWidth;
    int fishWidthHeight;
    int itemWidthHeight;
    int lifeWidthHeight;

    boolean ifGameFinish;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save_the_ocean);

        gameNumber = Integer.parseInt(getIntent().getStringExtra("EXTRA_TEXT_LEVEL"));

        constraintLayout = findViewById(R.id.constraintLayout);
        textViewCounterType = findViewById(R.id.textViewCounterType);
        textViewNumCounter = findViewById(R.id.textViewNumCounter);
        countDownView = findViewById(R.id.downCounterView);
        fishView = findViewById(R.id.fishView);
        progressBarPoints = findViewById(R.id.progressBarPoints);
        lifeContainer = findViewById(R.id.lifeContainer);


        musicController = new MusicController(this);
        animationsImage = new AnimationsImage(fishView);
        itemsType = new ItemsType();
        itemsInGameList = new ArrayList<>();
        starList = new ArrayList<>();
        lifeImageList = new ArrayList<>();

        gameType = itemsType.getGameTypeByPosition(gameNumber);


        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        displayHeight = displayMetrics.heightPixels;
        displayWidth = displayMetrics.widthPixels;

        gamePoints = 0;
        gameLevel = 1;
        ifGameFinish = false;
        itemsInGameList.clear();

        setUpStartScreen();
    }

    /**
     * Sets up a start view .
     */
    private void setUpStartScreen(){
        fishWidthHeight = displayWidth/displayDividerFishWidth;
        itemWidthHeight = displayWidth/displayDividerItemWidth;
        lifeWidthHeight = displayWidth/displayDividerLifeWidth;


        if(gameNumber == 0){
            textViewCounterType.setVisibility(View.INVISIBLE);
            textViewNumCounter.setVisibility(View.INVISIBLE);
            goalPoints = gameType.gameGoalNumValue;
            progressBarPoints.setMax(goalPoints);
        }else if(gameNumber == 1){
            textViewCounterType.setText(R.string.text_level);
            textViewNumCounter.setText(String.valueOf(gameLevel));
            //level starts at lev 1. 5 points between lev 1 and lev 2. then 10 points for all next coming levels.
            goalPoints = 5 + ((gameType.gameGoalNumValue - 2) * 10);
            progressBarPoints.setMax(goalPoints);
        }else{
            textViewCounterType.setText(R.string.text_points);
            textViewNumCounter.setText(String.valueOf(gamePoints));
            progressBarPoints.setVisibility(View.INVISIBLE);
            for(int i = 0; i < gameType.gameGoalNumValue; i++){
                ImageView ivLife = new ImageView(this);
                ivLife.setImageResource(R.drawable.item_animal_fish_2);
                lifeImageList.add(ivLife);
            }
            upDateLife();
        }

        if(gameNumber != 2) {
            progressBarPoints.setProgress(0);
            progressBarPoints.setScaleY(4f);
        }

        animationsImage.setStartFish();
        fishView.getLayoutParams().width = fishWidthHeight;
        fishView.getLayoutParams().height = fishWidthHeight;

        musicController.startBackgroundOcean();
        //animationImage.countDownStartGame();
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
                handlerGame.postDelayed(this, randomIntSpanTime());
            }
        };
        handlerGame.post(runnableGame);
    }

    /**
     * Adds new Item to game.
     */
    private void addNewItem(){
        Item item = new Item(this, itemsType, constraintLayout, displayHeight, displayWidth,
                itemWidthHeight, gameLevel, gameNumber);
        itemsInGameList.add(item);
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
            gamePoints = gamePoints + 1;
        }

        if((gameNumber == 0 || gameNumber == 1) && !ifGameFinish){
            if(!item.getIsTrash() ){
                gamePoints = gamePoints - 1;
            }

            if(gamePoints > 0){
                progressBarPoints.setProgress(gamePoints);
            }

            if(gameNumber == 1){
                if (gamePoints == 5 || gamePoints % 10 == 0) {
                    if (gamePoints == 5) {
                        gameLevel = 2;
                    } else {
                        gameLevel = (gamePoints / 10) + 1;
                    }
                    textViewNumCounter.setText(String.valueOf(gameLevel));
                }
            }

            if(gamePoints == goalPoints){
                ifGameFinish = true;
                gameOver();
            }
        }else if(gameNumber == 2 && !ifGameFinish){
            textViewNumCounter.setText(String.valueOf(gamePoints));

            if(!item.getIsTrash() ){
                removeLife();
            }

            if(lifeImageList.isEmpty()){
                ifGameFinish = true;
                gameOver();
            }
        }
        removeItemFromGame(item);
    }

    public void removeItemFromGame(Item item){
        constraintLayout.removeView(item);
        itemsInGameList.remove(item);
        item.removeHandlerItem();
    }

    public void removeLife(){
        if(!lifeImageList.isEmpty()) {
            lifeImageList.remove(0);
            upDateLife();
        }
    }

    public void upDateLife(){
        lifeContainer.removeAllViews();
        for(ImageView i : lifeImageList) {
            lifeContainer.addView(i);
            i.getLayoutParams().width = lifeWidthHeight;
            i.getLayoutParams().height = lifeWidthHeight;
        }
        if(lifeImageList.isEmpty()){
            gameOver();
        }
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

    private int randomIntSpanTime(){
        int time = new Random().nextInt(startMaxSpanDelayItem - startMinSpanDelayItem) + startMinSpanDelayItem;
        time = time - (gameLevel * 4);
        if(time < 100){
            time = 100;
        }
        return time;
    }

    // TODO: 2022-03-01 fix bug on this methode. 
    private void goToMainActivity(){
        super.onBackPressed();
    }

    @Override
    protected void onPause(){
        super.onPause();

        if(handlerGame != null){
            handlerGame.removeCallbacks(runnableGame);
        }
        if(animationsImage.handlerCounter != null){
            animationsImage.handlerCounter.removeCallbacks(animationsImage.runnableCounter);
        }

        for (Item i : itemsInGameList){
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
        for (Item i : itemsInGameList) {
            i.moveItemDown();
        }

        animationsImage.countDownStartGame(this, countDownView);

        musicController.startBackgroundOcean();
    }

}