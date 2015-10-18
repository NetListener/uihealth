package com.example.think.uihealth.model;

import cn.bmob.v3.BmobObject;

/**
 * Created by Zane on 2015/10/12.
 */
public class BmobUserData extends BmobObject{

    private String sex;
    private int age;
    private double totalCholesterol;
    private double HDLCholesterol;
    private double bloodPressure;
    private double smokerValue;
    private double diabetesValue;
    private double Result;

    private BmobUser mUser;//一条数据只能对应一个用户，一对一关系, Pointer type

    public BmobUser getmUser() {
        return mUser;
    }

    public void setmUser(BmobUser mUser) {
        this.mUser = mUser;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public double getTotalCholesterol() {
        return totalCholesterol;
    }

    public void setTotalCholesterol(double totalCholesterol) {
        this.totalCholesterol = totalCholesterol;
    }

    public double getHDLCholesterol() {
        return HDLCholesterol;
    }

    public void setHDLCholesterol(double HDLCholesterol) {
        this.HDLCholesterol = HDLCholesterol;
    }

    public double getBloodPressure() {
        return bloodPressure;
    }

    public void setBloodPressure(double bloodPressure) {
        this.bloodPressure = bloodPressure;
    }

    public double getSmokerValue() {
        return smokerValue;
    }

    public void setSmokerValue(double smokerValue) {
        this.smokerValue = smokerValue;
    }

    public double getDiabetesValue() {
        return diabetesValue;
    }

    public void setDiabetesValue(double diabetesValue) {
        this.diabetesValue = diabetesValue;
    }

    public double getResult() {
        return Result;
    }

    public void setResult(double result) {
        Result = result;
    }
}
