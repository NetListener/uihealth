package com.example.think.uihealth.model.bean;

import cn.bmob.v3.BmobObject;

/**
 * Created by kermit on 15-11-11.
 */
public class ForumContent extends BmobObject {

    private Forum mForum;
    private Comment mComment;

    public Forum getForum() {
        return mForum;
    }

    public void setForum(Forum forum) {
        mForum = forum;
    }

    public Comment getComment() {
        return mComment;
    }

    public void setComment(Comment comment) {
        mComment = comment;
    }
}
