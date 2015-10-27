package com.example.think.uihealth.view.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.think.uihealth.R;
import com.example.think.uihealth.model.BmobUser;
import com.example.think.uihealth.model.BmobUserData;
import com.gc.materialdesign.views.ButtonFlat;
import com.gc.materialdesign.views.ProgressBarCircularIndeterminate;
import com.kermit.exutils.utils.ActivityCollector;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by Zane on 2015/9/23.
 */
public class StartActivity extends AppCompatActivity{

    public static final String TAG = "StartActivity";

    @Bind(R.id.btn_startactivity_start)
    ButtonFlat mButton;
    @Bind(R.id.btn_startactivity_query)
    ButtonFlat mButtonQuery;
    @Bind(R.id.textview_startactivity)
    TextView mTextview;
    @Bind(R.id.toolbar_startactivity)
    Toolbar mToolbar;

    private Boolean isAutoChange = false;
    private BmobUser mUser;
    public static final String AUTOCHANGE = "AUTOCHANGE";


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_loginactivity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_layout);
        ButterKnife.bind(this);

        mToolbar.setTitle("");
        setSupportActionBar(mToolbar);
        //getSupportActionBar().setHomeButtonEnabled(true);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ActivityCollector.getInstance().pushActivity(this);

        Intent intent = this.getIntent();
        final String userName = intent.getStringExtra(LoginActivity.LOGIN_USERNAME);

        mTextview.setText("欢迎"+userName);


        //change ths test size in buttonflat



        mButtonQuery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(StartActivity.this, BeforeDataActivity.class);
                startActivity(intent1);
            }
        });

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(StartActivity.this, MainActivity.class);
                intent.putExtra(LoginActivity.LOGIN_USERNAME, userName);
                startActivity(intent);

            }
        });
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
                            ActivityCollector.getInstance().closeAllActivity();

                        }
                    })
                    .show();
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollector.getInstance().popActivity(this);
    }
}
