package com.hqhop.www.iot.activities.main.map;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.AMapOptions;
import com.amap.api.maps.AMapUtils;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.SupportMapFragment;
import com.amap.api.maps.UiSettings;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.LatLngBounds;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.MyLocationStyle;
import com.hqhop.www.iot.App;
import com.hqhop.www.iot.R;
import com.hqhop.www.iot.activities.main.workbench.NewWorkbenchFragment;
import com.hqhop.www.iot.activities.main.workbench.station.detail.DetailStationActivity;
import com.hqhop.www.iot.api.map.MapService;
import com.hqhop.www.iot.base.http.RetrofitManager;
import com.hqhop.www.iot.base.util.CommonUtils;
import com.hqhop.www.iot.base.util.ToolbarUtils;
import com.hqhop.www.iot.bean.StationTypeBean;
import com.umeng.analytics.MobclickAgent;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by allen on 2017/7/3.
 */

@SuppressLint("ValidFragment")
public class MapFragment extends SupportMapFragment implements AMapLocationListener, AMap.OnMarkerClickListener, AMap.OnInfoWindowClickListener, AMap.InfoWindowAdapter, AMap.OnMapClickListener {

    private String TAG = "MapFragment";

    private Context mContext;

    private View rootView;

    private Toolbar toolbar;

    private TextView titleToolbar;

    private ImageView ivStatus;

    private MapView mapView;

    private AMap aMap;

    private MyLocationStyle myLocationStyle;

    private AMapLocationClientOption mLocationOption;

    private AMapLocationClient mlocationClient;

    private UiSettings uiSettings;

    private MapService mapService;

    private static MapFragment mapFragment;

    private StationTypeBean stationBean;

    private List<MarkerOptions> markerOptionsList = new ArrayList<>();

    private List<Marker> markerList = new ArrayList<>();

    private Marker currentMarker;

    private double currentLatitude, currentLongitude;

    private int index = -1;

    private int status = 0;
    private String stationType = "all";

    private List<String> shownStationNames;
    private List<String> shownSales;
    private List<String> shownAddresses;
    private List<Integer> shownIds;
    private List<Integer> shownAlarms;
    private List<Integer> shownStatuses;
    private List<String> shownTypes;
    private List<Double> shownLatitudes;
    private List<Double> shownLongitudes;

    private boolean finishInitialization = false;

    public MapFragment() {
    }

    private MapFragment(Context context) {
        mContext = context;
    }

    public static MapFragment getInstance(Context context) {
        if (mapFragment == null) {
            mapFragment = new MapFragment(context);
        }
        return mapFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        if (rootView == null) {
        rootView = inflater.inflate(R.layout.fragment_map, container, false);
//        }

        shownStationNames = new ArrayList<>();
        shownSales = new ArrayList<>();
        shownAddresses = new ArrayList<>();
        shownIds = new ArrayList<>();
        shownAlarms = new ArrayList<>();
        shownStatuses = new ArrayList<>();
        shownTypes = new ArrayList<>();
        shownLatitudes = new ArrayList<>();
        shownLongitudes = new ArrayList<>();

        ivStatus = rootView.findViewById(R.id.iv_status_station_map);
        ivStatus.setOnClickListener(view -> showModulesSettingPopupWindow());

        mapService = RetrofitManager.getInstance(mContext).createService(MapService.class);

        stationBean = new StationTypeBean();
        stationBean.setData(new ArrayList<>());

        CommonUtils.setTranslucentStatusBar(mContext);

        toolbar = rootView.findViewById(R.id.toolbar);
        titleToolbar = rootView.findViewById(R.id.title_toolbar);
        setupToolbar();

        mapView = rootView.findViewById(R.id.map);
        mapView.onCreate(savedInstanceState);
        if (aMap == null) {
            aMap = mapView.getMap();
        }

        myLocationStyle = new MyLocationStyle();
//        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE_NO_CENTER);
//        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_FOLLOW_NO_CENTER);
        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATE);
        myLocationStyle.interval(10000); //设置连续定位模式下的定位间隔，只在连续定位模式下生效，单次定位模式下不会生效。单位为毫秒。
        myLocationStyle.showMyLocation(true);
        myLocationStyle.myLocationIcon(BitmapDescriptorFactory.fromResource(R.drawable.point));
        aMap.setMyLocationStyle(myLocationStyle);//设置定位蓝点的Style
        aMap.getUiSettings().setMyLocationButtonEnabled(true);//设置默认定位按钮是否显示，非必需设置。
//        aMap.getUiSettings().setZoomPosition(AMapOptions.ZOOM_POSITION_RIGHT_CENTER);
        aMap.setMyLocationEnabled(true);// 设置为true表示启动显示定位蓝点，false表示隐藏定位蓝点并不进行定位，默认是false。
        aMap.setOnMarkerClickListener(this);
        aMap.setOnInfoWindowClickListener(this);
        aMap.setInfoWindowAdapter(this);
        aMap.setOnMapClickListener(this);
//        aMap.setTrafficEnabled(true);// 显示实时交通情况
        uiSettings = aMap.getUiSettings();
        uiSettings.setCompassEnabled(true);// 显示指北针
        uiSettings.setMyLocationButtonEnabled(true);// 显示默认的定位按钮
        uiSettings.setScaleControlsEnabled(true);// 显示比例尺控件
        uiSettings.setLogoPosition(AMapOptions.LOGO_POSITION_BOTTOM_RIGHT);// Logo显示位置
        uiSettings.setZoomControlsEnabled(false);// 隐藏缩放按钮

        mlocationClient = new AMapLocationClient(mContext);
        mLocationOption = new AMapLocationClientOption();
        mlocationClient.setLocationListener(this);
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        mlocationClient.setLocationOption(mLocationOption);
        mlocationClient.startLocation();

        return rootView;
    }

