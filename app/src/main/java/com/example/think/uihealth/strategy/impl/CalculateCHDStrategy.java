package com.example.think.uihealth.strategy.impl;

import com.example.think.uihealth.model.Human;
import com.example.think.uihealth.strategy.Strategy;

/**
 * Created by Kermit on 15-9-22.
 * e-mail : wk19951231@163.com
 */

/**
 * 公式L
 *
 * cholesterol 胆固醇
 *  under_160       -0.65945    高于
 *  160_to_199      0.0
 *  200_to_239      0.17692
 *  240_to_279      0.50539
 *  not_under 280   0.65713     不低于
 *
 * 下面的格式同上
 *
 * HDL-C 高密度脂蛋白胆固醇
 *  <35            0.49744
 *  35 - 44        0.24310
 *  45 - 49        0.0
 *  50 - 59        -0.05107
 *  >=60           -0.00226
 *
 * BP 血压
 *  normal         0.0
 *  high_normal    0.28320
 *  state_I_hypertension      0.52169
 *  state_II_hypertension     0.61859
 *  diabetes       0.42839    (diabetes 糖尿病)
 *  not_diabetes   0.0
 *  smoker         0.52337
 *  not_smoker     0.0
 *
 *  以上需要作为选项的内容
 *
 * 公式G
 * Male_G = 3.0975
 * Female_G = 9.92545
 *
 * 公式A
 * FunA = L - G
 *
 * 公式B
 * FunB = e^A
 *
 * 公式P
 * 1 - [s(t)]^B  t为10年男性 s(t) = 0.90015,女性为0.96246
 *
 * P的结果是10年后得冠心病的几率
 *
 * 55岁的人有16%的风险，而一个健康的人有7%的风险
 *
 *
 * CHD 冠心病
 * LDL 低密度脂蛋白
 */


//*  normal         0.0
//        *  high_normal    0.28320
//        *  state_I_hypertension      0.52169
//        *  state_II_hypertension     0.61859
//        *  diabetes       0.42839    (diabetes 糖尿病)
//        *  not_diabetes   0.0

public class CalculateCHDStrategy implements Strategy {

    public static final double L_AGE = 0.04826;

    public static final double CHOLESTEROL_UNDER_160 = -0.65945;
    public static final double CHOLESTEROL_160_TO_199 = 0.0 ;
    public static final double CHOLESTEROL_200_TO_239 = 0.17692;
    public static final double CHOLESTEROL_240_TO_279 = 0.50539;
    public static final double CHOLESTEROL_NOT_UNDER_280 = 0.65713;

    public static final double HDL_CHOLESTEROL_UNDER_35 = 0.49744;
    public static final double HDL_CHOLESTEROL_35_TO_44 = 0.24310;
    public static final double HDL_CHOLESTEROL_45_TO_49 = 0.0;
    public static final double HDL_CHOLESTEROL_50_TO_59 = -0.05107;
    public static final double HDL_CHOLESTEROL_NOT_UNDER_60 = -0.00226;

    public static final double NORMAL = 0.0;
    public static final double HIGH_NORMAL = 0.28320;
    public static final double STATE_I_HYPERTENSION = 0.52169;
    public static final double STATE_II_HYPERTENSION = 0.61859;


    public static final double DIABETES = 0.42839;
    public static final double NOT_DIABETES = 0.0;


    public static final double SMOKER = 0.52337;
    public static final double NOT_SMOKER = 0.0;

    public static final double G_MALE = 3.0975;
    public static final double G_FEMALE = 9.92545;

    public static final double P_MALE = 0.90015;
    public static final double P_FEMALE = 0.96246;


    private Human mHuman;

    @Override
    public void setValue(Object dataBean) {
        mHuman = (Human) dataBean;
    }

    @Override
    public void calculate() {

    }

    @Override
    public String getResult() {
        return FuncP() + "";
    }

    private double FuncL(){
        double chol;
        chol = L_AGE * mHuman.getAge() + mHuman.getTotalCholesterol()
                + mHuman.getHDLCholesterol() + mHuman.getBloodPressure()
                + mHuman.getSmokerValue() + mHuman.getDiabetesValue();
        return chol;
    }
    private double FuncG(){
        if (mHuman.getSex() == Human.MALE){
            return G_MALE;
        }else{
            return G_FEMALE;
        }
    }
    private double FuncA(){
        return FuncL() - FuncG();
    }
    private double FuncB(){
        return Math.pow(Math.E, FuncA());
    }
    private double FuncP(){
        if (mHuman.getSex() == Human.MALE){
            return 1- Math.pow(P_MALE, FuncB());
        }else{
            return 1- Math.pow(P_FEMALE, FuncB());
        }
    }


}
