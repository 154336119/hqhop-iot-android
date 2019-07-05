//package com.hqhop.www.iot.activities.main.follow.station;
//
//import android.annotation.SuppressLint;
//import android.app.Dialog;
//import android.content.Intent;
//import android.os.Bundle;
//import android.support.design.widget.TabLayout;
//import android.text.TextUtils;
//import android.util.Log;
//import android.view.View;
//import android.widget.ImageView;
//import android.widget.RadioButton;
//import android.widget.RadioGroup;
//import android.widget.RelativeLayout;
//import android.widget.TextView;
//
//import com.blankj.utilcode.util.ConvertUtils;
//import com.github.mikephil.charting.animation.Easing;
//import com.github.mikephil.charting.charts.LineChart;
//import com.github.mikephil.charting.components.AxisBase;
//import com.github.mikephil.charting.components.Legend;
//import com.github.mikephil.charting.components.XAxis;
//import com.github.mikephil.charting.components.YAxis;
//import com.github.mikephil.charting.data.Entry;
//import com.github.mikephil.charting.data.LineData;
//import com.github.mikephil.charting.data.LineDataSet;
//import com.github.mikephil.charting.formatter.IAxisValueFormatter;
//import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
//import com.hqhop.www.iot.R;
//import com.hqhop.www.iot.activities.main.workbench.station.detail.DetailStationActivity;
//import com.hqhop.www.iot.api.workbench.detail.StationDetailService;
//import com.hqhop.www.iot.base.http.RetrofitManager;
//import com.hqhop.www.iot.base.util.CommonUtils;
//import com.hqhop.www.iot.base.view.BaseAppCompatActivity;
//import com.hqhop.www.iot.bean.EquipmentRealData;
//import com.hqhop.www.iot.bean.ParameterDetailBean;
//import com.hqhop.www.iot.bean.ReportBean;
//
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import java.text.DecimalFormat;
//import java.util.ArrayList;
//import java.util.Iterator;
//import java.util.List;
//
//import info.hoang8f.android.segmented.SegmentedGroup;
//import rx.Subscriber;
//import rx.android.schedulers.AndroidSchedulers;
//import rx.schedulers.Schedulers;
//
//public class DetailItemActivity extends BaseAppCompatActivity implements RadioGroup.OnCheckedChangeListener {
//
//    private Intent gotIntent;
//
//    private String station = "";
//
//    private String title = "";
//
//    private ArrayList<String> parameterNames = new ArrayList<>();
//
//    private ArrayList<String> parameterIds = new ArrayList<>();
//
//    private ArrayList<String> upperLimits = new ArrayList<>();
//
//
//    private ArrayList<String> lowerLimits = new ArrayList<>();
//
//    private ArrayList<String> units = new ArrayList<>();
//
//    private String parameterName, parameterId;
//
//    private String unit;
//
//    private String stationId;
//
//    private String currentParameterId;
//
//    private String equipmentId;
//
//    private TextView tvName, tvValue, tvUnit, tvManufactor, tvBoughtDate, tvBoughtPrice, tvManufacturedDate, tvAssetCode, tvMaintainDate;
//
//    private ImageView ivStatus;
//
//    private TabLayout tabLayout;
//
//    private SegmentedGroup segmentedGroup;
//
//    private RadioButton radioToday, radioWeek, radioMonth;
//
//    private TextView tvChartUnit;
//
//    private LineChart chart;
//
//    private Dialog dialog;
//
//    private StationDetailService stationDetailService;
//
//
//    /**
//     * 报表x轴的数据
//     */
//    private List<String> chartDataDayKeys, chartDataWeekKeys, chartDataMonthKeys;
//
//    /**
//     * 报表有数据和无数据的索引
//     */
//    private List<Integer> hasValueIndexList, hasNoValueIndexList;
//
//    /**
//     * 图标线段的颜色
//     */
//    private List<Integer> chartColors;
//
//    private float currentValue = -10000F;
//
//    /**
//     * 报表y轴的数据
//     */
//    private List<Float> chartDataDayValues, chartDataWeekValues, chartDataMonthValues;
//
//    private EquipmentRealData realDataBean;
//
//    private ParameterDetailBean parameterDetailBean;
//
//    private ReportBean reportBean;
//
//    private boolean finishedFetchingRealData = true, finishedFetchingParameterDetail = true, finishedFetchingReport = true, canClickTabLayout = false;
//
//    private String reportType = "day";
//
//    private int index = -1;
//
//    /**
//     * 当前报表的标题
//     */
//    private String currentTableTitle;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        init();
//    }
//
//    private void init() {
//        gotIntent = getIntent();
//
//        chartDataDayKeys = new ArrayList<>();
//        chartDataWeekKeys = new ArrayList<>();
//        chartDataMonthKeys = new ArrayList<>();
//        chartDataDayValues = new ArrayList<>();
//        chartDataWeekValues = new ArrayList<>();
//        chartDataMonthValues = new ArrayList<>();
//        hasValueIndexList = new ArrayList<>();
//        hasNoValueIndexList = new ArrayList<>();
//        chartColors = new ArrayList<>();
//
//        dialog = CommonUtils.createLoadingDialog(this);
//        stationDetailService = RetrofitManager.getInstance(this).createService(StationDetailService.class);
//
//        tvName = findViewById(R.id.tv_name_detail_item);
//        tvValue = findViewById(R.id.tv_value_detail_item);
//        ivStatus = findViewById(R.id.iv_status_detail_item);
//        tvUnit = findViewById(R.id.tv_unit_detail_item);
//        tvManufactor = findViewById(R.id.tv_manufactor_detail_item);
//        tvBoughtDate = findViewById(R.id.tv_bought_date_detail_item);
//        tvBoughtPrice = findViewById(R.id.tv_bought_price_detail_item);
//        tvManufacturedDate = findViewById(R.id.tv_manufactured_date_detail_item);
//        tvAssetCode = findViewById(R.id.tv_asset_code_detail_item);
//        tvMaintainDate = findViewById(R.id.tv_maintain_date_detail_item);
//
//        tabLayout = findViewById(R.id.title_detail_item_follow);
////        tvChartUnit = (TextView) findViewById(R.id.tv_table_unit_detail_item);
//        chart = findViewById(R.id.chart_detail_item_follow);
//
//        segmentedGroup = findViewById(R.id.segmented_group_detail_item);
//        radioToday = findViewById(R.id.radio_today_detail_item);
//        radioWeek = findViewById(R.id.radio_week_detail_item);
//        radioMonth = findViewById(R.id.radio_month_detail_item);
//
//        String type = gotIntent.getStringExtra("type");
//        if (!TextUtils.isEmpty(type) && type.equals("follow")) {
//            // 从关注模块跳转过来
//            station = gotIntent.getStringExtra("station");
//            title = gotIntent.getStringExtra("title");
//            parameterName = gotIntent.getStringExtra("parameterName");
//            parameterId = gotIntent.getStringExtra("parameterId");
//            equipmentId = gotIntent.getStringExtra("equipmentId");
//            unit = gotIntent.getStringExtra("unit");
//            stationId = String.valueOf(gotIntent.getIntExtra("stationId", 0));
//            currentTableTitle = parameterName;
//            currentParameterId = parameterId;
//
////            setToolBarTitle(station + "\n" + title);
//            setToolBarTitle(station);
//            tvName.setText(title);
//            if (TextUtils.isEmpty(unit)) {
//                tvUnit.setVisibility(View.GONE);
//            } else {
//                tvUnit.setVisibility(View.VISIBLE);
//                tvUnit.setText(unit);
////                tvChartUnit.setText(unit);
//            }
//            tabLayout.addTab(tabLayout.newTab().setText(parameterName));
//        } else if (!TextUtils.isEmpty(type) && type.equals("monitor")) {
//            // 从设备实时监控页面跳转过来
//            index = gotIntent.getIntExtra("index", 0);
//            station = gotIntent.getStringExtra("station");
//            title = gotIntent.getStringExtra("title");
//
//            ArrayList<String> tempParameterNames = gotIntent.getStringArrayListExtra("parameterNames");
//            ArrayList<String> tempParameterIds = gotIntent.getStringArrayListExtra("parameterIds");
//            ArrayList<String> tempParameterTypes = gotIntent.getStringArrayListExtra("parameterTypes");
//            upperLimits = gotIntent.getStringArrayListExtra("upperLimits");
//            lowerLimits = gotIntent.getStringArrayListExtra("lowerLimits");
//            ArrayList<String> tempUnits = gotIntent.getStringArrayListExtra("units");
//            for (int i = 0; i < tempParameterIds.size(); i++) {
//                if (!TextUtils.isEmpty(tempParameterTypes.get(i)) && !tempParameterTypes.get(i).equals("switcher")) {
//                    parameterIds.add(tempParameterIds.get(i));
//                    parameterNames.add(tempParameterNames.get(i));
//                    units.add(tempUnits.get(i));
//                }
//            }
//
////            parameterNames = gotIntent.getStringArrayListExtra("parameterNames");
////            parameterIds = gotIntent.getStringArrayListExtra("parameterIds");
////            parameterTypes = gotIntent.getStringArrayListExtra("parameterTypes");
////            units = gotIntent.getStringArrayListExtra("units");
//            equipmentId = gotIntent.getStringExtra("equipmentId");
//            stationId = DetailStationActivity.stationId;
//
//            currentTableTitle = parameterNames.get(index);
//            currentParameterId = parameterIds.get(index);
//            setToolBarTitle(station + "\n" + title);
////            setToolBarTitle(station);
//            tvName.setText(title);
//            if (TextUtils.isEmpty(units.get(index))) {
//                tvUnit.setVisibility(View.GONE);
//            } else {
//                tvUnit.setVisibility(View.VISIBLE);
//                tvUnit.setText(units.get(index));
////                tvChartUnit.setText(units.get(index));
//            }
//
//            for (int i = 0; i < parameterNames.size(); i++) {
//                tabLayout.addTab(tabLayout.newTab().setText(parameterNames.get(i)));
//            }
//            tabLayout.getTabAt(index).select();
//            tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
//                @Override
//                public void onTabSelected(TabLayout.Tab tab) {
//                    index = tab.getPosition();
//                    if (!currentParameterId.equals(parameterIds.get(tab.getPosition()))) {
//                        currentParameterId = parameterIds.get(tab.getPosition());
//                        currentTableTitle = parameterNames.get(tab.getPosition());
//                        // 切换Tab选项卡后重置日周月选项
//                        reportType = "day";
//                        radioToday.setChecked(true);
//                        fetchData();
//                        if (TextUtils.isEmpty(units.get(tab.getPosition()))) {
//                            tvUnit.setVisibility(View.GONE);
//                        } else {
//                            tvUnit.setVisibility(View.VISIBLE);
//                            tvUnit.setText(units.get(tab.getPosition()));
////                            tvChartUnit.setText(units.get(tab.getPosition()));
//                        }
//                    }
//                }
//
//                @Override
//                public void onTabUnselected(TabLayout.Tab tab) {
//                }
//
//                @Override
//                public void onTabReselected(TabLayout.Tab tab) {
//                }
//            });
//        }
//        segmentedGroup.setOnCheckedChangeListener(this);// 日周月切换点击事件
//
//        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
//
//        fetchData();
//    }
//
//    private void fetchData() {
//        fetchRealData();
//        fetchParameterDetail();
//        fetchReport();
//    }
//
//    private void fetchRealData() {
//        canClickTabLayout = false;
//        finishedFetchingRealData = false;
//        tabLayout.setClickable(false);
//        CommonUtils.showProgressDialogWithMessage(this, dialog, null);
//        stationDetailService.getRealData(currentParameterId)
//                .subscribeOn(Schedulers.io())
//                .subscribeOn(AndroidSchedulers.mainThread())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Subscriber<EquipmentRealData>() {
//                    @Override
//                    public void onCompleted() {
//
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
////                        CommonUtils.showToast("网络错误");
//                        finishedFetchingRealData = true;
//                        canClickTabLayout = true;
//                        finishLoading();
//                    }
//
//                    @Override
//                    public void onNext(EquipmentRealData bean) {
//                        finishedFetchingRealData = true;
//                        canClickTabLayout = true;
//                        finishLoading();
//                        realDataBean = bean;
//                        if (bean.isSuccess()) {
//                            setRealData();
//                        }
//                    }
//                });
//    }
//
//    private void setRealData() {
//        DecimalFormat decimalFormat = new DecimalFormat("0.00");
//        if (upperLimits.isEmpty() || lowerLimits.isEmpty()) {
//            tvValue.setTextColor(getResources().getColor(R.color.green_70));
//            tvUnit.setTextColor(getResources().getColor(R.color.green_70));
//            ivStatus.setVisibility(View.VISIBLE);
//            ivStatus.setBackgroundResource(R.drawable.ripple_blue);
//            tvValue.setText(decimalFormat.format(realDataBean.getData()));//format 返回的是字符串
//        } else {
//            if (!upperLimits.get(index).isEmpty()) {
//                float upper = Float.valueOf(upperLimits.get(index));
//                float lower = Float.valueOf(lowerLimits.get(index));
//                if (realDataBean.getData() >= upper) {
//                    // 报警
//                    tvValue.setTextColor(getResources().getColor(R.color.white));
//                    tvUnit.setTextColor(getResources().getColor(R.color.white));
//                    ivStatus.setVisibility(View.VISIBLE);
//                    ivStatus.setBackgroundResource(R.drawable.ripple_red);
//                    RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) ivStatus.getLayoutParams();
//                    layoutParams.width = ConvertUtils.dp2px(90F);
//                    layoutParams.height = ConvertUtils.dp2px(90F);
//                    ivStatus.setLayoutParams(layoutParams);
//                } else if (realDataBean.getData() < upper || realDataBean.getData() > lower) {
//                    // 正常
//                    tvValue.setTextColor(getResources().getColor(R.color.green_70));
//                    tvUnit.setTextColor(getResources().getColor(R.color.green_70));
//                    ivStatus.setVisibility(View.VISIBLE);
//                    ivStatus.setBackgroundResource(R.drawable.ripple_blue);
//                    float percent = (realDataBean.getData() - lower) / (upper - lower);
//                    RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) ivStatus.getLayoutParams();
//                    layoutParams.width = ConvertUtils.dp2px(90F);
//                    layoutParams.height = ConvertUtils.dp2px(90 * percent);
//                    ivStatus.setLayoutParams(layoutParams);
//                }
//            } else {
//                // 隐藏图片
//                ivStatus.setVisibility(View.GONE);
//            }
//            tvValue.setText(decimalFormat.format(realDataBean.getData()));//format 返回的是字符串
//        }
//    }
//
//    private void fetchParameterDetail() {
//        canClickTabLayout = false;
//        tabLayout.setClickable(false);
//        finishedFetchingParameterDetail = false;
//        CommonUtils.showProgressDialogWithMessage(this, dialog, null);
//        stationDetailService.getParameterDetail(equipmentId)
//                .subscribeOn(Schedulers.io())
//                .subscribeOn(AndroidSchedulers.mainThread())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Subscriber<ParameterDetailBean>() {
//                    @Override
//                    public void onCompleted() {
//
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
////                        CommonUtils.showToast("网络错误");
//                        finishedFetchingParameterDetail = true;
//                        canClickTabLayout = true;
//                        finishLoading();
//                    }
//
//                    @Override
//                    public void onNext(ParameterDetailBean bean) {
//                        finishedFetchingParameterDetail = true;
//                        canClickTabLayout = true;
//                        finishLoading();
//                        parameterDetailBean = bean;
//                        if (bean.isSuccess()) {
//                            setParameterDetail();
//                        }
//                    }
//                });
//    }
//
//    @SuppressLint("SetTextI18n")
//    private void setParameterDetail() {
//        tvManufactor.setText(getString(R.string.manufacture) + parameterDetailBean.getData().getManufacturer());
//        tvBoughtDate.setText(getString(R.string.date_purchase) + CommonUtils.timestampToDate(parameterDetailBean.getData().getPurchaseDate()));
//        tvBoughtPrice.setText(getString(R.string.price) + parameterDetailBean.getData().getPurchasePrice());
//        tvManufacturedDate.setText(getString(R.string.production_date) + CommonUtils.timestampToDate(parameterDetailBean.getData().getDateOfManufacture()));
//        tvAssetCode.setText(getString(R.string.assets_no) + parameterDetailBean.getData().getCode());
//        tvMaintainDate.setText(getString(R.string.next_maintenance_date) + CommonUtils.timestampToDate(parameterDetailBean.getData().getNextMaintenanceDate()));
//    }
//
//    private void fetchReport() {
//        canClickTabLayout = false;
//        tabLayout.setClickable(false);
//        finishedFetchingReport = false;
//        CommonUtils.showProgressDialogWithMessage(this, dialog, null);
////        stationDetailService.getReport(reportType, stationId, CommonUtils.timestampToDate(System.currentTimeMillis()), currentParameterId)
////        stationDetailService.getReport(reportType, stationId, CommonUtils.timestampToDateWithHour(System.currentTimeMillis()), currentParameterId)
//        stationDetailService.getReport(reportType, stationId, String.valueOf(System.currentTimeMillis()), currentParameterId)
//                .subscribeOn(Schedulers.io())
//                .subscribeOn(AndroidSchedulers.mainThread())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Subscriber<ReportBean>() {
//                    @Override
//                    public void onCompleted() {
//
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        chart.clear();
//                        Log.e("Allen", e.toString());
////                        chart.clear();
//                        CommonUtils.showToast(getString(R.string.network_error));
//                        finishedFetchingReport = true;
//                        canClickTabLayout = true;
//                        finishLoading();
//                        segmentedGroup.setClickable(true);
//                    }
//
//                    @Override
//                    public void onNext(ReportBean bean) {
//                        finishedFetchingReport = true;
//                        canClickTabLayout = true;
//                        finishLoading();
//                        if (bean.isSuccess() && bean.getData().size() > 0) {
//                            reportBean = bean;
//                            setReport();
//                        }
//                        segmentedGroup.setClickable(true);
//                    }
//                });
//    }
//
//    private void setReport() {
//        chartDataDayKeys.clear();
//        chartDataWeekKeys.clear();
//        chartDataMonthKeys.clear();
//        chartDataDayValues.clear();
//        chartDataWeekValues.clear();
//        chartDataMonthValues.clear();
//        hasValueIndexList.clear();
//        hasNoValueIndexList.clear();
//        chartColors.clear();
//
//        dealChartData();
////        Log.d("Allen", reportBean.getData().size() + "");
////        for (int i = 0; i < reportBean.getData().size(); i++) {
////            try {
////                JSONObject object = new JSONObject(reportBean.getData().get(i).toString());
////                Iterator<String> iterator = object.keys();
////                while (iterator.hasNext()) {
////                    String key = iterator.next();
////                    if (TextUtils.isEmpty(object.get(key).toString().trim())) {
////                        // 该点无数据
//////                        switch (reportType) {
//////                            case "day":
//////                                chartDataDayKeys.add(key);
////////                                chartDataDayValues.add(-10000F);
//////                                break;
//////                            case "week":
//////                                chartDataWeekKeys.add(key);
////////                                chartDataWeekValues.add(-10000F);
//////                                break;
//////                            case "month":
//////                                chartDataMonthKeys.add(key);
////////                                chartDataMonthValues.add(-10000F);
//////                                break;
//////                        }
////                    } else {
////                        // 该点有数据
////                        Float value = Float.parseFloat(object.get(key).toString());
////                        Log.d("Allen", "key => " + key + ", value => " + value);
////                        switch (reportType) {
////                            case "day":
////                                chartDataDayKeys.add(key);
////                                chartDataDayValues.add(value);
////                                break;
////                            case "week":
////                                chartDataWeekKeys.add(key);
////                                chartDataWeekValues.add(value);
////                                break;
////                            case "month":
////                                chartDataMonthKeys.add(key);
////                                chartDataMonthValues.add(value);
////                                break;
////                        }
////                    }
////
////                }
////            } catch (JSONException e) {
////                e.printStackTrace();
////            }
////        }
////        initTable();
////        chart.setVisibility(View.VISIBLE);
////        Log.d("Allen", "设置数据完成");
//    }
//
//    /**
//     * 处理获取的表数据
//     */
//    private void dealChartData() {
//        for (int i = 0; i < reportBean.getData().size(); i++) {
//            try {
//                JSONObject object = new JSONObject(reportBean.getData().get(i).toString());
//                Iterator<String> iterator = object.keys();
//                while (iterator.hasNext()) {
//                    String key = iterator.next();
//                    if (TextUtils.isEmpty(object.get(key).toString().trim())) {
//                        hasNoValueIndexList.add(i);
//                        chartColors.add(getResources().getColor(R.color.backgroundActivity));
////                        // 该点无数据
//                        switch (reportType) {
//                            case "day":
////                                chartDataDayKeys.add(key);
//                                chartDataDayKeys.add(CommonUtils.timestampToDate(CommonUtils.dateToTimestamp(key), "HH:mm"));
//                                if (currentValue == -10000F) {
//                                    chartDataDayValues.add(0F);
//                                } else {
//                                    chartDataDayValues.add(currentValue);
//                                }
//                                break;
//                            case "week":
////                                chartDataWeekKeys.add(key);
//                                chartDataWeekKeys.add(CommonUtils.timestampToDate(CommonUtils.dateToTimestamp(key), "MM-dd"));
//                                if (currentValue == -10000F) {
//                                    chartDataWeekValues.add(0F);
//                                } else {
//                                    chartDataWeekValues.add(currentValue);
//                                }
//                                break;
//                            case "month":
////                                chartDataMonthKeys.add(key);
//                                chartDataMonthKeys.add(CommonUtils.timestampToDate(CommonUtils.dateToTimestamp(key), "MM-dd"));
//                                if (currentValue == -10000F) {
//                                    chartDataMonthValues.add(0F);
//                                } else {
//                                    chartDataMonthValues.add(currentValue);
//                                }
//                                break;
//                        }
//                    } else {
//                        hasValueIndexList.add(i);
//                        chartColors.add(getResources().getColor(R.color.colorPrimary));
//                        // 该点有数据
//                        Float value = Float.parseFloat(object.get(key).toString());
//                        Log.d("Allen", "key => " + key + ", value => " + value);
//                        switch (reportType) {
//                            case "day":
////                                chartDataDayKeys.add(key);
//                                chartDataDayKeys.add(CommonUtils.timestampToDate(CommonUtils.dateToTimestamp(key), "HH:mm"));
//                                chartDataDayValues.add(value);
//                                break;
//                            case "week":
////                                chartDataWeekKeys.add(key);
//                                chartDataWeekKeys.add(CommonUtils.timestampToDate(CommonUtils.dateToTimestamp(key), "MM-dd"));
//                                chartDataWeekValues.add(value);
//                                break;
//                            case "month":
////                                chartDataMonthKeys.add(key);
//                                chartDataMonthKeys.add(CommonUtils.timestampToDate(CommonUtils.dateToTimestamp(key), "MM-dd"));
//                                chartDataMonthValues.add(value);
//                                break;
//                        }
//                        currentValue = value;
//                    }
//                }
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//        }
//        initTable();
//        chart.setVisibility(View.VISIBLE);
//    }
//
//    private void initTable() {
//        chart.clear();
//        chart.setDrawGridBackground(false);
//        chart.getDescription().setEnabled(false);
//        chart.setDrawBorders(false);
//        chart.setMaxVisibleValueCount(30);
//        chart.setPinchZoom(true);
//        chart.setDragEnabled(true);
//        chart.setScaleEnabled(true);
//        chart.setDrawGridBackground(false);
//        chart.getAxisRight().setEnabled(false);
//        chart.setNoDataText("没有数据");
//        chart.animateXY(1000, 1000, Easing.EasingOption.EaseInOutExpo, Easing.EasingOption.EaseInOutExpo);
//
//        XAxis xAxis = chart.getXAxis();
//        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
//        xAxis.setDrawAxisLine(false);
//        xAxis.setDrawGridLines(false);
//        xAxis.setDrawLabels(true);
//        xAxis.setGranularity(1f);
//        xAxis.setLabelRotationAngle(-25);
//        xAxis.setTextSize(8f);
//        xAxis.setAvoidFirstLastClipping(false);
//        float maxValue = 0f;
//        float minValue = 0f;
//        switch (reportType) {
//            case "day":
//                if (chartDataDayKeys.size() == 24) {
////                    xAxis.setLabelCount(chartDataDayKeys.size() / 6);
//                    xAxis.setLabelCount(5);
//                } else if (chartDataDayKeys.size() > 20) {
//                    xAxis.setLabelCount(chartDataDayKeys.size() / 2);
//                } else {
//                    xAxis.setLabelCount(chartDataDayKeys.size());
//                }
//                maxValue = CommonUtils.getMaxValue(chartDataDayValues);
//                minValue = CommonUtils.getMinValue(chartDataDayValues);
////                if (isMinManual && CommonUtils.getMinValue(chartDataDayValues) < -1000) {
////                }
////                minValue = isMinManual ? 0F : CommonUtils.getMinValue(chartDataDayValues);
//                break;
//            case "week":
//                if (chartDataWeekKeys.size() > 14) {
//                    xAxis.setLabelCount(chartDataWeekKeys.size() / 2);
//                } else {
//                    xAxis.setLabelCount(chartDataWeekKeys.size());
//                }
//                maxValue = CommonUtils.getMaxValue(chartDataWeekValues);
//                minValue = CommonUtils.getMinValue(chartDataWeekValues);
////                minValue = isMinManual ? 0F : CommonUtils.getMinValue(chartDataWeekValues);
//                break;
//            case "month":
//                if (chartDataMonthKeys.size() > 20) {
//                    xAxis.setLabelCount(chartDataMonthKeys.size() / 2);
//                } else {
//                    xAxis.setLabelCount(chartDataMonthKeys.size());
//                }
//                maxValue = CommonUtils.getMaxValue(chartDataMonthValues);
//                minValue = CommonUtils.getMinValue(chartDataMonthValues);
////                minValue = isMinManual ? 0F : CommonUtils.getMinValue(chartDataMonthValues);
//                break;
//        }
////        xAxis.setLabelCount(chartDataKeys.size());
//        xAxis.setValueFormatter(new IAxisValueFormatter() {
//            @Override
//            public String getFormattedValue(float value, AxisBase axis) {
////                return chartDataKeys.get(Math.round(value));
//                Log.d("Allen", "自定义x轴格式: " + value);
//                switch (reportType) {
//                    case "day":
//                        if (Math.round(value) < chartDataDayKeys.size())
//                            return chartDataDayKeys.get(Math.round(value));
//                    case "week":
//                        if (Math.round(value) < chartDataWeekKeys.size())
//                            return chartDataWeekKeys.get(Math.round(value));
//                    case "month":
//                        if (Math.round(value) < chartDataMonthKeys.size())
//                            return chartDataMonthKeys.get(Math.round(value));
//                    default:
//                        return "";
//                }
//            }
//        });
//
//        YAxis leftAxis = chart.getAxisLeft();
//        leftAxis.removeAllLimitLines(); // reset all limit lines to avoid overlapping lines
//        leftAxis.setDrawAxisLine(true);
//        leftAxis.setDrawGridLines(false);
////        leftAxis.addLimitLine(ll1);// 上限
////        leftAxis.addLimitLine(ll2);// 下限
////        float maxValue = CommonUtils.getMaxValue(chartDataValues);
//        leftAxis.setAxisMaximum(maxValue + 1);// 设置报表y轴最大值
////        leftAxis.setAxisMinimum(0f);// 设置报表y轴最小值
//        leftAxis.setAxisMinimum(minValue);// 设置报表y轴最小值
//        leftAxis.enableGridDashedLine(10f, 10f, 0f);
//        leftAxis.setDrawZeroLine(false);
//        leftAxis.setDrawLimitLinesBehindData(true);
//
//        // 点击显示详细信息，暂时隐藏
////        XYMarkerView mv = new XYMarkerView(this, xAxis.getValueFormatter());
////        mv.setChartView(chart); // For bounds control
////        chart.setMarker(mv); // Set the marker to the chart
//
//        Legend l = chart.getLegend();
//        l.setEnabled(false);
//        l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
//        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
//        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
//        l.setForm(Legend.LegendForm.LINE);
//        l.setDrawInside(false);
//        l.setFormSize(9f);
//        l.setTextSize(11f);
//        l.setXEntrySpace(4f);
//
//        chart.getAxisRight().setEnabled(false);
//        switch (reportType) {
//            case "day":
////                setChartData(chartDataDayKeys.size());
//                setChartData(chartDataDayValues.size());
//                break;
//            case "week":
////                setChartData(chartDataWeekKeys.size());
//                setChartData(chartDataWeekValues.size());
//                break;
//            case "month":
////                setChartData(chartDataMonthKeys.size());
//                setChartData(chartDataMonthValues.size());
//                break;
//        }
////        setChartData(chartDataKeys.size());
//    }
//
//    /**
//     * 设置图表
//     */
//    private void setChartData(int count) {
//        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
//        ArrayList<Entry> yValues = new ArrayList<>();
////        int color;
//        for (int i = 0; i < count; i++) {
////            yValues.add(new Entry(i, Float.parseFloat(chartDataValues.get(i))));
//            switch (reportType) {
//                case "day":
//                    yValues.add(new Entry(i, chartDataDayValues.get(i)));
//                    break;
//                case "week":
//                    yValues.add(new Entry(i, chartDataWeekValues.get(i)));
//                    break;
//                case "month":
//                    yValues.add(new Entry(i, chartDataMonthValues.get(i)));
//                    break;
//            }
////            color = chartColors.get(i % chartColors.size());
//        }
//        LineDataSet set = new LineDataSet(yValues, currentTableTitle);
//        setChartStyle(set);
//        dataSets.add(set);
//
//        ((LineDataSet) dataSets.get(0)).setColors(chartColors);
//
//        LineData data = new LineData(dataSets);
//        chart.setData(data);
//        chart.invalidate();
//    }
//
//    /**
//     * 设置图标样式
//     */
//    private void setChartStyle(LineDataSet set) {
////        set.setMode(LineDataSet.Mode.LINEAR);
////        set.setMode(LineDataSet.Mode.LINEAR);
//        set.setDrawValues(false);
//        set.setDrawFilled(false);
//        set.setDrawIcons(false);
//        set.setDrawCircles(false);
//        set.disableDashedHighlightLine();
//        set.disableDashedLine();
//        chart.setHighlightPerTapEnabled(false);
//    }
//
//    @Override
//    protected boolean isShowBacking() {
//        return true;
//    }
//
//    @Override
//    protected int getLayoutId() {
//        return R.layout.activity_detail_item;
//    }
//
//    private void finishLoading() {
//        if (finishedFetchingRealData && finishedFetchingParameterDetail && finishedFetchingReport) {
//            CommonUtils.hideProgressDialog(dialog);
//            tabLayout.setClickable(true);
//        }
//    }
//
//    public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
//        switch (checkedId) {
//            case R.id.radio_today_detail_item:
//                reportType = "day";
//                segmentedGroup.setClickable(false);
//                fetchReport();
//                break;
//            case R.id.radio_week_detail_item:
//                reportType = "week";
//                segmentedGroup.setClickable(false);
//                fetchReport();
//                break;
//            case R.id.radio_month_detail_item:
//                reportType = "month";
//                segmentedGroup.setClickable(false);
//                fetchReport();
//                break;
//            default:
//                break;
//        }
//    }
//}

