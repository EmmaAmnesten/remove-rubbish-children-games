package com.example.bortmedskrp;

/**
 * Created by Emma on 2021-10-15.
 * Class som defines objects.
 */

class ItemsType {

    public enum items{
        BANANA(R.drawable.trash_banana, true),
        BAG(R.drawable.trash_bag, true),
        APPLE(R.drawable.trash_apple, true),
        TIN(R.drawable.trash_tin, true),
        CAN(R.drawable.trash_can, true),
        FISH1(R.drawable.animal_fish_1, false),
        FISH2(R.drawable.animal_fish_2, false),
        JELLYFISH(R.drawable.animal_jellyfish, false);


        int drawableValue;
        boolean ifTrashValue;
        items(int drawable, boolean isTrash) {
            drawableValue = drawable;
            ifTrashValue = isTrash;
        }
    }

    public enum stars{
        STAR1(R.drawable.star1),
        STAR2(R.drawable.star2),
        STAR3(R.drawable.star3),
        STAR4(R.drawable.star4),
        STAR5(R.drawable.star5),
        STAR6(R.drawable.star6);


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
