package com.example.project;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class druglistnew extends AppCompatActivity {
    ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_druglistnew);
        makeList();
    }
    public void makeList(){
        final drugDataBase drugsDB;
        drugsDB=new drugDataBase(getApplicationContext());
        final ArrayList<drug> drugs=new ArrayList<drug>();
        final ArrayList<String>users=new ArrayList<>();
        final ArrayList<byte[]>pics=new ArrayList<>();
        ArrayList<String> drugName=new ArrayList<String>();
        Cursor cursor=drugsDB.getAllDrugs();
        while(!cursor.isAfterLast()){   //populating the drug arrayList and users arrayList
            drug d=new drug();
            users.add(cursor.getString(0));
            d.setName(cursor.getString(1));
            d.setPortion(Float.parseFloat(cursor.getString(2)));
            d.setNumPerDay(Integer.parseInt(cursor.getString(3)));
            d.setStartTime(Integer.parseInt(cursor.getString(4)));
            drugs.add(d);
            byte[] image = cursor.getBlob(5);
            pics.add(image);
            cursor.moveToNext();
        }
        final Spinner spin=(Spinner)findViewById(R.id.spinner);
        for(int i =0;i<users.size();i++){
            if(userInfo.user.getUsername().equals(users.get(i))){
                drugName.add(drugs.get(i).getName());
            }
        }
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, drugName);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(arrayAdapter);

        spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String pillName = spin.getSelectedItem().toString();
                    for(int j =0;j<drugs.size();j++){
                    if(drugs.get(j).getName().equals(pillName)){
                        TextView tvPortion=(TextView) findViewById(R.id.textView11);
                        tvPortion.setText(String.valueOf(drugs.get(j).getPortion()));

                        try{
                            imageView=(ImageView)findViewById(R.id.pillImg);
                            Bitmap decodedByte = BitmapFactory.decodeByteArray(pics.get(j), 0, pics.get(j).length);
                            imageView.setImageBitmap(decodedByte);
                        }catch(Exception e){
                            Toast.makeText(getApplicationContext(), "Problem here boss", Toast.LENGTH_LONG).show();
                        };

                        int startTime=drugs.get(j).getStartTime();



                        if(drugs.get(j).getNumPerDay()==1){
                            TextView tvDate=(TextView) findViewById(R.id.textView15);
                            tvDate.setText("Every "+String.valueOf(startTime)+" PM!");
                        }else if(drugs.get(j).getNumPerDay()==2){
                            TextView tvDate=(TextView) findViewById(R.id.textView15);
                            tvDate.setText(String.valueOf(startTime)+" PM and "+String.valueOf(startTime)+" AM!");
                        }else if(drugs.get(j).getNumPerDay()==3){
                            TextView tvDate= (TextView) findViewById(R.id.textView15);
                            ArrayList<String> times=new ArrayList<>();
                            times.add(String.valueOf(drugs.get(j).getStartTime()));
                            if(drugs.get(j).getStartTime()<4) {
                                times.add(String.valueOf(drugs.get(j).getStartTime()+8));
                                times.add(String.valueOf(drugs.get(j).getStartTime()+4));
                                tvDate.setText(times.get(0) +" PM ,"+times.get(1)+" PM and "+times.get(2)+" AM!");
                            }
                            else if(drugs.get(j).getStartTime()==4){
                                times.add(String.valueOf(drugs.get(j).getStartTime() + 8));
                                times.add(String.valueOf(drugs.get(j).getStartTime() + 4));
                                tvDate.setText(times.get(0) + " PM ," + times.get(1) + " AM and " + times.get(2) + " AM!");
                            }
                            else if(drugs.get(j).getStartTime()>4&&drugs.get(j).getStartTime()<8) {
                                times.add(String.valueOf(drugs.get(j).getStartTime() -4));
                                times.add(String.valueOf(drugs.get(j).getStartTime() + 4));
                                tvDate.setText(times.get(0) + " PM ," + times.get(1) + " AM and " + times.get(2) + " AM!");
                            }
                            else if(drugs.get(j).getStartTime()==8){
                                times.add(String.valueOf(drugs.get(j).getStartTime() - 4));
                                times.add(String.valueOf(drugs.get(j).getStartTime() + 4));
                                tvDate.setText(times.get(0) + " PM ," + times.get(1) + " AM and " + times.get(2) + " PM!");
                            }
                            else if(drugs.get(j).getStartTime()>8&&drugs.get(j).getStartTime()<12) {
                                times.add(String.valueOf(drugs.get(j).getStartTime() -4));
                                times.add(String.valueOf(drugs.get(j).getStartTime() - 8));
                                tvDate.setText(times.get(0) + " PM ," + times.get(1) + " AM and " + times.get(2) + " PM!");
                            }
                            else if(drugs.get(j).getStartTime()==12) {
                                times.add(String.valueOf(drugs.get(j).getStartTime() -4));
                                times.add(String.valueOf(drugs.get(j).getStartTime() -8));
                                tvDate.setText(times.get(0) + " PM ," + times.get(1) + " PM and " + times.get(2) + " AM!");
                            }
                        }
                    }
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                TextView tvPortion=(TextView) findViewById(R.id.textView11);
                tvPortion.setText("PLEASE SELECT A PILL");
                TextView tvDate= (TextView) findViewById(R.id.textView15);
                tvDate.setText("PLEASE SELECT A PILL");
            }
        });
    }
}