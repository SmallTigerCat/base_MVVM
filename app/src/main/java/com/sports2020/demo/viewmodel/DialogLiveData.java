package com.sports2020.demo.viewmodel;

import androidx.lifecycle.MutableLiveData;

import com.sports2020.demo.bean.DialogBean;

/**
 * 自定义加载对话框信息LiveData
 * @param <T>
 */
public final class DialogLiveData<T> extends MutableLiveData<T> {

    private DialogBean bean = new DialogBean();

    public void setValue(boolean isShow) {
        bean.setShow(isShow);
        bean.setMsg("");
        setValue((T) bean);
    }

    public void setValue(boolean isShow, String msg) {
        bean.setShow(isShow);
        bean.setMsg(msg);
        setValue((T) bean);
    }
}