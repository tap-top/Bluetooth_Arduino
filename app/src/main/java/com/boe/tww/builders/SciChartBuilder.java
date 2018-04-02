//******************************************************************************
// SCICHART® Copyright SciChart Ltd. 2011-2016. All rights reserved.
//
// Web: http://www.scichart.com
// Support: support@scichart.com
// Sales:   sales@scichart.com
//
// SciChartBuilder.java is part of the SCICHART® Examples. Permission is hereby granted
// to modify, create derivative works, distribute and publish any part of this source
// code whether for commercial, private or personal use.
//
// The SCICHART® examples are distributed in the hope that they will be useful, but
// without any warranty. It is provided "AS IS" without warranty of any kind, either
// expressed or implied.
//******************************************************************************

package com.boe.tww.builders;

import android.content.Context;
import android.util.DisplayMetrics;

import com.scichart.charting.visuals.pointmarkers.IPointMarker;
import com.scichart.drawing.common.FontStyle;
import com.scichart.drawing.common.PenStyle;

public final class SciChartBuilder {
    private final DisplayMetrics displayMetrics;
    private final Context context;

    private static SciChartBuilder INSTANCE;

    public static void init(Context context) {
        INSTANCE = new SciChartBuilder(context);
    }

    public static void dispose() {
        INSTANCE = null;
    }

    public static SciChartBuilder instance() {
        return INSTANCE;
    }

    private SciChartBuilder(Context context){
        this.context = context;
        this.displayMetrics = context.getResources().getDisplayMetrics();
    }

    public PenStyle.Builder newPen() { return new PenStyle.Builder(displayMetrics); }

    public FontStyle.Builder newFont() { return new FontStyle.Builder(displayMetrics); }



    public <T extends IPointMarker> PointMarkerBuilder<T> newPointMarker(T pointMarker) { return new PointMarkerBuilder<>(pointMarker, displayMetrics); }



    public RenderableSeriesBuilder.FastLineRenderableSeriesBuilder newLineSeries() { return new RenderableSeriesBuilder.FastLineRenderableSeriesBuilder(displayMetrics); }

    public RenderableSeriesBuilder.FastMountainRenderableSeriesBuilder newMountainSeries() { return new RenderableSeriesBuilder.FastMountainRenderableSeriesBuilder(displayMetrics); }

    public RenderableSeriesBuilder.FastColumnRenderableSeriesBuilder newColumnSeries() { return new RenderableSeriesBuilder.FastColumnRenderableSeriesBuilder(displayMetrics); }

    public RenderableSeriesBuilder.FastImpulseRenderableSeriesBuilder newImpulseSeries() { return new RenderableSeriesBuilder.FastImpulseRenderableSeriesBuilder(displayMetrics); }

    public RenderableSeriesBuilder.XyScatterRenderableSeriesBuilder newScatterSeries() { return new RenderableSeriesBuilder.XyScatterRenderableSeriesBuilder(displayMetrics); }

    public RenderableSeriesBuilder.FastBandRenderableSeriesBuilder newBandSeries() { return new RenderableSeriesBuilder.FastBandRenderableSeriesBuilder(displayMetrics); }

    public RenderableSeriesBuilder.FastCandlestickRenderableSeriesBuilder newCandlestickSeries() { return new RenderableSeriesBuilder.FastCandlestickRenderableSeriesBuilder(displayMetrics); }

    public RenderableSeriesBuilder.FastOhlcRenderableSeriesBuilder newOhlcSeries() { return new RenderableSeriesBuilder.FastOhlcRenderableSeriesBuilder(displayMetrics); }

    public RenderableSeriesBuilder.FastErrorBarsRenderableSeriesBuilder newErrorBarsSeries() { return new RenderableSeriesBuilder.FastErrorBarsRenderableSeriesBuilder(displayMetrics); }

    public RenderableSeriesBuilder.FastFixedErrorBarsRenderableSeriesBuilder newFixedErrorBarsSeries() { return new RenderableSeriesBuilder.FastFixedErrorBarsRenderableSeriesBuilder(displayMetrics); }

    public RenderableSeriesBuilder.FastUniformHeatmapRenderableSeriesBuilder newUniformHeatmap() {return new RenderableSeriesBuilder.FastUniformHeatmapRenderableSeriesBuilder(displayMetrics); }



    public AxisBuilder.NumericAxisBuilder newNumericAxis() { return new AxisBuilder.NumericAxisBuilder(context); }

    public AxisBuilder.DateAxisBuilder newDateAxis() { return new AxisBuilder.DateAxisBuilder(context); }

    public AxisBuilder.CategoryDateAxisBuilder newCategoryDateAxis() { return new AxisBuilder.CategoryDateAxisBuilder(context); }



    public <TX extends Comparable<TX>, TY extends Comparable<TY>> DataSeriesBuilder.XyDataSeriesBuilder<TX, TY> newXyDataSeries(Class<TX> xType, Class<TY> yType) { return new DataSeriesBuilder.XyDataSeriesBuilder<>(xType, yType); }

    public <TX extends Comparable<TX>, TY extends Comparable<TY>> DataSeriesBuilder.OhlcDataSeriesBuilder<TX, TY> newOhlcDataSeries(Class<TX> xType, Class<TY> yType) { return new DataSeriesBuilder.OhlcDataSeriesBuilder<>(xType, yType); }

    public <TX extends Comparable<TX>, TY extends Comparable<TY>> DataSeriesBuilder.XyyDataSeriesBuilder<TX, TY> newXyyDataSeries(Class<TX> xType, Class<TY> yType) { return new DataSeriesBuilder.XyyDataSeriesBuilder<>(xType, yType); }



    public ModifierGroupBuilder newModifierGroup() { return new ModifierGroupBuilder(context); }

    public ModifierGroupBuilder newModifierGroupWithDefaultModifiers() {
        return newModifierGroup()
                .withPinchZoomModifier().build()
                .withZoomPanModifier().withReceiveHandledEvents(true).build()
                .withZoomExtentsModifier().build();
    }

    public AnnotationBuilder.TextAnnotationBuilder newTextAnnotation() { return new AnnotationBuilder.TextAnnotationBuilder(context); }

    public AnnotationBuilder.BoxAnnotationBuilder newBoxAnnotation() { return new AnnotationBuilder.BoxAnnotationBuilder(context); }

    public AnnotationBuilder.LineAnnotationBuilder newLineAnnotation() { return new AnnotationBuilder.LineAnnotationBuilder(context); }

    public AnnotationBuilder.LineArrowAnnotationBuilder newLineArrowAnnotation() { return new AnnotationBuilder.LineArrowAnnotationBuilder(context); }

    public AnnotationBuilder.CustomAnnotationBuilder newCustomAnnotation() { return new AnnotationBuilder.CustomAnnotationBuilder(context); }

    public AnnotationBuilder.AxisMarkerAnnotationBuilder newAxisMarkerAnnotation() { return new AnnotationBuilder.AxisMarkerAnnotationBuilder(context); }

    public AnnotationBuilder.HorizontalLineAnnotationBuilder newHorizontalLineAnnotation() { return new AnnotationBuilder.HorizontalLineAnnotationBuilder(context); }

    public AnnotationBuilder.VerticalLineAnnotationBuilder newVerticalLineAnnotation() { return new AnnotationBuilder.VerticalLineAnnotationBuilder(context); }
}
