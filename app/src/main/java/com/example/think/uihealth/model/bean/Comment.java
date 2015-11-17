package com.example.think.uihealth.model.bean;


import java.io.Serializable;

import cn.bmob.v3.BmobObject;

/**
 * Created by kermit on 15-11-11.
 */
public class Comment extends BmobObject implements Serializable {

    private BmobUser commentAuthor;
    private String content;
    private String time;
    private Forum mForum;

    public Forum getForum() {
        return mForum;
    }

    public void setForum(Forum forum) {
        mForum = forum;
    }

    public BmobUser getAuthor() {
        return commentAuthor;
    }

    public void setAuthor(BmobUser author) {
        this.commentAuthor = author;
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
