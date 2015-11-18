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
public class FollowingsFragment extends Fragment {

    @Bind(R.id.recycler_fragment_followings)
    RecyclerView recyclerFragmentFollowings;
    @Bind(R.id.swipe_fragment_followings)
    SwipeRefreshLayout swipeFragmentFollowings;
    @Bind(R.id.progressbar_followingfragment)
    ProgressBarCircularIndeterminate progressbarFollowingfragment;
    private BmobUser mUser;
    private BmobQuery<BmobUser> query;
    private List<String> followingImageUrl = new ArrayList<>();
    private List<String> followingNickName = new ArrayList<>();
    private List<BmobUser> followingNumber;
    private FollowersAdapter mAdapter;
    private LinearLayoutManager linearLayoutManager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_following_layout, container, false);
        ButterKnife.bind(this, view);
        initData();

        return view;
    }


    private void initData() {
        mUser = BmobUser.getCurrentUser(App.getInstance(), BmobUser.class);
        query = new BmobQuery<>();
        mAdapter = new FollowersAdapter();
        linearLayoutManager = new LinearLayoutManager(App.getInstance());
    }

    private void fetchData() {
        progressbarFollowingfragment.setVisibility(View.VISIBLE);
        query.addWhereEqualTo("objectId", mUser.getObjectId());
        query.findObjects(App.getInstance(), new FindListener<BmobUser>() {
            @Override
            public void onSuccess(List<BmobUser> list) {
                progressbarFollowingfragment.setVisibility(View.INVISIBLE);
                followingNumber = list.get(0).getUserFollowings();
                for (int i = 0; i < followingNumber.size(); i++) {
                    followingImageUrl.add(followingNumber.get(i).getUserPhoto());
                    followingNickName.add(followingNumber.get(i).getNickName());
                }
                mAdapter.setFollowerNickname(followingNickName);
                mAdapter.setFollowersImage(followingImageUrl);
                mAdapter.notifyDataSetChanged();
                recyclerFragmentFollowings.setLayoutManager(linearLayoutManager);
                recyclerFragmentFollowings.setAdapter(mAdapter);
            }

            @Override
            public void onError(int i, String s) {
                ExUtils.ToastLong(s);
                progressbarFollowingfragment.setVisibility(View.INVISIBLE);
            }
        });

        swipeFragmentFollowings.setRefreshing(false);
    }

    @Override
    public void onResume() {
        super.onResume();
        fetchData();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
