package com.example.think.uihealth.moduel.forum.forum.activity.fragment;

import android.content.Context;
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

import com.afollestad.materialdialogs.MaterialDialog;
import com.example.think.uihealth.R;
import com.example.think.uihealth.model.bean.BmobUser;
import com.example.think.uihealth.model.bean.Forum;
import com.example.think.uihealth.util.ImageProvider;
import com.example.think.uihealth.util.ImageUtils;
import com.kermit.exutils.utils.ExUtils;

import java.io.File;
import java.util.ArrayList;
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
    private String tag;

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
        this.mListener = (WriteTopicFragmentUploadListener) getActivity();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tag = getArguments().getString("tag");
    }


    private String content;
    private String title;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_writetopic_layout, container, false);
        ButterKnife.bind(this, view);
        return view;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        final List<String> pics = new ArrayList<>();

        mImageProvider = new ImageProvider(this);
        mBtnWritetopicInsertimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new MaterialDialog.Builder(getActivity())
                        .title("请选择")
                        .items(new String[]{"添加", "删除"})
                        .itemsCallback(new MaterialDialog.ListCallback() {
                            @Override
                            public void onSelection(MaterialDialog materialDialog, View view, int i, CharSequence charSequence) {
                                switch (i) {
                                    case 0:
                                        addImg();
                                        break;
                                    case 1:
                                        mBtnWritetopicInsertimg.setImageResource(R.drawable.insert_img);
                                        if (pics.size() != 0) {
                                            pics.remove(0);
                                            bmobFile = null;
                                        }
                                        break;
                                }
                            }
                        })
                        .show();
            }
        });

        mButtonTopicSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(mEdWritetopic.getText().toString().trim()) ||
                        TextUtils.isEmpty(mEdWritetopicContent.getText().toString().trim())) {
                    ExUtils.Toast("输入不能为空哦");
                    return;
                }
                title = mEdWritetopic.getText().toString().trim();
                content = mEdWritetopicContent.getText().toString().trim();
                uploadData(title, content, pics);
            }
        });
    }


    private Forum mForum;
    private void uploadData(String topic, String content, final List<String> pics){
        mForum = new Forum();

        mForum.setTime(ExUtils.getTime());
        mForum.setAuthor(BmobUser.getCurrentUser(getContext(), BmobUser.class));
        mForum.setTitle(topic);
        mForum.setContent(content);
        mForum.setTag(tag);

        showProgress("请稍候");
        if(bmobFile != null) {
            bmobFile.uploadblock(getContext(), new UploadFileListener() {
                @Override
                public void onSuccess() {
                    dismissProgress();
                    img = bmobFile.getFileUrl(getContext());
                    pics.add(img);
                    mForum.setPic(pics);
                    ExUtils.Toast("上传文件成功");
                    uploadForm();
                    pics.remove(0);

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
        new MaterialDialog.Builder(getActivity())
                .title("请选择图片来源")
                .items(new String[]{"拍照", "相册"})
                .itemsCallback(new MaterialDialog.ListCallback() {
                    @Override
                    public void onSelection(MaterialDialog materialDialog, View view, int i, CharSequence charSequence) {
                        preImage(i);
                    }
                })
                .show();
    }


    private MaterialDialog dialog;
    public void showProgress(String title){
        dialog = new MaterialDialog.Builder(getActivity())
                .title(title)
                .content("请稍候")
                .progress(true, 100)
                .cancelable(false)
                .show();
    }

    public void dismissProgress(){
        if (dialog != null){
            dialog.dismiss();
        }
    }

    private String img = "";
    private ImageProvider mImageProvider;
    private BmobFile bmobFile;
    public void preImage(int style){
        ImageProvider.OnImageSelectListener onImageSelectListener = new ImageProvider.OnImageSelectListener() {
            @Override
            public void onImageSelect() {
                showProgress("请稍候");
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
        super.onActivityResult(requestCode, resultCode, data);
        mImageProvider.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        bmobFile = null;
        ButterKnife.unbind(this);
    }
}