package com.hqhop.www.iot.activities.main.follow.station;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.hqhop.www.iot.App;
import com.hqhop.www.iot.BuildConfig;
import com.hqhop.www.iot.R;
import com.hqhop.www.iot.activities.main.workbench.station.detail.DetailStationActivity;
import com.hqhop.www.iot.api.workbench.detail.StationDetailService;
import com.hqhop.www.iot.base.Common;
import com.hqhop.www.iot.base.http.RetrofitManager;
import com.hqhop.www.iot.base.util.CommonUtils;
import com.hqhop.www.iot.base.util.WebViewUtils;
import com.hqhop.www.iot.base.view.BaseAppCompatActivity;
import com.hqhop.www.iot.bean.EquipmentRealData;
import com.hqhop.www.iot.bean.ParameterDetailBean;
import com.hqhop.www.iot.bean.TechnologyFlowBean;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.footer.BallPulseFooter;
import com.scwang.smartrefresh.layout.header.BezierRadarHeader;

import java.text.DecimalFormat;
import java.util.ArrayList;

import me.itangqi.waveloadingview.WaveLoadingView;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

//public class DetailItemActivity extends BaseAppCompatActivity implements RadioGroup.OnCheckedChangeListener {
public class DetailItemActivity extends BaseAppCompatActivity {

