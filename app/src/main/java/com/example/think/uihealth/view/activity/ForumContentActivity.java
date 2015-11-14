package com.example.think.uihealth.view.activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.think.uihealth.R;
import com.example.think.uihealth.model.bean.ForumContent;
import com.example.think.uihealth.view.adapter.ForumContentAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by kermit on 15-11-11.
 */
public class ForumContentActivity extends AppCompatActivity {

    @Bind(R.id.recycler_activity_forumcontent)
    RecyclerView mRecyclerActivityForumcontent;
    @Bind(R.id.swipe_activity_forumcontent)
    SwipeRefreshLayout mSwipeActivityForumcontent;
    @Bind(R.id.toolbar_forumcontent)
    Toolbar mToolbar;


    private ForumContentAdapter mAdapter;
    private int page = 1;
    private LinearLayoutManager mLayoutManager;
    private List<ForumContent> mContents;
    private String tag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forumcontent_layout);
        ButterKnife.bind(this);

        tag = getIntent().getStringExtra("tag");

        mToolbar.setTitle(tag);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        initView();
        fetchData();
    }

    private int lastVisibleItem = 0;

    private void initView() {
        mContents = new ArrayList<>();
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new ForumContentAdapter(this, mContents);
        mRecyclerActivityForumcontent.setLayoutManager(mLayoutManager);
        mRecyclerActivityForumcontent.setAdapter(mAdapter);
        mRecyclerActivityForumcontent.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                lastVisibleItem = mLayoutManager.findLastVisibleItemPosition();
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE
                        && lastVisibleItem + 1 == mAdapter.getItemCount()) {
                    LoadMore();
                }
            }
        });

        mSwipeActivityForumcontent.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                fetchData();
            }
        });
    }


    private void LoadMore() {
        fetchData();
        page++;
    }


    // TODO: 15-11-11 加载更多
    private void fetchData() {
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                BmobQuery<ForumContent> query = new BmobQuery<>();
                query.include("author");
                query.setLimit(5);
                query.setSkip((page - 1) * 5);
                query.order("-time");
                query.findObjects(ForumContentActivity.this, new FindListener<ForumContent>() {
                    @Override
                    public void onSuccess(List<ForumContent> list) {
                        mAdapter.addData(list);
                    }

                    @Override
                    public void onError(int i, String s) {

                    }
                });
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

        }

        return true;
    }
}
