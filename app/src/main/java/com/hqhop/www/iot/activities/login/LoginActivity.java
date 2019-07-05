package com.hqhop.www.iot.activities.login;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.blankj.utilcode.util.ActivityUtils;
import com.hqhop.www.iot.App;
import com.hqhop.www.iot.R;
import com.hqhop.www.iot.activities.login.forget.ForgetActivity;
import com.hqhop.www.iot.activities.login.register.RegisterActivity;
import com.hqhop.www.iot.activities.main.MainActivity;
import com.hqhop.www.iot.api.login.LoginService;
import com.hqhop.www.iot.base.Common;
import com.hqhop.www.iot.base.http.RetrofitManager;
import com.hqhop.www.iot.base.util.CommonUtils;
import com.hqhop.www.iot.base.util.XOREncryptAndDecrypt;
import com.hqhop.www.iot.base.view.BaseAppCompatActivity;
import com.hqhop.www.iot.base.view.LongPressView;
import com.hqhop.www.iot.presenter.login.TokenPresenter;

public class LoginActivity extends BaseAppCompatActivity implements ITokenView, IUserInfoView {

    private static EditText etAccount;

    private static EditText etPassword;

    private Button btnLogin;

    private TextView tvRegister, tvForgetPassword;

    private Dialog dialog;

    private String username;

    private String password;

    private LongPressView lpvServer;

    private TokenPresenter tokenPresenter;