    private Intent gotIntent;

    private String station = "";

    private String title = "";

    private ArrayList<String> parameterNames = new ArrayList<>();

    private ArrayList<String> parameterIds = new ArrayList<>();

    private ArrayList<String> upperLimits = new ArrayList<>();

    private ArrayList<String> lowerLimits = new ArrayList<>();

    private ArrayList<String> units = new ArrayList<>();

    private String parameterName, parameterId;

    private String unit;

    private String stationId;

    private String currentParameterId;

    private String equipmentId;

    private TextView tvName, tvValue, tvUnit, tvManufactor, tvBoughtDate, tvBoughtPrice, tvManufacturedDate, tvAssetCode, tvMaintainDate;

    //    private ImageView ivStatus;
    private WaveLoadingView waveView;

    private TabLayout tabLayout;

    private SmartRefreshLayout refreshLayout;

    private WebView webView;

//    private SegmentedGroup segmentedGroup;

//    private RadioButton radioToday, radioWeek, radioMonth;

//    private TextView tvChartUnit;

//    private LineChart chart;

    private Dialog dialog;

    private StationDetailService stationDetailService;


    /**
     * 报表x轴的数据
     */
//    private List<String> chartDataDayKeys, chartDataWeekKeys, chartDataMonthKeys;

    /**
     * 报表有数据和无数据的索引
     */
//    private List<Integer> hasValueIndexList, hasNoValueIndexList;

