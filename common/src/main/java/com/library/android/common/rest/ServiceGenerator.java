package com.library.android.common.rest;

import com.library.android.common.BuildConfig;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 11/3/2019
 * Adaptive, flexible and sustainable retrofit client class for network api calls
 * <p>
 * All class members are static here because we don't want to eat up memory by creating new objects each and every time
 * to use unnecessary new open socket connection. We need only one to reuse open socket connections.
 * </p>
 *
 * @author srdpatel
 * @see <a href="https://futurestud.io/tutorials/retrofit-2-creating-a-sustainable-android-client">Source</a>
 * @since 1.0
 */
public class ServiceGenerator {

    // comment by srdpatel: 11/3/2019 Replace base url with your own
    private static final String BASE_URL = "https://api.github.com/";

    private static Retrofit.Builder builder =
            new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create());

    private static Retrofit retrofit = builder.build();

    private static HttpLoggingInterceptor logging =
            new HttpLoggingInterceptor();

    private static OkHttpClient.Builder httpClient =
            new OkHttpClient.Builder();

    public static <S> S createService(
            Class<S> serviceClass) {
        if (BuildConfig.DEBUG && !httpClient.interceptors().contains(logging)) {
            logging.level(HttpLoggingInterceptor.Level.BODY);
            httpClient.addInterceptor(logging);
            builder.client(httpClient.build());
            retrofit = builder.build();
        }

        return retrofit.create(serviceClass);
    }
}