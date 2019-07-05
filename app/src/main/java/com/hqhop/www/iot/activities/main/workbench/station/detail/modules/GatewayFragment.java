package com.hqhop.www.iot.activities.main.workbench.station.detail.modules;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.geocoder.GeocodeResult;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.RegeocodeAddress;
import com.amap.api.services.geocoder.RegeocodeQuery;
import com.amap.api.services.geocoder.RegeocodeResult;
import com.hqhop.www.iot.R;
import com.hqhop.www.iot.activities.main.workbench.station.detail.DetailStationActivity;
import com.hqhop.www.iot.base.util.CommonUtils;
import com.hqhop.www.iot.base.view.RoundProgressBar;
import com.hqhop.www.iot.bean.AllEquipmentsBean;
import com.hqhop.www.iot.bean.GatewayBean;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.footer.BallPulseFooter;
import com.scwang.smartrefresh.layout.header.BezierRadarHeader;
import com.umeng.analytics.MobclickAgent;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by allen on 09/27/2017.
 */

public class GatewayFragment extends Fragment implements GeocodeSearch.OnGeocodeSearchListener {

    private String TAG = "GatewayFragment";

    private View rootView;

//    private SmartRefreshLayout refreshLayout;

    private TextView tvStation, tvLinks, tvStatus, tvSIM, tvDate, tvGPS, tvVersion, tvHardwareVersion, tvProtocolVersion;

    private RoundProgressBar rpbCPU, rpbDisk, rpbMemory, rpbCellData;

    private boolean isFirst = true;

    private DetailStationActivity context;

    private GatewayBean gatewayBean;

    private GeocodeSearch geocodeSearch;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_gateway, container, false);

        context = (DetailStationActivity) getActivity();

        geocodeSearch = new GeocodeSearch(context);
        geocodeSearch.setOnGeocodeSearchListener(this);

//        refreshLayout = rootView.findViewById(R.id.refresh_layout_gateway);
//        refreshLayout.setRefreshHeader(new BezierRadarHeader(context).setPrimaryColor(getResources().getColor(R.color.colorPrimary)));
//        refreshLayout.setRefreshFooter(new BallPulseFooter(context));
//        refreshLayout.setOnRefreshListener(refreshLayout -> {
//            fetchData();
//        });

        tvStation = rootView.findViewById(R.id.tv_station_gateway);
        tvLinks = rootView.findViewById(R.id.tv_link_gateway);
        tvStatus = rootView.findViewById(R.id.tv_status_gateway);
        tvSIM = rootView.findViewById(R.id.tv_sim_gateway);
        tvDate = rootView.findViewById(R.id.tv_date_gateway);
        tvGPS = rootView.findViewById(R.id.tv_gps_gateway);
        tvVersion = rootView.findViewById(R.id.tv_version_gateway);
        tvHardwareVersion = rootView.findViewById(R.id.tv_hardware_version_gateway);
        tvProtocolVersion = rootView.findViewById(R.id.tv_protocol_version_gateway);

//        tvStation.setText(DetailStationActivity.stationName);
////        tvLinks.setText("5个");
//        tvLinks.setText(((int) (Math.random() * 10)) + "个");
//        tvStatus.setText(getString(R.string.actived));
//        tvSIM.setText("18696914606");
//        tvDate.setText(CommonUtils.timestampToDate(System.currentTimeMillis()));
//        tvGPS.setText(getString(R.string.hqhp_dzgs_address));
//        tvVersion.setText("V1.3");
//        tvHardwareVersion.setText("V1.3");
//        tvProtocolVersion.setText("V1.3");

        rpbCPU = rootView.findViewById(R.id.rpb_cpu);
        rpbDisk = rootView.findViewById(R.id.rpb_disk);
        rpbMemory = rootView.findViewById(R.id.rpb_memory);
        rpbCellData = rootView.findViewById(R.id.rpb_celldata);