    /**
     * 图标线段的颜色
     */
//    private List<Integer> chartColors;

//    private float currentValue = -10000F;

    /**
     * 报表y轴的数据
     */
//    private List<Float> chartDataDayValues, chartDataWeekValues, chartDataMonthValues;

    private EquipmentRealData realDataBean;

    private ParameterDetailBean parameterDetailBean;

    private TechnologyFlowBean technologyFlowBean;

    private String url = Common.HQHP_SITE_URL;

//    private ReportBean reportBean;

    private boolean finishedFetchingRealData = true, finishedFetchingParameterDetail = true, finishedFetchingReport = true, canClickTabLayout = false;

//    private String reportType = "day";

    private int index = -1;

    private float dpi;

    /**
     * 当前报表的标题
     */
//    private String currentTableTitle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (waveView != null && waveView.isActivated()) {
            waveView.cancelAnimation();
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void init() {
        gotIntent = getIntent();

        DisplayMetrics metrics = getResources().getDisplayMetrics();
        dpi = metrics.density;

//        chartDataDayKeys = new ArrayList<>();
//        chartDataWeekKeys = new ArrayList<>();
//        chartDataMonthKeys = new ArrayList<>();
//        chartDataDayValues = new ArrayList<>();
//        chartDataWeekValues = new ArrayList<>();
//        chartDataMonthValues = new ArrayList<>();
//        hasValueIndexList = new ArrayList<>();
//        hasNoValueIndexList = new ArrayList<>();
//        chartColors = new ArrayList<>();

        refreshLayout = findViewById(R.id.refresh_layout_detail_item);
        refreshLayout.setRefreshHeader(new BezierRadarHeader(this).setPrimaryColor(getResources().getColor(R.color.colorPrimary)));
        refreshLayout.setRefreshFooter(new BallPulseFooter(this));
        refreshLayout.setOnRefreshListener(refreshLayout -> fetchData());
        webView = findViewById(R.id.webview_detail_item);
//        webView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
//        webView.setScrollbarFadingEnabled(true);
//        webView.setHorizontalScrollBarEnabled(false);
//        webView.setVerticalScrollBarEnabled(false);
//        WebSettings webSettings = webView.getSettings();
//        webSettings.setJavaScriptEnabled(true);// 允许js运行
//        webSettings.setSupportZoom(true);// 允许缩放
//        webSettings.setBuiltInZoomControls(false);// 出现缩放按钮
//        webSettings.setUseWideViewPort(true);// 允许扩大比例的缩放
////        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);// 自适应
//        webSettings.setLoadWithOverviewMode(true);
////        webSettings.setAppCacheEnabled(false); // 要使缓存有效，必须通过setAppCachePath()方法指定缓存路径
//        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        WebViewUtils.setWebViewStyles(webView);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                if (refreshLayout.isRefreshing()) {
                    refreshLayout.finishRefresh(true);
                }
            }
        });

        dialog = CommonUtils.createLoadingDialog(this);
        stationDetailService = RetrofitManager.getInstance(this).createService(StationDetailService.class);

        tvName = findViewById(R.id.tv_name_detail_item);
        tvValue = findViewById(R.id.tv_value_detail_item);
