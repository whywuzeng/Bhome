package com.system.bhouse.bhouse.Workflowlist.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.custom.vg.list.CustomAdapter;
import com.system.bhouse.bean.SearchHistroyBean;
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
 * UpdateDate: 用别人的库的 一个历史记录
 */
public class SeacherHistoryAdapter extends CustomAdapter{

    private Context context;
    private List<SiteSearchTwoWayBean> beanList=new ArrayList<>();

     private List<SearchHistroyBean> searchHistroyBeans=new ArrayList<>();

    private LayoutInflater from;

    public SeacherHistoryAdapter(Context context,List<SearchHistroyBean> SearchHistroyBean)
    {

        from=LayoutInflater.from(context);
        SiteSearchTwoWayBean searchTwoWayBean=null;
        this.searchHistroyBeans=SearchHistroyBean;
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

    public int getCount() {

        return searchHistroyBeans.size();
    }

    public Object getItem(int position) {

        return searchHistroyBeans.get(position);
    }

    public long getItemId(int position) {

        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder=null;
        if (convertView==null)
        {
            convertView= from.inflate(R.layout.item_spec, parent, false);
            holder=new ViewHolder();
            holder.tv_spec=(TextView)convertView.findViewById(R.id.tv_spec);
            convertView.setTag(holder);
        }else {
            holder=(ViewHolder)convertView.getTag();
        }

        holder.tv_spec.setText(searchHistroyBeans.get(position).getSh_name());

        return convertView;
    }

    private static class ViewHolder{
        private TextView tv_spec;
    }

}
