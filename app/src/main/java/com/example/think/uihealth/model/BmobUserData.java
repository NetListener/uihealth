package com.example.think.uihealth.model;

import java.io.Serializable;

import cn.bmob.v3.BmobObject;

/**
 * Created by Zane on 2015/10/12.
 */
public class BmobUserData extends BmobObject implements Serializable{

    private String sex;
    private String age;
    private String totalCholesterol;
    private String HDLCholesterol;
    private String bloodPressure;
    private String smokerValue;
    private String diabetesValue;
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

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getTotalCholesterol() {
        return totalCholesterol;
    }

    public void setTotalCholesterol(String totalCholesterol) {
        this.totalCholesterol = totalCholesterol;
    }

    public String getHDLCholesterol() {
        return HDLCholesterol;
    }

    public void setHDLCholesterol(String HDLCholesterol) {
        this.HDLCholesterol = HDLCholesterol;
    }

    public String getBloodPressure() {
        return bloodPressure;
    }

    public void setBloodPressure(String bloodPressure) {
        this.bloodPressure = bloodPressure;
    }

    public String getSmokerValue() {
        return smokerValue;
    }

    public void setSmokerValue(String smokerValue) {
        this.smokerValue = smokerValue;
    }

    public String getDiabetesValue() {
        return diabetesValue;
    }

    public void setDiabetesValue(String diabetesValue) {
        this.diabetesValue = diabetesValue;
    }

    public double getResult() {
        return Result;
    }

    public void setResult(double result) {
        Result = result;
    }
}
