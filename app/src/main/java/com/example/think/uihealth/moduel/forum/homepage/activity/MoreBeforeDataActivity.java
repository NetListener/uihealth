package com.example.think.uihealth.moduel.forum.homepage.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.think.uihealth.R;
import com.example.think.uihealth.moduel.forum.homepage.activity.BeforeDataActivity;
import com.example.think.uihealth.widget.CombineTextImageviewView;
import com.kermit.exutils.utils.ActivityCollector;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Zane on 2015/10/15.
 */
public class MoreBeforeDataActivity extends AppCompatActivity {

    @Bind(R.id.textview_morebeforedata_sex)
    CombineTextImageviewView mTextviewSex;
    @Bind(R.id.textview_morebeforedata_age)
    CombineTextImageviewView mTextviewAge;
    @Bind(R.id.textview_morebeforedata_cholesterol)
    CombineTextImageviewView mTextviewCholesterol;
    @Bind(R.id.textview_morebeforedata_smoke)
    CombineTextImageviewView mTextviewSmoke;
    @Bind(R.id.textview_morebeforedata_HDL)
    CombineTextImageviewView mTextviewHDL;
    @Bind(R.id.textview_morebeforedata_bloodpresure)
    CombineTextImageviewView mTextviewBloodPresure;
    @Bind(R.id.textview_morebeforedata_diabetes_mellitus)
    CombineTextImageviewView mTextviewDiabetes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_morebeforedata_layout);
        ButterKnife.bind(this);

        Intent intent = this.getIntent();

        ActivityCollector.getInstance().pushActivity(this);
        mTextviewAge.setText(intent.getStringExtra(BeforeDataActivity.age));
        mTextviewBloodPresure.setText(intent.getStringExtra(BeforeDataActivity.bloodpresure));
        mTextviewCholesterol.setText(intent.getStringExtra(BeforeDataActivity.cholesterol));
        mTextviewDiabetes.setText(intent.getStringExtra(BeforeDataActivity.diabetes_mellitus));
        mTextviewHDL.setText(intent.getStringExtra(BeforeDataActivity.HDL));
        mTextviewSmoke.setText(intent.getStringExtra(BeforeDataActivity.smoke));
        mTextviewSex.setText(intent.getStringExtra(BeforeDataActivity.sex));

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollector.getInstance().popActivity(this);
    }
}
