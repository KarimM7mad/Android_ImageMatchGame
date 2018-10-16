package com.example.karimm7mad.advmob_w3;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    public Button startBtn, highScoreBtn, exitBtn;
    public Intent goToGamePlayIntent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        this.startBtn = findViewById(R.id.startBtn);
        this.highScoreBtn = findViewById(R.id.highScoreBtn);
        this.exitBtn = findViewById(R.id.exitBtn);
        this.goToGamePlayIntent = new Intent(MainActivity.this, TheGamePlayActivity.class);


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
        this.startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(goToGamePlayIntent);
            }
        });

    }

}
