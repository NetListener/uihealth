package com.example.think.uihealth.view.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
<<<<<<< HEAD
<<<<<<< HEAD
=======
import android.widget.AdapterView;
>>>>>>> origin/dev
=======
>>>>>>> origin/dev
import android.widget.TextView;

import com.example.think.uihealth.R;
import com.example.think.uihealth.app.App;
<<<<<<< HEAD
<<<<<<< HEAD
import com.example.think.uihealth.model.bean.BmobUserData;
=======
import com.example.think.uihealth.model.BmobUserData;
import com.example.think.uihealth.view.fragment.ContentFragment;
import com.gc.materialdesign.views.ProgressBarCircularIndeterminate;
>>>>>>> origin/dev
=======
import com.example.think.uihealth.model.bean.BmobUserData;
>>>>>>> origin/dev

import java.text.NumberFormat;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * Created by Zane on 2015/10/14.
 */
public class BeforeDataRecycleviewAdapter extends RecyclerView.Adapter<BeforeDataRecycleviewAdapter.MyViewHolder>{

    private List<BmobUserData> mBmobUsers;
    private LayoutInflater mLayoutInflater;
    private OnBeforeDataItemClickListener mListener;


    public interface OnBeforeDataItemClickListener{
        void OnClick(View view, int pos);
        void OnLongClick(View view, int pos);
    }

    public void setOnBeforeDataItemClickListener(OnBeforeDataItemClickListener mListener){
        this.mListener = mListener;
    }

    public List<BmobUserData> getmBmobUsers(){
        return mBmobUsers;
    }

    public void setmBmobUsers(List<BmobUserData> mBmobUsers){
        this.mBmobUsers = mBmobUsers;
    }

    public BeforeDataRecycleviewAdapter(List<BmobUserData> mBmobUsers) {
        this.mBmobUsers = mBmobUsers;
        mLayoutInflater = LayoutInflater.from(App.getInstance());
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(mLayoutInflater.inflate(R.layout.recycleview_beforedata_layout, parent,
        false));
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {


        holder.mTextViewTime.setText(mBmobUsers.get(position).getCreatedAt());
        
        NumberFormat numberFormat = NumberFormat.getNumberInstance();
        numberFormat.setMaximumFractionDigits(2);
        holder.mTextViewData.setText(numberFormat.format(mBmobUsers.get(position).getResult())+"%");

        //调用接口
        if(mListener != null){
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.OnClick(v, position);
                }
            });
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    mListener.OnLongClick(v, position);
                    return true;
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mBmobUsers.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        
        @Bind(R.id.textview_recycleview_beforedata_time)
        TextView mTextViewTime;
        @Bind(R.id.textview_recycleview_beforedata)
        TextView mTextViewData;

        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
