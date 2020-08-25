package com.sports2020.demo.api.response;

import android.text.TextUtils;

/**
 * 网络请求公共返回数据信息 根据服务端常用返回数据格式进行属性定义
 * <p>
 * Created by KarenChia on 2019/8/17 0017.
 */
public class CommonResponseBody<T> {

    /**
     * state : error/success
     * message : 接口提示信息
     * data : 接口中所有的响应数据
     */
    private String state;
    private String message;
    private T data;

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public boolean isSuccess() {
        if (TextUtils.isEmpty(state))
            return false;
        return TextUtils.equals("success", state);
    }

    @Override
    public String toString() {
        return "CommonResponseBody{" +
                "state='" + state + '\'' +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}
