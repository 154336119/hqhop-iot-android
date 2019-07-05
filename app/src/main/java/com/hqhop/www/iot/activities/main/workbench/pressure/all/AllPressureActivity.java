package com.hqhop.www.iot.activities.main.workbench.pressure.all;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Spinner;

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
import com.hqhop.www.iot.App;
import com.hqhop.www.iot.R;
import com.hqhop.www.iot.api.workbench.pressure.PressureService;
import com.hqhop.www.iot.base.adapter.AllPressureGridViewAdapter;
import com.hqhop.www.iot.base.http.RetrofitManager;
import com.hqhop.www.iot.base.util.CommonUtils;
import com.hqhop.www.iot.base.view.BaseAppCompatActivity;
import com.hqhop.www.iot.bean.AllPressureBean;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.BallPulseFooter;
import com.scwang.smartrefresh.layout.header.BezierRadarHeader;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class AllPressureActivity extends BaseAppCompatActivity {

    private Dialog dialog;

    private SmartRefreshLayout refreshLayout;

    private Spinner spinnerSortType;

    private BarChart mChart;

    private GridView pressureGridView;

    private AllPressureGridViewAdapter pressureAdapter;

    private List<String> pressureStations;

    private List<String> pressureValues;

    private List<String> pressureUnits;

    private PressureService pressureService;

    private AllPressureBean allPressureBean;

    private boolean finishedFetchingData = true;

    /**
     * 排序规则:
     * 默认
     * 时间由近到远
     * 时间由远到近
     * 压力由小到大
     * 压力由大到小
     */
    private int sortType = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    private void init() {
        setToolBarTitle("全部出口压力");

        dialog = CommonUtils.createLoadingDialog(this);

        pressureService = RetrofitManager.getInstance(this).createService(PressureService.class);

        refreshLayout = (SmartRefreshLayout) findViewById(R.id.refresh_layout_all_pressure);
        refreshLayout.setRefreshHeader(new BezierRadarHeader(this).setPrimaryColor(getResources().getColor(R.color.colorPrimary)));
        refreshLayout.setRefreshFooter(new BallPulseFooter(this));
        // 刷新监听
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                fetchData();
            }
        });

        spinnerSortType = (Spinner) findViewById(R.id.spinner_type_all_pressure);
        spinnerSortType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                sortType = position;
                fetchData();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        pressureGridView = (GridView) findViewById(R.id.gridview_pressure_all_pressure);
        pressureStations = new ArrayList<>();
        pressureValues = new ArrayList<>();
        pressureUnits = new ArrayList<>();
        pressureAdapter = new AllPressureGridViewAdapter(this, pressureStations, pressureValues, pressureUnits);
        pressureGridView.setAdapter(pressureAdapter);

        fetchData();

        mChart = (BarChart) findViewById(R.id.chart_all_pressure);
        // Chart设置
