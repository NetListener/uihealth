package com.example.think.uihealth.view.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bmob.BmobProFile;
import com.example.think.uihealth.R;
import com.example.think.uihealth.app.App;
import com.example.think.uihealth.model.BmobUser;
import com.example.think.uihealth.util.GetHttpImageView;
import com.example.think.uihealth.view.activity.ChangeInfoActivity;
import com.gc.materialdesign.views.ProgressBarCircularIndeterminate;
import com.kermit.exutils.utils.ExUtils;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by Zane on 15/11/9.
 */
public class MyUserInfoFragment extends Fragment {

    @Bind(R.id.imageview_fragment_userPhoto)
    ImageView mImageviewFragmentUserPhoto;
    @Bind(R.id.textview_fragment_nickname)
    TextView mTextviewFragmentNickname;
    @Bind(R.id.layout_changeuserInfo)
    RelativeLayout layoutChangeuserInfo;
    @Bind(R.id.progressbar_userinfofragment)
    ProgressBarCircularIndeterminate progressbarUserinfofragment;

    private String nickName;
    private String imageUrl;
    private BmobProFile bmobProFile;
    private BmobUser mUser;
    private BmobQuery<BmobUser> query;

    public static final String TAG = "MyUserInfoFragment";
    public static final String NICKNAME = "NICKNAME";
    public static final String IMAGEURL = "IMAGEURL";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_startactivity_userinfo, container, false);
        ButterKnife.bind(this, view);

        progressbarUserinfofragment.setVisibility(View.VISIBLE);

        mUser = BmobUser.getCurrentUser(App.getInstance(), BmobUser.class);
        String userId = mUser.getObjectId();

        query = new BmobQuery<BmobUser>();
        query.addWhereEqualTo("objectId", userId);
        query.findObjects(App.getInstance(), new FindListener<BmobUser>() {
            @Override
            public void onSuccess(List<BmobUser> list) {
                Log.i(TAG, String.valueOf(list));
                if (!list.equals(null)) {
                    nickName = list.get(0).getNickName();
                    imageUrl = list.get(0).getUserPhoto();
                    mTextviewFragmentNickname.setText(nickName);
                    if (imageUrl != null) {
                        mImageviewFragmentUserPhoto.setImageBitmap(GetHttpImageView.getHttpBitmap(imageUrl));
                    } else {
                        mImageviewFragmentUserPhoto.setImageResource(R.drawable.defaultphoto);
                    }
                } else {
                    ExUtils.ToastLong("用户查询失败");

                }
                progressbarUserinfofragment.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onError(int i, String s) {
                ExUtils.ToastLong(s);
                progressbarUserinfofragment.setVisibility(View.INVISIBLE);
            }
        });

        layoutChangeuserInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ChangeInfoActivity.class);
                intent.putExtra(NICKNAME, nickName);
                intent.putExtra(IMAGEURL, imageUrl);
                startActivity(intent);
            }
        });

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        String userId = mUser.getObjectId();
        query = new BmobQuery<BmobUser>();
        query.addWhereEqualTo("objectId", userId);
        query.findObjects(App.getInstance(), new FindListener<BmobUser>() {
            @Override
            public void onSuccess(List<BmobUser> list) {
                Log.i(TAG, String.valueOf(list));
                if (!list.equals(null)) {
                    nickName = list.get(0).getNickName();
                    imageUrl = list.get(0).getUserPhoto();
                    mTextviewFragmentNickname.setText(nickName);
                    if (imageUrl != "") {
                        mImageviewFragmentUserPhoto.setImageBitmap(GetHttpImageView.getHttpBitmap(imageUrl));
                    } else {
                        mImageviewFragmentUserPhoto.setImageResource(R.drawable.defaultphoto);
                    }
                } else {
                    ExUtils.ToastLong("用户查询失败");

                }
                progressbarUserinfofragment.setVisibility(View.INVISIBLE);
            }
            @Override
            public void onError(int i, String s) {
                ExUtils.ToastLong(s);
                progressbarUserinfofragment.setVisibility(View.INVISIBLE);
            }
        });
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }


}
