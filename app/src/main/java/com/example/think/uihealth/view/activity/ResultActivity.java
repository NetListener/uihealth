package com.example.think.uihealth.view.activity;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;


import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;


import com.example.think.uihealth.R;

import com.example.think.uihealth.view.fragment.ResultCompareFragment;
import com.example.think.uihealth.view.fragment.ResultPreviousFragment;

import com.kermit.exutils.utils.ActivityCollector;


import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * Created by Zane on 2015/9/23.
 */
public class ResultActivity extends AppCompatActivity {

    @Bind(R.id.toolbar_resultactivity)
    Toolbar mToolbar;

    private String userNmae;

    private ResultCompareFragment mCompareFragment;
    private ResultPreviousFragment mPreviousFragment;
    private int countFragment = 0;

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
        final FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.framelayout_resultactivity_exchangebyfragment, mPreviousFragment);
        //添加这个碎片到返回栈
        transaction.addToBackStack(null);
        transaction.commit();
        countFragment = 1;

        mPreviousFragment.setOnCompareButtonClickListener(new ResultPreviousFragment.OnCompareButtonClickListener() {
            @Override
            public void compareButtonClick(ResultCompareFragment resultCompareFragment) {
                mCompareFragment = resultCompareFragment;
                FragmentTransaction transaction1 = getSupportFragmentManager().beginTransaction();
                transaction1.add(R.id.framelayout_resultactivity_exchangebyfragment, mCompareFragment);
                transaction1.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                transaction1.addToBackStack(null);
                countFragment = 2;
                transaction1.commit();
            }
        });

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if(countFragment == 2){
            countFragment = 1;
        }else if(countFragment == 1){
            ActivityCollector.getInstance().closeActivity(ResultActivity.this);
        }

        return super.onKeyDown(keyCode, event);
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
