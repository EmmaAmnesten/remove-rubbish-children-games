package com.example.bortmedskrp;

import android.telephony.gsm.GsmCellLocation;

import java.util.ArrayList;

/**
 * Created by Emma on 2021-10-15.
 * Class som defines objects.
 */

class ItemsType {

    public enum items{
        BANANA(R.drawable.item_trash_banana, true),
        CARROT(R.drawable.item_trash_carrot, true),
        APPLE(R.drawable.item_trash_apple, true),
        BAG(R.drawable.item_trash_bag, true),
        CARDBOARD(R.drawable.item_trash_cardboard, true),
        BIN(R.drawable.item_trash_bin, true),
        TIN(R.drawable.item_trash_tin, true),
        CAN(R.drawable.item_trash_can, true),
        FISH1(R.drawable.item_animal_fish_1, false),
        FISH2(R.drawable.item_animal_fish_2, false),
        JELLYFISH(R.drawable.item_animal_jellyfish, false);


        int drawableValue;
        boolean ifTrashValue;
        items(int drawable, boolean isTrash) {
            drawableValue = drawable;
            ifTrashValue = isTrash;
        }
    }

    public enum stars{
        STAR1(R.drawable.end_star_pink),
        STAR2(R.drawable.end_star_orange),
        STAR3(R.drawable.end_star_purple),
        STAR4(R.drawable.end_star_yellow),
        STAR5(R.drawable.end_star_green),
        STAR6(R.drawable.end_star_blue);


        int drawableValue;
        stars(int drawable) {
            drawableValue = drawable;
        }
    }

    public enum gameType{
        GAME_TYPE1(10, false, false, R.string.game1_button),     //end type progressbar
        GAME_TYPE2(10, false, true, R.string.game2_button),      //end type level 10
        GAME_TYPE3(5, true, true, R.string.game3_button);      //end type life

        int gameGoalNumValue;
        Boolean lifeValue;
        boolean speedUpValue;
        int buttonStringValue;
        gameType(int gameGoalNum, boolean life, boolean speedUp, int buttonString){
            gameGoalNumValue = gameGoalNum;
            lifeValue = life;
            speedUpValue = speedUp;
            buttonStringValue = buttonString;
        }
    }

    public items getItemByPosition(int intPosition) {
        return items.values()[intPosition];
    }

    public int getItemsLength(){
        return items.values().length;
    }

    public stars getStarByPosition(int intPosition){
        return stars.values()[intPosition];
    }

    public int getStarLength(){
        return stars.values().length;
    }

    public gameType getGameTypeByPosition(int intPosition) {
        return gameType.values()[intPosition];
    }
}
