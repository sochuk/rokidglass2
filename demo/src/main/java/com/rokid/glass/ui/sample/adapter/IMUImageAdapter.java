package com.rokid.glass.ui.sample.adapter;

import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.rokid.glass.imusdk.core.ui.recyclerview.BaseAdapter;
import com.rokid.glass.imusdk.core.ui.recyclerview.BaseViewHolder;
import com.rokid.glass.ui.sample.R;

/**
 * @date 2019-08-15
 */

public class IMUImageAdapter extends BaseAdapter<IMUImageItem, IMUImageAdapter.ImageViewHolder> {
    private static String TAG = "ImageAdapter";

    public IMUImageAdapter() {
        this(R.layout.image_recycler_item);
    }

    public IMUImageAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(@NonNull ImageViewHolder viewHolder, IMUImageItem item) {
        viewHolder.img.setImageResource(item.getResId());
        viewHolder.itemTitle.setText(item.getName());
    }

    @Override
    protected void focusPosition(@NonNull ImageViewHolder viewHolder, IMUImageItem item) {
//        viewHolder.imgBg.setBackgroundResource(R.drawable.item_select);
        viewHolder.itemContent.setScaleX(1.1f);
        viewHolder.itemContent.setScaleY(1.1f);
        Log.e(TAG,"focusPosition = "+item);
    }

    @Override
    protected void normalPosition(@NonNull ImageViewHolder viewHolder, IMUImageItem item) {
//        viewHolder.imgBg.setBackgroundResource(R.drawable.item_normal);
        viewHolder.itemContent.setScaleX(1.0f);
        viewHolder.itemContent.setScaleY(1.0f);
        Log.e(TAG,"normalPosition = "+item);
    }

    static class ImageViewHolder extends BaseViewHolder {
        ImageView img;
        ImageView imgBg;

        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.image_item);
            imgBg = itemView.findViewById(R.id.image_bg);
        }
    }
}
