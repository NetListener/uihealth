package com.example.think.uihealth.model.bean;

import java.util.List;

import cn.bmob.v3.BmobObject;

/**
 * Created by kermit on 15-11-11.
 */
public class Forum extends BmobObject{

    private BmobUser author;
    private String title;
    private String time;
    private String content;
    private String tag;
    private List<Comment> mComment;
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

    public List<Comment> getComment() {
        return mComment;
    }

    public void setComment(List<Comment> comment) {
        mComment = comment;
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
}
