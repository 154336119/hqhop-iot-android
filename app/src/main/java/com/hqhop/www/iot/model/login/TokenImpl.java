package com.hqhop.www.iot.model.login;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.google.gson.JsonSyntaxException;
import com.hqhop.www.iot.App;
import com.hqhop.www.iot.R;
import com.hqhop.www.iot.api.login.LoginService;
import com.hqhop.www.iot.bean.TokenBean;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by allen on 2017/7/13.
 */

public class TokenImpl implements IToken {

    @Override
    public void getToken(Context context, LoginService service, String username, String password, OnGetTokenListener tokenListener) {
        service.getToken(username, password)
                .subscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<TokenBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        if (e instanceof JsonSyntaxException || e instanceof IllegalStateException) {
                            tokenListener.getTokenFailed(context.getString(R.string.account_password_error));
                        } else {
                            tokenListener.getTokenFailed(context.getString(R.string.network_error));
                        }
                    }

                    @Override
                    public void onNext(TokenBean tokenBean) {
                        if (tokenBean.isSuccess()) {
                            String token = tokenBean.getData().getAccess_token();
                            if (!TextUtils.isEmpty(token)) {
                                App.token = token;
                                App.userid = tokenBean.getData().getUserId();
                                App.userName = tokenBean.getData().getUserName();
                                Log.d("Allen", "token=" + token + ", userid=" + tokenBean.getData().getUserId());
                                tokenListener.getTokenSucceed();
                            } else {
                                tokenListener.getTokenFailed(tokenBean.getMessage());
                            }
                        } else {
                            tokenListener.getTokenFailed(tokenBean.getMessage());
                        }
                    }
                });
    }
}
