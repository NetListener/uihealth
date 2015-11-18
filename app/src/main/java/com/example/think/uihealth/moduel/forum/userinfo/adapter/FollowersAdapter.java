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
 * Created by Zane on 15/11/17.
 */
public class FollowersAdapter extends RecyclerView.Adapter<FollowersAdapter.MyViewHolder2> {

    private List<String> followersImage;
    private List<String> followerNickname;

    public List<String> getFollowersImage() {
        return followersImage;
    }

    public void setFollowersImage(List<String> followersImage) {
        this.followersImage = followersImage;
    }

    public List<String> getFollowerNickname() {
        return followerNickname;
    }

    public void setFollowerNickname(List<String> followerNickname) {
        this.followerNickname = followerNickname;
    }

    @Override
    public MyViewHolder2 onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(App.getInstance()).inflate(R.layout.item_fragment_followers, parent, false);
        MyViewHolder2 myViewHolder2 = new MyViewHolder2(view);

        return myViewHolder2;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder2 holder, int position) {
        holder.imageviewFollowerfragmentAvatar.setImageURI(Uri.parse(followersImage.get(position)));
        holder.textviewFollowersfragmentNickname.setText(followerNickname.get(position));
    }

    @Override
    public int getItemCount() {
        return followerNickname.size();
    }

    class MyViewHolder2 extends RecyclerView.ViewHolder {

        @Bind(R.id.imageview_followerfragment_avatar)
        SimpleDraweeView imageviewFollowerfragmentAvatar;
        @Bind(R.id.textview_followersfragment_nickname)
        TextView textviewFollowersfragmentNickname;

        public MyViewHolder2(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
