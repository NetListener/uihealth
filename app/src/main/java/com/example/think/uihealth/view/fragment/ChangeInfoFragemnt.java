package com.example.think.uihealth.view.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.think.uihealth.R;
import com.example.think.uihealth.app.App;
import com.example.think.uihealth.model.BmobUser;
import com.example.think.uihealth.strategy.Strategy;
import com.example.think.uihealth.util.GetHttpImageView;
import com.gc.materialdesign.views.ProgressBarCircularIndeterminate;
import com.kermit.exutils.utils.ExUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobRealTimeData;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.ValueEventListener;

/**
 * Created by Zane on 15/11/9.
 */
public class ChangeInfoFragemnt extends Fragment {


    public static final String NICKENAME = "NICKENAME";
    public static final String IMAGEVIEW = "IMAGEVIEW";
    public static final String FOLLOWERS = "FOLLOWERS";
    public static final String FOLLOWING = "FOLLOWING";
    public static final String TAG = "ChangeInfoFragment";
    private BmobRealTimeData bmobRealTimeData;
    private BmobUser mUser;
    private String nickName_change;
    private String imageUrl_change;
    private int followers_change;
    private int following_change;
    private BmobQuery<BmobUser> query;

    @Bind(R.id.imageview_changinfofragment_photo)
    ImageView imageviewChanginfofragmentPhoto;
    @Bind(R.id.layout_changeuserphoto)
    LinearLayout layoutChangeuserphoto;
    @Bind(R.id.textview_changeinfofragment_username)
    TextView textviewChangeinfofragmentUsername;
    @Bind(R.id.layout_changeusernickename)
    LinearLayout layoutChangeusernickename;
    @Bind(R.id.progressbar_changinfofragment)
    ProgressBarCircularIndeterminate progressbarChanginfofragment;

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    public static ChangeInfoFragemnt newInstance(String imageUrl, String nickeName, int followers,
                                                 int following) {

        ChangeInfoFragemnt changeInfoFragemnt = new ChangeInfoFragemnt();
        Bundle bundle = new Bundle();
        bundle.putString(NICKENAME, nickeName);
        bundle.putString(IMAGEVIEW, imageUrl);
        bundle.putInt(FOLLOWERS, followers);
        bundle.putInt(FOLLOWING, following);

        changeInfoFragemnt.setArguments(bundle);
        return changeInfoFragemnt;

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_changinfo_layout, container, false);
        ButterKnife.bind(this, view);

        nickName_change = getArguments().getString(NICKENAME);
        imageUrl_change = getArguments().getString(IMAGEVIEW);
        followers_change = getArguments().getInt(FOLLOWERS);
        following_change = getArguments().getInt(FOLLOWING);

        initDate(imageUrl_change, nickName_change, followers_change, following_change);


        //改变昵称的接口调用
        layoutChangeusernickename.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ChangeNicknameFragment changeNicknameFragment = new ChangeNicknameFragment();
                FragmentTransaction transaction1 = getActivity().getSupportFragmentManager().beginTransaction();
                transaction1.replace(R.id.fragment_changinfoactivity, changeNicknameFragment);
                transaction1.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                transaction1.addToBackStack(null);
                transaction1.commit();


            }
        });

//        bmobRealTimeData = new BmobRealTimeData();
//
//        bmobRealTimeData.start(getActivity(), new ValueEventListener() {
//            @Override
//            public void onConnectCompleted() {
//                ExUtils.Toast("连接成功");
//                Log.i(TAG, "hehe");
//                if(bmobRealTimeData.isConnected()){
//                    bmobRealTimeData.subRowDelete("_User", mUser.getObjectId());
//                }else {
//                    Log.i(TAG, "wrong");
//                }
//            }
//
//            @Override
//            public void onDataChange(JSONObject jsonObject) {
//                try {
//                    if(BmobRealTimeData.ACTION_UPDATETABLE.equals(jsonObject.optString("action"))) {
//                        nickName_change = jsonObject.getString("nickName");
//                        imageUrl_change = jsonObject.getString("userPhoto");
//                        followers_change = jsonObject.getInt("Followers");
//                        following_change = jsonObject.getInt("Following");
//                    }
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//                Log.i(TAG, String.valueOf(jsonObject));
//
//            }
//        });


        return view;
    }



    @Override
    public void onStart() {
        super.onStart();
        //mUser = BmobUser.getCurrentUser(getActivity(), BmobUser.class);
        String userId = mUser.getObjectId();
        query.addWhereEqualTo("objectId", userId);
        query.findObjects(App.getInstance(), new FindListener<BmobUser>() {
            @Override
            public void onSuccess(List<BmobUser> list) {
                Log.i(TAG, String.valueOf(list));
                if (!list.equals(null)) {

                    textviewChangeinfofragmentUsername.setText(list.get(0).getNickName());
                    imageUrl_change = list.get(0).getUserPhoto();

                } else {
                    ExUtils.ToastLong("用户查询失败");

                }

            }

            @Override
            public void onError(int i, String s) {
                ExUtils.ToastLong(s);

            }
        });
    }

    public void initDate(String imageUrl, String nickName, int followers, int following) {
        textviewChangeinfofragmentUsername.setText(nickName);
        mUser = BmobUser.getCurrentUser(getActivity(), BmobUser.class);
        query = new BmobQuery<BmobUser>();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }
}
