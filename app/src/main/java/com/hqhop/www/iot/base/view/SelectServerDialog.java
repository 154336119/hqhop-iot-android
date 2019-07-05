package com.hqhop.www.iot.base.view;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.hqhop.www.iot.R;

/**
 * Created by allen on 2017/9/11.
 */

public class SelectServerDialog extends AlertDialog implements View.OnClickListener {
    private Context mContext;

    private EditText mEtAddress;

    private RadioButton mRbDebug, mRbRelease;

    private TextView mBtnOk;

    private TextView mBtnCancel;

    protected SelectServerDialog(@NonNull Context context) {
        super(context);
        mContext = context;
    }

    protected SelectServerDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        mContext = context;
    }

    protected SelectServerDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        mContext = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_dialog_select_server);
        mEtAddress = findViewById(R.id.et_address_select_server);
        mRbDebug = findViewById(R.id.rb_debug_select_server);
        mRbRelease = findViewById(R.id.rb_release_select_server);
        mBtnOk = findViewById(R.id.tv_position);
        mBtnCancel = findViewById(R.id.tv_negative);
        mBtnOk.setOnClickListener(this);
        mBtnCancel.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
//            case R.id.tv_position:
//                doOk();
//                break;
//            case R.id.tv_negative:
//                doCancel();
//                break;
            default:
                break;
        }
    }

    private void doOk() {
        dismiss();
        Toast.makeText(mContext, "OK", Toast.LENGTH_SHORT).show();
    }

    private void doCancel() {
        dismiss();
        Toast.makeText(mContext, "CANCEL", Toast.LENGTH_SHORT).show();
    }
}
