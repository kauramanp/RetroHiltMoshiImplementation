package com.aman.retrofitmoshihiltapp

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    fun provideBaseUrl() = "https://gorest.co.in/public/v2/"

    @Provides
    @Singleton
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            if (BuildConfig.DEBUG) {
                setLevel(HttpLoggingInterceptor.Level.BODY)
            } else {
                setLevel(HttpLoggingInterceptor.Level.NONE)
            }
        }
    }

    @Singleton
    @Provides
    fun provideHttpClient(
        httpLoggingInterceptor: HttpLoggingInterceptor,
//        @TokenInterceptor tokenInterceptor: Interceptor,
//        @LangInterceptor langInterceptor: Interceptor,
//        @Named("DeviceInfoInterceptor") deviceInfoInterceptor: Interceptor
    ): OkHttpClient {
        return OkHttpClient
            .Builder().apply {
                readTimeout(120, TimeUnit.SECONDS)
                connectTimeout(120, TimeUnit.SECONDS)
                writeTimeout(120, TimeUnit.SECONDS)
//                addInterceptor(deviceInfoInterceptor)
//                addInterceptor(langInterceptor)
//                addInterceptor(tokenInterceptor)
                addInterceptor(httpLoggingInterceptor)
            }
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(BASE_URL: String,
                        okHttpClient: OkHttpClient,
    ): Retrofit =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): ApiService =
        retrofit.create(ApiService::class.java)
}
