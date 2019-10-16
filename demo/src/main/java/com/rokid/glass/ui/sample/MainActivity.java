package com.rokid.glass.ui.sample;

import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.rokid.glass.ui.dialog.GlassDialog;
import com.rokid.glass.ui.dialog.GlassDialogListener;
import com.rokid.glass.ui.util.CountDownManager;
import com.rokid.glass.ui.util.RokidSystem;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private GlassDialog mNotificationDialog;
    private GlassDialog mSimpleVoiceDialog;
    private GlassDialog.SimpleVoiceDialogBuilder mSimpleVoiceDialogBuilder;
    private GlassDialog.CustomerVoiceDialogBuilder mCustomerVoiceDialogBuilder;

    private GlassDialog mImageDialog;
    private GlassDialog.ImageDialogBuilder mImageDialogBuilder;

    private GlassDialog mCustomerMessageDialog;

    private GlassDialog.CustomerImageDialogBuilder mCustomerImageDialogBuilder;

    private View mCustomTimerView;
    private TextView mTimerTv;
    private CountDownManager countDownManager;

    private TextView mGlassInfoTv;
    private GlassDialog mTitleDialog;
    private GlassDialog mSingleDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.notification_btn).setOnClickListener(this);
        findViewById(R.id.simple_voice_btn).setOnClickListener(this);
        findViewById(R.id.image_btn).setOnClickListener(this);
        findViewById(R.id.simple_message_btn).setOnClickListener(this);
        findViewById(R.id.simple_content_btn).setOnClickListener(this);
        findViewById(R.id.image_content_btn).setOnClickListener(this);
        findViewById(R.id.customer_message_btn).setOnClickListener(this);
        findViewById(R.id.customer_voice_btn).setOnClickListener(this);
        findViewById(R.id.customer_image_btn).setOnClickListener(this);
        findViewById(R.id.customer_image_content_btn).setOnClickListener(this);

        mGlassInfoTv = findViewById(R.id.glass_info);
        mGlassInfoTv.setText(RokidSystem.getHardwareVersion());

        mCustomTimerView = LayoutInflater.from(this).inflate(R.layout.layout_timer, null);
        mTimerTv = mCustomTimerView.findViewById(R.id.custom_timer);

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
                        if (null != mSimpleVoiceDialogBuilder) {
                            mSimpleVoiceDialogBuilder.dynamicTitle(getString(R.string.voice_play_over));
                            mSimpleVoiceDialogBuilder.dynamicConfirmText(getString(R.string.voice_replay));
                        }

                        if (null != mImageDialogBuilder) {
                            //单独处理
                            mImageDialogBuilder.dynamicConfirmText(getString(R.string.voice_replay));
                        }

                        if (null != mCustomerImageDialogBuilder) {
                            //customer
                            mCustomerImageDialogBuilder.dynamicConfirmText(getString(R.string.voice_replay));
                        }
                    }
                })
                .build();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.alignment_btn:
                Rect rect = new Rect(776, 430, 900, 554);
                //real rect  Rect(1001,318,1398,713)
                break;
            case R.id.notification_btn:
                mNotificationDialog = new GlassDialog.NotificationDialogBuilder(this)
                        .setTitle(getString(R.string.notification_title))
                        .setMessage(getString(R.string.notification_message))
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

                                mSimpleVoiceDialogBuilder.dynamicTitle(getString(R.string.voice_playing));
                                mSimpleVoiceDialogBuilder.dynamicCustomConfirmView(mCustomTimerView);

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
            case R.id.image_btn:
                mImageDialogBuilder = new GlassDialog.ImageDialogBuilder(this)
                        .setTitle(getString(R.string.image_title))
                        .setConfirmText(getString(R.string.voice_play))
                        .setCancelText(getString(R.string.voice_collapse))
                        .setNotifyResId(R.mipmap.ic_notify_img)
                        .setConfirmListener(new GlassDialogListener() {
                            @Override
                            public void onClick(View view) {
                                Toast.makeText(MainActivity.this,
                                        "Click Confirm", Toast.LENGTH_SHORT).show();

                                mImageDialogBuilder.dynamicCustomConfirmView(mCustomTimerView);
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

                mImageDialogBuilder.show();
                break;
//            case R.id.simple_message_btn:
//                mTitleDialog = new GlassDialog.SimpleMessageDialogBuilder(this)
//                        .setTitle("此操作将清空设备所有数据\n是否确认？")
//                        .setConfirmText(getString(R.string.voice_play))
//                        .setConfirmListener(new GlassDialogListener() {
//                            @Override
//                            public void onClick(View view) {
//                                Toast.makeText(MainActivity.this,
//                                        "Click Confirm", Toast.LENGTH_SHORT).show();
//                                mTitleDialog.dismiss();
//                            }
//                        }).show();
//                break;
            case R.id.simple_message_btn:
                mSingleDialog = new GlassDialog.SingleContentDialogBuilder(this)
                        .setTitle("此操作将清空设备所有数据\n是否确认？")
//                        .setTitle("此操作将清空设备所有数据")
                        .setCancelText("ddd")
                        .setConfirmText(getString(R.string.voice_play))
                        .setConfirmListener(new GlassDialogListener() {
                            @Override
                            public void onClick(View view) {
                                Toast.makeText(MainActivity.this,
                                        "Click Confirm", Toast.LENGTH_SHORT).show();
                                mSingleDialog.dismiss();
                            }
                        }).show();
                break;
            case R.id.simple_content_btn:
                new GlassDialog.SimpleContentDialogBuilder(this)
                        .setTitle(getString(R.string.simple_message_title))
                        .setConfirmText(getString(R.string.voice_play))
                        .setCancelText(getString(R.string.voice_collapse))
                        .setContent(getString(R.string.simple_content))
                        .setConfirmListener(new GlassDialogListener() {
                            @Override
                            public void onClick(View view) {
                                Toast.makeText(MainActivity.this,
                                        "Click Confirm", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setCancelListener(new GlassDialogListener() {
                            @Override
                            public void onClick(View view) {
                                Toast.makeText(MainActivity.this,
                                        "Click Cancel", Toast.LENGTH_SHORT).show();

                            }
                        }).show();
                break;
            case R.id.image_content_btn:
                new GlassDialog.ImageContentDialogBuilder(this)
                        .setTitle(getString(R.string.image_content_title))
                        .setConfirmText(getString(R.string.voice_play))
                        .setCancelText(getString(R.string.voice_collapse))
                        .setNotifyResId(R.mipmap.ic_notify_img)
                        .setContent(getString(R.string.simple_content))
                        .setConfirmListener(new GlassDialogListener() {
                            @Override
                            public void onClick(View view) {
                                Toast.makeText(MainActivity.this,
                                        "Click Confirm", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setCancelListener(new GlassDialogListener() {
                            @Override
                            public void onClick(View view) {
                                Toast.makeText(MainActivity.this,
                                        "Click Cancel", Toast.LENGTH_SHORT).show();

                            }
                        }).show();
                break;
            case R.id.customer_message_btn:
                mCustomerMessageDialog = new GlassDialog.CustomerSimpleMsgDialogBuilder(this)
                        .setTitle(getString(R.string.image_content_title))
                        .setConfirmText(getString(R.string.voice_play))
                        .setCancelText(getString(R.string.voice_collapse))
                        .setCustomerText(getString(R.string.voice_customer))
                        .setContent(getString(R.string.simple_content))
                        .setCustomerListener(new GlassDialogListener() {
                            @Override
                            public void onClick(View view) {
                                Toast.makeText(MainActivity.this,
                                        "Click Customer", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setConfirmListener(new GlassDialogListener() {
                            @Override
                            public void onClick(View view) {
                                Toast.makeText(MainActivity.this,
                                        "Click Confirm", Toast.LENGTH_SHORT).show();
                                if (null != mCustomerMessageDialog && mCustomerMessageDialog.isShowing()) {
                                    mCustomerMessageDialog.dismiss();
                                }
                            }
                        })
                        .setCancelListener(new GlassDialogListener() {
                            @Override
                            public void onClick(View view) {
                                Toast.makeText(MainActivity.this,
                                        "Click Cancel", Toast.LENGTH_SHORT).show();

                            }
                        })
                        .show();
                break;
            case R.id.customer_image_btn:
                mCustomerImageDialogBuilder = new GlassDialog.CustomerImageDialogBuilder(this)
                        .setTitle(getString(R.string.image_title))
                        .setConfirmText(getString(R.string.voice_play))
                        .setCancelText(getString(R.string.voice_collapse))
                        .setCustomerText(getString(R.string.voice_customer))
                        .setNotifyResId(R.mipmap.ic_notify_img)
                        .setCustomerListener(new GlassDialogListener() {
                            @Override
                            public void onClick(View view) {
                                Toast.makeText(MainActivity.this,
                                        "Click Customer", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setConfirmListener(new GlassDialogListener() {
                            @Override
                            public void onClick(View view) {
                                Toast.makeText(MainActivity.this,
                                        "Click Play", Toast.LENGTH_SHORT).show();
                                mCustomerImageDialogBuilder.dynamicCustomConfirmView(mCustomTimerView);
                                countDownManager.start();
                            }
                        })
                        .setCancelListener(new GlassDialogListener() {
                            @Override
                            public void onClick(View view) {
                                Toast.makeText(MainActivity.this,
                                        "Click Cancel", Toast.LENGTH_SHORT).show();

                            }
                        });

                mCustomerImageDialogBuilder.show();
                break;
            case R.id.customer_image_content_btn:
                new GlassDialog.CustomerImageContentDialogBuilder(this)
                        .setTitle(getString(R.string.image_content_title))
                        .setConfirmText(getString(R.string.voice_play))
                        .setCancelText(getString(R.string.voice_collapse))
                        .setNotifyResId(R.mipmap.ic_notify_img)
                        .setContent(getString(R.string.multi_content))
                        .setCustomerText(getString(R.string.voice_customer))
                        .setCustomerListener(new GlassDialogListener() {
                            @Override
                            public void onClick(View view) {
                                Toast.makeText(MainActivity.this,
                                        "Click Customer", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setConfirmListener(new GlassDialogListener() {
                            @Override
                            public void onClick(View view) {
                                Toast.makeText(MainActivity.this,
                                        "Click Play", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setCancelListener(new GlassDialogListener() {
                            @Override
                            public void onClick(View view) {
                                Toast.makeText(MainActivity.this,
                                        "Click Cancel", Toast.LENGTH_SHORT).show();

                            }
                        })
                        .show();
                break;
            case R.id.customer_voice_btn:
                mCustomerVoiceDialogBuilder = new GlassDialog.CustomerVoiceDialogBuilder(this)
                        .setTitle(getString(R.string.voice_test))
                        .setConfirmText(getString(R.string.voice_play))
                        .setCancelText(getString(R.string.voice_collapse))
                        .setCustomerText(getString(R.string.voice_customer))
                        .setConfirmListener(new GlassDialogListener() {
                            @Override
                            public void onClick(View view) {
                                Toast.makeText(MainActivity.this,
                                        "Click Confirm", Toast.LENGTH_SHORT).show();

                                mCustomerVoiceDialogBuilder.dynamicTitle(getString(R.string.voice_playing));
                                mCustomerVoiceDialogBuilder.dynamicCustomConfirmView(mCustomTimerView);

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

                mCustomerVoiceDialogBuilder.show();
                break;
        }
    }
}