    /**
     * 获取站点数据
     */
    private void getStations() {
        mapService.getStations(App.userid, 0, 20, stationType, 3, 0, status)
                .subscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<StationTypeBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(StationTypeBean bean) {
                        finishInitialization = true;
                        if (bean.isSuccess()) {
                            stationBean = bean;
                            setStationsMarker();
                        }
                    }
                });
    }

    /**
     * 设置站点覆盖物
     */
    private void setStationsMarker() {
        shownStationNames.clear();
        shownSales.clear();
        shownAddresses.clear();
        shownIds.clear();
        shownAlarms.clear();
        shownStatuses.clear();
        shownTypes.clear();
        shownLatitudes.clear();
        shownLongitudes.clear();
        for (int i = 0; i < markerList.size(); i++) {
            markerList.get(i).remove();
        }
//        aMap.moveCamera(CameraUpdateFactory.newLatLngBounds());
        markerList.clear();
//        aMap.getMapScreenMarkers().clear();

        for (int i = 0; i < stationBean.getData().size(); i++) {
            if (TextUtils.isEmpty(stationBean.getData().get(i).getLatitude()) || TextUtils.isEmpty(stationBean.getData().get(i).getLongitude())) {
                // 经纬度任意一个为空，则不显示覆盖物
            } else {
                shownStationNames.add(stationBean.getData().get(i).getName());
                shownSales.add(stationBean.getData().get(i).getSaleToday());
                shownAddresses.add(stationBean.getData().get(i).getAddress());
                shownIds.add(stationBean.getData().get(i).getId());
                shownAlarms.add(stationBean.getData().get(i).getAlarmCount());
                shownStatuses.add(stationBean.getData().get(i).getStatus());
                shownTypes.add(stationBean.getData().get(i).getType());
                shownLatitudes.add(Double.parseDouble(stationBean.getData().get(i).getLatitude()));
                shownLongitudes.add(Double.parseDouble(stationBean.getData().get(i).getLongitude()));
            }
        }

        int imgResource;
        boolean canMoveCamera = true;
//        for (int i = 0; i < stationBean.getData().size(); i++) {
        for (int i = 0; i < shownStationNames.size(); i++) {
//            int status = stationBean.getData().get(i).getStatus();
            int status = shownStatuses.get(i);
//            int alarmCount = stationBean.getData().get(i).getAlarmCount();
            int alarmCount = shownAlarms.get(i);
//            String type = stationBean.getData().get(i).getType();
//            String type = stationBean.getData().get(i).getType();
            String type = shownTypes.get(i);
            switch (type.toLowerCase()) {
                case "gas":// 气化站
                    if (alarmCount > 0) {
                        imgResource = R.drawable.avatar_gas;
                    } else {
                        if (status == 1) {
                            imgResource = R.drawable.avatar_gas_type;
                        } else {
                            imgResource = R.drawable.avatar_gas_offline;
                        }
                    }
                    break;
                case "oil":// 加油站
                    if (alarmCount > 0) {
                        imgResource = R.drawable.avatar_oil_alarm;
                    } else {
                        if (status == 1) {
                            imgResource = R.drawable.avatar_oil_type;
                        } else {
                            imgResource = R.drawable.avatar_oil_offline;
                        }
                    }
                    break;
                case "cng":
                    if (alarmCount > 0) {
                        imgResource = R.drawable.avatar_cng_alarm;
                    } else {
                        if (status == 1) {
                            imgResource = R.drawable.avatar_cng_type;
                        } else {
                            imgResource = R.drawable.avatar_cng_offline;
                        }
                    }
                    break;
                case "lng":
                    if (alarmCount > 0) {
                        imgResource = R.drawable.avatar_lng_alarm;
                    } else {
                        if (status == 1) {
                            imgResource = R.drawable.avatar_lng_type;
                        } else {
                            imgResource = R.drawable.avatar_lng_offline;
                        }
                    }
                    break;
                case "l-cng":
                    if (alarmCount > 0) {
                        imgResource = R.drawable.avatar_lcng;
                    } else {
                        if (status == 1) {
                            imgResource = R.drawable.avatar_lcng_type;
                        } else {
                            imgResource = R.drawable.avatar_lcng_offline;
                        }
                    }
                    break;
                case "charging":
                    if (alarmCount > 0) {
                        imgResource = R.drawable.avatar_charging_alarm;
                    } else {
                        if (status == 1) {
                            imgResource = R.drawable.avatar_charging_type;
                        } else {
                            imgResource = R.drawable.avatar_charging_offline;
                        }
                    }
                    break;
                case "factory":
                    if (alarmCount > 0) {
                        imgResource = R.drawable.avatar_factory_alarm;
                    } else {
                        if (status == 1) {
                            imgResource = R.drawable.avatar_factory_type;
                        } else {
                            imgResource = R.drawable.avatar_factory_offline;
                        }
                    }
                    break;
                case "ship":
                    if (alarmCount > 0) {
                        imgResource = R.drawable.avatar_ship_alarm;
                    } else {
                        if (status == 1) {
                            imgResource = R.drawable.avatar_ship_type;
                        } else {
                            imgResource = R.drawable.avatar_ship_offline;
                        }
                    }
                    break;
                case "h2":
                    if (alarmCount > 0) {
                        imgResource = R.drawable.hydrogen_alarm;
                    } else {
                        if (status == 1) {
                            imgResource = R.drawable.hydrogen_default;
                        } else {
                            imgResource = R.drawable.hydrogen_offline;
                        }
                    }
//                    if (alarmCount > 0) {
//                        imgResource = R.drawable.avatar_ship_alarm;
//                    } else {
//                        if (status == 1) {
//                            imgResource = R.drawable.avatar_ship_type;
//                        } else {
//                            imgResource = R.drawable.avatar_ship_offline;
//                        }
//                    }
                    break;
                default:
                    imgResource = R.mipmap.ic_launcher_round;
                    break;
            }
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = 3;
//            if (TextUtils.isEmpty(stationBean.getData().get(i).getLatitude()) || TextUtils.isEmpty(stationBean.getData().get(i).getLongitude())) {
//                continue;
//            }
//            try {
            MarkerOptions markerOptions = new MarkerOptions();
            markerOptions
//                    .position(new LatLng(Double.parseDouble(stationBean.getData().get(i).getLatitude()), Double.parseDouble(stationBean.getData().get(i).getLongitude())))
                    .position(new LatLng(shownLatitudes.get(i), shownLongitudes.get(i)))
//                    .title(stationBean.getData().get(i).getName())
                    .title(shownStationNames.get(i))
                    .draggable(false)
                    .icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory.decodeResource(getResources(), imgResource, options)))
                    .setFlat(true);
            markerOptionsList.add(markerOptions);
            markerList.add(aMap.addMarker(markerOptions));
