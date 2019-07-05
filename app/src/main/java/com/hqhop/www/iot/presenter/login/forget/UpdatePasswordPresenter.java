package com.hqhop.www.iot.presenter.login.forget;

import android.content.Context;

import com.hqhop.www.iot.R;
import com.hqhop.www.iot.activities.login.forget.IUpdatePasswordView;
import com.hqhop.www.iot.api.login.LoginService;
import com.hqhop.www.iot.model.login.forget.IUpdatePassword;
import com.hqhop.www.iot.model.login.forget.OnUpdatePasswordListener;
import com.hqhop.www.iot.model.login.forget.UpdatePasswordImpl;

/**
 * Created by allen on 2017/7/13.
 */

public class UpdatePasswordPresenter {

    private IUpdatePassword updatePasswordBiz;

    private IUpdatePasswordView updatePasswordView;

    public UpdatePasswordPresenter(IUpdatePasswordView updatePasswordView) {
        this.updatePasswordView = updatePasswordView;
        this.updatePasswordBiz = new UpdatePasswordImpl();
    }

    public void updatePassword(Context context, LoginService service) {
        updatePasswordView.showUpdatePasswordLoading();
        updatePasswordBiz.updatePassword(context, service, updatePasswordView.getVerificationCode(), updatePasswordView.getLoginName(), updatePasswordView.getPassword(), new OnUpdatePasswordListener() {
            @Override
            public void updateSucceed(String message) {
                updatePasswordView.hideUpdatePasswordLoading();
//                updatePasswordView.updatePasswordSucceed(message);
                updatePasswordView.updatePasswordSucceed(context.getResources().getString(R.string.reset_password_successfully));
            }

            @Override
            public void updateFailed(String message) {
                updatePasswordView.hideUpdatePasswordLoading();
//                updatePasswordView.updatePasswordFailed(message);
                updatePasswordView.updatePasswordFailed(context.getResources().getString(R.string.reset_password_failed));
            }
        });
    }
}
