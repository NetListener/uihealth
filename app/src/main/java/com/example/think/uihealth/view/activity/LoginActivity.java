package com.example.think.uihealth.view.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toolbar;

import com.example.think.uihealth.R;
import com.example.think.uihealth.model.BmobUser;
import com.gc.materialdesign.views.ButtonRectangle;
import com.gc.materialdesign.views.ProgressBarCircularIndeterminate;
import com.kermit.exutils.utils.ActivityCollector;
import com.kermit.exutils.utils.ExUtils;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by Zane on 2015/10/12.
 */
public class LoginActivity extends AppCompatActivity{

    public static final String LOGIN_USERNAME = "USERNAME";
    public static final String LOGIN_PASSWORD = "PASSWORD";
    public static final String LOGIN_REMEMBER_PASSWORD = "REMEMBER_PASSWORD";
    public static final String LOGIN_REMEMBER_LOGIN = "REMEMBER_LOGIN";

    @Bind(R.id.textview_loginactivity_register)
    TextView mTexiView;
    @Bind(R.id.btn_loginactivity_login)
    ButtonRectangle mButton;
    @Bind(R.id.edit_loginacitivity_username)
    TextView mUserName;
    @Bind(R.id.edit_loginactivity_password)
    TextView mPassword;
    @Bind(R.id.checkbox_loginactivity_login)
    CheckBox loginCheckBox;
    @Bind(R.id.checkbox_loginactivity_password)
    CheckBox passwordCheckBox;
    @Bind(R.id.toolbar_loginactivity)
    android.support.v7.widget.Toolbar mToolbar;
    @Bind(R.id.progressbar_loginactivity)
    ProgressBarCircularIndeterminate mProgressbar;

    private BmobUser mBmobUser;
    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mEditor;
    private Boolean isRememberPassword;
    private Boolean isRemeberLogin;
    private Boolean isAutoChange;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //初始化bmob sdk,(context, application ID)
        Bmob.initialize(this, "65e7b14780b2be4c5636a88135d04d00");
        setContentView(R.layout.activity_login_layout);
        ButterKnife.bind(this);

        ActivityCollector.getInstance().pushActivity(this);


        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mEditor = mSharedPreferences.edit();

        //恢复用户名
        String userName = mSharedPreferences.getString(LOGIN_USERNAME, "");
        mUserName.setText(userName);



        //恢复密码
        isRememberPassword = mSharedPreferences.getBoolean(LOGIN_REMEMBER_PASSWORD, false);
        if(isRememberPassword){
            String Password = mSharedPreferences.getString(LOGIN_PASSWORD, "");
            mPassword.setText(Password);
            passwordCheckBox.setChecked(true);
        }

        //避免重复登入
        isAutoChange = this.getIntent().getBooleanExtra(StartActivity.AUTOCHANGE, true);
        isRemeberLogin = mSharedPreferences.getBoolean(LOGIN_REMEMBER_LOGIN, false);
        if(isRemeberLogin && isAutoChange) {
            cn.bmob.v3.BmobUser mBmobUser2 = BmobUser.getCurrentUser(this);
            if (mBmobUser2 != null) {
                Intent intent = new Intent(LoginActivity.this, StartActivity.class);
                intent.putExtra(LOGIN_USERNAME, mUserName.getText().toString());
                ActivityCollector.getInstance().closeActivity(LoginActivity.this);
                startActivity(intent);
            }
        }


        //注册的监听
        mTexiView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        //实行登入
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //显示进度条
                mProgressbar.setVisibility(View.VISIBLE);

                //自动保存用户名
                mEditor.putString(LOGIN_USERNAME,mUserName.getText().toString());

                //选择自动登入
                if(loginCheckBox.isChecked()){
                    mEditor.putBoolean(LOGIN_REMEMBER_LOGIN, true);
                }

                //选择保存密码
                if(passwordCheckBox.isChecked()){
                    mEditor.putString(LOGIN_PASSWORD, mPassword.getText().toString());
                    mEditor.putBoolean(LOGIN_REMEMBER_PASSWORD, true);
                }
                else {
                    mEditor.clear();
                }
                mEditor.commit();

                //开始登入
                mBmobUser = new BmobUser();
                mBmobUser.setUsername(mUserName.getText().toString());
                mBmobUser.setPassword(mPassword.getText().toString());
                mBmobUser.login(LoginActivity.this, new SaveListener() {
                    @Override
                    public void onSuccess() {
                        mProgressbar.setVisibility(View.INVISIBLE);
                        Intent intent = new Intent(LoginActivity.this, StartActivity.class);
                        intent.putExtra(LOGIN_USERNAME, mUserName.getText().toString());
                        ActivityCollector.getInstance().closeActivity(LoginActivity.this);
                        startActivity(intent);
                    }

                    @Override
                    public void onFailure(int i, String s) {
                        ExUtils.ToastLong(s);
                    }
                });
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollector.getInstance().popActivity(this);
    }
}
