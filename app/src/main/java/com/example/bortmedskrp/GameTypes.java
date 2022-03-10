package com.example.bortmedskrp;

class GameTypes {
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

    public gameType getGameTypeByPosition(int intPosition) {
        return gameType.values()[intPosition];
    }
}
