package com.hqhop.www.iot.activities.main.workbench.station.detail;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.hqhop.www.iot.App;
import com.hqhop.www.iot.R;
import com.hqhop.www.iot.activities.main.workbench.station.detail.modules.AlertMessageFragment;
import com.hqhop.www.iot.activities.main.workbench.station.detail.modules.BusinessStatisticsFragment;
import com.hqhop.www.iot.activities.main.workbench.station.detail.modules.DispatchRecordFragment;
import com.hqhop.www.iot.activities.main.workbench.station.detail.modules.EquipmentMaintainFragment;
import com.hqhop.www.iot.activities.main.workbench.station.detail.modules.EquipmentMonitorFragment;
import com.hqhop.www.iot.activities.main.workbench.station.detail.modules.GatewayFragment;
import com.hqhop.www.iot.activities.main.workbench.station.detail.modules.MoreFragment;
import com.hqhop.www.iot.activities.main.workbench.station.detail.modules.PollingRecordFragment;
import com.hqhop.www.iot.activities.main.workbench.station.detail.modules.TechnologyFlowActivity;
import com.hqhop.www.iot.activities.main.workbench.station.detail.modules.TechnologyFlowFragment;
import com.hqhop.www.iot.activities.main.workbench.station.detail.modules.VideoMonitorFragment;
import com.hqhop.www.iot.api.WeatherService;
import com.hqhop.www.iot.api.workbench.detail.StationDetailService;
import com.hqhop.www.iot.base.Common;
import com.hqhop.www.iot.base.adapter.TitlesDetailStationGridViewAdapter;
import com.hqhop.www.iot.base.adapter.ViewPagerAdapter;
import com.hqhop.www.iot.base.http.RetrofitManager;
import com.hqhop.www.iot.base.util.CommonUtils;
import com.hqhop.www.iot.base.view.BaseAppCompatActivity;
import com.hqhop.www.iot.base.view.HorizontalListView;
import com.hqhop.www.iot.bean.ShownEquipmentButtonBean;
import com.hqhop.www.iot.bean.StationAlarmInfoBean;
import com.hqhop.www.iot.bean.StationInforBean;
import com.hqhop.www.iot.bean.StationTypeBean;
import com.hqhop.www.iot.bean.WeatherBean;
import com.yongchun.library.utils.ScreenUtils;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class DetailStationActivity extends BaseAppCompatActivity implements View.OnClickListener {

    private Intent gotIntent;

    private StationTypeBean.DataBean dataBean;

    public static String stationName = "";

    private String leaderName, leaderPhone;

    public static String stationId = "";

    private int alertCount;

    public static String stationType = "all";

    private TextView titleToolbar;

    private ImageView ivTriangle;

    private LinearLayout containerAlert, containerWeather;

    private View dividerWeatherAndAlert;

    private ImageView ivWeather;

    private TextView tvWeatherError;

    private LinearLayout containerTemperature;

    private TextView tvAlertTitle, tvAlert, tvWeather, tvTemperatureNow, tvTemperatureMax, tvTemperatureMin;

    private HorizontalListView gridViewTitle;

    private TitlesDetailStationGridViewAdapter titlesAdapter;

    private List<Integer> iconIds;

    private List<String> titles;

    private List<String> codes;

    /**
     * GridView的子项View
     */
    private List<View> gridViewItems;

    private ViewPager viewPager;

    private ViewPagerAdapter viewPagerAdapter;

    private List<Fragment> fragmentList;

    /**
     * 设备监控Fragment
     */
    private Fragment equipmentMonitorFragment;

    /**
     * 报警信息Fragment
     */
    private Fragment alertMessageFragment;

    /**
     * 运营统计Fragment
     */
    private Fragment businessStatisticsFragment;

    /**
     * 工艺流程图Fragment
     */
    private Fragment technologyFlowFragment;

    /**
     * 设备维保Fragment
     */
    private Fragment equipmentMaintainFragment;

    /**
     * 调度记录Fragment
     */
    private Fragment dispatchRecordFragment;

    /**
     * 巡检记录Fragment
     */
    private Fragment pollingRecordFragment;

    /**
     * 视频监控Fragment
     */
    private Fragment videoMonitorFragment;

    /**
     * 采集网关Fragment
     */
    private Fragment gatewayFragment;

    /**
     * 更多Fragment
     */
    private Fragment moreFragment;

    private int technologyIndex = -1;

    private int alarmIndex = -1;

    public Dialog dialog;

    public StationDetailService stationDetailService;

    private ShownEquipmentButtonBean buttonBean;

    private StationInforBean stationInforBean;

    private StationAlarmInfoBean stationAlarmInforBean;

    // 经度、纬度
    private String longitude, latitude;

    private int type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    private void init() {
        gotIntent = getIntent();

        dialog = CommonUtils.createLoadingDialog(this);
        gridViewItems = new ArrayList<>();

        stationDetailService = RetrofitManager.getInstance(this).createService(StationDetailService.class);

        titleToolbar = findViewById(R.id.title_toolbar);
        ivTriangle = findViewById(R.id.triangle_toolbar);
        // 报警栏
        containerAlert = findViewById(R.id.container_alert_detail_station);
        tvAlert = findViewById(R.id.tv_alert_detail_station);
        tvAlertTitle = findViewById(R.id.tv_alert_title_detail_station);
        // 分割线
        dividerWeatherAndAlert = findViewById(R.id.divider_weather_alert_detail_station);
        // 天气栏
        containerWeather = findViewById(R.id.container_weather_detail_station);
        ivWeather = findViewById(R.id.iv_weather_detail_station);
        tvWeatherError = findViewById(R.id.tv_weather_error_detail_station);
        containerTemperature = findViewById(R.id.container_temperature_detail_station);
        tvWeather = findViewById(R.id.tv_weather_detail_station);
        tvTemperatureNow = findViewById(R.id.tv_temperature_now_detail_station);
        tvTemperatureMax = findViewById(R.id.tv_temperature_max_detail_station);
        tvTemperatureMin = findViewById(R.id.tv_temperature_min_detail_station);
        viewPager = findViewById(R.id.viewpager_detail_station);
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                Log.d("Allen", "onPageScrolled:" + position);
            }

            @Override
            public void onPageSelected(int position) {
                Log.d("Allen", "onPageSelected:" + position);
//                setItemBackgroundColor(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                Log.d("Allen", "onPageScrollStateChanged:" + state);
            }
        });

        type = gotIntent.getIntExtra("type", 0);
