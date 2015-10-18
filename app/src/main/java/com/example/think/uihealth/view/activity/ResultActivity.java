package com.example.think.uihealth.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.think.uihealth.R;
import com.example.think.uihealth.model.BmobUser;
import com.example.think.uihealth.model.BmobUserData;
import com.example.think.uihealth.model.Human;
import com.example.think.uihealth.strategy.Strategy;
import com.example.think.uihealth.strategy.impl.CalculateCHDStrategy;
import com.gc.materialdesign.views.ProgressBarCircularIndeterminate;
import com.kermit.exutils.utils.ActivityCollector;
import com.kermit.exutils.utils.ExUtils;

import java.text.NumberFormat;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by Zane on 2015/9/23.
 */
public class ResultActivity extends AppCompatActivity {


    @Bind(R.id.textview_resultfragment_result)
    TextView result;
    @Bind(R.id.toolbar_resultactivity)
    Toolbar mToolbar;
    @Bind(R.id.btn_resultactivity_savedata)
    Button mButton;
    @Bind(R.id.progressbar_resulatactivity)
    ProgressBarCircularIndeterminate mProgress;

    private String mResult;

    private Strategy mStrategy;

    private String userNmae;
    private BmobUser mUser;
    private BmobUserData mBmobUserData;
    private Human mHuman;

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
            }
        });


        ActivityCollector.getInstance().pushActivity(this);

        //显示结果
        mStrategy = new CalculateCHDStrategy();
        mStrategy.setValue(Human.getInstance());
        NumberFormat numberFormat = NumberFormat.getNumberInstance();
        numberFormat.setMaximumFractionDigits(2);
        result.setText(numberFormat.format((Double.parseDouble(mStrategy.getResult()) * 100)) + "%");


        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mProgress.setVisibility(View.VISIBLE);

                mHuman = Human.getInstance();

                mUser = BmobUser.getCurrentUser(ResultActivity.this, BmobUser.class);
                mBmobUserData = new BmobUserData();
                mBmobUserData.setResult((Double.parseDouble(mStrategy.getResult()) * 100));
                mBmobUserData.setAge(mHuman.getAge());
                mBmobUserData.setBloodPressure(mHuman.getBloodPressure());
                mBmobUserData.setDiabetesValue(mHuman.getDiabetesValue());
                mBmobUserData.setHDLCholesterol(mHuman.getHDLCholesterol());
                mBmobUserData.setSex(mHuman.getSex());
                mBmobUserData.setSmokerValue(mHuman.getSmokerValue());
                mBmobUserData.setTotalCholesterol(mHuman.getTotalCholesterol());
                mBmobUserData.setmUser(mUser);
                mBmobUserData.save(ResultActivity.this, new SaveListener() {
                    @Override
                    public void onSuccess() {
                        mProgress.setVisibility(View.INVISIBLE);
                        ExUtils.Toast("保存成功");
                    }

                    @Override
                    public void onFailure(int i, String s) {
                        mProgress.setVisibility(View.INVISIBLE);
                        ExUtils.Toast(s);
                    }
                });
            }
        });
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
    }
}
