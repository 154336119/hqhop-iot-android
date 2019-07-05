package com.hqhop.www.iot.base.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hqhop.www.iot.R;

import java.util.List;

public class ModuleAdapter extends BaseAdapter {

    private Context context;

    public List<String> channelList;

    private RelativeLayout containerModule;

    private TextView item_text;

//    private View lineLeft, lineTop, lineRight, lineBottom;

    private ImageView ivIconShown;

    /**
     * 是否可见 在移动动画完毕之前不可见，动画完毕后可见
     */
    boolean isVisible = true;
    /**
     * 要删除的position
     */
    public int remove_position = -1;
    /**
     * 是否是用户频道
     */
    private boolean isUser = false;

    public ModuleAdapter(Context context, List<String> channelList, boolean isUser) {
        this.context = context;
        this.channelList = channelList;
        this.isUser = isUser;
    }

    @Override
    public int getCount() {
        return channelList == null ? 0 : channelList.size();
    }

    @Override
    public String getItem(int position) {
        if (channelList != null && channelList.size() != 0) {
            return channelList.get(position);
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.adapter_mygridview_item, null);
        containerModule = view.findViewById(R.id.rl_subscribe);
        item_text = (TextView) view.findViewById(R.id.text_item);
//        lineLeft = view.findViewById(R.id.line_left);
//        lineTop = view.findViewById(R.id.line_top);
//        lineRight = view.findViewById(R.id.line_right);
//        lineBottom = view.findViewById(R.id.line_bottom);
        ivIconShown = view.findViewById(R.id.icon_shown);
        if (!isUser) {
            // 未选
//            lineLeft.setVisibility(View.GONE);
//            lineTop.setVisibility(View.GONE);
//            lineRight.setVisibility(View.GONE);
//            lineBottom.setVisibility(View.GONE);
            ivIconShown.setVisibility(View.GONE);
            containerModule.setBackgroundResource(R.drawable.shape_module_unselected);
        } else {
            // 已选
            containerModule.setBackgroundResource(R.drawable.shape_module_selected);
        }

        String channel = getItem(position);
        item_text.setText(channel);
        if (isUser) {
            if ((position == 0) || (position == 1)) {
                item_text.setEnabled(false);
            }
        }
        if (!isVisible && (position == -1 + channelList.size())) {
            item_text.setText("");
            item_text.setSelected(true);
            item_text.setEnabled(true);
        }
        if (remove_position == position) {
            item_text.setText("");
        }
        return view;
    }

    /**
     * 获取频道列表
     */
    public List<String> getChannnelLst() {
        return channelList;
    }

    /**
     * 添加频道列表
     */
    public void addItem(String channel) {
        channelList.add(channel);
        notifyDataSetChanged();
    }

    /**
     * 设置删除的position
     */
    public void setRemove(int position) {
        remove_position = position;
        notifyDataSetChanged();
        // notifyDataSetChanged();
    }

    /**
     * 删除频道列表
     */
    public void remove() {
        channelList.remove(remove_position);
        remove_position = -1;
        notifyDataSetChanged();
    }

    /**
     * 设置频道列表
     */
    public void setListDate(List<String> list) {
        channelList = list;
    }

    /**
     * 获取是否可见
     */
    public boolean isVisible() {
        return isVisible;
    }

    /**
     * 设置是否可见
     */
    public void setVisible(boolean visible) {
        isVisible = visible;
    }

}
