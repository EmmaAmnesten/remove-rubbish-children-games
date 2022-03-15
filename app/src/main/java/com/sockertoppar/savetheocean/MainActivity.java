package com.sockertoppar.savetheocean;

import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.sockertoppar.savetheocean.R;

/**
 * Buttons for various game types.
 */

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

        LinearLayout buttonGame1 = findViewById(R.id.button_game1);
        LinearLayout buttonGame2 = findViewById(R.id.button_game2);
        LinearLayout buttonGame3 = findViewById(R.id.button_game3);

        TextView buttonGame1Text = findViewById(R.id.button_game1_text);
        TextView buttonGame2Text = findViewById(R.id.button_game2_text);
        TextView buttonGame3Text = findViewById(R.id.button_game3_text);

        buttonGame1Text.setText(getString(gameType1.buttonStringValue,
                String.valueOf(gameType1.gameGoalNumValue)));
        buttonGame2Text.setText(getString(gameType2.buttonStringValue,
                String.valueOf(gameType2.gameGoalNumValue)));
        buttonGame3Text.setText(getString(gameType3.buttonStringValue,
                String.valueOf(gameType3.gameGoalNumValue)));

        buttonGame1.setOnClickListener(v -> startGameSaveOcean(game1Num));
        buttonGame2.setOnClickListener(v -> startGameSaveOcean(game2Num));
        buttonGame3.setOnClickListener(v -> startGameSaveOcean(game3Num));
    }


    private void startGameSaveOcean(int levelNum){
        Intent intent = new Intent(this, SaveTheOcean.class)
                .putExtra("EXTRA_TEXT_LEVEL", String.valueOf(levelNum));
        startActivity(intent);
    }


}