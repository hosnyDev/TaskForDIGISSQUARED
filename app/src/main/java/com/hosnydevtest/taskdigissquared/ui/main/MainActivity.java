package com.hosnydevtest.taskdigissquared.ui.main;


import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.hosnydevtest.taskdigissquared.R;
import com.hosnydevtest.taskdigissquared.databinding.ActivityMainBinding;
import com.hosnydevtest.taskdigissquared.utils.DrawLineChart;
import com.hosnydevtest.taskdigissquared.utils.GoalProgressBar;
import com.hosnydevtest.taskdigissquared.utils.InternetUtil;

/**
 * Created by hosnyDev on 12/26/2020
 */
public class MainActivity extends AppCompatActivity {

    private MainViewModel viewModel;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.AppTheme);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        viewModel = new ViewModelProvider(this).get(MainViewModel.class);

        initView();
        binding.btnTryInternet.setOnClickListener(v -> initView());

    }

    private void initView() {

        if (new InternetUtil(this).isConnection()) { //check internet connection

            viewModel.getMutableLiveDataList().observe(this, valuesModels -> {

                initProgress(binding.progressbar1, binding.txt1, valuesModels.getRSRP(), 1);
                initProgress(binding.progressbar2, binding.txt2, valuesModels.getRSRQ(), 2);
                initProgress(binding.progressbar3, binding.txt3, valuesModels.getSINR(), 3);

                DrawLineChart.getInstance().addEntry(binding.lineChart1, valuesModels.getRSRP(), "RSRP P");
                DrawLineChart.getInstance().addEntry(binding.lineChart2, valuesModels.getRSRQ(), "RSRQ P");
                DrawLineChart.getInstance().addEntry(binding.lineChart3, valuesModels.getSINR(), "SINR P");

            });

            viewModel.getMutableLiveDataError().observe(this, s ->
                    new AlertDialog.Builder(this)
                            .setMessage(s)
                            .create().show());

            viewModel.getMutableLiveDataProgress().observe(this, aBoolean -> {
                if (aBoolean) {
                    binding.progress.setVisibility(View.VISIBLE);
                    binding.layoutView.setVisibility(View.GONE);
                } else {
                    binding.progress.setVisibility(View.GONE);
                    binding.layoutView.setVisibility(View.VISIBLE);
                }
                binding.layoutNoInternet.setVisibility(View.GONE);
            });

        } else {
            Toast.makeText(this, getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show();
            binding.layoutNoInternet.setVisibility(View.VISIBLE);
            binding.layoutView.setVisibility(View.GONE);
        }

    }


    private void initProgress(GoalProgressBar progressBar, TextView textView, int value, int valueState) {

        //set value to text view
        textView.setText(String.valueOf(value));

        //convert negative value to positive
        if (value < 0)
            value = Math.abs(value);

        progressBar.setProgress(value, valueState);

    }

}
