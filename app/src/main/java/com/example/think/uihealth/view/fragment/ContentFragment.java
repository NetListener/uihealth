package com.example.think.uihealth.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.think.uihealth.R;
import com.example.think.uihealth.config.TestContent;
import com.example.think.uihealth.view.adapter.ContentRecycleviewAdapter;
import com.example.think.uihealth.view.adapter.ContentViewpagerAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by think on 2015/9/22.
 */
public class ContentFragment extends Fragment{

    public static final String TAG = "CONTENTFRAGMENT";

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
        mFragmentViewpager = (ViewPager) view.findViewById(R.id.viewpager_fragment_testcontent);
        ButterKnife.bind(this ,view);

        //初始化itemfragment的变量值
        mContents = TestContent.getContents();
        mTitles = TestContent.TITLES;

        // TODO: 2015/9/22 viewpager适配器
        for(int i = 0; i < mTitles.length; i++){
            mFragments.add(ContentItemFragment.newInstance(mTitles[i], mContents.get(i)));
            Log.i("ContentFragment", mContents.get(i)[0]);
        }

        mContentViewPagerAdapter = new ContentViewpagerAdapter(getActivity().getSupportFragmentManager(),
                mFragments);

        mFragmentViewpager.setAdapter(mContentViewPagerAdapter);



        return view;
    }

    public static ViewPager getViewPager(){
        return mFragmentViewpager;
    }


}
