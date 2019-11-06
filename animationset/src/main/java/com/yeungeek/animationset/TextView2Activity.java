package com.yeungeek.animationset;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.view.KeyEvent;
import android.view.Window;

/**
 * @author jian.yang
 * @date 2019/10/30
 */

public class TextView2Activity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_view2);

        setupWindowAnimations();
    }


    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_ENTER:
            case KeyEvent.KEYCODE_DPAD_CENTER:
                finish();
                break;
        }
        return super.onKeyUp(keyCode, event);
    }

    private void setupWindowAnimations() {
        Transition transition = TransitionInflater.from(this).inflateTransition(R.transition.explode);
        getWindow().setEnterTransition(transition);
    }
}