//                shownLatitudes.add(Double.parseDouble(stationBean.getData().get(i).getLatitude()));
//                shownLongitudes.add(Double.parseDouble(stationBean.getData().get(i).getLongitude()));

            if (canMoveCamera) {
//                aMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(Double.parseDouble(stationBean.getData().get(i).getLatitude()), Double.parseDouble(stationBean.getData().get(i).getLongitude()))));
//                aMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(shownLatitudes.get(i), shownLongitudes.get(i))));

                LatLng topLeft = new LatLng(Collections.max(shownLatitudes), Collections.min(shownLongitudes));
                LatLng bottomRight = new LatLng(Collections.min(shownLatitudes), Collections.max(shownLongitudes));
//                aMap.moveCamera(CameraUpdateFactory.newLatLngBounds(new LatLngBounds(topLeft, bottomRight), 0));
                aMap.moveCamera(CameraUpdateFactory.newLatLngBounds(LatLngBounds.builder().include(bottomRight).include(topLeft).build(), 10));
                canMoveCamera = false;
            }
//            } catch (NumberFormatException e) {
//                e.printStackTrace();
//                Log.d("Allen", "setStationsMarker: " + e);
//            }
        }
//        if (!stationBean.getData().isEmpty()) {
//            aMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(Double.parseDouble(stationBean.getData().get(0).getLatitude()), Double.parseDouble(stationBean.getData().get(0).getLongitude()))));
////            aMap.moveCamera(CameraUpdateFactory.newLatLngBounds());
////            aMap.moveCamera(CameraUpdateFactory.newLatLngBoundsRect());
//        }
    }

    private void setupToolbar() {
        ToolbarUtils.setCustomToolbar(mContext, toolbar);
        ToolbarUtils.setTitle(titleToolbar, getString(R.string.title_map));
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
//        MobclickAgent.onPageStart(TAG);
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
//        MobclickAgent.onPageEnd(TAG);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            MobclickAgent.onPageStart(TAG);
        } else {
            MobclickAgent.onPageEnd(TAG);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
//        mapFragment = null;
    }

    public void destroySelf() {
        mapFragment = null;
    }

    @Override
    public void onLocationChanged(AMapLocation amapLocation) {
        if (amapLocation != null) {
            if (amapLocation.getErrorCode() == 0) {
                //定位成功回调信息，设置相关消息
                amapLocation.getLocationType();//获取当前定位结果来源，如网络定位结果，详见定位类型表
                currentLatitude = amapLocation.getLatitude();//获取纬度
                currentLongitude = amapLocation.getLongitude();//获取经度
                App.latitude = String.valueOf(currentLatitude);
                App.longitude = String.valueOf(currentLongitude);
                amapLocation.getAccuracy();//获取精度信息
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date date = new Date(amapLocation.getTime());
                df.format(date);//定位时间
                if (!finishInitialization) {
                    getStations();
                }
            } else {
                //显示错误信息ErrCode是错误码，errInfo是错误信息，详见错误码表。
                Log.e("AmapError", "location Error, ErrCode:"
                        + amapLocation.getErrorCode() + ", errInfo:"
                        + amapLocation.getErrorInfo());
            }
        }
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        Log.d("Allen", "title:" + marker.getTitle());
        Log.d("Allen", "options:" + marker.getOptions().getTitle());
        return false;
    }

    /**
     * 判断点击的Marker的index
     *
     * @param marker
     */
    private int clickWhichMarker(Marker marker) {
        int index = -1;
        for (int i = 0; i < markerList.size(); i++) {
            if (marker.getTitle().equals(markerList.get(i).getTitle())) {
                index = i;
            }
        }
        return index;
    }

    /**
     * 对Marker进行设置
     */
    @Override
    public View getInfoWindow(Marker marker) {
        index = clickWhichMarker(marker);
//        if (index < 0 || index >= stationBean.getData().size()) {
        if (index < 0 || index >= shownLatitudes.size()) {
            return null;
        }
        currentMarker = marker;
//        String station = stationBean.getData().get(index).getName();
        String station = shownStationNames.get(index);
//        String address = stationBean.getData().get(index).getAddress();
        String address = shownAddresses.get(index);
//        String sales = stationBean.getData().get(index).getSaleToday();
        String sales = shownSales.get(index);
//        String stationId = String.valueOf(stationBean.getData().get(index).getId());
        String stationId = String.valueOf(shownIds.get(index));
//        float distance = getDistance(currentLatitude, currentLongitude, Double.parseDouble(stationBean.getData().get(index).getLatitude()), Double.parseDouble(stationBean.getData().get(index).getLongitude())) / 1000;
        float distance = getDistance(currentLatitude, currentLongitude, shownLatitudes.get(index), shownLongitudes.get(index)) / 1000;
//        double distance = getDistance(currentLatitude, currentLongitude, 34.341568, 108.940174) / 1000;
        String shownDistance = String.format("%.2f", distance) + "km";
        View contentView = LayoutInflater.from(mContext).inflate(R.layout.amap_info_window, null);
        LinearLayout containerStation = contentView.findViewById(R.id.container_amap_info);
        ImageView ivIcon = contentView.findViewById(R.id.iv_icon_amap_info);
        TextView tvStation = contentView.findViewById(R.id.tv_station_amap_info);
        TextView tvDetail = contentView.findViewById(R.id.tv_detail_amap_info);
        TextView tvSales = contentView.findViewById(R.id.tv_sales_amap_info);
        TextView tvAddress = contentView.findViewById(R.id.tv_address_amap_info);
        TextView tvDistance = contentView.findViewById(R.id.tv_distance_amap_info);

        containerStation.setOnClickListener(view -> {
            Intent intent = new Intent(mContext, DetailStationActivity.class);
            intent.putExtra("type", 2);
            intent.putExtra("id", stationId);
            mContext.startActivity(intent);
        });
        tvStation.setText(station);
        tvSales.append("　" + sales);// 销量
        tvAddress.setText(TextUtils.isEmpty(address) ? getString(R.string.no_detail_address) : address);
        tvDistance.setText(shownDistance);

        tvDistance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideInfoWindow();
//                Log.d("Allen", "当前纬度" + currentLatitude + "当前经度" + currentLongitude + "目标纬度" + Float.parseFloat(stationBean.getData().get(index).getLatitude()) + "目标经度" + Float.parseFloat(stationBean.getData().get(index).getLongitude()));
//                testNatigate(currentLatitude, currentLongitude, Float.parseFloat(stationBean.getData().get(index).getLatitude()), Float.parseFloat(stationBean.getData().get(index).getLongitude()));
                testNatigate(currentLatitude, currentLongitude, shownLatitudes.get(index), shownLongitudes.get(index));
            }
        });

        return contentView;
    }

    @Override
    public void onInfoWindowClick(Marker marker) {

    }

    @Override
    public View getInfoContents(Marker marker) {
        return null;
    }

    private float getDistance(double lat1, double lon1, double lat2, double lon2) {
//        float[] results = new float[1];
//        Location.distanceBetween(lat1, lon1, lat2, lon2, results);
//        return results[0];
        return AMapUtils.calculateLineDistance(new LatLng(lat1, lon1), new LatLng(lat2, lon2));
    }

    @Override
    public void onMapClick(LatLng latLng) {
        hideInfoWindow();
    }

    /**
     * 隐藏InfoWindow
     */
    private void hideInfoWindow() {
        if (currentMarker.isInfoWindowShown()) {
            currentMarker.hideInfoWindow();
        }
    }

    /**
     * 导航测试
     */
    private void testNatigate(double lat1, double lon1, double lat2, double lon2) {
//        CommonUtils.showToast("开始导航，全程" + getDistance(lat1, lon1, lat2, lon2) / 1000 + "公里");
        Intent navigationIntent = new Intent(mContext, AMapNavigationActivity.class);
        navigationIntent.putExtra("lat1", lat1);
        navigationIntent.putExtra("lon1", lon1);
        navigationIntent.putExtra("lat2", lat2);
        navigationIntent.putExtra("lon2", lon2);
        startActivity(navigationIntent);
    }

    private void showModulesSettingPopupWindow() {
        View contentView = getLayoutInflater().inflate(R.layout.layout_popupwindow_switch_station_type_map, null);
        LinearLayout containerAll = contentView.findViewById(R.id.container_all_switch_station_type_map);
        ImageView ivAll = contentView.findViewById(R.id.iv_all_switch_station_type_map);
        TextView tvAll = contentView.findViewById(R.id.tv_all_switch_station_type_map);

        LinearLayout containerGas = contentView.findViewById(R.id.container_gas_switch_station_type_map);
        ImageView ivGas = contentView.findViewById(R.id.iv_gas_switch_station_type_map);
        TextView tvGas = contentView.findViewById(R.id.tv_gas_switch_station_type_map);

        LinearLayout containerOil = contentView.findViewById(R.id.container_oil_switch_station_type_map);
        ImageView ivOil = contentView.findViewById(R.id.iv_oil_switch_station_type_map);
        TextView tvOil = contentView.findViewById(R.id.tv_oil_switch_station_type_map);

        LinearLayout containerCng = contentView.findViewById(R.id.container_cng_switch_station_type_map);
        ImageView ivCng = contentView.findViewById(R.id.iv_cng_switch_station_type_map);
        TextView tvCng = contentView.findViewById(R.id.tv_cng_switch_station_type_map);

        LinearLayout containerLng = contentView.findViewById(R.id.container_lng_switch_station_type_map);
        ImageView ivLng = contentView.findViewById(R.id.iv_lng_switch_station_type_map);
        TextView tvLng = contentView.findViewById(R.id.tv_lng_switch_station_type_map);

        LinearLayout containerLcng = contentView.findViewById(R.id.container_lcng_switch_station_type_map);
        ImageView ivLcng = contentView.findViewById(R.id.iv_lcng_switch_station_type_map);
        TextView tvLcng = contentView.findViewById(R.id.tv_lcng_switch_station_type_map);

        LinearLayout containerCharging = contentView.findViewById(R.id.container_charging_switch_station_type_map);
        ImageView ivCharging = contentView.findViewById(R.id.iv_charging_switch_station_type_map);
        TextView tvCharging = contentView.findViewById(R.id.tv_charging_switch_station_type_map);

        LinearLayout containerFactory = contentView.findViewById(R.id.container_factory_switch_station_type_map);
        ImageView ivFactory = contentView.findViewById(R.id.iv_factory_switch_station_type_map);
        TextView tvFactory = contentView.findViewById(R.id.tv_factory_switch_station_type_map);

        LinearLayout containerShip = contentView.findViewById(R.id.container_ship_switch_station_type_map);
        ImageView ivShip = contentView.findViewById(R.id.iv_ship_switch_station_type_map);
        TextView tvShip = contentView.findViewById(R.id.tv_ship_switch_station_type_map);

        LinearLayout containerHydrogen = contentView.findViewById(R.id.container_hydrogen_switch_station_type_map);
        ImageView ivHydrogen = contentView.findViewById(R.id.iv_hydrogen_switch_station_type_map);
        TextView tvHydrogen = contentView.findViewById(R.id.tv_hydrogen_switch_station_type_map);

        for (int i = 0; i < NewWorkbenchFragment.getInstance(mContext).workbenchBean.getData().size(); i++) {
            switch (NewWorkbenchFragment.getInstance(mContext).workbenchBean.getData().get(i).getStationType().toLowerCase()) {
                case "gas":// 气化站
                    containerGas.setVisibility(View.VISIBLE);
                    break;
                case "oil":// 加油站
                    containerOil.setVisibility(View.VISIBLE);
                    break;
                case "cng":
                    containerCng.setVisibility(View.VISIBLE);
                    break;
                case "lng":
                    containerLng.setVisibility(View.VISIBLE);
                    break;
                case "l-cng":
                    containerLcng.setVisibility(View.VISIBLE);
                    break;
                case "charging":
                    containerCharging.setVisibility(View.VISIBLE);
                    break;
                case "factory":
                    containerFactory.setVisibility(View.VISIBLE);
                    break;
                case "ship":
                    containerShip.setVisibility(View.VISIBLE);
                    break;
                case "h2":
                    containerHydrogen.setVisibility(View.VISIBLE);
                    break;
                default:
                    break;
            }
        }

        switch (stationType.toLowerCase()) {
            case "all":
                containerAll.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                ivAll.setImageResource(R.drawable.switch_all_selected);
                tvAll.setTextColor(getResources().getColor(R.color.white));
                break;
            case "gas":// 气化站
                containerGas.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                ivGas.setImageResource(R.drawable.avatar_gas_offline);
                tvGas.setTextColor(getResources().getColor(R.color.white));
                break;
            case "oil":// 加油站
                containerOil.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                ivOil.setImageResource(R.drawable.avatar_oil_offline);
                tvOil.setTextColor(getResources().getColor(R.color.white));
                break;
            case "cng":
                containerCng.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                ivCng.setImageResource(R.drawable.avatar_cng_offline);
                tvCng.setTextColor(getResources().getColor(R.color.white));
                break;
            case "lng":
                containerLng.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                ivLng.setImageResource(R.drawable.avatar_lng_offline);
                tvLng.setTextColor(getResources().getColor(R.color.white));
                break;
            case "l-cng":
                containerLcng.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                ivLcng.setImageResource(R.drawable.avatar_lcng_offline);
                tvLcng.setTextColor(getResources().getColor(R.color.white));
                break;
            case "charging":
                containerCharging.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                ivCharging.setImageResource(R.drawable.avatar_charging_offline);
                tvCharging.setTextColor(getResources().getColor(R.color.white));
                break;
            case "factory":
                containerFactory.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                ivFactory.setImageResource(R.drawable.avatar_factory_offline);
                tvFactory.setTextColor(getResources().getColor(R.color.white));
                break;
            case "ship":
                containerShip.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                ivShip.setImageResource(R.drawable.avatar_ship_offline);
                tvShip.setTextColor(getResources().getColor(R.color.white));
                break;
            case "h2":
                containerHydrogen.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                ivHydrogen.setImageResource(R.drawable.hydrogen_offline);
                tvHydrogen.setTextColor(getResources().getColor(R.color.white));
                break;
            default:
                break;
        }

        final PopupWindow popupWindow = new PopupWindow(contentView,
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);

        containerAll.setOnClickListener(view -> {
            stationType = "all";
            popupWindow.dismiss();
            getStations();
            ivStatus.setImageResource(R.drawable.switch_all_default);
        });
        containerGas.setOnClickListener(view -> {
            stationType = "gas";
            popupWindow.dismiss();
            getStations();
            ivStatus.setImageResource(R.drawable.avatar_gas_type);
        });
        containerOil.setOnClickListener(view -> {
            stationType = "oil";
            popupWindow.dismiss();
            getStations();
            ivStatus.setImageResource(R.drawable.avatar_oil_type);
        });
        containerCng.setOnClickListener(view -> {
            stationType = "cng";
            popupWindow.dismiss();
            getStations();
            ivStatus.setImageResource(R.drawable.avatar_cng_type);
        });
        containerLng.setOnClickListener(view -> {
            stationType = "lng";
            popupWindow.dismiss();
            getStations();
            ivStatus.setImageResource(R.drawable.avatar_lng_type);
        });
        containerLcng.setOnClickListener(view -> {
            stationType = "l-cng";
            popupWindow.dismiss();
            getStations();
            ivStatus.setImageResource(R.drawable.avatar_lcng_type);
        });
        containerCharging.setOnClickListener(view -> {
            stationType = "charging";
            popupWindow.dismiss();
            getStations();
            ivStatus.setImageResource(R.drawable.avatar_charging_type);
        });
        containerFactory.setOnClickListener(view -> {
            stationType = "factory";
            popupWindow.dismiss();
            getStations();
            ivStatus.setImageResource(R.drawable.avatar_factory_type);
        });
        containerShip.setOnClickListener(view -> {
            stationType = "ship";
            popupWindow.dismiss();
            getStations();
            ivStatus.setImageResource(R.drawable.avatar_ship_type);
        });
        containerHydrogen.setOnClickListener(view -> {
            stationType = "h2";
            popupWindow.dismiss();
            getStations();
            ivStatus.setImageResource(R.drawable.hydrogen_default);
        });
        // 设置好参数之后再show
        popupWindow.showAsDropDown(ivStatus);
        // 设置背景变暗
//        final WindowManager.LayoutParams lp = getActivity().getWindow().getAttributes();
//        lp.alpha = 0.7f;
//        getActivity().getWindow().setAttributes(lp);
//        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
//            @Override
//            public void onDismiss() {
//                // popupwindow关闭时背景还原
////                lp.alpha = 1f;
////                getActivity().getWindow().setAttributes(lp);
//            }
//        });
    }
}
