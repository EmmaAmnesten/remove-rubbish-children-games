package com.example.bortmedskrp;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;


public class MainActivity extends AppCompatActivity {

    private static final int game1Num = 0;
    private static final int game2Num = 1;
    private static final int game3Num = 2;

    ItemsType itemsType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        itemsType = new ItemsType();

        ItemsType.gameType gameType1 = itemsType.getGameTypeByPosition(game1Num);
        ItemsType.gameType gameType2 = itemsType.getGameTypeByPosition(game2Num);
        ItemsType.gameType gameType3 = itemsType.getGameTypeByPosition(game3Num);

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