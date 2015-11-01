package com.example.think.uihealth.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.think.uihealth.R;
import com.kermit.exutils.utils.ExUtils;

/**
 * Created by Zane on 15/10/28.
 */
public class CombineTextImageviewView extends RelativeLayout{

    ImageView mImageView;
    TextView mTextview;
    int srcId;
    String text;
    int marginCenter;
    float textSize;
    int textColor;

    public static final String TAG = "ComBineTextImageView";

    public CombineTextImageviewView(Context context) {
        super(context);
    }

    public CombineTextImageviewView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
        getAttrs(context, attrs);

    }

    public CombineTextImageviewView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
        getAttrs(context, attrs);
    }

    private void getAttrs(Context context, AttributeSet attrs) {


        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.CombineTextImageviewView);

        try {
            srcId = a.getResourceId(R.styleable.CombineTextImageviewView_imageSrc, 0);
            Log.i(TAG, String.valueOf(srcId));
            marginCenter = a.getDimensionPixelOffset(R.styleable.CombineTextImageviewView_marginCenter, 0);
            text = a.getString(R.styleable.CombineTextImageviewView_text);
            Log.i(TAG, String.valueOf(marginCenter));
            textSize = a.getDimension(R.styleable.CombineTextImageviewView_textSize, 0);
            Log.i(TAG, String.valueOf(textSize));
            textColor = a.getInteger(R.styleable.CombineTextImageviewView_textColor, 0);
        }finally {
            a.recycle();
        }
        if(srcId != 0) {
            Log.i(TAG, String.valueOf(srcId));
            mImageView.setImageResource(srcId);
            invalidate();
            requestLayout();
        }

        mTextview.setTextSize(ExUtils.px2dip(textSize));
        mTextview.setTextColor(textColor);
        LayoutParams params = new LayoutParams(context, attrs);
        params.setMargins(ExUtils.px2dip(marginCenter), 0, 0, 0);
        mTextview.setLayoutParams(params);
        mTextview.setText(text);
    }

    public void setText(String text){
        Log.i(TAG, text);
        mTextview.setText(text);
    }
    public void setImageSrc(int resId){
        mImageView.setImageResource(resId);
    }
    private void initView(Context context) {
        LayoutInflater.from(context).inflate(R.layout.combinetextimageview_widget_layout, this);

        mImageView = (ImageView) findViewById(R.id.imageview_widget_textimage);
        mTextview = (TextView) findViewById(R.id.textview_widget_textimage);
    }

}
