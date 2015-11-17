package com.example.think.uihealth.view.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.think.uihealth.R;
import com.example.think.uihealth.app.App;
import com.example.think.uihealth.config.QQConstant;
import com.example.think.uihealth.model.bean.BmobUser;
import com.example.think.uihealth.moduel.forum.homepage.StartActivity;
import com.gc.materialdesign.views.ButtonRectangle;
import com.gc.materialdesign.views.ProgressBarCircularIndeterminate;
import com.kermit.exutils.utils.ActivityCollector;
import com.kermit.exutils.utils.ExUtils;
import com.tencent.connect.common.Constants;
import com.tencent.open.utils.HttpUtils;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by Zane on 2015/10/12.
 * QQAPPID：1104827985
 * QQAPPKEY：4ik21sZOEUCH4Jz9
 */
public class LoginActivity extends AppCompatActivity {

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
    Toolbar mToolbar;
    @Bind(R.id.progressbar_loginactivity)
    ProgressBarCircularIndeterminate mProgressbar;
    @Bind(R.id.btn_loginactivity_qqlogin)
    Button mQQLoginButton;



    private BmobUser mBmobUser;
    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mEditor;
    private Boolean isRememberPassword;
    private Boolean isRemeberLogin;
    private Boolean isAutoChange;

    private Tencent mTencent;
    public static String mAppid;
    public static String openidString;
    public static String access_token;
    public static String nicknameString;
    public static String TAG="LoginActivity";
    public static int openidHashcode;
    private String url = "https://graph.qq.com/user/get_user_info";

    private BaseUiListener listener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //初始化bmob sdk,(context, application ID)
        Bmob.initialize(this, "65e7b14780b2be4c5636a88135d04d00");


        setContentView(R.layout.activity_login_layout);
        ButterKnife.bind(this);

        ActivityCollector.getInstance().pushActivity(this);

        listener = new BaseUiListener();


        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mEditor = mSharedPreferences.edit();

        //QQ登入
        mQQLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //显示进度条
                mProgressbar.setVisibility(View.VISIBLE);

                //选择自动登入
                mEditor.putBoolean(LOGIN_REMEMBER_LOGIN, true);

                //存入QQ登入的统一密码
                mEditor.putBoolean(LOGIN_REMEMBER_PASSWORD, false);

                mEditor.commit();

