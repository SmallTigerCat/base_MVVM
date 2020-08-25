package com.sports2020.demo;

import android.os.Bundle;
import android.view.View;
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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dataBinding = initDataBinding();
        dataBinding.setLifecycleOwner(this);
        viewModel = initViewModel();
        initObserve();
        initView();
        initData();
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            hideSystemUI();
        }
    }

    private void hideSystemUI() {
        // Enables regular immersive mode.
        // For "lean back" mode, remove SYSTEM_UI_FLAG_IMMERSIVE.
        // Or for "sticky immersive," replace it with SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                        // Set the content to appear under the system bars so that the
                        // content doesn't resize when the system bars hide and show.
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        // Hide the nav bar and status bar
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (dataBinding != null) {
            dataBinding.unbind();
        }
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

}

