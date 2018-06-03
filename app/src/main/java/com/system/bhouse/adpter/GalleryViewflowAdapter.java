package com.system.bhouse.adpter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

/**
 * Created by Administrator on 2016-8-8.
 * ClassName: com.system.bhouse.adpter
 * Author:Administrator
 * Fuction: 填充轮播图的adpter
 * UpdateUser:
 * UpdateDate:
 */
public class GalleryViewflowAdapter extends BaseAdapter {
    private Context mContext;
    private int[] pics;

    public GalleryViewflowAdapter(Context context,int[] pics){
        this.mContext=context;
        this.pics=pics;
    }
    @Override
    public int getCount() {
        return pics.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            ImageView imageView = new ImageView(mContext);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            imageView.setLayoutParams(new android.view.ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            convertView = imageView;
            viewHolder.imageView = (ImageView) convertView;
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.imageView.setImageResource(pics[position]);
        return convertView;
    }

    private class ViewHolder {
        ImageView imageView;
    }
}
