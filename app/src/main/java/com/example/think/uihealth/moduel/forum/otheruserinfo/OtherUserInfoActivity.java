package com.example.think.uihealth.moduel.forum.otheruserinfo;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.think.uihealth.R;
import com.example.think.uihealth.app.App;
import com.example.think.uihealth.model.bean.BmobUser;
import com.example.think.uihealth.model.bean.Follow;
import com.example.think.uihealth.model.bean.UserOtherAttr;
import com.example.think.uihealth.moduel.forum.forum.activity.ForumContentActivity;
import com.facebook.drawee.view.SimpleDraweeView;
import com.gc.materialdesign.views.ProgressBarCircularIndeterminate;
import com.kermit.exutils.utils.ExUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.bmob.v3.AsyncCustomEndpoints;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.CloudCodeListener;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by Zane on 15/11/18.
 * 查看别人用户的界面
 */
public class OtherUserInfoActivity extends AppCompatActivity {

    public static final String TAG = "OtherUserInfoActivity";

    @Bind(R.id.imageview_activity_otheruserPhoto)
    SimpleDraweeView imageviewActivityOtheruserPhoto;
    @Bind(R.id.textview_acitivty_othernickname)
    TextView textviewAcitivtyOthernickname;
    @Bind(R.id.layout_activity_othercheckfollowing)
    RelativeLayout layoutActivityOthercheckfollowing;
    @Bind(R.id.layout_activity_othercheckfollower)
    RelativeLayout layoutActivityOthercheckfollower;
    @Bind(R.id.layout_activity_othercheckforum)
    RelativeLayout layoutActivityOthercheckforum;
    @Bind(R.id.progressbar_userinfofragment)
    ProgressBarCircularIndeterminate mProgressbar;
    @Bind(R.id.layout_activity_tofollow)
    RelativeLayout layoutActivityTofollow;
    @Bind(R.id.layout_activity_tomessage)
    RelativeLayout layoutActivityTomessage;
    @Bind(R.id.textview_other_otherfollowingnumber)
    TextView textviewOtherOtherfollowingnumber;
    @Bind(R.id.textview_activity_otherfollowernumber)
    TextView textviewActivityOtherfollowernumber;

    private BmobQuery<BmobUser> query;
    private BmobQuery<Follow> query_follow;
    private BmobQuery<UserOtherAttr> query_other;
    private BmobUser mUser;
    private UserOtherAttr userOtherAttr;
    private Intent intent;
    private List<String> nowUserFollowings;
    private List<String> otherUserFollowers;
    private String userId;
    private int otherUserFollowerNumbers;
    private int nowUserFollowingNumbers;
    private Boolean isSuccess;
    private Boolean isHave;
    private String otherAttrId = "";

