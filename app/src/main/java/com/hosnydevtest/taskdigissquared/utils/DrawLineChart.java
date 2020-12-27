package com.hosnydevtest.taskdigissquared.utils;

import android.graphics.Color;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

/**
 * Created by hosnyDev on 12/26/2020
 */
public class DrawLineChart {

    private static DrawLineChart mInstance;

    public static synchronized DrawLineChart getInstance() {
        if (mInstance == null)
            mInstance = new DrawLineChart();
        return mInstance;
    }

    public void addEntry(LineChart chart, int dataSet1, String name1) {

        LineData data = chart.getData();


        if (data == null) {
            data = new LineData();
        }
        chart.setData(data);

        ILineDataSet set = data.getDataSetByIndex(0);

        if (set == null) {
            set = createSet1(name1);
            data.addDataSet(set);
        }

        int dataSetIndex = (int) (Math.random() * data.getDataSetCount());
        ILineDataSet randomSet = data.getDataSetByIndex(dataSetIndex);

        data.addEntry(new Entry(randomSet.getEntryCount(), (float) dataSet1), dataSetIndex);

        data.notifyDataChanged();

        // let the chart know it's data has changed
        chart.notifyDataSetChanged();

        chart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);

        chart.setVisibleXRangeMaximum(6);

        YAxis yAxisRight = chart.getAxisRight();
        yAxisRight.setEnabled(false);

        // chart.animateX(1000, Easing.EaseInQuad);

        // this automatically refreshes the chart (calls invalidate())
        chart.moveViewTo(data.getEntryCount() - 7, 50f, YAxis.AxisDependency.LEFT);

        chart.setDrawGridBackground(false);
        chart.getDescription().setEnabled(false);
        chart.setNoDataText("wait to load data...!");
        chart.invalidate();
    }

    private LineDataSet createSet1(String name1) {

        int color;

        switch (name1) {
            case "RSRP P":
                color = Color.parseColor("#47158E");
                break;
            case "RSRQ P":
                color = Color.parseColor("#DC3636");
                break;
            default:
                color = Color.parseColor("#2AAA5F");
                break;
        }

        LineDataSet set = new LineDataSet(null, name1);
        set.setLineWidth(2.5f);
        set.setCircleRadius(4.5f);
        set.setColor(color);
        set.setCircleColor(color);
        set.setHighLightColor(color);
        set.setAxisDependency(YAxis.AxisDependency.LEFT);
        set.setValueTextSize(10f);

        return set;
    }

}
