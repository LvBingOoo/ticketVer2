package com.cc.util;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;

public class SelectStatesUtil {

    public static StateListDrawable addStateDrawable(Context context, int idNormal, int idPressed, int idFocused) {
        StateListDrawable sd = new StateListDrawable();
        Drawable normal = idNormal == -1 ? null : context.getResources().getDrawable(idNormal);
        Drawable pressed = idPressed == -1 ? null : context.getResources().getDrawable(idPressed);
        Drawable focus = idFocused == -1 ? null : context.getResources().getDrawable(idFocused);
        // 注意该处的顺序，只要有一个状态与之相配，背景就会被换掉
        // 所以不要把大范围放在前面了，如果sd.addState(new[]{},normal)放在第一个的话，就没有什么效果了
        sd.addState(new int[] { android.R.attr.state_enabled, android.R.attr.state_focused }, focus);
        sd.addState(new int[] { android.R.attr.state_pressed, android.R.attr.state_enabled }, pressed);
        sd.addState(new int[] { android.R.attr.state_focused }, focus);
        sd.addState(new int[] { android.R.attr.state_pressed }, pressed);
        sd.addState(new int[] { android.R.attr.state_enabled }, normal);
        sd.addState(new int[] {}, normal);
        return sd;
    }

    public static StateListDrawable addStateDrawableTodr(Context context, Drawable idNormal, Drawable idPressed,
            Drawable idFocused) {
        StateListDrawable sd = new StateListDrawable();
        Drawable normal = idNormal;
        Drawable pressed = idPressed;
        Drawable focus = idFocused;
        // 注意该处的顺序，只要有一个状态与之相配，背景就会被换掉
        // 所以不要把大范围放在前面了，如果sd.addState(new[]{},normal)放在第一个的话，就没有什么效果了
		sd.addState(new int[] { android.R.attr.state_selected }, idPressed);
        sd.addState(new int[] { android.R.attr.state_enabled, android.R.attr.state_focused }, focus);
        sd.addState(new int[] { android.R.attr.state_pressed, android.R.attr.state_enabled }, pressed);
        sd.addState(new int[] { android.R.attr.state_focused }, focus);
        sd.addState(new int[] { android.R.attr.state_pressed }, pressed);
        sd.addState(new int[] { android.R.attr.state_enabled }, normal);
        sd.addState(new int[] {}, normal);
        return sd;
    }

    public static ColorStateList createColorStateList(int normal, int pressed, int focused, int unable) {
        int[] colors = new int[] { pressed, focused, normal, focused, unable, normal };
        int[][] states = new int[6][];
        states[0] = new int[] { android.R.attr.state_pressed, android.R.attr.state_enabled };
        states[1] = new int[] { android.R.attr.state_enabled, android.R.attr.state_focused };
        states[2] = new int[] { android.R.attr.state_enabled };
        states[3] = new int[] { android.R.attr.state_focused };
        states[4] = new int[] { android.R.attr.state_window_focused };
        states[5] = new int[] {};
        ColorStateList colorList = new ColorStateList(states, colors);
        return colorList;
    }
}
