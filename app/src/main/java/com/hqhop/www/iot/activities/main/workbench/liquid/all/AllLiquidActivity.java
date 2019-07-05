package com.hqhop.www.iot.activities.main.workbench.liquid.all;

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
import com.hqhop.www.iot.api.workbench.liquid.LiquidService;
import com.hqhop.www.iot.base.adapter.AllLiquidGridViewAdapter;
import com.hqhop.www.iot.base.http.RetrofitManager;
import com.hqhop.www.iot.base.util.CommonUtils;
import com.hqhop.www.iot.base.view.BaseAppCompatActivity;
import com.hqhop.www.iot.bean.AllLiquidBean;
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

public class AllLiquidActivity extends BaseAppCompatActivity {

    private Dialog dialog;

    private SmartRefreshLayout refreshLayout;

    private Spinner spinnerSortType;

    private BarChart mChart;

    private GridView liquidLevelGridView;

    private AllLiquidGridViewAdapter liquidAdapter;

    private List<String> liquidStations;

    private List<String> liquidValues;

    private List<String> liquidUnits;

    private LiquidService liquidService;

    private AllLiquidBean allLiquidBean;

    private boolean finishedFetchingData = true;

    /**
     * 排序规则:
     * 默认
     * 时间由近到远
     * 时间由远到近
     * 液位由小到大
     * 液位由大到小
     */
    private int sortType = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    private void init() {
        setToolBarTitle("全部液位信息");

        dialog = CommonUtils.createLoadingDialog(this);

        liquidService = RetrofitManager.getInstance(this).createService(LiquidService.class);

        refreshLayout = (SmartRefreshLayout) findViewById(R.id.refresh_layout_all_liquid);
        refreshLayout.setRefreshHeader(new BezierRadarHeader(this).setPrimaryColor(getResources().getColor(R.color.colorPrimary)));
        refreshLayout.setRefreshFooter(new BallPulseFooter(this));
        // 刷新监听
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                fetchData();
            }
        });

        spinnerSortType = (Spinner) findViewById(R.id.spinner_type_all_liquid);
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

        liquidLevelGridView = (GridView) findViewById(R.id.gridview_liquid_all_liquid);
        liquidStations = new ArrayList<>();
        liquidValues = new ArrayList<>();
        liquidUnits = new ArrayList<>();
        liquidAdapter = new AllLiquidGridViewAdapter(this, liquidStations, liquidValues, liquidUnits);
        liquidLevelGridView.setAdapter(liquidAdapter);

        fetchData();

        mChart = (BarChart) findViewById(R.id.chart_all_liquid);
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
//        mChart.setNoDataText("没有数据");
//        xAxis = mChart.getXAxis();
//        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
//        xAxis.setDrawGridLines(false);
//        xAxis.setGranularity(1f);
//        xAxis.setLabelCount(liquidStations.size());
//        xAxis.setValueFormatter(new IAxisValueFormatter() {
//            @Override
//            public String getFormattedValue(float value, AxisBase axis) {
//                return liquidStations.get(Math.round(value));
//            }
//        });
//        leftAxis = mChart.getAxisLeft();
//        leftAxis.setValueFormatter(new IAxisValueFormatter() {
//            @Override
//            public String getFormattedValue(float value, AxisBase axis) {
//                return new DecimalFormat("###,###,###,###").format(value) + "mm";
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
//        setChartData(liquidStations.size());
    }

    /**
     * 获取、更新数据
     */
    private void fetchData() {
        CommonUtils.showProgressDialogWithMessage(this, dialog, null);
        finishedFetchingData = false;
        liquidService.getAllLiquidMessage(App.userid, "liquid_level", 0, 10, "all", sortType)
                .subscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<AllLiquidBean>() {
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
                    public void onNext(AllLiquidBean bean) {
                        finishedFetchingData = true;
                        finishRefresh();
                        liquidStations.clear();
                        liquidValues.clear();
                        liquidUnits.clear();
                        allLiquidBean = bean;
                        if (bean.isSuccess() && !bean.getData().isEmpty()) {
                            setLiquidData();
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

    private void setLiquidData() {
        for (int i = 0; i < allLiquidBean.getData().size(); i++) {
            liquidStations.add(allLiquidBean.getData().get(i).getStationName());
            liquidValues.add(allLiquidBean.getData().get(i).getValue());
            liquidUnits.add(allLiquidBean.getData().get(i).getUnit());
        }
//        for (int i = 0; i < 10; i++) {
//            liquidStations.add("asgdjhfas");
//            liquidValues.add("1231412");
//        }
        liquidAdapter.notifyDataSetChanged();

//        xAxis.setLabelCount(liquidStations.size());
//        leftAxis.setValueFormatter(new IAxisValueFormatter() {
//            @Override
//            public String getFormattedValue(float value, AxisBase axis) {
////                return new DecimalFormat("###,###,###,###").format(value) + "mm";
//                return new DecimalFormat("###,###,###,###").format(value) + allLiquidBean.getData().get(0).getUnit();
//            }
//        });

        mChart.setDrawBarShadow(false);
        mChart.setDrawValueAboveBar(true);
        mChart.getDescription().setEnabled(false);
        mChart.setMaxVisibleValueCount(60);
        mChart.setPinchZoom(true);
        mChart.setDragEnabled(true);
        mChart.setScaleEnabled(true);
        mChart.setDrawGridBackground(false);
        mChart.getAxisRight().setEnabled(false);
        mChart.setNoDataText("没有数据");
        XAxis xAxis = mChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setGranularity(1f);
        xAxis.setLabelCount(liquidStations.size());
        xAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return liquidStations.get(Math.round(value));
            }
        });
        YAxis leftAxis = mChart.getAxisLeft();
        leftAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
//                return new DecimalFormat("###,###,###,###").format(value) + "mm";
                return new DecimalFormat("###,###,###,###").format(value) + allLiquidBean.getData().get(0).getUnit();
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
        setChartData(liquidStations.size());
//        liquidStations.add("成都市十陵站");
//        liquidStations.add("成都市龙泉站");
//        liquidStations.add("成都市大面站");
//        liquidStations.add("成都市不晓得站");
//        liquidStations.add("成都市不晓得站");
//        liquidValues.add("95.1665");
//        liquidValues.add("98.0465");
//        liquidValues.add("107.6665");
//        liquidValues.add("117.4355");
//        liquidValues.add("117.4355");
    }

    /**
     * 设置图表
     */
    private void setChartData(int count) {
        ArrayList<BarEntry> yValues = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            yValues.add(new BarEntry(i, Float.parseFloat(liquidValues.get(i))));
        }
        BarDataSet set;
        if (mChart.getData() != null &&
                mChart.getData().getDataSetCount() > 0) {
            set = (BarDataSet) mChart.getData().getDataSetByIndex(0);
            set.setValues(yValues);
            mChart.getData().notifyDataChanged();
            mChart.notifyDataSetChanged();
        } else {
            set = new BarDataSet(yValues, "液位信息");
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
        return R.layout.activity_all_liquid;
    }
}
