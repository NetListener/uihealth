package com.example.think.uihealth.moduel.forum;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.think.uihealth.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by kermit on 15-11-10.
 */
public class ForumFragment extends Fragment {

    @Bind(R.id.btn_forum_tohot)
    TextView mBtnForumTohot;
    @Bind(R.id.btn_forum_tooften)
    TextView mBtnForumTooften;

    private static ForumFragment fragment;
    public static ForumFragment newInstance(){
        if (fragment == null){
            fragment = new ForumFragment();
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
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_forum_layout, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        mBtnForumTohot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.forum_container, ForumTopicFragment.newInstance())
                        .commit();
            }
        });

        mBtnForumTooften.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.forum_container, ForumOftenFragment.newInstance())
                        .commit();
            }
        });
        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.forum_container, ForumTopicFragment.newInstance())
                .commit();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
