package com.rokid.glass.ui.sample;

import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.rokid.glass.ui.dialog.GlassDialog;
import com.rokid.glass.ui.dialog.GlassDialogListener;
import com.rokid.glass.ui.toast.GlassToastUtil;
import com.rokid.glass.ui.util.RokidSystem;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView mGlassInfoTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.font_btn).setOnClickListener(this);
        findViewById(R.id.simple_toast).setOnClickListener(this);
        findViewById(R.id.custom_dialog_btn).setOnClickListener(this);
        findViewById(R.id.dialog_btn).setOnClickListener(this);
        findViewById(R.id.auto_size_btn).setOnClickListener(this);

        mGlassInfoTv = findViewById(R.id.glass_info);
        mGlassInfoTv.setText(RokidSystem.getHardwareVersion());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.font_btn:
                startActivity(new Intent(this, FontActivity.class));
                break;
            case R.id.dialog_btn:
                new GlassDialog.CommonDialogBuilder(this)
                        .setTitle("我是很长我是很长我是很" +
                                "长我是很长我是很长我是很长我是很长我是很长")
                        .setConfirmText("确定按钮")
                        .setCancelText("次级按钮")
                        .setConfirmListener(new GlassDialogListener() {
                            @Override
                            public void onClick(View view) {

                            }
                        })
                        .setCancelListener(new GlassDialogListener() {
                            @Override
                            public void onClick(View view) {

                            }
                        })
                        .show();
                break;
            case R.id.custom_dialog_btn:
                new GlassDialog.CommonDialogBuilder(this)
                        .setTitle("Custom Content")
                        .setConfirmText("确定")
                        .setCancelText("取消")
                        .setContentLayoutId(R.layout.layout_custom_dialog_content)
                        .show();
                break;
            case R.id.simple_toast:
                GlassToastUtil.showToast(this, R.string.glassui_toast_test);
                break;
            case R.id.auto_size_btn:
                startActivity(new Intent(this, AutoSizeActivity.class));
                break;
        }
    }
}
