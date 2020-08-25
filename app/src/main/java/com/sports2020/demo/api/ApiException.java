package com.sports2020.demo.api;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.ConnectException;
import java.net.SocketTimeoutException;

import retrofit2.HttpException;

/**
 * 网络请求异常处理
 * <p>
 * Created by KarenChia on 2019/8/12.
 */
public class ApiException extends RuntimeException {
    /**
     * 当前类的实例
     */
    private static ApiException apiException;

    private ApiException() {
    }

    /**
     * 使用单例模式获取ApiException实例
     *
     * @return ApiException实例
     */
    public static ApiException getInstance() {
        if (apiException == null) {
            synchronized (ApiException.class) {
                if (apiException == null) {
                    apiException = new ApiException();
                }
            }
        }
        return apiException;
    }

    /**
     * 获取接口请求错误中的错误信息
     *
     * @param e Throwable错误信息对象
     * @return 错误信息
     */
    public String getOnErrorMsg(Throwable e) {
        String errorMsg = "发生未知错误";
        if (e == null) {
            return errorMsg;
        }
        if (e instanceof HttpException) {
            HttpException httpException = (HttpException) e;
            int code = httpException.code();
            if (code == 500 || code == 404) {
                errorMsg = "服务器出错";
                return errorMsg;
            }
            if (code == 400) {
                try {
                    String errorBody = httpException.response().errorBody().string();
                    JSONObject jsonObject = new JSONObject(errorBody);
                    errorMsg = jsonObject.getString("message");
                    return errorMsg;
                } catch (JSONException e1) {
                    e1.printStackTrace();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
        if (e instanceof ConnectException) {
            errorMsg = "网络已断开,请检查网络连接是否正常";
            return errorMsg;
        }
        if (e instanceof SocketTimeoutException) {
            errorMsg = "网络连接超时";
            return errorMsg;
        }
        return errorMsg;
    }
}
