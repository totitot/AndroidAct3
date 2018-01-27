package com.crisolokenth.act3;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.sql.SQLException;

public class HomeScreen extends AppCompatActivity {
    static final String DATABASE_TABLE = "userdetailsTB";
    DBAdapter db;

    String name, username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
        db = new DBAdapter(getApplicationContext());

    }

    @Override
    protected void onStart() {
        super.onStart();
        try{
            db.open();
        }catch(SQLException e){
            e.printStackTrace();
        }

        username = getIntent().getStringExtra("user_username");

        Cursor mCursor =
                db.db.query(true, DATABASE_TABLE, new String[] {"_id",
                                "name", "username", "password","address","gender","email"},
                        "username =?", new String[]{username},
                        null, null, null, null);

        if(mCursor.moveToFirst()) name = mCursor.getString(1);
        ((TextView)findViewById(R.id.textView9)).setText(name);
    }

    public void ViewInfo(View view){
        Intent next_screen = new Intent(this, UpdateInfo.class);
        next_screen.putExtras(getIntent());
        startActivity(next_screen);
        finish();
    }

    public void Logout(View view){
        Intent next_screen = new Intent(this, MainActivity.class);
        startActivity(next_screen);
        finish();
    }

    @Override
    protected void onStop() {
        super.onStop();
        db.close();
    }
}
