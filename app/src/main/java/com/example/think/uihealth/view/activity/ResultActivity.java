package com.example.think.uihealth.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.think.uihealth.R;
import com.example.think.uihealth.model.Human;
import com.example.think.uihealth.strategy.Strategy;
import com.example.think.uihealth.strategy.impl.CalculateCHDStrategy;
import com.example.think.uihealth.view.fragment.ContentFragment;
import com.kermit.exutils.utils.ActivityCollector;
import com.kermit.exutils.utils.ExUtils;

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

    private Strategy mStrategy;

    private String userNmae;

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

        mStrategy = new CalculateCHDStrategy();
        mStrategy.setValue(Human.getInstance());
        result.setText((int)(Double.parseDouble(mStrategy.getResult()) * 100) + "%");
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
