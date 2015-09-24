package com.example.think.uihealth.model;

/**
 * Created by Kermit on 15-9-22.
 * e-mail : wk19951231@163.com
 */
public class Human {

    public static final String MALE = "male";
    public static final String FEMALE = "female";

    private String sex;
    private int age;
    private double totalCholesterol;
    private double HDLCholesterol;
    private double bloodPressure;
    private double SmokerValue;


    public Human(){
        sex = MALE;
        age = 20;
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
        return SmokerValue;
    }

    public void setSmokerValue(double SmokerValue) {
        this.SmokerValue = SmokerValue;
    }

}
