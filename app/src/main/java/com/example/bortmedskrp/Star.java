package com.example.bortmedskrp;

import android.content.Context;
import android.os.Handler;
import androidx.constraintlayout.widget.ConstraintLayout;
import java.util.Random;

/**
 * Created by  on 2021-11-05.
 * Stars like a fireworks when game is over.
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

    public Star(Context context){
        super(context);
    }

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
        setX((Integer)(displayWidth / 2));
        setY((Integer)(displayHeight / 2));

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
     * Move star in random direction
     * and random speed (speedDelay)
     */
    public void moveStar(){

        Star star = this;

        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                float starPosX = getX();
                float starPosY = getY();

                if(starPosX > - starWidth
                        && starPosX < displayWidth
                        && starPosY > - starHeight
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
        return new Random().nextInt(20) - 10;
    }

    private int randomDelay(){
        return new Random().nextInt(7) + 5;
    }

    public void removeHandlerStar(){
        handler.removeCallbacks(runnable);
    }
}
