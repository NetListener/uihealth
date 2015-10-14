package com.example.think.uihealth.config;

import java.util.ArrayList;

/**
 * Created by Zane on 2015/9/22.
 * 每项测试的常量信息
 */
public class TestContent {

    public final static String[] TITLES = {"性别?", "年龄?", "总胆固醇含量?", "吸烟?",
    "高密度脂蛋白?", "血压情况", "是否糖尿病？"};

    public final static ArrayList<String[]> CONTENTS = new ArrayList<>();

    public final static String[] GENDER = {"男", "女"};

    public final static String[] AGE = {"20-34", "35-39", "40-44", "45-49", "50-54",
    "55-59", "60-64", "65-69", "70-74", "75-79"};

    //胆固醇
    public final static String[] TOTAL_CHOLESTEROL = {"< 160 mg/dl", "160-199 mg/dl", "200-239 mg/dl",
    "240-279 mg/dl", "> 280 mg/dl"};

    public final static String[] SMOKER = {"是", "否"};

    //高密度脂蛋白
    public final static String[] HDL = {"< 35 mg/dl", "35-44 mg/dl", "45-49 mg/dl", "50-59 mg/dl",
    ">= 60 mg/dl"};

    //是否使用高血压药物
    public final static String[] HYPERTENSION_MEDITION = {"正常", "血压偏高", "高血压I", "高血压II"};

    //心脏的收缩压
    public final static String[] SYSTOLIC_BLOOD_PRESURE = {"是", "不是"};


    public static ArrayList<String[]> getContents(){

        CONTENTS.add(GENDER);
        CONTENTS.add(AGE);
        CONTENTS.add(TOTAL_CHOLESTEROL);
        CONTENTS.add(SMOKER);
        CONTENTS.add(HDL);
        CONTENTS.add(HYPERTENSION_MEDITION);
        CONTENTS.add(SYSTOLIC_BLOOD_PRESURE);

        return CONTENTS;
    }
}
