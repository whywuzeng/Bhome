package com.system.bhouse.base;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Administrator on 2016-4-18.
 * ClassName: com.system.bhouse.base
 * Author:Administrator
 * Fuction:
 * UpdateUser:
 * UpdateDate:
 */
public class BaseRecyclerViewHolder extends RecyclerView.ViewHolder {

    protected SparseArray<View> mViews;

    protected Context mContext;

    public BaseRecyclerViewHolder(View itemView,Context mContext) {
        super(itemView);
        this.mContext=mContext;
        mViews=new SparseArray<>();
    }


    private <T extends View> T FindbyId(int id){
        View view = mViews.get(id);
        if(view==null){
            view = itemView.findViewById(id);
            mViews.put(id,view);
        }
        return (T )view;
    }

    public View getView(int viewId){
        return FindbyId(viewId);
    }

    public TextView getTextView(int viewId) {
        return (TextView) getView(viewId);
    }

    public Button getButton(int viewId) {
        return (Button) getView(viewId);
    }

    public ImageView getImageView(int viewId) {
        return (ImageView) getView(viewId);
    }

    public ImageButton getImageButton(int viewId) {
        return (ImageButton) getView(viewId);
    }

    public EditText getEditText(int viewId) {
        return (EditText) getView(viewId);
    }


    public BaseRecyclerViewHolder setText(int viewId, String value) {
        TextView view = FindbyId(viewId);
        view.setText(value);
        return this;
    }

    public BaseRecyclerViewHolder setBackground(int viewId, int resId) {
        View view = FindbyId(viewId);
        view.setBackgroundResource(resId);
        return this;
    }

    public BaseRecyclerViewHolder setClickListener(int viewId, View.OnClickListener listener) {
        View view = FindbyId(viewId);
        view.setOnClickListener(listener);
        return this;
    }

}
