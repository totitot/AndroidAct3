package com.crisolokenth.act3;

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


    }

    @Override
    protected void onStart() {
        super.onStart();
        try {
            db.open();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void createNewUser(View view) {
        Intent next_screen = new Intent(this, RegisterUser.class);
        startActivity(next_screen);
    }

    public void login(View view) {

        String username = ((EditText)(findViewById(R.id.editText))).getText().toString();
        String password = ((EditText)(findViewById(R.id.editText2))).getText().toString();
        Cursor mCursor =
                db.db.query(true, DATABASE_TABLE, new String[] {"_id",
                                "name", "username", "password","address","gender","email"},
                        "username =?", new String[]{username},
                        null, null, null, null);

        if (mCursor.moveToFirst()) {
            if(password.equals(mCursor.getString(3))){
                Toast.makeText(this,"Incorrect user and/or password!",Toast.LENGTH_SHORT).show();
            }
            else{
                Intent next_screen = new Intent(this, HomeScreen.class);
 //               next_screen.putExtra("user_name", mCursor.getString(1));
                next_screen.putExtra("user_username", username);
//                next_screen.putExtra("user_password", mCursor.getString(3));
//                next_screen.putExtra("user_address", mCursor.getString(4));
//                next_screen.putExtra("user_gender", mCursor.getString(5));
//                next_screen.putExtra("user_email", mCursor.getString(6));

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
    protected void onStop() {
        super.onStop();
        db.close();
    }
}
