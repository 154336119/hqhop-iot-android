package com.hqhop.www.iot.activities.login.forget;

import android.app.Dialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.hqhop.www.iot.BuildConfig;
import com.hqhop.www.iot.R;
import com.hqhop.www.iot.api.login.LoginService;
import com.hqhop.www.iot.base.Common;
import com.hqhop.www.iot.base.http.RetrofitManager;
import com.hqhop.www.iot.base.util.CommonUtils;
import com.hqhop.www.iot.base.util.CountDownUtils;
import com.hqhop.www.iot.base.util.XOREncryptAndDecrypt;
import com.hqhop.www.iot.base.view.BaseAppCompatActivity;
import com.hqhop.www.iot.presenter.login.forget.GetVeriCodePresenter;
import com.hqhop.www.iot.presenter.login.forget.UpdatePasswordPresenter;

public class ForgetActivity extends BaseAppCompatActivity implements View.OnClickListener, IGetVeriCodeView, IUpdatePasswordView {

    private View rootView;

    private RelativeLayout containerAccount;

    private LinearLayout containerPassword;

    private EditText etAccount;

    private EditText etVerificationCode;

    private EditText etPassword;

    private Button btnVerificationCode;

    private Button btnReset;

    private Dialog dialog;

    private CountDownUtils countDownUtils;

//    private LoginService service;

    private GetVeriCodePresenter getVeriCodePresenter;

    private UpdatePasswordPresenter updatePasswordPresenter;

