package com.hqhop.www.iot.activities.main.workbench.station.detail.modules.video;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.drawable.AnimationDrawable;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.hqhop.www.iot.App;
import com.hqhop.www.iot.R;
import com.hqhop.www.iot.base.util.CommonUtils;
import com.huamaitel.api.HMDefines;
import com.umeng.analytics.MobclickAgent;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class PlayActivity extends Activity {
    private static final String TAG = "PlayActivity";

    // Records/Pictures/Logs related.
    public static final String FOLDER_NAME_RECORD = "records";
    public static final String FOLDER_NAME_CAPTURE = "pictures";
    public static final String FOLDER_NAME_LOG = "log";
    public static final int FILE_TYPE_RECORD = 1;
    public static final int FILE_TYPE_CAPTURE = 2;
    public static final int FILE_TYPE_LOG = 3;

    public static final int MSG_SHOW_RECORD_TIME = 11;
    public static final int MSG_STOP_RECORD = 12;
    private Button mbtn_record = null;
    private Button mbtn_capture = null;
    private Button mbtn_arming = null;
    private Button mbtn_disarming = null;
    private Button mbtn_opentalk = null;
    private Button mbtn_closetalk = null;
    private Button mbtn_openlisten = null;
    private Button mbtn_closelisten = null;
    private ImageView mivrecordDot = null;
    private TextView mtvrecordTime = null;

    private boolean mIfLogin = false; // If login...
    private boolean mIsPlaying = false; // Is playing video...
    private boolean mIsListening = false; // Is listening...
    private boolean mIsTalking = false; // Is talking...
    private boolean mIsRecording = false; // Is Recording...
    private boolean mIfExit = false; // If exit the activity...

    private int mNodeId = 0; // The node id.
    private int mChannelIndex = 0; // The channel index.
    private int mVideoStream = HMDefines.CodeStream.MAIN_STREAM; // The video
    // stream
    // (Main/sub
    // video
    // stream.)

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.play_activity);
        // 让屏幕保持不关闭
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON, WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        initView();

        setListener();

        // Get the node id from the DeviceActivity .
        Intent intent = getIntent();
        mNodeId = intent.getIntExtra(App.NODE_ID, 0);
        mChannelIndex = intent.getIntExtra(App.CHANNEL, 0);
        mVideoStream = intent.getIntExtra(App.VIDEO_STREAM, HMDefines.CodeStream.MAIN_STREAM);

        // 登录 & 打开视频
        openVideo();

    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onPageStart(TAG);
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd(TAG);
        MobclickAgent.onPause(this);
    }

    private void initView() {
        mbtn_record = findViewById(R.id.id_btn_record);
        mbtn_capture = findViewById(R.id.id_btn_capture);
        mbtn_arming = findViewById(R.id.id_btn_arming);
        mbtn_disarming = findViewById(R.id.id_btn_disarming);
        mbtn_opentalk = findViewById(R.id.id_btn_opentalk);
        mbtn_closetalk = findViewById(R.id.id_btn_closetalk);
        mbtn_openlisten = findViewById(R.id.id_btn_openlisten);
        mbtn_closelisten = findViewById(R.id.id_btn_closelisten);
        mtvrecordTime = findViewById(R.id.record_time);
        mivrecordDot = findViewById(R.id.record_dot);
        mivrecordDot.setBackgroundResource(R.drawable.record_anim);
        mivrecordDot.setImageDrawable(null);

    }

    private void setListener() {
        /**
         * 打开录像
         */
        mbtn_record.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                String fileName = App.getJni().getNodeName(App.curNodeHandle);
                String path = getFilePath(FILE_TYPE_RECORD, fileName);
                if (mIsRecording) {
                    App.getJni().stopLocalRecord(App.mRecordHandle);
                    mIsRecording = false;
                    mUIHandler.sendEmptyMessage(MSG_STOP_RECORD);
                    Toast.makeText(getApplicationContext(), getString(R.string.stop_video_save_at) + path, Toast.LENGTH_SHORT).show();
                } else {
                    App.mCapturePath = path;
                    App.mRecordHandle = App.getJni().startLocalRecord(App.mUserId, path);

                    if (App.mRecordHandle == 0) {
                        Log.e(TAG, "start local record fail.");
                    } else {
                        Log.i(TAG, "start local record success.");
                        showRecordAnim(true);
                        Toast.makeText(getApplicationContext(), getString(R.string.start_video), Toast.LENGTH_SHORT).show();
                        showRecordTime();
                        mIsRecording = true;
                    }
                }
            }

            /*
             * Show record time text
             */
            private void showRecordTime() {
                new Thread() {
                    @Override
                    public void run() {
                        super.run();
                        while (mIsRecording) {
                            mUIHandler.sendEmptyMessage(MSG_SHOW_RECORD_TIME);
                            try {
                                sleep(1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }.start();
            }
        });

        /**
         * ����
         */
        mbtn_capture.setOnClickListener(view -> {
            String fileName = App.getJni().getNodeName(App.curNodeHandle);
            String path = getFilePath(FILE_TYPE_CAPTURE, fileName);
            App.mCapturePath = path;

            byte data[] = App.getJni().localCapture(App.mUserId);
            if (null == data) {
                Toast.makeText(getApplicationContext(), getString(R.string.capture_failed), Toast.LENGTH_SHORT).show();
            } else {
                boolean res = saveCapturedPic(data, App.mCapturePath);
                if (res) {
                    Log.i(TAG, "Local capture success." + getString(R.string.capture_succeed_save_at) + path);
                    Toast.makeText(getApplicationContext(), getString(R.string.capture_succeed_save_at) + path, Toast.LENGTH_SHORT).show();
                } else {
                    Log.e(TAG, "Local capture fail.");
                }
            }
        });

        /**
         * 打开听
         */
        mbtn_openlisten.setOnClickListener(view -> {
            startListen();
            mbtn_openlisten.setEnabled(false);
            mbtn_closelisten.setEnabled(true);

        });

        /**
         * 关闭听
         */
        mbtn_closelisten.setOnClickListener(view -> {
            stopListen();

            mbtn_closelisten.setEnabled(false);
            mbtn_openlisten.setEnabled(true);
        });

        /**
         * 打开说
         */
        mbtn_opentalk.setOnClickListener(view -> {
            startTalk();

            mbtn_opentalk.setEnabled(false);
            mbtn_closetalk.setEnabled(true);
        });

        /**
         * 关闭说
         */
        mbtn_closetalk.setOnClickListener(view -> {
            stopTalk();
            mbtn_opentalk.setEnabled(true);
            mbtn_closetalk.setEnabled(false);
        });

        /**
         * 布防
         */
        mbtn_arming.setOnClickListener(view -> {
            App.getJni().arming(App.mUserId, 1, "");
            CommonUtils.showToast(getString(R.string.protection_succeed));
            mbtn_arming.setEnabled(false);
            mbtn_disarming.setEnabled(true);
        });

        /**
         * 撤防
         */
        mbtn_disarming.setOnClickListener(view -> {
            App.getJni().disarming(App.mUserId, 1, "");
            CommonUtils.showToast(getString(R.string.removal_succeed));
            mbtn_disarming.setEnabled(false);
            mbtn_arming.setEnabled(true);
        });

    }

    private void startListen() {
        new Thread() {
            public void run() {
                HMDefines.OpenAudioParam param = new HMDefines.OpenAudioParam();
                param.channel = mChannelIndex;
                HMDefines.OpenAudioRes res = new HMDefines.OpenAudioRes();

                App.mAudioHandle = App.getJni().startAudio(App.mUserId, param, res);

                if (App.mAudioHandle == -1) {
                    mIsListening = false;
                    Log.e(TAG, "抱歉，该设备不支持音频功能");
                    return;
                } else {
                    mIsListening = true;
                }
            }
        }.start();
    }

    private void stopListen() {
        int ret = App.getJni().stopAudio(App.mTalkHandle);
        if (ret != 0) {
            Log.e(TAG, "close the audio fail");
        } else {
            Log.i(TAG, "close the audio success");
        }
        mIsListening = false;
    }

    private void startTalk() {
        new Thread() {
            public void run() {
                HMDefines.OpenTalkParam param = new HMDefines.OpenTalkParam();
                param.channel = mChannelIndex;
                param.audioEncode = App.mChannelCapacity[0].audioCodeType;
                param.sample = App.mChannelCapacity[0].audioSample;
                param.audioChannel = App.mChannelCapacity[0].audioChannel;
                App.mTalkHandle = App.getJni().startTalk(App.mUserId, param);
                if (App.mTalkHandle == -1) {
                    mIsTalking = false;
                    return;
                } else {
                    mIsTalking = true;
                }
            }
        }.start();
    }

    private void stopTalk() {
        App.getJni().stopTalk(App.mTalkHandle);
        mIsTalking = false;
    }

    private void openVideo() {
        new Thread() {
            @Override
            public void run() {
                super.run();

                if (App.mIsUserLogin) {
                    // 从互联网登录
                    if (mNodeId == 0) {
                        return;
                    }
                    // Step 1: Login the device.

                    App.mUserId = App.getJni().loginEx(mNodeId, HMDefines.ConnectMode.CONN_MODE_DEFAULT);
                    if (App.mUserId == 0) {
                        return;
                    }
                } else {
                    /**
                     * 从局域网登录
                     */
                    String sIp = getIntent().getStringExtra("ip");
                    String sPort = getIntent().getStringExtra("port");
                    String sUser = getIntent().getStringExtra("user");
                    String sPass = getIntent().getStringExtra("pass");
                    String sSn = getIntent().getStringExtra("sn");
                    if (sIp != null && sPort != null && sSn != null && sUser != null) {
                        // Step 1: Login the device.
                        App.mUserId = App.getJni().login(sIp, Short.parseShort(sPort), sSn, sUser, sPass);
                        if (App.mUserId == 0) {
                            return;
                        }
                    }
                }

                mIfLogin = true;

                /**
                 * Step 2: Get device information.
                 */
                App.mDeviceInfo = App.getJni().getDeviceInfo(App.mUserId);
                App.mChannelCapacity = App.getJni().getChannelCapacity(App.mUserId);
                if (App.mDeviceInfo == null) {
                    return;
                }
                // Step 3: Start video
                HMDefines.OpenVideoParam param = new HMDefines.OpenVideoParam();
                param.channel = mChannelIndex;
                param.codeStream = HMDefines.CodeStream.MAIN_STREAM;
                param.videoType = HMDefines.VideoStream.REAL_STREAM;
                HMDefines.OpenVideoRes res = new HMDefines.OpenVideoRes();

                App.mVideoHandle = App.getJni().startVideo(App.mUserId, param, res);

                if (App.mVideoHandle == -1) {
                    mIsPlaying = false;
                    return;
                }
                mIsPlaying = true;
            }
        }.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this).setTitle(getString(R.string.exit_hm_video)).setIcon(android.R.drawable.ic_dialog_info).setPositiveButton(getString(R.string.confirm), (dialog, which) -> {
            // Exit the play activity for exiting.
            if (!mIfExit) {
                exitPlayActivity();
            }
            PlayActivity.this.finish();
        }).setNegativeButton(getString(R.string.cancel), (dialog, which) -> {
            // do nothing
        }).show();
    }

    private void exitPlayActivity() {
        // Avoid calling this function many times.
        mIfExit = true;

        // Close some resources.
        new Thread() {
            public void run() {
                releaseResources();
            }

        }.start();

        // Back to the device list activity.
        backtoDeviceList();
    }

    private void backtoDeviceList() {
        PlayActivity.this.finish();
    }

    private void releaseResources() {
        if (mIsPlaying) {
            int result = App.getJni().stopVideo(App.mVideoHandle);
            if (result != 0) {
                Log.e(TAG, "stopvideo fail.");
            } else {
                Log.i(TAG, "stopvideo success.");
            }
            mIsPlaying = false;
        }

        if (mIsRecording) {
            int result = App.getJni().stopLocalRecord(App.mRecordHandle);
            if (result != 0) {
                Log.e(TAG, "stopLocalRecord fail.");
            } else {
                Log.i(TAG, "stopLocalRecord success.");
            }
            mIsRecording = false;
        }

        if (mIsTalking) {

            int result = App.getJni().stopTalk(App.mTalkHandle);
            if (result != 0) {
                Log.e(TAG, "stopTalk fail.");
            } else {
                Log.i(TAG, "stopTalk success.");
            }
            mIsTalking = false;
        }
        if (mIsListening) {
            int result = App.getJni().stopAudio(App.mAudioHandle);
            if (result != 0) {
                Log.e(TAG, "stopAudio fail.");
            } else {
                Log.i(TAG, "stopAudio success.");
            }

            mIsListening = false;
        }

        if (mIfLogin) {
            int result = App.getJni().logout(App.mUserId);
            if (result != 0) {
                Log.e(TAG, "logout fail.");
            } else {
                Log.i(TAG, "logout success.");
            }
            mIfExit = false;
        }

    }

    /**
     * 显示录像时间
     */
    @SuppressLint("HandlerLeak")
    public Handler mUIHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_SHOW_RECORD_TIME:
                    int time = App.getJni().getLocalRecordTime(App.mRecordHandle);
                    String timString = Duration2Time(time);
                    mtvrecordTime.setText(timString);
                    break;

                case MSG_STOP_RECORD:
                    int result = App.getJni().stopLocalRecord(App.mUserId);
                    if (result != 0) {
                        Log.e(TAG, "close local record fail.");
                    } else {
                        Log.i(TAG, "close local record success.");
                    }

                    mIsRecording = false;
                    showRecordAnim(false);
                    break;
            }
        }

    };

    /************************************************* 工具方法 *******************************************************/
    /*
     * 将byte的数据保存为为Jpg、Png等图片格式。
	 */
    public static boolean saveCapturedPic(byte data[], String path) {
        Bitmap bmp = BitmapFactory.decodeByteArray(data, 0, data.length);
        FileOutputStream out = null;

        if (null == bmp) {
            Log.e(TAG, "bitmap is null.");
            return false;
        }

        try {
            out = new FileOutputStream(path);
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
            return false;
        }

        bmp.compress(CompressFormat.PNG, 80, out);

        try {
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return true;
    }

    /**
     * 时间格式转换
     *
     * @param duration
     * @return
     */
    public static String Duration2Time(int duration) {
        String time = "";
        long ms = duration * 1000;

        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
        formatter.setTimeZone(TimeZone.getTimeZone("GMT+00:00"));
        time = formatter.format(ms);

        return time;
    }

    /**
     * 是否开始时间动画
     *
     * @param isStart
     */
    private void showRecordAnim(boolean isStart) {
        AnimationDrawable animator = (AnimationDrawable) mivrecordDot.getBackground();

        if (isStart) {
            mtvrecordTime.setVisibility(View.VISIBLE);
            mivrecordDot.setVisibility(View.VISIBLE);
            animator.start();
        } else {
            mtvrecordTime.setVisibility(View.GONE);
            mivrecordDot.setVisibility(View.GONE);
            animator.stop();
        }
    }

    /*
     * Generate the path to save video/picture/log.
	 */
    public static String getFilePath(int fileType, String fileName) {
        String path = "";
        String sdPath = "";

        // Get the path of SD card.
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            sdPath = Environment.getExternalStorageDirectory().getAbsolutePath();
        } else {
            return path;
        }
        // Generate the path of Android client.
        path = sdPath + File.separator + "HMSDKDemo";
        switch (fileType) {
            case FILE_TYPE_RECORD:
                path += File.separator + FOLDER_NAME_RECORD;
                break;

            case FILE_TYPE_CAPTURE:
                path += File.separator + FOLDER_NAME_CAPTURE;
                break;

            case FILE_TYPE_LOG:
                path += File.separator + FOLDER_NAME_LOG;
                break;

            default:
                break;
        }

        // Make sure the path is exist.
        File f = new File(path);
        if (!f.exists()) {
            f.mkdirs();
        }

        // Generate the file name.
        SimpleDateFormat formatter = new SimpleDateFormat("yyMMddHHmmss");
        Date curDate = new Date(System.currentTimeMillis());
        String localTime = formatter.format(curDate);
        String filename = fileName + localTime;

        // Attach the extension name.
        switch (fileType) {
            case FILE_TYPE_RECORD:
                path += File.separator + filename + ".hmv";
                break;

            case FILE_TYPE_CAPTURE:
                path += File.separator + filename + ".png";
                break;

            case FILE_TYPE_LOG:
                path += File.separator + filename + ".txt";
                break;

            default:
                break;
        }

        return path;
    }

}

