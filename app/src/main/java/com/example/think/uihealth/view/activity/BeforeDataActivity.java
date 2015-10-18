package com.example.think.uihealth.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.example.think.uihealth.R;
import com.example.think.uihealth.model.BmobUser;
import com.example.think.uihealth.model.BmobUserData;
import com.example.think.uihealth.view.adapter.BeforeDataRecycleviewAdapter;
import com.gc.materialdesign.views.ProgressBarCircularIndeterminate;
import com.kermit.exutils.utils.ActivityCollector;
import com.kermit.exutils.utils.ExUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.DeleteListener;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by Zane on 2015/10/14.
 */
public class BeforeDataActivity extends AppCompatActivity{

    @Bind(R.id.recyclerview_activity_beforedata)
    RecyclerView mRecycleView;
    @Bind(R.id.progressbar_beforedataactivity)
    ProgressBarCircularIndeterminate mProgress;

    private BeforeDataRecycleviewAdapter mAdapter;
    private BmobUser mUser;
    private LinearLayoutManager mManager;

    public static final String age = "age";
    public static final String bloodpresure = "bloodpresure";
    public static final String cholesterol = "cholesterol";
    public static final String diabetes_mellitus = "diabetes_mellitus";
    public static final String HDL = "HDL";
    public static final String sex = "sex";
    public static final String smoke = "smoke";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beforedata_layout);
        ButterKnife.bind(this);

        ActivityCollector.getInstance().pushActivity(this);

        mUser = BmobUser.getCurrentUser(BeforeDataActivity.this, BmobUser.class);
        BmobQuery<BmobUserData> query = new BmobQuery<BmobUserData>();
        query.addWhereEqualTo("mUser", mUser);
        query.order("-updatedAt");
        query.findObjects(BeforeDataActivity.this, new FindListener<BmobUserData>() {
            @Override
            public void onSuccess(final List<BmobUserData> list) {
                mProgress.setVisibility(View.INVISIBLE);
                mAdapter = new BeforeDataRecycleviewAdapter(list);

                //接口回调
                mAdapter.setOnBeforeDataItemClickListener(new BeforeDataRecycleviewAdapter.OnBeforeDataItemClickListener() {
                    @Override
                    public void OnClick(View view, int pos) {
                        // TODO: 2015/10/15 数据查询 和删除
                        Intent intent = new Intent(BeforeDataActivity.this, MoreBeforeDataActivity.class);
                        intent.putExtra(age, list.get(pos).getAge());
                        intent.putExtra(bloodpresure, list.get(pos).getBloodPressure());
                        intent.putExtra(cholesterol, list.get(pos).getTotalCholesterol());
                        intent.putExtra(HDL, list.get(pos).getHDLCholesterol());
                        intent.putExtra(sex, list.get(pos).getSex());
                        intent.putExtra(smoke, list.get(pos).getSmokerValue());
                        startActivity(intent);

                    }

                    @Override
                    public void OnLongClick(View view, final int pos) {
                        BmobUserData mBmobUserData = list.get(pos);
                        mBmobUserData.delete(BeforeDataActivity.this, new DeleteListener() {
                            @Override
                            public void onSuccess() {
                                new MaterialDialog.Builder(BeforeDataActivity.this)
                                        .items(new CharSequence[]{"删除"})
                                        .itemsCallback(new MaterialDialog.ListCallback() {
                                            @Override
                                            public void onSelection(MaterialDialog materialDialog, View view, int i
                                                    , CharSequence charSequence) {
                                                switch (i) {
                                                    case 0:
                                                        List<BmobUserData> mBmobUserData = mAdapter.getmBmobUsers();
                                                        mBmobUserData.remove(pos);
                                                        mAdapter.setmBmobUsers(mBmobUserData);
                                                        mAdapter.notifyDataSetChanged();
                                                        ExUtils.Toast("删除成功");
                                                        break;
                                                }
                                            }
                                        }).show();
                            }

                            @Override
                            public void onFailure(int i, String s) {
                                ExUtils.Toast(s);
                            }
                        });
                    }
                });

                mRecycleView.setAdapter(mAdapter);
                mManager = new LinearLayoutManager(BeforeDataActivity.this);
                mRecycleView.setLayoutManager(mManager);
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
}
