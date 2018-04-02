package com.boe.tww.bluetooth;

import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.boe.tww.builders.SciChartBuilder;
import com.scichart.charting.model.dataSeries.IXyDataSeries;
import com.scichart.charting.model.dataSeries.XyDataSeries;
import com.scichart.charting.visuals.SciChartSurface;
import com.scichart.charting.visuals.axes.AutoRange;
import com.scichart.charting.visuals.axes.IAxis;
import com.scichart.charting.visuals.renderableSeries.FastLineRenderableSeries;
import com.scichart.charting.visuals.renderableSeries.IRenderableSeries;
import com.scichart.core.framework.UpdateSuspender;
import com.scichart.data.model.DoubleRange;
import com.scichart.data.model.ISciList;
import com.scichart.drawing.common.PenStyle;

import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class fragment1 extends Fragment {
    public double time = 0.0;
    private final static long TIME_INTERVAL = 200;
    private final double sampleRate =5;
    private final static int fifoCapacity = 50;
    private int a = 1;
    private final static double ONE_OVER_TIME_INTERVAL = 1.0 / TIME_INTERVAL;
    private final static double VISIBLE_RANGE_MAX = fifoCapacity * ONE_OVER_TIME_INTERVAL;
    private final static double GROW_BY = VISIBLE_RANGE_MAX * 0.1;
    private final static DoubleRange xVisibleRange = new DoubleRange(-GROW_BY, VISIBLE_RANGE_MAX + GROW_BY);

    public final static SciChartBuilder sciChartBuilder = SciChartBuilder.instance();
    public final static IXyDataSeries<Double, Double> series0 =
            sciChartBuilder.newXyDataSeries(Double.class, Double.class).
                    withFifoCapacity(fifoCapacity).build();

    private final static IXyDataSeries<Double, Double> series1 =
            sciChartBuilder.newXyDataSeries(Double.class, Double.class).
                    withFifoCapacity(fifoCapacity).build();

    public static ArrayList<Integer> sourceData = new ArrayList();
    private int _currentIndex;
    public static int _totalIndex;

    private fragment1.TraceAOrB whichTrace = fragment1.TraceAOrB.TraceA;
    private final ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
    private ScheduledFuture<?> schedule;
    private static SciChartSurface chartSurface;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment1, null);
        chartSurface = (SciChartSurface) root.findViewById(R.id.sci_chart);
        //设置边框
        chartSurface.setRenderableSeriesAreaBorderStyle(
                new PenStyle(getResources().getColor(R.color.wavebackgroung),true,2f));//2f是线条的粗细
        initOCGExample();
        //
        return root;
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.e("jin","--------是不是ocg进入到了onActivityCreated状态");

        if (savedInstanceState != null) {
            _currentIndex = savedInstanceState.getInt("currentIndex");
            _totalIndex = savedInstanceState.getInt("totalIndex");
            final ISciList<Double> xValues0 = savedInstanceState.getParcelable("xValues0");
            final ISciList<Double> yValues0 = savedInstanceState.getParcelable("yValues0");
            series0.append(xValues0, yValues0);
            final ISciList<Double> xValues1 = savedInstanceState.getParcelable("xValues1");
            final ISciList<Double> yValues1 = savedInstanceState.getParcelable("yValues1");
            series1.append(xValues1, yValues1);
        }
    }


    private void initOCGExample() {
        UpdateSuspender.using(chartSurface, new Runnable() {
            @Override
            public void run() {
                final IAxis xBottomAxis = sciChartBuilder
                        .newNumericAxis()
//                        .withGrowBy(1, 1)
                        .withDrawMajorBands(true)
                        .withVisibleRange(xVisibleRange)
                        .build();
                final IAxis yRightAxis = sciChartBuilder
                        .newNumericAxis()
//                        .withGrowBy(1, 1)
                        .withAutoRangeMode(AutoRange.Never)
                        .withDrawMajorBands(true)
                        .withVisibleRange(1, 20)
                        .build();
                final FastLineRenderableSeries rSeries = sciChartBuilder.newLineSeries()
                        .withStrokeStyle(0xFF99EE99, 1f, true)
                        .withIsDigitalLine(true)
                        .build();
                final IRenderableSeries rs1 = sciChartBuilder.newLineSeries()
                        .withDataSeries(series0)
                        //设置划线颜色&粗细
                        .withStrokeStyle(Color.rgb(213,145,253),1.3f,true)
                        .build();
                Collections.addAll(chartSurface.getXAxes(), xBottomAxis);
                Collections.addAll(chartSurface.getYAxes(), yRightAxis);
                Collections.addAll(chartSurface.getRenderableSeries(), rs1);
//                Collections.addAll(chartSurface.getRenderableSeries(), rs1, rs2);
            }
        });

        schedule = scheduledExecutorService.scheduleWithFixedDelay(new Runnable() {
            @Override
            public void run() {
                UpdateSuspender.using(chartSurface, appendDataRunnable);

            }
        }, 0, TIME_INTERVAL, TimeUnit.MILLISECONDS);
    }

    private final Runnable appendDataRunnable = new Runnable() {
        @Override
        public void run() {
            a= 1 + (int) (Math.random() * 20);
            sourceData.add(a);
            a= 1 + (int) (Math.random() * 20);
            sourceData.add(a);
            if(sourceData!=null && sourceData.size()>1){
                appendPoint(sampleRate);
                sourceData.clear();
            }
        }
    };

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt("currentIndex", _currentIndex);
        outState.putInt("totalIndex", _totalIndex);
        outState.putParcelable("xValues0", series0.getXValues());
        outState.putParcelable("yValues0", series0.getYValues());
        outState.putParcelable("xValues1", series1.getXValues());
        outState.putParcelable("yValues1", series1.getYValues());
    }

    public static void cleanPoint (){
        xVisibleRange.setMinMax(-GROW_BY, VISIBLE_RANGE_MAX + GROW_BY);
        UpdateSuspender.using(chartSurface, new Runnable() {
            @Override
            public void run() {
                series0.clear();
            }
        });

    }

    private synchronized void appendPoint(double sampleRate) {

        if (_currentIndex >= sourceData.size()) {
            _currentIndex = 0;
//            schedule.cancel(true);
        }
        // Get the next value and time, and append to the chart
        double ocgSignal = sourceData.get(_currentIndex);
//        time = (_totalIndex / sampleRate) % 10;
//        if(time==0){
//            cleanPoint();
//        }
        time += ONE_OVER_TIME_INTERVAL;

        if(time > VISIBLE_RANGE_MAX)
            xVisibleRange.setMinMax(xVisibleRange.getMin() + ONE_OVER_TIME_INTERVAL, xVisibleRange.getMax() + ONE_OVER_TIME_INTERVAL);
//        time = _totalIndex/2;
        if (true) {//让波形图暂停还是开始  与Cnoga sdk  切换
//        if ((sourceData.size() != 0)&&(!DeviceDataActivity.devicesStop)) {//让波形图暂停还是开始  与Cnoga sdk  切换
            series0.append(time, ocgSignal);
            _currentIndex++;
            _totalIndex++;

//            if (_totalIndex % (sampleRate*8) == 0) {
//                whichTrace = whichTrace == TraceAOrB.TraceA ? TraceAOrB.TraceB : TraceAOrB.TraceA;
//            }
        }
    }




    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (schedule != null) {
            schedule.cancel(true);
        }
//        cleanPoint();
        Log.e("jin","--------是不是ocg进入到了onDestroyView状态");

    }

    enum TraceAOrB {
        TraceA,
        TraceB
    }
}
