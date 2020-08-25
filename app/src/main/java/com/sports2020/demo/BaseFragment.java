package com.sports2020.demo;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;

import com.sports2020.demo.bean.DialogBean;
import com.sports2020.demo.util.ToastUtil;
import com.sports2020.demo.view.LoadingDialog;
import com.sports2020.demo.viewmodel.BaseViewModel;


/**
 * APP Fragment基类
 *
 * @author tsx
 */
public abstract class BaseFragment<VM extends BaseViewModel, DB extends ViewDataBinding> extends Fragment {

    protected Context context;
    protected VM viewModel;
    protected DB dataBinding;
    private LoadingDialog loadingDialog;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        dataBinding = initDataBinding(inflater, getContentView(), container);
        viewModel = initViewModel();
        initObserve();
        initView();
        initData();
        return dataBinding.getRoot();
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
    protected DB initDataBinding(LayoutInflater inflater, @LayoutRes int layoutId, ViewGroup container) {
        return DataBindingUtil.inflate(inflater, layoutId, container, false);
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
            loadingDialog = new LoadingDialog(context);
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
        ToastUtil.getInstance().show(context, getString(messageResId), Toast.LENGTH_LONG);
    }

    /**
     * Toast显示消息，显示时间为Toast.LENGTH_LONG
     *
     * @param message 需要显示的消息
     */
    protected void showMessageLong(String message) {
        ToastUtil.getInstance().show(context, message, Toast.LENGTH_LONG);
    }

    /**
     * Toast显示消息，显示时间为Toast.LENGTH_SHORT
     *
     * @param messageResId 消息资源Id
     */
    protected void showMessageShort(@StringRes int messageResId) {
        ToastUtil.getInstance().show(context, getString(messageResId), Toast.LENGTH_SHORT);
    }

    /**
     * Toast显示消息，显示时间为Toast.LENGTH_SHORT
     *
     * @param message 需要显示的消息
     */
    protected void showMessageShort(String message) {
        ToastUtil.getInstance().show(context, message, Toast.LENGTH_SHORT);
    }

    /**
     * Toast显示消息
     *
     * @param message  需要显示的消息
     * @param showTime 显示时间
     */
    protected void showMessage(String message, int showTime) {
        ToastUtil.getInstance().show(context, message, showTime);
    }
}