//        ivStatus = findViewById(R.id.iv_status_detail_item);
        waveView = findViewById(R.id.wave_detail_item);
        tvUnit = findViewById(R.id.tv_unit_detail_item);
        tvManufactor = findViewById(R.id.tv_manufactor_detail_item);
//        tvBoughtDate = findViewById(R.id.tv_bought_date_detail_item);
//        tvBoughtPrice = findViewById(R.id.tv_bought_price_detail_item);
        tvManufacturedDate = findViewById(R.id.tv_manufactured_date_detail_item);
        tvAssetCode = findViewById(R.id.tv_asset_code_detail_item);
        tvMaintainDate = findViewById(R.id.tv_maintain_date_detail_item);

        tabLayout = findViewById(R.id.title_detail_item_follow);
//        tvChartUnit = (TextView) findViewById(R.id.tv_table_unit_detail_item);
//        chart = findViewById(R.id.chart_detail_item_follow);

//        segmentedGroup = findViewById(R.id.segmented_group_detail_item);
//        radioToday = findViewById(R.id.radio_today_detail_item);
//        radioWeek = findViewById(R.id.radio_week_detail_item);
//        radioMonth = findViewById(R.id.radio_month_detail_item);

        String type = gotIntent.getStringExtra("type");
        if (!TextUtils.isEmpty(type) && type.equals("follow")) {
            // 从关注模块跳转过来
            station = gotIntent.getStringExtra("station");
            title = gotIntent.getStringExtra("title");
            parameterName = gotIntent.getStringExtra("parameterName");
            parameterId = gotIntent.getStringExtra("parameterId");
            equipmentId = gotIntent.getStringExtra("equipmentId");
            unit = gotIntent.getStringExtra("unit");
            stationId = String.valueOf(gotIntent.getIntExtra("stationId", 0));
//            currentTableTitle = parameterName;
            currentParameterId = parameterId;

//            setToolBarTitle(station + "\n" + title);
            setToolBarTitle(station);
            tvName.setText(title);
            if (TextUtils.isEmpty(unit)) {
                tvUnit.setVisibility(View.GONE);
            } else {
                tvUnit.setVisibility(View.VISIBLE);
                tvUnit.setText(unit);
//                tvChartUnit.setText(unit);
            }
            tabLayout.addTab(tabLayout.newTab().setText(parameterName));
        } else if (!TextUtils.isEmpty(type) && type.equals("monitor")) {
            // 从设备实时监控页面跳转过来
            index = gotIntent.getIntExtra("index", 0);
            station = gotIntent.getStringExtra("station");
            title = gotIntent.getStringExtra("title");

            ArrayList<String> tempParameterNames = gotIntent.getStringArrayListExtra("parameterNames");
            ArrayList<String> tempParameterIds = gotIntent.getStringArrayListExtra("parameterIds");
            ArrayList<String> tempParameterTypes = gotIntent.getStringArrayListExtra("parameterTypes");
            upperLimits = gotIntent.getStringArrayListExtra("upperLimits");
            lowerLimits = gotIntent.getStringArrayListExtra("lowerLimits");
            ArrayList<String> tempUnits = gotIntent.getStringArrayListExtra("units");
            for (int i = 0; i < tempParameterIds.size(); i++) {
                if (!TextUtils.isEmpty(tempParameterTypes.get(i)) && !tempParameterTypes.get(i).equals("switcher")) {
                    parameterIds.add(tempParameterIds.get(i));
                    parameterNames.add(tempParameterNames.get(i));
                    units.add(tempUnits.get(i));
                }
            }

//            parameterNames = gotIntent.getStringArrayListExtra("parameterNames");
//            parameterIds = gotIntent.getStringArrayListExtra("parameterIds");
//            parameterTypes = gotIntent.getStringArrayListExtra("parameterTypes");
//            units = gotIntent.getStringArrayListExtra("units");
            equipmentId = gotIntent.getStringExtra("equipmentId");
            stationId = DetailStationActivity.stationId;

//            currentTableTitle = parameterNames.get(index);
            currentParameterId = parameterIds.get(index);
            setToolBarTitle(station + "\n" + title);
//            setToolBarTitle(station);
            tvName.setText(title);
            if (TextUtils.isEmpty(units.get(index))) {
                tvUnit.setVisibility(View.GONE);
            } else {
                tvUnit.setVisibility(View.VISIBLE);
                unit = units.get(index);
                tvUnit.setText(unit);
//                tvChartUnit.setText(units.get(index));
            }

            for (int i = 0; i < parameterNames.size(); i++) {
                tabLayout.addTab(tabLayout.newTab().setText(parameterNames.get(i)));
            }
            tabLayout.getTabAt(index).select();
            tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                @Override
                public void onTabSelected(TabLayout.Tab tab) {
                    waveView.cancelAnimation();
                    index = tab.getPosition();
                    if (!currentParameterId.equals(parameterIds.get(tab.getPosition()))) {
                        currentParameterId = parameterIds.get(tab.getPosition());
//                        currentTableTitle = parameterNames.get(tab.getPosition());
                        // 切换Tab选项卡后重置日周月选项
//                        reportType = "day";
//                        radioToday.setChecked(true);
                        fetchData();
                        if (TextUtils.isEmpty(units.get(tab.getPosition()))) {
                            tvUnit.setVisibility(View.GONE);
                        } else {
                            tvUnit.setVisibility(View.VISIBLE);
                            unit = units.get(tab.getPosition());
                            tvUnit.setText(unit);
//                            tvChartUnit.setText(units.get(tab.getPosition()));
                        }
                    }
                }

                @Override
                public void onTabUnselected(TabLayout.Tab tab) {
                }

                @Override
                public void onTabReselected(TabLayout.Tab tab) {
                }
            });
        }
