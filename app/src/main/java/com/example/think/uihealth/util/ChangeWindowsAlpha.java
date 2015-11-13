package com.example.think.uihealth.util;

import android.app.Activity;
import android.support.v4.app.FragmentActivity;
import android.view.WindowManager;

/**
 * Created by Zane on 15/11/13.
 */
public class ChangeWindowsAlpha {

    public static void changeWindowsAlpha(Activity activity, float alpha){

        WindowManager.LayoutParams params = activity.getWindow().getAttributes();
        params.alpha = alpha;
        activity.getWindow().setAttributes(params);

    }

}
