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

    private static final int miniSpeed = 6;
    private static final int maxSpeed = 15;

    String name;
    int drawable;
    Boolean isTrash;
    int speed;

    SaveTheOcean saveTheOcean;
    int displayHeight;

    Handler handler;
    Runnable runnable;

    public Item(Context context){
        super(context);
    }

    public Item(Context context, ItemsType itemsType, ConstraintLayout constraintLayout,
                int displayHeight, int displayWidth, int itemWidthHeight) {
        super(context);
        int randomInt = new Random().nextInt(itemsType.getItemsLength());
        ItemsType.items items = itemsType.getItemByPosition(randomInt);

        name = items.name();
        drawable = items.drawableValue;
        isTrash = items.ifTrashValue;
        speed = new Random().nextInt(maxSpeed - miniSpeed) + miniSpeed;

        saveTheOcean = (SaveTheOcean) context;
        this.displayHeight = displayHeight;

        setImageResource(drawable);
        constraintLayout.addView(this);
        getLayoutParams().width = itemWidthHeight;
        getLayoutParams().height = itemWidthHeight;
        int posX = new Random().nextInt(displayWidth - itemWidthHeight);
        setX(posX);
        setY(-itemWidthHeight);
    }

    public String getName() {
        return name;
    }

    public Boolean getIsTrash() {
        return isTrash;
    }

    public int getSpeed(){
        return speed;
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
