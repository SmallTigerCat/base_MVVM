package com.sports2020.demo.api;

import com.google.gson.JsonObject;
import com.sports2020.demo.AppConstant;
import com.sports2020.demo.util.DesUtil;

import java.util.Set;
import java.util.TreeSet;

import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * 简单的post请求体
 *
 * @author tsx
 */
public final class JsonRequestBody {

    public static final class Builder {

        private JsonObject jsonObject;
        private boolean isDes = false;

        public Builder() {
            jsonObject = new JsonObject();
        }

        public Builder setParam(String key, String value) {
            jsonObject.addProperty(key, value);
            return this;
        }

        public Builder setDesParam(String key, String value) {
            jsonObject.addProperty(key, DesUtil.getInstance().encode(value));
            return this;
        }

        public Builder setParam(String key, Number value) {
            jsonObject.addProperty(key, value);
            return this;
        }

        public Builder setDesParam(String key, Number value) {
            jsonObject.addProperty(key, DesUtil.getInstance().encode(value.toString()));
            return this;
        }

        public Builder des() {
            isDes = true;
            return this;
        }

        public RequestBody build() {
            return RequestBody.create(MediaType.parse(AppConstant.MEDIA_TYPE_JSON_STRING), jsonObject.toString());
        }

        /**
         * 所有参数再包装一层
         *
         * @return 请求体
         */
        public RequestBody wrapBuild() {
            String encode = isDes ? DesUtil.getInstance().encode(jsonObject.toString()) : jsonObject.toString();

            Set<String> keySet = new TreeSet<>(jsonObject.keySet());
            for (String key : keySet) {
                jsonObject.remove(key);
            }

            jsonObject.addProperty("value", encode);
            return RequestBody.create(MediaType.parse(AppConstant.MEDIA_TYPE_JSON_STRING), jsonObject.toString());
        }
    }
}