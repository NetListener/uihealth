package com.example.think.uihealth.moduel.forum.userinfo.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.think.uihealth.R;
import com.example.think.uihealth.app.App;
import com.example.think.uihealth.model.bean.BmobUser;
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
    private BmobUser mUser;
    private BmobQuery<BmobUser> query;
    private List<String> followerImageUrl = new ArrayList<>();
    private List<String> followerNickName = new ArrayList<>();
    private List<BmobUser> followersNumber;
    private FollowersAdapter mAdapter;
    private LinearLayoutManager linearLayoutManager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_followers_layout, container, false);
        ButterKnife.bind(this, view);
        initData();

        mSwipeFragmentFollowers.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                fetchData();
                mAdapter.notifyDataSetChanged();
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        fetchData();
    }

    private void initData() {
        mUser = BmobUser.getCurrentUser(App.getInstance(), BmobUser.class);
        query = new BmobQuery<>();
        mAdapter = new FollowersAdapter();
        linearLayoutManager = new LinearLayoutManager(App.getInstance());
    }

    // TODO: 15/11/17 获取粉丝数据
    private void fetchData() {
        mProgressbarFollowerfragment.setVisibility(View.VISIBLE);
        query.addWhereEqualTo("objectId", mUser.getObjectId());
        query.findObjects(App.getInstance(), new FindListener<BmobUser>() {
            @Override
            public void onSuccess(List<BmobUser> list) {
                mProgressbarFollowerfragment.setVisibility(View.INVISIBLE);
                followersNumber = list.get(0).getUserFollowers();
                for (int i = 0; i < followersNumber.size(); i++) {
                    followerImageUrl.add(followersNumber.get(i).getUserPhoto());
                    followerNickName.add(followersNumber.get(i).getNickName());
                }
                mAdapter.setFollowerNickname(followerNickName);
                mAdapter.setFollowersImage(followerImageUrl);
                mAdapter.notifyDataSetChanged();
                mRecyclerView.setLayoutManager(linearLayoutManager);
                mRecyclerView.setAdapter(mAdapter);
            }

            @Override
            public void onError(int i, String s) {
                ExUtils.ToastLong(s);
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
