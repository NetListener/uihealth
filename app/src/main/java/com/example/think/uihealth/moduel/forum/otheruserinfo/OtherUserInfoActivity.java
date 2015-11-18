package com.example.think.uihealth.moduel.forum.otheruserinfo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.think.uihealth.R;
import com.example.think.uihealth.app.App;
import com.example.think.uihealth.model.bean.BmobUser;
import com.example.think.uihealth.moduel.forum.forum.activity.ForumContentActivity;
import com.gc.materialdesign.views.ProgressBarCircularIndeterminate;
import com.kermit.exutils.utils.ExUtils;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by Zane on 15/11/18.
 * 查看别人用户的界面
 */
public class OtherUserInfoActivity extends AppCompatActivity {

    public static final String TAG = "OtherUserInfoActivity";

    @Bind(R.id.imageview_activity_otheruserPhoto)
    ImageView imageviewActivityOtheruserPhoto;
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

    private String userId;
    private BmobQuery<BmobUser> query;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otheruserinfo_layout);
        ButterKnife.bind(this);
        query = new BmobQuery<>();

        Intent intent = getIntent();
        if (intent.getStringExtra(ForumContentActivity.FROUMAUTHOR) != null) {
            userId = intent.getStringExtra(ForumContentActivity.FROUMAUTHOR);
        } else {
            userId = intent.getStringExtra(ForumContentActivity.COMMENTAUTHOR);
        }

    }

    public void fetchData() {

        query.addWhereEqualTo("objectId", userId);
        query.findObjects(App.getInstance(), new FindListener<BmobUser>() {
            @Override
            public void onSuccess(List<BmobUser> list) {
                Log.i(TAG, String.valueOf(list));

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
    }

    @Override
    protected void onResume() {
        super.onResume();
        ButterKnife.unbind(this);
    }
}
