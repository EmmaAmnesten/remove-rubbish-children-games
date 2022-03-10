package com.example.bortmedskrp;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

/**
 * Buttons for various game types.
 */

// TODO: 2022-03-10 add image on buttons, so children who can not read see differences on buttons

public class MainActivity extends AppCompatActivity {

    private static final int game1Num = 0;
    private static final int game2Num = 1;
    private static final int game3Num = 2;

    GameTypes gameTypes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gameTypes = new GameTypes();

        GameTypes.gameType gameType1 = gameTypes.getGameTypeByPosition(game1Num);
        GameTypes.gameType gameType2 = gameTypes.getGameTypeByPosition(game2Num);
        GameTypes.gameType gameType3 = gameTypes.getGameTypeByPosition(game3Num);

        Button buttonGameSaveTheOceanLevel1 = findViewById(R.id.button_level1);
        Button buttonGameSaveTheOceanLevel2 = findViewById(R.id.button_level2);
        Button buttonGameSaveTheOceanLevel3 = findViewById(R.id.button_level3);

        buttonGameSaveTheOceanLevel1.setText(getString(gameType1.buttonStringValue,
                String.valueOf(gameType1.gameGoalNumValue)));
        buttonGameSaveTheOceanLevel2.setText(getString(gameType2.buttonStringValue,
                String.valueOf(gameType2.gameGoalNumValue)));
        buttonGameSaveTheOceanLevel3.setText(getString(gameType3.buttonStringValue,
                String.valueOf(gameType3.gameGoalNumValue)));

        buttonGameSaveTheOceanLevel1.setOnClickListener(v -> startGameSaveOcean(game1Num));
        buttonGameSaveTheOceanLevel2.setOnClickListener(v -> startGameSaveOcean(game2Num));
        buttonGameSaveTheOceanLevel3.setOnClickListener(v -> startGameSaveOcean(game3Num));
    }


    private void startGameSaveOcean(int levelNum){
        Intent intent = new Intent(this, SaveTheOcean.class)
                .putExtra("EXTRA_TEXT_LEVEL", String.valueOf(levelNum));
        startActivity(intent);
    }


}