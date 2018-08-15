package com.jjoey.sportseco.utils;

import android.content.Context;
import android.util.TypedValue;
import android.widget.Button;

import com.jjoey.sportseco.R;

public class MeetingButton extends Button {

    public MeetingButton(Context context, String text) {
        super(context);
        setTextSize(TypedValue.COMPLEX_UNIT_SP, 10);
        setBackground(getResources().getDrawable(R.drawable.meeting_button));
        setTextColor(getResources().getColor(R.color.white));
        setText(text);
    }
}
