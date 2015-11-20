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
public class FollowingsFragment extends Fragment {

    @Bind(R.id.recycler_fragment_followings)
    RecyclerView mRecyclerView;
    @Bind(R.id.swipe_fragment_followings)
    SwipeRefreshLayout swipeFragmentFollowings;
    @Bind(R.id.progressbar_followingfragment)
    ProgressBarCircularIndeterminate progressbarFollowingfragment;

    private BmobUser mUser;
    private BmobQuery<BmobUser> query;
    private BmobQuery<Follow> query_follow;
    private List<String> followingImageUrl = new ArrayList<>();
    private List<String> followingNickName = new ArrayList<>();
    private FollowersAdapter mAdapter;
    private LinearLayoutManager linearLayoutManager;
    public static final String ID = "ID";
    private String id;
    private static final int STATE_REFRESH = 0;// 下拉刷新
    private static final int STATE_MORE = 1;// 加载更多
    private static final int STATE_FIRST = 2;// 第一次加载
    public static final String TAG = "FollowingsFragment";

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0:
                    mAdapter.setFollowerNickname(followingNickName);
                    mAdapter.setFollowersImage(followingImageUrl);
                    Log.i(TAG, followingImageUrl.get(0));
                    mAdapter.notifyDataSetChanged();
                    mRecyclerView.setLayoutManager(linearLayoutManager);
                    mRecyclerView.setAdapter(mAdapter);
                    break;
            }
        }
    };

    public static FollowingsFragment newInstance(String id){
        FollowingsFragment fragment = new FollowingsFragment();

        Bundle bundle = new Bundle();
        bundle.putString(ID, id);
        fragment.setArguments(bundle);

        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_following_layout, container, false);
        ButterKnife.bind(this, view);
        initData();

        swipeFragmentFollowings.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                fetchData(STATE_REFRESH, 0);
                mAdapter.notifyDataSetChanged();
            }
        });

        return view;
    }


    private void initData() {
        //mUser = BmobUser.getCurrentUser(App.getInstance(), BmobUser.class);
        id = getArguments().getString(ID);
        Log.i(TAG, id+" 1");
        query = new BmobQuery<>();
        query_follow = new BmobQuery<>();
        mAdapter = new FollowersAdapter();
        linearLayoutManager = new LinearLayoutManager(App.getInstance());
    }

    private void fetchData(final int actionType, int page) {
        progressbarFollowingfragment.setVisibility(View.VISIBLE);
        query_follow.addWhereEqualTo("followUseId", id);
        Log.i(TAG, id + " 2");
        query_follow.setLimit(10);
        query_follow.setSkip(page * 10);
        query_follow.order("-time");
        query_follow.findObjects(App.getInstance(), new FindListener<Follow>() {
            @Override
            public void onSuccess(List<Follow> list) {
                Log.i(TAG, list.get(0).getObjectId()+" 4");
                switch (actionType){
                    case STATE_FIRST:
                    case STATE_REFRESH:
                        followingImageUrl.clear();
                        followingNickName.clear();
                        break;
                }
                for(Follow follow : list){
                    Log.i(TAG, list.get(0).getObjectId()+" 3");
                    query.addWhereEqualTo("objectId", follow.getBefollowUserId());
                    query.findObjects(App.getInstance(), new FindListener<BmobUser>() {
                        @Override
                        public void onSuccess(List<BmobUser> list) {
                            ExUtils.Toast("查询成功");

                            followingImageUrl.add(list.get(0).getUserPhoto());
                            followingNickName.add(list.get(0).getNickName());
                            Log.i(TAG, followingImageUrl.get(0)+"haha");
                            progressbarFollowingfragment.setVisibility(View.INVISIBLE);

                            Message message = new Message();
                            message.what = 0;
                            handler.sendMessage(message);

                        }

                        @Override
                        public void onError(int i, String s) {
                            ExUtils.Toast(s);
                            progressbarFollowingfragment.setVisibility(View.INVISIBLE);
                        }
                    });
                }

                progressbarFollowingfragment.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onError(int i, String s) {
                ExUtils.Toast(s+"7");
                progressbarFollowingfragment.setVisibility(View.INVISIBLE);
            }
        });

        swipeFragmentFollowings.setRefreshing(false);
    }

    @Override
    public void onResume() {
        super.onResume();
        fetchData(STATE_FIRST, 0);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
