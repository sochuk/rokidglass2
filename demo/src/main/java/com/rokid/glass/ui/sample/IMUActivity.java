package com.rokid.glass.ui.sample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;

import com.rokid.glass.imusdk.core.IMUView;
import com.rokid.glass.ui.sample.adapter.IMUImageAdapter;
import com.rokid.glass.ui.sample.adapter.IMUImageItem;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: sunchao
 * @CreateDate: 2020/7/18 2:52 PM
 */
public class IMUActivity extends AppCompatActivity {
    private IMUView mImuView;
    private List<IMUImageItem> mItems;
    private IMUImageAdapter mAdapter;

    private int[] resId = new int[]{R.mipmap.ic_1, R.mipmap.ic_2,
            R.mipmap.ic_3, R.mipmap.ic_4, R.mipmap.ic_5, R.mipmap.ic_6,
            R.mipmap.ic_7, R.mipmap.ic_8, R.mipmap.ic_9, R.mipmap.ic_10};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imu);

        initViews();
        initData();
    }

    private void initViews() {
        mImuView = findViewById(R.id.ui_recycler_view);
        getLifecycle().addObserver(mImuView);

        mAdapter = new IMUImageAdapter();
        mImuView.setAdapter(mAdapter);
        mImuView.setSlow();
    }

    private void initData() {
        mItems = new ArrayList<>();
        for (int i = 0; i < resId.length; i++) {
            mItems.add(new IMUImageItem().setResId(resId[i]).setName("姓名" + i));
        }

        mAdapter.setData(mItems);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_DPAD_CENTER:
            case KeyEvent.KEYCODE_ENTER://此处做点击事件的响应
                Log.d("position", "current pos  =" + mAdapter.getCurrentPosition() + "    keyCode = " + keyCode);
                IMUImageItem data = mAdapter.getItem(mAdapter.getCurrentPosition());
                if (null == data) {
                    return super.onKeyUp(keyCode, event);
                }
//                enterItem(data);
                break;

        }
        return super.onKeyDown(keyCode, event);
    }

}
