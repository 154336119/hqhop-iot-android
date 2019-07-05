package com.hqhop.www.iot.activities.main.workbench.station.type;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.arlib.floatingsearchview.FloatingSearchView;
import com.arlib.floatingsearchview.suggestions.SearchSuggestionsAdapter;
import com.arlib.floatingsearchview.suggestions.model.SearchSuggestion;
import com.blankj.utilcode.util.KeyboardUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.hqhop.www.iot.App;
import com.hqhop.www.iot.R;
import com.hqhop.www.iot.activities.main.workbench.station.all.AllStationActivity;
import com.hqhop.www.iot.activities.main.workbench.station.detail.DetailStationActivity;
import com.hqhop.www.iot.api.workbench.type.StationTypeService;
import com.hqhop.www.iot.base.Common;
import com.hqhop.www.iot.base.adapter.StationListGridViewAdapter;
import com.hqhop.www.iot.base.http.RetrofitManager;
import com.hqhop.www.iot.base.util.CommonUtils;
import com.hqhop.www.iot.base.view.BaseAppCompatActivity;
import com.hqhop.www.iot.bean.SearchStationBean;
import com.hqhop.www.iot.bean.StationTypeBean;
import com.mancj.materialsearchbar.MaterialSearchBar;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.footer.BallPulseFooter;
import com.scwang.smartrefresh.layout.header.BezierRadarHeader;

