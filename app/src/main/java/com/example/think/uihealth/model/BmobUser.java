package com.example.think.uihealth.model;



/**
 * Created by Zane on 2015/10/12.
 * Bmob的存储内容表
 */
public class BmobUser extends cn.bmob.v3.BmobUser {

    private BmobUserData bmobUserData;
    //true is man, false is female
    private Boolean Sex;


    public BmobUserData getBmobUserData() {
        return bmobUserData;
    }

    public void setBmobUserData(BmobUserData bmobUserData) {
        this.bmobUserData = bmobUserData;
    }

    public Boolean getSex() {
        return Sex;
    }

    public void setSex(Boolean sex) {
        Sex = sex;
    }
}
