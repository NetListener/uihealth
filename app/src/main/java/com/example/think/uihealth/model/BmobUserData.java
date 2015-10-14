package com.example.think.uihealth.model;

/**
 * Created by Zane on 2015/10/12.
 */
public class BmobUserData {

    private String sex;
    private int age;
    private double totalCholesterol;
    private double HDLCholesterol;
    private double bloodPressure;
    private double smokerValue;
    private double diabetesValue;
    private int Result;

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

    public int getResult() {
        return Result;
    }

    public void setResult(int result) {
        Result = result;
    }
}
