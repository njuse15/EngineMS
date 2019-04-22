package cn.nju.se15.enginems.model.service;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by 果宝 on 2018/ic_info/7.
 */

public class RetrofitServer {
    private static final String BASE_URL = "https://clevelandalto.site";
    private static final int DEFAULT_TIMEOUT = 10;
    private Retrofit retrofit;

    private static RetrofitServer mRetrofitServer;

    private RetrofitServer() {
        OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();
        httpClientBuilder.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);

        retrofit = new Retrofit.Builder()
                .client(httpClientBuilder.build())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(BASE_URL)
                .build();
    }

    public static synchronized RetrofitServer getInstance() {
        if(mRetrofitServer == null) {
            mRetrofitServer = new RetrofitServer();
        }
        return mRetrofitServer;
    }

    public <T> T create(Class<T> service) {
        return retrofit.create(service);
    }
}
