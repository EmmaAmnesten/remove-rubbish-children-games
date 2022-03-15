package com.sockertoppar.savetheocean;

import com.sockertoppar.savetheocean.R;

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
}
