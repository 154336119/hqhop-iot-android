package com.hqhop.www.iot.activities

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.TextUtils
import android.util.Log
import com.hqhop.www.iot.App
import com.hqhop.www.iot.R
import com.hqhop.www.iot.activities.login.LoginActivity
import com.hqhop.www.iot.activities.main.MainActivity
import com.hqhop.www.iot.api.login.LoginService
import com.hqhop.www.iot.base.Common
import com.hqhop.www.iot.base.http.RetrofitManager
import com.hqhop.www.iot.bean.TokenBean
import rx.Subscriber
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

class SplashActivity : AppCompatActivity() {

    private var loginService: LoginService? = null

    private var sp: SharedPreferences? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        loginService = RetrofitManager.getInstance(this).createService(LoginService::class.java)
        sp = getSharedPreferences(Common.SP_USER_INFO, Context.MODE_PRIVATE)
        val a = sp?.getString("a", "")
        val b = sp?.getString("b", "")
        if (TextUtils.isEmpty(a) || TextUtils.isEmpty(b)) {
            gotoLogin()
        } else {
            loginService?.getToken(a, b)
                    ?.subscribeOn(Schedulers.io())
                    ?.subscribeOn(AndroidSchedulers.mainThread())
                    ?.observeOn(AndroidSchedulers.mainThread())
                    ?.subscribe(object : Subscriber<TokenBean>() {
                        override fun onCompleted() {

                        }

                        override fun onNext(tokenBean: TokenBean?) {
                            if (tokenBean?.isSuccess!!) {
                                val token = tokenBean.data.access_token
                                if (!TextUtils.isEmpty(token)) {
                                    App.token = token
                                    App.userid = tokenBean.data.userId
                                    App.userName = tokenBean.data.userName
                                    App.hasLogin = true
                                    startActivity(Intent(this@SplashActivity, MainActivity::class.java))
                                } else {
                                    gotoLogin()
                                }
                            } else {
                                gotoLogin()
                            }
                        }

                        override fun onError(e: Throwable?) {
                            gotoLogin()
                        }
                    })
        }
    }

    private fun gotoLogin() {
//        editor = sp?.edit()
//        editor?.putString("a", "")
//        editor?.putString("b", "")
//        editor?.apply()
        val loginIntent = Intent(this, LoginActivity::class.java)
        startActivity(loginIntent)
    }
}
