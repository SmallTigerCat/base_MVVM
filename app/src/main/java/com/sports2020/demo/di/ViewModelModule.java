package com.sports2020.demo.di;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.sports2020.demo.SplashViewModel;
import com.sports2020.demo.viewmodel.ViewModelFactory;

import dagger.Binds;
import dagger.Module;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.ActivityComponent;
import dagger.multibindings.IntoMap;

@Module
@InstallIn(ActivityComponent.class)
public abstract class ViewModelModule {

    @Binds
    abstract ViewModelProvider.Factory bindViewModelFactory(ViewModelFactory factory);

    @Binds
    @IntoMap
    @ViewModelKey(SplashViewModel.class)
    abstract ViewModel bindSplashViewModel(SplashViewModel splashViewModel);
//
//    @Binds
//    @IntoMap
//    @ViewModelKey(LoginViewModel.class)
//    abstract ViewModel bindLoginViewModel(LoginViewModel loginViewModel);
//
//    @Binds
//    @IntoMap
//    @ViewModelKey(MainViewModel.class)
//    abstract ViewModel bindMainViewModel(MainViewModel mainViewModel);

}
