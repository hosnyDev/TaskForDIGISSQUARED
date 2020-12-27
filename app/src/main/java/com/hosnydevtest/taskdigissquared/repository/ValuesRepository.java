package com.hosnydevtest.taskdigissquared.repository;

import com.hosnydevtest.taskdigissquared.data.RetrofitClint;
import com.hosnydevtest.taskdigissquared.model.ValuesModel;
import com.hosnydevtest.taskdigissquared.ui.main.ValuesListener;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by hosnyDev on 12/26/2020
 */
public class ValuesRepository {

    private final ValuesListener listener;

    public ValuesRepository(ValuesListener listener) {
        this.listener = listener;
    }

    public void getData() {

        listener.isProgress(true);

        Observable<ValuesModel> observable = RetrofitClint.getInstance().getValues()
                .observeOn(Schedulers.newThread())
                .repeatWhen(completed -> completed.delay(2, TimeUnit.SECONDS));

        observable.subscribe(
                valuesModel -> {
                    listener.valuesDataAdded(valuesModel);
                    listener.isProgress(false);
                },
                error -> {
                    listener.isProgress(false);
                    listener.onError(error.getMessage());
                });

    }

}
