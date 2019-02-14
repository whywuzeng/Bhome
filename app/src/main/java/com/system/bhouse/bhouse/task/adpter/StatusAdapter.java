package com.system.bhouse.bhouse.task.adpter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.system.bhouse.bhouse.R;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;
import org.androidannotations.annotations.res.StringArrayRes;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017-11-14.
 */

@EBean
public class StatusAdapter extends BaseAdapter {

    @StringArrayRes(R.array.important_task)
    String[] mData;

    @RootContext
    Context context;


    @Override
    public int getCount() {
        return this.mData.length;
    }

    @Override
    public Object getItem(int position) {
        return mData[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = null;
        ViewHolder holder=null;
        if (convertView == null) {
            view = LayoutInflater.from(context).inflate(R.layout.activity_task_status_list_item, parent, false);
            holder=new ViewHolder(view);
            view.setTag(holder);
        }else
        {
            view= convertView;
            holder= (ViewHolder) view.getTag();
        }
        holder.title.setText(mData[position]);



        return view;
    }

    static class ViewHolder {
        @BindView(R.id.icon)
        View icon;
        @BindView(R.id.title)
        TextView title;
        @BindView(R.id.check)
        ImageView check;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