//        segmentedGroup.setOnCheckedChangeListener(this);// 日周月切换点击事件

        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);

        fetchData();
    }

    private void fetchData() {
        fetchRealData();
        fetchParameterDetail();
//        fetchReport();
        fetchEquipmentChat();
    }

    private void fetchRealData() {
        canClickTabLayout = false;
        finishedFetchingRealData = false;
        tabLayout.setClickable(false);
        CommonUtils.showProgressDialogWithMessage(this, dialog, null);
        stationDetailService.getRealData(currentParameterId)
                .subscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<EquipmentRealData>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
//                        CommonUtils.showToast("网络错误");
                        finishedFetchingRealData = true;
                        canClickTabLayout = true;
                        finishLoading();
                    }

                    @Override
                    public void onNext(EquipmentRealData bean) {
                        finishedFetchingRealData = true;
                        canClickTabLayout = true;
                        finishLoading();
                        realDataBean = bean;
                        if (bean.isSuccess()) {
                            setRealData();
                        }
                    }
                });
    }

    private void setRealData() {
        DecimalFormat decimalFormat = new DecimalFormat("0.00");
        if (upperLimits.isEmpty() || lowerLimits.isEmpty()) {
            tvValue.setTextColor(getResources().getColor(R.color.green_70));
            tvUnit.setTextColor(getResources().getColor(R.color.green_70));
            waveView.setProgressValue(70);
            waveView.setWaveColor(getResources().getColor(R.color.colorPrimary));
            waveView.startAnimation();
//            ivStatus.setVisibility(View.VISIBLE);
//            ivStatus.setBackgroundResource(R.drawable.ripple_blue);
            tvValue.setText(decimalFormat.format(realDataBean.getData()));//format 返回的是字符串
        } else {
            if (!upperLimits.get(index).isEmpty()) {
                float upper = Float.valueOf(upperLimits.get(index));
                float lower = Float.valueOf(lowerLimits.get(index));
                if (realDataBean.getData() >= upper) {
                    // 报警
                    tvValue.setTextColor(getResources().getColor(R.color.white));
                    tvUnit.setTextColor(getResources().getColor(R.color.white));
                    waveView.setProgressValue(80);
                    waveView.setWaveColor(Color.parseColor("#E05A72"));
                    waveView.startAnimation();
//                    ivStatus.setVisibility(View.VISIBLE);
//                    ivStatus.setBackgroundResource(R.drawable.ripple_red);

//                    RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) ivStatus.getLayoutParams();
//                    layoutParams.width = ConvertUtils.dp2px(90F);
//                    layoutParams.height = ConvertUtils.dp2px(90F);
//                    ivStatus.setLayoutParams(layoutParams);
                } else if (realDataBean.getData() < upper || realDataBean.getData() > lower) {
                    // 正常
                    tvValue.setTextColor(getResources().getColor(R.color.green_70));
                    tvUnit.setTextColor(getResources().getColor(R.color.green_70));
                    int percent = (int) ((realDataBean.getData() - lower) / (upper - lower) * 100);
                    waveView.setProgressValue(percent);
                    waveView.setWaveColor(getResources().getColor(R.color.colorPrimary));
                    waveView.startAnimation();
//                    ivStatus.setVisibility(View.VISIBLE);
//                    ivStatus.setBackgroundResource(R.drawable.ripple_blue);

//                    float percent = (realDataBean.getData() - lower) / (upper - lower);
//                    RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) ivStatus.getLayoutParams();
//                    layoutParams.width = ConvertUtils.dp2px(90F);
//                    layoutParams.height = ConvertUtils.dp2px(90 * percent);
//                    ivStatus.setLayoutParams(layoutParams);
                }
            } else {
                // 隐藏图片
//                ivStatus.setVisibility(View.GONE);
                waveView.setProgressValue(70);
                waveView.setWaveColor(getResources().getColor(R.color.colorPrimary));
                waveView.startAnimation();
            }
            tvValue.setText(decimalFormat.format(realDataBean.getData()));//format 返回的是字符串
        }
    }

    private void fetchParameterDetail() {
        canClickTabLayout = false;
        tabLayout.setClickable(false);
        finishedFetchingParameterDetail = false;
        CommonUtils.showProgressDialogWithMessage(this, dialog, null);
        stationDetailService.getParameterDetail(equipmentId)
                .subscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ParameterDetailBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
//                        CommonUtils.showToast("网络错误");
                        finishedFetchingParameterDetail = true;
                        canClickTabLayout = true;
                        finishLoading();
                    }

                    @Override
                    public void onNext(ParameterDetailBean bean) {
                        finishedFetchingParameterDetail = true;
                        canClickTabLayout = true;
                        finishLoading();
                        parameterDetailBean = bean;
                        if (bean.isSuccess()) {
                            setParameterDetail();
                        }
                    }
                });
    }

    @SuppressLint("SetTextI18n")
    private void setParameterDetail() {
        tvManufactor.setText(getString(R.string.manufacture) + parameterDetailBean.getData().getManufacturer());
//        tvBoughtDate.setText(getString(R.string.date_purchase) + CommonUtils.timestampToDate(parameterDetailBean.getData().getPurchaseDate()));
//        tvBoughtPrice.setText(getString(R.string.price) + parameterDetailBean.getData().getPurchasePrice());
        tvManufacturedDate.setText(getString(R.string.production_date) + CommonUtils.timestampToDate(parameterDetailBean.getData().getDateOfManufacture()));
        tvAssetCode.setText(getString(R.string.assets_no) + parameterDetailBean.getData().getCode());
        tvMaintainDate.setText(getString(R.string.next_maintenance_date) + CommonUtils.timestampToDate(parameterDetailBean.getData().getNextMaintenanceDate()));
    }

    private void fetchEquipmentChat() {
        stationDetailService.getEquipmentChartUrl()
                .subscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<TechnologyFlowBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        CommonUtils.hideProgressDialog(dialog);
                        CommonUtils.showToast(getString(R.string.network_error));
                        webView.loadUrl(Common.HQHP_SITE_URL);
                        if (refreshLayout.isRefreshing()) {
                            refreshLayout.finishRefresh(true);
                        }
                    }

                    @Override
                    public void onNext(TechnologyFlowBean bean) {
                        CommonUtils.hideProgressDialog(dialog);
                        technologyFlowBean = bean;
                        if (bean.isSuccess()) {
                            setEquipmentChartData();
                        }
                        if (refreshLayout.isRefreshing()) {
                            refreshLayout.finishRefresh(true);
                        }
                    }
                });
    }

    private void setEquipmentChartData() {
//        Log.d("chart", "getWidth=" + webView.getWidth() + ", getHeight=" + webView.getHeight() + ", measuredWidth=" + webView.getMeasuredWidth() + ", measuredHeight=" + webView.getMeasuredHeight());
        if (technologyFlowBean.getData().getUrl().length() > 0) {
            String unitToSend = "";
            switch (unit.toUpperCase()) {
                case "KPA":
                    unitToSend = "7";
                    break;
                case "℃":
                    unitToSend = "8";
                    break;
                case "KM":
                    unitToSend = "9";
                    break;
                case "MPA":
                    unitToSend = "68";
                    break;
                case "MM":
                    unitToSend = "69";
                    break;
                case "%":
                    unitToSend = "70";
                    break;
                case "KG":
                    unitToSend = "71";
                    break;
                case "M³":
                    unitToSend = "72";
                    break;
                case "MMHG":
                    unitToSend = "73";
                    break;
                case "M³/H":
                    unitToSend = "74";
                    break;
                case "NM³/h":
                    unitToSend = "75";
                    break;
                case "KG/S":
                    unitToSend = "88";
                    break;
                case "A":
                    unitToSend = "89";
                    break;
                case "HZ":
                    unitToSend = "90";
                    break;
                case "MIN":
                    unitToSend = "91";
                    break;
                default:
                    break;
            }
            url = Common.BASE_URL + technologyFlowBean.getData().getUrl() + "?stationId=" + stationId +
                    "&dateStr=" + System.currentTimeMillis() + "&parameterId=" + currentParameterId + "&access_token=" + App.token + "&w=" + webView.getWidth() / dpi + "&h=" + webView.getHeight() / dpi + "&unit=" + unitToSend;
        }
        if (BuildConfig.DEBUG) Log.d("chart", "url=" + url);
        webView.loadUrl(url);
    }

