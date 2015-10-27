package com.example.think.uihealth.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.think.uihealth.R;
import com.kermit.exutils.utils.ActivityCollector;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Zane on 2015/10/15.
 */
public class MoreBeforeDataActivity extends AppCompatActivity{

    @Bind(R.id.textview_morebeforedata_age)
    TextView mTextviewAge;
    @Bind(R.id.textview_morebeforedata_bloodpresure)
    TextView mTextviewBloodPresure;
    @Bind(R.id.textview_morebeforedata_cholesterol)
    TextView mTextviewCholesterol;
    @Bind(R.id.textview_morebeforedata_diabetes_mellitus)
    TextView mTextviewDiabetes;
    @Bind(R.id.textview_morebeforedata_HDL)
    TextView mTextviewHDL;
    @Bind(R.id.textview_morebeforedata_sex)
    TextView mTextviewSex;
    @Bind(R.id.textview_morebeforedata_smoke)
    TextView mTextviewSmoke;

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
