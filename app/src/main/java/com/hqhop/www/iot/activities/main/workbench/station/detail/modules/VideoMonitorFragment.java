package com.hqhop.www.iot.activities.main.workbench.station.detail.modules;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.hqhop.www.iot.App;
import com.hqhop.www.iot.R;
import com.hqhop.www.iot.activities.main.workbench.station.detail.DetailStationActivity;
import com.hqhop.www.iot.activities.main.workbench.station.detail.modules.video.PlayActivity;
import com.hqhop.www.iot.base.util.CommonUtils;
import com.huamaitel.api.HMDefines;
import com.huamaitel.api.HMJniInterface;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 站点详情页-视频监控Fragment
 * Created by allen on 2017/7/24.
 */

public class VideoMonitorFragment extends Fragment {

    private View rootView;

    private int mVideoStream = HMDefines.CodeStream.MAIN_STREAM;
    private List<Map<String, Object>> mListData = new ArrayList<>();

    private Button hmLogin1, hmLogin2, hmLogin3, hmLogin4;

    private static final String TAG = "VideoMonitorFragment";
    private static final int EVENT_CONNECT_SUCCESS = 1;
    private static final int EVENT_CONNECT_FAIL = 2;
    private static final int EVENT_LOGIN_SUCCESS = 3;
    private static final int EVENT_LOGIN_FAIL = 4;
    private static final String SERVER_ADDR = "www.huamaiyun.com";
    private static final short SERVER_PORT = 80;
    private Handler handler;
    private Thread mServerThread = null;
    private HMDefines.LoginServerInfo info = null;

    private TextView tvStatus;

    private boolean isFirst = true;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_video_monitor, container, false);
        CommonUtils.showProgressDialogWithMessage(getActivity(), ((DetailStationActivity) getActivity()).dialog, null);
//        App.getJni().init();
//        login();
        hmLogin1 = rootView.findViewById(R.id.btn_hm_login_test1);
        hmLogin2 = rootView.findViewById(R.id.btn_hm_login_test2);
        hmLogin3 = rootView.findViewById(R.id.btn_hm_login_test3);
        hmLogin4 = rootView.findViewById(R.id.btn_hm_login_test4);
//        hmLogin1.setOnClickListener(view -> test(6));
        hmLogin1.setOnClickListener(view -> test(0));
//        hmLogin2.setOnClickListener(view -> test(7));
        hmLogin2.setOnClickListener(view -> test(0));
//        hmLogin3.setOnClickListener(view -> test(8));
        hmLogin3.setOnClickListener(view -> test(0));
//        hmLogin4.setOnClickListener(view -> test(9));
        hmLogin4.setOnClickListener(view -> test(0));
        tvStatus = rootView.findViewById(R.id.tv_status_video_monitor);
        registerHander();
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

    @Override
    public void onDestroy() {
        super.onDestroy();
//        if (App.treeId != 0) {
//            App.getJni().releaseTree(App.treeId);
//        }
//
//        if (App.serverId != 0) {
//            App.getJni().disconnectServer(App.serverId);
//        }
        App.getJni().uninit();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && isFirst) {
            isFirst = false;
            App.getJni().init();
            login();
        }
    }

    private void login() {
        mServerThread = new Thread() {
            @Override
            public void run() {
                info = new HMDefines.LoginServerInfo();
                info.ip = SERVER_ADDR;
                info.port = SERVER_PORT;
                info.user = "hqhp";
                info.password = "1234";
                info.model = android.os.Build.MODEL;
                info.version = android.os.Build.VERSION.RELEASE;

                String error = jni_connectServer();
                if (error != null) {
                    Log.e(TAG, "Connect server fail.");
                    sendEmptyMessage(EVENT_CONNECT_FAIL, error);
                } else {
                    Log.i(TAG, "Connect server success.");
                    sendEmptyMessage(EVENT_CONNECT_SUCCESS);
                }
            }
        };
        mServerThread.start();
    }

    @SuppressLint("HandlerLeak")
    private void registerHander() {
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case EVENT_LOGIN_SUCCESS:
//                        Intent intent = new Intent();
//                        intent.setClass(getActivity(), DeviceActivity.class);
//                        startActivity(intent);

                        int treeId = App.treeId;
                        int rootId = App.getJni().getRoot(treeId);
                        App.rootList.clear();
                        App.rootList.add(rootId);
                        getChildrenByNodeId(rootId);
                        test(1);
                        test(9);

                        hmLogin1.setVisibility(View.VISIBLE);
                        hmLogin2.setVisibility(View.VISIBLE);
                        hmLogin3.setVisibility(View.VISIBLE);
                        hmLogin4.setVisibility(View.VISIBLE);
                        break;
                    case EVENT_LOGIN_FAIL:
                        App.getJni().disconnectServer(App.serverId);
                        break;
                    case EVENT_CONNECT_SUCCESS:
                        int result = App.getJni().getDeviceList(App.serverId);
                        if (result != HMDefines.HMEC_OK) {
                            sendEmptyMessage(EVENT_LOGIN_FAIL);
                            return;
                        }
                        // step 2: Get user information.
                        HMDefines.UserInfo userInfo = App.getJni().getUserInfo(App.serverId);
                        if (userInfo == null) {
                            sendEmptyMessage(EVENT_LOGIN_FAIL);
                            return;
                        }
                        /**
                         * 判断选择: huamaiyun和see1000中需要添加userInfo.useTransferService !=8
                         *
                         * 这个判断 seebao中需要去掉，否则容易报错！
                         */
                        // step 3: Get transfer service.
                        // if (userInfo.useTransferService != 0&&userInfo.useTransferService !=8) {
                        if (userInfo.useTransferService != 0) {
                            result = App.getJni().getTransferInfo(App.serverId);
                            if (result != HMDefines.HMEC_OK) {
                                sendEmptyMessage(EVENT_LOGIN_FAIL);
                                return;
                            }
                        }
                        // step 4: Get tree id.
                        App.treeId = App.getJni().getTree(App.serverId);
                        sendEmptyMessage(EVENT_LOGIN_SUCCESS);
                        break;
                    case EVENT_CONNECT_FAIL:
                        CommonUtils.showToast(msg.obj.toString());
                        tvStatus.setVisibility(View.VISIBLE);
                        break;

                }