//        ivTriangle.setVisibility(View.VISIBLE);
        if (type == 0) {
            // 从其他页面跳转过来
            dataBean = gotIntent.getParcelableExtra(Common.BUNDLE_STATION);
            stationName = gotIntent.getStringExtra("name");
            stationId = gotIntent.getStringExtra("id");
            alertCount = gotIntent.getIntExtra("alarmCount", 0);
            leaderName = gotIntent.getStringExtra("leaderName");
            leaderPhone = gotIntent.getStringExtra("leaderPhone");
            stationType = gotIntent.getStringExtra("stationType");
            longitude = gotIntent.getStringExtra("longitude");
            latitude = gotIntent.getStringExtra("latitude");
            setToolBarTitle(stationName);
        } else if (type == 1) {
            // 从工作台跳转过来
            stationName = gotIntent.getStringExtra("name");
            stationId = gotIntent.getStringExtra("id");
            alertCount = gotIntent.getIntExtra("alarmCount", 0);
            leaderName = gotIntent.getStringExtra("leaderName");
            leaderPhone = gotIntent.getStringExtra("leaderPhone");
            stationType = gotIntent.getStringExtra("stationType");
            longitude = gotIntent.getStringExtra("longitude");
            latitude = gotIntent.getStringExtra("latitude");
            setToolBarTitle(stationName);
        } else if (type == 2) {
            // 从推送、地图、通知、经营统计Web页面、站点类型页面搜索结果跳转过来
            stationId = gotIntent.getStringExtra("id");
            fetchInformation();
        }

//        ivTriangle.setVisibility(View.VISIBLE);
        titleToolbar.setOnClickListener(this);
        ivTriangle.setOnClickListener(this);

