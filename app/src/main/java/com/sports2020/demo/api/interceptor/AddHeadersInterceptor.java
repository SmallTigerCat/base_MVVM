package com.sports2020.demo.api.interceptor;

import com.sports2020.demo.AppConstant;
import com.sports2020.demo.util.DateUtil;
import com.sports2020.demo.util.MD5Util;

import java.io.IOException;
import java.util.UUID;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 网络公共请求头配置类
 * <p>
 * Created by KarenChia on 2019/8/13.
 */
public class AddHeadersInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request originalRequest = chain.request();
        Request.Builder builder = originalRequest.newBuilder();
        // Provide your custom header here
        builder.header("plat", AppConstant.HTTP_HEADER_PLAT);
        builder.header("time", getTimeHeader());
        builder.header("guid", getGuidHeader());
        builder.header("sign", getSignHeader());
        builder.header("appId", AppConstant.HTTP_HEADER_APP_ID);
        Request.Builder requestBuilder = builder.method(originalRequest.method(), originalRequest.body());
        Request request = requestBuilder.build();
        return chain.proceed(request);
    }

    /**
     * 获取 sign
     *
     * @return sign
     */
    private String getSignHeader() {
        return MD5Util.md5Decode32(AppConstant.HTTP_HEADER_APP_ID + AppConstant.HTTP_HEADER_PLAT + getTimeHeader() + getGuidHeader());
    }

    /**
     * 获取 guid
     *
     * @return guid
     */
    private String getGuidHeader() {
        return UUID.randomUUID().toString();
    }

    /**
     * 获取请求头中需要配置的时间
     *
     * @return 请求头中需要配置的时间
     */
    private String getTimeHeader() {
        return DateUtil.getNowDate(DateUtil.DatePattern.ALL_TIME)
                .replace("-", "")
                .replace(" ", "")
                .replace(":", "");
    }
}
