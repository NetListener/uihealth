package com.example.think.uihealth.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.think.uihealth.R;
import com.example.think.uihealth.model.bean.ForumContent;

import java.util.List;

/**
 * Created by kermit on 15-11-11.
 */
public class ForumContentAdapter extends RecyclerView.Adapter {

    private final static int CONTENT = 12580;
    private final static int COMMENT = 12581;

    private Context mContext;
    private List<ForumContent> mContents;

    public ForumContentAdapter(Context context, List<ForumContent> contents) {
        mContext = context;
        mContents = contents;
    }

    /**
     * 数据修改
     * @param contents
     * @return
     */
    public boolean setData(List<ForumContent> contents){
        if (contents != null) {
            mContents = contents;
            return true;
        }
        return false;
    }

    public boolean addData(List<ForumContent> contents){
        if(contents != null){
            mContents.addAll(contents);
            return true;
        }
        return false;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0){
            return CONTENT;
        }
        else
            return COMMENT;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        RecyclerView.ViewHolder viewHolder = null;
        switch (viewType){
            case CONTENT:
                view = LayoutInflater.from(mContext).inflate(R.layout.item_activity_forumcontenttopic, parent, false);
                viewHolder = new ForumContentViewHolder(view);
                break;
            case COMMENT:
                view = LayoutInflater.from(mContext).inflate(R.layout.item_activity_forumcontent, parent, false);
                viewHolder = new ForumCommentViewHolder(view);
                break;
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        // TODO: 15-11-11  viewholer数据绑定
        switch (position){
            case CONTENT:
                ForumContentViewHolder contentViewHolder = (ForumContentViewHolder) holder;
                break;
            case COMMENT:
                ForumCommentViewHolder commentViewHolder = (ForumCommentViewHolder) holder;
                break;
        }
    }

    @Override
    public int getItemCount() {
        return mContents.size();
    }

    class ForumContentViewHolder extends RecyclerView.ViewHolder{

        public ForumContentViewHolder(View itemView) {
            super(itemView);
        }
    }

    class ForumCommentViewHolder extends RecyclerView.ViewHolder{

        public ForumCommentViewHolder(View itemView) {
            super(itemView);
        }
    }
}
