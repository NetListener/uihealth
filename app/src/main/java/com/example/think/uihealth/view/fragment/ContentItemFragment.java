package com.example.think.uihealth.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.think.uihealth.R;
import com.example.think.uihealth.view.adapter.ContentRecycleviewAdapter;

import butterknife.Bind;
import butterknife.ButterKnife;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by think on 2015/9/22.
 */
public class ContentItemFragment extends Fragment{
    
    public static final String TAG = "ContentItemFragment";
    private static final String TITLE = "TITLE";
    private static final String CONTENT = "CONTENT";
    private String[] mContents;
    private String title;
    private ContentRecycleviewAdapter mAdapter;
    private LinearLayoutManager mManager;


    @Bind(R.id.textview_fragment_title)
    TextView mFragmentTextview;
    @Bind(R.id.recyclerview_fragment_content)
    RecyclerView mFragmentRecycleview;


    public static final ContentItemFragment newInstance(String title, String[] content){
        ContentItemFragment contentItemFragment = new ContentItemFragment();
        
        Bundle bundle = new Bundle();
        bundle.putString(TITLE, title);
        bundle.putStringArray(CONTENT, content);
        
        contentItemFragment.setArguments(bundle);
        
        return contentItemFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_content_item_layout, container, false);
        ButterKnife.bind(this, view);
        
        mContents = getArguments().getStringArray(CONTENT);
        title = getArguments().getString(TITLE);
        
        mFragmentTextview.setText(title);

        // TODO: 2015/9/22 设置recycleview
        mAdapter = new ContentRecycleviewAdapter(getActivity(), mContents);

        // TODO: 2015/9/23 添加最后一个页面实现fragment跳转的逻辑 以及存储信息的逻辑
        mAdapter.setOnItemClickListener(new ContentRecycleviewAdapter.OnItemClickListener() {
            @Override
            public void OnClick(View view, int pos) {

                if(!title.equals("心脏的收缩压?")){
                    ContentFragment.getViewPager().arrowScroll(2);
                }else {
                    ContentFragment.getOnLastItemClickListener().activitySkip();
                }
            }
        });

        mFragmentRecycleview.setAdapter(mAdapter);
        mManager = new LinearLayoutManager(getActivity());
        mFragmentRecycleview.setLayoutManager(mManager);

        return view;
    }
}
