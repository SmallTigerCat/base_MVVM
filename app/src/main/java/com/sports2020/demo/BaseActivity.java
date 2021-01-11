package com.sports2020.demo;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.Observer;

import com.sports2020.demo.bean.DialogBean;
import com.sports2020.demo.util.ToastUtil;
import com.sports2020.demo.view.LoadingDialog;
import com.sports2020.demo.viewmodel.BaseViewModel;


/**
 * APP Activity基类
 *
 * @param <VM> 业务依赖的viewModel
 * @param <DB> 界面binding
 * @author tsx
 */
public abstract class BaseActivity<VM extends BaseViewModel, DB extends ViewDataBinding> extends AppCompatActivity {

    protected VM viewModel;
    protected DB dataBinding;
    private LoadingDialog loadingDialog;

    /**
     * 上一次点击的时间戳
     */
    private long mLastClickTime = 0L;
    /**
     * 被判断为重复点击的时间间隔
     */
    private long MIN_CLICK_DELAY_TIME = 300L;
    /**
     * 是否支持双击，默认为不支持
     */
    private boolean mDoubleClickEnable = false;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dealStatusBar();
        dataBinding = initDataBinding();
        dataBinding.setLifecycleOwner(this);
        viewModel = initViewModel();
        initObserve();
        initView();
        initData();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (dataBinding != null) {
            dataBinding.unbind();
        }
        hideSystemSoftInput();
    }

    /**
     * 获取布局文件ID
     *
     * @return 当前界面布局文件的ID
     */
    protected abstract int getContentView();

    /**
     * 初始化DataBinding
     */
    protected DB initDataBinding() {
        return DataBindingUtil.setContentView(this, getContentView());
    }

    /**
     * 初始化ViewModel
     */
    protected abstract VM initViewModel();

    /**
     * 监听当前ViewModel中 showDialog
     */
    private void initObserve() {
        if (viewModel == null) return;
        viewModel.getShowDialog(this, (DialogBean bean) -> {
            if (bean.isShow()) {
                showProgressDialog(bean.getMsg());
            } else {
                dismissProgressDialog();
            }
        });
        viewModel.getError(this, new Observer<Object>() {
            @Override
            public void onChanged(Object obj) {
                showMessageShort(obj.toString());
            }
        });
    }

    /**
     * 初始化视图
     */
    protected abstract void initView();

    /**
     * 初始化数据
     */
    protected abstract void initData();

    /**
     * 显示用户等待框
     *
     * @param messageResId 提示信息
     */
    protected void showProgressDialog(@StringRes int messageResId) {
        showProgressDialog(getString(messageResId));
    }

    /**
     * 显示用户等待框
     *
     * @param msg 提示信息
     */
    protected void showProgressDialog(String msg) {
        if (loadingDialog != null && loadingDialog.isShowing()) {
            loadingDialog.setLoadingMsg(msg);
        } else {
            loadingDialog = new LoadingDialog(this);
            loadingDialog.setLoadingMsg(msg);
            loadingDialog.show();
        }
    }

    /**
     * 隐藏等待框
     */
    protected void dismissProgressDialog() {
        if (loadingDialog != null && loadingDialog.isShowing()) {
            loadingDialog.dismiss();
            loadingDialog = null;
        }
    }

    /**
     * Toast显示消息，显示时间为Toast.LENGTH_LONG
     *
     * @param messageResId 消息资源Id
     */
    protected void showMessageLong(@StringRes int messageResId) {
        ToastUtil.getInstance().show(this, getString(messageResId), Toast.LENGTH_LONG);
    }

    /**
     * Toast显示消息，显示时间为Toast.LENGTH_LONG
     *
     * @param message 需要显示的消息
     */
    protected void showMessageLong(String message) {
        ToastUtil.getInstance().show(this, message, Toast.LENGTH_LONG);
    }

    /**
     * Toast显示消息，显示时间为Toast.LENGTH_SHORT
     *
     * @param messageResId 消息资源Id
     */
    protected void showMessageShort(@StringRes int messageResId) {
        ToastUtil.getInstance().show(this, getString(messageResId), Toast.LENGTH_SHORT);
    }

    /**
     * Toast显示消息，显示时间为Toast.LENGTH_SHORT
     *
     * @param message 需要显示的消息
     */
    protected void showMessageShort(String message) {
        ToastUtil.getInstance().show(this, message, Toast.LENGTH_SHORT);
    }

    /**
     * Toast显示消息
     *
     * @param message  需要显示的消息
     * @param showTime 显示时间
     */
    protected void showMessage(String message, int showTime) {
        ToastUtil.getInstance().show(this, message, showTime);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_UP) {
            if (isDoubleClick()) {
                return true;
            }
        }
        //点击软键盘外部，软键盘消失
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            // 获得当前得到焦点的View，一般情况下就是EditText（特殊情况就是轨迹求或者实体案件会移动焦点）
            View v = getCurrentFocus();
            if (isShouldHideInput(v, ev)) {
                hideSoftInput(v.getWindowToken());
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    private Boolean isDoubleClick() {
        if (mDoubleClickEnable) return false;
        long time = System.currentTimeMillis();
        if (time - mLastClickTime > MIN_CLICK_DELAY_TIME) {
            mLastClickTime = time;
            return false;
        } else {
            return true;
        }
    }

    /**
     * 根据EditText所在坐标和用户点击的坐标相对比，来判断是否隐藏键盘，因为当用户点击EditText时没必要隐藏
     *
     * @param v
     * @param event
     * @return
     */
    private Boolean isShouldHideInput(View v, MotionEvent event) {
        if (v instanceof EditText) {
            int[] l = new int[]{0, 0};
            v.getLocationInWindow(l);
            int left = l[0];
            int top = l[1];
            int bottom = top + v.getHeight();
            int right = left + v.getWidth();
            // 点击EditText的事件，忽略它。
            return !(event.getX() > left) || !(event.getX() < right) || !(event.getY() > top) || !(event.getY() < bottom);
        }
        // 如果焦点不是EditText则忽略，这个发生在视图刚绘制完，第一个焦点不在EditView上，和用户用轨迹球选择其他的焦点
        return false;
    }

    private void hideSystemSoftInput() {
        View view = getWindow().peekDecorView();
        if (view != null && view.getWindowToken() != null) {
            InputMethodManager im = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            im.hideSoftInputFromWindow(
                    view.getWindowToken(),
                    InputMethodManager.HIDE_NOT_ALWAYS
            );
        }
    }

    /**
     * 多种隐藏软件盘方法的其中一种
     *
     * @param token
     */
    private void hideSoftInput(IBinder token) {
        if (token != null) {
            InputMethodManager im = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            im.hideSoftInputFromWindow(
                    token,
                    InputMethodManager.HIDE_NOT_ALWAYS
            );
        }
    }

    /**
     * 处理状态栏
     */
    private void dealStatusBar() {
        Window window = getWindow();
        //KITKAT也能满足，只是SYSTEM_UI_FLAG_LIGHT_STATUS_BAR（状态栏字体颜色反转）只有在6.0才有效
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);//透明状态栏
            // 状态栏字体设置为深色，SYSTEM_UI_FLAG_LIGHT_STATUS_BAR 为SDK23增加
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            // 部分机型的statusbar会有半透明的黑色背景
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(Color.TRANSPARENT);// SDK21
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        View decor = getWindow().getDecorView();
        if (setAndroidNativeLightStatusBar()) {
            decor.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        } else {
            decor.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
        }
    }

    protected boolean setAndroidNativeLightStatusBar() {
        return true;
    }
}

