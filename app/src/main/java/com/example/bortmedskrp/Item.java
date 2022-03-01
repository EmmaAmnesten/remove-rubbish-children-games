package com.example.bortmedskrp;

import android.content.Context;
import android.os.Handler;

import androidx.constraintlayout.widget.ConstraintLayout;
import java.util.Random;



/**
 * Created by  on 2021-10-15.
 * Object in game. Random item type and add to game.
 */

class Item extends androidx.appcompat.widget.AppCompatImageView {

    final static int startMiniSpeed = 6;
    final static int startMaxSpeed = 15;


    SaveTheOcean saveTheOcean;
    int displayHeight;
    int gameLevel;
    int gameNumber;

    String name;
    int drawable;
    Boolean isTrash;
    int speed;

    Handler handler;
    Runnable runnable;

    public Item(Context context){
        super(context);
    }

    public Item(Context context, ItemsType itemsType, ConstraintLayout constraintLayout,
                int displayHeight, int displayWidth, int itemWidthHeight, int gameLevel, int gameNumber) {
        super(context);

        saveTheOcean = (SaveTheOcean) context;
        this.displayHeight = displayHeight;
        this.gameLevel = gameLevel;
        this.gameNumber = gameNumber;

        ItemsType.items items = itemsType.getItemByPosition(randomInt(itemsType.getItemsLength()));
        name = items.name();
        drawable = items.drawableValue;
        isTrash = items.ifTrashValue;

        setImageResource(drawable);
        constraintLayout.addView(this);
        getLayoutParams().width = itemWidthHeight;
        getLayoutParams().height = itemWidthHeight;
        setX(randomInt(displayWidth - itemWidthHeight));
        setY(-itemWidthHeight);

        speed = randomIntSpeed();
    }

    private int randomInt(int toInt){
        return new Random().nextInt(toInt);
    }

    private int randomIntSpeed(){
        int speed = new Random().nextInt(startMaxSpeed - startMiniSpeed) + startMiniSpeed;
        if(gameLevel < 10) {
            speed = speed + gameLevel;
        }else{
            speed = speed + 10;
        }
        return speed;
    }

    public String getName() {
        return name;
    }

    public Boolean getIsTrash() {
        return isTrash;
    }

    /**
     * Move item down in game.
     */
    public void moveItemDown(){

        Item item = this;

        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                float itemPosY = getY();

                if(itemPosY < displayHeight){
                    setY(itemPosY + speed);
                    handler.postDelayed(this,25);
                }else {
                    saveTheOcean.removeItemFromGame(item);
                    if(gameNumber == 2 && item.getIsTrash()){
                        saveTheOcean.removeLife();
                    }
                    handler.removeCallbacks(this);
                }
            }
        };

        handler.post(runnable);
    }

    public void removeHandlerItem(){
        handler.removeCallbacks(runnable);
    }
}
