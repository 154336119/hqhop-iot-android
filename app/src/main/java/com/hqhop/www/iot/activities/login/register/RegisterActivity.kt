package com.hqhop.www.iot.activities.login.register

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.KeyEvent
import android.view.View
import com.blankj.utilcode.util.ToastUtils
import com.hqhop.www.iot.App
import com.hqhop.www.iot.BuildConfig
import com.hqhop.www.iot.R
import com.hqhop.www.iot.activities.main.about.NewScanQRCodeActivity
import com.hqhop.www.iot.api.login.RegisterService
import com.hqhop.www.iot.api.register.InviteRegisterService
import com.hqhop.www.iot.base.Common
import com.hqhop.www.iot.base.http.RetrofitManager
import com.hqhop.www.iot.base.util.CommonUtils
import com.hqhop.www.iot.base.util.CountDownUtils
import com.hqhop.www.iot.base.util.XOREncryptAndDecrypt
import com.hqhop.www.iot.base.view.BaseAppCompatActivity
import com.hqhop.www.iot.bean.CheckInvitePermissionBean
import com.hqhop.www.iot.bean.RegisterBean
import com.hqhop.www.iot.bean.VerificationCodeBean
import kotlinx.android.synthetic.main.activity_register.*
import rx.Subscriber
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

class RegisterActivity : BaseAppCompatActivity(), View.OnKeyListener {

    private var countDownUtils: CountDownUtils? = null

    private lateinit var registerService: RegisterService

    private var isChecked = true

    private var inviteRegisterService: InviteRegisterService? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()
    }

    private fun init() {
        setToolBarTitle(getString(R.string.register))

        registerService = RetrofitManager.getInstance(this).createService(RegisterService::class.java)
        inviteRegisterService = RetrofitManager.getInstance(this).createService(InviteRegisterService::class.java)

        iv_protocol_register.setOnClickListener {
            if (isChecked) {
                isChecked = false
                iv_protocol_register.setImageResource(R.drawable.protocol_unselected)
            } else {
                isChecked = true
                iv_protocol_register.setImageResource(R.drawable.protocol_selected)
            }
        }

        btn_get_verify_register.setOnClickListener {
            getVerificationCode()
        }

        btn_register_register.setOnClickListener {
            if (isChecked) {
                register()
            } else {
                CommonUtils.showToast("请先同意用户协议")
            }
        }
        tv_protocol_register.setOnClickListener {
            val protocolIntent = Intent(this, WebActivity::class.java)
            protocolIntent.putExtra("url", "template/knowledge/Knowledge.html")
            startActivity(protocolIntent)
        }
        btn_scan_register.setOnClickListener {
            val scanIntent = Intent(this, NewScanQRCodeActivity::class.java)
            startActivityForResult(scanIntent, Common.REQUEST_CODE_QRCODE)
        }

        et_invite_register.setOnKeyListener(this)
    }

    override fun onKey(v: View?, keyCode: Int, keyEvent: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_ENTER && keyEvent?.action == KeyEvent.ACTION_UP) {
            when (v?.id) {
                R.id.et_invite_register -> {
                    register()
                }
            }
        }
        return false
    }

    /**
     * 获取验证码
     */
    private fun getVerificationCode() {
        if (TextUtils.isEmpty(et_phone_register.text)) {
            CommonUtils.showToast(getString(R.string.phone_cant_be_empty))
            return
        }
        CommonUtils.hideKeyboard(this)

        if (countDownUtils == null) {
            countDownUtils = CountDownUtils(btn_get_verify_register, Common.TIME_INTERVAL_VERIFICATION_CODE.toLong(), Common.TIME_INTERVAL_REFRESH_COUNTDOWN.toLong())
        }
        countDownUtils?.start()

        registerService.getVerificationCode(et_phone_register.text.toString())
                .subscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Subscriber<VerificationCodeBean>() {
                    override fun onNext(bean: VerificationCodeBean?) {
                        if (bean?.isSuccess!!) {
                            CommonUtils.showToast(bean.message)
                        } else {
                            CommonUtils.showToast(bean.message)
                        }
                    }

                    override fun onError(e: Throwable?) {
                        CommonUtils.showToast(getString(R.string.network_error))
                        if (countDownUtils != null) {
                            countDownUtils?.cancel()
                            countDownUtils = null
                            btn_get_verify_register.setText(R.string.get_verification_code_again)
                            btn_get_verify_register.isClickable = true
                        }
                    }

                    override fun onCompleted() {

                    }
                })
    }

    /**
     * 注册
     */
    private fun register() {
        if (TextUtils.isEmpty(et_phone_register.text)) { // 手机号为空
            CommonUtils.showToast(getString(R.string.phone_cant_be_empty))
            return
        }
        if (TextUtils.isEmpty(et_verify_register.text)) { // 验证码为空
            CommonUtils.showToast(getString(R.string.verification_code_cant_empty))
            return
        }
        if (TextUtils.isEmpty(et_password_register.text) || TextUtils.isEmpty(et_repeat_password_register.text)) { // 密码为空
            CommonUtils.showToast(getString(R.string.password_cant_be_empty))
            return
        }
        if (et_password_register.text.toString() != et_repeat_password_register.text.toString()) { // 密码不一致
            CommonUtils.showToast(getString(R.string.password_must_be_same))
            return
        }
        if (TextUtils.isEmpty(et_invite_register.text)) { // 邀请码为空
            CommonUtils.showToast(getString(R.string.invitation_code_cant_be_empty))
            return
        }

        registerService.register(et_phone_register.text.toString(), et_verify_register.text.toString(), XOREncryptAndDecrypt.encrypt(et_repeat_password_register.text.toString()), et_invite_register.text.toString(), 1, App.DEVICE_ID)
                .subscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Subscriber<RegisterBean>() {
                    override fun onNext(bean: RegisterBean?) {
                        if (bean != null) {
                            CommonUtils.showToast(bean.message)
                            if (bean.isSuccess) {
                                // 注册成功
                                finish()
                            }
                        } else {
                            CommonUtils.showToast(getString(R.string.unknown_error))
                        }
                    }

                    override fun onError(e: Throwable?) {
                        CommonUtils.showToast(getString(R.string.network_error))
                    }

                    override fun onCompleted() {

                    }
                })
    }

    /**
     * 检查二维码状态
     */
    private fun checkQRCodeStatus(userId: String) {
        inviteRegisterService?.checkQRCodeStatus(userId)
                ?.subscribeOn(Schedulers.io())
                ?.subscribeOn(AndroidSchedulers.mainThread())
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.subscribe(object : Subscriber<CheckInvitePermissionBean>() {
                    override fun onError(e: Throwable?) {

                    }

                    override fun onCompleted() {

                    }

                    override fun onNext(bean: CheckInvitePermissionBean?) {
                        if (bean?.isSuccess == true) {
                            container_information_register.visibility = View.GONE
                            tv_wait_register.visibility = View.VISIBLE
                        }
                    }
                })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == Common.REQUEST_CODE_QRCODE) {
            if (resultCode == Activity.RESULT_OK) {
                val qrcodeId = data?.getStringExtra("result")
                if (TextUtils.isEmpty(qrcodeId)) {
                    ToastUtils.showShort("二维码扫描失败")
                } else {
                    checkQRCodeStatus(qrcodeId!!)
                }
            }
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_register
    }

    override fun isShowBacking(): Boolean {
        return true
    }
}
