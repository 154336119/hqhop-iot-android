package com.hqhop.www.iot.activities.main.workbench;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.hqhop.www.iot.App;
import com.hqhop.www.iot.R;
import com.hqhop.www.iot.activities.main.MainActivity;
import com.hqhop.www.iot.activities.main.workbench.alert.detail.DetailAlertActivity;
import com.hqhop.www.iot.activities.main.workbench.event.AllEventsActivity;
import com.hqhop.www.iot.activities.main.workbench.station.detail.DetailStationActivity;
import com.hqhop.www.iot.activities.main.workbench.station.type.NewStationTypeActivity;
import com.hqhop.www.iot.api.workbench.WorkbenchService;
import com.hqhop.www.iot.base.Common;
import com.hqhop.www.iot.base.adapter.EventsGridViewAdapter;
import com.hqhop.www.iot.base.adapter.OverviewGridViewAdapter;
import com.hqhop.www.iot.base.http.RetrofitManager;
import com.hqhop.www.iot.base.util.CommonUtils;
import com.hqhop.www.iot.base.util.ToolbarUtils;
import com.hqhop.www.iot.bean.EventsBean;
import com.hqhop.www.iot.bean.WorkbenchBean;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.footer.BallPulseFooter;
import com.scwang.smartrefresh.layout.header.BezierRadarHeader;
import com.umeng.analytics.MobclickAgent;
import com.yongchun.library.utils.ScreenUtils;

import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by allen on 2017/9/10.
 */

public class NewWorkbenchFragment extends Fragment implements View.OnClickListener {

    private String TAG = "NewWorkbenchFragment";

    private Context mContext;

    private View rootView;

    private Toolbar toolbar;

    private TextView titleToolbar;

    private TextView toolbarBadge;

//    private LongPressView lpvServer;

    //    private LongPressView messageToolbar;
    private ImageView messageToolbar;

    // 显示PopupWindow的载体
    private View viewBasePopupWindow;

    private SmartRefreshLayout refreshLayout;

    private RelativeLayout containerSingleStation;

    private ImageView ivSingle;

    private String singleStationType;

    private TextView tvOnlineSingle, tvTotalSingle, tvStationSingle;

    // 总览
    private GridView overviewGridView;

    private OverviewGridViewAdapter overviewAdapter;

    private List<String> overviewIconIds;

    private List<String> overviewTypes;

    private List<String> overviewOnlines;

    private List<String> overviewTotals;

    private List<Integer> overviewAlarms;

    private RelativeLayout containerNoData;

    private TextView tvNodata;

    // 事件列表
    private LinearLayout containerEvents;

    private Spinner spinnerEvents;

    private GridView eventsGridView;

    private EventsGridViewAdapter eventsAdapter;

    private String eventType = "all";

    /**
     * 事件站点id
     */
    private List<String> eventStationIds;

    /**
     * 事件站点名称
     */
    private List<String> eventStations;

    /**
     * 事件站点内容
     */
    private List<String> eventContents;

    /**
     * 事件出现日期
     */
    private List<String> eventDates;

    /**
     * 事件类型
     */
    private List<String> eventScenes;

    /**
     * 暂无数据
     */
//    private RelativeLayout containerNoData;

    private WorkbenchService workbenchService;

    public WorkbenchBean workbenchBean;

    private EventsBean eventsBean;

    private boolean finishedFetchingOverview = true, finishedFetchingEvents = true;

    private static NewWorkbenchFragment workbenchFragment;

    public NewWorkbenchFragment() {
    }

    @SuppressLint("ValidFragment")
    private NewWorkbenchFragment(Context context) {
        mContext = context;
    }

    public static NewWorkbenchFragment getInstance(Context context) {
        if (workbenchFragment == null) {
            workbenchFragment = new NewWorkbenchFragment(context);
        }
        return workbenchFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);

//        if (rootView == null) {
        rootView = inflater.inflate(R.layout.fragment_new_workbench, container, false);
//        }

        toolbar = rootView.findViewById(R.id.toolbar);
        titleToolbar = rootView.findViewById(R.id.title_toolbar);
        toolbarBadge = rootView.findViewById(R.id.badge_toolbar);
        messageToolbar = rootView.findViewById(R.id.message_toolbar);

        workbenchService = RetrofitManager.getInstance(getActivity()).createService(WorkbenchService.class);

        viewBasePopupWindow = rootView.findViewById(R.id.view_base_popupwindow_workbench);