//        rpbCPU.setProgress(((int) (Math.random() * 100)));
//        rpbDisk.setProgress(((int) (Math.random() * 100)));
//        rpbMemory.setProgress(((int) (Math.random() * 100)));
//        rpbCellData.setProgress(((int) (Math.random() * 100)));

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart(TAG);
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd(TAG);
    }

    private void fetchData() {
        context.stationDetailService.getGatewayInfo(DetailStationActivity.stationId)
                .subscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<GatewayBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        CommonUtils.showToast(getResources().getString(R.string.network_error));
                    }

                    @Override
                    public void onNext(GatewayBean bean) {
                        if (bean.isSuccess()) {
                            gatewayBean = bean;
                            setData();
                        }
                    }
                });
    }

    private void setData() {
        tvStation.setText(TextUtils.isEmpty(gatewayBean.getData().getStationName()) ? getString(R.string.unknown) : gatewayBean.getData().getStationName());
        tvLinks.setText(String.valueOf(gatewayBean.getData().getDeviceCount()));
        if (gatewayBean.getData().getStatus() == 1) {
            // 未激活
            tvStatus.setText(getString(R.string.inactived));
        } else {
            tvStatus.setText(getString(R.string.actived));
        }
        int cpu, disk, mem, data;
        try {
            cpu = Integer.parseInt(gatewayBean.getData().getCpu());
        } catch (NumberFormatException e) {
            cpu = 0;
            e.printStackTrace();
        }
        try {
            disk = Integer.parseInt(gatewayBean.getData().getDisk());
        } catch (NumberFormatException e) {
            disk = 0;
            e.printStackTrace();
        }
        try {
            mem = Integer.parseInt(gatewayBean.getData().getMemory());
        } catch (NumberFormatException e) {
            mem = 0;
            e.printStackTrace();
        }
        try {
            data = Integer.parseInt(gatewayBean.getData().getFlow());
        } catch (NumberFormatException e) {
            data = 0;
            e.printStackTrace();
        }
        rpbCPU.setProgress(cpu);
        rpbDisk.setProgress(disk);
        rpbMemory.setProgress(mem);
        rpbCellData.setProgress(data);
        tvSIM.setText(TextUtils.isEmpty(gatewayBean.getData().getSimSn()) ? getString(R.string.unknown) : gatewayBean.getData().getSimSn());
        tvDate.setText(TextUtils.isEmpty(gatewayBean.getData().getValidityPeriod()) ? getString(R.string.unknown) : gatewayBean.getData().getValidityPeriod());
        String address;
        if (TextUtils.isEmpty(gatewayBean.getData().getAddress())) {
            if (TextUtils.isEmpty(gatewayBean.getData().getLongitude()) || TextUtils.isEmpty(gatewayBean.getData().getLatitude())) {
                address = getString(R.string.unknown);
            } else {
                // 根据经纬度获取地址信息
                RegeocodeQuery query = new RegeocodeQuery(new LatLonPoint(Double.parseDouble(gatewayBean.getData().getLatitude()), Double.parseDouble(gatewayBean.getData().getLongitude())), 200, GeocodeSearch.AMAP);
                geocodeSearch.getFromLocationAsyn(query);
            }
        } else {
            address = gatewayBean.getData().getAddress();
        }
        tvGPS.setText(TextUtils.isEmpty(gatewayBean.getData().getAddress()) ? getString(R.string.unknown) : gatewayBean.getData().getAddress());
        tvVersion.setText(TextUtils.isEmpty(gatewayBean.getData().getSoftwareVersion()) ? getString(R.string.unknown) : gatewayBean.getData().getSoftwareVersion());
        tvHardwareVersion.setText(TextUtils.isEmpty(gatewayBean.getData().getHardwareVersion()) ? getString(R.string.unknown) : gatewayBean.getData().getHardwareVersion());
        tvProtocolVersion.setText(TextUtils.isEmpty(gatewayBean.getData().getProtocolVersion()) ? getString(R.string.unknown) : gatewayBean.getData().getProtocolVersion());
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && isFirst) {
            isFirst = false;
            fetchData();
        }
    }

    @Override
    public void onRegeocodeSearched(RegeocodeResult regeocodeResult, int i) {
        RegeocodeAddress regeocodeAddress = regeocodeResult.getRegeocodeAddress();
        String formatAddress = regeocodeAddress.getFormatAddress();
//        simpleAddress = formatAddress.substring(9);
//        tvChoseAddress.setText("查询经纬度对应详细地址：\n" + simpleAddress);
        if (TextUtils.isEmpty(formatAddress)) {
            tvGPS.setText(getString(R.string.unknown));
        } else {
            tvGPS.setText(formatAddress);
        }
    }

    @Override
    public void onGeocodeSearched(GeocodeResult geocodeResult, int i) {

    }
}
