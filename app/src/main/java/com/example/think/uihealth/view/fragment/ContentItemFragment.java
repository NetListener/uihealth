package com.example.think.uihealth.view.fragment;

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
import com.example.think.uihealth.model.Human;
import com.example.think.uihealth.strategy.Strategy;
import com.example.think.uihealth.strategy.impl.CalculateCHDStrategy;
import com.example.think.uihealth.view.adapter.ContentRecycleviewAdapter;

import org.apache.http.impl.io.ChunkedInputStream;

import butterknife.Bind;
import butterknife.ButterKnife;

import static com.example.think.uihealth.config.TestContent.TITLES;

/**
 * Created by think on 2015/9/22.
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

        // TODO: 2015/9/22 设置recycleview
        mAdapter = new ContentRecycleviewAdapter(getActivity(), mContents);

        // TODO: 2015/9/23 添加最后一个页面实现fragment跳转的逻辑 以及存储信息的逻辑
        mAdapter.setOnItemClickListener(new ContentRecycleviewAdapter.OnItemClickListener() {
            @Override
            public void OnClick(View view, int pos) {

                if (title.equals(TITLES[0])){
                    switch (pos) {
                        case 0:
                            Human.getInstance().setSex(Human.MALE);
                            break;
                        case 1:
                            Human.getInstance().setSex(Human.FEMALE);
                            break;
                        default:
                            Human.getInstance().setSex(Human.MALE);
                    }
                }else if (title.equals(TITLES[1])){
                    switch (pos){
                        case 0:
                            Human.getInstance().setAge(27);
                            break;
                        case 1:
                            Human.getInstance().setAge(37);
                            break;
                        case 2:
                            Human.getInstance().setAge(42);
                            break;
                        case 3:
                            Human.getInstance().setAge(46);
                            break;
                        case 4:
                            Human.getInstance().setAge(52);
                            break;
                        case 5:
                            Human.getInstance().setAge(56);
                            break;
                        case 6:
                            Human.getInstance().setAge(62);
                            break;
                        case 7:
                            Human.getInstance().setAge(67);
                            break;
                        case 8:
                            Human.getInstance().setAge(72);
                            break;
                        case 9:
                            Human.getInstance().setAge(76);
                            break;
                        default:
                            Human.getInstance().setAge(27);
                    }
                }else if (title.equals(TITLES[2])){
                    switch (pos){
                        case 0:
                            Human.getInstance().setTotalCholesterol(CalculateCHDStrategy.CHOLESTEROL_UNDER_160);
                            break;
                        case 1:
                            Human.getInstance().setTotalCholesterol(CalculateCHDStrategy.CHOLESTEROL_160_TO_199);
                            break;
                        case 2:
                            Human.getInstance().setTotalCholesterol(CalculateCHDStrategy.CHOLESTEROL_200_TO_239);
                            break;
                        case 3:
                            Human.getInstance().setTotalCholesterol(CalculateCHDStrategy.CHOLESTEROL_240_TO_279);
                            break;
                        case 4:
                            Human.getInstance().setTotalCholesterol(CalculateCHDStrategy.CHOLESTEROL_NOT_UNDER_280);
                            break;
                        default:
                            Human.getInstance().setTotalCholesterol(CalculateCHDStrategy.CHOLESTEROL_UNDER_160);
                    }
                }else if (title.equals(TITLES[3])){
                    switch (pos){
                        case 0:
                            Human.getInstance().setSmokerValue(CalculateCHDStrategy.SMOKER);
                            break;
                        case 1:
                            Human.getInstance().setSmokerValue(CalculateCHDStrategy.NOT_SMOKER);
                            break;
                        default:
                            Human.getInstance().setSmokerValue(CalculateCHDStrategy.NOT_SMOKER);

                    }
                }else if (title.equals(TITLES[4])){
                    switch (pos){
                        case 0:
                            Human.getInstance().setHDLCholesterol(CalculateCHDStrategy.HDL_CHOLESTEROL_UNDER_35);
                            break;
                        case 1:
                            Human.getInstance().setHDLCholesterol(CalculateCHDStrategy.HDL_CHOLESTEROL_35_TO_44);
                            break;
                        case 2:
                            Human.getInstance().setHDLCholesterol(CalculateCHDStrategy.HDL_CHOLESTEROL_45_TO_49);
                            break;
                        case 3:
                            Human.getInstance().setHDLCholesterol(CalculateCHDStrategy.HDL_CHOLESTEROL_50_TO_59);
                            break;
                        case 4:
                            Human.getInstance().setHDLCholesterol(CalculateCHDStrategy.HDL_CHOLESTEROL_NOT_UNDER_60);
                            break;
                        default:
                            Human.getInstance().setHDLCholesterol(CalculateCHDStrategy.HDL_CHOLESTEROL_35_TO_44);
                    }
                }else if (title.equals(TITLES[5])){
                    switch (pos){
                        case 0:
                            Human.getInstance().setBloodPressure(CalculateCHDStrategy.NORMAL);
                            break;
                        case 1:
                            Human.getInstance().setBloodPressure(CalculateCHDStrategy.HIGH_NORMAL);
                            break;
                        case 2:
                            Human.getInstance().setBloodPressure(CalculateCHDStrategy.STATE_I_HYPERTENSION);
                            break;
                        case 3:
                            Human.getInstance().setBloodPressure(CalculateCHDStrategy.STATE_II_HYPERTENSION);
                            break;
                        default:
                            Human.getInstance().setBloodPressure(CalculateCHDStrategy.NORMAL);
                    }
                }else if (title.equals(TITLES[6])){
                    switch (pos){
                        case 0:
                            Human.getInstance().setDiabetesValue(CalculateCHDStrategy.NOT_DIABETES);
                            break;
                        case 1:
                            Human.getInstance().setDiabetesValue(CalculateCHDStrategy.DIABETES);
                            break;
                        default:
                            Human.getInstance().setDiabetesValue(CalculateCHDStrategy.NOT_DIABETES);
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
