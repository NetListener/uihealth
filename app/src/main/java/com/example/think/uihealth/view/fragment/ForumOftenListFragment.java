package com.example.think.uihealth.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.think.uihealth.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by kermit on 15-11-15.
 */
public class ForumOftenListFragment extends Fragment {


    private static ForumOftenListFragment fragment;
    @Bind(R.id.recycler_fragment_forumoften)
    RecyclerView mRecyclerFragmentForumoften;
    @Bind(R.id.swipe_fragment_forumoften)
    SwipeRefreshLayout mSwipeFragmentForumoften;

    public static ForumOftenListFragment newInstance() {
        if (fragment == null) {
            fragment = new ForumOftenListFragment();
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
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_forumoftenlist_layout, container, false);
        ButterKnife.bind(this, view);
        return view;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
