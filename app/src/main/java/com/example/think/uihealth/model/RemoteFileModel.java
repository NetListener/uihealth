package com.example.think.uihealth.model;

import android.content.Context;

import com.example.think.uihealth.app.App;

import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.listener.UploadFileListener;

/**
 * Created by Kermit on 15-8-19.
 * e-mail : wk19951231@163.com
 */
public class RemoteFileModel {

    private static final String TAG = "RemoteFileModel";

    private static RemoteFileModel remoteFileModel;
    private Context mContext;

    private RemoteFileModel(){
        mContext = App.getInstance();
    }

    public static RemoteFileModel getInstance(){
        if (remoteFileModel == null){
            remoteFileModel = new RemoteFileModel();
        }
        return remoteFileModel;
    }

    public void upload(BmobFile bmobFile, UploadFileListener listener){
        bmobFile.uploadblock(mContext, listener);
    }
}