//        if (alertCount > 0) {
////            containerAlert.setVisibility(View.VISIBLE);
////            showDivider();
//            tvAlert.setText("本站有" + alertCount + "条报警信息");
//        } else {
//            tvAlert.setText("暂无报警信息");
////            showDivider();
//        }

        gridViewTitle = findViewById(R.id.gridview_titles_detail_station);
        ViewGroup.LayoutParams lp = gridViewTitle.getLayoutParams();
        lp.height = ScreenUtils.dip2px(this, 100);
        gridViewTitle.setLayoutParams(lp);

        iconIds = new ArrayList<>();
        titles = new ArrayList<>();
        codes = new ArrayList<>();
        titlesAdapter = new TitlesDetailStationGridViewAdapter(this, iconIds, titles, codes, gridViewTitle);
        gridViewTitle.setAdapter(titlesAdapter);
        gridViewTitle.setOnItemClickListener((parent, view, position, id) -> {
            if (gridViewItems.size() == 0) {
                for (int i = 0; i < fragmentList.size(); i++) {
//                    gridViewItems.add(gridViewTitle.getChildAt(i));
                    gridViewItems.add(titlesAdapter.getItem(i));
                }
            }
            if (technologyIndex != -1 && position == technologyIndex) {
                Intent intent = new Intent(DetailStationActivity.this, TechnologyFlowActivity.class);
                startActivity(intent);
            } else {
                viewPager.setCurrentItem(position, true);
            }
            setItemBackgroundColor(position);
        });

        fragmentList = new ArrayList<>();
        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), fragmentList);
        viewPager.setAdapter(viewPagerAdapter);

        fetchData();
    }

    private void fetchData() {
        fetchWhatToShow();
        if (gotIntent.getIntExtra("type", 0) != 2) {
            fetchWeather();
        }
        fetchAlarm();
    }

    private void fetchInformation() {
        stationDetailService.getStationInformation(stationId)
                .subscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<StationInforBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        CommonUtils.showToast(getString(R.string.network_error));
                    }

                    @Override
                    public void onNext(StationInforBean bean) {
                        stationInforBean = bean;
                        if (bean.isSuccess()) {
                            setInformation();
                        }
                    }
                });
    }

    private void setInformation() {
        stationName = stationInforBean.getData().getName();
        leaderName = stationInforBean.getData().getLeaderName();
        leaderPhone = stationInforBean.getData().getLeaderMobile();
        stationType = stationInforBean.getData().getType();
        latitude = stationInforBean.getData().getLatitude();
        longitude = stationInforBean.getData().getLongitude();
        setToolBarTitle(stationName);
        fetchWeather();
    }

    private void fetchWhatToShow() {
        stationDetailService.getShownItems("1", App.userid)
                .subscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ShownEquipmentButtonBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        CommonUtils.showToast(getString(R.string.network_error));
                    }

                    @Override
                    public void onNext(ShownEquipmentButtonBean bean) {
                        buttonBean = bean;
                        if (bean.isSuccess() && bean.getData().size() > 0) {
                            setWhatToShow();
                        }
                    }
                });
    }

    private void setWhatToShow() {
        for (int i = 0; i < buttonBean.getData().size(); i++) {
            titles.add(buttonBean.getData().get(i).getName());
            codes.add(buttonBean.getData().get(i).getCode());
            if (buttonBean.getData().get(i).getName().equals("报警信息")) {
                alarmIndex = i;
            }
//            if (buttonBean.getData().get(i).getCode().equals("1002")) {
//                alarmIndex = i;
//            }
        }
        setTitlesData();
    }

    private void fetchWeather() {
        if (!TextUtils.isEmpty(longitude) && !TextUtils.isEmpty(latitude)) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("https://free-api.heweather.com/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            WeatherService weatherService = retrofit.create(WeatherService.class);
            Call<WeatherBean> callShops = weatherService.getWeather(longitude + "," + latitude, Common.WEATHER_KEY);
            callShops.enqueue(new Callback<WeatherBean>() {
                @Override
                public void onResponse(Call<WeatherBean> call, Response<WeatherBean> response) {
                    if (response.isSuccessful()) {
                        try {
                            WeatherBean weatherBean = response.body();
                            if (weatherBean.getHeWeather5().size() > 0) {
                                String city = weatherBean.getHeWeather5().get(0).getBasic().getCity();
                                String nowWeather = weatherBean.getHeWeather5().get(0).getDaily_forecast().get(0).getCond().getTxt_d();
                                String nextWeather = weatherBean.getHeWeather5().get(0).getDaily_forecast().get(0).getCond().getTxt_n();
                                String minTemp = weatherBean.getHeWeather5().get(0).getDaily_forecast().get(0).getTmp().getMin();
                                String maxTemp = weatherBean.getHeWeather5().get(0).getDaily_forecast().get(0).getTmp().getMax();
                                String nowTemp = weatherBean.getHeWeather5().get(0).getNow().getTmp();
                                tvTemperatureNow.setText(nowTemp + "℃");
                                tvTemperatureMax.setText(maxTemp + "℃");
                                tvTemperatureMin.setText(minTemp + "℃");
                                tvWeatherError.setVisibility(View.GONE);
                                containerTemperature.setVisibility(View.VISIBLE);

                                String code = weatherBean.getHeWeather5().get(0).getNow().getCond().getCode();
                                if (code.equals("102") || code.equals("103") || code.equals("200") || code.equals("201") || code.equals("202") ||
                                        code.equals("203") || code.equals("204") || code.equals("205") || code.equals("206") || code.equals("207") || code.equals("208") ||
                                        code.equals("209") || code.equals("210") || code.equals("211") || code.equals("212") || code.equals("213") || code.equals("301") ||
                                        code.equals("303") || code.equals("308") || code.equals("309") || code.equals("405") || code.equals("406") || code.equals("500") ||
                                        code.equals("900") || code.equals("901") || code.equals("999")) {
                                    Thread thread = new Thread() {
                                        @Override
                                        public void run() {
                                            super.run();
                                            try {
                                                Bitmap baseBitmap, afterBitmap;
                                                URL url = new URL(Common.WEATHER_ICON_URL + weatherBean.getHeWeather5().get(0).getNow().getCond().getCode() + ".png");
                                                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                                                conn.setConnectTimeout(5000);
                                                conn.setRequestMethod("GET");
                                                if (conn.getResponseCode() == 200) {
                                                    InputStream inputStream = conn.getInputStream();
                                                    baseBitmap = BitmapFactory.decodeStream(inputStream);
                                                    afterBitmap = Bitmap.createBitmap(baseBitmap.getWidth(), baseBitmap.getHeight(), baseBitmap.getConfig());
                                                    Canvas canvas = new Canvas(afterBitmap);
                                                    Paint paint = new Paint();
                                                    float[] src = new float[]{
                                                            1, 0, 0, 0, 255,
                                                            0, 1, 0, 0, 255,
                                                            0, 0, 1, 0, 255,
                                                            0, 0, 0, 1, 0};
                                                    ColorMatrix colorMatrix = new ColorMatrix();
                                                    colorMatrix.set(src);
                                                    paint.setColorFilter(new ColorMatrixColorFilter(src));
                                                    canvas.drawBitmap(baseBitmap, new Matrix(), paint);
                                                    runOnUiThread(() -> {
                                                        ivWeather.setVisibility(View.VISIBLE);
                                                        ivWeather.setImageBitmap(afterBitmap);
                                                    });
                                                }
                                            } catch (IOException e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    };
                                    thread.start();
                                } else {
                                    switch (code) {
                                        case "100":
                                            ivWeather.setVisibility(View.VISIBLE);
                                            ivWeather.setImageResource(R.drawable.weather_qing);
                                            break;
                                        case "101":
                                            ivWeather.setVisibility(View.VISIBLE);
                                            ivWeather.setImageResource(R.drawable.weather_duoyun);
                                            break;
                                        case "104":
                                            ivWeather.setVisibility(View.VISIBLE);
                                            ivWeather.setImageResource(R.drawable.weather_yin);
                                            break;
                                        case "300":
                                            ivWeather.setVisibility(View.VISIBLE);
                                            ivWeather.setImageResource(R.drawable.weather_zhenyu);
                                            break;
                                        case "302":
                                            ivWeather.setVisibility(View.VISIBLE);
                                            ivWeather.setImageResource(R.drawable.weather_leizhenyu);
                                            break;
                                        case "304":
                                            ivWeather.setVisibility(View.VISIBLE);
                                            ivWeather.setImageResource(R.drawable.weather_leizhenyu_bingbao);
                                            break;
                                        case "305":
                                            ivWeather.setVisibility(View.VISIBLE);
                                            ivWeather.setImageResource(R.drawable.weather_xiaoyu);
                                            break;
                                        case "306":
                                            ivWeather.setVisibility(View.VISIBLE);
                                            ivWeather.setImageResource(R.drawable.weather_zhongyu);
                                            break;
                                        case "307":
                                            ivWeather.setVisibility(View.VISIBLE);
                                            ivWeather.setImageResource(R.drawable.weather_dayu);
                                            break;
                                        case "310":
                                            ivWeather.setVisibility(View.VISIBLE);
                                            ivWeather.setImageResource(R.drawable.weather_baoyu);
                                            break;
                                        case "311":
                                            ivWeather.setVisibility(View.VISIBLE);
                                            ivWeather.setImageResource(R.drawable.weather_dabaoyu);
                                            break;
                                        case "312":
                                            ivWeather.setVisibility(View.VISIBLE);
                                            ivWeather.setImageResource(R.drawable.weather_tedabaoyu);
                                            break;
                                        case "313":
                                            ivWeather.setVisibility(View.VISIBLE);
                                            ivWeather.setImageResource(R.drawable.weather_dongyu);
                                            break;
                                        case "400":
                                            ivWeather.setVisibility(View.VISIBLE);
                                            ivWeather.setImageResource(R.drawable.weather_xiaoxue);
                                            break;
                                        case "401":
                                            ivWeather.setVisibility(View.VISIBLE);
                                            ivWeather.setImageResource(R.drawable.weather_zhongxue);
                                            break;
                                        case "402":
                                            ivWeather.setVisibility(View.VISIBLE);
                                            ivWeather.setImageResource(R.drawable.weather_daxue);
                                            break;
                                        case "403":
                                            ivWeather.setVisibility(View.VISIBLE);
                                            ivWeather.setImageResource(R.drawable.weather_baoxue);
                                            break;
                                        case "404":
                                            ivWeather.setVisibility(View.VISIBLE);
                                            ivWeather.setImageResource(R.drawable.weather_yujiaxue);
                                            break;
                                        case "407":
                                            ivWeather.setVisibility(View.VISIBLE);
                                            ivWeather.setImageResource(R.drawable.weather_zhenxue);
                                            break;
                                        case "501":
                                            ivWeather.setVisibility(View.VISIBLE);
                                            ivWeather.setImageResource(R.drawable.weather_wu);
                                            break;
                                        case "502":
                                            ivWeather.setVisibility(View.VISIBLE);
                                            ivWeather.setImageResource(R.drawable.weather_mai);
                                            break;
                                        case "503":
                                            ivWeather.setVisibility(View.VISIBLE);
                                            ivWeather.setImageResource(R.drawable.weather_yangsha);
                                            break;
                                        case "504":
                                            ivWeather.setVisibility(View.VISIBLE);
                                            ivWeather.setImageResource(R.drawable.weather_fuchen);
                                            break;
                                        case "507":
                                            ivWeather.setVisibility(View.VISIBLE);
                                            ivWeather.setImageResource(R.drawable.weather_shachenbao);
                                            break;
                                        case "508":
                                            ivWeather.setVisibility(View.VISIBLE);
                                            ivWeather.setImageResource(R.drawable.weather_qiangshachenbao);
                                            break;
                                    }
                                }
                            } else {
                                tvWeatherError.setVisibility(View.VISIBLE);
                                tvWeatherError.setText(getString(R.string.getting_weather_data_failed));
                                containerTemperature.setVisibility(View.GONE);
                            }
                        } catch (NullPointerException e) {
                            e.printStackTrace();
                            tvWeatherError.setVisibility(View.VISIBLE);
                            tvWeatherError.setText(getString(R.string.getting_weather_data_failed));
                            containerTemperature.setVisibility(View.GONE);
                        }
                    }
                }

                @Override
                public void onFailure(Call<WeatherBean> call, Throwable t) {
                    tvWeatherError.setVisibility(View.VISIBLE);
                    tvWeatherError.setText(getString(R.string.getting_weather_data_failed));
                    containerTemperature.setVisibility(View.GONE);
                }
            });
        } else {
            tvWeatherError.setVisibility(View.VISIBLE);
            tvWeatherError.setText(getString(R.string.getting_weather_data_failed));
            containerTemperature.setVisibility(View.GONE);
        }
    }

    /**
     * 获取站点的报警信息
     */
    private void fetchAlarm() {
        stationDetailService.getStationAlarmInformation(stationId)
                .subscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<StationAlarmInfoBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
//                        CommonUtils.showToast("网络错误");
                        Log.d("Allen", "获取站点报警信息错误" + e.toString());
                    }

                    @Override
                    public void onNext(StationAlarmInfoBean bean) {
                        stationAlarmInforBean = bean;
                        if (bean.isSuccess()) {
                            setAlarm();
                        }
                    }
                });
    }

    @SuppressLint("SetTextI18n")
    private void setAlarm() {
//        if (stationAlarmInforBean.getData().getStatus().equals("报警")) {
//            // 报警
//            tvAlert.setText(stationAlarmInforBean.getData().getContent() + getString(R.string.alarm_message));
//        } else {
//            // 未报警
//            tvAlertTitle.append("(" + getString(R.string.no_alarm_data) + ")");
//            tvAlert.setText(stationAlarmInforBean.getData().getContent());
//        }
        tvAlertTitle.setText(stationAlarmInforBean.getData().getStatus());
        tvAlert.setText(stationAlarmInforBean.getData().getContent());
    }

    /**
     * 点击切换GridView的item的背景
     *
     * @param position 点击项的index
     */
    private void setItemBackgroundColor(int position) {
//        for (View i : gridViewItems) {
//            if (technologyIndex != -1 && position != technologyIndex) {
//                i.setBackgroundColor(getResources().getColor(R.color.white));
//                gridViewItems.get(position).setBackgroundColor(getResources().getColor(R.color.black_transparency_10p));
//            }
//        }

        titlesAdapter.selectedIndex = position;
        titlesAdapter.notifyDataSetChanged();
    }

    private void setTitlesData() {
//        for (String title : titles) {
        for (String code : codes) {
//            switch (title) {
            switch (code) {
                case "1001":
                    if (equipmentMonitorFragment == null) {
                        iconIds.add(R.drawable.ic_equipment_monitor_detail_station);
                        equipmentMonitorFragment = new EquipmentMonitorFragment();
                        fragmentList.add(equipmentMonitorFragment);
                    }
                    break;
                case "1002":
                    if (alertMessageFragment == null) {
                        iconIds.add(R.drawable.ic_alert_message_detail_station);
                        alertMessageFragment = new AlertMessageFragment();
                        fragmentList.add(alertMessageFragment);
                    }
//                    if (gatewayFragment == null) {
//                        iconIds.add(R.drawable.ic_gateway_detail_station);
//                        gatewayFragment = new GatewayFragment();
//                        fragmentList.add(gatewayFragment);
//                    }
                    break;
                case "1003":
                    if (businessStatisticsFragment == null) {
                        iconIds.add(R.drawable.ic_business_statistics_detail_station);
                        businessStatisticsFragment = new BusinessStatisticsFragment();
                        Bundle bundle = new Bundle();
                        bundle.putString("stationId",stationId);
                        businessStatisticsFragment.setArguments(bundle);
                        fragmentList.add(businessStatisticsFragment);
                    }
                    break;
                case "1004":
                    if (technologyFlowFragment == null) {
                        iconIds.add(R.drawable.ic_technology_flow_detail_station);
                        technologyFlowFragment = new TechnologyFlowFragment();
                        fragmentList.add(technologyFlowFragment);
                        technologyIndex = titles.indexOf("工艺流程图");
                    }
                    break;
                case "1005":
                    if (equipmentMaintainFragment == null) {
                        iconIds.add(R.drawable.ic_equipment_maintain_detail_station);
                        equipmentMaintainFragment = new EquipmentMaintainFragment();
                        fragmentList.add(equipmentMaintainFragment);
                    }
                    break;
                case "1006":
                    if (dispatchRecordFragment == null) {
                        iconIds.add(R.drawable.ic_dispatch_record_detail_station);
                        dispatchRecordFragment = new DispatchRecordFragment();
                        fragmentList.add(dispatchRecordFragment);
                    }
                    break;
                case "1007":
                    if (pollingRecordFragment == null) {
                        iconIds.add(R.drawable.ic_polling_record_detail_station);
                        pollingRecordFragment = new PollingRecordFragment();
                        fragmentList.add(pollingRecordFragment);
                    }
                    break;
                case "1008":
                    if (videoMonitorFragment == null) {
                        iconIds.add(R.drawable.ic_video_monitor_detail_station);
                        videoMonitorFragment = new VideoMonitorFragment();
                        fragmentList.add(videoMonitorFragment);
                    }
                    break;
                case "1010":
                    if (gatewayFragment == null) {
                        iconIds.add(R.drawable.ic_gateway_detail_station);
                        gatewayFragment = new GatewayFragment();
                        fragmentList.add(gatewayFragment);
                    }
                    break;
                case "1009":
                    if (moreFragment == null) {
                        iconIds.add(R.drawable.ic_more_detail_station);
                        moreFragment = new MoreFragment();
                        fragmentList.add(moreFragment);
                    }
//                    if (gatewayFragment == null) {
//                        iconIds.add(R.drawable.ic_gateway_detail_station);
//                        gatewayFragment = new GatewayFragment();
//                        fragmentList.add(gatewayFragment);
//                    }
                    break;
            }
        }
//        Log.i("Allen", "titles: " + titles.size() + ", iconIds: " + iconIds.size());
        titlesAdapter.notifyDataSetChanged();
        viewPagerAdapter.notifyDataSetChanged();
        viewPager.setOffscreenPageLimit(titles.size());
        if (type == 1 && gotIntent.getBooleanExtra("showShowAlarm", false) && fragmentList.contains(alertMessageFragment)) {
            viewPager.setCurrentItem(fragmentList.indexOf(alertMessageFragment));
            setItemBackgroundColor(fragmentList.indexOf(alertMessageFragment));
        } else {
            viewPager.setCurrentItem(0);
            setItemBackgroundColor(0);
        }
    }

    /**
     * 显示PopupWindow
     */
    private void showInformationPopupWindow() {
        unfoldRotationAnimation(ivTriangle);

        View contentView = LayoutInflater.from(this).inflate(
                R.layout.layout_popupwindow_information_detail_station, null);
        final PopupWindow popupWindow = new PopupWindow(contentView,
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        TextView tvStation = contentView.findViewById(R.id.tv_station_popupwindow_detail_station);
        TextView tvPerson = contentView.findViewById(R.id.tv_person_popupwindow_detail_station);
        TextView tvPhone = contentView.findViewById(R.id.tv_phone_popupwindow_detail_station);
        ImageView ivDial = contentView.findViewById(R.id.iv_location_popupwindow_detail_station);
//        tvStation.setText(dataBean.getName());
        tvStation.setText(stationName);
//        tvPerson.setText(dataBean.getLeaderName());
        tvPerson.setText(TextUtils.isEmpty(leaderName) ? getString(R.string.unknown) : leaderName);
//        tvPhone.setText(dataBean.getLeaderMobile());
        tvPhone.setText(TextUtils.isEmpty(leaderPhone) ? getString(R.string.unknown) : leaderPhone);
        tvPhone.setOnClickListener(view -> {
            if (!TextUtils.isEmpty(leaderPhone)) {
                CommonUtils.dial(DetailStationActivity.this, leaderPhone);
            }
            popupWindow.dismiss();
        });
        ivDial.setOnClickListener(view -> popupWindow.dismiss());
        // 设置好参数之后再show
        popupWindow.showAsDropDown(findViewById(R.id.toolbar));
        // 设置背景变暗
        final WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = 0.7f;
        getWindow().setAttributes(lp);
        popupWindow.setOnDismissListener(() -> {
            // popupwindow关闭时背景还原
            lp.alpha = 1f;
            getWindow().setAttributes(lp);
            foldRotationAnimation(ivTriangle);
        });
    }

    @Override
    protected boolean isShowBacking() {
        return true;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_detail_station;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.triangle_toolbar:
            case R.id.title_toolbar:
                showInformationPopupWindow();
                break;
            default:
                break;
        }
    }

    /**
     * 折叠时ImageView旋转动画
     */
    private void foldRotationAnimation(View view) {
        ObjectAnimator rotateAnim = ObjectAnimator.ofFloat(view, "rotation", -180F, 0F);
        rotateAnim.setDuration(Common.DURATION_FOLD_ANIMATION);
        rotateAnim.start();
    }

    /**
     * 展开时ImageView旋转动画
     */
    private void unfoldRotationAnimation(View view) {
        ObjectAnimator rotateAnim = ObjectAnimator.ofFloat(view, "rotation", 0F, -180F);
        rotateAnim.setDuration(Common.DURATION_FOLD_ANIMATION);
        rotateAnim.start();
    }
}
