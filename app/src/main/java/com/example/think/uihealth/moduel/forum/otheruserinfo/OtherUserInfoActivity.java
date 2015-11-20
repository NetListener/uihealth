package com.example.think.uihealth.moduel.forum.otheruserinfo;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.think.uihealth.R;
import com.example.think.uihealth.app.App;
import com.example.think.uihealth.model.bean.BmobUser;
import com.example.think.uihealth.model.bean.Follow;
import com.example.think.uihealth.moduel.forum.forum.activity.ForumContentActivity;
import com.facebook.drawee.view.SimpleDraweeView;
import com.gc.materialdesign.views.ProgressBarCircularIndeterminate;
import com.kermit.exutils.utils.ExUtils;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.bmob.v3.BmobQuery;
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
    private BmobUser mUser;
    private Intent intent;
    private List<String> nowUserFollowings;
    private List<String> otherUserFollowers;
    private String userId;
    private Boolean isSuccess = false;

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
                ExUtils.Toast("关注成功!");
                mProgressbar.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onFailure(int i, String s) {
                ExUtils.Toast(s);
                mProgressbar.setVisibility(View.INVISIBLE);
            }
        });
//        BmobQuery<BmobUser> query1 = new BmobQuery<>();
//        query1.addWhereEqualTo("objectId", mUser.getObjectId());
//        query1.findObjects(App.getInstance(), new FindListener<BmobUser>() {
//            @Override
//            public void onSuccess(List<BmobUser> list) {
//                mProgressbar.setVisibility(View.INVISIBLE);
//                if (!list.equals(null)) {
//                    nowUserFollowings = list.get(0).getUserFollowings();
//                    nowUserFollowings.add(userId);
//                }
//                //更新当前用户
//                BmobUser newUser = new BmobUser();
//                newUser.setUserFollowings(nowUserFollowings);
//                newUser.update(App.getInstance(), mUser.getObjectId(), new UpdateListener() {
//                    @Override
//                    public void onSuccess() {
//                        isSuccess = true;
//                    }
//
//                    @Override
//                    public void onFailure(int i, String s) {
//                        isSuccess = false;
//                        ExUtils.Toast(s+"2");
//                    }
//                });
//                BmobUser newUser2 = new BmobUser();
//                newUser2.setUserFollowers(otherUserFollowers);
//                Log.i(TAG, userId);
//                newUser2.update(App.getInstance(), userId, new UpdateListener() {
//                    @Override
//                    public void onSuccess() {
//                        isSuccess = true;
//                    }
//
//                    @Override
//                    public void onFailure(int i, String s) {
//                        ExUtils.Toast(s + "1");
//                        isSuccess = false;
//                    }
//                });
//            }
//
//            @Override
//            public void onError(int i, String s) {
//                mProgressbar.setVisibility(View.INVISIBLE);
//                ExUtils.ToastLong(s+"3");
//            }
//        });
//
//        if(isSuccess == true){
//            ExUtils.Toast("success!");
//        } else {
//            ExUtils.Toast("failed");
//        }

    }

    public void fetchData() {

        query.addWhereEqualTo("objectId", userId);
        query.findObjects(App.getInstance(), new FindListener<BmobUser>() {
            @Override
            public void onSuccess(List<BmobUser> list) {
//                Log.i(TAG, String.valueOf(list));
                BmobUser user = list.get(0);

                if(!user.equals(null)){
                    textviewAcitivtyOthernickname.setText(user.getNickName());
                    textviewActivityOtherfollowernumber.setText(String.valueOf(user.getFollowers()));
                    textviewOtherOtherfollowingnumber.setText(String.valueOf(user.getFollowing()));
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
