package com.hqhop.www.iot.activities.main.follow.module;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
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

import com.hqhop.www.iot.R;
import com.hqhop.www.iot.base.adapter.ModuleAdapter;
import com.hqhop.www.iot.base.util.CommonUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by allen on 2017/7/28.
 */

public class QuickModuleFragment extends Fragment implements AdapterView.OnItemClickListener {

    private View rootView;

    private GridView shownGridView, notShownGridView;

    private List<String> shownItems, notShownItems, shownItemsIds, notShownItemsIds;

    private ModuleAdapter shownAdapter, notShownAdapter;

    private boolean isInitial;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_quick_module, container, false);

        shownGridView = (GridView) rootView.findViewById(R.id.gridview_shown_quick_module);
        notShownGridView = (GridView) rootView.findViewById(R.id.gridview_not_shown_quick_module);

        shownItems = new ArrayList<>();
        notShownItems = new ArrayList<>();
        shownItemsIds = new ArrayList<>();
        notShownItemsIds = new ArrayList<>();
        shownAdapter = new ModuleAdapter(getActivity(), shownItems, true);
        notShownAdapter = new ModuleAdapter(getActivity(), notShownItems, false);
        shownGridView.setAdapter(shownAdapter);
        notShownGridView.setAdapter(notShownAdapter);

        shownGridView.setOnItemClickListener(this);
        notShownGridView.setOnItemClickListener(this);

//        DaoMaster.DevOpenHelper devOpenHelper = new DaoMaster.DevOpenHelper(getActivity(), Common.DB_QUICK_PREFIX_NAME + ((ModuleSettingActivity) getActivity()).id, null);
//        DaoMaster daoMaster = new DaoMaster(devOpenHelper.getWritableDb());
//        DaoSession daoSession = daoMaster.newSession();
//        dao = daoSession.getModulesQuickBeanDao();
//        shownList = new ArrayList<>();
//        notShownList = new ArrayList<>();
//        quickBeanList = dao.queryBuilder().where(ModulesQuickBeanDao.Properties.ModuleName.isNotNull()).build().list();
//        if (quickBeanList == null || quickBeanList.size() == 0) {
//            isInitial = true;
//            ModulesQuickBean temp1 = new ModulesQuickBean("模块1", true);
//            ModulesQuickBean temp2 = new ModulesQuickBean("模块2", true);
//            ModulesQuickBean temp3 = new ModulesQuickBean("模块3", true);
//            ModulesQuickBean temp4 = new ModulesQuickBean("模块4", true);
//            shownList.add(temp1);
//            shownList.add(temp2);
//            shownList.add(temp3);
//            shownList.add(temp4);
//            ModulesQuickBean temp5 = new ModulesQuickBean("模块5", false);
//            ModulesQuickBean temp6 = new ModulesQuickBean("模块6", false);
//            ModulesQuickBean temp7 = new ModulesQuickBean("模块7", false);
//            ModulesQuickBean temp8 = new ModulesQuickBean("模块8", false);
//            notShownList.add(temp5);
//            notShownList.add(temp6);
//            notShownList.add(temp7);
//            notShownList.add(temp8);
//
//            List<ModulesQuickBean> beanList = new ArrayList<>();
//            beanList.add(temp1);
//            beanList.add(temp2);
//            beanList.add(temp3);
//            beanList.add(temp4);
//            beanList.add(temp5);
//            beanList.add(temp6);
//            beanList.add(temp7);
//            beanList.add(temp8);
//            dao.insertInTx(beanList);
//        } else {
//            // 如果有数据
//            isInitial = false;
//        }

