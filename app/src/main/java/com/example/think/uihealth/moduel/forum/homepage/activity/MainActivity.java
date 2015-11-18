package com.example.think.uihealth.moduel.forum.homepage.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.think.uihealth.R;
import com.example.think.uihealth.moduel.forum.homepage.fragment.ContentFragment;
import com.example.think.uihealth.view.activity.LoginActivity;
import com.kermit.exutils.utils.ActivityCollector;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Zane on 2015/9/23.
 */
public class MainActivity extends AppCompatActivity {

    public static final String TAG = "MainActivity";

    private static final String RESULT = "RESULT";

    @Bind(R.id.toolbar_main)
    Toolbar mToolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        ActivityCollector.getInstance().pushActivity(this);

        final String userName = this.getIntent().getStringExtra(LoginActivity.LOGIN_USERNAME);

        mToolbar.setTitle("");
        setSupportActionBar(mToolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContentFragment.getViewPager().arrowScroll(1);
            }
        });

        ContentFragment mContentFragment = new ContentFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frg_main_testcontent, mContentFragment);
        transaction.commit();

        //页面跳转
        mContentFragment.setOnLastItemClickListener(new ContentFragment.OnLastItemClickListener() {
            @Override
            public void activitySkip() {
                Intent intent = new Intent(MainActivity.this, ResultActivity.class);

                intent.putExtra(LoginActivity.LOGIN_USERNAME, userName);
                startActivity(intent);
                overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
            }
        });

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollector.getInstance().popActivity(this);
    }
}
