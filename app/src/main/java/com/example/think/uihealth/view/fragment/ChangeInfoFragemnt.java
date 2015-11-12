package com.example.think.uihealth.view.fragment;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
<<<<<<< HEAD
<<<<<<< HEAD
=======
import android.graphics.BitmapFactory;
import android.graphics.Color;
>>>>>>> origin/dev
=======
>>>>>>> origin/dev
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Message;
<<<<<<< HEAD
<<<<<<< HEAD
=======
import android.provider.ContactsContract;
>>>>>>> origin/dev
=======
>>>>>>> origin/dev
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

<<<<<<< HEAD
<<<<<<< HEAD
=======
import com.android.volley.toolbox.ImageLoader;
>>>>>>> origin/dev
=======
>>>>>>> origin/dev
import com.bmob.BTPFileResponse;
import com.bmob.BmobProFile;
import com.bmob.btp.callback.UploadListener;
import com.example.think.uihealth.R;
import com.example.think.uihealth.app.App;
<<<<<<< HEAD
<<<<<<< HEAD
import com.example.think.uihealth.model.bean.BmobUser;
=======
import com.example.think.uihealth.model.BmobUser;
import com.example.think.uihealth.strategy.Strategy;
>>>>>>> origin/dev
=======
import com.example.think.uihealth.model.bean.BmobUser;
>>>>>>> origin/dev
import com.example.think.uihealth.util.GetHttpImageView;
import com.example.think.uihealth.util.PhotoUtils;
import com.gc.materialdesign.views.ProgressBarCircularIndeterminate;
import com.kermit.exutils.utils.ExUtils;

<<<<<<< HEAD
<<<<<<< HEAD
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
=======
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Handler;
import java.util.logging.LogRecord;
>>>>>>> origin/dev
=======
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
>>>>>>> origin/dev

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.bmob.v3.BmobQuery;
<<<<<<< HEAD
<<<<<<< HEAD
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;

=======
import cn.bmob.v3.BmobRealTimeData;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.listener.ValueEventListener;
>>>>>>> origin/dev
=======
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;
>>>>>>> origin/dev

/**
 * Created by Zane on 15/11/9.
 */
public class ChangeInfoFragemnt extends Fragment {


    public static final String NICKENAME = "NICKENAME";
    public static final String IMAGEVIEW = "IMAGEVIEW";
    public static final String FOLLOWERS = "FOLLOWERS";
    public static final String FOLLOWING = "FOLLOWING";
    public static final String TAG = "ChangeInfoFragment";
    public static final int UPLOADAVATAR_CAMERA = 1;
    public static final int UPLOADAVATAR_LOCATION = 2;
    public static final int UPLOADAVATAR_CROP = 3;


    private BmobUser mUser;
    private String nickName_change;
    private String imageUrl_change;
    private int followers_change;
    private int following_change;
    private BmobQuery<BmobUser> query;
    private PopupWindow popupWindow;
    private View viewParent;
    private String photo_filePath = "";
    private String finalPath;
    //图片上传之后返回的唯一文件名。
    private String fileName;
    private String finalUrl;
    private Uri imgUri;

    @Bind(R.id.imageview_changinfofragment_photo)
    ImageView imageviewChanginfofragmentPhoto;
    @Bind(R.id.layout_changeuserphoto)
    LinearLayout layoutChangeuserphoto;
    @Bind(R.id.textview_changeinfofragment_username)
    TextView textviewChangeinfofragmentUsername;
    @Bind(R.id.layout_changeusernickename)
    LinearLayout layoutChangeusernickename;
    @Bind(R.id.progressbar_changinfofragment)
    ProgressBarCircularIndeterminate progressbarChanginfofragment;
    private boolean isFromCamera = false;

    //获取网络图片
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            Message message = new Message();
            message.what = 1;
            message.obj = GetHttpImageView.getHttpBitmap(finalUrl);
            handle.sendMessage(message);
        }
    };
    android.os.Handler handle = new android.os.Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1:
                    imageviewChanginfofragmentPhoto.setImageBitmap((Bitmap) msg.obj);

                    break;
            }
        }
    };


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    public static ChangeInfoFragemnt newInstance(String imageUrl, String nickeName, int followers,
                                                 int following) {

        ChangeInfoFragemnt changeInfoFragemnt = new ChangeInfoFragemnt();
        Bundle bundle = new Bundle();
        bundle.putString(NICKENAME, nickeName);
        bundle.putString(IMAGEVIEW, imageUrl);
        bundle.putInt(FOLLOWERS, followers);
        bundle.putInt(FOLLOWING, following);

        changeInfoFragemnt.setArguments(bundle);
        return changeInfoFragemnt;

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        viewParent = inflater.inflate(R.layout.fragment_changinfo_layout, container, false);
        ButterKnife.bind(this, viewParent);

        nickName_change = getArguments().getString(NICKENAME);
        imageUrl_change = getArguments().getString(IMAGEVIEW);
        followers_change = getArguments().getInt(FOLLOWERS);
        following_change = getArguments().getInt(FOLLOWING);

        initDate(imageUrl_change, nickName_change, followers_change, following_change);


        //改变昵称的接口调用
        layoutChangeusernickename.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ChangeNicknameFragment changeNicknameFragment = new ChangeNicknameFragment();
                FragmentTransaction transaction1 = getActivity().getSupportFragmentManager().beginTransaction();
                transaction1.replace(R.id.fragment_changinfoactivity, changeNicknameFragment);
                transaction1.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                transaction1.addToBackStack(null);
                transaction1.commit();

            }
        });
        //改变头像的点击事件
        layoutChangeuserphoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAvatarPop();
