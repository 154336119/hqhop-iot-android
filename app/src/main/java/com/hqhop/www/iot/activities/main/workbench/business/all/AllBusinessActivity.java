package com.hqhop.www.iot.activities.main.workbench.business.all;

import android.os.Bundle;
import android.support.v4.widget.NestedScrollView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.hqhop.www.iot.R;
import com.hqhop.www.iot.base.view.BaseAppCompatActivity;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class AllBusinessActivity extends BaseAppCompatActivity {

    private NestedScrollView scrollView;

    private BarChart chartCurrent, chartToday, chartYesterday, chartWeek, chartMonth, chartForecast;

    private List<String> currentValues, todayValues, yesterValues, weekValues, monthValues, forecastValues, stations;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    private void init() {
        setToolBarTitle("经营概览");

        scrollView = (NestedScrollView) findViewById(R.id.scrollview_all_business);

        currentValues = new ArrayList<>();
        todayValues = new ArrayList<>();
        yesterValues = new ArrayList<>();
        weekValues = new ArrayList<>();
        monthValues = new ArrayList<>();
        forecastValues = new ArrayList<>();
        stations = new ArrayList<>();

        getData();

        chartCurrent = (BarChart) findViewById(R.id.chart_current_all_business);
        chartToday = (BarChart) findViewById(R.id.chart_today_all_business);
        chartYesterday = (BarChart) findViewById(R.id.chart_yesterday_all_business);
        chartWeek = (BarChart) findViewById(R.id.chart_week_all_business);
        chartMonth = (BarChart) findViewById(R.id.chart_month_all_business);
        chartForecast = (BarChart) findViewById(R.id.chart_forecast_all_business);

        setChart(chartCurrent);
        setChart(chartToday);
        setChart(chartYesterday);
        setChart(chartWeek);
        setChart(chartMonth);
        setChart(chartForecast);
        setCurrentChartData(stations.size());
        setTodayChartData(stations.size());
        setYesterdayChartData(stations.size());
        setWeekChartData(stations.size());
        setMonthChartData(stations.size());
        setForecastChartData(stations.size());
    }

    private void setChart(BarChart chart) {
        chart.setDrawBarShadow(false);
        chart.setDrawValueAboveBar(true);
        chart.getDescription().setEnabled(false);
        chart.setMaxVisibleValueCount(60);
        chart.setPinchZoom(true);
        chart.setDragEnabled(true);
        chart.setScaleEnabled(true);
        chart.setDrawGridBackground(false);
        chart.getAxisRight().setEnabled(false);
        XAxis xAxis = chart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setGranularity(1f);
        xAxis.setLabelCount(stations.size());
        xAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return stations.get(Math.round(value));
            }
        });
        YAxis leftAxis = chart.getAxisLeft();
        leftAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return new DecimalFormat("###,###,###,###").format(value) + "kg";
            }
        });
        leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        leftAxis.setSpaceTop(15f);
        leftAxis.setDrawGridLines(false);
        leftAxis.setAxisMinimum(0f);
        Legend l = chart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setDrawInside(false);
        l.setForm(Legend.LegendForm.SQUARE);
        l.setFormSize(9f);
        l.setTextSize(11f);
        l.setXEntrySpace(4f);
    }

    private void getData() {
        stations.add("成都市龙泉站");
        stations.add("成都市龙泉站");
        stations.add("成都市龙泉站");
        stations.add("成都市龙泉站");
        currentValues.add("3346.3242");
        currentValues.add("3541.1245");
        currentValues.add("3321.2346");
        currentValues.add("3658.1245");

        todayValues.add("3346.3242");
        todayValues.add("3541.1245");
        todayValues.add("3321.2346");
        todayValues.add("3658.1245");

        yesterValues.add("3346.3242");
        yesterValues.add("3541.1245");
        yesterValues.add("3321.2346");
        yesterValues.add("3658.1245");

        weekValues.add("3346.3242");
        weekValues.add("3541.1245");
        weekValues.add("3321.2346");
        weekValues.add("3658.1245");

        monthValues.add("3346.3242");
        monthValues.add("3541.1245");
        monthValues.add("3321.2346");
        monthValues.add("3658.1245");

        forecastValues.add("3346.3242");
        forecastValues.add("3541.1245");
        forecastValues.add("3321.2346");
        forecastValues.add("3658.1245");
    }

    private void setCurrentChartData(int count) {
        ArrayList<BarEntry> yValues = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            yValues.add(new BarEntry(i, Float.parseFloat(currentValues.get(i))));
        }
        BarDataSet set;
        if (chartCurrent.getData() != null &&
                chartCurrent.getData().getDataSetCount() > 0) {
            set = (BarDataSet) chartCurrent.getData().getDataSetByIndex(0);
            set.setValues(yValues);
            chartCurrent.getData().notifyDataChanged();
            chartCurrent.notifyDataSetChanged();
        } else {
            set = new BarDataSet(yValues, "当前库存量");
            set.setDrawIcons(false);
            set.setColors(ColorTemplate.MATERIAL_COLORS);
            ArrayList<IBarDataSet> dataSets = new ArrayList<>();
            dataSets.add(set);
            BarData data = new BarData(dataSets);
            data.setValueTextSize(10f);
            data.setBarWidth(0.9f);
            chartCurrent.setData(data);
        }
    }

    private void setTodayChartData(int count) {
        ArrayList<BarEntry> yValues = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            yValues.add(new BarEntry(i, Float.parseFloat(todayValues.get(i))));
        }
        BarDataSet set;
        if (chartToday.getData() != null &&
                chartToday.getData().getDataSetCount() > 0) {
            set = (BarDataSet) chartToday.getData().getDataSetByIndex(0);
            set.setValues(yValues);
            chartToday.getData().notifyDataChanged();
            chartToday.notifyDataSetChanged();
        } else {
            set = new BarDataSet(yValues, "今日总销量");
            set.setDrawIcons(false);
            set.setColors(ColorTemplate.MATERIAL_COLORS);
            ArrayList<IBarDataSet> dataSets = new ArrayList<>();
            dataSets.add(set);
            BarData data = new BarData(dataSets);
            data.setValueTextSize(10f);
            data.setBarWidth(0.9f);
            chartToday.setData(data);
        }
    }

    private void setYesterdayChartData(int count) {
        ArrayList<BarEntry> yValues = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            yValues.add(new BarEntry(i, Float.parseFloat(yesterValues.get(i))));
        }
        BarDataSet set;
        if (chartYesterday.getData() != null &&
                chartYesterday.getData().getDataSetCount() > 0) {
            set = (BarDataSet) chartYesterday.getData().getDataSetByIndex(0);
            set.setValues(yValues);
            chartYesterday.getData().notifyDataChanged();
            chartYesterday.notifyDataSetChanged();
        } else {
            set = new BarDataSet(yValues, "昨日总销量");
            set.setDrawIcons(false);
            set.setColors(ColorTemplate.MATERIAL_COLORS);
            ArrayList<IBarDataSet> dataSets = new ArrayList<>();
            dataSets.add(set);
            BarData data = new BarData(dataSets);
            data.setValueTextSize(10f);
            data.setBarWidth(0.9f);
            chartYesterday.setData(data);
        }
    }

    private void setWeekChartData(int count) {
        ArrayList<BarEntry> yValues = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            yValues.add(new BarEntry(i, Float.parseFloat(weekValues.get(i))));
        }
        BarDataSet set;
        if (chartWeek.getData() != null &&
                chartWeek.getData().getDataSetCount() > 0) {
            set = (BarDataSet) chartWeek.getData().getDataSetByIndex(0);
            set.setValues(yValues);
            chartWeek.getData().notifyDataChanged();
            chartWeek.notifyDataSetChanged();
        } else {
            set = new BarDataSet(yValues, "周销量");
            set.setDrawIcons(false);
            set.setColors(ColorTemplate.MATERIAL_COLORS);
            ArrayList<IBarDataSet> dataSets = new ArrayList<>();
            dataSets.add(set);
            BarData data = new BarData(dataSets);
            data.setValueTextSize(10f);
            data.setBarWidth(0.9f);
            chartWeek.setData(data);
        }
    }

    private void setMonthChartData(int count) {
        ArrayList<BarEntry> yValues = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            yValues.add(new BarEntry(i, Float.parseFloat(monthValues.get(i))));
        }
        BarDataSet set;
        if (chartMonth.getData() != null &&
                chartMonth.getData().getDataSetCount() > 0) {
            set = (BarDataSet) chartMonth.getData().getDataSetByIndex(0);
            set.setValues(yValues);
            chartMonth.getData().notifyDataChanged();
            chartMonth.notifyDataSetChanged();
        } else {
            set = new BarDataSet(yValues, "月销量");
            set.setDrawIcons(false);
            set.setColors(ColorTemplate.MATERIAL_COLORS);
            ArrayList<IBarDataSet> dataSets = new ArrayList<>();
            dataSets.add(set);
            BarData data = new BarData(dataSets);
            data.setValueTextSize(10f);
            data.setBarWidth(0.9f);
            chartMonth.setData(data);
        }
    }

    private void setForecastChartData(int count) {
        ArrayList<BarEntry> yValues = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            yValues.add(new BarEntry(i, Float.parseFloat(forecastValues.get(i))));
        }
        BarDataSet set;
        if (chartForecast.getData() != null &&
                chartForecast.getData().getDataSetCount() > 0) {
            set = (BarDataSet) chartForecast.getData().getDataSetByIndex(0);
            set.setValues(yValues);
            chartForecast.getData().notifyDataChanged();
            chartForecast.notifyDataSetChanged();
        } else {
            set = new BarDataSet(yValues, "今日预测销量");
            set.setDrawIcons(false);
            set.setColors(ColorTemplate.MATERIAL_COLORS);
            ArrayList<IBarDataSet> dataSets = new ArrayList<>();
            dataSets.add(set);
            BarData data = new BarData(dataSets);
            data.setValueTextSize(10f);
            data.setBarWidth(0.9f);
            chartForecast.setData(data);
        }
    }

    @Override
    protected boolean isShowBacking() {
        return true;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_all_business;
    }
}
