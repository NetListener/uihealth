package com.example.think.uihealth.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.think.uihealth.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Zane on 2015/9/22.
 */
public class ContentRecycleviewAdapter extends RecyclerView.Adapter<ContentRecycleviewAdapter.MyViewHolder>{

    private LayoutInflater mLayoutInflater;
    private String[] mContents;
    private OnItemClickListener mListener;
    private static ContentRecycleviewAdapter mInstance;

    public interface OnItemClickListener{
        void OnClick (View view, int pos);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        mListener = listener;
    }

    public ContentRecycleviewAdapter(Context context, String[] mContents) {

        mLayoutInflater = LayoutInflater.from(context);

        this.mContents = mContents;

    }

    public static ContentRecycleviewAdapter getInstance(Context context, String[] mContents){

        if(mInstance == null){
            mInstance = new ContentRecycleviewAdapter(context, mContents);
        }

        return mInstance;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {

        return new MyViewHolder(mLayoutInflater.inflate(R.layout.recylceview_content_item_layout,
                viewGroup, false));

    }

    @Override
    public void onBindViewHolder(MyViewHolder myViewHolder, final int i) {

        myViewHolder.mRecycleViewContent.setText(mContents[i]);

        if(mListener != null){
            myViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.OnClick(v, i);
                }
            });
        }

    }

    @Override
    public int getItemCount() {

        return mContents.length;

    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        @Bind(R.id.textview_recycleview_content)
        TextView mRecycleViewContent;

        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
