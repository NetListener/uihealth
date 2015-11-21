package com.example.think.uihealth.model.bean;

import cn.bmob.v3.BmobObject;

/**
 * Created by Zane on 15/11/20.
 */
public class UserOtherAttr extends BmobObject{

    private int followers;
    private BmobUser user;

    public BmobUser getUser() {
        return user;
    }

    public void setUser(BmobUser user) {
        this.user = user;
    }

    public int getFollowers() {
        return followers;
    }

    public void setFollowers(int followers) {
        this.followers = followers;
    }
}
