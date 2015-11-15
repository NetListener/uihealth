package com.example.think.uihealth.view.fragment;

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
import com.example.think.uihealth.model.bean.Forum;
import com.example.think.uihealth.view.adapter.ForumRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by kermit on 15-11-13.
 */
public class ForumTopicFragment extends Fragment{

    @Bind(R.id.recycler_fragment_forumtopic)
    RecyclerView mRecyclerFragmentForum;
    @Bind(R.id.swipe_fragment_forumtopic)
    SwipeRefreshLayout mSwipeFragmentForum;

    private List<Forum> mForumList;
    private ForumRecyclerViewAdapter mAdapter;
    private static ForumTopicFragment fragment;

    public static ForumTopicFragment newInstance(){
        if (fragment == null){
            fragment = new ForumTopicFragment();
        }
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_forumtopic_layout, container, false);
        ButterKnife.bind(this, view);
        return view;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        mForumList = new ArrayList<>();
        mAdapter = new ForumRecyclerViewAdapter(getActivity(), mForumList);
        mRecyclerFragmentForum.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerFragmentForum.setAdapter(mAdapter);

        mSwipeFragmentForum.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                fetchData();
                page = 1;
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        fetchData();
        page = 1;
    }

    private static int page;
    private void fetchData(){
        // TODO: 15-11-11 get forum data
        BmobQuery<Forum> query = new BmobQuery<>();
        query.setLimit(10);
        query.setSkip((page - 1) * 10);
        query.order("-time");
        query.findObjects(getContext(), new FindListener<Forum>() {
            @Override
            public void onSuccess(List<Forum> list) {
                if (mAdapter.setData(list)) {
                    mAdapter.notifyDataSetChanged();
                }
                mSwipeFragmentForum.setRefreshing(false);
            }
            @Override
            public void onError(int i, String s) {

            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
