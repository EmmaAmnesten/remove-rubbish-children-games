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

        saveTheOcean = (SaveTheOcean) context;
        this.displayHeight = displayHeight;

        ItemsType.items items = itemsType.getItemByPosition(randomInt(0, itemsType.getItemsLength()));
        name = items.name();
        drawable = items.drawableValue;
        isTrash = items.ifTrashValue;

        setImageResource(drawable);
        constraintLayout.addView(this);
        getLayoutParams().width = itemWidthHeight;
        getLayoutParams().height = itemWidthHeight;
        setX(randomInt(0, displayWidth - itemWidthHeight));
        setY(-itemWidthHeight);

        speed = randomInt(miniSpeed, maxSpeed);
    }

    private int randomInt(int fromInt, int toInt){
        return new Random().nextInt(toInt - fromInt) + fromInt;
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
