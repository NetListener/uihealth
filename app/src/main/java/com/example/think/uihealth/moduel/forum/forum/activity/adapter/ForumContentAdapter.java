package com.example.think.uihealth.moduel.forum.forum.activity.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.think.uihealth.R;
import com.example.think.uihealth.model.bean.Comment;
import com.example.think.uihealth.model.bean.Forum;
import com.facebook.drawee.view.SimpleDraweeView;
import com.kermit.exutils.utils.ExUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by kermit on 15-11-11.
 *
 * modify by Zane on 15-11-18
 */
public class ForumContentAdapter extends RecyclerView.Adapter {

    private final static int TOPIC = 12580;
    private final static int COMMENT = 12581;


    private Context mContext;
    private Forum mForum;
    private List<Comment> commentList;
    private OnForumAuthorClickListener onForumAuthorClickListener;
    private OnCommentAuthorClickListener onCommentAuthorClickListener;

    public void setOnForumAuthorClickListener(OnForumAuthorClickListener onForumAuthorClickListener){
        this.onForumAuthorClickListener = onForumAuthorClickListener;
    }
    public interface OnForumAuthorClickListener{
        void OnForumAuthorClick();
    }
    public void setOnCommentAuthorClickListener(OnCommentAuthorClickListener onCommentAuthorClickListener){
        this.onCommentAuthorClickListener = onCommentAuthorClickListener;
    }
    public interface OnCommentAuthorClickListener{
        void OnCommentAuthorClick(int position);
    }

    public ForumContentAdapter(Context context, Forum contents) {
        mContext = context;
        mForum = contents;
    }

    /**
     * 数据修改
     *
     * @param mForums
     * @return
     */
    public boolean setForum(Forum mForums) {
        if (mForums != null) {
            this.mForum = mForums;
            return true;
        }
        return false;
    }

    public boolean setComments(List<Comment> commentList) {
        if (commentList != null) {
            this.commentList = commentList;
            return true;
        }
        return false;
    }

    public boolean addComments(List<Comment> commentList){
        if(commentList != null){
            this.commentList.addAll(commentList);
            return true;
        }
        return false;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return TOPIC;
        } else {
            return COMMENT;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        RecyclerView.ViewHolder viewHolder = null;
        switch (viewType) {
            case TOPIC:
                view = LayoutInflater.from(mContext).inflate(R.layout.item_activity_forumcontenttopic, parent, false);
                viewHolder = new ForumTopicViewHolder(view);
                break;
            case COMMENT:
                view = LayoutInflater.from(mContext).inflate(R.layout.item_activity_forumcontent, parent, false);
                viewHolder = new ForumCommentViewHolder(view);
                break;
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        // TODO: 15-11-11  viewholer数据绑定
        if (position == 0) {
            ForumTopicViewHolder contentViewHolder = (ForumTopicViewHolder) holder;

            contentViewHolder.mImgItemForumcontentFace.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onForumAuthorClickListener.OnForumAuthorClick();
                }
            });

            contentViewHolder.mTvItemForumcontent.setText(mForum.getContent());
            contentViewHolder.mTvItemTitleForumcontent.setText(mForum.getTitle());

            if (mForum.getPic() != null && !TextUtils.isEmpty(mForum.getPic().get(0))) {
                contentViewHolder.mImgItemForumcontent.setImageURI(Uri.parse(mForum.getPic().get(0)));
            } else {
                contentViewHolder.mImgItemForumcontent.setImageResource(R.mipmap.ic_launcher);
            }

            // TODO: 15-11-16 数据获取为空
            if (!TextUtils.isEmpty(mForum.getAuthor().getUserPhoto())) {
                contentViewHolder.mImgItemForumcontentFace.setImageURI(Uri.parse(mForum.getAuthor().getUserPhoto()));
            }

            contentViewHolder.mTvItemForumcontentNickname.setText(mForum.getAuthor().getNickName());
            contentViewHolder.mTvItemForumcontentTime.setText(mForum.getTime());
            contentViewHolder.mTvItemForumcontentFeedback.setText("回复 " + mForum.getCommentCount());
        } else {
            ForumCommentViewHolder commentViewHolder = (ForumCommentViewHolder) holder;

            commentViewHolder.mImgForumcommentFace.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onCommentAuthorClickListener.OnCommentAuthorClick(position);
                }
            });

            if (!TextUtils.isEmpty(commentList.get(position - 1).getAuthor().getUserPhoto())) {
                commentViewHolder.mImgForumcommentFace.setImageURI(Uri.parse(commentList.get(position - 1).getAuthor().getUserPhoto()));
            }else{
                commentViewHolder.mImgForumcommentFace.setImageResource(R.mipmap.ic_launcher);
            }
            commentViewHolder.mTvForumcomment.setText(commentList.get(position - 1).getContent());
            commentViewHolder.mTvForumcommentNickname.setText(commentList.get(position - 1).getAuthor().getNickName());
            commentViewHolder.mTvForumcommentTime.setText(commentList.get(position - 1).getTime());
        }
    }


    @Override
    public int getItemCount() {
        if (mForum == null) return 0;
        else if (commentList == null) return 1;
        return commentList.size() + 1;
    }

    class ForumTopicViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.tv_item_title_forumcontent)
        TextView mTvItemTitleForumcontent;
        @Bind(R.id.tv_item_forumcontent)
        TextView mTvItemForumcontent;
        @Bind(R.id.img_item_forumcontent)
        SimpleDraweeView mImgItemForumcontent;
        @Bind(R.id.img_item_forumcontent_face)
        SimpleDraweeView mImgItemForumcontentFace;
        @Bind(R.id.tv_item_forumcontent_nickname)
        TextView mTvItemForumcontentNickname;
        @Bind(R.id.tv_item_forumcontent_time)
        TextView mTvItemForumcontentTime;
        @Bind(R.id.tv_item_forumcontent_feedback)
        TextView mTvItemForumcontentFeedback;

        public ForumTopicViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    class ForumCommentViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.tv_forumcomment)
        TextView mTvForumcomment;
        @Bind(R.id.img_forumcomment_face)
        SimpleDraweeView mImgForumcommentFace;
        @Bind(R.id.tv_forumcomment_nickname)
        TextView mTvForumcommentNickname;
        @Bind(R.id.tv_forumcomment_time)
        TextView mTvForumcommentTime;

        public ForumCommentViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
