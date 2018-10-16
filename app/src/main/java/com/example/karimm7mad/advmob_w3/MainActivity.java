package com.example.karimm7mad.advmob_w3;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    public Button startBtn, highScoreBtn, exitBtn;
    public Intent goToGamePlayIntent, goTOHighScoreIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        this.goToGamePlayIntent = new Intent(MainActivity.this, TheGamePlayActivity.class);
        this.goTOHighScoreIntent = new Intent(MainActivity.this, HighScoreActivity.class);

        this.startBtn = findViewById(R.id.startBtn);
        this.highScoreBtn = findViewById(R.id.highScoreBtn);
        this.exitBtn = findViewById(R.id.exitBtn);

        this.startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(goToGamePlayIntent);
            }
        });

        this.highScoreBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(goTOHighScoreIntent);
            }
        });

        this.exitBtn.setOnClickListener(new View.OnClickListener() {
            boolean b1 = false;

            @Override
            public void onClick(View v) {
                if (b1) {
                    System.exit(0);
                } else {
                    Toast.makeText(getBaseContext(), "Click again to close", Toast.LENGTH_SHORT).show();
                    b1 = true;
                }
            }
        });

    }

}
