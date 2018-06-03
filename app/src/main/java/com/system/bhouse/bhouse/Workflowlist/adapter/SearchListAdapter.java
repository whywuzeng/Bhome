package com.system.bhouse.bhouse.Workflowlist.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.system.bhouse.bean.SiteSearchTwoWayBean;
import com.system.bhouse.bhouse.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017-03-17.
 * ClassName: com.system.bhouse.bhouse.Workflowlist.adapter
 * Author:Administrator
 * Fuction:
 * UpdateUser:
 * UpdateDate:
 */
public class SearchListAdapter extends BaseAdapter{


    private Context context;
    private List<SiteSearchTwoWayBean> beanList=new ArrayList<>();

    private LayoutInflater from;

    public SearchListAdapter(Context context)
    {
        this.context=context;

        from = LayoutInflater.from(context);

        SiteSearchTwoWayBean searchTwoWayBean=null;
        for (int i=1;i<10;i++)
        {
            searchTwoWayBean=new SiteSearchTwoWayBean();
            searchTwoWayBean.SPH="131"+i;
            searchTwoWayBean.numberType="销售报价"+i;
            searchTwoWayBean.DocumentNumber="天津-201703-001"+"--"+i;
            searchTwoWayBean.ManagementOrganization="江苏工厂"+i;
            beanList.add(searchTwoWayBean);
        }
    }

    @Override
    public int getCount() {
        return beanList.size();
    }

    @Override
    public Object getItem(int position) {
        return beanList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        ViewHolder holder=null;
        if (convertView==null)
        {
            holder=new ViewHolder();
            convertView= from.inflate(R.layout.search_list_item,parent,false);
            holder.tv_searchlist= (TextView)convertView.findViewById(R.id.tv_searchlist);
            convertView.setTag(holder);
        }else {
            holder=(ViewHolder)convertView.getTag();
        }
        holder.tv_searchlist.setText(beanList.get(position).DocumentNumber);

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               onItemMyClick.onItem(beanList.get(position).DocumentNumber);
            }
        });

        return convertView;
    }

    private static class ViewHolder
    {
        private TextView tv_searchlist;
    }

    private  OnItemMyClick onItemMyClick;

    public void setOnItemMyClick(OnItemMyClick onItemMyClick)
    {
        this.onItemMyClick=onItemMyClick;
    }

    public interface OnItemMyClick
    {
        void onItem(String value);
    }

}
