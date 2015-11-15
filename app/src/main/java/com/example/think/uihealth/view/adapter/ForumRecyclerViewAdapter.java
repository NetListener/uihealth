package com.example.think.uihealth.view.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.think.uihealth.R;
import com.example.think.uihealth.model.bean.Forum;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by kermit on 15-11-11.
 */
public class ForumRecyclerViewAdapter extends RecyclerView.Adapter<ForumRecyclerViewAdapter.ForumViewHoler> {

    private Context mContext;
    private List<Forum> mForumList;


    public ForumRecyclerViewAdapter(Context context, List<Forum> list) {
        this.mContext = context;
        mForumList = list;
    }

    @Override
    public ForumViewHoler onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_fragment_topic, parent, false);
        ForumViewHoler viewHoler = new ForumViewHoler(view);
        return viewHoler;
    }

    @Override
    public void onBindViewHolder(ForumViewHoler holder, int position) {
        holder.mTvItemForum.setText(mForumList.get(position).getContent());
        holder.mTvItemForumTitle.setText(mForumList.get(position).getTitle());

        if (mForumList.get(position).getPic() != null && !TextUtils.isEmpty(mForumList.get(position).getPic().get(0))) {
            holder.mImgItemForum.setImageURI(Uri.parse(mForumList.get(position).getPic().get(0)));
        }else{
            holder.mImgItemForum.setImageResource(R.mipmap.ic_launcher);
        }

        if (!TextUtils.isEmpty(mForumList.get(position).getAuthor().getUserPhoto())){
            holder.mImgItemForumFace.setImageURI(Uri.parse(mForumList.get(position).getAuthor().getUserPhoto()));
        }

        holder.mTvItemForumNickname.setText(mForumList.get(position).getAuthor().getNickName());
        holder.mTvItemForumTime.setText(mForumList.get(position).getTime());
        holder.mTvItemForumFeedback.setText("回复 " + mForumList.get(position).getCommentCount());
    }

    @Override
    public int getItemCount() {
        if (mForumList == null) return 0;
        return mForumList.size();
    }


    public boolean setData(List<Forum> forums) {
        if (forums != null) {
            this.mForumList = forums;
            return true;
        }
        return false;
    }

    public boolean addData(List<Forum> forums) {
        if (forums != null) {
            this.mForumList.addAll(forums);
            return true;
        }
        return false;
    }


    class ForumViewHoler extends RecyclerView.ViewHolder {

        @Bind(R.id.tv_item_forum)
        TextView mTvItemForum;
        @Bind(R.id.img_item_forum)
        SimpleDraweeView mImgItemForum;
        @Bind(R.id.img_item_forum_face)
        SimpleDraweeView mImgItemForumFace;
        @Bind(R.id.tv_item_forum_nickname)
        TextView mTvItemForumNickname;
        @Bind(R.id.tv_item_forum_time)
        TextView mTvItemForumTime;
        @Bind(R.id.tv_item_forum_feedback)
        TextView mTvItemForumFeedback;
        @Bind(R.id.tv_item_title_forum)
        TextView mTvItemForumTitle;

        public ForumViewHoler(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
