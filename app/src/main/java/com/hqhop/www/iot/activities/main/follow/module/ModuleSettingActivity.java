package com.hqhop.www.iot.activities.main.follow.module;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hqhop.www.iot.App;
import com.hqhop.www.iot.R;
import com.hqhop.www.iot.activities.main.follow.FollowFragment;
import com.hqhop.www.iot.api.follow.ModuleService;
import com.hqhop.www.iot.base.adapter.ModuleAdapter;
import com.hqhop.www.iot.base.http.RetrofitManager;
import com.hqhop.www.iot.base.util.CommonUtils;
import com.hqhop.www.iot.base.view.BaseAppCompatActivity;
import com.hqhop.www.iot.bean.ModuleBean;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.footer.BallPulseFooter;
import com.scwang.smartrefresh.layout.header.BezierRadarHeader;

import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

//public class ModuleSettingActivity extends BaseAppCompatActivity implements RadioGroup.OnCheckedChangeListener {
public class ModuleSettingActivity extends BaseAppCompatActivity implements AdapterView.OnItemClickListener {

    private Intent gotIntent;

    private Dialog dialog;

    private SmartRefreshLayout refreshLayout;

    public String id;

//    private SegmentedGroup segmentedGroup;
//
//    private RadioButton radioDetail, radioQuick;
//
//    private ViewPager viewPager;
//
//    private ViewPagerAdapter adapter;
//
//    private DetailModuleFragment detailModuleFragment;
//
//    private QuickModuleFragment quickModuleFragment;
//
//    private List<Fragment> fragmentList;

    private GridView shownGridView, notShownGridView;

    private TextView tvRemove;

    private List<String> shownItems, notShownItems, shownItemsIds, notShownItemsIds;

    private ModuleAdapter shownAdapter, notShownAdapter;

    private boolean isInitial;

    public ModuleService moduleService;

    public static ModuleBean moduleBean;

    private boolean finishedFetchingData = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    private void init() {
        setToolBarTitle(getString(R.string.modules_setting));

        dialog = CommonUtils.createLoadingDialog(this);
        refreshLayout = findViewById(R.id.refresh_layout_module_setting);
        refreshLayout.setRefreshHeader(new BezierRadarHeader(this).setPrimaryColor(getResources().getColor(R.color.colorPrimary)));
        refreshLayout.setRefreshFooter(new BallPulseFooter(this));
        // 刷新监听
        refreshLayout.setOnRefreshListener(refreshLayout -> fetchData());
        moduleService = RetrofitManager.getInstance(this).createService(ModuleService.class);

        gotIntent = getIntent();
        id = gotIntent.getStringExtra("id");

        shownGridView = findViewById(R.id.gridview_shown_detail_module);
        notShownGridView = findViewById(R.id.gridview_not_shown_detail_module);

        shownItems = new ArrayList<>();
        notShownItems = new ArrayList<>();
        shownItemsIds = new ArrayList<>();
        notShownItemsIds = new ArrayList<>();
        shownAdapter = new ModuleAdapter(this, shownItems, true);
        notShownAdapter = new ModuleAdapter(this, notShownItems, false);
        shownGridView.setAdapter(shownAdapter);
        notShownGridView.setAdapter(notShownAdapter);

        shownGridView.setOnItemClickListener(this);
        notShownGridView.setOnItemClickListener(this);

//        CommonUtils.showToast(id);

//        segmentedGroup = (SegmentedGroup) findViewById(R.id.segmented_group_module_setting);
//        radioDetail = (RadioButton) findViewById(R.id.radio_detail_module_setting);
//        radioQuick = (RadioButton) findViewById(R.id.radio_quick_module_setting);
//        viewPager = (ViewPager) findViewById(R.id.viewpager_module_setting);
//
//        fragmentList = new ArrayList<>();
//        detailModuleFragment = new DetailModuleFragment();
//        quickModuleFragment = new QuickModuleFragment();
//        Bundle bundle = new Bundle();
//        bundle.putString("id", id);
//        detailModuleFragment.setArguments(bundle);
//        quickModuleFragment.setArguments(bundle);
//        fragmentList.add(detailModuleFragment);
//        fragmentList.add(quickModuleFragment);
//        adapter = new ViewPagerAdapter(getSupportFragmentManager(), fragmentList);
//        viewPager.setOffscreenPageLimit(2);
//        viewPager.setAdapter(adapter);
//        viewPager.setCurrentItem(0);

        tvRemove = findViewById(R.id.tv_remove_follow_detail_module);
        tvRemove.setOnClickListener(view ->
                moduleService.setMudules(App.userid, id, "delete")
                        .subscribeOn(Schedulers.io())
                        .subscribeOn(AndroidSchedulers.mainThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Subscriber<ModuleBean>() {
                            @Override
                            public void onCompleted() {

                            }

                            @Override
                            public void onError(Throwable e) {
//                        CommonUtils.showToast("网络错误");
//                        finishedFetchingData = true;
//                        finishRefresh();
                                finish();
                                FollowFragment.getInstance(ModuleSettingActivity.this).refresh();
                            }

                            @Override
                            public void onNext(ModuleBean bean) {
                                finish();
                                FollowFragment.getInstance(ModuleSettingActivity.this).refresh();
//                        finishedFetchingData = true;
//                        finishRefresh();
////                        alertLevels.clear();
////                        alertStations.clear();
////                        alertReasons.clear();
////                        alertDates.clear();
//                        moduleBean = bean;
//                        detailModuleFragment.setData();
//                        quickModuleFragment.setData();
//                        if (bean.isSuccess() && !bean.getData().isEmpty()) {
//                            setAlertData();
//                        }
                            }
                        }));

        fetchData();

//        segmentedGroup.setOnCheckedChangeListener(this);
    }