//    private void fetchReport() {
//        canClickTabLayout = false;
//        tabLayout.setClickable(false);
//        finishedFetchingReport = false;
//        CommonUtils.showProgressDialogWithMessage(this, dialog, null);
////        stationDetailService.getReport(reportType, stationId, CommonUtils.timestampToDate(System.currentTimeMillis()), currentParameterId)
////        stationDetailService.getReport(reportType, stationId, CommonUtils.timestampToDateWithHour(System.currentTimeMillis()), currentParameterId)
//        stationDetailService.getReport(reportType, stationId, String.valueOf(System.currentTimeMillis()), currentParameterId)
//                .subscribeOn(Schedulers.io())
//                .subscribeOn(AndroidSchedulers.mainThread())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Subscriber<ReportBean>() {
//                    @Override
//                    public void onCompleted() {
//
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
////                        chart.clear();
//                        Log.e("Allen", e.toString());
////                        chart.clear();
//                        CommonUtils.showToast(getString(R.string.network_error));
//                        finishedFetchingReport = true;
//                        canClickTabLayout = true;
//                        finishLoading();
////                        segmentedGroup.setClickable(true);
//                    }
//
//                    @Override
//                    public void onNext(ReportBean bean) {
//                        finishedFetchingReport = true;
//                        canClickTabLayout = true;
//                        finishLoading();
//                        if (bean.isSuccess() && bean.getData().size() > 0) {
//                            reportBean = bean;
//                            setReport();
//                        }
////                        segmentedGroup.setClickable(true);
//                    }
//                });
//    }

//    private void setReport() {
//        chartDataDayKeys.clear();
//        chartDataWeekKeys.clear();
//        chartDataMonthKeys.clear();
//        chartDataDayValues.clear();
//        chartDataWeekValues.clear();
//        chartDataMonthValues.clear();
//        hasValueIndexList.clear();
//        hasNoValueIndexList.clear();
//        chartColors.clear();
//
//        dealChartData();
////        Log.d("Allen", reportBean.getData().size() + "");
////        for (int i = 0; i < reportBean.getData().size(); i++) {
////            try {
////                JSONObject object = new JSONObject(reportBean.getData().get(i).toString());
////                Iterator<String> iterator = object.keys();
////                while (iterator.hasNext()) {
////                    String key = iterator.next();
////                    if (TextUtils.isEmpty(object.get(key).toString().trim())) {
////                        // 该点无数据
//////                        switch (reportType) {
//////                            case "day":
//////                                chartDataDayKeys.add(key);
////////                                chartDataDayValues.add(-10000F);
//////                                break;
//////                            case "week":
//////                                chartDataWeekKeys.add(key);
////////                                chartDataWeekValues.add(-10000F);
//////                                break;
//////                            case "month":
//////                                chartDataMonthKeys.add(key);
////////                                chartDataMonthValues.add(-10000F);
//////                                break;
//////                        }
////                    } else {
////                        // 该点有数据
////                        Float value = Float.parseFloat(object.get(key).toString());
////                        Log.d("Allen", "key => " + key + ", value => " + value);
////                        switch (reportType) {
////                            case "day":
////                                chartDataDayKeys.add(key);
////                                chartDataDayValues.add(value);
////                                break;
////                            case "week":
////                                chartDataWeekKeys.add(key);
////                                chartDataWeekValues.add(value);
////                                break;
////                            case "month":
////                                chartDataMonthKeys.add(key);
////                                chartDataMonthValues.add(value);
////                                break;
////                        }
////                    }
////
////                }
////            } catch (JSONException e) {
////                e.printStackTrace();
////            }
////        }
////        initTable();
////        chart.setVisibility(View.VISIBLE);
////        Log.d("Allen", "设置数据完成");
//    }

    /**
     * 处理获取的表数据
     */
//    private void dealChartData() {
//        for (int i = 0; i < reportBean.getData().size(); i++) {
//            try {
//                JSONObject object = new JSONObject(reportBean.getData().get(i).toString());
//                Iterator<String> iterator = object.keys();
//                while (iterator.hasNext()) {
//                    String key = iterator.next();
//                    if (TextUtils.isEmpty(object.get(key).toString().trim())) {
//                        hasNoValueIndexList.add(i);
//                        chartColors.add(getResources().getColor(R.color.backgroundActivity));
////                        // 该点无数据
//                        switch (reportType) {
//                            case "day":
////                                chartDataDayKeys.add(key);
//                                chartDataDayKeys.add(CommonUtils.timestampToDate(CommonUtils.dateToTimestamp(key), "HH:mm"));
//                                if (currentValue == -10000F) {
//                                    chartDataDayValues.add(0F);
//                                } else {
//                                    chartDataDayValues.add(currentValue);
//                                }
//                                break;
//                            case "week":
////                                chartDataWeekKeys.add(key);
//                                chartDataWeekKeys.add(CommonUtils.timestampToDate(CommonUtils.dateToTimestamp(key), "MM-dd"));
//                                if (currentValue == -10000F) {
//                                    chartDataWeekValues.add(0F);
//                                } else {
//                                    chartDataWeekValues.add(currentValue);
//                                }
//                                break;
//                            case "month":
////                                chartDataMonthKeys.add(key);
//                                chartDataMonthKeys.add(CommonUtils.timestampToDate(CommonUtils.dateToTimestamp(key), "MM-dd"));
//                                if (currentValue == -10000F) {
//                                    chartDataMonthValues.add(0F);
//                                } else {
//                                    chartDataMonthValues.add(currentValue);
//                                }
//                                break;
//                        }
//                    } else {
//                        hasValueIndexList.add(i);
//                        chartColors.add(getResources().getColor(R.color.colorPrimary));
//                        // 该点有数据
//                        Float value = Float.parseFloat(object.get(key).toString());
//                        Log.d("Allen", "key => " + key + ", value => " + value);
//                        switch (reportType) {
//                            case "day":
////                                chartDataDayKeys.add(key);
//                                chartDataDayKeys.add(CommonUtils.timestampToDate(CommonUtils.dateToTimestamp(key), "HH:mm"));
//                                chartDataDayValues.add(value);
//                                break;
//                            case "week":
////                                chartDataWeekKeys.add(key);
//                                chartDataWeekKeys.add(CommonUtils.timestampToDate(CommonUtils.dateToTimestamp(key), "MM-dd"));
//                                chartDataWeekValues.add(value);
//                                break;
//                            case "month":
////                                chartDataMonthKeys.add(key);
//                                chartDataMonthKeys.add(CommonUtils.timestampToDate(CommonUtils.dateToTimestamp(key), "MM-dd"));
//                                chartDataMonthValues.add(value);
//                                break;
//                        }
//                        currentValue = value;
//                    }
//                }
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//        }
////        initTable();
////        chart.setVisibility(View.VISIBLE);
//    }

