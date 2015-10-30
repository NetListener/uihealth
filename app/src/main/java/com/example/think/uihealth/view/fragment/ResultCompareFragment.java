package com.example.think.uihealth.view.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.think.uihealth.R;
import com.example.think.uihealth.widget.CombineTextImageviewView;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Zane on 15/10/29.
 */
public class ResultCompareFragment extends android.support.v4.app.Fragment {

    @Bind(R.id.textview_resultfragment_sex)
    TextView mTextviewResultfragmentSex;
    @Bind(R.id.textview_resultfragment_sexresult)
    CombineTextImageviewView mTextviewResultfragmentSexresult;
    //eg.上次测试：72.8%
    @Bind(R.id.textview_resultfragment_beforeresult)
    TextView mTextviewResultfragmentBeforeresult;
    @Bind(R.id.textview_resultfragment_beforesexresult)
    CombineTextImageviewView mTextviewResultfragmentBeforesexresult;

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
