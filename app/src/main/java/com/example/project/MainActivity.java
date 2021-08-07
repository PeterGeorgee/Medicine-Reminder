package com.example.project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    public static String userName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView txtt=(TextView)findViewById(R.id.textView8);
        txtt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,MainActivity2.class));
            }
        });


        Button logg=(Button)findViewById(R.id.button);
        logg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logIn();
            }
        });
    }
    public void logIn() {
        boolean loggedIn = false;
        EditText username = (EditText) findViewById(R.id.userName);
        String userName = username.getText().toString();
        EditText pass = (EditText) findViewById(R.id.password);
        String password = pass.getText().toString();
        accountDataBase accDB = new accountDataBase(getApplicationContext());
        Cursor cursor = accDB.getAllAccounts();
        ArrayList<User> accounts = new ArrayList<>();
        while (!cursor.isAfterLast()) {
            User u = new User(cursor.getString(0), cursor.getString(1));
            accounts.add(u);
            cursor.moveToNext();
        }
        if (userName.equals("")||password.equals("")) {
            Toast.makeText(getApplicationContext(), "INVALID PARAMETERS", Toast.LENGTH_LONG).show();
        } else {
            for (int i = 0; i < accounts.size(); i++) {
                if (accounts.get(i).getUsername().equals(userName)) {
                    if (accounts.get(i).getPassword().equals(password)) {
                        this.userName = userName;
                        userInfo.user = new User(userName, password);
                        startActivity(new Intent(MainActivity.this, MainActivity4.class));
                        Toast.makeText(getApplicationContext(), "Hello, "+userName+"!", Toast.LENGTH_LONG).show();
                        loggedIn = true;
                    } else {
                        Toast.makeText(getApplicationContext(), "INCORRECT PASSWORD", Toast.LENGTH_LONG).show();
                    }
                }
            }
            if (!loggedIn) {
                Toast.makeText(getApplicationContext(), "Username not found", Toast.LENGTH_LONG).show();
            }
        }
    }
}