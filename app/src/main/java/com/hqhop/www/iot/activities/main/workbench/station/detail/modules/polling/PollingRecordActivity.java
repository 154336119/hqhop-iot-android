package com.hqhop.www.iot.activities.main.workbench.station.detail.modules.polling;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hqhop.www.iot.R;
import com.hqhop.www.iot.base.Common;
import com.hqhop.www.iot.base.util.CommonUtils;
import com.hqhop.www.iot.base.view.BaseAppCompatActivity;
import com.yongchun.library.view.ImageSelectorActivity;

import java.io.File;
import java.util.ArrayList;

public class PollingRecordActivity extends BaseAppCompatActivity implements View.OnClickListener {

    private TextView tvTime, tvAddress;

    private EditText etRemark;

    private Button btnUploadImage;

    private RecyclerView recyclerView;

    private ImageView ivResult;

    private Button btnUpload;

    private Intent gotIntent;

    private String time, address;

    private ArrayList<String> images;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    private void init() {
        setToolBarTitle("巡检记录");

        gotIntent = getIntent();
        time = gotIntent.getStringExtra(Common.POLLING_TIME);
        address = gotIntent.getStringExtra(Common.POLLING_ADDRESS);

        tvTime = (TextView) findViewById(R.id.tv_time_polling_record);
        tvAddress = (TextView) findViewById(R.id.tv_address_polling_record);
        etRemark = (EditText) findViewById(R.id.et_remark_polling_record);
        btnUploadImage = (Button) findViewById(R.id.btn_upload_polling_record);
        recyclerView = (RecyclerView) findViewById(R.id.result_recycler_polling_record);
        ivResult = (ImageView) findViewById(R.id.iv_result_polling_record);
        btnUpload = (Button) findViewById(R.id.btn_submit_polling_record);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 5));

        tvTime.setText(time);
        tvAddress.setText(address);
        btnUploadImage.setOnClickListener(this);
    }

    @Override
    protected boolean isShowBacking() {
        return true;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_polling_record;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == ImageSelectorActivity.REQUEST_IMAGE) {
            images = (ArrayList<String>) data.getSerializableExtra(ImageSelectorActivity.REQUEST_OUTPUT);
            if (images.size() == 1) {
                recyclerView.setVisibility(View.GONE);
                ivResult.setVisibility(View.VISIBLE);
                Glide.with(this)
                        .load(new File(images.get(0)))
                        .into(ivResult);
            } else {
                ivResult.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
                recyclerView.setAdapter(new GridAdapter());
            }
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_upload_polling_record:
                ImageSelectorActivity.start(this, 9, ImageSelectorActivity.MODE_MULTIPLE, true, true, false);
                break;
            case R.id.btn_submit_polling_record:
                CommonUtils.showToast("提交");
                break;
            default:
                break;
        }
    }

    private class GridAdapter extends RecyclerView.Adapter<GridAdapter.ViewHolder> {

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_result, parent, false);
            return new ViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            Glide.with(PollingRecordActivity.this)
                    .load(new File(images.get(position)))
                    .centerCrop()
                    .into(holder.imageView);
        }

        @Override
        public int getItemCount() {
            return images.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            ImageView imageView;

            public ViewHolder(View itemView) {
                super(itemView);
                imageView = (ImageView) itemView.findViewById(R.id.image);
            }
        }
    }
}
