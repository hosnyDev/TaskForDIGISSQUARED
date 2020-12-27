package com.hosnydevtest.taskdigissquared.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by hosnyDev on 12/26/2020
 */
public class InternetUtil {

    private final Context context;

    public InternetUtil(Context context) {

        this.context = context;
    }

    public boolean isConnection() {

        ConnectivityManager conn = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        if (conn != null) {

            NetworkInfo info = conn.getActiveNetworkInfo();
            return info != null && info.isConnected();

        }

        return false;
    }

}
