package com.example.think.uihealth.strategy;

/**
 * Created by Kermit on 15-9-22.
 * e-mail : wk19951231@163.com
 */
public interface Strategy {

    void setValue(Object dataBean);

    void calculate();

    String getResult();
}
