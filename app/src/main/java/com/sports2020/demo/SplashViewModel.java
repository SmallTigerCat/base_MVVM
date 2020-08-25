package com.sports2020.demo;

import androidx.lifecycle.LiveData;

import com.sports2020.demo.db.User;
import com.sports2020.demo.repo.UserRepo;
import com.sports2020.demo.viewmodel.BaseViewModel;

import javax.inject.Inject;

/**
 * 启动页 viewModel
 *
 * @author tsx
 */
public class SplashViewModel extends BaseViewModel {

    public LiveData<User> user;

    @Inject
    public SplashViewModel(UserRepo userRepo) {
        user = userRepo.getCurrentUser();
    }
}
