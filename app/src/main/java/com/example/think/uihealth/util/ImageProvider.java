package com.example.think.uihealth.util;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.MediaStore.Images.Media;
import android.support.v4.app.Fragment;

import com.kermit.exutils.utils.ExUtils;

import java.io.File;
import java.io.IOException;

/**
 * Created by Kermit on 15-8-17.
 * e-mail : wk19951231@163.com
 */
public class ImageProvider {

    private static final int REQUEST_CAMERA = 21;
    private static final int REQUEST_ALBUM = 22;
    private static final int REQUEST_NET = 23;
    private static final int REQUEST_CORP = 24;

    private Activity mActivity;
    private Fragment mFragment;
    private File dir;
    private File tempImage;
    private OnImageSelectListener mListener;

    public interface OnImageSelectListener{
        void onImageSelect();
        void onImageLoaded(Uri uri);
        void onError();
    }

    public ImageProvider(Activity activity){
        mActivity = activity;
        this.dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        this.dir.mkdir();
    }

    public ImageProvider(Fragment fragment){
        this.mFragment = fragment;
        this.dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        this.dir.mkdir();
    }

    public void getImageFromAlbum(OnImageSelectListener listener){
        this.mListener = listener;
        Intent intent = new Intent("android.intent.action.PICK");
        intent.setDataAndType(Media.EXTERNAL_CONTENT_URI, "image/*");
        if (this.mActivity == null){
            this.mFragment.startActivityForResult(intent, REQUEST_ALBUM);
        }else {
            this.mActivity.startActivityForResult(intent, REQUEST_ALBUM);
        }
    }

    public void getImageFromCamera(OnImageSelectListener listener){
        this.mListener = listener;
        this.tempImage = this.createTempImageFile();
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        intent.putExtra("output", Uri.fromFile(this.tempImage));
        if (this.mActivity == null){
            this.mFragment.startActivityForResult(intent, REQUEST_CAMERA);
        }else {
            this.mActivity.startActivityForResult(intent, REQUEST_CAMERA);
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data){
        if (resultCode == Activity.RESULT_OK){
            if (this.mListener != null){
                switch (requestCode){
                    case REQUEST_CAMERA:
                        this.mListener.onImageSelect();
                        this.mListener.onImageLoaded(Uri.fromFile(tempImage));
                        break;
                    case REQUEST_ALBUM:
                        this.mListener.onImageSelect();
                        this.mListener.onImageLoaded(data.getData());
                        break;
                    case REQUEST_CORP:
                        this.mListener.onImageSelect();
                        this.mListener.onImageLoaded(Uri.fromFile(tempImage));
                        break;
                }
            }
        }
    }

    public void cropImage(Uri uri, int width, int height, OnImageSelectListener listener){
        mListener = listener;
        this.tempImage = this.createTempImageFile();
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", true);
        intent.putExtra("scale", true);
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", width);
        intent.putExtra("outputY", height);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(tempImage));
        mActivity.startActivityForResult(intent, REQUEST_CORP);
    }

    public File createTempImageFile(){
        File file = new File(this.dir, System.currentTimeMillis() + ".jpg");
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }
}
