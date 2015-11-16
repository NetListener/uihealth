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
import android.widget.LinearLayout;

import com.example.think.uihealth.R;
import com.example.think.uihealth.model.bean.Forum;
import com.example.think.uihealth.view.adapter.ForumOftenListAdapter;
import com.kermit.exutils.utils.ExUtils;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by kermit on 15-11-15.
 */
public class ForumOftenListFragment extends Fragment {


    private static ForumOftenListFragment fragment;
    @Bind(R.id.recycler_fragment_forumoften)
    RecyclerView mRecyclerFragmentForumoften;
    @Bind(R.id.swipe_fragment_forumoften)
    SwipeRefreshLayout mSwipeFragmentForumoften;

    private LinearLayoutManager mLinearLayoutManager;
    private ForumOftenListAdapter mAdapter;
    private String tag;

    public static ForumOftenListFragment newInstance() {
        if (fragment == null) {
            fragment = new ForumOftenListFragment();
        }
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLinearLayoutManager = new LinearLayoutManager(getContext());
        mAdapter = new ForumOftenListAdapter(getContext());
        tag = getArguments().getString("tag");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_forumoftenlist_layout, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        fetchData();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        mRecyclerFragmentForumoften.setLayoutManager(mLinearLayoutManager);
        mRecyclerFragmentForumoften.setAdapter(mAdapter);
        mSwipeFragmentForumoften.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                fetchData();
            }
        });
    }

    private int page = 1;
    private void fetchData(){
        BmobQuery<Forum> query = new BmobQuery<>();
        query.setLimit(10);
        query.setSkip((page - 1) * 10);
        query.addWhereEqualTo("tag", tag);
        query.order("-time");
        query.findObjects(getContext(), new FindListener<Forum>() {
            @Override
            public void onSuccess(List<Forum> list) {
                if (mAdapter.setData(list)) {
                    mAdapter.notifyDataSetChanged();
                }
                mSwipeFragmentForumoften.setRefreshing(false);
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
