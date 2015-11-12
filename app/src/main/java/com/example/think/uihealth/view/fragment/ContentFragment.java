package com.example.think.uihealth.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
<<<<<<< HEAD
<<<<<<< HEAD
=======
import android.util.Log;
>>>>>>> origin/dev
=======
>>>>>>> origin/dev
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.think.uihealth.R;
import com.example.think.uihealth.config.TestContent;
<<<<<<< HEAD
<<<<<<< HEAD
=======
import com.example.think.uihealth.model.Human;
import com.example.think.uihealth.strategy.Strategy;
>>>>>>> origin/dev
=======
>>>>>>> origin/dev
import com.example.think.uihealth.view.adapter.ContentRecycleviewAdapter;
import com.example.think.uihealth.view.adapter.ContentViewpagerAdapter;

import java.util.ArrayList;
import java.util.List;

<<<<<<< HEAD
<<<<<<< HEAD
=======
import butterknife.Bind;
>>>>>>> origin/dev
=======
>>>>>>> origin/dev
import butterknife.ButterKnife;

/**
 * Created by Zane on 2015/9/22.
 */
public class ContentFragment extends Fragment{

    public static final String TAG = "ContentFragment";

    private ArrayList<String[]> mContents;
    private String[] mTitles;
    private List<ContentItemFragment> mFragments = new ArrayList<>();
    private ContentViewpagerAdapter mContentViewPagerAdapter;
    private ContentRecycleviewAdapter mContentRecycleViewAdapter;
    private static OnLastItemClickListener mListener;

    private static ViewPager mFragmentViewpager;


    public interface OnLastItemClickListener{
        void activitySkip();
    }

    public static OnLastItemClickListener getOnLastItemClickListener(){
        return mListener;
    }

    public void setOnLastItemClickListener(OnLastItemClickListener mListener){
        this.mListener = mListener;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_content_layout, container, false);
        ButterKnife.bind(this ,view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        mFragmentViewpager = (ViewPager) view.findViewById(R.id.viewpager_fragment_testcontent);
        //初始化itemfragment的变量值
        mContents = TestContent.getContents();
        mTitles = TestContent.TITLES;

        for(int i = 0; i < mTitles.length; i++){
            mFragments.add(ContentItemFragment.newInstance(mTitles[i], mContents.get(i)));
        }

        mContentViewPagerAdapter = new ContentViewpagerAdapter(getActivity().getSupportFragmentManager(),
                mFragments);

        mFragmentViewpager.setAdapter(mContentViewPagerAdapter);
    }

    public static ViewPager getViewPager(){
        return mFragmentViewpager;
    }

}
