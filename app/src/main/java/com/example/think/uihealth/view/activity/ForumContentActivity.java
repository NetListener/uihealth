package com.example.think.uihealth.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.think.uihealth.R;
import com.example.think.uihealth.model.bean.BmobUser;
import com.example.think.uihealth.model.bean.Comment;
import com.example.think.uihealth.model.bean.Forum;
import com.example.think.uihealth.view.adapter.ForumContentAdapter;
import com.example.think.uihealth.view.fragment.ForumOftenListFragment;
import com.kermit.exutils.utils.ExUtils;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;

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
    @Bind(R.id.tv_activity_title)
    TextView mTvActivityTitle;
    @Bind(R.id.ed_activity_writecoment)
    EditText mEdActivityWritecoment;
    @Bind(R.id.tv_activity_sendcomment)
    TextView mTvActivitySendcomment;

    private ForumContentAdapter mAdapter;
    private int page = 1;
    private LinearLayoutManager mLayoutManager;

    private Forum mForum;

    private String content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forumcontent_layout);
        ButterKnife.bind(this);

        if (getIntent() != null) {
            Intent intent = getIntent();
            if (intent.getBundleExtra(ForumOftenListFragment.TAG) != null) {
                mForum = intent.getBundleExtra(ForumOftenListFragment.TAG).getParcelable("forum");
            } else if (intent.getExtras().get("") != null) {

            }
        }

        mTvActivityTitle.setText(mForum.getTitle());
        mToolbar.setTitle("");
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

    private String getComment(){
        return mEdActivityWritecoment.getText().toString().trim();
    }

    private int lastVisibleItem = 0;

    private void initView() {
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new ForumContentAdapter(this, mForum);

        mRecyclerActivityForumcontent.setHasFixedSize(true);
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
                page = 1;
            }
        });

        mTvActivitySendcomment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(getComment()))
                    return;
                content = getComment();
                Comment comment = new Comment();
                comment.setAuthor(BmobUser.getCurrentUser(ForumContentActivity.this, BmobUser.class));
                comment.setForumId(mForum.getObjectId());
                comment.setTime(ExUtils.getTime());
                comment.setContent(content);
                comment.save(ForumContentActivity.this, new SaveListener() {
                    @Override
                    public void onSuccess() {
                        ExUtils.Toast("评论成功！");
                        finish();
                    }

                    @Override
                    public void onFailure(int i, String s) {

                    }
                });
            }
        });

    }


    private void LoadMore() {
        fetchData();
        page++;
    }


    // TODO: 15-11-11 加载更多
    private void fetchData() {
        BmobQuery<Comment> query = new BmobQuery<>();
        query.addWhereEqualTo("mForum", mForum.getObjectId());

        query.setLimit(10);
        query.setSkip((page - 1) * 10);
        query.order("-time");

        query.findObjects(this, new FindListener<Comment>() {
            @Override
            public void onSuccess(List<Comment> list) {
                if (mAdapter.setComments(list)) {
                    mAdapter.notifyDataSetChanged();
                }
                mSwipeActivityForumcontent.setRefreshing(false);
            }

            @Override
            public void onError(int i, String s) {
                mSwipeActivityForumcontent.setRefreshing(false);
            }
        });
    }

}
