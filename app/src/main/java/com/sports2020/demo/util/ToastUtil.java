package com.sports2020.demo.util;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.widget.Toast;

import androidx.databinding.DataBindingUtil;

import com.sports2020.demo.R;
import com.sports2020.demo.databinding.ViewCustomToastBinding;


/**
 * toast 提示框
 * <p>
 * Created by KarenChia on 2019/10/30.
 */
public class ToastUtil {
    /**
     * 单例
     */
    private static ToastUtil instance;
    /**
     * 提示框
     */
    private Toast toast;

    private ToastUtil() {
    }

    public static ToastUtil getInstance() {
        if (instance == null) {
            synchronized (ToastUtil.class) {
                if (instance == null) {
                    instance = new ToastUtil();
                }
            }
        }
        return instance;
    }

    /**
     * 显示提示框
     *
     * @param context  上下文
     * @param text     提示信息
     * @param duration 显示时间
     */
    public void show(Context context, String text, int duration) {
        if (toast != null) {
            toast.cancel();
            toast = null;
        }
        ViewCustomToastBinding binding = DataBindingUtil.inflate(LayoutInflater.from(context),
                R.layout.view_custom_toast, null, false);
        binding.tvMsg.setText(text);
        toast = new Toast(context);
        toast.setView(binding.getRoot());
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.setDuration(duration);
        toast.show();
    }
}
