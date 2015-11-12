package com.example.think.uihealth.view.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;

import com.example.think.uihealth.R;
import com.example.think.uihealth.app.App;
import com.example.think.uihealth.model.bean.BmobUser;
import com.example.think.uihealth.util.GetHttpImageView;
import com.example.think.uihealth.view.fragment.ForumFragment;
import com.example.think.uihealth.view.fragment.HomePageFragment;
import com.example.think.uihealth.view.fragment.MyUserInfoFragment;
import com.kermit.exutils.utils.ActivityCollector;
import com.kermit.exutils.utils.ExUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;
import it.neokree.materialnavigationdrawer.MaterialNavigationDrawer;
import it.neokree.materialnavigationdrawer.elements.MaterialSection;

/**
 * Created by Zane on 2015/9/23.
 *
 * 使用第三方库MatrialNavigationDrawer
 */
public class StartActivity extends MaterialNavigationDrawer {

    public static final String TAG = "StartActivity";

    private HomePageFragment mHomePageFragment;

    private MaterialSection section_Home;
    private MaterialSection section_Forum;
    private MaterialSection section_Question;
    private MaterialSection section_Info;

    private Boolean isAutoChange = false;
    public static final String AUTOCHANGE = "AUTOCHANGE";
    private String userName;
    private int basicColor = Color.parseColor("#33BB77");
    private SimpleDateFormat simpleDateFormat;
    private MyUserInfoFragment mUserInfoFragment;

    private ForumFragment mForumFragment;

    private BmobUser mBmobUser;
    private Bitmap changeBitmap;
    private String imageUrl;
    private BitmapDrawable changeAvatar;
    private BmobQuery<BmobUser> query;

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1:
                    setFirstAccountPhoto(changeAvatar);
                    break;
            }
        }
    };

    @Override
    public void init(Bundle bundle) {
        //获得USERNAME
        //userName = getIntent().getStringExtra(LoginActivity.LOGIN_USERNAME);
        initData();
        //将头像从BITMAP改为DRAWBLE
        //Log.i(TAG, String.valueOf(mBmobUser.getUserPhoto()));
        //setSecondAccountPhoto(getResources().getDrawable(R.drawable.defaultphoto));


        setUserEmail(simpleDateFormat.format(new Date()));

        section_Home = newSection("我的首页", R.drawable.b, mHomePageFragment)
                .setSectionColor(basicColor);
        section_Info = newSection("我的信息", R.drawable.myinfo, mUserInfoFragment)
                .setSectionColor(basicColor);
        //把它实例化


        section_Forum = newSection("我的论坛", R.drawable.forum, mForumFragment)
                .setSectionColor(basicColor);


        //添加section
        this.addSection(section_Home);
        this.addSection(section_Info);
        this.addSection(section_Forum);
    }

    @Override
    protected void onResume() {
        super.onResume();
        closeDrawer();
        String userId = mBmobUser.getObjectId();
        query.addWhereEqualTo("objectId", userId);
        query.findObjects(App.getInstance(), new FindListener<BmobUser>() {
            @Override
            public void onSuccess(List<BmobUser> list) {
                userName = list.get(0).getNickName();
                setUsername(userName + ", 欢迎您!");
                final String url = list.get(0).getUserPhoto();
                if(imageUrl != url){
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            Message message = new Message();
                            message.what = 1;
                            changeBitmap = GetHttpImageView.getHttpBitmap(url);
                            changeAvatar = new BitmapDrawable(getResources(), changeBitmap);
                            handler.sendMessage(message);
                        }
                    }).start();
                }else {
                    if(url == "") {
                        changeAvatar = (BitmapDrawable) getResources().getDrawable(R.drawable.defaultphoto);
                    }
                }
            }
            @Override
            public void onError(int i, String s) {
                ExUtils.ToastLong(s);
            }
        });
    }

    public void initData(){
        initFirstFragment(userName);
        initOtherFragment();
        simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日");
        mBmobUser = BmobUser.getCurrentUser(App.getInstance(), BmobUser.class);
        String userId = mBmobUser.getObjectId();
        query = new BmobQuery<>();
        query.addWhereEqualTo("objectId", userId);
        query.findObjects(App.getInstance(), new FindListener<BmobUser>() {
            @Override
            public void onSuccess(List<BmobUser> list) {
                userName = list.get(0).getNickName();
                setUsername(userName + ", 欢迎您!");
                imageUrl = list.get(0).getUserPhoto();
                if(imageUrl != ""){
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            Message message = new Message();
                            message.what = 1;
                            changeBitmap = GetHttpImageView.getHttpBitmap(imageUrl);
                            changeAvatar = new BitmapDrawable(getResources(), changeBitmap);
                            handler.sendMessage(message);
                        }
                    }).start();
                }else {
                    changeAvatar = (BitmapDrawable) getResources().getDrawable(R.drawable.defaultphoto);
                }
            }

            @Override
            public void onError(int i, String s) {

            }
        });
    }
    public void initFirstFragment(String userName){
        mHomePageFragment = HomePageFragment.newInstance(userName);
        mForumFragment = ForumFragment.newInstance();
    }
    public void initOtherFragment(){
        // TODO: 15/11/8 在这里获得其他子页面的碎片实例
        mUserInfoFragment = new MyUserInfoFragment();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_loginactivity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_changeuser:
                Intent intent = new Intent(StartActivity.this, LoginActivity.class);
                intent.putExtra(AUTOCHANGE, isAutoChange);

                startActivity(intent);
                ActivityCollector.getInstance().closeActivity(StartActivity.this);
                break;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) { //按下的如果是BACK，同时没有重复
            new AlertDialog.Builder(StartActivity.this)
                    .setTitle("提示")
                    .setMessage("确认退出？")
                    .setNegativeButton("否", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    })
                    .setPositiveButton("是", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                            ActivityCollector.getInstance().closeAllActivity();

                        }
                    })
                    .show();
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }
    //
    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollector.getInstance().popActivity(this);
    }
}