//                CommonUtils.hideProgressDialog(((DetailStationActivity) getActivity()).dialog);
            }
        };
    }

    private void sendEmptyMessage(int msgId) {
        sendEmptyMessage(msgId, null);
    }

    private void sendEmptyMessage(int msgId, String error) {
        if (handler == null) {
            return;
        }
        Message msg = new Message();
        msg.what = msgId;
        msg.obj = error;
        handler.sendMessage(msg);
    }

    public String jni_connectServer() {
        StringBuilder error = new StringBuilder();
        App.serverId = App.getJni().connectServer(info, error);
        if (App.serverId == -1) {
            return error.toString();
        }
        return null;
    }

    private void getChildrenByNodeId(int nodeId) {
        Log.d(TAG, "getDeviceListByNodeId nodeId: " + nodeId);
        if (nodeId != 0) {
            HMJniInterface sdk = App.getJni();
            mListData.clear();

            int count = sdk.getChildrenCount(nodeId);
            Log.d(TAG, "getChildrenCount: " + count);
            for (int i = 0; i < count; ++i) {
                Map<String, Object> obj = new HashMap<String, Object>();
                int childrenNode = sdk.getChildAt(nodeId, i);
                int nodeType = sdk.getNodeType(childrenNode);

                obj.put("type", nodeType);

                if (nodeType == HMDefines.NodeTypeInfo.NODE_TYPE_GROUP) {
                    obj.put("img", R.drawable.folder);
                } else if (nodeType == HMDefines.NodeTypeInfo.NODE_TYPE_DEVICE) {
                    obj.put("img", R.drawable.device);
                } else if (nodeType == HMDefines.NodeTypeInfo.NODE_TYPE_DVS) {
                    obj.put("img", R.drawable.dvs);
                } else if (nodeType == HMDefines.NodeTypeInfo.NODE_TYPE_CHANNEL) {
                    obj.put("img", R.drawable.device);
                }

                Log.d(TAG, " childNode: " + childrenNode);
                Log.d(TAG, "childNode Url: " + sdk.getNodeUrl(childrenNode));
                Log.d(TAG, "childNode sn: " + sdk.getDeviceSn(childrenNode));

                obj.put("id", childrenNode);
                obj.put("name", sdk.getNodeName(childrenNode));

                mListData.add(obj);
            }
        }
    }

    private void test(int position) {
        try {
//        Map<String, Object> map = (Map<String, Object>) parent.getItemAtPosition(position);
            Map<String, Object> map = mListData.get(position);
            int nodeType = (Integer) map.get("type");
            int nodeId = (Integer) map.get("id");

            App.curNodeHandle = nodeId;
            if (nodeType == HMDefines.NodeTypeInfo.NODE_TYPE_DVS || nodeType == HMDefines.NodeTypeInfo.NODE_TYPE_GROUP) {
                App.rootList.add(nodeId);
                getChildrenByNodeId(nodeId);
                //            ((SimpleAdapter) mListView.getAdapter()).notifyDataSetChanged();
            }
            if (nodeType == HMDefines.NodeTypeInfo.NODE_TYPE_CHANNEL) {
                int nodeDvsId = App.getJni().getParentId(nodeId);
                HMDefines.ChannelInfo info = App.getJni().getChannelInfo(nodeId); // 获取通道信息
                //如果设备在线
                gotoPlayActivity(nodeDvsId, info.index, mVideoStream);
            } else if (nodeType == HMDefines.NodeTypeInfo.NODE_TYPE_DEVICE) {
                gotoPlayActivity(nodeId, 0, mVideoStream);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void gotoPlayActivity(int nodeId, int channel, int videoStream) {
        Intent intent = new Intent();
        intent.setClass(getActivity(), PlayActivity.class);
        intent.putExtra(App.NODE_ID, nodeId);
        intent.putExtra(App.CHANNEL, channel);
        intent.putExtra(App.VIDEO_STREAM, videoStream);
        App.mIsUserLogin = true;
        startActivity(intent);
    }
}
