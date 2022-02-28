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

    private static final int negativeSpeedSpan = 10;
    private static final int maxSpeedSpan = 20;
    private static final int minDelay = 4;
    private static final int maxDelay = 8;

    SaveTheOcean saveTheOcean;

    Handler handler;
    Runnable runnable;

    String name;
    int drawable;

    int displayHeight;
    int displayWidth;
    int starHeight;
    int starWidth;

    int directionSpeedX;
    int directionSpeedY;
    int speedDelay;

    public Star(Context context){
        super(context);
    }

    public Star(Context context, ItemsType itemsType, ConstraintLayout constraintLayout,
                int displayHeight, int displayWidth) {
        super(context);

        saveTheOcean = (SaveTheOcean) context;
        this.displayHeight = displayHeight;
        this.displayWidth = displayWidth;

        ItemsType.stars star = itemsType.getStarByPosition(randomInt(0, itemsType.getStarLength()));
        name = star.name();
        drawable = star.drawableValue;

        setImageResource(drawable);
        constraintLayout.addView(this);
        setX(displayWidth >> 1);
        setY(displayHeight >> 1);

        directionSpeedX = randomIntNegativeSpan();
        directionSpeedY = randomIntNegativeSpan();
        speedDelay = randomInt(minDelay, maxDelay);

        starHeight = getDrawable().getIntrinsicHeight();
        starWidth = getDrawable().getIntrinsicWidth();
    }

    private int randomInt(int fromInt, int toInt){
        return new Random().nextInt(toInt - fromInt) - fromInt;
    }

    private int randomIntNegativeSpan(){
        return new Random().nextInt(maxSpeedSpan) - negativeSpeedSpan;
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
                    setX(starPosX + directionSpeedX);
                    setY(starPosY + directionSpeedY);
                    handler.postDelayed(this, speedDelay);
                }else {
                    saveTheOcean.removeStarFromGame(star);
                    handler.removeCallbacks(this);
                }
            }
        };

        handler.post(runnable);
    }



    public void removeHandlerStar(){
        handler.removeCallbacks(runnable);
    }
}
