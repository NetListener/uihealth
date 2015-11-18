package com.example.think.uihealth.moduel.forum.userinfo.adapter;

import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.think.uihealth.R;
import com.example.think.uihealth.app.App;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Zane on 15/11/18.
 */
public class FollowingAdapter extends RecyclerView.Adapter<FollowingAdapter.MyViewHolderFollowing> {


    private List<String> followingsImage;
    private List<String> followingsNickname;

    public List<String> getFollowingsImage() {
        return followingsImage;
    }

    public void setFollowingsImage(List<String> followingsImage) {
        this.followingsImage = followingsImage;
    }

    public List<String> getFollowingsNickname() {
        return followingsNickname;
    }

    public void setFollowingsNickname(List<String> followingsNickname) {
        this.followingsNickname = followingsNickname;
    }

    @Override
    public MyViewHolderFollowing onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(App.getInstance()).inflate(R.layout.item_following_layout, parent, false);
        MyViewHolderFollowing holder = new MyViewHolderFollowing(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolderFollowing holder, int position) {

        holder.imageviewFollowingfragmentAvatar.setImageURI(Uri.parse(followingsImage.get(position)));
        holder.textviewFollowingfragmentNickname.setText(followingsNickname.get(position));

    }

    @Override
    public int getItemCount() {
        return followingsNickname.size();
    }

    class MyViewHolderFollowing extends RecyclerView.ViewHolder {

        @Bind(R.id.imageview_followingfragment_avatar)
        SimpleDraweeView imageviewFollowingfragmentAvatar;
        @Bind(R.id.textview_followingfragment_nickname)
        TextView textviewFollowingfragmentNickname;

        public MyViewHolderFollowing(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
