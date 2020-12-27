package com.hosnydevtest.taskdigissquared.utils;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.DecelerateInterpolator;

import com.hosnydevtest.taskdigissquared.R;

/**
 * Created by hosnyDev on 12/26/2020
 */
public class GoalProgressBar extends View {


    private Paint progressPaint;
    private int goal;
    private int _progressValue, _valueNumber, progress;

    private float goalIndicatorHeight;
    private int unfilledSectionColor;
    private int barThickness;


    public GoalProgressBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        progressPaint = new Paint();
        progressPaint.setStyle(Paint.Style.FILL_AND_STROKE);

        TypedArray typedArray = getContext().getTheme().obtainStyledAttributes(attrs, R.styleable.GoalProgressBar, 0, 0);
        try {
            setGoalIndicatorHeight(typedArray.getDimensionPixelSize(R.styleable.GoalProgressBar_goalIndicatorHeight, 10));
            setUnfilledSectionColor(typedArray.getColor(R.styleable.GoalProgressBar_unfilledSectionColor, Color.RED));
            setBarThickness(typedArray.getDimensionPixelOffset(R.styleable.GoalProgressBar_barThickness, 4));

        } finally {
            typedArray.recycle();
        }
    }

    @Override
    protected Parcelable onSaveInstanceState() {
        Bundle bundle = new Bundle();

        // save our added state - progress and goal
        bundle.putInt("progress", progress);
        bundle.putInt("goal", goal);

        // save super state
        bundle.putParcelable("superState", super.onSaveInstanceState());

        return bundle;
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        if (state instanceof Bundle) {
            Bundle bundle = (Bundle) state;

            // restore our added state - progress and goal
            // setProgress(bundle.getInt("progress"));
            setGoal(bundle.getInt("goal"));

            // restore super state
            state = bundle.getParcelable("superState");
        }

        super.onRestoreInstanceState(state);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int halfHeight = getHeight() / 2;
        int progressEndX = (int) (getWidth() * progress / 100f);

        // draw the filled portion of the bar
        progressPaint.setStrokeWidth(barThickness);

        progressPaint.setColor(calculateValueColor(_progressValue, _valueNumber));

        canvas.drawLine(0, halfHeight, progressEndX, halfHeight, progressPaint);

        // draw the unfilled portion of the bar
        progressPaint.setColor(unfilledSectionColor);
        canvas.drawLine(progressEndX, halfHeight, getWidth(), halfHeight, progressPaint);

    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = MeasureSpec.getSize(widthMeasureSpec);

        int specHeight = MeasureSpec.getSize(heightMeasureSpec);
        int height;
        if (MeasureSpec.getMode(heightMeasureSpec) == MeasureSpec.AT_MOST) {
            height = (int) Math.min(goalIndicatorHeight, specHeight);
        } else {
            height = specHeight;
        }

        // must call this, otherwise the app will crash
        setMeasuredDimension(width, height);
    }

    public void setProgress(int progress, int valueNumber) {
        _progressValue = progress;
        _valueNumber = valueNumber;
        setProgress(progress, true);
    }

    public void setProgress(final int progress, boolean animate) {
        if (animate) {
            ValueAnimator barAnimator = ValueAnimator.ofFloat(0, 1);

            barAnimator.setDuration(700);

            // reset progress without animating
            setProgress(0, false);

            barAnimator.setInterpolator(new DecelerateInterpolator());

            barAnimator.addUpdateListener(animation -> {
                float interpolation = (float) animation.getAnimatedValue();

                int p = progress;
                //convert negative value to positive
                if (p < 0)
                    p = Math.abs(p);
                setProgress((int) (interpolation * p), false);
            });

            if (!barAnimator.isStarted()) {
                barAnimator.start();
            }
        } else {
            this.progress = progress;
            postInvalidate();
        }
    }

    public void setGoal(int goal) {
        this.goal = goal;
        postInvalidate();
    }

    public void setGoalIndicatorHeight(float goalIndicatorHeight) {
        this.goalIndicatorHeight = goalIndicatorHeight;
        postInvalidate();
    }


    public void setUnfilledSectionColor(int unfilledSectionColor) {
        this.unfilledSectionColor = unfilledSectionColor;
        postInvalidate();
    }

    public void setBarThickness(int barThickness) {
        this.barThickness = barThickness;
        postInvalidate();
    }


    private int calculateValueColor(int progressValue, int valueNumber) {

        int color = progressValue;

        //convert negative value to positive
        if (color < 0)
            color = Math.abs(color);

        switch (valueNumber) {

            case 1: //RSRP
                color = setValue1(color);
                break;

            case 2: //RSRQ
                color = setValue2(color);
                break;

            default: //SINR
                color = setValue3(color);
                break;
        }

        return color;
    }

    private int setValue3(int progressValue) {

        int color = Color.parseColor("#000000");

        if (isBetween(progressValue, 0, 0))
            color = Color.parseColor("#000000");
        else if (isBetween(progressValue, 5, 0))
            color = Color.parseColor("#F90500");
        else if (isBetween(progressValue, 10, 5))
            color = Color.parseColor("#FD7632");
        else if (isBetween(progressValue, 15, 10))
            color = Color.parseColor("#FBFD00");
        else if (isBetween(progressValue, 20, 15))
            color = Color.parseColor("#00FF06");
        else if (isBetween(progressValue, 25, 20))
            color = Color.parseColor("#027500");
        else if (isBetween(progressValue, 30, 25))
            color = Color.parseColor("#0EFFF8");
        else if (isBetween(progressValue, 200, 30))
            color = Color.parseColor("#0000F0");

        return color;

    }

    private int setValue2(int progressValue) {

        int color = Color.parseColor("#000000");

        if (isBetween(progressValue, 200, 19.5))
            color = Color.parseColor("#000000");
        else if (isBetween(progressValue, 19.5, 14))
            color = Color.parseColor("#ff0000");
        else if (isBetween(progressValue, 14, 9))
            color = Color.parseColor("#ffee00");
        else if (isBetween(progressValue, 9, 3))
            color = Color.parseColor("#80ff00");
        else if (isBetween(progressValue, 3, 0))
            color = Color.parseColor("#80ff00");

        return color;
    }

    private int setValue1(int progressValue) {

        int color = Color.parseColor("#000A00");

        if (isBetween(progressValue, 200, 110))
            color = Color.parseColor("#000A00");
        else if (isBetween(progressValue, 110, 100))
            color = Color.parseColor("#E51304");
        else if (isBetween(progressValue, 100, 90))
            color = Color.parseColor("#FAFD0C");
        else if (isBetween(progressValue, 90, 80))
            color = Color.parseColor("#02FA0E");
        else if (isBetween(progressValue, 80, 70))
            color = Color.parseColor("#0B440D");
        else if (isBetween(progressValue, 70, 60))
            color = Color.parseColor("#0EFFF8");
        else if (isBetween(progressValue, 60, 0))
            color = Color.parseColor("#0007FF");

        return color;
    }

    public static boolean isBetween(int x, double upper, double lower) {
        return lower <= x && x <= upper;
    }
}