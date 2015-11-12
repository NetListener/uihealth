package com.example.think.uihealth.model.bean;


import android.graphics.Bitmap;

import java.net.URL;

/**
 * Created by Zane on 2015/10/12.
 * Bmob的存储内容表
 */
public class BmobUser extends cn.bmob.v3.BmobUser {

    private String nickName;
    private String userPhoto;
    private int Followers;
    private int Following;


    public String getUserPhoto() {
        return userPhoto;
    }

    public void setUserPhoto(String userPhoto) {
        this.userPhoto = userPhoto;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public int getFollowers() {
        return Followers;
    }

    public void setFollowers(int followers) {
        Followers = followers;
    }

    public int getFollowing() {
        return Following;
    }

    public void setFollowing(int following) {
        Following = following;
    }
}
