package com.imangazalievm.bubbble.di.modules;

import android.support.annotation.NonNull;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.imangazalievm.bubbble.BuildConfig;
import com.imangazalievm.bubbble.data.network.DribbbleApiService;
import com.imangazalievm.bubbble.data.network.ErrorHandler;
import com.imangazalievm.bubbble.data.network.NetworkChecker;
import com.imangazalievm.bubbble.data.network.interceptors.DribbbleTokenInterceptor;
import com.imangazalievm.bubbble.data.network.interceptors.NetworkCheckInterceptor;
import com.imangazalievm.bubbble.data.repository.CommentsRepository;
import com.imangazalievm.bubbble.data.repository.FollowersRepository;
import com.imangazalievm.bubbble.data.repository.ImagesRepository;
import com.imangazalievm.bubbble.data.repository.ShotsRepository;
import com.imangazalievm.bubbble.data.repository.UsersRepository;
import com.imangazalievm.bubbble.di.qualifiers.OkHttpInterceptors;
import com.imangazalievm.bubbble.di.qualifiers.OkHttpNetworkInterceptors;
import com.imangazalievm.bubbble.domain.repository.ICommentsRepository;
import com.imangazalievm.bubbble.domain.repository.IFollowersRepository;
import com.imangazalievm.bubbble.domain.repository.IImagesRepository;
import com.imangazalievm.bubbble.domain.repository.IShotsRepository;
import com.imangazalievm.bubbble.domain.repository.IUsersRepository;

import java.util.List;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class DataModule {

    private final String baseUrl;

    public DataModule(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    @Provides
    @Singleton
    IShotsRepository provideBookRepository(ShotsRepository bookRepository) {
        return bookRepository;
    }

    @Provides
    @Singleton
    ICommentsRepository provideCommentsRepository(CommentsRepository commentsRepository) {
        return commentsRepository;
    }

    @Provides
    @Singleton
    IUsersRepository provideUsersRepository(UsersRepository usersRepository) {
        return usersRepository;
    }

    @Provides
    @Singleton
    IFollowersRepository provideFollowersRepository(FollowersRepository followersRepository) {
        return followersRepository;
    }

    @Provides
    @Singleton
    IImagesRepository provideImagesRepository(ImagesRepository imagesRepository) {
        return imagesRepository;
    }

    @Provides
    @NonNull
    @Singleton
    public OkHttpClient provideOkHttpClient(NetworkChecker networkChecker,
                                            @OkHttpInterceptors @NonNull List<Interceptor> interceptors,
                                            @OkHttpNetworkInterceptors @NonNull List<Interceptor> networkInterceptors) {
        final OkHttpClient.Builder okHttpBuilder = new OkHttpClient.Builder();
        okHttpBuilder.addInterceptor(new NetworkCheckInterceptor(networkChecker));
        okHttpBuilder.addInterceptor(new DribbbleTokenInterceptor(BuildConfig.DRIBBBLE_CLIENT_ACCESS_TOKEN));

        for (Interceptor interceptor : interceptors) {
            okHttpBuilder.addInterceptor(interceptor);
        }

        for (Interceptor networkInterceptor : networkInterceptors) {
            okHttpBuilder.addNetworkInterceptor(networkInterceptor);
        }

        return okHttpBuilder.build();
    }

    @Provides
    @Singleton
    Retrofit provideRetrofit(OkHttpClient okHttpClient) {
        Gson gson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create();
        return new Retrofit.Builder()
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(baseUrl)
                .build();
    }

    @Provides
    @Singleton
    Gson provideGson() {
        return new Gson();
    }

    @Provides
    @Singleton
    ErrorHandler provideErrorHandler(Gson gson) {
        return new ErrorHandler(gson);
    }

    @Provides
    @Singleton
    DribbbleApiService provideApi(Retrofit retrofit) {
        return retrofit.create(DribbbleApiService.class);
    }

}
