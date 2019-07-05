package com.hqhop.www.iot.presenter.login.forget;

import android.content.Context;

import com.hqhop.www.iot.activities.login.forget.IGetVeriCodeView;
import com.hqhop.www.iot.api.login.LoginService;
import com.hqhop.www.iot.model.login.forget.GetVeriCodeImpl;
import com.hqhop.www.iot.model.login.forget.IGetVeriCode;
import com.hqhop.www.iot.model.login.forget.OnGetVeriCodeListener;

/**
 * Created by allen on 2017/7/13.
 */

public class GetVeriCodePresenter {

    private IGetVeriCode getVeriCodeBiz;

    private IGetVeriCodeView getVeriCodeView;

    public GetVeriCodePresenter(IGetVeriCodeView getVeriCodeView) {
        this.getVeriCodeView = getVeriCodeView;
        this.getVeriCodeBiz = new GetVeriCodeImpl();
    }

    public void getCode(Context context, LoginService service) {
        getVeriCodeView.showGetCodeLoading();
        getVeriCodeBiz.getVeriCode(context, service, getVeriCodeView.getAccount(), new OnGetVeriCodeListener() {
            @Override
            public void getVeriCodeSucceed(String message) {
                getVeriCodeView.hideGetCodeLoading();
                getVeriCodeView.getCodeSucceed(message);
            }

            @Override
            public void getVeriCodeFailed(String message) {
                getVeriCodeView.hideGetCodeLoading();
                getVeriCodeView.getCodeFailed(message);
            }
        });
    }
}
