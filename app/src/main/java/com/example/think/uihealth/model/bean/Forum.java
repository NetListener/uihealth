package com.example.think.uihealth.model.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobObject;

/**
 * Created by kermit on 15-11-11.
 */
public class Forum extends BmobObject implements Parcelable {

    private BmobUser author;
    private String title;
    private String time;
    private String content;
    private String tag;
    private List<String> pic;
    private int commentCount;

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public int getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(int commentCount) {
        this.commentCount = commentCount;
    }

    public List<String> getPic() {
        return pic;
    }

    public void setPic(List<String> pic) {
        this.pic = pic;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public BmobUser getAuthor() {
        return author;
    }

    public void setAuthor(BmobUser author) {
        this.author = author;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.author, flags);
        dest.writeString(this.title);
        dest.writeString(this.time);
        dest.writeString(this.content);
        dest.writeString(this.tag);
        dest.writeStringList(this.pic);
        dest.writeInt(this.commentCount);
    }

    public Forum() {
    }

    protected Forum(Parcel in) {
        this.author = in.readParcelable(BmobUser.class.getClassLoader());
        this.title = in.readString();
        this.time = in.readString();
        this.content = in.readString();
        this.tag = in.readString();
        this.pic = in.createStringArrayList();
        this.commentCount = in.readInt();
    }

    public static final Parcelable.Creator<Forum> CREATOR = new Parcelable.Creator<Forum>() {
        public Forum createFromParcel(Parcel source) {
            return new Forum(source);
        }

        public Forum[] newArray(int size) {
            return new Forum[size];
        }
    };
}
