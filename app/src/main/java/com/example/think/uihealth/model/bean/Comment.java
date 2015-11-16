package com.example.think.uihealth.model.bean;

import cn.bmob.v3.BmobObject;

/**
 * Created by kermit on 15-11-11.
 */
public class Comment extends BmobObject{

    private BmobUser author;
    private String content;
    private String time;
    private String mForum;

    public String getForumId() {
        return mForum;
    }

    public void setForumId(String forum) {
        mForum = forum;
    }

    public BmobUser getAuthor() {
        return author;
    }

    public void setAuthor(BmobUser author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