import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class NewStationTypeActivity extends BaseAppCompatActivity implements View.OnClickListener {

    private Dialog dialog;

    private SmartRefreshLayout refreshLayout;

    private Intent gotIntent;

    private ImageView ivStationType;

    private TextView tvOnline, tvTotal;

    private int pageNumber = 1;

    private int itemsPerPage = 5;

    private boolean isLoadmore = false, isRefresh = false, finishedRefresh = true, finishedLoadmore = true;

    // 显示PopupWindow的载体
    private View viewBasePopupWindow;

    /**
     * 站点类型:gas, oil...
     */
    public static String type;

//    private LinearLayout titleList;

    private TextView tvStationList;

    private TextView tvTypeMore;

    private ImageView ivTypeArrow;

    private TextView tvListMore;

    private ImageView ivListArrow;

    private LinearLayout containerStations;

    private RelativeLayout containerNoData;

    private TextView tvNodata;

    private FloatingSearchView searchBar;

    private GridView listGridView;

    private StationListGridViewAdapter stationListAdapter;

    private List<String> stationListStations;

    private List<Integer> stationListStatuses;

    private List<String> stationListAddresses;

    private List<String> stationListSales;

    private List<Integer> stationListAlerts;

    private List<String> stationListScenes;

    private List<String> stationListTypes;

    private List<String> stationListIds;

    private List<String> stationListLeaderNames;

    private List<String> stationListLeaderMobiles;

    private List<String> stationListLongitudes;

    private List<String> stationListLatitudes;

    private StationTypeService stationTypeService;

    private StationTypeBean stationTypeBean;

    /**
     * 查询参数status：0->全部,1->正常,2->离线
     */
    private int status = 0;

    private int stationTypeSelectedFromPopup = 0;

    private int stationType = 3;

    private boolean finishedFetchingStations = true;

    private SearchStationBean searchStationBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    @SuppressLint("SetTextI18n")
    private void init() {
        gotIntent = getIntent();
        ivStationType = findViewById(R.id.iv_gasify_title_station_type);
        tvOnline = findViewById(R.id.tv_online_station_type);
        tvTotal = findViewById(R.id.tv_total_station_type);
//        tvStationType = findViewById(R.id.tv_station_single_station_type);
        tvOnline.setText(gotIntent.getIntExtra("online", 0) + "");
        tvTotal.setText(gotIntent.getIntExtra("total", 0) + "");
        type = gotIntent.getStringExtra(Common.TYPE_STATION);
        switch (type.toLowerCase()) {
            case "gas":// 气化站
                setToolBarTitle(getString(R.string.gasify_station));
//                setToolBarTitle("");
//                tvStationType.setText("气化站");
                ivStationType.setImageResource(R.drawable.avatar_gas_type);
                break;
            case "oil":// 加油站
                setToolBarTitle(getString(R.string.gasonline_station));
//                setToolBarTitle("");
//                tvStationType.setText("加油站");
                ivStationType.setImageResource(R.drawable.avatar_oil_type);
                break;
            case "cng":
//                setToolBarTitle(getString(R.string.cng_station));
                setToolBarTitle(getString(R.string.gas_station));
//                setToolBarTitle("");
//                tvStationType.setText("CNG站");
                ivStationType.setImageResource(R.drawable.avatar_cng_station_type);
                break;
            case "lng":
                setToolBarTitle(getString(R.string.lng_station));
//                setToolBarTitle("");
//                tvStationType.setText("LNG站");
                ivStationType.setImageResource(R.drawable.avatar_lng_station_type);
                break;
            case "l-cng":
                setToolBarTitle(getString(R.string.lcng_station));
//                setToolBarTitle("");
//                tvStationType.setText("L-CNG站");
                ivStationType.setImageResource(R.drawable.avatar_lcng_station_type);
                break;
            case "charging":
                setToolBarTitle(getString(R.string.charging_station));
//                setToolBarTitle("");
//                tvStationType.setText("充电站");
                ivStationType.setImageResource(R.drawable.avatar_charging_type);
                break;
            case "factory":
                setToolBarTitle(getString(R.string.factory));
//                setToolBarTitle("");
//                tvStationType.setText("工厂");
                ivStationType.setImageResource(R.drawable.avatar_factory_type);
                break;
            case "ship":
                setToolBarTitle(getString(R.string.ship));
//                setToolBarTitle("");
//                tvStationType.setText("船");
                ivStationType.setImageResource(R.drawable.avatar_ship_type);
                break;
            case "h2":
                setToolBarTitle(getString(R.string.hydrogen));
//                setToolBarTitle("");
//                tvStationType.setText("船");
                ivStationType.setImageResource(R.drawable.avatar_hydrogen_type);
                break;
            default:
                setToolBarTitle(getString(R.string.other));
//                setToolBarTitle("");
//                tvStationType.setText("未知站点");
                ivStationType.setImageResource(R.mipmap.ic_launcher);
                break;
        }

        stationTypeService = RetrofitManager.getInstance(this).createService(StationTypeService.class);

        dialog = CommonUtils.createLoadingDialog(this);

        viewBasePopupWindow = findViewById(R.id.view_base_popupwindow_station_type);

        refreshLayout = findViewById(R.id.refresh_layout_station_type);
        refreshLayout.setRefreshHeader(new BezierRadarHeader(this).setPrimaryColor(getResources().getColor(R.color.colorPrimary)));
        refreshLayout.setRefreshFooter(new BallPulseFooter(this));
        // 刷新监听
        refreshLayout.setOnRefreshListener(refreshLayout -> {
            pageNumber = 1;
            isRefresh = true;
            isLoadmore = false;
            finishedRefresh = false;
            fetchData();
        });
        refreshLayout.setOnLoadmoreListener(refreshLayout -> {
            pageNumber += 1;
            isRefresh = false;
            isLoadmore = true;
            finishedLoadmore = false;
            fetchData();
        });

//        titleList = (LinearLayout) findViewById(R.id.title_list_station_type);
        tvStationList = findViewById(R.id.tv_list_station_type);
        tvTypeMore = findViewById(R.id.tv_type_station_type);
        ivTypeArrow = findViewById(R.id.iv_arrow_type_station_type);
        if (type.toLowerCase().equals("cng")) {
            tvTypeMore.setVisibility(View.VISIBLE);
            ivTypeArrow.setVisibility(View.VISIBLE);
        }
        tvListMore = findViewById(R.id.tv_more_list_station_type);
        ivListArrow = findViewById(R.id.iv_arrow_list_station_type);
//        titleList.setOnClickListener(this);
        tvStationList.setOnClickListener(this);
        tvTypeMore.setOnClickListener(this);
        ivTypeArrow.setOnClickListener(this);
        tvListMore.setOnClickListener(this);
        ivListArrow.setOnClickListener(this);
        containerStations = findViewById(R.id.container_list_station_type);
        searchBar = findViewById(R.id.search_bar_station_type);
        searchBar.setOnQueryChangeListener(new FloatingSearchView.OnQueryChangeListener() {
            @Override
            public void onSearchTextChanged(String oldQuery, String newQuery) {
                searchBar.showProgress();
                stationTypeService.searchStationByName(App.userid, type, newQuery.trim())
                        .subscribeOn(Schedulers.io())
                        .subscribeOn(AndroidSchedulers.mainThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Subscriber<SearchStationBean>() {
                            @Override
                            public void onCompleted() {
                            }

                            @Override
                            public void onError(Throwable e) {
                                searchBar.hideProgress();
                            }

                            @Override
                            public void onNext(SearchStationBean bean) {
                                searchStationBean = bean;
                                searchBar.hideProgress();
                                List<SearchStationBean.DataBean> results = new ArrayList<>();
                                for (int i = 0; i < bean.getData().size(); i++) {
                                    results.add(bean.getData().get(i));
                                }
                                searchBar.swapSuggestions(results);
                            }
                        });
            }
        });
        searchBar.setOnBindSuggestionCallback(new SearchSuggestionsAdapter.OnBindSuggestionCallback() {
            @Override
            public void onBindSuggestion(View suggestionView, ImageView leftIcon, TextView textView, SearchSuggestion item, int itemPosition) {

            }
        });
        searchBar.setOnSearchListener(new FloatingSearchView.OnSearchListener() {
            @Override
            public void onSuggestionClicked(SearchSuggestion searchSuggestion) {
                KeyboardUtils.hideSoftInput(NewStationTypeActivity.this);
                Intent intent = new Intent(NewStationTypeActivity.this, DetailStationActivity.class);
                intent.putExtra("type", 2);
                intent.putExtra("id", ((SearchStationBean.DataBean) searchSuggestion).getId() + "");
                startActivity(intent);
            }

            @Override
            public void onSearchAction(String currentQuery) {

            }
        });

        listGridView = findViewById(R.id.gridview_list_station_type);
        containerNoData = findViewById(R.id.container_no_data_workbench);
        tvNodata = findViewById(R.id.tv_nodata_layout);
        tvNodata.setText(getString(R.string.no_station_data));
        stationListStations = new ArrayList<>();
        stationListStatuses = new ArrayList<>();
        stationListAddresses = new ArrayList<>();
        stationListSales = new ArrayList<>();
        stationListAlerts = new ArrayList<>();
        stationListScenes = new ArrayList<>();
        stationListTypes = new ArrayList<>();
        stationListIds = new ArrayList<>();
        stationListLeaderNames = new ArrayList<>();
        stationListLeaderMobiles = new ArrayList<>();
        stationListLongitudes = new ArrayList<>();
        stationListLatitudes = new ArrayList<>();
        stationListAdapter = new StationListGridViewAdapter(this, stationListStations, stationListStatuses, stationListAddresses, stationListSales, stationListAlerts, stationListScenes, stationListTypes);
        listGridView.setAdapter(stationListAdapter);
        listGridView.setOnItemClickListener((parent, view, position, id) -> {
            Intent detailStationIntent = new Intent(NewStationTypeActivity.this, DetailStationActivity.class);
//            Bundle bundle = new Bundle();
//            StationTypeBean.DataBean dataBean = stationTypeBean.getData().get(position);
//            bundle.putParcelable(Common.BUNDLE_STATION, dataBean);
//            detailStationIntent.putExtras(bundle);
            detailStationIntent.putExtra("name", stationListStations.get(position));
            detailStationIntent.putExtra("id", stationListIds.get(position));
            detailStationIntent.putExtra("alarmCount", stationListAlerts.get(position));
            detailStationIntent.putExtra("leaderName", stationListLeaderNames.get(position));
            detailStationIntent.putExtra("leaderPhone", stationListLeaderMobiles.get(position));
            detailStationIntent.putExtra("stationType", stationListTypes.get(position));
            detailStationIntent.putExtra("longitude", stationListLongitudes.get(position));
            detailStationIntent.putExtra("latitude", stationListLatitudes.get(position));
            startActivity(detailStationIntent);
        });

        if (type.toLowerCase().equals("cng")) {
            type = "cng,lng,l-cng";
        }
        fetchData();
    }

    /**
     * 关闭刷新动画
     */
    private void finishRefresh() {
        if (finishedRefresh) {
            if (refreshLayout.isRefreshing()) {
                refreshLayout.finishRefresh(true);
            }
            CommonUtils.hideProgressDialog(dialog);
        }
    }

    private void finishLoadmore() {
        if (finishedLoadmore) {
            if (refreshLayout.isLoading()) {
                refreshLayout.finishLoadmore(true);
            }
            CommonUtils.hideProgressDialog(dialog);
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_new_station_type;
    }

    @Override
    protected boolean isShowBacking() {
        return true;
    }

    /**
     * 获取、刷新数据
     */
    private void fetchData() {
        CommonUtils.showProgressDialogWithMessage(this, dialog, null);
        fetchStations();
    }

    /**
     * 请求站点列表信息
     */
    private void fetchStations() {
        finishedFetchingStations = false;
        stationTypeService.getStations(App.userid, pageNumber, itemsPerPage, type, stationType, 0, status)
                .subscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<StationTypeBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        stationListStations.clear();
                        stationListStatuses.clear();
                        stationListAddresses.clear();
                        stationListSales.clear();
                        stationListAlerts.clear();
                        stationListScenes.clear();
                        stationListTypes.clear();
                        stationListAdapter.notifyDataSetChanged();

                        containerNoData.setVisibility(View.VISIBLE);
                        CommonUtils.showToast(getString(R.string.network_error));
                        CommonUtils.hideProgressDialog(dialog);
                        if (isLoadmore) {
                            pageNumber -= 1;
                        }
                        finishedRefresh = true;
                        finishedLoadmore = true;
                        finishRefresh();
                        finishLoadmore();
                        containerStations.setVisibility(View.GONE);
                    }

                    @Override
                    public void onNext(StationTypeBean bean) {
                        CommonUtils.hideProgressDialog(dialog);
                        finishedRefresh = true;
                        finishedLoadmore = true;
                        finishRefresh();
                        finishLoadmore();

                        stationTypeBean = bean;
                        if (isLoadmore && bean.getData().isEmpty()) {
//                            CommonUtils.showToast("无更多数据");
                            pageNumber -= 1;
                        }
                        if (bean.isSuccess() && !bean.getData().isEmpty()) {
                            setStationListData();
                            containerNoData.setVisibility(View.GONE);
                        } else {
                            if (isRefresh) {
                                stationListIds.clear();
                                stationListStations.clear();
                                stationListStatuses.clear();
                                stationListAddresses.clear();
                                stationListSales.clear();
                                stationListAlerts.clear();
                                stationListScenes.clear();
                                stationListTypes.clear();
                                stationListAdapter.notifyDataSetChanged();
                            }
                            containerNoData.setVisibility(View.VISIBLE);
                        }
                    }
                });
    }

    /**
     * 设置、显示、更新站点列表信息
     */
    private void setStationListData() {
        if (isRefresh) {
            stationListStations.clear();
            stationListStatuses.clear();
            stationListAddresses.clear();
            stationListSales.clear();
            stationListAlerts.clear();
            stationListScenes.clear();
            stationListTypes.clear();
            stationListIds.clear();
            stationListLeaderNames.clear();
            stationListLeaderMobiles.clear();
            stationListLongitudes.clear();
            stationListLatitudes.clear();

            containerStations.setVisibility(View.VISIBLE);
            for (int i = 0; i < stationTypeBean.getData().size(); i++) {
                stationListStations.add(stationTypeBean.getData().get(i).getName());
                stationListStatuses.add(stationTypeBean.getData().get(i).getStatus());
                stationListAddresses.add(stationTypeBean.getData().get(i).getAddress());
                stationListSales.add(String.valueOf(stationTypeBean.getData().get(i).getSaleToday()));
                stationListAlerts.add(stationTypeBean.getData().get(i).getAlarmCount());
                stationListScenes.add(stationTypeBean.getData().get(i).getScene());
                stationListIds.add(String.valueOf(stationTypeBean.getData().get(i).getId()));
                stationListLeaderNames.add(stationTypeBean.getData().get(i).getLeaderName());
                stationListLeaderMobiles.add(stationTypeBean.getData().get(i).getLeaderMobile());
                stationListLongitudes.add(stationTypeBean.getData().get(i).getLongitude());
                stationListLatitudes.add(stationTypeBean.getData().get(i).getLatitude());
                stationListTypes.add(stationTypeBean.getData().get(i).getType());
            }
            stationListAdapter.notifyDataSetChanged();
        } else {
            containerStations.setVisibility(View.VISIBLE);
            for (int i = 0; i < stationTypeBean.getData().size(); i++) {
                stationListStations.add(stationListTypes.size(), stationTypeBean.getData().get(i).getName());
                stationListStatuses.add(stationListTypes.size(), stationTypeBean.getData().get(i).getStatus());
                stationListAddresses.add(stationListTypes.size(), stationTypeBean.getData().get(i).getAddress());
                stationListSales.add(stationListTypes.size(), String.valueOf(stationTypeBean.getData().get(i).getSaleToday()));
                stationListAlerts.add(stationListTypes.size(), stationTypeBean.getData().get(i).getAlarmCount());
                stationListScenes.add(stationListTypes.size(), stationTypeBean.getData().get(i).getScene());
                stationListIds.add(stationListTypes.size(), String.valueOf(stationTypeBean.getData().get(i).getId()));
                stationListLeaderNames.add(stationListTypes.size(), stationTypeBean.getData().get(i).getLeaderName());
                stationListLeaderMobiles.add(stationListTypes.size(), stationTypeBean.getData().get(i).getLeaderMobile());
                stationListLongitudes.add(stationListTypes.size(), stationTypeBean.getData().get(i).getLongitude());
                stationListLatitudes.add(stationListTypes.size(), stationTypeBean.getData().get(i).getLatitude());
                stationListTypes.add(stationListTypes.size(), stationTypeBean.getData().get(i).getType());
            }
            stationListAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_arrow_type_station_type:
                showTypePopupWindow();
                break;
            case R.id.tv_type_station_type:
                showTypePopupWindow();
                break;
            case R.id.iv_arrow_list_station_type:
                showModulesSettingPopupWindow();
                break;
            case R.id.tv_more_list_station_type:
                showModulesSettingPopupWindow();
                break;
            case R.id.tv_list_station_type:
                Intent allStationIntent = new Intent(this, AllStationActivity.class);
                startActivity(allStationIntent);
                break;
            default:
                break;
        }
    }

    /**
     * 显示PopupWindow
     */
    private void showModulesSettingPopupWindow() {
        View contentView = getLayoutInflater().inflate(R.layout.layout_popupwindow_switch_station_type, null);
        LinearLayout containerAll = contentView.findViewById(R.id.container_all_switch_station_type);
        ImageView ivAll = contentView.findViewById(R.id.iv_all_switch_station_type);
        TextView tvAll = contentView.findViewById(R.id.tv_all_switch_station_type);
        ImageView ivAllCheck = contentView.findViewById(R.id.iv_check_all_switch_station_type);

        LinearLayout containerOnline = contentView.findViewById(R.id.container_online_switch_station_type);
        ImageView ivOnline = contentView.findViewById(R.id.iv_online_switch_station_type);
        TextView tvOnline = contentView.findViewById(R.id.tv_online_switch_station_type);
        ImageView ivOnlineCheck = contentView.findViewById(R.id.iv_check_online_switch_station_type);

        LinearLayout containerOffline = contentView.findViewById(R.id.container_offline_switch_station_type);
        ImageView ivOffline = contentView.findViewById(R.id.iv_offline_switch_station_type);
        TextView tvOffline = contentView.findViewById(R.id.tv_offline_switch_station_type);
        ImageView ivOfflineCheck = contentView.findViewById(R.id.iv_check_offline_switch_station_type);

        LinearLayout containerAlarm = contentView.findViewById(R.id.container_alarm_switch_station_type);
        ImageView ivAlarm = contentView.findViewById(R.id.iv_alarm_switch_station_type);
        TextView tvAlarm = contentView.findViewById(R.id.tv_alarm_switch_station_type);
        ImageView ivAlarmCheck = contentView.findViewById(R.id.iv_check_alarm_switch_station_type);

        final PopupWindow popupWindow = new PopupWindow(contentView,
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);

        switch (status) {
            case 0:
                containerAll.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                ivAll.setImageResource(R.drawable.switch_all_selected);
                tvAll.setTextColor(getResources().getColor(R.color.white));
                ivAllCheck.setVisibility(View.VISIBLE);
                break;
            case 1:
                containerOnline.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                ivOnline.setImageResource(R.drawable.switch_online_selected);
                tvOnline.setTextColor(getResources().getColor(R.color.white));
                ivOnlineCheck.setVisibility(View.VISIBLE);
                break;
            case 2:
                containerOffline.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                ivOffline.setImageResource(R.drawable.switch_offline_selected);
                tvOffline.setTextColor(getResources().getColor(R.color.white));
                ivOfflineCheck.setVisibility(View.VISIBLE);
                break;
            case 3:
                containerAlarm.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                ivAlarm.setImageResource(R.drawable.switch_alarm_selected);
                tvAlarm.setTextColor(getResources().getColor(R.color.white));
                ivAlarmCheck.setVisibility(View.VISIBLE);
                break;
            default:
                break;
        }

        containerAll.setOnClickListener(view -> {
            status = 0;
            popupWindow.dismiss();
            isRefresh = true;
            pageNumber = 1;
            tvListMore.setText(getString(R.string.all));
            fetchData();
        });
        containerOnline.setOnClickListener(view -> {
            status = 1;
            popupWindow.dismiss();
            isRefresh = true;
            pageNumber = 1;
            tvListMore.setText(getString(R.string.normal));
            fetchData();
        });
        containerOffline.setOnClickListener(view -> {
            status = 2;
            popupWindow.dismiss();
            isRefresh = true;
            pageNumber = 1;
            tvListMore.setText(getString(R.string.offline));
            fetchData();
        });
        containerAlarm.setOnClickListener(view -> {
            status = 3;
            popupWindow.dismiss();
            isRefresh = true;
            pageNumber = 1;
            tvListMore.setText(getString(R.string.alarm));
            fetchData();
        });
        // 设置好参数之后再show
        popupWindow.showAsDropDown(tvListMore);
        // 设置背景变暗
        final WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = 0.7f;
        getWindow().setAttributes(lp);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                // popupwindow关闭时背景还原
                lp.alpha = 1f;
                getWindow().setAttributes(lp);
            }
        });
    }

    private void showTypePopupWindow() {
        View contentView = getLayoutInflater().inflate(R.layout.layout_popupwindow_switch_gas_station_type, null);
        LinearLayout containerAll = contentView.findViewById(R.id.container_all_switch_gas_station_type);
        ImageView ivAll = contentView.findViewById(R.id.iv_all_switch_gas_station_type);
        TextView tvAll = contentView.findViewById(R.id.tv_all_switch_gas_station_type);
        ImageView ivAllCheck = contentView.findViewById(R.id.iv_check_all_switch_gas_station_type);

        LinearLayout containerCNG = contentView.findViewById(R.id.container_online_switch_gas_station_type);
        ImageView ivCNG = contentView.findViewById(R.id.iv_cng_switch_gas_station_type);
        TextView tvCNG = contentView.findViewById(R.id.tv_cng_switch_gas_station_type);
        ImageView ivCNGCheck = contentView.findViewById(R.id.iv_check_cng_switch_gas_station_type);

        LinearLayout containerLNG = contentView.findViewById(R.id.container_offline_switch_gas_station_type);
        ImageView ivLNG = contentView.findViewById(R.id.iv_lng_switch_gas_station_type);
        TextView tvLNG = contentView.findViewById(R.id.tv_lng_switch_gas_station_type);
        ImageView ivLNGCheck = contentView.findViewById(R.id.iv_check_lng_switch_gas_station_type);

        LinearLayout containerLCNG = contentView.findViewById(R.id.container_alarm_switch_gas_station_type);
        ImageView ivLCNG = contentView.findViewById(R.id.iv_lcng_switch_gas_station_type);
        TextView tvLCNG = contentView.findViewById(R.id.tv_lcng_switch_gas_station_type);
        ImageView ivLCNGCheck = contentView.findViewById(R.id.iv_check_lcng_switch_gas_station_type);

        final PopupWindow popupWindow = new PopupWindow(contentView,
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);

        switch (stationTypeSelectedFromPopup) {
            case 0:
                containerAll.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                ivAll.setImageResource(R.drawable.switch_all_selected);
                tvAll.setTextColor(getResources().getColor(R.color.white));
                ivAllCheck.setVisibility(View.VISIBLE);
                break;
            case 1:
                containerCNG.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                ivCNG.setImageResource(R.drawable.avatar_cng_offline);
                tvCNG.setTextColor(getResources().getColor(R.color.white));
                ivCNGCheck.setVisibility(View.VISIBLE);
                break;
            case 2:
                containerLNG.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                ivLNG.setImageResource(R.drawable.avatar_lng_offline);
                tvLNG.setTextColor(getResources().getColor(R.color.white));
                ivLNGCheck.setVisibility(View.VISIBLE);
                break;
            case 3:
                containerLCNG.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                ivLCNG.setImageResource(R.drawable.avatar_lcng_offline);
                tvLCNG.setTextColor(getResources().getColor(R.color.white));
                ivLCNGCheck.setVisibility(View.VISIBLE);
                break;
            default:
                break;
        }

        containerAll.setOnClickListener(view -> {
            stationTypeSelectedFromPopup = 0;
            popupWindow.dismiss();
            isRefresh = true;
            pageNumber = 1;
            tvTypeMore.setText(getString(R.string.all));
            type = "cng,lng,l-cng";
            fetchData();
        });
        containerCNG.setOnClickListener(view -> {
            stationTypeSelectedFromPopup = 1;
            popupWindow.dismiss();
            isRefresh = true;
            pageNumber = 1;
            tvTypeMore.setText(getString(R.string.cng_station));
            type = "cng";
            fetchData();
        });
        containerLNG.setOnClickListener(view -> {
            stationTypeSelectedFromPopup = 2;
            popupWindow.dismiss();
            isRefresh = true;
            pageNumber = 1;
            tvTypeMore.setText(getString(R.string.lng_station));
            type = "lng";
            fetchData();
        });
        containerLCNG.setOnClickListener(view -> {
            stationTypeSelectedFromPopup = 3;
            popupWindow.dismiss();
            isRefresh = true;
            pageNumber = 1;
            tvTypeMore.setText(getString(R.string.lcng_station));
            type = "l-cng";
            fetchData();
        });
        // 设置好参数之后再show
        popupWindow.showAsDropDown(tvTypeMore);
        // 设置背景变暗
        final WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = 0.7f;
        getWindow().setAttributes(lp);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                // popupwindow关闭时背景还原
                lp.alpha = 1f;
                getWindow().setAttributes(lp);
            }
        });
    }
}
