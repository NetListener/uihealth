package com.example.think.uihealth.util;

import android.graphics.Bitmap;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by Zane on 15/11/11.
 */
public class PhotoUtils {

    //保存图片
    public static void savaBitmap(String dir, String fileName, Bitmap bitmap){
        try {
            File file = new File(dir, fileName);
            if(file.exists()){
                file.delete();
            }else {
                new File(dir).mkdirs();
            }
            file.createNewFile();
            BufferedOutputStream os = new BufferedOutputStream(new FileOutputStream(file));
            //生成流
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, os);
            os.flush();
            os.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void SaveBitmap(Bitmap bitmap, String path){
        File file = new File(path);
        try {
            if (!file.exists()) {
                new File(path.substring(0, path.lastIndexOf('/'))).mkdirs();
            }else{
                file.delete();
            }
            file.createNewFile();
            BufferedOutputStream os = new BufferedOutputStream(new FileOutputStream(file));
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, os);
            os.flush();
            os.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