<<<<<<< HEAD
=======
                //viewParent.setBackgroundColor(getResources().getColor(R.color.material_blue_grey_800));
>>>>>>> origin/dev
            }
        });

//        bmobRealTimeData = new BmobRealTimeData();
//
//        bmobRealTimeData.start(getActivity(), new ValueEventListener() {
//            @Override
//            public void onConnectCompleted() {
//                ExUtils.Toast("连接成功");
//                Log.i(TAG, "hehe");
//                if(bmobRealTimeData.isConnected()){
//                    bmobRealTimeData.subRowDelete("_User", mUser.getObjectId());
//                }else {
//                    Log.i(TAG, "wrong");
//                }
//            }
//
//            @Override
//            public void onDataChange(JSONObject jsonObject) {
//                try {
//                    if(BmobRealTimeData.ACTION_UPDATETABLE.equals(jsonObject.optString("action"))) {
//                        nickName_change = jsonObject.getString("nickName");
//                        imageUrl_change = jsonObject.getString("userPhoto");
//                        followers_change = jsonObject.getInt("Followers");
//                        following_change = jsonObject.getInt("Following");
//                    }
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//                Log.i(TAG, String.valueOf(jsonObject));
//
//            }
//        });
        return viewParent;
    }
    @Override
    public void onStart() {
        super.onStart();
        //mUser = BmobUser.getCurrentUser(getActivity(), BmobUser.class);
        String userId = mUser.getObjectId();
        query.addWhereEqualTo("objectId", userId);
        query.findObjects(App.getInstance(), new FindListener<BmobUser>() {
            @Override
            public void onSuccess(List<BmobUser> list) {
                Log.i(TAG, String.valueOf(list));
                if (!list.equals(null)) {
                    textviewChangeinfofragmentUsername.setText(list.get(0).getNickName());
                    imageUrl_change = list.get(0).getUserPhoto();
                    if(imageUrl_change != finalUrl){
                        //imageviewChanginfofragmentPhoto.setImageBitmap(GetHttpImageView.getHttpBitmap(imageUrl_change));
                        finalUrl = imageUrl_change;
                        new Thread(runnable).start();
                    }else {
                        if(imageUrl_change == "") {
                            imageviewChanginfofragmentPhoto.setImageResource(R.drawable.defaultphoto);
                        }
                    }
                } else {
                    ExUtils.ToastLong("用户查询失败");
                }
            }
            @Override
            public void onError(int i, String s) {
                ExUtils.ToastLong(s);
            }
        });
    }

    public void initDate(String imageUrl, String nickName, int followers, int following) {
        textviewChangeinfofragmentUsername.setText(nickName);
        mUser = BmobUser.getCurrentUser(getActivity(), BmobUser.class);
        query = new BmobQuery<BmobUser>();
        if(imageUrl != ""){
            //imageviewChanginfofragmentPhoto.setImageBitmap(GetHttpImageView.getHttpBitmap(imageUrl));
            finalUrl = imageUrl;
            new Thread(runnable).start();
        }
        else {
            imageviewChanginfofragmentPhoto.setImageResource(R.drawable.defaultphoto);
        }
    }

    //设置选择框
    public void setPopupWindow(View view){
        popupWindow = new PopupWindow(view, ExUtils.getScreenWidth(), 900);
        popupWindow.setTouchInterceptor(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_OUTSIDE){
                    popupWindow.dismiss();
                    return false;
                }
                return false;
            }
        });

        popupWindow.setWidth(WindowManager.LayoutParams.MATCH_PARENT);
        popupWindow.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
        popupWindow.setTouchable(true);
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.setAnimationStyle(R.style.Animation_AppCompat_DropDownUp);
        popupWindow.showAtLocation(viewParent, Gravity.BOTTOM, 0, 0);

    }
    //显示选择框并添加逻辑
    public void showAvatarPop(){
        RelativeLayout layout_photo;
        RelativeLayout layout_choose;
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.pop_choose_layout, null);
        layout_choose = (RelativeLayout) view.findViewById(R.id.layout_choose);
        layout_photo = (RelativeLayout) view.findViewById(R.id.layout_photo);

        //添加监听逻辑
        layout_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "选择拍照");
                //设置一个存储拍照相片的文件路径,存储路径＋avatar