    private String loginName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_forget;
    }

    @Override
    protected boolean isShowBacking() {
        return true;
    }

    private void init() {
        getVeriCodePresenter = new GetVeriCodePresenter(this);
        updatePasswordPresenter = new UpdatePasswordPresenter(this);

        setToolBarTitle(getString(R.string.reset_password));
        containerAccount = findViewById(R.id.container_account_forget);
        containerPassword = findViewById(R.id.container_password_forget);
        rootView = findViewById(R.id.root_activity_forget);
        etAccount = findViewById(R.id.et_account_forget);
        etVerificationCode = findViewById(R.id.et_verify_forget);
        etPassword = findViewById(R.id.et_password_forget);
        btnVerificationCode = findViewById(R.id.btn_get_verify_forget);
        dialog = CommonUtils.createLoadingDialog(ForgetActivity.this);

        btnVerificationCode.setOnClickListener(this);
        btnReset = findViewById(R.id.btn_reset_forget);
        btnReset.setOnClickListener(this);

//        service = RetrofitManager.getInstance(this).createService(LoginService.class);

        etAccount.setOnKeyListener((view, keyCode, keyEvent) -> {
            if (keyCode == KeyEvent.KEYCODE_ENTER && keyEvent.getAction() == KeyEvent.ACTION_UP) {
                if (TextUtils.isEmpty(etAccount.getText().toString().trim())) {
                    CommonUtils.showToast(getString(R.string.input_account));
                    return true;
                } else {
                    CommonUtils.hideKeyboard(ForgetActivity.this);
                    getVeriCodePresenter.getCode(ForgetActivity.this, RetrofitManager.getInstance(ForgetActivity.this).createService(LoginService.class));
                }
            }
            return false;
        });

        etVerificationCode.setOnKeyListener((view, keyCode, keyEvent) -> {
            if (keyCode == KeyEvent.KEYCODE_ENTER && keyEvent.getAction() == KeyEvent.ACTION_UP) {
                if (TextUtils.isEmpty(etVerificationCode.getText().toString().trim())) {
                    CommonUtils.showToast(getString(R.string.verification_code_cant_empty));
                    return true;
                } else {
                    etPassword.requestFocus();
                }
            }
            return false;
        });

        etPassword.setOnKeyListener((view, keyCode, keyEvent) -> {
            if (keyCode == KeyEvent.KEYCODE_ENTER && keyEvent.getAction() == KeyEvent.ACTION_UP) {
                if (TextUtils.isEmpty(etVerificationCode.getText().toString().trim()) || TextUtils.isEmpty(etPassword.getText().toString().trim())) {
                    CommonUtils.showToast(getString(R.string.verification_code_password_cant_empty));
                    return true;
                } else {
                    CommonUtils.hideKeyboard(ForgetActivity.this);
                    updatePasswordPresenter.updatePassword(ForgetActivity.this, RetrofitManager.getInstance(ForgetActivity.this).createService(LoginService.class));
                }
            }
            return false;
        });

//        if (BuildConfig.DEBUG) {
//            etAccount.setText("zhadmin");
//        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_get_verify_forget:
                getVeriCodePresenter.getCode(this, RetrofitManager.getInstance(this).createService(LoginService.class));
                break;
            case R.id.btn_reset_forget:
                if (containerAccount.getVisibility() == View.VISIBLE) {
                    // 第1个布局可见
                    if (TextUtils.isEmpty(etAccount.getText().toString().trim())) {
                        CommonUtils.showToast(getString(R.string.input_account));
                    } else {
                        CommonUtils.hideKeyboard(this);
                        getVeriCodePresenter.getCode(this, RetrofitManager.getInstance(this).createService(LoginService.class));
                        btnReset.setClickable(false);
                    }
                } else {
                    // 第2个布局可见
                    if (TextUtils.isEmpty(etVerificationCode.getText().toString().trim()) || TextUtils.isEmpty(etPassword.getText().toString().trim())) {
                        CommonUtils.showToast(getString(R.string.verification_code_password_cant_empty));
                    } else {
                        CommonUtils.hideKeyboard(this);
                        updatePasswordPresenter.updatePassword(this, RetrofitManager.getInstance(this).createService(LoginService.class));
                    }
                }
                break;
            default:
                break;
        }
    }

    @Override
    public String getAccount() {
        loginName = etAccount.getText().toString().trim();
        return loginName;
    }

    @Override
    public void getCodeSucceed(String message) {
        if (containerAccount.getVisibility() == View.VISIBLE) {
            // 第1个布局可见
            containerAccount.setVisibility(View.GONE);
            containerPassword.setVisibility(View.VISIBLE);
            btnReset.setText(getString(R.string.confirm));
            CommonUtils.showToast(message);
            countDownUtils = new CountDownUtils(btnVerificationCode, Common.TIME_INTERVAL_VERIFICATION_CODE, Common.TIME_INTERVAL_REFRESH_COUNTDOWN);
            countDownUtils.start();
            btnReset.setClickable(true);
        } else {
            // 第2个布局可见
            CommonUtils.showToast(message);
            countDownUtils = new CountDownUtils(btnVerificationCode, Common.TIME_INTERVAL_VERIFICATION_CODE, Common.TIME_INTERVAL_REFRESH_COUNTDOWN);
            countDownUtils.start();
        }
    }

    @Override
    public void getCodeFailed(String message) {
        CommonUtils.showToast(message);
        btnReset.setClickable(true);
    }

    @Override
    public void showGetCodeLoading() {
        CommonUtils.showProgressDialogWithMessage(this, dialog, null);
    }

    @Override
    public void hideGetCodeLoading() {
        CommonUtils.hideProgressDialog(dialog);
    }

    @Override
    public String getVerificationCode() {
        return etVerificationCode.getText().toString().trim();
    }

    @Override
    public String getLoginName() {
        return loginName;
    }

    @Override
    public String getPassword() {
        return XOREncryptAndDecrypt.encrypt(etPassword.getText().toString().trim());
//        return MD5Utils.getMD5(etPassword.getText().toString().trim());
    }

    @Override
    public void updatePasswordSucceed(String message) {
        CommonUtils.showToast(message);
        this.finish();
    }

    @Override
    public void updatePasswordFailed(String message) {
        if (TextUtils.isEmpty(message)) {
            CommonUtils.showToast(getString(R.string.reset_password_failed));
        } else {
            CommonUtils.showToast(message);
        }
    }

    @Override
    public void showUpdatePasswordLoading() {
        CommonUtils.showProgressDialogWithMessage(this, dialog, null);
    }

    @Override
    public void hideUpdatePasswordLoading() {
        CommonUtils.hideProgressDialog(dialog);
    }
}
