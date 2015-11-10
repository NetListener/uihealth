package com.example.think.uihealth.view.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.think.uihealth.R;
import com.example.think.uihealth.model.BmobUser;
import com.example.think.uihealth.util.GetEditTextWordNumber;
import com.gc.materialdesign.views.ProgressBarCircularIndeterminate;
import com.kermit.exutils.utils.ExUtils;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by Zane on 15/11/9.
 */
public class ChangeNicknameFragment extends Fragment{

    @Bind(R.id.edittext_fragment_changenickename)
    EditText mEditText;
    @Bind(R.id.button_fragment_updatenickname)
    Button mButton;
    @Bind(R.id.progressbar_changenicknamefragment)
    ProgressBarCircularIndeterminate mProgressbar;
    private int nickNameNumber = 0;
    private ChangeInfoFragemnt changeInfoFragemnt = new ChangeInfoFragemnt();
    public static final String NICKNAMEFROMCHANGE = "NICKNAMEFROMCHANGE";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_changenickename_layout, container, false);
        ButterKnife.bind(this, view);

        mEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                nickNameNumber = mEditText.getText().length();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(nickNameNumber == 0){
                    ExUtils.ToastLong("昵称不能为空～");
                }
                else {
                    mProgressbar.setVisibility(View.VISIBLE);
                    //更新昵称
                    BmobUser newUser = new BmobUser();
                    newUser.setNickName(mEditText.getText().toString());
                    BmobUser mUser = BmobUser.getCurrentUser(getActivity(), BmobUser.class);
                    newUser.update(getActivity(), mUser.getObjectId(), new UpdateListener() {
                        @Override
                        public void onSuccess() {
                            mProgressbar.setVisibility(View.INVISIBLE);
                            ExUtils.Toast("修改成功");
                        }

                        @Override
                        public void onFailure(int i, String s) {
                            mProgressbar.setVisibility(View.INVISIBLE);
                            ExUtils.ToastLong(s);
                        }
                    });
                }
            }
        });

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
