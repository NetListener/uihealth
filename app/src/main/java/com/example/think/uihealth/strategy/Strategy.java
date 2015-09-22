package com.example.think.uihealth.strategy;

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
 * 1 - P[s(t)]^B  t为10年男性 s(t) = 0.90015,女性为0.96246
 *
 * P的结果是10年后得冠心病的几率
 *
 * 55岁的人有16%的风险，而一个健康的人有7%的风险
 *
 *
 * CHD 冠心病
 * LDL 低密度脂蛋白
 */
public interface Strategy {


    void getValue(Object dataBean);

    void calculate();

    String getResult();
}
