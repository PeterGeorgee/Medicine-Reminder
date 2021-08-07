package com.example.project;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;
import java.sql.Blob;
import java.util.ArrayList;

public class adddrugnew extends AppCompatActivity {
    private static final int CAMERA_REQUEST = 100;
    byte[] byteArray;
    Bitmap photo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adddrugnew);
        TextView logOut=(TextView)findViewById(R.id.textView14);
        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(adddrugnew.this,MainActivity3.class));
            }
        });
        Button add=(Button)findViewById(R.id.addBtn);
        add.setOnClickListener(new View.OnClickListener(){
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                addPill();
            }
        });
        createStuff();

        Button addph=(Button)findViewById(R.id.button5);
        addph.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent cameraIntent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent,CAMERA_REQUEST);
            }
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_REQUEST) {
            photo = (Bitmap) data.getExtras().get("data");
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            photo.compress(Bitmap.CompressFormat.PNG, 100, stream);
            byteArray = stream.toByteArray();

        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void addPill(){
        EditText pillNamee =(EditText) findViewById(R.id.nameOfPill);
        String pillName=pillNamee.getText().toString();

        Spinner spin=(Spinner)findViewById(R.id.sQuantity);
        String pillPerDay =spin.getSelectedItem().toString();

        spin=(Spinner)findViewById(R.id.sPortion);
        String pillPortion =spin.getSelectedItem().toString();

        spin=(Spinner)findViewById(R.id.sTime);
        String pillTime =spin.getSelectedItem().toString();


        final drugDataBase drugsDB=new drugDataBase(this);
        drugsDB.createNewDrug(MainActivity.userName,pillName,Float.parseFloat(pillPortion),Integer.parseInt(pillPerDay),Integer.parseInt(pillTime),byteArray);
        Toast.makeText(this, "Drug Added", Toast.LENGTH_SHORT).show();
    }
    public void createStuff() {
        Spinner spin = (Spinner) findViewById(R.id.sQuantity);
        ArrayList<String> quantities = new ArrayList<>();
        quantities.add("1");
        quantities.add("2");
        quantities.add("3");
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, quantities);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(arrayAdapter);

        spin = (Spinner) findViewById(R.id.sPortion);
        ArrayList<String> portions = new ArrayList<>();
        portions.add("0.5");
        portions.add("1");
        portions.add("1.5");
        portions.add("2");

        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, portions);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(arrayAdapter);

        spin = (Spinner) findViewById(R.id.sTime);
        ArrayList<String> times = new ArrayList<>();
        for (int i = 1; i < 13; i++)
            times.add((Integer.toString(i)));
        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, times);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(arrayAdapter);
    }
}