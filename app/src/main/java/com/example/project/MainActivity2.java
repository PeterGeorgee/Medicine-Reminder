package com.example.project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration);

        Button reg=(Button)findViewById(R.id.button2);
        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    signUp();
                    startActivity(new Intent(MainActivity2.this, MainActivity.class));

            }
        });
    }
    public void signUp(){
        EditText newUser=(EditText)findViewById(R.id.userName);
        String userName=newUser.getText().toString();
        EditText newPass=(EditText)findViewById(R.id.password);
        String password=newPass.getText().toString();
        accountDataBase accDB=new accountDataBase(this);
        accDB.createNewAccount(userName,password);
        userInfo.user=new User(userName,password);
    }
}