                QQLogin();
            }
        });


        //恢复用户名
        String userName = mSharedPreferences.getString(LOGIN_USERNAME, "");
        mUserName.setText(userName);


        //恢复密码
        isRememberPassword = mSharedPreferences.getBoolean(LOGIN_REMEMBER_PASSWORD, false);
        if (isRememberPassword) {
            String Password = mSharedPreferences.getString(LOGIN_PASSWORD, "");
            mPassword.setText(Password);
            passwordCheckBox.setChecked(true);
        }

        //避免重复登入
        isAutoChange = this.getIntent().getBooleanExtra(StartActivity.AUTOCHANGE, true);
        isRemeberLogin = mSharedPreferences.getBoolean(LOGIN_REMEMBER_LOGIN, false);
        if(isRemeberLogin){
            loginCheckBox.setChecked(true);
        }
        if (isRemeberLogin && isAutoChange) {
            cn.bmob.v3.BmobUser mBmobUser2 = BmobUser.getCurrentUser(this);
            if (mBmobUser2 != null) {
                Intent intent = new Intent(LoginActivity.this, StartActivity.class);
                intent.putExtra(LOGIN_USERNAME, mBmobUser2.getUsername());
                loginCheckBox.setChecked(true);
                startActivity(intent);
                ActivityCollector.getInstance().closeActivity(LoginActivity.this);

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
                mEditor.putString(LOGIN_USERNAME, mUserName.getText().toString());

                //选择自动登入
                if (loginCheckBox.isChecked()) {
                    mEditor.putBoolean(LOGIN_REMEMBER_LOGIN, true);
                }else {
                    mEditor.clear();
                }

                //选择保存密码
                if (passwordCheckBox.isChecked()) {
                    mEditor.putString(LOGIN_PASSWORD, mPassword.getText().toString());
                    mEditor.putBoolean(LOGIN_REMEMBER_PASSWORD, true);
                } else {
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
                        BmobUser bmobUser = BmobUser.getCurrentUser(LoginActivity.this, BmobUser.class);
                        Intent intent = new Intent(LoginActivity.this, StartActivity.class);
                        intent.putExtra(LOGIN_USERNAME, bmobUser.getNickName());
                        ActivityCollector.getInstance().closeActivity(LoginActivity.this);
                        startActivity(intent);
                    }

                    @Override
                    public void onFailure(int i, String s) {
                        ExUtils.ToastLong(s);
                        mProgressbar.setVisibility(View.INVISIBLE);
                    }
                });
            }
        });
    }

    private void QQLogin() {
        mAppid = QQConstant.APPID;
        //实例化Tencent类，这是QQ互联服务的核心类

        mTencent = Tencent.createInstance(mAppid, App.getInstance());

        /*通过这句代码，SDK实现了QQ的登录，这个方法有三个参数，第一个参数是context上下文，第二个参数SCOPO 是一个String类型的字符串，表示一些权限
        官方文档中的说明：应用需要获得哪些API的权限，由“，”分隔。例如：SCOPE = “get_user_info,add_t”；所有权限用“all”
        第三个参数，是一个事件监听器，IUiListener接口的实例，这里用的是该接口的实现类 */
        mTencent.login(LoginActivity.this, "all", listener);


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Tencent.onActivityResultData(requestCode, resultCode, data, listener);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollector.getInstance().popActivity(this);
    }

    private class BaseUiListener implements IUiListener{

        //登入成功之后的方法实现
        @Override
        public void onComplete(Object o) {
            ExUtils.Toast("登入成功");
            try {
                //这个openid可以用来实现QQ分享之类的，他是每一个qq用户的唯一标识码
                openidString = ((JSONObject) o).getString("openid");

                //获得openid的hash用来作为qq登入的用户用,永远不会变，并且一一对应
                openidHashcode = openidString.hashCode();

                Log.i(TAG, String.valueOf(openidHashcode));
                access_token = ((JSONObject) o).getString("access_token");
                //Log.i(TAG, access_token);

                //这个url是获取用户信息的接口地址
                url = url + "?access_token=" + access_token + "&oauth_consumer_key=" + mAppid +
                        "&openid=" + openidString + "&format=json";

            } catch (JSONException e) {
                e.printStackTrace();
            }

            //现在我们还想获取一些QQ用户的信息，比如头像，昵称nickname
            getUserInfoInThread();

        }

        @Override
        public void onError(UiError uiError) {
            mProgressbar.setVisibility(View.INVISIBLE);
        }

        @Override
        public void onCancel() {
            mProgressbar.setVisibility(View.INVISIBLE);
        }

        //同步获取用户信息
        public void getUserInfoInThread()
        {
            new Thread(){
                @Override
                public void run() {
                    JSONObject json = null;
                    try {
                        json = mTencent.request(url, null, Constants.HTTP_GET);
                        nicknameString = json.getString("nickname").toString();

                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (HttpUtils.NetworkUnavailableException e) {
                        e.printStackTrace();
                    } catch (HttpUtils.HttpStatusException e) {
                        e.printStackTrace();
                    }

                    //给QQ登入的用户注册一个账号
                    QQRegister(nicknameString, openidHashcode);

                }
            }.start();
        }

    }

    private void QQRegister(final String nickname, final int openidHashcode) {
        BmobUser bmobUser = new BmobUser();
        bmobUser.setUsername(nickname+"_"+openidHashcode);
        //QQ用户的默认密码是123456
        bmobUser.setPassword("123456");
        bmobUser.signUp(LoginActivity.this, new SaveListener() {
            @Override
            public void onSuccess() {
                QQLoginAfterRegister(nickname, openidHashcode);
            }

            @Override
            public void onFailure(int i, String s) {

                QQLoginAfterRegister(nickname, openidHashcode);

            }
        });
    }

    private void QQLoginAfterRegister(final String nickname, final int openidHashcode){
        mBmobUser = new BmobUser();
        mBmobUser.setUsername(nickname+"_"+openidHashcode);
        mBmobUser.setPassword("123456");
        mBmobUser.login(LoginActivity.this, new SaveListener() {
            @Override
            public void onSuccess() {
                //自动保存用户名
                mEditor.putString(LOGIN_USERNAME, nickname);
                mEditor.commit();
                Intent intent = new Intent(LoginActivity.this, StartActivity.class);
                intent.putExtra(LOGIN_USERNAME, nickname);
                ActivityCollector.getInstance().closeActivity(LoginActivity.this);
                startActivity(intent);
                mProgressbar.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onFailure(int i, String s) {
                ExUtils.Toast(s);
                mProgressbar.setVisibility(View.INVISIBLE);
            }
        });
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) { //按下的如果是BACK，同时没有重复
            new AlertDialog.Builder(LoginActivity.this)
                    .setTitle("提示")
                    .setMessage("确认退出？")
                    .setNegativeButton("否", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    })
                    .setPositiveButton("是", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCollector.getInstance().closeAllActivity();

                        }
                    })
                    .show();
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }


}

