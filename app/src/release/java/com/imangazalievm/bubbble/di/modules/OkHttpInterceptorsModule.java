package com.imangazalievm.bubbble.di.modules;

import android.support.annotation.NonNull;

import com.imangazalievm.bubbble.di.qualifiers.OkHttpInterceptors;
import com.imangazalievm.bubbble.di.qualifiers.OkHttpNetworkInterceptors;

import java.util.List;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Interceptor;

import static java.util.Collections.emptyList;

@Module
public class OkHttpInterceptorsModule {

    @Provides
    @OkHttpInterceptors
    @Singleton
    @NonNull
    public List<Interceptor> provideOkHttpInterceptors() {
        return emptyList();
    }

    @Provides
    @OkHttpNetworkInterceptors
    @Singleton
    @NonNull
    public List<Interceptor> provideOkHttpNetworkInterceptors() {
        return emptyList();
    }

}
