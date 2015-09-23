package com.example.think.uihealth.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;


import com.example.think.uihealth.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Zane on 2015/9/23.
 */
public class ResultActivity extends AppCompatActivity{

    @Bind(R.id.textview_resultfragment_result)
    TextView result;
    @Bind(R.id.toolbar_resultactivity)
    Toolbar mToolbar;

    private String mResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_layout);
        ButterKnife.bind(this);

        mToolbar.setTitle("");
        setSupportActionBar(mToolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_resultactivity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        switch (id){
            case R.id.action_done:
                // TODO: 2015/9/23 返回到最初的活动
                Intent intent = new Intent(ResultActivity.this, StartActivity.class);
                startActivity(intent);
                break;
        }
        
        return super.onOptionsItemSelected(item);
    }
}