    private SharedPreferences sp;

    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        App.hasLogin = false;
        init();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    private void init() {
        sp = getSharedPreferences(Common.SP_USER_INFO, Context.MODE_PRIVATE);

        tokenPresenter = new TokenPresenter(this);

        setToolBarTitle(getString(R.string.login));
        etAccount = findViewById(R.id.et_account_login);
        etPassword = findViewById(R.id.et_password_login);
        btnLogin = findViewById(R.id.btn_login_login);
        tvRegister = findViewById(R.id.tv_register_password_login);
        tvForgetPassword = findViewById(R.id.tv_forget_password_login);
        dialog = CommonUtils.createLoadingDialog(LoginActivity.this);

        btnLogin.setOnClickListener(view -> {
            username = etAccount.getText().toString().trim();
            password = etPassword.getText().toString().trim();
            if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {
                CommonUtils.showToast(R.string.username_password_cant_be_empty);
            } else {
                btnLogin.setClickable(false);
                CommonUtils.hideKeyboard(LoginActivity.this);
                tokenPresenter.getToken(LoginActivity.this, RetrofitManager.getInstance(LoginActivity.this).createService(LoginService.class));
            }
        });
        tvRegister.setOnClickListener(view -> {
            Intent registerIntent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(registerIntent);
        });
        tvForgetPassword.setOnClickListener(view -> {
            Intent forgetIntent = new Intent(LoginActivity.this, ForgetActivity.class);
            startActivity(forgetIntent);
        });

        etAccount.setOnKeyListener((view, keyCode, keyEvent) -> {
            username = etAccount.getText().toString().trim();
            if (keyCode == KeyEvent.KEYCODE_ENTER && keyEvent.getAction() == KeyEvent.ACTION_UP) {
                if (TextUtils.isEmpty(username)) {
                    CommonUtils.showToast(getString(R.string.input_account));
                    return true;
                } else {
                    etPassword.requestFocus();
                }
            }
            return false;
        });
        etPassword.setOnKeyListener((view, keyCode, keyEvent) -> {
            username = etAccount.getText().toString().trim();
            password = etPassword.getText().toString().trim();
            if (keyCode == KeyEvent.KEYCODE_ENTER && keyEvent.getAction() == KeyEvent.ACTION_UP) {
                if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {
                    CommonUtils.showToast(R.string.username_password_cant_be_empty);
                    return true;
                } else {
                    CommonUtils.hideKeyboard(LoginActivity.this);
                    tokenPresenter.getToken(LoginActivity.this, RetrofitManager.getInstance(LoginActivity.this).createService(LoginService.class));
                }
            }
            return false;
        });

        lpvServer = findViewById(R.id.lpv_select_server);
        lpvServer.setVisibility(View.VISIBLE);
        lpvServer.setOnLongClickListener(view -> {
            final AlertDialog alertDialog = new AlertDialog.Builder(LoginActivity.this).create();
            alertDialog.show();
            if (alertDialog.getWindow() != null) {
                alertDialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
            }
            Window window = alertDialog.getWindow();
            window.setContentView(R.layout.layout_dialog_select_server);
            EditText mEtAddress = window.findViewById(R.id.et_address_select_server);
            RadioGroup mRg = window.findViewById(R.id.rg_select_server);
            RadioButton mRbDebug = window.findViewById(R.id.rb_debug_select_server);
            RadioButton mRbRelease = window.findViewById(R.id.rb_release_select_server);
            TextView ok = window.findViewById(R.id.tv_position);
            TextView cancel = window.findViewById(R.id.tv_negative);
            SharedPreferences sharedPreferences = getSharedPreferences("url_config", Context.MODE_PRIVATE);
            String baseUrl = sharedPreferences.getString("BASE_URL", Common.BASE_URL);
            Common.BASE_URL = baseUrl;
            mEtAddress.setText(baseUrl);
            mEtAddress.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable editable) {
                    String text = editable.toString();
                    Log.d("Allen", text);
                    if (!text.equals(Common.BASE_DEBUG_URL) && !text.equals(Common.BASE_RELEASE_URL) && (mRbDebug.isChecked() || mRbRelease.isChecked())) {
                        mRbDebug.setChecked(false);
                        mRbRelease.setChecked(false);
                        mEtAddress.requestFocus();
                    }
                }
            });
            mRg.setOnCheckedChangeListener((radioGroup, id) -> {
                switch (id) {
                    case R.id.rb_debug_select_server:
                        mEtAddress.setText(Common.BASE_DEBUG_URL);
                        break;
                    case R.id.rb_release_select_server:
                        mEtAddress.setText(Common.BASE_RELEASE_URL);
                        break;
                }
            });
            cancel.setOnClickListener(view1 -> alertDialog.dismiss());
            ok.setOnClickListener(view12 -> {
                if (!TextUtils.isEmpty(mEtAddress.getText().toString())) {
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("BASE_URL", mEtAddress.getText().toString().trim());
                    editor.apply();
                    Common.BASE_URL = mEtAddress.getText().toString();
                    alertDialog.dismiss();
                    Toast.makeText(LoginActivity.this, getString(R.string.have_set_server_address_as) + mEtAddress.getText().toString(), Toast.LENGTH_LONG).show();
                } else {
                    CommonUtils.showToast(getString(R.string.input_server_address));
                }
            });
            return true;
        });
    }

    private void startMain() {
        App.hasLogin = true;
        Intent mainIntent = new Intent(this, MainActivity.class);
        mainIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        this.finish();
        startActivity(mainIntent);
    }

    @Override
    public String getUsername() {
        return etAccount.getText().toString().trim();
    }

    @Override
    public String getPassword() {
        return password = XOREncryptAndDecrypt.encrypt(etPassword.getText().toString().trim());
    }

    @Override
    public String getGrantType() {
        return Common.GRANT_TYPE;
    }

    @Override
    public void tokenSucceed() {
        SharedPreferences.Editor editor2 = sp.edit();
        editor2.putString("a", getUsername());
        editor2.putString("b", getPassword());
        editor2.apply();
        btnLogin.setClickable(true);
        etAccount.setText("");
        etPassword.setText("");
        startMain();
    }

    @Override
    public void tokenFailed(String message) {
        btnLogin.setClickable(true);
        CommonUtils.showToast(message);
    }

    @Override
    public void showTokenLoading() {
        CommonUtils.showProgressDialogWithMessage(this, dialog, null);
    }

    @Override
    public void hideTokenLoading() {
        CommonUtils.hideProgressDialog(dialog);
    }

    @Override
    public String getUsernameForInit() {
        return etAccount.getText().toString().trim();
    }

    @Override
    public void initSucceed() {
        btnLogin.setClickable(true);
        CommonUtils.showToast(getString(R.string.login_successfully));
        startMain();
    }

    @Override
    public void initFailed(String message) {
        btnLogin.setClickable(true);
        CommonUtils.showToast(message);
    }

    @Override
    public void showInitLoading() {
        CommonUtils.showProgressDialogWithMessage(this, dialog, null);
    }

    @Override
    public void hideInitLoading() {
        CommonUtils.hideProgressDialog(dialog);
    }

    public static void clearData() {
//        etPassword.setText("");
//        etAccount.setText("");
    }

    @Override
    public void onBackPressed() {
        ActivityUtils.startHomeActivity();
    }
}
