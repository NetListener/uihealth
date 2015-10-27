package com.example.think.uihealth.model;

/**
 * Created by Kermit on 15-9-22.
 * e-mail : wk19951231@163.com
 */
public class Human {

    public static final String MALE = "male";
    public static final String FEMALE = "female";

    private String sex;
    private String sexBmob;
    private int age;
    private String ageBmob;
    private double totalCholesterol;
    private String totalCholesterolBmob;
    private double HDLCholesterol;
    private String HDLCholesterolBmob;
    private double bloodPressure;
    private String bloodPressureBmob;
    private double smokerValue;
    private String smolerValueBmob;
    private double diabetesValue;
    private String diabetesValueBmob;

    public Human(){
        sex = MALE;
        age = 20;
    }

    public static class HumanHolder{
        private static final Human instance = new Human();
    }

    public String getSexBmob() {
        return sexBmob;
    }

    public void setSexBmob(String sexBmob) {
        this.sexBmob = sexBmob;
    }

    public String getAgeBmob() {
        return ageBmob;
    }

    public void setAgeBmob(String ageBmob) {
        this.ageBmob = ageBmob;
    }

    public String getTotalCholesterolBmob() {
        return totalCholesterolBmob;
    }

    public void setTotalCholesterolBmob(String totalCholesterolBmob) {
        this.totalCholesterolBmob = totalCholesterolBmob;
    }

    public String getHDLCholesterolBmob() {
        return HDLCholesterolBmob;
    }

    public void setHDLCholesterolBmob(String HDLCholesterolBmob) {
        this.HDLCholesterolBmob = HDLCholesterolBmob;
    }

    public String getSmolerValueBmob() {
        return smolerValueBmob;
    }

    public void setSmolerValueBmob(String smolerValueBmob) {
        this.smolerValueBmob = smolerValueBmob;
    }

    public String getBloodPressureBmob() {
        return bloodPressureBmob;
    }

    public void setBloodPressureBmob(String bloodPressureBmob) {
        this.bloodPressureBmob = bloodPressureBmob;
    }

    public String getDiabetesValueBmob() {
        return diabetesValueBmob;
    }

    public void setDiabetesValueBmob(String diabetesValueBmob) {
        this.diabetesValueBmob = diabetesValueBmob;
    }

    public static Human getInstance(){
        return HumanHolder.instance;
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

    public void setSmokerValue(double SmokerValue) {
        this.smokerValue = SmokerValue;
    }

    public double getDiabetesValue() {
        return diabetesValue;
    }

    public void setDiabetesValue(double diabetesValue) {
        this.diabetesValue = diabetesValue;
    }
}
