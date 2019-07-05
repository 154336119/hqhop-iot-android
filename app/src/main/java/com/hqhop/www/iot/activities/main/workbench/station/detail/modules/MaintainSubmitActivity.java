package com.hqhop.www.iot.activities.main.workbench.station.detail.modules;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.hqhop.www.iot.App;
import com.hqhop.www.iot.R;
import com.hqhop.www.iot.activities.main.workbench.station.detail.DetailStationActivity;
import com.hqhop.www.iot.base.util.CommonUtils;
import com.hqhop.www.iot.base.view.BaseAppCompatActivity;

import java.util.Calendar;

public class MaintainSubmitActivity extends BaseAppCompatActivity {

    private TextView tvNumber;

    private TextView tvCustomerName;

    private TextView tvStationName;

    private EditText etPerson;

    private EditText etPhone;

    private EditText etDescribe;

    private TextView tvFile;

    private TextView tvDate;

    private Calendar calendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        init();
    }

    @SuppressLint("SetTextI18n")
    private void init() {
        setToolBarTitle("报修");

        tvNumber = findViewById(R.id.tv_number_maintain_submit);
        tvCustomerName = findViewById(R.id.tv_customer_name_maintain_submit);
        tvStationName = findViewById(R.id.tv_station_name_maintain_submit);
        etPerson = findViewById(R.id.et_person_maintain_submit);
        etPhone = findViewById(R.id.et_phone_maintain_submit);
        etDescribe = findViewById(R.id.et_describe_maintain_submit);
        tvFile = findViewById(R.id.tv_file_maintain_submit);
        tvDate = findViewById(R.id.tv_date_maintain_submit);

        tvNumber.setText(String.valueOf(System.currentTimeMillis()));
        tvCustomerName.setText(App.userName);
        tvStationName.setText(DetailStationActivity.stationName);
        tvFile.setText(DetailStationActivity.stationName + "设备运行数据.xls");
        tvDate.setOnClickListener(view -> {
            if (calendar == null) {
                calendar = Calendar.getInstance();
            }
            DatePickerDialog.OnDateSetListener listener = new DatePickerDialog.OnDateSetListener() {
                @SuppressLint("SetTextI18n")
                @Override
                public void onDateSet(DatePicker picker, int year, int month, int day) {
                    calendar.set(Calendar.YEAR, year);
                    calendar.set(Calendar.MONTH, month);
                    calendar.set(Calendar.DAY_OF_MONTH, day);
                    tvDate.setText(year + "-" + (++month) + "-" + day);
                }
            };
            DatePickerDialog dialog = new DatePickerDialog(this, 0, listener, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));//后边三个参数为显示dialog时默认的日期，月份从0开始，0-11对应1-12个月
            dialog.show();
        });
    }

    public void submit(View view) {
        if (TextUtils.isEmpty(etPerson.getText().toString().trim())
                || TextUtils.isEmpty(etPhone.getText().toString().trim())
                || TextUtils.isEmpty(etDescribe.getText().toString().trim())
                || TextUtils.isEmpty(tvDate.getText().toString())) {
            CommonUtils.showToast("请输入完整信息");
            return;
        }
        CommonUtils.showToast("报修成功");
        finish();
    }

    @Override
    protected boolean isShowBacking() {
        return true;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_maintain_submit;
    }
}
