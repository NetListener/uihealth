package com.example.think.uihealth.view.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.think.uihealth.view.fragment.ContentItemFragment;

import java.util.List;

/**
 * Created by Zane on 2015/9/22.
 */
public class ContentViewpagerAdapter extends FragmentStatePagerAdapter{

    private List<ContentItemFragment> mFragments;

    public ContentViewpagerAdapter(FragmentManager fm, List<ContentItemFragment> mFragments) {
        super(fm);
        this.mFragments = mFragments;
    }

    @Override
    public Fragment getItem(int position) {

        return this.mFragments.get(position);

    }

    @Override
    public int getCount() {

        return this.mFragments.size();

    }
}
