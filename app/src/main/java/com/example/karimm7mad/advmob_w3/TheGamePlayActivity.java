package com.example.karimm7mad.advmob_w3;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.Random;

public class TheGamePlayActivity extends AppCompatActivity {

    public static int numOfBtns = 8;
    public ImageButton[] imagesBtn = null;
    public int[] audioNumber = null;
    public int[] imagesNumber = null;
    public int appearingIndex1, appearingIndex2, tmpoooo;
    public Intent audioServiceIntent;
    public static boolean GameIsActive = false;
    int timeTaken;
    public DBAdapter dbman = null;
    AlertDialog.Builder builder = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_the_game_play);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        this.dbman = new DBAdapter(this.getBaseContext());
        this.dbman.open();
        this.audioServiceIntent = new Intent(TheGamePlayActivity.this, AudioService.class);
        this.appearingIndex1 = -1;
        this.appearingIndex2 = -1;
        this.tmpoooo = -1;
        this.timeTaken = (int) System.currentTimeMillis();
        this.initializeImgBtns();
    }
    public void createDialog() {
        final EditText usernameEditTxt = new EditText(this);
        usernameEditTxt.setHint("Enter Your name here");
        this.builder = new AlertDialog.Builder(this);
        this.builder.setTitle("Congrants ;-)");
        this.builder.setMessage("You Finished in " + this.timeTaken + " seconds.\nEnter your name and save");
        this.builder.setView(usernameEditTxt);
        this.builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                String namee = usernameEditTxt.getText().toString();
                if (namee.equalsIgnoreCase("enter Your name") || namee.isEmpty()) {
                    namee = "No Name :-(";
                }
                dbman.insertRow(namee, (int) timeTaken);
                dbman.close();
                dialog.cancel();
                finish();
            }
        });
        this.builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                dbman.close();
                dialog.cancel();
                finish();
            }
        });
        this.builder.create().show();
    }
    @SuppressLint("ResourceType")
    private void initializeImgBtns() {
        //generate the random pics and audio indices
        this.generateRandomIntNumbers();
        //create the array of btns
        this.imagesBtn = new ImageButton[numOfBtns];
        //create the object of each btn
        for (int i = 0; i < numOfBtns; i++) {
            this.imagesBtn[i] = findViewById(R.id.image0btn + (i));
            this.imagesBtn[i].setImageResource(R.raw.questionmarkimg);
            this.imagesBtn[i].setScaleType(ImageView.ScaleType.FIT_XY);
        }
        //create the listeners of each btn
        for (int i = 0; i < numOfBtns; i++) {
            tmpoooo = i;
            this.imagesBtn[i].setOnClickListener(new View.OnClickListener() {
                int btnIndex = tmpoooo;
                @Override
                public void onClick(View v) {
                    callCheckMatchIfPossible(btnIndex);
                }
            });
        }
    }
    @SuppressLint("ResourceType")
    public void callCheckMatchIfPossible(int pieceNum) {
        //appearing index = -1 indicates that no btn in clicked
        imagesBtn[pieceNum].setImageResource(imagesNumber[pieceNum]);
        if (appearingIndex1 == -1) {
            appearingIndex1 = pieceNum;
            audioServiceIntent.putExtra("voiceName", audioNumber[pieceNum]);
            startService(audioServiceIntent);
        }
        // one image is clicked and checking second btn
        else if (appearingIndex2 == -1) {
            //if not the same as btn 1 , then check the match of the 2 images
            if (pieceNum != appearingIndex1) {
                appearingIndex2 = pieceNum;
                audioServiceIntent.putExtra("voiceName", audioNumber[pieceNum]);
                startService(audioServiceIntent);
                this.checkMatch();
            }
            //the 2 btns are the same , hide the selected btn
            else {
                this.imagesBtn[pieceNum].setImageResource(R.raw.questionmarkimg);
                appearingIndex1 = -1;
                appearingIndex2 = -1;
            }
        }
    }

    @SuppressLint("ResourceType")
    public void checkMatch() {
        if (this.imagesNumber[appearingIndex1] == this.imagesNumber[appearingIndex2]) {
            this.imagesBtn[appearingIndex1].setClickable(false);
            this.imagesBtn[appearingIndex2].setClickable(false);
            Toast.makeText(this.getBaseContext(), "Match ;-)", Toast.LENGTH_SHORT).show();
            checkWinState();
        } else {
            Toast.makeText(this.getBaseContext(), "No Match :-(", Toast.LENGTH_SHORT).show();
            this.imagesBtn[appearingIndex1].setImageResource(R.raw.questionmarkimg);
            this.imagesBtn[appearingIndex2].setImageResource(R.raw.questionmarkimg);
        }
        appearingIndex1 = -1;
        appearingIndex2 = -1;
    }
    public void checkWinState() {
        for (int i = 0; i < numOfBtns; i++)
            if (this.imagesBtn[i].isClickable())
                return;
        this.timeTaken = (((int) System.currentTimeMillis() - this.timeTaken) / 1000);
        this.createDialog();
    }
    //generate the random images
    public void generateRandomIntNumbers() {
        Log.i("info", "debug : start generate rnd num");
        this.imagesNumber = new int[numOfBtns];
        this.audioNumber = new int[numOfBtns];
        Random r = new Random();
        //generate 8 random values from 0->3
        for (int i = 0; i < numOfBtns; i += 2) {
            this.imagesNumber[i] = i / 2;
            this.imagesNumber[i + 1] = i / 2;
            this.audioNumber[i] = i / 2;
            this.audioNumber[i + 1] = i / 2;
        }
        int rndIndex = -1;
        int swapTmp = -1;
        for (int i = numOfBtns - 1; i >= 0; i--) {
            rndIndex = r.nextInt(i + 1);
            //swap img numbers
            swapTmp = this.imagesNumber[rndIndex];
            this.imagesNumber[rndIndex] = this.imagesNumber[i];
            this.imagesNumber[i] = swapTmp;
            //swap audio numbers
            swapTmp = this.audioNumber[rndIndex];
            this.audioNumber[rndIndex] = this.audioNumber[i];
            this.audioNumber[i] = swapTmp;
        }
        //map the integer values to resources
        for (int i = 0; i < this.imagesNumber.length; i++) {
            this.audioNumber[i] = this.getMappedAudId(this.audioNumber[i]);
            this.imagesNumber[i] = this.getMappedImgId(this.imagesNumber[i]);
        }
        Log.i("info", "debug : finish generate rnd num");
    }
    //to get the resources in term of integer Values in imageNumber
    public int getMappedImgId(int id) {
        switch (id) {
            case 0:
                return R.raw.dogimg;
            case 1:
                return R.raw.lionimg;
            case 2:
                return R.raw.owlimg;
            case 3:
                return R.raw.eagleimg;
        }
        return R.raw.questionmarkimg;
    }
    public int getMappedAudId(int id) {
        switch (id) {
            case 0:
                return R.raw.dogaud;
            case 1:
                return R.raw.lionaud;
            case 2:
                return R.raw.owlaud;
            case 3:
                return R.raw.eagleaud;
        }
        return R.raw.questionmarkimg;
    }
    @Override
    protected void onDestroy() {
        Log.i("info", "debug : start on destroy");
        super.onDestroy();
        Log.i("info", "debug : finish super on destroy");
        stopService(audioServiceIntent);

    }
}
