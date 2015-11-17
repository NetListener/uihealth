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
public class ForumTopicAdapter extends RecyclerView.Adapter<ForumTopicAdapter.ForumViewHoler> implements
        View.OnClickListener{

    private Context mContext;
    private List<Forum> mForumList;

    private OnRecyclerViewItemClickListener mListener;

    public interface OnRecyclerViewItemClickListener{
        void onClick(View v, Object obj);
    }

    public void setOnItemClickListener(OnRecyclerViewItemClickListener onItemClickListener){
        this.mListener = onItemClickListener;
    }


    public ForumTopicAdapter(Context context, List<Forum> list) {
        this.mContext = context;
        mForumList = list;
    }

    @Override
    public ForumViewHoler onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_fragment_topic, parent, false);
        ForumViewHoler viewHoler = new ForumViewHoler(view);

        view.setOnClickListener(this);

        return viewHoler;
    }

    @Override
    public void onBindViewHolder(ForumViewHoler holder, int position) {

        holder.itemView.setTag(mForumList.get(position));

        holder.mTvItemForum.setText(mForumList.get(position).getContent());
        holder.mTvItemForumTitle.setText(mForumList.get(position).getTitle());

        if (mForumList.get(position).getPic() != null && !TextUtils.isEmpty(mForumList.get(position).getPic().get(0))) {
            holder.mImgItemForum.setImageURI(Uri.parse(mForumList.get(position).getPic().get(0)));
        }else{
            holder.mImgItemForum.setImageResource(R.mipmap.ic_launcher);
        }

        // TODO: 15-11-16 数据获取为空
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

    @Override
    public void onClick(View v) {
        mListener.onClick(v, v.getTag());
    }

}