class PlayView extends GLSurfaceView {
    private PlayRenderer playRenderer;
    private boolean isFirstIn = true;

    public PlayView(Context context, AttributeSet attrs) {
        super(context);

        // Create an OpenGL ES 2.0 context.
        setEGLContextClientVersion(2);

        // Set the Renderer for drawing on the GLSurfaceView
        playRenderer = new PlayRenderer();
        setRenderer(playRenderer);

        // Set render mode.
        setRenderMode(RENDERMODE_WHEN_DIRTY);

        // Register the OpenGL render callback.
        App.getJni().setRenderCallback(() -> {
            requestRender(); // Force to render if video data comes.
        });
    }

    // 这个接口定义了在一个OpenGL的GLSurfaceView中绘制图形所需要的方法。
    class PlayRenderer implements Renderer {

        // 设置OpenGL的环境变量，或是初始化OpenGL的图形物体。
        public void onSurfaceChanged(GL10 gl, int w, int h) {
            App.getJni().gLResize(w, h);
        }

        // 这个方法主要完成绘制图形的操作。
        public void onDrawFrame(GL10 gl) {
            if (isFirstIn) {
                isFirstIn = false;
                return;
            }

            App.getJni().gLRender();
        }

        // 主要用来对GLSurfaceView容器的变化进行响应。
        @Override
        public void onSurfaceCreated(GL10 gl, EGLConfig config) {
            App.getJni().gLInit();
        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        super.surfaceDestroyed(holder);
        App.getJni().gLUninit();
    }
}

