package com.example.think.uihealth.moduel.forum.homepage.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.think.uihealth.R;
import com.example.think.uihealth.config.TestContent;
import com.example.think.uihealth.model.bean.Human;
import com.example.think.uihealth.strategy.impl.CalculateCHDStrategy;
import com.example.think.uihealth.moduel.forum.homepage.adapter.ContentRecycleviewAdapter;

import butterknife.Bind;
import butterknife.ButterKnife;

import static com.example.think.uihealth.config.TestContent.TITLES;

/**
 * Created by Zane on 2015/9/22.
 */
public class ContentItemFragment extends Fragment{

    public static final String TAG = "ContentItemFragment";
    private static final String TITLE = "TITLE";
    private static final String CONTENT = "CONTENT";
    private String[] mContents;
    private String title;
    private ContentRecycleviewAdapter mAdapter;
    private LinearLayoutManager mManager;


    @Bind(R.id.textview_fragment_title)
    TextView mFragmentTextview;
    @Bind(R.id.recyclerview_fragment_content)
    RecyclerView mFragmentRecycleview;


    public static ContentItemFragment newInstance(String title, String[] content){
        ContentItemFragment contentItemFragment = new ContentItemFragment();

        Bundle bundle = new Bundle();
        bundle.putString(TITLE, title);
        bundle.putStringArray(CONTENT, content);

        contentItemFragment.setArguments(bundle);

        return contentItemFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_content_item_layout, container, false);
        ButterKnife.bind(this, view);
        return view;
    }


    @Override
    public void onViewCreated(View view, @Nullable final Bundle savedInstanceState) {

        mContents = getArguments().getStringArray(CONTENT);
        title = getArguments().getString(TITLE);

        mFragmentTextview.setText(title);


        mAdapter = new ContentRecycleviewAdapter(getActivity(), mContents);
        //为点击事件传值给算法
        mAdapter.setOnItemClickListener(new ContentRecycleviewAdapter.OnItemClickListener() {
            @Override
            public void OnClick(View view, int pos) {

                if (title.equals(TITLES[0])){
                    switch (pos) {
                        case 0:
                            Human.getInstance().setSex(Human.MALE);
                            Human.getInstance().setSexBmob(TestContent.GENDER[0]);
                            break;
                        case 1:
                            Human.getInstance().setSex(Human.FEMALE);
                            Human.getInstance().setSexBmob(TestContent.GENDER[1]);
                            break;
                        default:
                            Human.getInstance().setSex(Human.MALE);
                            Human.getInstance().setSexBmob(TestContent.GENDER[0]);
                    }
                }else if (title.equals(TITLES[1])){
                    switch (pos){
                        case 0:
                            Human.getInstance().setAge(27);
                            Human.getInstance().setAgeBmob(TestContent.AGE[0]);
                            break;
                        case 1:
                            Human.getInstance().setAge(37);
                            Human.getInstance().setAgeBmob(TestContent.AGE[1]);
                            break;
                        case 2:
                            Human.getInstance().setAge(42);
                            Human.getInstance().setAgeBmob(TestContent.AGE[2]);
                            break;
                        case 3:
                            Human.getInstance().setAge(46);
                            Human.getInstance().setAgeBmob(TestContent.AGE[3]);
                            break;
                        case 4:
                            Human.getInstance().setAge(52);
                            Human.getInstance().setAgeBmob(TestContent.AGE[4]);
                            break;
                        case 5:
                            Human.getInstance().setAge(56);
                            Human.getInstance().setAgeBmob(TestContent.AGE[5]);
                            break;
                        case 6:
                            Human.getInstance().setAge(62);
                            Human.getInstance().setAgeBmob(TestContent.AGE[6]);
                            break;
                        case 7:
                            Human.getInstance().setAge(67);
                            Human.getInstance().setAgeBmob(TestContent.AGE[7]);
                            break;
                        case 8:
                            Human.getInstance().setAge(72);
                            Human.getInstance().setAgeBmob(TestContent.AGE[8]);
                            break;
                        case 9:
                            Human.getInstance().setAge(76);
                            Human.getInstance().setAgeBmob(TestContent.AGE[9]);
                            break;
                        default:
                            Human.getInstance().setAge(27);
                            Human.getInstance().setAgeBmob(TestContent.AGE[0]);
                    }
                }else if (title.equals(TITLES[2])){
                    switch (pos){
                        case 0:
                            Human.getInstance().setTotalCholesterol(CalculateCHDStrategy.CHOLESTEROL_UNDER_160);
                            Human.getInstance().setTotalCholesterolBmob(TestContent.TOTAL_CHOLESTEROL[0]);
                            break;
                        case 1:
                            Human.getInstance().setTotalCholesterol(CalculateCHDStrategy.CHOLESTEROL_160_TO_199);
                            Human.getInstance().setTotalCholesterolBmob(TestContent.TOTAL_CHOLESTEROL[1]);
                            break;
                        case 2:
                            Human.getInstance().setTotalCholesterol(CalculateCHDStrategy.CHOLESTEROL_200_TO_239);
                            Human.getInstance().setTotalCholesterolBmob(TestContent.TOTAL_CHOLESTEROL[2]);
                            break;
                        case 3:
                            Human.getInstance().setTotalCholesterol(CalculateCHDStrategy.CHOLESTEROL_240_TO_279);
                            Human.getInstance().setTotalCholesterolBmob(TestContent.TOTAL_CHOLESTEROL[3]);
                            break;
                        case 4:
                            Human.getInstance().setTotalCholesterol(CalculateCHDStrategy.CHOLESTEROL_NOT_UNDER_280);
                            Human.getInstance().setTotalCholesterolBmob(TestContent.TOTAL_CHOLESTEROL[4]);
                            break;
                        default:
                            Human.getInstance().setTotalCholesterol(CalculateCHDStrategy.CHOLESTEROL_UNDER_160);
                            Human.getInstance().setTotalCholesterolBmob(TestContent.TOTAL_CHOLESTEROL[0]);
                    }
                }else if (title.equals(TITLES[3])){
                    switch (pos){
                        case 0:
                            Human.getInstance().setSmokerValue(CalculateCHDStrategy.SMOKER);
                            Human.getInstance().setSmolerValueBmob(TestContent.SMOKER[0]);
                            break;
                        case 1:
                            Human.getInstance().setSmokerValue(CalculateCHDStrategy.NOT_SMOKER);
                            Human.getInstance().setSmolerValueBmob(TestContent.SMOKER[1]);
                            break;
                        default:
                            Human.getInstance().setSmokerValue(CalculateCHDStrategy.NOT_SMOKER);
                            Human.getInstance().setSmolerValueBmob(TestContent.SMOKER[0]);
                    }
                }else if (title.equals(TITLES[4])){
                    switch (pos){
                        case 0:
                            Human.getInstance().setHDLCholesterol(CalculateCHDStrategy.HDL_CHOLESTEROL_UNDER_35);
                            Human.getInstance().setHDLCholesterolBmob(TestContent.HDL[0]);
                            break;
                        case 1:
                            Human.getInstance().setHDLCholesterol(CalculateCHDStrategy.HDL_CHOLESTEROL_35_TO_44);
                            Human.getInstance().setHDLCholesterolBmob(TestContent.HDL[1]);
                            break;
                        case 2:
                            Human.getInstance().setHDLCholesterol(CalculateCHDStrategy.HDL_CHOLESTEROL_45_TO_49);
                            Human.getInstance().setHDLCholesterolBmob(TestContent.HDL[2]);
                            break;
                        case 3:
                            Human.getInstance().setHDLCholesterol(CalculateCHDStrategy.HDL_CHOLESTEROL_50_TO_59);
                            Human.getInstance().setHDLCholesterolBmob(TestContent.HDL[3]);
                            break;
                        case 4:
                            Human.getInstance().setHDLCholesterol(CalculateCHDStrategy.HDL_CHOLESTEROL_NOT_UNDER_60);
                            Human.getInstance().setHDLCholesterolBmob(TestContent.HDL[4]);
                            break;
                        default:
                            Human.getInstance().setHDLCholesterol(CalculateCHDStrategy.HDL_CHOLESTEROL_35_TO_44);
                            Human.getInstance().setHDLCholesterolBmob(TestContent.HDL[0]);
                    }
                }else if (title.equals(TITLES[5])){
                    switch (pos){
                        case 0:
                            Human.getInstance().setBloodPressure(CalculateCHDStrategy.NORMAL);
                            Human.getInstance().setBloodPressureBmob(TestContent.HYPERTENSION_MEDITION[0]);
                            break;
                        case 1:
                            Human.getInstance().setBloodPressure(CalculateCHDStrategy.HIGH_NORMAL);
                            Human.getInstance().setBloodPressureBmob(TestContent.HYPERTENSION_MEDITION[1]);
                            break;
                        case 2:
                            Human.getInstance().setBloodPressure(CalculateCHDStrategy.STATE_I_HYPERTENSION);
                            Human.getInstance().setBloodPressureBmob(TestContent.HYPERTENSION_MEDITION[2]);
                            break;
                        case 3:
                            Human.getInstance().setBloodPressure(CalculateCHDStrategy.STATE_II_HYPERTENSION);
                            Human.getInstance().setBloodPressureBmob(TestContent.HYPERTENSION_MEDITION[3]);
                            break;
                        default:
                            Human.getInstance().setBloodPressure(CalculateCHDStrategy.NORMAL);
                            Human.getInstance().setBloodPressureBmob(TestContent.HYPERTENSION_MEDITION[0]);
                    }
                }else if (title.equals(TITLES[6])){
                    switch (pos){
                        case 0:
                            Human.getInstance().setDiabetesValue(CalculateCHDStrategy.DIABETES);
                            Human.getInstance().setDiabetesValueBmob(TestContent.SYSTOLIC_BLOOD_PRESURE[0]);
                            break;
                        case 1:
                            Human.getInstance().setDiabetesValue(CalculateCHDStrategy.NOT_DIABETES);
                            Human.getInstance().setDiabetesValueBmob(TestContent.SYSTOLIC_BLOOD_PRESURE[1]);
                            break;
                        default:
                            Human.getInstance().setDiabetesValue(CalculateCHDStrategy.NOT_DIABETES);
                            Human.getInstance().setDiabetesValueBmob(TestContent.SYSTOLIC_BLOOD_PRESURE[0]);
                    }
                }

                if(!title.equals(TITLES[6])){
                    ContentFragment.getViewPager().arrowScroll(2);
                }else {
                    ContentFragment.getOnLastItemClickListener().activitySkip();
                }
            }
        });

        mFragmentRecycleview.setAdapter(mAdapter);
        mManager = new LinearLayoutManager(getActivity());
        mFragmentRecycleview.setLayoutManager(mManager);

    }
}
