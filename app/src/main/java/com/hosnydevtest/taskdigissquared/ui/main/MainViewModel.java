package com.hosnydevtest.taskdigissquared.ui.main;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.hosnydevtest.taskdigissquared.model.ValuesModel;
import com.hosnydevtest.taskdigissquared.repository.ValuesRepository;

/**
 * Created by hosnyDev on 12/26/2020
 */
public class MainViewModel extends ViewModel implements ValuesListener {

    private static final String TAG = "MainViewModel";

    private final MutableLiveData<ValuesModel> mutableLiveDataList = new MutableLiveData<>();
    private final MutableLiveData<String> mutableLiveDataError = new MutableLiveData<>();
    private final MutableLiveData<Boolean> mutableLiveDataProgress = new MutableLiveData<>();

    public MainViewModel() {
        ValuesRepository repository = new ValuesRepository(this);
        repository.getData();
    }

    public LiveData<ValuesModel> getMutableLiveDataList() {
        return mutableLiveDataList;
    }

    public LiveData<String> getMutableLiveDataError() {
        return mutableLiveDataError;
    }

    public LiveData<Boolean> getMutableLiveDataProgress() {
        return mutableLiveDataProgress;
    }


    @Override
    public void valuesDataAdded(ValuesModel getValues) {
        Log.d(TAG, "listDataAdded: RSRP -> " + getValues.getRSRP());
        Log.d(TAG, "listDataAdded: RSRQ -> " + getValues.getRSRQ());
        Log.d(TAG, "listDataAdded: SINR -> " + getValues.getSINR());
        mutableLiveDataList.postValue(getValues);
    }

    @Override
    public void onError(String error) {
        Log.d(TAG, "onError: " + error);
        mutableLiveDataError.postValue(error);
    }

    @Override
    public void isProgress(Boolean isProgress) {
        mutableLiveDataProgress.postValue(isProgress);
    }

}