        refreshLayout = rootView.findViewById(R.id.refresh_layout_workbench);
        refreshLayout.setRefreshHeader(new BezierRadarHeader(mContext).setPrimaryColor(getResources().getColor(R.color.colorPrimary)));
        refreshLayout.setRefreshFooter(new BallPulseFooter(mContext));
        refreshLayout.setOnRefreshListener(refreshLayout -> fetchData());

        containerSingleStation = rootView.findViewById(R.id.container_single_station_workbench);
        ivSingle = rootView.findViewById(R.id.iv_single_title_workbench);
        tvOnlineSingle = rootView.findViewById(R.id.tv_online_single_station_workbench);
        tvTotalSingle = rootView.findViewById(R.id.tv_total_single_station_workbench);
        tvStationSingle = rootView.findViewById(R.id.tv_station_single_station_workbench);
        tvStationSingle.setOnClickListener(view -> {
            Intent stationTypeListIntent = new Intent(mContext, NewStationTypeActivity.class);
            stationTypeListIntent.putExtra(Common.TYPE_STATION, singleStationType);
            stationTypeListIntent.putExtra("online", workbenchBean.getData().get(0).getStationsOnlineNumber());
            stationTypeListIntent.putExtra("total", workbenchBean.getData().get(0).getStationsNumber());
            startActivity(stationTypeListIntent);
        });

