package com.example.think.uihealth.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.think.uihealth.R;
import com.gc.materialdesign.views.ButtonFlat;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Zane on 2015/9/23.
 */
public class StartActivity extends AppCompatActivity{

    @Bind(R.id.btn_startactivity_start)
    ButtonFlat mButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_layout);
        ButterKnife.bind(this);

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(StartActivity.this, MainActivity.class);

                startActivity(intent);
            }
        });
    }
}
