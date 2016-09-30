package com.fangzhich.sneakerlab.base.data.net;

import android.util.Base64;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import timber.log.Timber;

/**
 * Oauth Service Generator
 * Created by Khorium on 2016/8/30.
 */
class ServiceGenerator {

    //Connection time out
    private static int TIME_OUT = 5;

    private static OkHttpClient.Builder mOkHttpBuilder = new OkHttpClient.Builder()
            .addInterceptor(new HttpLogInterceptor(new HttpLogInterceptor.Logger() {
                @Override
                public void log(String message) {
                    Timber.d(message);
                }
            }).setLevel(HttpLogInterceptor.Level.BODY))
            .connectTimeout(TIME_OUT, TimeUnit.SECONDS);

    private static Retrofit.Builder mRetrofitBuilder = new Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .baseUrl(BaseApi.BASE_URL);

    public static Class<ServiceGenerator> setTimeOut(int timeOut) {
        TIME_OUT = timeOut;
        return ServiceGenerator.class;
    }


    static <S> S createService(Class<S> serviceClass) {
        return mRetrofitBuilder
                .client(mOkHttpBuilder.build())
                .build()
                .create(serviceClass);
    }

    public static <S> S createService(Class<S> serviceClass, String username, String password) {
        if (username != null && password != null) {
            String credentials = username + ":" + password;
            final String basic =
                    "Basic " + Base64.encodeToString(credentials.getBytes(), Base64.NO_WRAP);

            mOkHttpBuilder.addInterceptor(new Interceptor() {
                @Override
                public Response intercept(Interceptor.Chain chain) throws IOException {
                    Request original = chain.request();

                    Request.Builder requestBuilder = original.newBuilder()
                            .header("Authorization", basic)
                            .header("Accept", "application/json")
                            .method(original.method(), original.body());

                    Request request = requestBuilder.build();
                    return chain.proceed(request);
                }
            });
        }
        return createService(serviceClass);
    }

    public static <S> S createService(Class<S> serviceClass, final AccessToken token) {
        if (token != null) {
            mOkHttpBuilder.addInterceptor(new Interceptor() {
                @Override
                public Response intercept(Interceptor.Chain chain) throws IOException {
                    Request original = chain.request();

                    Request.Builder requestBuilder = original.newBuilder()
                            .header("Accept", "application/json")
                            .header("Authorization",
                                    token.getTokenType() + " " + token.getAccessToken())
                            .method(original.method(), original.body());

                    Request request = requestBuilder.build();
                    return chain.proceed(request);
                }
            });
        }

        return createService(serviceClass);
    }

    public static <S> S createService(Class<S> serviceClass, final String clientAccessToken) {
        if (clientAccessToken != null) {
            mOkHttpBuilder.addInterceptor(new Interceptor() {
                @Override
                public Response intercept(Interceptor.Chain chain) throws IOException {
                    Request original = chain.request();

                    Request.Builder requestBuilder = original.newBuilder()
                            .header("Accept", "application/json")
                            .header("Authorization",
                                    clientAccessToken)
                            .method(original.method(), original.body());

                    Request request = requestBuilder.build();
                    return chain.proceed(request);
                }
            });
        }

        return createService(serviceClass);
    }

    private class AccessToken {

        public AccessToken() {
        }

        public AccessToken(String tokenType, String accessToken) {
            this.tokenType = tokenType;
            this.accessToken = accessToken;
        }

        private String tokenType;
        private String accessToken;

        String getTokenType() {
            return tokenType;
        }

        String getAccessToken() {
            return accessToken;
        }
    }


}
