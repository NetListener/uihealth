package com.example.think.uihealth.view.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.think.uihealth.R;
import com.example.think.uihealth.config.OftenConstant;
import com.kermit.exutils.utils.ExUtils;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by kermit on 15-11-13.
 */
public class ForumOftenFragment extends Fragment implements ChoiceFragment.ChoiceCallback{

    @Bind(R.id.ll_forumoften_layout)
    LinearLayout mLlForumoftenLayout;


    private static ForumOftenFragment mFragment;
    private ChoiceFragment mChoiceFragment;
    @Bind(R.id.forum_add)
    ImageView mForumAdd;

    public static ForumOftenFragment newInstance() {
        if (mFragment == null) {
            mFragment = new ForumOftenFragment();
        }
        return mFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_forumoften_layout, container, false);
        ButterKnife.bind(this, view);
        return view;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        mChoiceFragment = new ChoiceFragment();
        mChoiceFragment.setChoiceCallback(this);
        mForumAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mChoiceFragment.show(getFragmentManager(), "choice");
            }
        });
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void choiceCallback(int pos) {
        final int i = pos;
        mChoiceFragment.dismiss();
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                ExUtils.Toast(OftenConstant.oftenSickness[i]);
            }
        });
    }
}
