package com.sports2020.demo.repo;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;

import com.jiabuedu_android.directsalestore.AppExecutors;
import com.sports2020.demo.AppApplication;
import com.sports2020.demo.api.ApiException;
import com.sports2020.demo.api.ApiService;
import com.sports2020.demo.api.JsonRequestBody;
import com.sports2020.demo.api.Resource;
import com.sports2020.demo.api.response.CommonResponseBody;
import com.sports2020.demo.api.response.LoginData;
import com.sports2020.demo.db.User;
import com.sports2020.demo.db.UserDao;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.RequestBody;

/**
 * 用户仓库
 *
 * @author tsx
 */
@Singleton
public class UserRepo {

    private ApiService apiService;
    private UserDao userDao;
    private AppExecutors appExecutors;

    @Inject
    public UserRepo(ApiService apiService, UserDao userDao, AppExecutors appExecutors) {
        this.apiService = apiService;
        this.userDao = userDao;
        this.appExecutors = appExecutors;
    }

    /**
     * @param userName
     * @param password
     */
    public void login(String userName, String password, MediatorLiveData<Resource<LoginData>> result) {
        result.setValue(Resource.Companion.loading());
        //构建post请求体
        RequestBody requestBody = new JsonRequestBody.Builder()
                .setParam("userName", userName)
                .setParam("password", password)
                .build();
//        //发起请求
//        Disposable disposable = apiService.login(requestBody)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(
//                        (CommonResponseBody<LoginData> responseBody) -> {
//                            if (responseBody.isSuccess()) {
//                                LoginData loginData = responseBody.getData();
//                                Log.d("UserRepo", "LoginData: " + loginData);
//                                result.setValue(Resource.Companion.success(loginData));
//                                User user = loginData.toUser();
//                                insertUer(user);
//                                AppApplication.getInstance().setCurrentUser(user);
//                            } else
//                                result.setValue(Resource.Companion.error(responseBody.getMessage()));
//                        },
//                        (Throwable throwable) -> {
//                            String errorMsg = ApiException.getInstance().getOnErrorMsg(throwable);
//                            result.setValue(Resource.Companion.error(errorMsg));
//                        });
    }

    public LiveData<User> getCurrentUser() {
        return userDao.getUser();
    }

    public void insertUer(User user) {
        appExecutors.diskIO().execute(() -> {
            userDao.insert(user);
        });
    }

    public void logout() {
        User user = AppApplication.getInstance().getCurrentUser();
        user.setActive(0);
        insertUer(user);
    }
}
