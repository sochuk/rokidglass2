package com.rokid.glass.ui.sample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.rokid.glass.ui.dialog.GlassDialog;
import com.rokid.glass.ui.dialog.GlassDialogListener;
import com.rokid.glass.ui.util.CountDownManager;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private GlassDialog mNotificationDialog;
    private GlassDialog mSimpleVoiceDialog;
    private GlassDialog.SimpleVoiceDialogBuilder mSimpleVoiceDialogBuilder;

    private View mCustomTimer;
    private TextView mTimerTv;
    private CountDownManager countDownManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.notification_btn).setOnClickListener(this);
        findViewById(R.id.simple_voice_btn).setOnClickListener(this);

        mCustomTimer = LayoutInflater.from(this).inflate(R.layout.layout_timer, null);
        mTimerTv = mCustomTimer.findViewById(R.id.custom_timer);

        countDownManager = new CountDownManager.Builder()
                .setMillisInFuture(10000)
                .setCountDownInterval(1000)
                .setCountDownListener(new CountDownManager.CountDownListener() {
                    @Override
                    public void onTick(long millisUntilFinished) {
                        mTimerTv.setText(String.valueOf(millisUntilFinished / 1000));
                    }

                    @Override
                    public void onFinish() {
                        mSimpleVoiceDialogBuilder.dynamicTitle("播报完毕");
                        mSimpleVoiceDialogBuilder.dynamicConfirmText("重播");
                    }
                })
                .build();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.notification_btn:
                mNotificationDialog = new GlassDialog.NotificationDialogBuilder(this)
                        .setTitle("您收到一条新的语音通知")
                        .setMessage("请在手机APP查看详情")
                        .create();
                mNotificationDialog.show();
                break;
            case R.id.simple_voice_btn:
                mSimpleVoiceDialogBuilder = new GlassDialog.SimpleVoiceDialogBuilder(this)
                        .setTitle(getString(R.string.voice_test))
                        .setConfirmText(getString(R.string.voice_play))
                        .setCancelText(getString(R.string.voice_collapse))
                        .setConfirmListener(new GlassDialogListener() {
                            @Override
                            public void onClick(View view) {
                                Toast.makeText(MainActivity.this,
                                        "Click Confirm", Toast.LENGTH_SHORT).show();

                                mSimpleVoiceDialogBuilder.dynamicTitle("播放中...");
                                mSimpleVoiceDialogBuilder.dynamicCustomConfirmView(mCustomTimer);

                                countDownManager.start();
                            }
                        })
                        .setCancelListener(new GlassDialogListener() {
                            @Override
                            public void onClick(View view) {
                                Toast.makeText(MainActivity.this,
                                        "Click Cancel", Toast.LENGTH_SHORT).show();
                                if (null != countDownManager) {
                                    countDownManager.cancel();
                                }
                            }
                        });

                mSimpleVoiceDialogBuilder.show();
                break;
        }
    }
}
