package com.airlinesdemoapp.core.di

import com.airlinesdemoapp.core.navigation.AppNavigator
import com.airlinesdemoapp.core.navigation.AppNavigatorImp
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

// add in activity class not in the whole app
@InstallIn(ActivityComponent::class)
@Module
abstract class NavigationModule {
    @Binds
    abstract fun bindsAppNavigator(appNavigator: AppNavigatorImp): AppNavigator
}