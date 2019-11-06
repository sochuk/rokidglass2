package com.rokid.glass.ui.sample;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;

/**
 * @author jian.yang
 * @date 2019/10/30
 */

public class TextViewActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        setContentView(R.layout.activity_text_view);
        findViewById(R.id.item_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(TextViewActivity.this, TextView2Activity.class),
                        ActivityOptionsCompat.makeSceneTransitionAnimation(TextViewActivity.this).toBundle());
            }
        });
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_ENTER:
            case KeyEvent.KEYCODE_DPAD_CENTER:
                startActivity(new Intent(TextViewActivity.this, TextView2Activity.class),
                        ActivityOptionsCompat.makeSceneTransitionAnimation(this).toBundle());
                break;
        }
        return super.onKeyUp(keyCode, event);
    }
}
