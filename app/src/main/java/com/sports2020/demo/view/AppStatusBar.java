package com.sports2020.demo.view;

import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatImageView;

import java.lang.reflect.Field;

/**
 * 状态栏占位控件
 *
 * @author tsx
 */
public class AppStatusBar extends AppCompatImageView {

    public AppStatusBar(Context context) {
        this(context, null);
    }

    public AppStatusBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AppStatusBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * 返回状态栏高度
     *
     * @return 像素
     */
    public int getStatusBarHeight(Context context) {
        if (isNeedShowStatusBar()) {
            try {
                Class<?> aClass = Class.forName("com.android.internal.R$dimen");
                Object obj = aClass.newInstance();
                Field field = aClass.getField("status_bar_height");
                int x = Integer.parseInt(field.get(obj).toString());
                int y = context.getResources().getDimensionPixelSize(x);
                return y;
            } catch (Exception e) {
                e.printStackTrace();
                return dipToPx(25);
            }
        } else {
            return 0;
        }
    }

    /**
     * 是否需要显示StatusBar
     *
     * @return true 显示StatusBar
     */
    public static boolean isNeedShowStatusBar() {
        return (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT);
    }

    /**
     * DIP 转 PX
     *
     * @param dip
     * @return
     */
    public int dipToPx(float dip) {
        return (int) (getResources().getDisplayMetrics().density * dip + 0.5f);
    }

    private int getStatusBarHeight() {
        return getStatusBarHeight(getContext());
    }

    @Override
    protected int getSuggestedMinimumHeight() {
        return getStatusBarHeight();
    }
}
