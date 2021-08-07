package com.example.project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class dueList extends AppCompatActivity {
    Calendar rightNow = Calendar.getInstance();
    ListView lv ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_due_list);
        loadFromDB();
        Button CLEAR=(Button)findViewById(R.id.button9);
        CLEAR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lv = (ListView) findViewById(R.id.dueList);
                ArrayList<String> dues=new ArrayList<>();
                dues.add("Done");
                ArrayAdapter<String> adapter = new ArrayAdapter<>(getBaseContext(),
                        android.R.layout.simple_list_item_1, dues);

                lv.setAdapter(adapter);
            }
        });
    }
    public void loadFromDB(){
        lv = (ListView) findViewById(R.id.dueList);
        final drugDataBase drugsDB;
        drugsDB=new drugDataBase(getApplicationContext());
        final ArrayList<drug> drugs=new ArrayList<drug>();
        final ArrayList<String>users=new ArrayList<>();
        Cursor cursor=drugsDB.getAllDrugs();
        try{

            while(!cursor.isAfterLast()) {
                drug d = new drug();
                users.add(cursor.getString(0));
                d.setName(cursor.getString(1));
                d.setPortion(Float.parseFloat(cursor.getString(2)));
                d.setNumPerDay(Integer.parseInt(cursor.getString(3)));
                d.setStartTime(Integer.parseInt(cursor.getString(4)));

                    try{
                        if (cursor.getInt(3) == 1) {
                            d.addTime(cursor.getInt(4));
                            d.addPMorAM("PM");
                        } else if (cursor.getInt(3) == 2) {
                            d.addTime(cursor.getInt(4));
                            d.addPMorAM("PM");
                            d.addTime(cursor.getInt(4));
                            d.addPMorAM("PM");
                        } else if (cursor.getInt(3) == 3) {
                            d.addTime(cursor.getInt(4));
                            d.addPMorAM("PM");
                            if (cursor.getInt(4) < 4) {
                                d.addTime(cursor.getInt(4) + 8);
                                d.addPMorAM("PM");
                                d.addTime(cursor.getInt(4) - 4);
                                d.addPMorAM("AM");
                            } else if (cursor.getInt(4) == 4) {
                                d.addTime(cursor.getInt(4) + 8);
                                d.addPMorAM("AM");
                                d.addTime(cursor.getInt(4) + 4);
                                d.addPMorAM("AM");
                            } else if (cursor.getInt(4) <= 8) {
                                d.addTime(cursor.getInt(4) - 4);
                                d.addPMorAM("AM");
                                d.addTime(cursor.getInt(4) + 4);
                                d.addPMorAM("AM");
                            } else if (cursor.getInt(4) <= 12) {
                                d.addTime(cursor.getInt(4) - 4);
                                d.addPMorAM("AM");
                                d.addTime(cursor.getInt(4) - 8);
                                d.addPMorAM("AM");
                            }
                        }
                    }catch(Exception e){
                            Toast.makeText(getApplicationContext(), "problem in 1 of 1 boss", Toast.LENGTH_LONG).show();
                    }

                drugs.add(d);
                cursor.moveToNext();
            }
            }catch(Exception e){
                Toast.makeText(getApplicationContext(), "problem in 1 boss", Toast.LENGTH_LONG).show();
            }
        ArrayList<String> dues=new ArrayList<>();
        try{
            for(int i =0;i<drugs.size();i++){
                for(int j=0;j<drugs.get(i).getNumPerDay();j++){
                    if(drugs.get(i).getPMorAM(j).equals("AM")){
                        if(drugs.get(i).getTimes(j)==12){
                            dues.add(drugs.get(i).getName()+" Time : "+"12"+" AM");
                        }else if(drugs.get(i).getTimes(j)<= (rightNow.getTime().getHours())){

                            dues.add(drugs.get(i).getName()+" Time : "+drugs.get(i).getTimes(j)+" AM");

                        }
                    }else{
                        if(drugs.get(i).getTimes(j)+12<= (rightNow.getTime().getHours())) {
                            dues.add(drugs.get(i).getName() + " Time : " + drugs.get(i).getTimes(j)+" PM");
                        }
                    }
                }
            }
        }catch(Exception e){
            Toast.makeText(getApplicationContext(), "problem here boss", Toast.LENGTH_LONG).show();
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getBaseContext(),
                android.R.layout.simple_list_item_1, dues);

        lv.setAdapter(adapter);

    }
}