//        mChart.setDrawBarShadow(false);
//        mChart.setDrawValueAboveBar(true);
//        mChart.getDescription().setEnabled(false);
//        mChart.setMaxVisibleValueCount(60);
//        mChart.setPinchZoom(true);
//        mChart.setDragEnabled(true);
//        mChart.setScaleEnabled(true);
//        mChart.setDrawGridBackground(false);
//        mChart.getAxisRight().setEnabled(false);
//        XAxis xAxis = mChart.getXAxis();
//        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
//        xAxis.setDrawGridLines(false);
//        xAxis.setGranularity(1f);
//        xAxis.setLabelCount(pressureStations.size());
//        xAxis.setValueFormatter(new IAxisValueFormatter() {
//            @Override
//            public String getFormattedValue(float value, AxisBase axis) {
//                return pressureStations.get(Math.round(value));
//            }
//        });
//        YAxis leftAxis = mChart.getAxisLeft();
//        leftAxis.setValueFormatter(new IAxisValueFormatter() {
//            @Override
//            public String getFormattedValue(float value, AxisBase axis) {
//                return new DecimalFormat("###,###,###,###").format(value) + "KPa";
//            }
//        });
//        leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
//        leftAxis.setSpaceTop(15f);
//        leftAxis.setDrawGridLines(false);
//        leftAxis.setAxisMinimum(0f);
//        Legend l = mChart.getLegend();
//        l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
//        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
//        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
//        l.setDrawInside(false);
//        l.setForm(Legend.LegendForm.SQUARE);
//        l.setFormSize(9f);
//        l.setTextSize(11f);
//        l.setXEntrySpace(4f);
//        setChartData(pressureStations.size());
    }

    private void fetchData() {
        CommonUtils.showProgressDialogWithMessage(this, dialog, null);
        finishedFetchingData = false;
        pressureService.getAllPressureMessage(App.userid, "pressure", 0, 10, "all", sortType)
                .subscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<AllPressureBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        CommonUtils.showToast("网络错误");
                        finishedFetchingData = true;
                        finishRefresh();
                    }

                    @Override
                    public void onNext(AllPressureBean bean) {
                        finishedFetchingData = true;
                        finishRefresh();
                        pressureStations.clear();
                        pressureValues.clear();
                        pressureUnits.clear();
                        allPressureBean = bean;
                        if (bean.isSuccess() && !bean.getData().isEmpty()) {
                            setPressureData();
                        }
                    }
                });
    }

    /**
     * 关闭刷新动画
     */
    private void finishRefresh() {
        if (finishedFetchingData) {
            if (refreshLayout.isRefreshing()) {
                refreshLayout.finishRefresh(true);
            }
            CommonUtils.hideProgressDialog(dialog);
        }
    }

    private void setPressureData() {
        for (int i = 0; i < allPressureBean.getData().size(); i++) {
            pressureStations.add(allPressureBean.getData().get(i).getStationName());
            pressureValues.add(allPressureBean.getData().get(i).getValue());
            pressureUnits.add(allPressureBean.getData().get(i).getUnit());
        }

        pressureAdapter.notifyDataSetChanged();

        mChart.setDrawBarShadow(false);
        mChart.setDrawValueAboveBar(true);
        mChart.getDescription().setEnabled(false);
        mChart.setMaxVisibleValueCount(60);
        mChart.setPinchZoom(true);
        mChart.setDragEnabled(true);
        mChart.setScaleEnabled(true);
        mChart.setDrawGridBackground(false);
        mChart.getAxisRight().setEnabled(false);
        XAxis xAxis = mChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setGranularity(1f);
        xAxis.setLabelCount(pressureStations.size());
        xAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return pressureStations.get(Math.round(value));
            }
        });
        YAxis leftAxis = mChart.getAxisLeft();
        leftAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
//                return new DecimalFormat("###,###,###,###").format(value) + "KPa";
                return new DecimalFormat("###,###,###,###").format(value) + allPressureBean.getData().get(0).getUnit();
            }
        });
        leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        leftAxis.setSpaceTop(15f);
        leftAxis.setDrawGridLines(false);
        leftAxis.setAxisMinimum(0f);
        Legend l = mChart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setDrawInside(false);
        l.setForm(Legend.LegendForm.SQUARE);
        l.setFormSize(9f);
        l.setTextSize(11f);
        l.setXEntrySpace(4f);
        setChartData(pressureStations.size());
    }

    /**
     * 设置图表
     */
    private void setChartData(int count) {
        ArrayList<BarEntry> yValues = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            yValues.add(new BarEntry(i, Float.parseFloat(pressureValues.get(i))));
        }
        BarDataSet set;
        if (mChart.getData() != null &&
                mChart.getData().getDataSetCount() > 0) {
            set = (BarDataSet) mChart.getData().getDataSetByIndex(0);
            set.setValues(yValues);
            mChart.getData().notifyDataChanged();
            mChart.notifyDataSetChanged();
        } else {
            set = new BarDataSet(yValues, "出口压力信息");
            set.setDrawIcons(false);
            set.setColors(ColorTemplate.MATERIAL_COLORS);
            ArrayList<IBarDataSet> dataSets = new ArrayList<>();
            dataSets.add(set);
            BarData data = new BarData(dataSets);
            data.setValueTextSize(10f);
            data.setBarWidth(0.9f);
            mChart.setData(data);
        }
        mChart.requestFocus();
        mChart.notifyDataSetChanged();
    }

    @Override
    protected boolean isShowBacking() {
        return true;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_all_pressure;
    }
}
