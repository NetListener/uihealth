package com.example.think.uihealth.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.think.uihealth.R;
import com.example.think.uihealth.model.bean.BmobUser;
import com.example.think.uihealth.model.bean.Forum;
import com.example.think.uihealth.util.GetHttpImageView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by kermit on 15-11-11.
 */
public class ForumRecyclerViewAdapter extends RecyclerView.Adapter<ForumRecyclerViewAdapter.ForumViewHoler> {

    private Context mContext;
    private List<Forum> mForumList;

    public ForumRecyclerViewAdapter(Context context) {
        this.mContext = context;
    }


    public ForumRecyclerViewAdapter(Context context, List<Forum> list) {
        this.mContext = context;
        setData(list);
    }

    @Override
    public ForumViewHoler onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.fragment_forum_item_layout, parent, false);
        ForumViewHoler viewHoler = new ForumViewHoler(view);
        return viewHoler;
    }

    @Override
    public void onBindViewHolder(ForumViewHoler holder, int position) {
        // TODO: 15-11-11 绑定数据
        holder.mTvItemForum.setText(mForumList.get(position).getTitle());
        holder.mImgItemForum.setImageBitmap(GetHttpImageView.getHttpBitmap(mForumList.get(position).getPic().get(0)));

        BmobUser user = (BmobUser)BmobUser.getCurrentUser(mContext);
        holder.mImgItemForumFace.setImageBitmap(GetHttpImageView.getHttpBitmap(user.getUserPhoto()));
        holder.mTvItemForumNickname.setText(user.getNickName());

        holder.mTvItemForumTime.setText(mForumList.get(position).getTime());
        holder.mTvItemForumFeedback.setText(mForumList.get(position).getCommentCount());
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
        ImageView mImgItemForum;
        @Bind(R.id.img_item_forum_face)
        ImageView mImgItemForumFace;
        @Bind(R.id.tv_item_forum_nickname)
        TextView mTvItemForumNickname;
        @Bind(R.id.tv_item_forum_time)
        TextView mTvItemForumTime;
        @Bind(R.id.tv_item_forum_feedback)
        TextView mTvItemForumFeedback;

        public ForumViewHoler(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
