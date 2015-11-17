package com.example.think.uihealth.moduel.forum;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.think.uihealth.R;
import com.example.think.uihealth.model.bean.BmobUser;
import com.example.think.uihealth.model.bean.Forum;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by kermit on 15-11-15.
 */
public class ForumOftenListAdapter extends
        RecyclerView.Adapter<ForumOftenListAdapter.ForumOftenViewHodler> implements
        View.OnClickListener{

    private Context mContext;
    private List<Forum> mForumList;


    private OnRecyclerViewItemClickListener mListener;


    public interface OnRecyclerViewItemClickListener{
        void onClick(View v, Object obj);
    }
    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener){
        this.mListener = listener;
    }


    public ForumOftenListAdapter(Context context, List<Forum> forumList) {
        mContext = context;
        mForumList = forumList;
    }

    public ForumOftenListAdapter(Context context) {
        mContext = context;
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

        view.setOnClickListener(this);

        return viewHodler;
    }

    @Override
    public void onBindViewHolder(ForumOftenViewHodler holder, int position) {
        holder.itemView.setTag(mForumList.get(position));
        // TODO: 15-11-11 绑定数据
        holder.mTvItemContentForumoften.setText(mForumList.get(position).getContent());

        if (mForumList.get(position).getPic() != null &&
                !TextUtils.isEmpty(mForumList.get(position).getPic().get(0))) {
            holder.mImgItemForumoften.setImageURI(Uri.parse(mForumList.get(position).getPic().get(0)));
        } else {
            holder.mImgItemForumoften.setImageResource(R.mipmap.ic_launcher);
        }

        BmobUser user = BmobUser.getCurrentUser(mContext, BmobUser.class);

        holder.mImgItemForumoftenFace.setImageURI(Uri.parse(user.getUserPhoto()));

        holder.mTvItemForumoftenNickname.setText(user.getNickName());

        holder.mTvItemForumoftenTime.setText(mForumList.get(position).getTime());
        holder.mTvItemForumoftenFeedback.setText("回复 " + mForumList.get(position).getCommentCount());
        holder.mTvItemTitleForumoften.setText(mForumList.get(position).getTitle());
    }

    @Override
    public int getItemCount() {
        if (mForumList == null) return 0;
        return mForumList.size();
    }


    @Override
    public void onClick(View v) {
        if (mListener != null) {
            mListener.onClick(v, v.getTag());
        }
    }


    class ForumOftenViewHodler extends RecyclerView.ViewHolder {

        @Bind(R.id.tv_item_title_forumoften)
        TextView mTvItemTitleForumoften;
        @Bind(R.id.tv_item_content_forumoften)
        TextView mTvItemContentForumoften;
        @Bind(R.id.img_item_forumoften)
        SimpleDraweeView mImgItemForumoften;
        @Bind(R.id.img_item_forumoften_face)
        SimpleDraweeView mImgItemForumoftenFace;
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
