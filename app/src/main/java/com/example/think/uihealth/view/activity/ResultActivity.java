package com.example.think.uihealth.view.activity;

import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;


import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.think.uihealth.R;
import com.example.think.uihealth.model.BmobUser;
import com.example.think.uihealth.model.BmobUserData;
import com.example.think.uihealth.model.Human;
import com.example.think.uihealth.strategy.Strategy;
import com.example.think.uihealth.strategy.impl.CalculateCHDStrategy;
import com.example.think.uihealth.view.fragment.ResultCompareFragment;
import com.example.think.uihealth.view.fragment.ResultPreviousFragment;
import com.gc.materialdesign.views.ProgressBarCircularIndeterminate;
import com.kermit.exutils.utils.ActivityCollector;
import com.kermit.exutils.utils.ExUtils;

import java.text.NumberFormat;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by Zane on 2015/9/23.
 */
public class ResultActivity extends AppCompatActivity {

    @Bind(R.id.toolbar_resultactivity)
    Toolbar mToolbar;

    private String userNmae;

    private ResultCompareFragment mCompareFragment;
    private ResultPreviousFragment mPreviousFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_layout);
        ButterKnife.bind(this);

        userNmae = this.getIntent().getStringExtra(LoginActivity.LOGIN_USERNAME);

        mToolbar.setTitle("");
        setSupportActionBar(mToolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityCollector.getInstance().closeActivity(ResultActivity.this);
                // saveTimes = 0;
            }
        });

        ActivityCollector.getInstance().pushActivity(this);

        mPreviousFragment = new ResultPreviousFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.framelayout_resultactivity_exchangebyfragment, mPreviousFragment);
        transaction.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_resultactivity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        switch (id) {
            case R.id.action_done:
                ActivityCollector.getInstance().closeActivity(ResultActivity.this);
                ActivityCollector.getInstance().closeActivityByName("com.example.think.uihealth.view" +
                        ".activity.MainActivity");
                Intent intent = new Intent(ResultActivity.this, StartActivity.class);
                intent.putExtra(LoginActivity.LOGIN_USERNAME, userNmae);
                startActivity(intent);
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollector.getInstance().popActivity(this);
        //saveTimes = 0;
    }
}
