package com.hosnydevtest.taskdigissquared.data;

import com.hosnydevtest.taskdigissquared.model.ValuesModel;

import io.reactivex.Observable;
import retrofit2.http.GET;

/**
 * Created by hosnyDev on 12/26/2020
 */
public interface Api {

    @GET("random")
    Observable<ValuesModel> getValues();

}
