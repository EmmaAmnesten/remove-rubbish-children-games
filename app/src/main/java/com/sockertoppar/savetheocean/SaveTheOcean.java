package com.sockertoppar.savetheocean;

import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.sockertoppar.savetheocean.R;

import java.util.ArrayList;
import java.util.Random;

/**
 * Save the ocean game.
 * Three types of games, variable gameNumber int 0,1 ore 2 is represents the game type.
 * Game types fetch from stringExtra from MainActivity.
 * (See game types descriptions in ItemTypes)
 */

public class SaveTheOcean extends AppCompatActivity {

    final static int minDelayTimeItem = 500;
    final static int maxDelayTimeItem = 1500;
    final static int displayDividerFishWidth = 3;
    final static int displayDividerItemWidth = 6;
    final static int displayDividerLifeWidth = 9;
    final static int minSpanTime = 350;
    final static int delaySecondsGoToMain = 6000;

    static int gameNumber;
    static int goalPoints;

    MusicController musicController;
    AnimationsImage animationsImage;
    GameTypes gameTypes;
    GameTypes.gameType gameType;
    ItemsType itemsType;

    ArrayList<Item> itemsInGameList;
    ArrayList<Star> starList;
    ArrayList<ImageView> lifeImageList;

    ConstraintLayout constraintLayout;
    TextView textViewCounterType;
    TextView textViewCounterNum;
    ImageView fishView;
    ImageView countDownView;
    ProgressBar progressBarPoints;
    LinearLayout lifeContainer;

    Handler handlerGame;
    Runnable runnableGame;

    int gamePoints;
    int gameLevel;
    int displayHeight;
    int displayWidth;
    int fishWidthHeight;
    int itemWidthHeight;
    int lifeWidthHeight;
    int spanTimeDecrease;

    boolean ifGameFinish;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save_the_ocean);

        gameNumber = Integer.parseInt(getIntent().getStringExtra("EXTRA_TEXT_LEVEL"));

        constraintLayout = findViewById(R.id.constraintLayout);
        textViewCounterType = findViewById(R.id.textViewCounterType);
        textViewCounterNum = findViewById(R.id.textViewNumCounter);
        countDownView = findViewById(R.id.downCounterView);
        fishView = findViewById(R.id.fishView);
        progressBarPoints = findViewById(R.id.progressBarPoints);
        lifeContainer = findViewById(R.id.lifeContainer);


        musicController = new MusicController(this);
        animationsImage = new AnimationsImage(fishView);
        gameTypes = new GameTypes();
        itemsType = new ItemsType();
        itemsInGameList = new ArrayList<>();
        starList = new ArrayList<>();
        lifeImageList = new ArrayList<>();

        gameType = gameTypes.getGameTypeByPosition(gameNumber);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        displayHeight = displayMetrics.heightPixels;
        displayWidth = displayMetrics.widthPixels;

        gamePoints = 0;
        gameLevel = 1;
        ifGameFinish = false;
        itemsInGameList.clear();
        spanTimeDecrease = 0;

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
            textViewCounterNum.setVisibility(View.INVISIBLE);
            goalPoints = gameType.gameGoalNumValue;
            progressBarPoints.setMax(goalPoints);
        }else if(gameNumber == 1){
            textViewCounterType.setText(R.string.text_level);

            textViewCounterNum.setText(String.valueOf(gameLevel));
            goalPoints = gameType.gameGoalNumValue;
            //count how many points are needed to reach level 10 to. Level starts at lev 1. 10 points for each levels.
            progressBarPoints.setMax((gameType.gameGoalNumValue - 1) * 10);
        }else{
            textViewCounterType.setText(R.string.text_points);
            textViewCounterNum.setText(String.valueOf(gamePoints));
            progressBarPoints.setVisibility(View.INVISIBLE);
            for(int i = 0; i < gameType.gameGoalNumValue; i++){
                ImageView ivLife = new ImageView(this);
                ivLife.setImageResource(R.drawable.button_earth);
                ivLife.setPadding(15,15,15,15);
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
    }

    private void startGame(){
        if(animationsImage.counterCountDown > 0){
            animationsImage.countDownStartGame(this, countDownView);
        }else {
            randomSpanTimeItem();
        }
    }

    /**
     * This method starts when animation countDownStartGame is finish. Starts from class AnimationsImage.
     * Runnable with random delay to add new Item to game.
     */
    public void randomSpanTimeItem() {
        handlerGame = new Handler();
        runnableGame = new Runnable() {
            @Override
            public void run() {
                addNewItem();
                handlerGame.postDelayed(this, randomIntSpanTime() );
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

            if (gamePoints % 10 == 0) {
                gameLevel = (gamePoints / 10) + 1;
            }
        }

        if((gameNumber == 0 || gameNumber == 1) && !ifGameFinish){
            if(!item.getIsTrash() ){
                gamePoints = gamePoints - 1;
            }

            if(gamePoints > 0){
                progressBarPoints.setProgress(gamePoints);
            }

            if(gameNumber == 1){
                textViewCounterNum.setText(String.valueOf(gameLevel));
            }

            if(gameNumber == 0){
                if(gamePoints == goalPoints){
                    ifGameFinish = true;
                    gameOver();
                }
            }else{
                if(gameLevel == goalPoints){
                    ifGameFinish = true;
                    gameOver();
                }
            }
        }else if(gameNumber == 2 && !ifGameFinish){
            textViewCounterNum.setText(String.valueOf(gamePoints));

            if(!item.getIsTrash() ){
                removeLife();
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
            musicController.soundLifeLost();
            upDateLife();
        }
    }

    public void upDateLife(){
        lifeContainer.removeAllViews();
        if(lifeImageList.isEmpty()){
            gameOver();
        }else {
            for(ImageView i : lifeImageList) {
                lifeContainer.addView(i);
                i.getLayoutParams().width = lifeWidthHeight;
                i.getLayoutParams().height = lifeWidthHeight;
            }
        }
    }

    private void gameOver(){
        animationsImage.finishRainbow(this, displayWidth);
        musicController.startFinishApplause();
        textViewCounterType.bringToFront();
        textViewCounterNum.bringToFront();
        TextView musicCredit = findViewById(R.id.textviewCredit);
        musicCredit.setVisibility(View.VISIBLE);
        for(int i = 0; i < 50; i++){
            addStar();
        }

        if(handlerGame != null){
            handlerGame.removeCallbacks(runnableGame);
        }

        //Waits some seconds (delaySecondsGoToMain) before go back to MainActivity.
        (new Handler()).postDelayed(this::goToMainActivity, delaySecondsGoToMain);
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

    private int randomIntSpanTime(){
        spanTimeDecrease = gameLevel * 5;
        int spanTime = new Random().nextInt(maxDelayTimeItem - minDelayTimeItem) + minDelayTimeItem;
        spanTime = spanTime - (spanTimeDecrease);
        if(spanTime < minSpanTime){
            spanTime = minSpanTime;
        }

        return spanTime;
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

        startGame();

        musicController.startBackgroundOcean();
    }

}