package com.example.project;

import java.util.ArrayList;

public class drug {
    public drug(){
        times=new ArrayList<>();
        pmOrAm=new ArrayList<>();
        this.status=false;
        this.drugTakenTimes=0;
    }
    private ArrayList<String>pmOrAm;
    public void addPMorAM(String pmOrAm){
        this.pmOrAm.add(pmOrAm);
    }
    public String getPMorAM(int num){
        return pmOrAm.get(num);
    }
    private String name;
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    private float portion;
    public float getPortion() {
        return portion;
    }
    public void setPortion(float portion) {
        this.portion = portion;
    }

    private int numPerDay;
    public int getNumPerDay() {
        return numPerDay;
    }
    public void setNumPerDay(int numPerDay) {
        this.numPerDay = numPerDay;
    }

    private int startTime;
    public int getStartTime(){return startTime;}
    public void setStartTime(int startTime){this.startTime=startTime;}

    private ArrayList<Integer>times;
    public void addTime(int num){
        times.add(num);
    }
    public int getTimes(int num){
        return this.times.get(num);
    }
    private int tabletNum;
    public int getTablet() {
        return tabletNum;
    }
    public void setTablet(int tabletNum) {
        this.tabletNum = tabletNum;
    }

    private boolean status;
    private int drugTakenTimes;

    public boolean getStatus(){
        return drugTakenTimes==tabletNum;
    }
    public void takeDrug(){
        drugTakenTimes++;
    }
}
