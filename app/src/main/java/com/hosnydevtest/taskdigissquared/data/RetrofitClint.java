package com.hosnydevtest.taskdigissquared.data;

import com.hosnydevtest.taskdigissquared.model.ValuesModel;

import io.reactivex.Observable;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by hosnyDev on 12/26/2020
 */
public class RetrofitClint {

    private static final String BASE_URL = "http://51.195.89.92:6000/";
    private final Retrofit retrofit;
    private static RetrofitClint mInstance;

    public static synchronized RetrofitClint getInstance() {
        if (mInstance == null)
            mInstance = new RetrofitClint();
        return mInstance;
    }

    public RetrofitClint() {
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    public Api getApi() {
        return retrofit.create(Api.class);
    }

    public Observable<ValuesModel> getValues() {
        return getApi().getValues();
    }

}
