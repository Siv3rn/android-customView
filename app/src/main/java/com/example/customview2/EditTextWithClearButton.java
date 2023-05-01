package com.example.customview2;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatEditText;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class EditTextWithClearButton extends AppCompatEditText {
    Drawable nClearButtonImange;

    private void init(){
        nClearButtonImange = ResourcesCompat.getDrawable(getResources(),R.drawable.baseline_close_24,null);
        addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                hideClearButton();
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                showClearButton();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (getCompoundDrawables()[2] != null){
                    float clearButtonStart = (getWidth()-getPaddingEnd()-nClearButtonImange.getIntrinsicWidth());
                    boolean isClearButonCLicked = false;
                    if(event.getX() > clearButtonStart){
                        isClearButonCLicked = true;

                    }
                    if (isClearButonCLicked){
                        if (event.getAction()==MotionEvent.ACTION_DOWN){
                        nClearButtonImange = ResourcesCompat.getDrawable(getResources(),R.drawable.baseline_close_black, null);
                        showClearButton();
                        }

                    if (event.getAction()==MotionEvent.ACTION_UP){
                        nClearButtonImange = ResourcesCompat.getDrawable(getResources(),R.drawable.baseline_close_24,null);
                        getText().clear();
                        hideClearButton();
                        return true;
                        }
                    }
                    else{
                        return false;
                    }
                }
                return false;
            }
        });
    }
    public EditTextWithClearButton(Context context) {
        super(context);
        init();
    }

    public EditTextWithClearButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public EditTextWithClearButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void showClearButton(){
        setCompoundDrawablesWithIntrinsicBounds(nClearButtonImange,null,  null,null);
    }

    private void hideClearButton(){
        setCompoundDrawablesWithIntrinsicBounds(null,null,null,null);

    }
}
