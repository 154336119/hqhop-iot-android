package com.hqhop.www.iot.presenter.login;

import android.content.Context;

import com.hqhop.www.iot.activities.login.ITokenView;
import com.hqhop.www.iot.api.login.LoginService;
import com.hqhop.www.iot.model.login.IToken;
import com.hqhop.www.iot.model.login.OnGetTokenListener;
import com.hqhop.www.iot.model.login.TokenImpl;

/**
 * Created by allen on 2017/7/13.
 */

public class TokenPresenter {

    private IToken tokenBiz;

    private ITokenView tokenView;

    public TokenPresenter(ITokenView tokenView) {
        this.tokenView = tokenView;
        this.tokenBiz = new TokenImpl();
    }

    public void getToken(Context context, LoginService service) {
        tokenView.showTokenLoading();
//        tokenBiz.getToken(context, service, tokenView.getUsername(), tokenView.getPassword(), tokenView.getGrantType(), new OnGetTokenListener() {
        tokenBiz.getToken(context, service, tokenView.getUsername(), tokenView.getPassword(), new OnGetTokenListener() {
            @Override
            public void getTokenSucceed() {
                    tokenView.hideTokenLoading();
                tokenView.tokenSucceed();
        }

        @Override
            public void getTokenFailed(String message) {
                tokenView.hideTokenLoading();
                tokenView.tokenFailed(message);
            }
        });
    }

}