    Handler handle = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1:
                    changeMyUserFollowingNumber();
                    Log.i(TAG, String.valueOf(nowUserFollowingNumbers));
                    if (isSuccess){
                        ExUtils.Toast("关注成功！");
                    } else {
                        ExUtils.Toast("关注失败");
                    }
                    fetchData();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otheruserinfo_layout);
        ButterKnife.bind(this);
        initData();

        if (intent.getStringExtra(ForumContentActivity.FROUMAUTHOR) != null) {
            userId = intent.getStringExtra(ForumContentActivity.FROUMAUTHOR);
        } else {
            userId = intent.getStringExtra(ForumContentActivity.COMMENTAUTHOR);
        }

        fetchData();

        layoutActivityTofollow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makeFollow();
            }
        });
    }

    private void initData(){
        query = new BmobQuery<>();
        intent = getIntent();
        mUser = BmobUser.getCurrentUser(App.getInstance(), BmobUser.class);
        query_follow = new BmobQuery<>();
        query_other = new BmobQuery<>();
        userOtherAttr = new UserOtherAttr();
    }
    //增加一个关注关系
    public void makeFollow(){

        mProgressbar.setVisibility(View.VISIBLE);
        Follow follow = new Follow();
        follow.setFollowUseId(mUser.getObjectId());
        follow.setBefollowUserId(userId);
        follow.save(App.getInstance(), new SaveListener() {
            @Override
            public void onSuccess() {

                mProgressbar.setVisibility(View.INVISIBLE);
                //更新本人用户的数据
                fetchNowUserData();
                isSuccess = true;

                //更新他人用户粉丝数据
                if (isHave){
                    UserOtherAttr userOtherAttr = new UserOtherAttr();
                    userOtherAttr.setFollowers(++otherUserFollowerNumbers);
                    userOtherAttr.update(App.getInstance(), otherAttrId, new UpdateListener() {
                        @Override
                        public void onSuccess() {
                            isSuccess = true;
                        }

                        @Override
                        public void onFailure(int i, String s) {
                            ExUtils.Toast(s);
                            isSuccess = false;
                        }
                    });
                } else {
                    userOtherAttr.setFollowers(1);
                    BmobUser user = new BmobUser();
                    user.setObjectId(userId);
                    userOtherAttr.setUser(user);
                    userOtherAttr.save(App.getInstance(), new SaveListener() {
                        @Override
                        public void onSuccess() {
                            isSuccess = true;
                        }

                        @Override
                        public void onFailure(int i, String s) {
                            ExUtils.Toast(s);
                            isSuccess = false;
                        }
                    });
                }
            }

            @Override
            public void onFailure(int i, String s) {
                ExUtils.Toast(s);
                mProgressbar.setVisibility(View.INVISIBLE);
                isSuccess = false;
            }
        });


    }

    public void changeMyUserFollowingNumber(){
        BmobUser newUser = new BmobUser();
        newUser.setFollowing(++nowUserFollowingNumbers);
        newUser.update(App.getInstance(), mUser.getObjectId(), new UpdateListener() {
            @Override
            public void onSuccess() {
                isSuccess = true;
                Log.i(TAG, String.valueOf(nowUserFollowingNumbers));
            }

            @Override
            public void onFailure(int i, String s) {
                isSuccess = false;
                ExUtils.Toast(s);
            }
        });
    }
    public void fetchNowUserData(){
        query.addWhereEqualTo("objectId", mUser.getObjectId());
        query.findObjects(App.getInstance(), new FindListener<BmobUser>() {
            @Override
            public void onSuccess(List<BmobUser> list) {
                isSuccess = true;
                nowUserFollowingNumbers = list.get(0).getFollowing();

                Message message = new Message();
                message.what = 1;
                handle.sendMessage(message);
            }

            @Override
            public void onError(int i, String s) {
                isSuccess = false;
                ExUtils.Toast(s);
            }
        });
    }

    public void fetchData() {
        //获取他人粉丝数
        BmobUser user = new BmobUser();
        user.setObjectId(userId);
        query_other.addWhereEqualTo("user", user);
        query_other.findObjects(App.getInstance(), new FindListener<UserOtherAttr>() {
            @Override
            public void onSuccess(List<UserOtherAttr> list) {
                textviewActivityOtherfollowernumber.setText(String.valueOf(list.get(0).getFollowers()));
                otherUserFollowerNumbers = list.get(0).getFollowers();
                isHave = true;
                otherAttrId = list.get(0).getObjectId();
            }

            @Override
            public void onError(int i, String s) {
                textviewActivityOtherfollowernumber.setText(String.valueOf(0));
                isHave = false;
            }
        });

        query.addWhereEqualTo("objectId", userId);
        query.findObjects(App.getInstance(), new FindListener<BmobUser>() {
            @Override
            public void onSuccess(List<BmobUser> list) {
//                Log.i(TAG, String.valueOf(list));
                BmobUser user = list.get(0);

                if(!user.equals(null)){
                    textviewAcitivtyOthernickname.setText(user.getNickName());
                    textviewOtherOtherfollowingnumber.setText(String.valueOf(user.getFollowing()));
                    //otherUserFollowerNumbers = user.getFollowers();
                    if (user.getUserPhoto() != "") {
                        imageviewActivityOtheruserPhoto.setImageURI(Uri.parse(user.getUserPhoto()));
                    } else {
                        imageviewActivityOtheruserPhoto.setImageResource(R.drawable.defaultphoto);
                    }
                    otherUserFollowers = user.getUserFollowers();
                    otherUserFollowers.add(mUser.getObjectId());
                }


            }

            @Override
            public void onError(int i, String s) {
                ExUtils.ToastLong(s);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

    @Override
    protected void onResume() {
        super.onResume();

    }
}
