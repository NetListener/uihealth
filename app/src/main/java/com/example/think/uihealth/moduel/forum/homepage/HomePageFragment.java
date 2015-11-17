package com.example.think.uihealth.moduel.forum.homepage;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.think.uihealth.R;
import com.example.think.uihealth.view.activity.LoginActivity;
import com.gc.materialdesign.views.ButtonFlat;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Zane on 15/11/8.
 */
public class HomePageFragment extends android.support.v4.app.Fragment{

    @Bind(R.id.btn_startactivity_start)
    ButtonFlat mButton;
    @Bind(R.id.btn_startactivity_query)
    ButtonFlat mButtonQuery;

    private String userName;
    public static final String USERNAME = "USERNAME";

    public static HomePageFragment newInstance(String userName){
        HomePageFragment homePageFragment = new HomePageFragment();
        Bundle bundle = new Bundle();
        bundle.putString(USERNAME, userName);
        homePageFragment.setArguments(bundle);
        return homePageFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_startactivity_homepage, container, false);
        ButterKnife.bind(this, view);

        userName = getArguments().getString(USERNAME);

        mButtonQuery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(getActivity(), BeforeDataActivity.class);
                startActivity(intent1);
            }
        });

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getActivity(), MainActivity.class);
                intent.putExtra(LoginActivity.LOGIN_USERNAME, userName);
                startActivity(intent);

            }
        });

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
