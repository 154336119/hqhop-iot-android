package com.hqhop.www.iot.activities.main.about

import android.os.Bundle
import android.text.TextUtils
import com.hqhop.www.iot.App
import com.hqhop.www.iot.R
import com.hqhop.www.iot.api.about.AboutService
import com.hqhop.www.iot.base.http.RetrofitManager
import com.hqhop.www.iot.base.util.CommonUtils
import com.hqhop.www.iot.base.view.BaseAppCompatActivity
import com.hqhop.www.iot.bean.FeedbackBean
import kotlinx.android.synthetic.main.activity_feedback.*
import rx.Subscriber
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

class FeedbackActivity : BaseAppCompatActivity() {

    private var aboutService: AboutService? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setToolBarTitle(getString(R.string.about_feedback))

        aboutService = RetrofitManager.getInstance(this).createService(AboutService::class.java)

        btn_feedback.setOnClickListener {
            if (TextUtils.isEmpty(et_feedback.text.toString())) {
                CommonUtils.showToast(getString(R.string.input_feedback))
            } else {
                aboutService?.feedback(App.userName, App.DEVICE_ID, "android", et_feedback.text.toString())
                        ?.subscribeOn(Schedulers.io())
                        ?.subscribeOn(AndroidSchedulers.mainThread())
                        ?.observeOn(AndroidSchedulers.mainThread())
                        ?.subscribe(object : Subscriber<FeedbackBean>() {
                            override fun onCompleted() {

                            }

                            override fun onNext(bean: FeedbackBean?) {
                                if (bean?.isSuccess!!) {
                                    CommonUtils.showToast("感谢您的建议")
                                    finish()
                                }
                            }

                            override fun onError(e: Throwable?) {

                            }
                        })
            }
        }
    }

    override fun isShowBacking(): Boolean {
        return true
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_feedback
    }
}
