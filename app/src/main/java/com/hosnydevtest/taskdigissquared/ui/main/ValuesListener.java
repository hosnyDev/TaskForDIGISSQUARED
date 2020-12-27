package com.hosnydevtest.taskdigissquared.ui.main;

import com.hosnydevtest.taskdigissquared.model.ValuesModel;

/**
 * Created by hosnyDev on 12/26/2020
 */
public interface ValuesListener {

    void valuesDataAdded(ValuesModel getValues);

    void onError(String error);

    void isProgress(Boolean isProgress);
}
