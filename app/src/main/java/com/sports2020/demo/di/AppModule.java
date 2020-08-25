package com.sports2020.demo.di;

import android.app.Application;
import android.text.TextUtils;
import android.util.Log;

import androidx.room.Room;

import com.sports2020.demo.AppConfig;
import com.sports2020.demo.AppConstant;
import com.sports2020.demo.BuildConfig;
import com.sports2020.demo.api.ApiService;
import com.sports2020.demo.api.interceptor.AddHeadersInterceptor;
import com.sports2020.demo.api.interceptor.AddQueryParameterInterceptor;
import com.sports2020.demo.db.AppDatabase;
import com.sports2020.demo.db.UserDao;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.ApplicationComponent;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

@Module
@InstallIn(ApplicationComponent.class)
public class AppModule {

    @Singleton
    @Provides
    ApiService provideApiService(OkHttpClient client) {
        return new Retrofit.Builder()
                //支持返回Call<String>
                .addConverterFactory(ScalarsConverterFactory.create())
                //支持直接格式化json返回Bean对象
                .addConverterFactory(GsonConverterFactory.create())
                //支持RxJava
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(AppConfig.getApiUrl())
                .client(client)
                .build()
                .create(ApiService.class);
    }

    @Singleton
    @Provides
    OkHttpClient provideOkHttpClient() {
        //Retrofit是对OkHttp的封装，底层配置还是需要针对OkHttp
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        //设置网络请求相关超时时间
        builder.connectTimeout(AppConstant.CONN_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(AppConstant.READ_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(AppConstant.WRITE_TIMEOUT, TimeUnit.SECONDS);

        //启用连接错误重连
        builder.retryOnConnectionFailure(true);
        //DEBUG版本，拦截网络请求，打印网络日志信息
        if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor(message -> {
                if (TextUtils.isEmpty(message)) {
                    return;
                }
                Log.d("http", message);
                //打印网络请求中以JSON字符串格式返回的数据
//                    String s = message.substring(0, 1);
//                    if ("{".equals(s) || "[".equals(s)) {
//                        Log.d("http", message);
//                    }
            });
            httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            builder.addInterceptor(httpLoggingInterceptor);
        }
        //设置公共请求参数
        builder.addInterceptor(new AddQueryParameterInterceptor());
        //设置请求头 也是通过拦截器
        builder.addInterceptor(new AddHeadersInterceptor());
        return builder.build();
    }

    @Singleton
    @Provides
    AppDatabase provideDb(Application app) {
        return Room
                .databaseBuilder(app, AppDatabase.class, AppConstant.DB_NAME)
                .fallbackToDestructiveMigration()
                .build();

    }

    @Singleton
    @Provides
    UserDao provideUserDao(AppDatabase db) {
        return db.userDao();
    }

}
