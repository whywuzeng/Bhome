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
public class SiteSearchTwoWayAdapter extends BaseAdapter{

    private List<SiteSearchTwoWayBean> beanList=new ArrayList<>();

    private Context context;

    private LayoutInflater from;


    public SiteSearchTwoWayAdapter(Context context)
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
    public View getView(int position, View convertView, ViewGroup parent) {


        ViewHolder holder=null;
        if (convertView==null)
        {
            View inflate = from.inflate(R.layout.site_search_gv_ll, parent, false);
            holder=new ViewHolder();
            holder.tv_num_1=(TextView)inflate.findViewById(R.id.tv_num_1);
            holder.tv_num_2=(TextView)inflate.findViewById(R.id.tv_num_2);
            holder.tv_num_3=(TextView)inflate.findViewById(R.id.tv_num_3);
            holder.tv_num_4=(TextView)inflate.findViewById(R.id.tv_num_4);

            convertView=inflate;
            convertView.setTag(holder);
        }else {
            holder=(ViewHolder)convertView.getTag();
        }

        holder.tv_num_1.setText(beanList.get(position).SPH);
        holder.tv_num_2.setText(beanList.get(position).numberType);
        holder.tv_num_3.setText(beanList.get(position).DocumentNumber);
        holder.tv_num_4.setText(beanList.get(position).ManagementOrganization);

        return convertView;
    }

    static class ViewHolder{

        private TextView tv_num_1;
        private TextView tv_num_2;
        private TextView tv_num_3;
        private TextView tv_num_4;
    }
}
