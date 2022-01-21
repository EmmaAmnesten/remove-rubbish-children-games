package com.example.bortmedskrp;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import java.time.Instant;

import static android.content.ContentValues.TAG;

public class MainActivity extends AppCompatActivity {

    private static final int level1Num = 5;
    private static final int level2Num = 10;
    private static final int level3Num = 50;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button buttonGameSaveTheOceanLevel1 = findViewById(R.id.button_level1);
        Button buttonGameSaveTheOceanLevel2 = findViewById(R.id.button_level2);
        Button buttonGameSaveTheOceanLevel3 = findViewById(R.id.button_level3);

        buttonGameSaveTheOceanLevel1.setText(getString(R.string.game1_button, String.valueOf(level1Num)));
        buttonGameSaveTheOceanLevel2.setText(getString(R.string.game1_button, String.valueOf(level2Num)));
        buttonGameSaveTheOceanLevel3.setText(getString(R.string.game1_button, String.valueOf(level3Num)));

        buttonGameSaveTheOceanLevel1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startGameSaveOcean(level1Num);
            }
        });
        buttonGameSaveTheOceanLevel2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startGameSaveOcean(level2Num);
            }
        });
        buttonGameSaveTheOceanLevel3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startGameSaveOcean(level3Num);
            }
        });
    }


    private void startGameSaveOcean(int levelNum){
        Intent intent = new Intent(this, SaveTheOcean.class)
                .putExtra("EXTRA_TEXT", String.valueOf(levelNum));
        startActivity(intent);
    }


}