package com.example.think.uihealth.view.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.think.uihealth.R;
import com.example.think.uihealth.model.bean.BmobUser;
import com.example.think.uihealth.model.bean.BmobUserData;
import com.example.think.uihealth.model.bean.Human;
import com.example.think.uihealth.strategy.Strategy;
import com.example.think.uihealth.strategy.impl.CalculateCHDStrategy;
import com.gc.materialdesign.views.ProgressBarCircularIndeterminate;
import com.kermit.exutils.utils.ExUtils;

import java.text.NumberFormat;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by Zane on 15/10/30.
 */
public class ResultPreviousFragment extends android.support.v4.app.Fragment{

    @Bind(R.id.textview_resultfragment_result)
    TextView result;
    @Bind(R.id.btn_resultactivity_savedata)
    Button mButtonSaveData;
    @Bind(R.id.progressbar_resulatactivity)
    ProgressBarCircularIndeterminate mProgress;
    @Bind(R.id.imageview_resultactivity_arrow)
    ImageView mArrowImageView;
    @Bind(R.id.btn_resultactivity_comparedata)
    Button mButtonCompare;

    private double mResult;
    private double mLasteResult;
    private Strategy mStrategy;
    private BmobUserData mBmobUserData;
    private Human mHuman;
    private BmobUser mUser;
    private int saveTimes = 0;
    private int isArrowDown;//0 up, 1 down, 2 null;
    private boolean isQuerySuccess;
    private boolean isFirstTest;
    private OnCompareButtonClickListener mCompareButtonClickListener;
    private ResultCompareFragment resultCompareFragment;
    private static final String TAG = "ResultPreviousFragment";


    public interface OnCompareButtonClickListener{
        void compareButtonClick(ResultCompareFragment resultCompareFragment);
    }

    public void setOnCompareButtonClickListener(OnCompareButtonClickListener mCompareButtonClickListener){
        this.mCompareButtonClickListener = mCompareButtonClickListener;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_resultactivity_previous_layout, container, false);
        ButterKnife.bind(this, view);

        //comparebutton
        mButtonCompare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //callback
                if(isFirstTest == false) {
                    mCompareButtonClickListener.compareButtonClick(resultCompareFragment);
                }else {
                    ExUtils.Toast("没有数据进行比较");
                }
            }
        });


        mProgress.setVisibility(View.VISIBLE);

        //查询上一次的数据
        mUser = BmobUser.getCurrentUser(getActivity(), BmobUser.class);
        BmobQuery<BmobUserData> query = new BmobQuery<BmobUserData>();
        query.addWhereEqualTo("mUser", mUser);
        query.order("-updatedAt");
        query.findObjects(getActivity(), new FindListener<BmobUserData>() {
            @Override
            public void onSuccess(List<BmobUserData> list) {
                mHuman = Human.getInstance();
                Log.i(TAG, String.valueOf(list.size()));
                if(list.size() != 0) {
                    isFirstTest = false;
                    mResult = list.get(0).getResult();
                    getLasteResult();
                    mProgress.setVisibility(View.INVISIBLE);
                    isQuerySuccess = true;
                    if (mLasteResult > mResult) {
                        isArrowDown = 0;
                    } else if (mLasteResult < mResult) {
                        isArrowDown = 1;
                    } else if (mLasteResult == mResult) {
                        isArrowDown = 2;
                    }
                    showResult(isArrowDown, isQuerySuccess);
                    //生成比较数据的碎片
                    resultCompareFragment = ResultCompareFragment.newInstance(list, mHuman,
                            (Double.parseDouble(mStrategy.getResult()) * 100));
                }else {
                    isFirstTest = true;
                    mProgress.setVisibility(View.INVISIBLE);
                    getLasteResult();
                    isQuerySuccess = true;
                    isArrowDown = 2;
                    showResult(isArrowDown, isQuerySuccess);
                }
            }

            @Override
            public void onError(int i, String s) {
                //Log.i(TAG, "wrong");
                getLasteResult();
                isQuerySuccess = false;
                mProgress.setVisibility(View.INVISIBLE);
                showResult(2, isQuerySuccess);
            }
        });
        //保存数据的按钮,这个不用给ACTIVITY提供回调方法了。
        mButtonSaveData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mProgress.setVisibility(View.VISIBLE);

                mUser = BmobUser.getCurrentUser(getActivity(), BmobUser.class);
                mBmobUserData = new BmobUserData();
                mBmobUserData.setResult((Double.parseDouble(mStrategy.getResult()) * 100));
                mBmobUserData.setAge(mHuman.getAgeBmob());
                mBmobUserData.setBloodPressure(mHuman.getBloodPressureBmob());
                mBmobUserData.setDiabetesValue(mHuman.getDiabetesValueBmob());
                mBmobUserData.setHDLCholesterol(mHuman.getHDLCholesterolBmob());
                mBmobUserData.setSex(mHuman.getSexBmob());
                mBmobUserData.setSmokerValue(mHuman.getSmolerValueBmob());
                mBmobUserData.setTotalCholesterol(mHuman.getTotalCholesterolBmob());
                mBmobUserData.setmUser(mUser);
                mBmobUserData.save(getActivity(), new SaveListener() {
                    @Override
                    public void onSuccess() {
                        mProgress.setVisibility(View.INVISIBLE);
                        ExUtils.Toast("保存成功");
                    }

                    @Override
                    public void onFailure(int i, String s) {
                        mProgress.setVisibility(View.INVISIBLE);
                        ExUtils.Toast(s);
                    }
                });
            }
        });

        return view;
    }
    private void showResult(int isArrowDown, boolean isQuerySuccess) {
        //显示结果

        NumberFormat numberFormat = NumberFormat.getNumberInstance();
        numberFormat.setMaximumFractionDigits(2);
        result.setText(numberFormat.format(mLasteResult) + "%");

        if(isQuerySuccess){
            if(isArrowDown == 1){
                mArrowImageView.setImageResource(R.drawable.down);
            }else if(isArrowDown == 0){
                mArrowImageView.setImageResource(R.drawable.up);
            }else if(isArrowDown == 2){
                mArrowImageView.setImageResource(0);
            }
        }
    }
    private void getLasteResult() {
        mStrategy = new CalculateCHDStrategy();
        mStrategy.setValue(Human.getInstance());
        mLasteResult = (Double.parseDouble(mStrategy.getResult()) * 100);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
