package com.example.think.uihealth.moduel.forum;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.think.uihealth.R;
import com.example.think.uihealth.config.OftenConstant;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by kermit on 15-11-13.
 */
public class ChoiceFragment extends DialogFragment {


    @Bind(R.id.listview_choice)
    ListView mListviewChoice;


    private ChoiceCallback mChoiceCallback;

    public interface ChoiceCallback{
        void choiceCallback(int pos);
    }

    public void setChoiceCallback(ChoiceCallback choiceCallback){
        this.mChoiceCallback = choiceCallback;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_choicesickness_layout, container, false);
        ButterKnife.bind(this, view);
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        mListviewChoice.setAdapter(new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, OftenConstant.oftenSickness));
        mListviewChoice.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mChoiceCallback.choiceCallback(position);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
