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
import android.widget.Toast;

import com.example.think.uihealth.R;
import com.example.think.uihealth.app.App;
import com.example.think.uihealth.model.bean.BmobUser;
import com.example.think.uihealth.model.bean.Follow;
import com.example.think.uihealth.model.bean.UserOtherAttr;
import com.example.think.uihealth.moduel.forum.forum.activity.ForumContentActivity;
import com.facebook.drawee.view.SimpleDraweeView;
import com.gc.materialdesign.views.ProgressBarCircularIndeterminate;
import com.kermit.exutils.utils.ExUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.DeleteListener;
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
    @Bind(R.id.tofollow)
    TextView text_isfollow;

    private BmobQuery<BmobUser> query;
    private BmobQuery<Follow> query_follow;
    private BmobQuery<UserOtherAttr> query_other;
    private BmobUser mUser;
    private UserOtherAttr userOtherAttr;
    private Intent intent;
    private String userId;
    private String followId = "";
    private int otherUserFollowerNumbers;
    private int nowUserFollowingNumbers;
    private Boolean isFollowSuccess;
    private Boolean isCancelSuccess;
    private Boolean isHave;
    private String otherAttrId = "";
    private Boolean isFollow = false;
    private long clickFollowTime;

    public static final int state_follow = 1;
    public static final int state_cancelfollow = -1;

    Handler handle = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    int n = (int) msg.obj;
                    changeMyUserFollowingNumber(n);
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
                if(System.currentTimeMillis() - clickFollowTime > 5000) {
                    if (isFollow == false) {
                        makeFollow();
                    } else {
                        cancelFollow();
                    }
                    clickFollowTime = System.currentTimeMillis();
                } else {
                    ExUtils.Toast("点击不能过于频繁哟～");
                }
            }
        });
    }

    private void initData() {
        query = new BmobQuery<>();
        intent = getIntent();
        mUser = BmobUser.getCurrentUser(App.getInstance(), BmobUser.class);
        query_follow = new BmobQuery<>();
        query_other = new BmobQuery<>();
        userOtherAttr = new UserOtherAttr();

        //判断是否已经关注，改变ISfollow的值,
        BmobQuery<Follow> query_follow1 = new BmobQuery<>();
        query_follow1.addWhereEqualTo("befollowUserId", userId);
        BmobQuery<Follow> query_follow2 = new BmobQuery<>();
        query_follow2.addWhereEqualTo("followUseId", mUser.getObjectId());
        List<BmobQuery<Follow>> andQuerys = new ArrayList<BmobQuery<Follow>>();
        andQuerys.add(query_follow1);
        andQuerys.add(query_follow2);
        query_follow.and(andQuerys);
        query_follow.findObjects(App.getInstance(), new FindListener<Follow>() {
            @Override
            public void onSuccess(List<Follow> list) {
                Log.i(TAG, String.valueOf(list));
                if (list.equals(null)) {
                    isFollow = false;
                    text_isfollow.setText("加关注");
                } else {
                    isFollow = true;
                    text_isfollow.setText("已关注");
                    followId = list.get(0).getObjectId();
                }
            }

            @Override
            public void onError(int i, String s) {
                text_isfollow.setText("加关注");
                isFollow = false;
            }
        });
        Log.i(TAG, String.valueOf(isFollow));
    }

    //取消一个关注关系
    public void cancelFollow(){
        //根据followID删除
        Follow follow = new Follow();
        follow.setObjectId(followId);
        follow.delete(App.getInstance(), new DeleteListener() {
            @Override
            public void onSuccess() {
                isCancelSuccess = true;
                //更改数字
                fetchNowUserData(state_cancelfollow);
                changeOtherUser(isHave, state_cancelfollow);
                text_isfollow.setText("加关注");
                isFollow = false;
            }

            @Override
            public void onFailure(int i, String s) {
                isCancelSuccess = false;
            }
        });
    }
    public void changeOtherUser(Boolean isHave, int actionType){
        //更新他人用户粉丝数据
        if (isHave) {
            UserOtherAttr userOtherAttr = new UserOtherAttr();
            otherUserFollowerNumbers = otherUserFollowerNumbers + actionType;
            userOtherAttr.setFollowers(otherUserFollowerNumbers);
            userOtherAttr.update(App.getInstance(), otherAttrId, new UpdateListener() {
                @Override
                public void onSuccess() {
                    isFollowSuccess = true;
                    isCancelSuccess = true;
                    ExUtils.Toast("取消关注成功！");
                }

                @Override
                public void onFailure(int i, String s) {
                    ExUtils.Toast(s);
                    isFollowSuccess = false;
                    isCancelSuccess = false;
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
                    isFollowSuccess = true;
                }

                @Override
                public void onFailure(int i, String s) {
                    ExUtils.Toast(s);
                    isFollowSuccess = false;
                }
            });
        }
    }
    //增加一个关注关系
    public void makeFollow() {
        mProgressbar.setVisibility(View.VISIBLE);
        Follow follow = new Follow();
        follow.setFollowUseId(mUser.getObjectId());
        follow.setBefollowUserId(userId);
        follow.save(App.getInstance(), new SaveListener() {
            @Override
            public void onSuccess() {

                mProgressbar.setVisibility(View.INVISIBLE);
                //更新本人用户的数据
                fetchNowUserData(state_follow);
                isFollowSuccess = true;
                changeOtherUser(isHave, state_follow);
                text_isfollow.setText("已关注");
                isFollow = true;
            }

            @Override
            public void onFailure(int i, String s) {
                ExUtils.Toast(s);
                mProgressbar.setVisibility(View.INVISIBLE);
                isFollowSuccess = false;
            }
        });


    }

    public void changeMyUserFollowingNumber(int n) {
        BmobUser newUser = new BmobUser();
        nowUserFollowingNumbers = nowUserFollowingNumbers + n;
        newUser.setFollowing(nowUserFollowingNumbers);
        newUser.update(App.getInstance(), mUser.getObjectId(), new UpdateListener() {
            @Override
            public void onSuccess() {
                isFollowSuccess = true;
                Log.i(TAG, String.valueOf(nowUserFollowingNumbers));
            }

            @Override
            public void onFailure(int i, String s) {
                isFollowSuccess = false;
                ExUtils.Toast(s);
            }
        });
    }

    public void fetchNowUserData(final int actionType) {
        query.addWhereEqualTo("objectId", mUser.getObjectId());
        query.findObjects(App.getInstance(), new FindListener<BmobUser>() {
            @Override
            public void onSuccess(List<BmobUser> list) {
                isFollowSuccess = true;
                isCancelSuccess = true;
                nowUserFollowingNumbers = list.get(0).getFollowing();

                Message message = new Message();
                message.what = 1;
                message.obj = actionType;
                handle.sendMessage(message);
            }

            @Override
            public void onError(int i, String s) {
                isFollowSuccess = false;
                isCancelSuccess = false;
                ExUtils.Toast(s);
            }
        });
    }

    public void fetchData() {


        query_follow.findObjects(App.getInstance(), new FindListener<Follow>() {
            @Override
            public void onSuccess(List<Follow> list) {
                if (!list.equals(null)) {
                    for (Follow follow : list) {
                        //otherUserFollowingers.add();
                    }
                }
            }

            @Override
            public void onError(int i, String s) {

            }
        });
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

                if (!user.equals(null)) {
                    textviewAcitivtyOthernickname.setText(user.getNickName());
                    textviewOtherOtherfollowingnumber.setText(String.valueOf(user.getFollowing()));
                    //otherUserFollowerNumbers = user.getFollowers();
                    if (user.getUserPhoto() != "") {
                        imageviewActivityOtheruserPhoto.setImageURI(Uri.parse(user.getUserPhoto()));
                    } else {
                        imageviewActivityOtheruserPhoto.setImageResource(R.drawable.defaultphoto);
                    }

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
