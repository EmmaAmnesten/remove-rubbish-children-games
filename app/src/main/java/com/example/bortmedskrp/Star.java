package com.example.bortmedskrp;

import android.content.Context;
import android.os.Handler;
import android.util.Log;

import androidx.constraintlayout.widget.ConstraintLayout;

import java.util.Random;

import static android.content.ContentValues.TAG;

/**
 * Created by  on 2021-11-05.
 */

class Star extends androidx.appcompat.widget.AppCompatImageView {

    String name;
    int drawable;

    SaveTheOcean saveTheOcean;
    int displayHeight;
    int displayWidth;

    int starHeight;
    int starWidth;

    Handler handler;
    Runnable runnable;

    float speedX;
    float speedY;
    int speedDelay;


    public Star(Context context, ItemsType itemsType, ConstraintLayout constraintLayout,
                int displayHeight, int displayWidth) {
        super(context);
        int randomInt = new Random().nextInt(itemsType.getStarLength());
        ItemsType.stars star = itemsType.getStarByPosition(randomInt);

        name = star.name();
        drawable = star.drawableValue;

        saveTheOcean = (SaveTheOcean) context;
        this.displayHeight = displayHeight;
        this.displayWidth = displayWidth;

        setImageResource(drawable);
        constraintLayout.addView(this);
        setX(displayWidth / 2);
        setY(displayHeight / 2);

        speedX = randomSpeed();
        speedY = randomSpeed();
        speedDelay = randomDelay();

        starHeight = getDrawable().getIntrinsicHeight();
        starWidth = getDrawable().getIntrinsicWidth();
    }

    public String getName() {
        return name;
    }


    /**
     * Flyttar stjärnan i slumpad riktning
     * och slumpad fart (speedDealay).
     */
    public void moveStar(){

        Star star = this;

        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                float starPosX = getX();
                float starPosY = getY();

                if(starPosX > (0 - starHeight)
                        && starPosX < displayWidth
                        && starPosY > (0 - starWidth)
                        && starPosY < displayHeight){
                    setX(starPosX + speedX);
                    setY(starPosY + speedY);
                    handler.postDelayed(this, speedDelay);
                }else {
                    saveTheOcean.removeStarFromGame(star);
                    handler.removeCallbacks(this);
                }
            }
        };

        handler.post(runnable);
    }

    private float randomSpeed(){
        float randomInt = new Random().nextInt(20) - 10;
        return randomInt;
    }

    private int randomDelay(){
        int randomInt = new Random().nextInt(7) + 5;
        return randomInt;
    }

    public void removeHandlerStar(){
        handler.removeCallbacks(runnable);
    }
}