//                File dir = new File(Environment.getExternalStorageDirectory() + "/avatar/");
//                if (!dir.exists()) {
//                    dir.mkdirs();
//                }
//                File file = new File(dir, new SimpleDateFormat("yyMMddHHmmss").format(new Date()));
//                //获得文件的绝对路径
//                photo_filePath = file.getAbsolutePath();
//
//                ContentValues values = new ContentValues();
//                //获得图片文件的URI
//                Uri imgUri = Uri.fromFile(file);
                String fileName = new SimpleDateFormat("yyMMddHHmmss").format(new Date());
                //在HASHMAP中存文件名
                ContentValues values = new ContentValues();
                values.put(MediaStore.Images.Media.TITLE, fileName);
                //获得URI
                imgUri = getActivity().getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
                //跳转系统的拍照功能,传递图片存储的文件地址的URI
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, imgUri);
                startActivityForResult(intent, UPLOADAVATAR_CAMERA);

            }
        });

        layout_choose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "选择相册");

                //跳转相册
                Intent intent = new Intent(Intent.ACTION_PICK, null);
                //设置打开的多媒体类型(图片)
                intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                startActivityForResult(intent, UPLOADAVATAR_LOCATION);

            }
        });

        setPopupWindow(view);
    }

    //获得从相机或者相册的数据
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == getActivity().RESULT_OK){
            switch (requestCode){
                case UPLOADAVATAR_CAMERA:
                    Uri uri = null;
                    if(data != null &&data.getData() != null){
                        uri = data.getData();
                    }
                    if(uri == null){
                        if(imgUri != null){
                            uri = imgUri;
                        }
                    }
                    if(!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
                        ExUtils.Toast("SDK不可用!");
                        return;
                    }
                    isFromCamera = true;
                    //进行剪裁
                    startImageCrop(uri, 200, 200);
                    break;
                case UPLOADAVATAR_LOCATION:
                    if(!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
                        ExUtils.Toast("SDK不可用!");
                        return;
                    }
                    isFromCamera = false;
                    //获得返回的选取的照片的地址URI
                    Uri uri_location = data.getData();
                    startImageCrop(uri_location, 200, 200);
                    break;
                case UPLOADAVATAR_CROP:
                    if (data == null){
                        return;
                    }
                    //保存头像
                    savaCropAvator(data);
                    //初始化图片存储文件的路径
                    photo_filePath = "";
                    //上传头像
                    uploadAvatar();

                    break;
            }
        }
    }

    public void uploadAvatar(){
        progressbarChanginfofragment.setVisibility(View.VISIBLE);
        BTPFileResponse response = BmobProFile.getInstance(App.getInstance())
                .upload(finalPath, new UploadListener() {
                    @Override
                    public void onSuccess(String fileName2, String url, BmobFile bmobFile) {
                        fileName = fileName2;

                        String url2 = BmobProFile.getInstance(App.getInstance())
                                .signURL(fileName2, url, "e982a1ddd094624cb82bd7feb7163f91",0 ,null);
                        updateUserAvatar(url2);
                    }

                    @Override
                    public void onProgress(int i) {

                    }

                    @Override
                    public void onError(int i, String s) {

                    }
                });
    }
    public void updateUserAvatar(final String url){
        BmobUser newUser = new BmobUser();
        newUser.setUserPhoto(url);
        mUser = BmobUser.getCurrentUser(App.getInstance(), BmobUser.class);
        newUser.update(App.getInstance(), mUser.getObjectId(), new UpdateListener() {
            @Override
            public void onSuccess() {
                ExUtils.Toast("更新头像成功");
                //获取图片
                finalUrl = url;
                new Thread(runnable).start();
                progressbarChanginfofragment.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onFailure(int i, String s) {
                ExUtils.Toast(s);
            }
        });
    }

    public void savaCropAvator(Intent data){
        Bundle bundle = data.getExtras();
        if(bundle != null){
            //获得返回的最终剪切好的Bitmap类型的数据
            Bitmap bitmap = bundle.getParcelable("data");
            if(bitmap != null){
                //改图片哇
                //imageviewChanginfofragmentPhoto.setImageBitmap(bitmap);
                //保存图片哇(png格式)
                String fileName = new SimpleDateFormat("yyMMddHHmmss")
                        .format(new Date()) + ".png";
                String fileDir = Environment.getExternalStorageDirectory() + "/lastavatar/";
                finalPath = fileDir+fileName;
                //存储图片
                PhotoUtils.savaBitmap(fileDir, fileName, bitmap);
            }
            if(bitmap!=null && bitmap.isRecycled()){
                bitmap.recycle();
            }


        }
    }
    //剪切图片
    public void startImageCrop(Uri uri, int outputX, int outputY){
        Intent intent = null;
        if(isFromCamera){

            intent = new Intent("com.android.camera.action.CROP");
        } else {
            intent = new Intent(Intent.ACTION_GET_CONTENT, null);
        }

        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", outputX);
        intent.putExtra("outputY", outputY);
        intent.putExtra("scale", true);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        intent.putExtra("return-data", true);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        intent.putExtra("noFaceDetection", true);
        startActivityForResult(intent, UPLOADAVATAR_CROP);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }
}
