package com.example.think.uihealth.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
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
 * Created by kermit on 15-11-15.
 */
public class ForumOftenAdapter extends RecyclerView.Adapter<ForumOftenAdapter.ForumOftenViewHodler> {

    private Context mContext;

    private List<Forum> mForumList;

    public ForumOftenAdapter(Context context, List<Forum> forumList) {
        mContext = context;
        mForumList = forumList;
    }

    public boolean setData(List<Forum> forums) {
        if (forums != null) {
            mForumList = forums;
            notifyDataSetChanged();
            return true;
        }
        return false;
    }

    public boolean addData(List<Forum> forums) {
        if (forums != null) {
            mForumList.addAll(forums);
            notifyDataSetChanged();
            return true;
        }
        return false;
    }

    @Override
    public ForumOftenViewHodler onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_fragment_oftentopic, parent, false);
        ForumOftenViewHodler viewHodler = new ForumOftenViewHodler(view);
        return viewHodler;
    }

    @Override
    public void onBindViewHolder(ForumOftenViewHodler holder, int position) {
        // TODO: 15-11-11 绑定数据
        holder.mTvItemForumoften.setText(mForumList.get(position).getTitle());

        if (mForumList.get(position).getPic() != null && !TextUtils.isEmpty(mForumList.get(position).getPic().get(0))) {
            holder.mImgItemForumoften.setImageBitmap(GetHttpImageView.getHttpBitmap(mForumList.get(position).getPic().get(0)));
        }

        BmobUser user = BmobUser.getCurrentUser(mContext, BmobUser.class);
        holder.mImgItemForumoftenFace.setImageBitmap(GetHttpImageView.getHttpBitmap(user.getUserPhoto()));
        holder.mTvItemForumoftenNickname.setText(user.getNickName());

        holder.mTvItemForumoftenTime.setText(mForumList.get(position).getTime());
        holder.mTvItemForumoftenFeedback.setText(mForumList.get(position).getCommentCount() + "");
    }

    @Override
    public int getItemCount() {
        return mForumList.size();
    }


    class ForumOftenViewHodler extends RecyclerView.ViewHolder {


        @Bind(R.id.tv_item_forumoften)
        TextView mTvItemForumoften;
        @Bind(R.id.img_item_forumoften)
        ImageView mImgItemForumoften;
        @Bind(R.id.img_item_forumoften_face)
        ImageView mImgItemForumoftenFace;
        @Bind(R.id.tv_item_forumoften_nickname)
        TextView mTvItemForumoftenNickname;
        @Bind(R.id.tv_item_forumoften_time)
        TextView mTvItemForumoftenTime;
        @Bind(R.id.tv_item_forumoften_feedback)
        TextView mTvItemForumoftenFeedback;

        public ForumOftenViewHodler(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
