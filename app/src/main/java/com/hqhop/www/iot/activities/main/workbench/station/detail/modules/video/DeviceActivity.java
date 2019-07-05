package com.hqhop.www.iot.activities.main.workbench.station.detail.modules.video;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.hqhop.www.iot.App;
import com.hqhop.www.iot.R;
import com.huamaitel.api.HMDefines;
import com.huamaitel.api.HMDefines.ChannelInfo;
import com.huamaitel.api.HMDefines.NodeTypeInfo;
import com.huamaitel.api.HMJniInterface;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DeviceActivity extends Activity {
    private static final String TAG = "DeviceActivity";
    private ListView mListView;
    private List<Map<String, Object>> mListData;
    private int mVideoStream = HMDefines.CodeStream.MAIN_STREAM;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.device_activity);
        mListView = (ListView) findViewById(R.id.id_device_list);
        mListData = new ArrayList<>();

        // Get the root of the tree.
        int treeId = App.treeId;
        int rootId = App.getJni().getRoot(treeId);

        App.rootList.clear();
        App.rootList.add(rootId);

        getChildrenByNodeId(rootId);

        SimpleAdapter adapter = new SimpleAdapter(this, mListData, R.layout.item_device, new String[]{"img", "name"}, new int[]{R.id.id_img_deviceIcon, R.id.id_device_name});
        mListView.setAdapter(adapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                @SuppressWarnings("unchecked")
                Map<String, Object> map = (Map<String, Object>) parent.getItemAtPosition(position);
                int nodeType = (Integer) map.get("type");
//                Log.d("DeviceActivity:", "nodeType:" + nodeType);
                int nodeId = (Integer) map.get("id");
//                Log.d("DeviceActivity:", "nodeId:" + nodeId);

                App.curNodeHandle = nodeId;
                if (nodeType == NodeTypeInfo.NODE_TYPE_DVS || nodeType == NodeTypeInfo.NODE_TYPE_GROUP) {
                    App.rootList.add(nodeId);
                    getChildrenByNodeId(nodeId);
                    ((SimpleAdapter) mListView.getAdapter()).notifyDataSetChanged();
                }
                if (nodeType == NodeTypeInfo.NODE_TYPE_CHANNEL) {
                    int nodeDvsId = App.getJni().getParentId(nodeId);
                    ChannelInfo info = App.getJni().getChannelInfo(nodeId); // 获取通道信息
                    //如果设备在线
                    Log.d(TAG, "info:" + info.index + "+" + info.name);
                    gotoPlayActivity(nodeDvsId, info.index, mVideoStream);
                } else if (nodeType == NodeTypeInfo.NODE_TYPE_DEVICE) {
                    gotoPlayActivity(nodeId, 0, mVideoStream);
                }
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (App.treeId != 0) {
            App.getJni().releaseTree(App.treeId);
        }

        if (App.serverId != 0) {
            App.getJni().disconnectServer(App.serverId);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (App.rootList.size() != 1) {
                int nodeId = App.rootList.get(App.rootList.size() - 2);
                App.rootList.remove(App.rootList.size() - 1);

                getChildrenByNodeId(nodeId);

                ((SimpleAdapter) mListView.getAdapter()).notifyDataSetChanged();
                return true;
            }
        }

        return super.onKeyDown(keyCode, event);
    }

    // Get the children list by this parent node.
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

                if (nodeType == NodeTypeInfo.NODE_TYPE_GROUP) {
                    obj.put("img", R.drawable.folder);
                } else if (nodeType == NodeTypeInfo.NODE_TYPE_DEVICE) {
                    obj.put("img", R.drawable.device);
                } else if (nodeType == NodeTypeInfo.NODE_TYPE_DVS) {
                    obj.put("img", R.drawable.dvs);
                } else if (nodeType == NodeTypeInfo.NODE_TYPE_CHANNEL) {
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

    private void gotoPlayActivity(int nodeId, int channel, int videoStream) {
        Intent intent = new Intent();
        intent.setClass(DeviceActivity.this, PlayActivity.class);
        intent.putExtra(App.NODE_ID, nodeId);
        intent.putExtra(App.CHANNEL, channel);
        intent.putExtra(App.VIDEO_STREAM, videoStream);
        App.mIsUserLogin = true;
        startActivity(intent);
    }
}
