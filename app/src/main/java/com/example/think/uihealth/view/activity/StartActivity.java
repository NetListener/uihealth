package com.example.think.uihealth.view.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.think.uihealth.R;
import com.example.think.uihealth.model.BmobUser;
import com.example.think.uihealth.view.fragment.HomePageFragment;
import com.kermit.exutils.utils.ActivityCollector;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.Bind;
import butterknife.ButterKnife;
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

    @Override
    public void init(Bundle bundle) {

        closeDrawer();
        //获得USERNAME
        userName = getIntent().getStringExtra(LoginActivity.LOGIN_USERNAME);
        initData(userName);

        //设置
        setFirstAccountPhoto(getResources().getDrawable(R.drawable.ic_launcher));
        setUsername(userName + ", 欢迎您!");
        setUserEmail(simpleDateFormat.format(new Date()));
        section_Home = newSection("我的首页", R.drawable.ic_launcher, mHomePageFragment)
                .setSectionColor(basicColor);


        //添加section
        this.addSection(section_Home);
    }

    public void initData(String userName){
        initFirstFragment(userName);
        initOtherFragment();
        simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日");

    }
    public void initFirstFragment(String userName){
        mHomePageFragment = HomePageFragment.newInstance(userName);
    }
    public void initOtherFragment(){
        // TODO: 15/11/8 在这里获得其他子页面的碎片实例
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
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_start_layout);
//        ButterKnife.bind(this);
//
//        mToolbar.setTitle("");
//        setSupportActionBar(mToolbar);
//        //getSupportActionBar().setHomeButtonEnabled(true);
//        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//
//        ActivityCollector.getInstance().pushActivity(this);
//
//        Intent intent = this.getIntent();
//        final String userName = intent.getStringExtra(LoginActivity.LOGIN_USERNAME);
//
//        mTextview.setText("欢迎" + userName);
//
//
//
//        //change ths test size in buttonflat
//
//
////        mButtonQuery.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View v) {
////                Intent intent1 = new Intent(StartActivity.this, BeforeDataActivity.class);
////                startActivity(intent1);
////            }
////        });
////
////        mButton.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View v) {
////
////                Intent intent = new Intent(StartActivity.this, MainActivity.class);
////                intent.putExtra(LoginActivity.LOGIN_USERNAME, userName);
////                startActivity(intent);
////
////            }
////        });
//    }
//
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