//        setData();

        return rootView;
    }

    public void setData() {
//        DaoMaster.DevOpenHelper devOpenHelper = new DaoMaster.DevOpenHelper(getActivity(), Common.DB_QUICK_PREFIX_NAME + ((ModuleSettingActivity) getActivity()).id, null);
//        DaoMaster daoMaster = new DaoMaster(devOpenHelper.getWritableDb());
//        DaoSession daoSession = daoMaster.newSession();
//        dao = daoSession.getModulesQuickBeanDao();
//        shownList = new ArrayList<>();
//        notShownList = new ArrayList<>();
//        quickBeanList = dao.queryBuilder().where(ModulesQuickBeanDao.Properties.ModuleName.isNotNull()).build().list();
//        if (quickBeanList == null || quickBeanList.size() == 0) {
//            isInitial = true;
//            ModulesQuickBean temp1 = new ModulesQuickBean("模块1", true);
//            ModulesQuickBean temp2 = new ModulesQuickBean("模块2", true);
//            ModulesQuickBean temp3 = new ModulesQuickBean("模块3", true);
//            ModulesQuickBean temp4 = new ModulesQuickBean("模块4", true);
//            shownList.add(temp1);
//            shownList.add(temp2);
//            shownList.add(temp3);
//            shownList.add(temp4);
//            ModulesQuickBean temp5 = new ModulesQuickBean("模块5", false);
//            ModulesQuickBean temp6 = new ModulesQuickBean("模块6", false);
//            ModulesQuickBean temp7 = new ModulesQuickBean("模块7", false);
//            ModulesQuickBean temp8 = new ModulesQuickBean("模块8", false);
//            notShownList.add(temp5);
//            notShownList.add(temp6);
//            notShownList.add(temp7);
//            notShownList.add(temp8);
//
//            List<ModulesQuickBean> beanList = new ArrayList<>();
//            beanList.add(temp1);
//            beanList.add(temp2);
//            beanList.add(temp3);
//            beanList.add(temp4);
//            beanList.add(temp5);
//            beanList.add(temp6);
//            beanList.add(temp7);
//            beanList.add(temp8);
//            dao.insertInTx(beanList);
//        } else {
//            // 如果有数据
//            isInitial = false;
//        }
//
//        if (isInitial) {
//            for (ModulesQuickBean temp : shownList) {
//                shownItems.add(temp.getModuleName());
//            }
//            for (ModulesQuickBean temp : notShownList) {
//                notShownItems.add(temp.getModuleName());
//            }
//        } else {
//            for (ModulesQuickBean temp : quickBeanList) {
//                if (temp.getIsShown()) {
//                    shownItems.add(temp.getModuleName());
//                    shownList.add(temp);
//                } else {
//                    notShownItems.add(temp.getModuleName());
//                    notShownList.add(temp);
//                }
//            }
//        }

        shownItems.clear();
        notShownItems.clear();
        for (int i = 0; i < ModuleSettingActivity.moduleBean.getData().getSelected().size(); i++) {
            shownItems.add(ModuleSettingActivity.moduleBean.getData().getSelected().get(i).getConfigName());
            shownItemsIds.add(ModuleSettingActivity.moduleBean.getData().getSelected().get(i).getConfigId());
        }
        for (int i = 0; i < ModuleSettingActivity.moduleBean.getData().getUnSelected().size(); i++) {
            notShownItems.add(ModuleSettingActivity.moduleBean.getData().getUnSelected().get(i).getConfigName());
            notShownItemsIds.add(ModuleSettingActivity.moduleBean.getData().getUnSelected().get(i).getConfigId());
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
        ImageView iv = new ImageView(getActivity());
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
        ViewGroup moveViewGroup = (ViewGroup) getActivity().getWindow().getDecorView();
        LinearLayout moveLinearLayout = new LinearLayout(getActivity());
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
        moveAnimation.setDuration(300L);//动画时间
        //动画配置
        AnimationSet moveAnimationSet = new AnimationSet(true);
        moveAnimationSet.setFillAfter(false);//动画效果执行完毕后，View对象不保留在终止的位置
        moveAnimationSet.addAnimation(moveAnimation);
        mMoveView.startAnimation(moveAnimationSet);
        moveAnimationSet.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {
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
            }
        });
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, final int position, long id) {
        switch (adapterView.getId()) {
            case R.id.gridview_shown_quick_module:
                //position为 0，1 的不可以进行任何操作
                if (position != 0 && position != 1) {
                    final ImageView moveImageView = getView(view);
                    if (moveImageView != null) {
                        TextView newTextView = (TextView) view.findViewById(R.id.text_item);
                        final int[] startLocation = new int[2];
                        newTextView.getLocationInWindow(startLocation);
                        final String channel = ((ModuleAdapter) adapterView.getAdapter()).getItem(position);//获取点击的频道内容
                        notShownAdapter.setVisible(false);
                        //添加到最后一个
                        notShownAdapter.addItem(channel);
                        notShownItemsIds.add(shownItemsIds.get(position));
                        shownItemsIds.remove(position);

                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    int[] endLocation = new int[2];
                                    //获取终点的坐标
                                    notShownGridView.getChildAt(notShownGridView.getLastVisiblePosition()).getLocationInWindow(endLocation);
                                    moveAnim(moveImageView, startLocation, endLocation, channel, shownGridView, true);
                                    shownAdapter.setRemove(position);
                                } catch (Exception localException) {
                                }
                            }
                        }, 50L);
                    }
                }
                break;
            case R.id.gridview_not_shown_quick_module:
                final ImageView moveImageView = getView(view);
                if (moveImageView != null) {
                    TextView newTextView = (TextView) view.findViewById(R.id.text_item);
                    final int[] startLocation = new int[2];
                    newTextView.getLocationInWindow(startLocation);
                    final String channel = ((ModuleAdapter) adapterView.getAdapter()).getItem(position);
                    shownAdapter.setVisible(false);
                    //添加到最后一个
                    shownAdapter.addItem(channel);
                    shownItemsIds.add(notShownItemsIds.get(position));
                    notShownItemsIds.remove(position);

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                int[] endLocation = new int[2];
                                //获取终点的坐标
                                shownGridView.getChildAt(shownGridView.getLastVisiblePosition()).getLocationInWindow(endLocation);
                                moveAnim(moveImageView, startLocation, endLocation, channel, notShownGridView, false);
                                notShownAdapter.setRemove(position);
                            } catch (Exception localException) {
                            }
                        }
                    }, 50L);
                }
                break;
            default:
                break;
        }
    }

    public void save() {
//        Log.d("test", "save quick");
//        dao.deleteAll();
//        List<ModulesQuickBean> list = new ArrayList<>();
//        for (ModulesQuickBean item : shownList) {
//            list.add(item);
//        }
//        for (ModulesQuickBean item : notShownList) {
//            list.add(item);
//        }
//        for (ModulesQuickBean item : list) {
//            Log.d("test", item.getModuleName() + ":" + item.getIsShown());
//        }
//        dao.insertInTx(list);
//        CommonUtils.showToast("快捷模块保存");
    }
}
