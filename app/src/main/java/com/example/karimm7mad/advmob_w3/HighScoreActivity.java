package com.example.karimm7mad.advmob_w3;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;


public class HighScoreActivity extends AppCompatActivity {

    private ListView recordsList = null;
    private String[] records = null;
    private DBAdapter dbman;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_high_score);

        this.dbman = new DBAdapter(this);
        this.dbman.open();
        Cursor c = this.dbman.getAllRows();
        this.records = new String[c.getCount()];

        String username = "", timestamp = "", record = "";
        float timeTaken = -1;
        int id = -1;
        if (c.moveToFirst()) {
            do {
                id = c.getInt(DBAdapter.COL_ROWID);
                username = c.getString(DBAdapter.COL_NAME);
                timeTaken = c.getFloat(DBAdapter.COL_SCORE);
                timestamp = c.getString(DBAdapter.COL_TIMESTAMP);
                record = timestamp +"\n"+id + "-" + username + "\nscore: " + timeTaken ;
                this.records[c.getPosition()] = record;
                record = "";
            } while (c.moveToNext());
        }

        this.recordsList = findViewById(R.id.recordsList);
        this.recordsList.setClickable(false);
        this.recordsList.setFocusable(false);
        this.recordsList.setAdapter(new ArrayAdapter<String>(this, R.layout.list_element, records));
    }

    @Override
    protected void onDestroy() {
        this.dbman.close();
        super.onDestroy();
    }
}
