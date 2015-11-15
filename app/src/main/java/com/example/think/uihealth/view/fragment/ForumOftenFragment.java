package com.example.think.uihealth.view.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.example.think.uihealth.R;
import com.example.think.uihealth.config.OftenConstant;
import com.example.think.uihealth.model.bean.BmobUser;
import com.example.think.uihealth.view.activity.ForumContentActivity;
import com.example.think.uihealth.view.activity.ForumOftenActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by kermit on 15-11-13.
 */
public class ForumOftenFragment extends Fragment implements
        ChoiceFragment.ChoiceCallback {

    @Bind(R.id.gridview_addchoice)
    GridView mGridviewAddchoice;

    private static ForumOftenFragment mFragment;
    private ChoiceFragment mChoiceFragment;
    private List<String> oftens;
    private MyAdapter mAdapter;

    public static ForumOftenFragment newInstance() {
        if (mFragment == null) {
            mFragment = new ForumOftenFragment();
        }
        return mFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_forumoften_layout, container, false);
        ButterKnife.bind(this, view);
        return view;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        mChoiceFragment = new ChoiceFragment();
        mChoiceFragment.setChoiceCallback(this);

        oftens = BmobUser.getCurrentUser(getContext(), BmobUser.class)
                .getOften();
        if (oftens == null) {
            oftens = new ArrayList<>();
            oftens.add("添加");
        }
        mAdapter = new MyAdapter();
        mGridviewAddchoice.setAdapter(mAdapter);

        final List<String> finalOftens = oftens;
        mGridviewAddchoice.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (finalOftens.get(position).equals("添加")) {
                    mChoiceFragment.show(getFragmentManager(), "choice");
                } else {
                    String tag = finalOftens.get(position);
                    Intent intent = new Intent(getContext(), ForumOftenActivity.class);
                    intent.putExtra("tag", tag);
                    startActivity(intent);
                }
            }
        });

        mGridviewAddchoice.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                oftens.remove(oftens.get(position));
                upLoadData();
                mAdapter.notifyDataSetChanged();
                return true;
            }
        });
    }


    private void upLoadData() {
        BmobUser user = BmobUser.getCurrentUser(getContext(), BmobUser.class);
        user.setOften(oftens);
        user.update(getContext());
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void choiceCallback(final int pos) {
        final int i = pos;
        mChoiceFragment.dismiss();
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                oftens.remove(oftens.size() - 1);
                oftens.add(OftenConstant.oftenSickness[i]);
                removeSame(oftens);
                oftens.add("添加");
                mAdapter.notifyDataSetChanged();
                upLoadData();
            }
        });
    }

    private void removeSame(List<String> list) {
        HashSet<String> hashSet = new HashSet<>(list);
        list.clear();
        list.addAll(hashSet);
    }

    class MyAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return oftens.size();
        }

        @Override
        public Object getItem(int position) {
            return oftens.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = LayoutInflater.from(getContext()).inflate(R.layout.item_oftentext_layout, parent, false);
            TextView textView = (TextView) view.findViewById(R.id.tv_oftenchoice);
            textView.setText(oftens.get(position));
            return view;
        }
    }
}
