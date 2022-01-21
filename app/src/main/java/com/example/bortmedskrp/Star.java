package com.example.bortmedskrp;

import android.content.Context;
import android.os.Handler;

import androidx.constraintlayout.widget.ConstraintLayout;

import java.util.Random;

/**
 * Created by  on 2021-11-05.
 */

class Star extends androidx.appcompat.widget.AppCompatImageView {

    String name;
    int drawable;

    SaveTheOcean saveTheOcean;
    int displayHeight;

    Handler handler;
    Runnable runnable;

    int speed;
    int curve;


    public Star(Context context, ItemsType itemsType, ConstraintLayout constraintLayout,
                int displayHeight, int displayWidth) {
        super(context);
        int randomInt = new Random().nextInt(itemsType.getStarLength());
        ItemsType.stars star = itemsType.getStarByPosition(randomInt);

        name = star.name();
        drawable = star.drawableValue;

        saveTheOcean = (SaveTheOcean) context;
        this.displayHeight = displayHeight;

        setImageResource(drawable);
        constraintLayout.addView(this);
//        getLayoutParams().width = itemWidthHeight;
//        getLayoutParams().height = itemWidthHeight;
        setX(displayWidth / 2);
        setY(displayHeight / 2);
    }

    public String getName() {
        return name;
    }


    /**
     */
    public void moveStar(){

        Star star = this;

        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                float starPosY = getY();

                if(starPosY < displayHeight){
                    setY(starPosY+10);
                    handler.postDelayed(this,25);
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
