package com.example.bortmedskrp;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.util.Log;
import android.widget.ImageView;

import androidx.constraintlayout.widget.ConstraintLayout;

import java.lang.reflect.Array;
import java.util.Random;

import static android.content.ContentValues.TAG;

/**
 * Created by  on 2021-10-15.
 * Objekt i spelet. Slumpas vad det är för objekt från itemType och sen läggs det till i spelet.
 */

class Item extends androidx.appcompat.widget.AppCompatImageView {

    String name;
    int drawable;
    Boolean isTrash;

    SaveTheOcean saveTheOcean;
    int displayHeight;

    Handler handler;
    Runnable runnable;


    public Item(Context context, ItemsType itemsType, ConstraintLayout constraintLayout,
                int displayHeight, int displayWidth, int itemWidthHeight) {
        super(context);
        int randomInt = new Random().nextInt(itemsType.getItemsLength());
        ItemsType.items items = itemsType.getItemByPosition(randomInt);

        name = items.name();
        drawable = items.drawableValue;
        isTrash = items.ifTrashValue;

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

    /**
     * Förflyttar objektet neråt.
     * item-objekt i spelet som faller neråt.
     */
    public void moveItemDown(){

        Item item = this;

        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                float itemPosY = getY();

                if(itemPosY < displayHeight){
                    setY(itemPosY+10);
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
