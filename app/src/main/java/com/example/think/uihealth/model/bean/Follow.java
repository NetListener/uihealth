package com.example.think.uihealth.model.bean;

import cn.bmob.v3.BmobObject;

/**
 * Created by Zane on 15/11/19.
 */
public class Follow extends BmobObject{

    private String followUseId;
    private String befollowUserId;

    public String getFollowUseId() {
        return followUseId;
    }

    public void setFollowUseId(String followUseId) {
        this.followUseId = followUseId;
    }

    public String getBefollowUserId() {
        return befollowUserId;
    }

    public void setBefollowUserId(String befollowUserId) {
        this.befollowUserId = befollowUserId;
    }
}
