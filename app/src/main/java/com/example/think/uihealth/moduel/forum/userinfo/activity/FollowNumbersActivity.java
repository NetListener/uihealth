package com.example.think.uihealth.moduel.forum.userinfo.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;

import com.example.think.uihealth.R;
import com.example.think.uihealth.app.App;
import com.example.think.uihealth.model.bean.BmobUser;
import com.example.think.uihealth.moduel.forum.userinfo.fragment.FollowersFragment;
import com.example.think.uihealth.moduel.forum.userinfo.fragment.FollowingsFragment;
import com.kermit.exutils.utils.ActivityCollector;
import com.kermit.exutils.utils.ExUtils;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Zane on 15/11/17.
 */
public class FollowNumbersActivity extends AppCompatActivity {

    public static final String TAG = "FollowNumbersActivity";

    @Bind(R.id.toolbar_follow)
    Toolbar mToolbar;
    @Bind(R.id.layout_fragment_morefollowing)
    RelativeLayout layoutFragmentMorefollowing;
    @Bind(R.id.layout_fragment_morefollowers)
    RelativeLayout layoutFragmentMorefollowers;

    private FollowersFragment followersFragment;
    private FollowingsFragment followingsFragment;
    private BmobUser mUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_follownumbers_activity);
        ButterKnife.bind(this);
        initData();
        ActivityCollector.getInstance().pushActivity(this);

        mToolbar.setTitle("");
        setSupportActionBar(mToolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.framelayout_fragment_changefollownumber, followersFragment);
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        transaction.commit();

        layoutFragmentMorefollowers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.framelayout_fragment_changefollownumber, followersFragment);
                transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                transaction.commit();
                ExUtils.Toast("follower");
            }
        });
        layoutFragmentMorefollowing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.framelayout_fragment_changefollownumber, followingsFragment);
                transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                transaction.commit();
                ExUtils.Toast("following");
            }
        });

    }

    private void initData(){
        mUser = BmobUser.getCurrentUser(App.getInstance(), BmobUser.class);
        followersFragment = FollowersFragment.newInstance(mUser.getObjectId());
        followingsFragment = FollowingsFragment.newInstance(mUser.getObjectId());
        Log.i(TAG, mUser.getObjectId());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
        ActivityCollector.getInstance().popActivity(this);
    }
}
