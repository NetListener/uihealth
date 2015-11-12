package com.example.think.uihealth.view.fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.think.uihealth.R;
import com.example.think.uihealth.model.bean.BmobUser;
import com.example.think.uihealth.model.bean.Forum;
import com.example.think.uihealth.util.ImageProvider;
import com.example.think.uihealth.util.ImageUtils;
import com.gc.materialdesign.widgets.ProgressDialog;
import com.kermit.exutils.utils.ExUtils;

import java.io.File;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UploadFileListener;

/**
 * Created by kermit on 15-11-11.
 */
public class WriteTopicFragment extends Fragment {

    @Bind(R.id.ed_writetopic)
    EditText mEdWritetopic;
    @Bind(R.id.btn_writetopic_insertimg)
    ImageView mBtnWritetopicInsertimg;
    @Bind(R.id.ed_writetopic_content)
    EditText mEdWritetopicContent;
    @Bind(R.id.button_topic_send)
    Button mButtonTopicSend;

    private static WriteTopicFragment fragment;

    private WriteTopicFragmentUploadListener mListener;
    public interface WriteTopicFragmentUploadListener{
        void UploadForumSuccess();
    }

    public static WriteTopicFragment newInstance() {
        if (fragment == null) {
            fragment = new WriteTopicFragment();
        }
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mListener = (WriteTopicFragmentUploadListener) context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_writetopic_layout, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    private String content;
    private String title;
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        mButtonTopicSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(mEdWritetopic.getText().toString().trim()) ||
                        TextUtils.isEmpty(mEdWritetopicContent.getText().toString().trim())) {
                    ExUtils.Toast("输入不能为空哦");
                    return;
                }
                content = mEdWritetopic.getText().toString().trim();
                title = mEdWritetopicContent.getText().toString().trim();

            }
        });
    }

    private Forum mForum;

    private void uploadData(String topic, String content){
        mForum = new Forum();

        mForum.setTime(ExUtils.getTime());
        mForum.setAuthor((BmobUser) BmobUser.getCurrentUser(getActivity()));
        mForum.setTitle(title);
        mForum.setContent(content);

        showProgress();
        if(bmobFile != null) {
            bmobFile.uploadblock(getActivity(), new UploadFileListener() {
                @Override
                public void onSuccess() {
                    img = bmobFile.getFileUrl(getActivity());
                    pics.add(img);
                    mForum.setPic(pics);
                    ExUtils.Toast("上传文件成功");
                    uploadForm();
                    mListener.UploadForumSuccess();
                }

                @Override
                public void onFailure(int i, String s) {
                    ExUtils.Toast("上传文件失败");
                    dismissProgress();
                }
            });
        }else{
            uploadForm();
        }
    }

    private void uploadForm() {
        mForum.save(getActivity(), new SaveListener() {
            @Override
            public void onSuccess() {
                ExUtils.Toast("success");
                dismissProgress();
            }

            @Override
            public void onFailure(int i, String s) {
                ExUtils.Toast("fail");
                dismissProgress();
            }
        });
    }

    private void addImg(){
        new AlertDialog.Builder(getActivity())
                .setItems(new String[]{"相册", "照相"}, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        preImage(which);
                    }
                })
                .create()
                .show();
    }


    private ProgressDialog mDialog;
    private void showProgress(){
        mDialog = new ProgressDialog(getActivity(), "请稍候");
        mDialog.show();
    }

    private void dismissProgress(){
        mDialog.dismiss();
    }

    private String img = "";
    private ImageProvider mImageProvider;
    private List<String> pics;
    private BmobFile bmobFile;
    public void preImage(int style){
        ImageProvider.OnImageSelectListener onImageSelectListener = new ImageProvider.OnImageSelectListener() {
            @Override
            public void onImageSelect() {
                showProgress();
            }

            @Override
            public void onImageLoaded(Uri uri) {
                dismissProgress();
                if (uri != null) {
                    ImageUtils.SaveBitmap(ImageUtils.readBitmapFromFile(uri.getPath(), 800, 400), uri.getPath());
                    bmobFile = new BmobFile(new File(uri.getPath()));
                    mBtnWritetopicInsertimg.setImageURI(uri);
                }
            }

            @Override
            public void onError() {
                ExUtils.Toast("上传文件失败");
            }
        };
        switch (style){
            case 0:
                mImageProvider.getImageFromCamera(onImageSelectListener);
                break;
            case 1:
                mImageProvider.getImageFromAlbum(onImageSelectListener);
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        mImageProvider.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