        containerEvents = rootView.findViewById(R.id.title_events_workbench);
        containerEvents.setOnClickListener(this);
        spinnerEvents = rootView.findViewById(R.id.spinner_events_type_workbench);
        spinnerEvents.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                switch (position) {
                    case 0:
                        eventType = "all";
                        fetchData();
                        break;
                    case 1:
                        eventType = "alarm";
//                        eventType = "报警";
                        fetchData();
                        break;
                    case 2:
                        eventType = "alarm_clear";
//                        eventType = "报警清除";
                        fetchData();
                        break;
                    case 3:
                        eventType = "scene";
                        fetchData();
                        break;
                    case 4:
                        eventType = "online";
//                        eventType = "上线";
                        fetchData();
                        break;
                    case 5:
                        eventType = "offline";
//                        eventType = "离线";
                        fetchData();
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        eventsGridView = rootView.findViewById(R.id.gridview_events_workbench);
//        View emptyView = inflater.inflate(R.layout.layout_empty, null);
//        ((ViewGroup) eventsGridView.getParent()).addView(emptyView);// 将EmptyView添加到当前的View Hierarchy中，否则不生效
//        eventsGridView.setEmptyView(emptyView);
        eventStationIds = new ArrayList<>();
        eventStations = new ArrayList<>();
        eventContents = new ArrayList<>();
        eventDates = new ArrayList<>();
        eventScenes = new ArrayList<>();
        eventsAdapter = new EventsGridViewAdapter(mContext, eventStationIds, eventStations, eventContents, eventDates, eventScenes);
        eventsGridView.setAdapter(eventsAdapter);
        eventsGridView.setOnItemClickListener((adapterView, view, position, id) -> {
            if (eventsBean.getData().get(position).getCategory().equals("alarm")) {
                Intent detailAlertIntent = new Intent(getActivity(), DetailAlertActivity.class);
                detailAlertIntent.putExtra("stationId", eventsBean.getData().get(position).getStationId());
                detailAlertIntent.putExtra("parameterId", eventsBean.getData().get(position).getParameterId());
                startActivity(detailAlertIntent);
            } else {
                String stationId = eventStationIds.get(position);
                String stationName = eventStations.get(position);
                Intent detailIntent = new Intent(mContext, DetailStationActivity.class);
                detailIntent.putExtra("type", 1);
                detailIntent.putExtra("id", stationId);
                detailIntent.putExtra("name", stationName);
                detailIntent.putExtra("leaderName", eventsBean.getData().get(position).getLeaderName());
                detailIntent.putExtra("leaderPhone", eventsBean.getData().get(position).getLeaderMobile());
                detailIntent.putExtra("longitude", eventsBean.getData().get(position).getLongitude());
                detailIntent.putExtra("latitude", eventsBean.getData().get(position).getLatitude());
//            if (eventsBean.getData().get(position).getCategory().equals("alarm") || eventsBean.getData().get(position).getCategory().equals("alarm_clear")) {
//                detailIntent.putExtra("showShowAlarm", true);
//            }
                startActivity(detailIntent);
            }
        });

        overviewGridView = rootView.findViewById(R.id.gridview_overview_workbench);
        overviewIconIds = new ArrayList<>();
        overviewTypes = new ArrayList<>();
        overviewOnlines = new ArrayList<>();
        overviewTotals = new ArrayList<>();
        overviewAlarms = new ArrayList<>();
        overviewAdapter = new OverviewGridViewAdapter(mContext, overviewIconIds, overviewTypes, overviewOnlines, overviewTotals, overviewAlarms);
        overviewGridView.setAdapter(overviewAdapter);
        overviewGridView.setOnItemClickListener((parent, view, position, id) -> {
            Intent stationTypeListIntent = new Intent(mContext, NewStationTypeActivity.class);
            stationTypeListIntent.putExtra(Common.TYPE_STATION, overviewTypes.get(position));
            stationTypeListIntent.putExtra("online", workbenchBean.getData().get(position).getStationsOnlineNumber());
            stationTypeListIntent.putExtra("total", workbenchBean.getData().get(position).getStationsNumber());
            startActivity(stationTypeListIntent);
        });
        containerNoData = rootView.findViewById(R.id.container_no_data_workbench);
        tvNodata = rootView.findViewById(R.id.tv_nodata_layout);
        tvNodata.setText(getString(R.string.no_events));

//        lpvServer = rootView.findViewById(R.id.lpv_select_server);
//        lpvServer.setVisibility(View.VISIBLE);
//        lpvServer.setOnLongClickListener(view -> {
//            final AlertDialog alertDialog = new AlertDialog.Builder(mContext).create();
//            alertDialog.show();
//            if (alertDialog.getWindow() != null) {
//                alertDialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
//            }
//            Window window = alertDialog.getWindow();
//            window.setContentView(R.layout.layout_dialog_select_server);
//            EditText mEtAddress = window.findViewById(R.id.et_address_select_server);
//            RadioGroup mRg = window.findViewById(R.id.rg_select_server);
//            RadioButton mRbDebug = window.findViewById(R.id.rb_debug_select_server);
//            RadioButton mRbRelease = window.findViewById(R.id.rb_release_select_server);
//            TextView ok = window.findViewById(R.id.tv_position);
//            TextView cancel = window.findViewById(R.id.tv_negative);
//            SharedPreferences sharedPreferences = mContext.getSharedPreferences("url_config", Context.MODE_PRIVATE);
//            SharedPreferences.Editor editor = sharedPreferences.edit();
//            String baseUrl = sharedPreferences.getString("BASE_URL", Common.BASE_URL);
//            mEtAddress.setText(baseUrl);
//            mEtAddress.addTextChangedListener(new TextWatcher() {
//                @Override
//                public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {
//
//                }
//
//                @Override
//                public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
//
//                }
//
//                @Override
//                public void afterTextChanged(Editable editable) {
//                    String text = editable.toString();
//                    if (!text.equals(Common.BASE_DEBUG_URL) && !text.equals(Common.BASE_RELEASE_URL) && (mRbDebug.isChecked() || mRbRelease.isChecked())) {
//                        mRbDebug.setChecked(false);
//                        mRbRelease.setChecked(false);
//                        mEtAddress.requestFocus();
////                            mEtAddress.setSelection(text.length());
//                    }
//                }
//            });
//            mRg.setOnCheckedChangeListener((radioGroup, id) -> {
//                switch (id) {
//                    case R.id.rb_debug_select_server:
//                        mEtAddress.setText(Common.BASE_DEBUG_URL);
//                        break;
//                    case R.id.rb_release_select_server:
//                        mEtAddress.setText(Common.BASE_RELEASE_URL);
//                        break;
//                }
//            });
//            cancel.setOnClickListener(view1 -> alertDialog.dismiss());
//            ok.setOnClickListener(view12 -> {
//                if (!TextUtils.isEmpty(mEtAddress.getText().toString())) {
//                    editor.putString("BASE_URL", mEtAddress.getText().toString().trim());
//                    editor.apply();
//                    Common.BASE_URL = mEtAddress.getText().toString();
//                    alertDialog.dismiss();
//                    Toast.makeText(mContext, getString(R.string.have_set_server_address_as) + mEtAddress.getText().toString(), Toast.LENGTH_LONG).show();
//                } else {
//                    CommonUtils.showToast(getString(R.string.input_server_address));
//                }
//            });
//            return true;
//        });

        setupToolbar();

        fetchData();

        return rootView;
    }

//    @Override
//    public void onResume() {
//        super.onResume();
//        MobclickAgent.onPageStart(TAG);
//    }
//
//    @Override
//    public void onPause() {
//        super.onPause();
//        MobclickAgent.onPageEnd(TAG);
//    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            MobclickAgent.onPageStart(TAG);
        } else {
            MobclickAgent.onPageEnd(TAG);
        }
    }

    /**
     * 关闭刷新动画
     */
    private void finishRefresh() {
        if (finishedFetchingOverview && finishedFetchingEvents) {
            if (refreshLayout.isRefreshing()) {
                refreshLayout.finishRefresh(true);
            }
            CommonUtils.hideProgressDialog(MainActivity.dialog);
        }
    }

    private void setupToolbar() {
        ToolbarUtils.setCustomToolbar(mContext, toolbar);
        ToolbarUtils.setTitle(titleToolbar, getString(R.string.title_workbench));
//        messageToolbar.setVisibility(View.VISIBLE);
//        messageToolbar.setOnClickListener(view -> {
////            Intent notificationIntent = new Intent(mContext, NotificationActivity.class);
//            Intent notificationIntent = new Intent(mContext, MessageWebActivity.class);
//            startActivity(notificationIntent);
//        });
    }

    /**
     * 获取、刷新数据
     */
    private void fetchData() {
        CommonUtils.showProgressDialogWithMessage(getActivity(), MainActivity.dialog, null);
        fetchOverview();
        fetchEvents();
    }

    /**
     * 请求总览信息
     */
    private void fetchOverview() {
        finishedFetchingOverview = false;
        workbenchService.getModuleType(App.userid)
                .subscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<WorkbenchBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
//                        CommonUtils.showToast("网络错误");
                        finishedFetchingOverview = true;
                        finishRefresh();
                    }

                    @Override
                    public void onNext(WorkbenchBean bean) {
                        finishedFetchingOverview = true;
                        finishRefresh();
                        overviewIconIds.clear();
                        overviewTypes.clear();
                        overviewOnlines.clear();
                        overviewTotals.clear();
                        overviewAlarms.clear();
                        workbenchBean = bean;
                        if (bean.isSuccess() && !bean.getData().isEmpty()) {
                            setOverviewData();
                        }
                    }
                });
    }

    /**
     * 设置、显示、更新总览信息
     */
    private void setOverviewData() {
        if (workbenchBean.getData().size() == 1) {
            overviewGridView.setVisibility(View.GONE);
            containerSingleStation.setVisibility(View.VISIBLE);
            tvOnlineSingle.setText(String.valueOf(workbenchBean.getData().get(0).getStationsOnlineNumber()));
            tvTotalSingle.setText(String.valueOf(workbenchBean.getData().get(0).getStationsNumber()));
            switch (workbenchBean.getData().get(0).getStationType().toLowerCase()) {
                case "gas":// 气化站
//                    viewHolder.ivStation.setImageResource(R.drawable.avatar_gas);
                    tvStationSingle.setText(getString(R.string.gasify_station));
                    ivSingle.setImageResource(R.drawable.avatar_gas_type);
                    singleStationType = "gas";
                    break;
                case "oil":// 油库
//                    viewHolder.ivStation.setImageResource(R.drawable.avatar_oil);
                    tvStationSingle.setText(getString(R.string.gasonline_station));
                    ivSingle.setImageResource(R.drawable.avatar_oil_type);
                    singleStationType = "oil";
                    break;
                case "cng":
//                    viewHolder.ivStation.setImageResource(R.drawable.avatar_cng);
//                    tvStationSingle.setText(getString(R.string.cng_station));
                    tvStationSingle.setText(getString(R.string.gas_station));
                    ivSingle.setImageResource(R.drawable.avatar_cng_station_type);
                    singleStationType = "cng";
                    break;
                case "lng":
//                    viewHolder.ivStation.setImageResource(R.drawable.avatar_lng);
//                    tvStationSingle.setText(getString(R.string.lng_station));
                    tvStationSingle.setText(getString(R.string.gas_station));
                    ivSingle.setImageResource(R.drawable.avatar_lng_station_type);
                    singleStationType = "lng";
                    break;
                case "l-cng":
//                    viewHolder.ivStation.setImageResource(R.drawable.avatar_lcng);
//                    tvStationSingle.setText(getString(R.string.lcng_station));
                    tvStationSingle.setText(getString(R.string.gas_station));
                    ivSingle.setImageResource(R.drawable.avatar_lcng_station_type);
                    singleStationType = "l-cng";
                    break;
                case "charging":
//                    viewHolder.ivStation.setImageResource(R.drawable.avatar_charging);
                    tvStationSingle.setText(getString(R.string.charging_station));
                    ivSingle.setImageResource(R.drawable.avatar_charging_type);
                    singleStationType = "charging";
                    break;
                case "factory":
//                    viewHolder.ivStation.setImageResource(R.drawable.avatar_factory);
                    tvStationSingle.setText(getString(R.string.factory));
                    ivSingle.setImageResource(R.drawable.avatar_factory_type);
                    singleStationType = "factory";
                    break;
                case "ship":
//                    viewHolder.ivStation.setImageResource(R.drawable.avatar_ship);
                    tvStationSingle.setText(getString(R.string.ship));
                    ivSingle.setImageResource(R.drawable.avatar_ship_type);
                    singleStationType = "ship";
                    break;
                case "h2":
//                    viewHolder.ivStation.setImageResource(R.drawable.avatar_gas);
                    tvStationSingle.setText(getString(R.string.hydrogen));
                    ivSingle.setImageResource(R.drawable.avatar_hydrogen_type);
                    singleStationType = "h2";
                    break;
                default:
//                    viewHolder.ivStation.setImageResource(R.mipmap.ic_launcher_round);
                    tvStationSingle.setText(getString(R.string.other));
                    ivSingle.setImageResource(R.mipmap.ic_launcher);
                    singleStationType = "unknown";
                    break;
            }
        } else if (workbenchBean.getData().size() > 1) {
            overviewGridView.setVisibility(View.VISIBLE);
            containerSingleStation.setVisibility(View.GONE);

            int size = workbenchBean.getData().size();
            if (size == 2) {
                overviewGridView.setNumColumns(2);
            } else {
                overviewGridView.setNumColumns(3);
                ViewGroup.LayoutParams layoutParams = overviewGridView.getLayoutParams();
                layoutParams.height = ScreenUtils.dip2px(mContext, 200);
                overviewGridView.setLayoutParams(layoutParams);
            }

            for (int i = 0; i < size; i++) {
                overviewIconIds.add(String.valueOf(workbenchBean.getData().get(i).getStationImage()));
                overviewTypes.add(workbenchBean.getData().get(i).getStationType());
                overviewOnlines.add(String.valueOf(workbenchBean.getData().get(i).getStationsOnlineNumber()));
                overviewTotals.add(String.valueOf(workbenchBean.getData().get(i).getStationsNumber()));
                overviewAlarms.add(workbenchBean.getData().get(i).getStationsAlarmNumber());
//                overviewAlarms.add(2);
            }
            overviewAdapter.notifyDataSetChanged();
        }
    }

    private void fetchEvents() {
        finishedFetchingEvents = false;
        workbenchService.getEventsMessage(App.userid, 10, eventType)
                .subscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<EventsBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        finishedFetchingEvents = true;
                        finishRefresh();
                        containerNoData.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onNext(EventsBean bean) {
                        finishedFetchingEvents = true;
                        finishRefresh();
                        eventStationIds.clear();
                        eventStations.clear();
                        eventContents.clear();
                        eventDates.clear();
                        eventScenes.clear();
                        eventsBean = bean;
                        eventsAdapter.notifyDataSetChanged();
                        if (bean.isSuccess() && !bean.getData().isEmpty()) {
                            setEvents();
                            containerNoData.setVisibility(View.GONE);
                        } else {
                            containerNoData.setVisibility(View.VISIBLE);
                        }
                    }
                });
    }

    private void setEvents() {
        finishedFetchingEvents = true;
        for (int i = 0; i < eventsBean.getData().size(); i++) {
            eventStationIds.add(eventsBean.getData().get(i).getStationId());
            eventStations.add(eventsBean.getData().get(i).getStationName());
            eventContents.add(eventsBean.getData().get(i).getContent());
            eventDates.add(eventsBean.getData().get(i).getTime());
            eventScenes.add(eventsBean.getData().get(i).getCategory());
        }
        eventsAdapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.title_events_workbench:
                Intent allEventsIntent = new Intent(mContext, AllEventsActivity.class);
                startActivity(allEventsIntent);
                break;
            default:
                break;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
//        workbenchFragment = null;
    }

    public void destroySelf() {
        workbenchFragment = null;
    }
}
