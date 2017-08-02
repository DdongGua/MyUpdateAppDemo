package com.example.myupdateappdemo.activity.widget;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by 亮亮 on 2017/7/26.
 */

public class FontTextView extends android.support.v7.widget.AppCompatTextView {
    public FontTextView(Context context) {
        super(context);
    }

    public FontTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public FontTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private Typeface createTypeface(Context context, String fontPath) {
        return Typeface.createFromAsset(context.getAssets(), fontPath);
    }

    @Override
    public void setTypeface(Typeface tf, int style) {
        super.setTypeface(createTypeface(getContext(), "fonts/MONACO.TTF"), style);
    }
}
