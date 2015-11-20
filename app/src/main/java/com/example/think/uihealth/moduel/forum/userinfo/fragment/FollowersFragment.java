package com.example.think.uihealth.moduel.forum.userinfo.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.think.uihealth.R;
import com.example.think.uihealth.app.App;
import com.example.think.uihealth.model.bean.BmobUser;
import com.example.think.uihealth.model.bean.Follow;
import com.example.think.uihealth.moduel.forum.userinfo.adapter.FollowersAdapter;
import com.gc.materialdesign.views.ProgressBarCircularIndeterminate;
import com.kermit.exutils.utils.ExUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by Zane on 15/11/17.
 */
public class FollowersFragment extends Fragment {

    public static final String TAG = "FollowersFragment";

    @Bind(R.id.recycler_fragment_followers)
    RecyclerView mRecyclerView;
    @Bind(R.id.swipe_fragment_followers)
    SwipeRefreshLayout mSwipeFragmentFollowers;
    @Bind(R.id.progressbar_followerfragment)
    ProgressBarCircularIndeterminate mProgressbarFollowerfragment;

    private BmobQuery<BmobUser> query;
    private BmobQuery<Follow> query_follow;
    private List<String> followerImageUrl = new ArrayList<>();
    private List<String> followerNickName = new ArrayList<>();
    private FollowersAdapter mAdapter;
    private LinearLayoutManager linearLayoutManager;
    private String id;
    public static final String ID = "ID";
    private static final int STATE_REFRESH = 0;// 下拉刷新
    private static final int STATE_MORE = 1;// 加载更多
    private static final int STATE_FIRST = 2;// 第一次加载

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0:
                    mAdapter.setFollowerNickname(followerNickName);
                    mAdapter.setFollowersImage(followerImageUrl);
                    mAdapter.notifyDataSetChanged();
                    mRecyclerView.setLayoutManager(linearLayoutManager);
                    mRecyclerView.setAdapter(mAdapter);
                    break;
            }
        }
    };

    public static FollowersFragment newInstance(String id){
        FollowersFragment fragment = new FollowersFragment();

        Bundle bundle = new Bundle();
        bundle.putString(ID, id);
        fragment.setArguments(bundle);

        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_followers_layout, container, false);
        ButterKnife.bind(this, view);
        initData();

        mSwipeFragmentFollowers.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                fetchData(STATE_REFRESH, 0);
                mAdapter.notifyDataSetChanged();
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        fetchData(STATE_FIRST, 0);
    }

    private void initData() {
        id = getArguments().getString(ID);
        query_follow = new BmobQuery<>();
        query = new BmobQuery<>();
        mAdapter = new FollowersAdapter();
        linearLayoutManager = new LinearLayoutManager(getContext());
    }

    // TODO: 15/11/17 获取粉丝数据
    private void fetchData(final int actionType, int page) {
        mProgressbarFollowerfragment.setVisibility(View.VISIBLE);
        query_follow.addWhereEqualTo("befollowUserId", id);
        query_follow.setLimit(10);
        query_follow.setSkip(page * 10);
        query_follow.order("-time");
        query_follow.findObjects(App.getInstance(), new FindListener<Follow>() {
            @Override
            public void onSuccess(List<Follow> list) {
                switch (actionType){
                    case STATE_FIRST:
                    case STATE_REFRESH:
                        followerImageUrl.clear();
                        followerNickName.clear();
                        break;
                }
                for(Follow follow : list){
                    query.addWhereEqualTo("objectId", follow.getFollowUseId());
                    query.findObjects(App.getInstance(), new FindListener<BmobUser>() {
                        @Override
                        public void onSuccess(List<BmobUser> list) {
                            ExUtils.Toast("查询成功");

                            followerImageUrl.add(list.get(0).getUserPhoto());
                            followerNickName.add(list.get(0).getNickName());

                            mProgressbarFollowerfragment.setVisibility(View.INVISIBLE);

                            Message message = new Message();
                            message.what = 0;
                            handler.sendMessage(message);

                        }

                        @Override
                        public void onError(int i, String s) {
                            ExUtils.Toast(s);
                            mProgressbarFollowerfragment.setVisibility(View.INVISIBLE);
                        }
                    });
                }

                mProgressbarFollowerfragment.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onError(int i, String s) {
                ExUtils.Toast(s);
                mProgressbarFollowerfragment.setVisibility(View.INVISIBLE);
            }
        });

        mSwipeFragmentFollowers.setRefreshing(false);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
