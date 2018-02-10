package com.totitot.act3;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.sql.SQLException;

public class MainActivity extends AppCompatActivity {
    static final String DATABASE_TABLE = "userdetailsTB";
    DBAdapter db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db = new DBAdapter(getApplicationContext());
        try {
            db.open();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

//  @Override
//  protected void onStart() {
//      super.onStart();
//      try {
//          db.open();
//      } catch (SQLException e) {
//          e.printStackTrace();
//      }
//  }

    public void createNewUser(View view) {
        Intent next_screen = new Intent(this, RegisterUser.class);
        startActivity(next_screen);
    }

    public void login(View view) {

        String username = ((EditText)(findViewById(R.id.username))).getText().toString();
        String password = ((EditText)(findViewById(R.id.password))).getText().toString();
        Cursor mCursor =
                db.db.query(true, DATABASE_TABLE, new String[] {"_id",
                                "name", "username", "password","address","gender","email"},
                        "username =?", new String[]{username},
                        null, null, null, null);

        if (mCursor.moveToFirst()) {
            String storedpassword = mCursor.getString(3);
            Toast.makeText(this,storedpassword + " entered " + password, Toast.LENGTH_SHORT).show();
            if(!storedpassword.equals(password)){
                Toast.makeText(this,"Incorrect user and/or password!",Toast.LENGTH_SHORT).show();
            }
            else{
                Intent next_screen = new Intent(this, HomeScreen.class);
                next_screen.putExtra("user_username", username);

                Toast.makeText(this,"Login success!", Toast.LENGTH_SHORT).show();
                startActivity(next_screen);
                finish();
            }
        }
        else{
            Toast.makeText(this,"Incorrect user and/or password!",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        db.close();
    }
}
