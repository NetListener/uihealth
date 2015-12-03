package com.example.think.uihealth.moduel.forum.userinfo.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.think.uihealth.R;
import com.example.think.uihealth.moduel.forum.userinfo.fragment.ChangeInfoFragemnt;
import com.example.think.uihealth.moduel.forum.userinfo.fragment.ChangeNicknameFragment;
import com.example.think.uihealth.moduel.forum.userinfo.fragment.MyUserInfoFragment;

import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * Created by Zane on 15/11/9.
 */
public class ChangeInfoActivity extends AppCompatActivity{

    @Bind(R.id.toolbar_main)
    Toolbar toolbarMain;
    private String nickName;
    private String imageUrl;
    private ChangeInfoFragemnt changeInfoFragemnt;
    private ChangeNicknameFragment changeNicknameFragment;
    private int fragmentCount = 0;

    public static final String TAG = "ChangInfoActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_changeinfo_layout);
        ButterKnife.bind(this);

        toolbarMain.setTitle("");
        setSupportActionBar(toolbarMain);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //开始获取用户信息
        nickName = getIntent().getStringExtra(MyUserInfoFragment.NICKNAME);
        imageUrl = getIntent().getStringExtra(MyUserInfoFragment.IMAGEURL);

        toolbarMain.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //生成第一个总修改页面的碎片
        changeInfoFragemnt = ChangeInfoFragemnt.newInstance(imageUrl, nickName, 0, 0);
        //changeInfoFragemnt = new ChangeInfoFragemnt();
        final FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_changinfoactivity, changeInfoFragemnt);
        //transaction.addToBackStack(null);
        transaction.commit();
        fragmentCount = 1;
    }
}
