package com.example.think.uihealth.view.activity;

import android.content.Intent;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import com.example.think.uihealth.R;

import com.example.think.uihealth.model.bean.BmobUser;

import com.example.think.uihealth.util.GetEditTextWordNumber;

import com.example.think.uihealth.model.bean.BmobUser;

import com.gc.materialdesign.views.ButtonRectangle;
import com.gc.materialdesign.views.ProgressBarCircularIndeterminate;
import com.kermit.exutils.utils.ActivityCollector;
import com.kermit.exutils.utils.ExUtils;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by Zane on 2015/10/12.
 */
public class RegisterActivity extends AppCompatActivity{

    @Bind(R.id.edittext_registeractivity_uesername)
    EditText mUsername;
    @Bind(R.id.edittext_registeractivity_password)
    EditText mPassword;
    @Bind(R.id.edittext_registeractivity_email)
    EditText mEmail;
    @Bind(R.id.btn_registeractivity_register)
    ButtonRectangle mButton;
    @Bind(R.id.progressbar_registeractivity)
    ProgressBarCircularIndeterminate mProgress;

    private BmobUser mBmobUser;
    private int passwordNum;
    private int usernameNum;
    private Boolean isAutoChange = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_layout);
        ButterKnife.bind(this);

        ActivityCollector.getInstance().pushActivity(this);

        //监听用户名位数

        mUsername.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                usernameNum = mUsername.getText().length();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        //监听密码的位数
        mPassword.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                passwordNum = mPassword.getText().length();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        //实行注册
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mProgress.setVisibility(View.VISIBLE);
                if(passwordNum >= 6 && usernameNum != 0) {
                    //开始向云后台存数据
                    mBmobUser = new BmobUser();
                    mBmobUser.setUsername(mUsername.getText().toString());
                    mBmobUser.setPassword(mPassword.getText().toString());
                    mBmobUser.setEmail(mEmail.getText().toString());
                    mBmobUser.setNickName(mUsername.getText().toString());
                    //mBmobUser.setUserPhoto(BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher));
                    //mBmobUser.setUserPhoto(getResources().getDrawable(R.drawable.ic_launcher));
                    mBmobUser.setUserPhoto("");
                    mBmobUser.setFollowers(0);
                    mBmobUser.setFollowing(0);

                    //添加注册的监听回调
                    mBmobUser.signUp(RegisterActivity.this, new SaveListener() {
                        @Override
                        public void onSuccess() {
                            mProgress.setVisibility(View.INVISIBLE);
                            ExUtils.Toast("注册成功");
                            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                            intent.putExtra(StartActivity.AUTOCHANGE, false);
                            startActivity(intent);
                        }

                        @Override
                        public void onFailure(int i, String s) {
                            ExUtils.ToastLong(s);
                            mProgress.setVisibility(View.INVISIBLE);
                        }
                    });
                }
                else if(passwordNum < 6){
                    ExUtils.ToastLong("密码必须大于6位");
                }
                else if(usernameNum == 0){
                    ExUtils.Toast("用户名不能为空");
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollector.getInstance().popActivity(this);
    }
}
