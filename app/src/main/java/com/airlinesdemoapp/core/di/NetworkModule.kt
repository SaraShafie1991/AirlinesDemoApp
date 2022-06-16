package com.airlinesdemoapp.core.di

import android.content.Context
import com.airlinesdemoapp.BuildConfig
import com.airlinesdemoapp.core.common.AppConst.BASE_URL
import com.airlinesdemoapp.core.common.AppConst.TIMEOUT_REQUEST
import com.airlinesdemoapp.data.api.RestAPI
import com.airlinesdemoapp.data.interceptor.AuthInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object NetworkModule {
    @Singleton
    @Provides
    fun providesLoggingInterceptor() : HttpLoggingInterceptor{
        val loggingInterceptor = HttpLoggingInterceptor()
        if(BuildConfig.DEBUG){
            loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        }
        return loggingInterceptor
    }

    @Singleton
    @Provides
    fun providesAuthInterceptor(@ApplicationContext applicationContext: Context) : AuthInterceptor{
        return AuthInterceptor(applicationContext)
    }

    @Singleton
    @Provides
    fun providesOkhttpClient(authInterceptor: AuthInterceptor,
                             loggingInterceptor: HttpLoggingInterceptor) : OkHttpClient{
        return OkHttpClient.Builder()
            .addInterceptor(authInterceptor)
            .addInterceptor(loggingInterceptor)
            .connectTimeout(TIMEOUT_REQUEST, TimeUnit.SECONDS)
            .readTimeout(TIMEOUT_REQUEST, TimeUnit.SECONDS)
            .writeTimeout(TIMEOUT_REQUEST, TimeUnit.SECONDS)
            .build()
    }

    @Singleton
    @Provides
    fun providesRetrofit(okHttpClient: OkHttpClient):Retrofit.Builder{
        return Retrofit.Builder()
            .client(okHttpClient)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
    }

    @Singleton
    @Provides
    fun providesFoursquareAPI(retrofit: Retrofit.Builder):RestAPI{
        return retrofit.baseUrl(BASE_URL).build().create(RestAPI::class.java)
    }
}