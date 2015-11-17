package com.example.think.uihealth.moduel.forum.homepage;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.think.uihealth.R;
import com.example.think.uihealth.model.bean.BmobUserData;
import com.example.think.uihealth.model.bean.Human;
import com.example.think.uihealth.widget.CombineTextImageviewView;

import java.io.Serializable;
import java.text.NumberFormat;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Zane on 15/10/29.
 */
public class ResultCompareFragment extends Fragment {


    @Bind(R.id.textview_resultfragment_sexresult)
    CombineTextImageviewView textviewResultfragmentSexresult;
    @Bind(R.id.textview_resultfragment_ageresult)
    CombineTextImageviewView textviewResultfragmentAgeresult;
    @Bind(R.id.textview_resultfragment_choresult)
    CombineTextImageviewView textviewResultfragmentChoresult;
    @Bind(R.id.textview_resultfragment_smokeresult)
    CombineTextImageviewView textviewResultfragmentSmokeresult;
    @Bind(R.id.textview_resultfragment_HDLresult)
    CombineTextImageviewView textviewResultfragmentHDLresult;
    @Bind(R.id.textview_resultfragment_bloodpresureresult)
    CombineTextImageviewView textviewResultfragmentBloodpresureresult;
    @Bind(R.id.textview_resultfragment_diabetes_mellitusresult)
    CombineTextImageviewView textviewResultfragmentDiabetesMellitusresult;
    @Bind(R.id.textview_resultfragment_result_result)
    CombineTextImageviewView textviewResultfragmentResultResult;
    @Bind(R.id.textview_resultfragment_beforesexresult)
    CombineTextImageviewView textviewResultfragmentBeforesexresult;
    @Bind(R.id.textview_resultfragment_beforeageresult)
    CombineTextImageviewView textviewResultfragmentBeforeageresult;
    @Bind(R.id.textview_resultfragment_beforechoresult)
    CombineTextImageviewView textviewResultfragmentBeforechoresult;
    @Bind(R.id.textview_resultfragment_beforesmokeresult)
    CombineTextImageviewView textviewResultfragmentBeforesmokeresult;
    @Bind(R.id.textview_resultfragment_beforeHDLresult)
    CombineTextImageviewView textviewResultfragmentBeforeHDLresult;
    @Bind(R.id.textview_resultfragment_beforebloodpresureresult)
    CombineTextImageviewView textviewResultfragmentBeforebloodpresureresult;
    @Bind(R.id.textview_resultfragment_beforediabetes_mellitusresult)
    CombineTextImageviewView textviewResultfragmentBeforediabetesMellitusresult;
    @Bind(R.id.textview_resultfragment_result_beforeresult)
    CombineTextImageviewView textviewResultfragmentResultBeforeresult;

    public static final String BEFORE_DATA = "BEFORE_DATA";
    public static final String LASTE_DATA = "LASTE_DATA";
    public static final String LASTE_RESULT = "LASTE_RESULT";
    private List<BmobUserData> mBmobUserDataList;
    private Human mHuman;
    private double lasteResult;

    //在ResultPreviousFragment里面去把需要比较的数据传递进来
    public static ResultCompareFragment newInstance(List<BmobUserData> list, Human mHuman, double lasteResult){

        ResultCompareFragment resultCompareFragment = new ResultCompareFragment();

        Bundle bundle = new Bundle();
        bundle.putSerializable(BEFORE_DATA, (Serializable) list);
        bundle.putSerializable(LASTE_DATA, mHuman);
        bundle.putDouble(LASTE_RESULT, lasteResult);
        resultCompareFragment.setArguments(bundle);
        return resultCompareFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_resultactivity_back_layout, container, false);
        ButterKnife.bind(this, view);

        mBmobUserDataList = (List<BmobUserData>) getArguments().getSerializable(BEFORE_DATA);
        mHuman = (Human) getArguments().getSerializable(LASTE_DATA);
        lasteResult = getArguments().getDouble(LASTE_RESULT);

        NumberFormat numberFormat = NumberFormat.getNumberInstance();
        numberFormat.setMaximumFractionDigits(2);

        textviewResultfragmentSexresult.setText(mHuman.getSexBmob());
        textviewResultfragmentAgeresult.setText(mHuman.getAgeBmob());
        textviewResultfragmentChoresult.setText(mHuman.getTotalCholesterolBmob());
        textviewResultfragmentSmokeresult.setText(mHuman.getSmolerValueBmob());
        textviewResultfragmentHDLresult.setText(mHuman.getHDLCholesterolBmob());
        textviewResultfragmentBloodpresureresult.setText(mHuman.getBloodPressureBmob());
        textviewResultfragmentDiabetesMellitusresult.setText(mHuman.getDiabetesValueBmob());
        textviewResultfragmentResultResult.setText(numberFormat.format(lasteResult)+"%");
        textviewResultfragmentBeforesexresult.setText(mBmobUserDataList.get(0).getSex());
        textviewResultfragmentBeforeageresult.setText(mBmobUserDataList.get(0).getAge());
        textviewResultfragmentBeforechoresult.setText(mBmobUserDataList.get(0).getTotalCholesterol());
        textviewResultfragmentBeforesmokeresult.setText(mBmobUserDataList.get(0).getSmokerValue());
        textviewResultfragmentBeforeHDLresult.setText(mBmobUserDataList.get(0).getHDLCholesterol());
        textviewResultfragmentBeforebloodpresureresult.setText(mBmobUserDataList.get(0).getBloodPressure());
        textviewResultfragmentBeforediabetesMellitusresult.setText(mBmobUserDataList.get(0).getDiabetesValue());
        textviewResultfragmentResultBeforeresult.setText(numberFormat.format(mBmobUserDataList.get(0).getResult())+"%");

        return view;
    }



    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
