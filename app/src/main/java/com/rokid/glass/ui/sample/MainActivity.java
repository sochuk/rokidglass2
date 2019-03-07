package com.rokid.glass.ui.sample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.rokid.glass.ui.dialog.GlassDialog;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private GlassDialog mNotificationDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.notification_btn).setOnClickListener(this);
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
        }
    }
}
