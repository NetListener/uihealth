package com.example.think.uihealth.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.think.uihealth.R;
import com.example.think.uihealth.view.fragment.ForumOftenListFragment;
import com.example.think.uihealth.view.fragment.WriteTopicFragment;

import butterknife.Bind;
import butterknife.ButterKnife;
/**
 * Created by kermit on 15-11-15.
 */
public class ForumOftenActivity extends AppCompatActivity
        implements WriteTopicFragment.WriteTopicFragmentUploadListener{

    @Bind(R.id.tv_forumoften_toolbar)
    TextView mTvForumoftenToolbar;
    @Bind(R.id.toolbar_forumoften)
    Toolbar mToolbar;

    private String tag;
    private WriteTopicFragment mWriteTopicFragment;
    private ForumOftenListFragment mForumOftenListFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forumoften_layout);
        ButterKnife.bind(this);

        tag = getIntent().getStringExtra("tag");
        mToolbar.setTitle("");
        mTvForumoftenToolbar.setText(tag);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mWriteTopicFragment = WriteTopicFragment.newInstance();
        mForumOftenListFragment = ForumOftenListFragment.newInstance();

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.forumoften_container, mForumOftenListFragment)
                .commit();

        initView();
    }

    private void initView() {
    }

    @Override
    protected void onResume() {
        super.onResume();
        isInActivity = true;
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    private boolean isInActivity;
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (isInActivity) {
            switch (item.getItemId()) {
                case R.id.menu_writetopic:
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.forumoften_container, mWriteTopicFragment)
                            .commit();
                    isInActivity = false;
                    break;
            }
        }
        return true;
    }

    @Override
    public void UploadForumSuccess() {

    }
}
