package com.example.think.uihealth.util;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

/**
 * Created by Zane on 15/11/10.
 */
public class GetEditTextWordNumber {

    private static int number;

    public static int getTextNumber(final EditText editText){

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                number = editText.getText().length();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        return number;
    }

}