//    private void initTable() {
//        chart.clear();
//        chart.setDrawGridBackground(false);
//        chart.getDescription().setEnabled(false);
//        chart.setDrawBorders(false);
//        chart.setMaxVisibleValueCount(30);
//        chart.setPinchZoom(true);
//        chart.setDragEnabled(true);
//        chart.setScaleEnabled(true);
//        chart.setDrawGridBackground(false);
//        chart.getAxisRight().setEnabled(false);
//        chart.setNoDataText("没有数据");
//        chart.animateXY(1000, 1000, Easing.EasingOption.EaseInOutExpo, Easing.EasingOption.EaseInOutExpo);
//
//        XAxis xAxis = chart.getXAxis();
//        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
//        xAxis.setDrawAxisLine(false);
//        xAxis.setDrawGridLines(false);
//        xAxis.setDrawLabels(true);
//        xAxis.setGranularity(1f);
//        xAxis.setLabelRotationAngle(-25);
//        xAxis.setTextSize(8f);
//        xAxis.setAvoidFirstLastClipping(false);
//        float maxValue = 0f;
//        float minValue = 0f;
//        switch (reportType) {
//            case "day":
//                if (chartDataDayKeys.size() == 24) {
////                    xAxis.setLabelCount(chartDataDayKeys.size() / 6);
//                    xAxis.setLabelCount(5);
//                } else if (chartDataDayKeys.size() > 20) {
//                    xAxis.setLabelCount(chartDataDayKeys.size() / 2);
//                } else {
//                    xAxis.setLabelCount(chartDataDayKeys.size());
//                }
//                maxValue = CommonUtils.getMaxValue(chartDataDayValues);
//                minValue = CommonUtils.getMinValue(chartDataDayValues);
////                if (isMinManual && CommonUtils.getMinValue(chartDataDayValues) < -1000) {
////                }
////                minValue = isMinManual ? 0F : CommonUtils.getMinValue(chartDataDayValues);
//                break;
//            case "week":
//                if (chartDataWeekKeys.size() > 14) {
//                    xAxis.setLabelCount(chartDataWeekKeys.size() / 2);
//                } else {
//                    xAxis.setLabelCount(chartDataWeekKeys.size());
//                }
//                maxValue = CommonUtils.getMaxValue(chartDataWeekValues);
//                minValue = CommonUtils.getMinValue(chartDataWeekValues);
////                minValue = isMinManual ? 0F : CommonUtils.getMinValue(chartDataWeekValues);
//                break;
//            case "month":
//                if (chartDataMonthKeys.size() > 20) {
//                    xAxis.setLabelCount(chartDataMonthKeys.size() / 2);
//                } else {
//                    xAxis.setLabelCount(chartDataMonthKeys.size());
//                }
//                maxValue = CommonUtils.getMaxValue(chartDataMonthValues);
//                minValue = CommonUtils.getMinValue(chartDataMonthValues);
////                minValue = isMinManual ? 0F : CommonUtils.getMinValue(chartDataMonthValues);
//                break;
//        }
////        xAxis.setLabelCount(chartDataKeys.size());
//        xAxis.setValueFormatter(new IAxisValueFormatter() {
//            @Override
//            public String getFormattedValue(float value, AxisBase axis) {
////                return chartDataKeys.get(Math.round(value));
//                Log.d("Allen", "自定义x轴格式: " + value);
//                switch (reportType) {
//                    case "day":
//                        if (Math.round(value) < chartDataDayKeys.size())
//                            return chartDataDayKeys.get(Math.round(value));
//                    case "week":
//                        if (Math.round(value) < chartDataWeekKeys.size())
//                            return chartDataWeekKeys.get(Math.round(value));
//                    case "month":
//                        if (Math.round(value) < chartDataMonthKeys.size())
//                            return chartDataMonthKeys.get(Math.round(value));
//                    default:
//                        return "";
//                }
//            }
//        });
//
//        YAxis leftAxis = chart.getAxisLeft();
//        leftAxis.removeAllLimitLines(); // reset all limit lines to avoid overlapping lines
//        leftAxis.setDrawAxisLine(true);
//        leftAxis.setDrawGridLines(false);
////        leftAxis.addLimitLine(ll1);// 上限
////        leftAxis.addLimitLine(ll2);// 下限
////        float maxValue = CommonUtils.getMaxValue(chartDataValues);
//        leftAxis.setAxisMaximum(maxValue + 1);// 设置报表y轴最大值
////        leftAxis.setAxisMinimum(0f);// 设置报表y轴最小值
//        leftAxis.setAxisMinimum(minValue);// 设置报表y轴最小值
//        leftAxis.enableGridDashedLine(10f, 10f, 0f);
//        leftAxis.setDrawZeroLine(false);
//        leftAxis.setDrawLimitLinesBehindData(true);
//
//        // 点击显示详细信息，暂时隐藏
////        XYMarkerView mv = new XYMarkerView(this, xAxis.getValueFormatter());
////        mv.setChartView(chart); // For bounds control
////        chart.setMarker(mv); // Set the marker to the chart
//
//        Legend l = chart.getLegend();
//        l.setEnabled(false);
//        l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
//        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
//        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
//        l.setForm(Legend.LegendForm.LINE);
//        l.setDrawInside(false);
//        l.setFormSize(9f);
//        l.setTextSize(11f);
//        l.setXEntrySpace(4f);
//
//        chart.getAxisRight().setEnabled(false);
//        switch (reportType) {
//            case "day":
////                setChartData(chartDataDayKeys.size());
//                setChartData(chartDataDayValues.size());
//                break;
//            case "week":
////                setChartData(chartDataWeekKeys.size());
//                setChartData(chartDataWeekValues.size());
//                break;
//            case "month":
////                setChartData(chartDataMonthKeys.size());
//                setChartData(chartDataMonthValues.size());
//                break;
//        }
////        setChartData(chartDataKeys.size());
//    }

    /**
     * 设置图表
     */
//    private void setChartData(int count) {
//        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
//        ArrayList<Entry> yValues = new ArrayList<>();
////        int color;
//        for (int i = 0; i < count; i++) {
////            yValues.add(new Entry(i, Float.parseFloat(chartDataValues.get(i))));
//            switch (reportType) {
//                case "day":
//                    yValues.add(new Entry(i, chartDataDayValues.get(i)));
//                    break;
//                case "week":
//                    yValues.add(new Entry(i, chartDataWeekValues.get(i)));
//                    break;
//                case "month":
//                    yValues.add(new Entry(i, chartDataMonthValues.get(i)));
//                    break;
//            }
////            color = chartColors.get(i % chartColors.size());
//        }
//        LineDataSet set = new LineDataSet(yValues, currentTableTitle);
//        setChartStyle(set);
//        dataSets.add(set);
//
//        ((LineDataSet) dataSets.get(0)).setColors(chartColors);
//
//        LineData data = new LineData(dataSets);
//        chart.setData(data);
//        chart.invalidate();
//    }

    /**
     * 设置图标样式
     */
//    private void setChartStyle(LineDataSet set) {
////        set.setMode(LineDataSet.Mode.LINEAR);
////        set.setMode(LineDataSet.Mode.LINEAR);
//        set.setDrawValues(false);
//        set.setDrawFilled(false);
//        set.setDrawIcons(false);
//        set.setDrawCircles(false);
//        set.disableDashedHighlightLine();
//        set.disableDashedLine();
//        chart.setHighlightPerTapEnabled(false);
//    }
    @Override
    protected boolean isShowBacking() {
        return true;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_detail_item;
    }

    private void finishLoading() {
//        if (finishedFetchingRealData && finishedFetchingParameterDetail && finishedFetchingReport) {
        if (finishedFetchingRealData && finishedFetchingParameterDetail) {
            CommonUtils.hideProgressDialog(dialog);
            tabLayout.setClickable(true);
        }
    }

//    public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
//        switch (checkedId) {
//            case R.id.radio_today_detail_item:
//                reportType = "day";
//                segmentedGroup.setClickable(false);
//                fetchReport();
//                break;
//            case R.id.radio_week_detail_item:
//                reportType = "week";
//                segmentedGroup.setClickable(false);
//                fetchReport();
//                break;
//            case R.id.radio_month_detail_item:
//                reportType = "month";
//                segmentedGroup.setClickable(false);
//                fetchReport();
//                break;
//            default:
//                break;
//        }
//    }
}