    private void fetchData() {
        CommonUtils.showProgressDialogWithMessage(this, dialog, null);
        finishedFetchingData = false;
//        moduleService.getMudulesData(App.userid, id)
//        moduleService.getMudulesData("40288e81567c9e7f01567cc223440007", id)
        moduleService.getMudulesData(App.userid, id)
                .subscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ModuleBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        CommonUtils.showToast(getString(R.string.network_error));
                        finishedFetchingData = true;
                        finishRefresh();
                    }

                    @Override
                    public void onNext(ModuleBean bean) {
                        finishedFetchingData = true;
                        finishRefresh();
//                        alertLevels.clear();
//                        alertStations.clear();
//                        alertReasons.clear();
//                        alertDates.clear();
                        moduleBean = bean;
                        setData();
//                        detailModuleFragment.setData();
//                        quickModuleFragment.setData();
//                        if (bean.isSuccess() && !bean.getData().isEmpty()) {
//                            setAlertData();
//                        }
                    }
                });
    }

    @Override
    protected boolean isShowBacking() {
        return true;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_module_setting;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_module_setting, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_ok_module_setting:
//                detailModuleFragment.save();
//                quickModuleFragment.save();
//                setResult(RESULT_OK);
//                finish();
//                FollowFragment.getInstance(this).refresh();
                save();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
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

    public void setData() {
        shownItems.clear();
        notShownItems.clear();
        for (int i = 0; i < moduleBean.getData().getSelected().size(); i++) {
            shownItems.add(moduleBean.getData().getSelected().get(i).getConfigName());
            shownItemsIds.add(moduleBean.getData().getSelected().get(i).getConfigId());
        }
        for (int i = 0; i < moduleBean.getData().getUnSelected().size(); i++) {
            notShownItems.add(moduleBean.getData().getUnSelected().get(i).getConfigName());
            notShownItemsIds.add(moduleBean.getData().getUnSelected().get(i).getConfigId());
        }
        shownAdapter.notifyDataSetChanged();
        notShownAdapter.notifyDataSetChanged();
    }

    /**
     * 获取点击的Item的对应View，
     * 因为点击的Item已经有了自己归属的父容器MyGridView，所有我们要是有一个ImageView来代替Item移动
     *
     * @param view
     * @return
     */
    private ImageView getView(View view) {
        view.destroyDrawingCache();
        view.setDrawingCacheEnabled(true);
        Bitmap cache = Bitmap.createBitmap(view.getDrawingCache());
        view.setDrawingCacheEnabled(false);
        ImageView iv = new ImageView(this);
        iv.setImageBitmap(cache);
        return iv;
    }

    /**
     * 获取移动的VIEW，放入对应ViewGroup布局容器
     *
     * @param viewGroup
     * @param view
     * @param initLocation
     * @return
     */
    private View getMoveView(ViewGroup viewGroup, View view, int[] initLocation) {
        int x = initLocation[0];
        int y = initLocation[1];
        viewGroup.addView(view);
        LinearLayout.LayoutParams mLayoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        mLayoutParams.leftMargin = x;
        mLayoutParams.topMargin = y;
        view.setLayoutParams(mLayoutParams);
        return view;
    }

    /**
     * 创建移动的ITEM对应的ViewGroup布局容器
     * 用于存放我们移动的View
     */
    private ViewGroup getMoveViewGroup() {
        //window中最顶层的view
        ViewGroup moveViewGroup = (ViewGroup) getWindow().getDecorView();
        LinearLayout moveLinearLayout = new LinearLayout(this);
        moveLinearLayout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        moveViewGroup.addView(moveLinearLayout);
        return moveLinearLayout;
    }

    /**
     * 点击ITEM移动动画
     */
    private void moveAnim(View moveView, int[] startLocation, int[] endLocation, final String moveChannel,
                          final GridView clickGridView, final boolean isUser) {
        int[] initLocation = new int[2];
        //获取传递过来的VIEW的坐标
        moveView.getLocationInWindow(initLocation);
        //得到要移动的VIEW,并放入对应的容器中
        final ViewGroup moveViewGroup = getMoveViewGroup();
        final View mMoveView = getMoveView(moveViewGroup, moveView, initLocation);
        //创建移动动画
        TranslateAnimation moveAnimation = new TranslateAnimation(
                startLocation[0], endLocation[0], startLocation[1],
                endLocation[1]);
        moveAnimation.setDuration(10L);//动画时间
        //动画配置
        AnimationSet moveAnimationSet = new AnimationSet(true);
        moveAnimationSet.setFillAfter(false);//动画效果执行完毕后，View对象不保留在终止的位置
        moveAnimationSet.addAnimation(moveAnimation);
        mMoveView.startAnimation(moveAnimationSet);
        moveAnimationSet.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {
                shownGridView.setClickable(false);
                notShownGridView.setClickable(false);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                moveViewGroup.removeView(mMoveView);
                // 判断点击的是DragGrid还是OtherGridView
                if (isUser) {
                    notShownAdapter.setVisible(true);
                    notShownAdapter.notifyDataSetChanged();
                    shownAdapter.remove();
                } else {
                    shownAdapter.setVisible(true);
                    shownAdapter.notifyDataSetChanged();
                    notShownAdapter.remove();
                }
                shownGridView.setClickable(true);
                notShownGridView.setClickable(true);
            }
        });
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, final int position, long id) {
        switch (adapterView.getId()) {
            case R.id.gridview_shown_detail_module:
                //position为 0，1 的不可以进行任何操作
//                if (position != 0 && position != 1) {
                final ImageView moveImageView = getView(view);
                if (moveImageView != null) {
                    TextView newTextView = view.findViewById(R.id.text_item);
                    final int[] startLocation = new int[2];
                    newTextView.getLocationInWindow(startLocation);
                    final String channel = ((ModuleAdapter) adapterView.getAdapter()).getItem(position);//获取点击的频道内容
                    notShownAdapter.setVisible(false);
                    //添加到最后一个
                    notShownAdapter.addItem(channel);
                    notShownItemsIds.add(shownItemsIds.get(position));
                    shownItemsIds.remove(position);

                    new Handler().postDelayed(() -> {
                        try {
                            int[] endLocation = new int[2];
                            //获取终点的坐标
                            notShownGridView.getChildAt(notShownGridView.getLastVisiblePosition()).getLocationInWindow(endLocation);
                            moveAnim(moveImageView, startLocation, endLocation, channel, shownGridView, true);
                            shownAdapter.setRemove(position);
                        } catch (Exception localException) {
                        }
                    }, 0L);
                }
                break;
            case R.id.gridview_not_shown_detail_module:
                final ImageView moveImageView2 = getView(view);
                if (moveImageView2 != null) {
                    TextView newTextView = view.findViewById(R.id.text_item);
                    final int[] startLocation = new int[2];
                    newTextView.getLocationInWindow(startLocation);
                    final String channel = ((ModuleAdapter) adapterView.getAdapter()).getItem(position);
                    shownAdapter.setVisible(false);
                    //添加到最后一个
                    shownAdapter.addItem(channel);
                    shownItemsIds.add(notShownItemsIds.get(position));
                    notShownItemsIds.remove(position);

                    new Handler().postDelayed(() -> {
                        try {
                            int[] endLocation = new int[2];
                            //获取终点的坐标
                            shownGridView.getChildAt(shownGridView.getLastVisiblePosition()).getLocationInWindow(endLocation);
                            moveAnim(moveImageView2, startLocation, endLocation, channel, notShownGridView, false);
                            notShownAdapter.setRemove(position);
                        } catch (Exception localException) {
                        }
                    }, 0L);
                }
                break;
            default:
                break;
        }
    }

    public void save() {
        moduleService.setMudules(App.userid, id, listToString(shownItemsIds, ','))
                .subscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ModuleBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
//                        CommonUtils.showToast("网络错误");
//                        finishedFetchingData = true;
//                        finishRefresh();
                        Log.d("Allen", e.toString());
                        finish();
                        FollowFragment.getInstance(ModuleSettingActivity.this).refresh();
                    }

                    @Override
                    public void onNext(ModuleBean bean) {
                        Log.d("Allen", "successful");
                        finish();
                        FollowFragment.getInstance(ModuleSettingActivity.this).refresh();
//                        finishedFetchingData = true;
//                        finishRefresh();
////                        alertLevels.clear();
////                        alertStations.clear();
////                        alertReasons.clear();
////                        alertDates.clear();
//                        moduleBean = bean;
//                        detailModuleFragment.setData();
//                        quickModuleFragment.setData();
//                        if (bean.isSuccess() && !bean.getData().isEmpty()) {
//                            setAlertData();
//                        }
                    }
                });
//        CommonUtils.showToast("详细模块保存");
    }

    private String listToString(List<String> list, char separator) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            sb.append(list.get(i)).append(separator);
        }
        if (list.size() == 0) {
            return "delete";
        } else {
            return sb.toString().substring(0, sb.toString().length() - 1);
        }
    }
}
