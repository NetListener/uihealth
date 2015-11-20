package com.example.think.uihealth.model.bean;


import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by Zane on 2015/10/12.
 * Bmob的存储内容表
 */
public class BmobUser extends cn.bmob.v3.BmobUser implements Parcelable {

    private String nickName;
    private String userPhoto;
    private int Followers;
    private int Following;
    private String gender;
    private List<String> often;
    private List<String> userFollowers;

    private List<String> userFollowings;

    public List<String> getUserFollowers() {
        return userFollowers;
    }

    public void setUserFollowers(List<String> userFollowers) {
        this.userFollowers = userFollowers;
    }

    public List<String> getUserFollowings() {
        return userFollowings;
    }

    public void setUserFollowings(List<String> userFollowings) {
        this.userFollowings = userFollowings;
    }

    public List<String> getOften() {
        return often;
    }

    public void setOften(List<String> often) {
        this.often = often;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.nickName);
        dest.writeString(this.userPhoto);
        dest.writeInt(this.Followers);
        dest.writeInt(this.Following);
        dest.writeString(this.gender);
        dest.writeStringList(this.often);
    }

    public BmobUser() {
    }

    protected BmobUser(Parcel in) {
        this.nickName = in.readString();
        this.userPhoto = in.readString();
        this.Followers = in.readInt();
        this.Following = in.readInt();
        this.gender = in.readString();
        this.often = in.createStringArrayList();
    }

    public static final Parcelable.Creator<BmobUser> CREATOR = new Parcelable.Creator<BmobUser>() {
        public BmobUser createFromParcel(Parcel source) {
            return new BmobUser(source);
        }

        public BmobUser[] newArray(int size) {
            return new BmobUser[size];
        }
    };
}
