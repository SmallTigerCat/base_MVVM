package com.sports2020.demo.view;

import android.app.Activity;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import androidx.annotation.ColorInt;
import androidx.databinding.DataBindingUtil;

import com.sports2020.demo.R;
import com.sports2020.demo.databinding.ViewTitleBarBinding;

import dagger.hilt.android.internal.managers.ViewComponentManager;

/**
 * 应用标题栏
 *
 * @author tsx
 */
public class AppTitleBar extends LinearLayout {

    public static final int FUNCTION_LEFT_BTN = 1;
    public static final int FUNCTION_TITLE = 2;
    public static final int FUNCTION_RIGHT_TEXT = 4;
    public static final int FUNCTION_RIGHT_SECOND_TEXT = 32;
    public static final int FUNCTION_LEFT_TEXT = 16;
    public static final int FUNCTION_RIGHT_BTN = 8;

    private int function;
    private ViewTitleBarBinding binding;

    public AppTitleBar(Context context) {
        this(context, null, 0);
    }

    public AppTitleBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AppTitleBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        binding = DataBindingUtil.inflate(LayoutInflater.from(context),
                R.layout.view_title_bar, null, false);
        LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        addView(binding.getRoot(), layoutParams);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.AppTitleBar);
        int defColor = getResources().getColor(R.color.title_bar_background);

        setActionBarBackground(typedArray.getColor(R.styleable.AppTitleBar_barBackground, defColor));
        setTitleText(typedArray.getString(R.styleable.AppTitleBar_titleText), typedArray.getInt(R.styleable.AppTitleBar_titleTextLimit, 0));
        int defTitleColor = getResources().getColor(R.color.color_333333);
        setTitleColor(typedArray.getColor(R.styleable.AppTitleBar_titleColor, defTitleColor));

        setLeftText(typedArray.getString(R.styleable.AppTitleBar_leftText));
        setRightText(typedArray.getString(R.styleable.AppTitleBar_rightText));
        setRightSecondText(typedArray.getString(R.styleable.AppTitleBar_rightSecondText));
        if (typedArray.getColorStateList(R.styleable.AppTitleBar_leftTextColor) != null) {
            setLeftTextColor(typedArray.getColorStateList(R.styleable.AppTitleBar_leftTextColor));
        } else {
            setLeftTextColor(typedArray.getColor(R.styleable.AppTitleBar_leftTextColor, defTitleColor));
        }

        if (typedArray.getColorStateList(R.styleable.AppTitleBar_rightTextColor) != null) {
            setRightTextColor(typedArray.getColorStateList(R.styleable.AppTitleBar_rightTextColor));
        } else {
            setRightTextColor(typedArray.getColor(R.styleable.AppTitleBar_rightTextColor, defTitleColor));
        }

        if (typedArray.getColorStateList(R.styleable.AppTitleBar_rightSecondTextColor) != null) {
            setRightSecondTextColor(typedArray.getColorStateList(R.styleable.AppTitleBar_rightSecondTextColor));
        } else {
            setRightSecondTextColor(typedArray.getColor(R.styleable.AppTitleBar_rightSecondTextColor, defTitleColor));
        }
        setRightBtnImageDrawable(typedArray.getDrawable(R.styleable.AppTitleBar_rightBtnImage));

        setStatusBarColor(typedArray.getColor(R.styleable.AppTitleBar_statusBarColor, Color.TRANSPARENT));

        Drawable leftBtnImageDrawable = typedArray.getDrawable(R.styleable.AppTitleBar_leftBtnImage);
        if (leftBtnImageDrawable == null) {
            leftBtnImageDrawable = getResources().getDrawable(R.drawable.ic_back);
        }
        setLeftBtnImageDrawable(leftBtnImageDrawable);

        showStatusBar(typedArray.getBoolean(R.styleable.AppTitleBar_showStatusBar, true));

        int function = typedArray.getInt(R.styleable.AppTitleBar_function, FUNCTION_LEFT_BTN | FUNCTION_TITLE);
        setFunction(function);

        String backClick = typedArray.getString(R.styleable.AppTitleBar_leftBtnClick);
        if (TextUtils.isEmpty(backClick)) {
            setLeftBtnClick(v -> {
                if (context instanceof Activity)
                    ((Activity) context).onBackPressed();
                else if (context instanceof ViewComponentManager.FragmentContextWrapper) {
                    ViewComponentManager.FragmentContextWrapper wrapper = (ViewComponentManager.FragmentContextWrapper) context;
                    wrapper.fragment.getActivity().onBackPressed();
                }
            });
        }
        typedArray.recycle();
    }

    public void setStatusBarColor(@ColorInt int color) {
        binding.statusBar.setBackgroundColor(color);
    }

    public void setActionBarBackground(@ColorInt int color) {
        binding.getRoot().setBackgroundColor(color);
    }

    public void setTitleText(String title) {
        setTitleText(title, 10);
    }

    public void setTitleText(String title, int textLimit) {
        if (textLimit > 0) {
            if (null != title && title.length() > textLimit) {
                title = title.substring(0, textLimit);
                title += "...";
            }
        }
        binding.title.setText(title);
    }

    public void setTitleSize(int unit, float size) {
        binding.title.setTextSize(unit, size);
    }

    public void setTitleColor(@ColorInt int color) {
        binding.title.setTextColor(color);
    }

    public void setRightTextColor(@ColorInt int color) {
        binding.rightText.setTextColor(color);
    }

    public void setRightTextColor(ColorStateList colorStateList) {
        binding.rightText.setTextColor(colorStateList);
    }

    public void setRightSecondTextColor(@ColorInt int color) {
        binding.rightSecondText.setTextColor(color);
    }

    public void setRightSecondTextColor(ColorStateList colorStateList) {
        binding.rightSecondText.setTextColor(colorStateList);
    }

    public void setLeftTextColor(@ColorInt int color) {
        binding.leftText.setTextColor(color);
    }

    public void setLeftTextColor(ColorStateList colorStateList) {
        binding.leftText.setTextColor(colorStateList);
    }

    public void showStatusBar(boolean enable) {
        binding.statusBar.setVisibility(enable ? VISIBLE : GONE);
    }

    public void setFunction(int function) {
        this.function = function;

        binding.leftBtn.setVisibility((function & FUNCTION_LEFT_BTN) == FUNCTION_LEFT_BTN ? VISIBLE : GONE);
        binding.title.setVisibility((function & FUNCTION_TITLE) == FUNCTION_TITLE ? VISIBLE : GONE);
        binding.leftText.setVisibility((function & FUNCTION_LEFT_TEXT) == FUNCTION_LEFT_TEXT ? VISIBLE : GONE);
        binding.rightText.setVisibility((function & FUNCTION_RIGHT_TEXT) == FUNCTION_RIGHT_TEXT ? VISIBLE : GONE);
        binding.rightSecondText.setVisibility((function & FUNCTION_RIGHT_SECOND_TEXT) == FUNCTION_RIGHT_SECOND_TEXT ? VISIBLE : GONE);
        binding.rightBtn.setVisibility((function & FUNCTION_RIGHT_BTN) == FUNCTION_RIGHT_BTN ? VISIBLE : GONE);
        binding.progressBar.setVisibility(GONE);
    }

    public void addFunction(int function) {
        setFunction(this.function | function);
    }

    public void removeFunction(int function) {
        setFunction(this.function & ~function);
    }

    public void setLeftBtnClick(OnClickListener onClickListener) {
        binding.leftBtn.setOnClickListener(onClickListener);
    }

    public void setRightBtnClick(OnClickListener onClickListener) {
        binding.rightBtn.setOnClickListener(onClickListener);
    }

    public void setLeftTextClick(OnClickListener onClickListener) {
        binding.leftText.setOnClickListener(onClickListener);
    }

    public void setRightTextClick(OnClickListener onClickListener) {
        binding.rightText.setOnClickListener(onClickListener);
    }

    public void setRightSecondTextClick(OnClickListener onClickListener) {
        binding.rightSecondText.setOnClickListener(onClickListener);
    }

    public void setTitleClick(OnClickListener onClickListener) {
        binding.title.setOnClickListener(onClickListener);
    }

    public void setRightBtnImageDrawable(Drawable drawable) {
        binding.rightBtn.setImageDrawable(drawable);
    }


    public void setLeftBtnImageDrawable(Drawable drawable) {
        binding.leftBtn.setImageDrawable(drawable);
    }

    public void setLeftText(String text) {
        binding.leftText.setText(text);
    }

    public void setRightText(String text) {
        binding.rightText.setText(text);
    }

    public void setRightSecondText(String text) {
        binding.rightSecondText.setText(text);
    }

    public void setProgressBarVisibility(boolean visibility) {
        binding.progressBar.setVisibility(visibility ? VISIBLE : GONE);
    }
}
