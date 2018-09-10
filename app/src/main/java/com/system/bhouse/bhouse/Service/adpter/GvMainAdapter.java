package com.system.bhouse.bhouse.Service.adpter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.system.bhouse.bhouse.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016-6-15.
 * ClassName: com.system.bhouse.bhouse.Service.adpter
 * Author:Administrator
 * Fuction:
 * UpdateUser:
 * UpdateDate:
 */

public class GvMainAdapter extends BaseAdapter {
    private List<String> mNameList ;
    private List<Integer> mDrawableList ;
    private LayoutInflater mInflater;
    private Context mContext;
    private ImageClick mImageClick;
    LinearLayout.LayoutParams params;

    public GvMainAdapter(Context context, ArrayList<String> nameList,
                       ArrayList<Integer> drawableList) {
       this. mNameList = new ArrayList<>();
        this.mDrawableList = new ArrayList<>();
        this.mNameList = nameList;
        this.mDrawableList = drawableList;
        mContext = context;
        mInflater = LayoutInflater.from(context);
    }



    @Override
    public int getCount() {
        return mDrawableList.size();
    }

    @Override
    public Object getItem(int position) {
        return mNameList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ItemViewTag viewTag=null;
    //R.layout.gridlayoutfragmentitem
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.gridlayoutfragmentitem,parent,false);
            // construct an item tag
            viewTag = new ItemViewTag(convertView);
            convertView.setTag(viewTag);
        }
        else {
            viewTag = (ItemViewTag) convertView.getTag();
        }

        // set name
        viewTag.mName.setText(mNameList.get(position));

        // set icon
        viewTag.mIcon.setImageResource(mDrawableList.get(position));

        viewTag.mIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mImageClick.setPosition(position);
            }
        });
//        viewTag.mIcon.setImageResource(R.drawable.kaohe);
//        viewTag.mIcon.setLayoutParams(params);
        return convertView;
    }

    public void setmImageClick(ImageClick mImageClick) {
        this.mImageClick = mImageClick;
    }

    class ItemViewTag {
        private ImageView mIcon;
        private TextView mName;

        public ItemViewTag(View view) {
            this.mIcon = (ImageView) view.findViewById
                    (R.id.grid_icon);
            this.mName =   (TextView) view.findViewById(R.id.grid_name);

        }
    }


    public interface ImageClick{
        public void setPosition(int position);
    }
}
