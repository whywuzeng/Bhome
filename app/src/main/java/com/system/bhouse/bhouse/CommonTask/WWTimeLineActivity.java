package com.system.bhouse.bhouse.CommonTask;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.system.bhouse.base.App;
import com.system.bhouse.bhouse.CommonTask.TransportationManagement.adapter.BaseQuickAdapter;
import com.system.bhouse.bhouse.CommonTask.TransportationManagement.adapter.BaseViewHolder;
import com.system.bhouse.bhouse.R;
import com.system.bhouse.bhouse.setup.WWCommon.WWBackActivity;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Administrator on 2018-06-13.
 * <p>
 * by author wz
 * <p>
 * com.system.bhouse.bhouse.CommonTask
 */

public abstract class WWTimeLineActivity extends WWBackActivity implements BaseQuickAdapter.OnItemClickListener {

    protected String[] stringArray;
    protected List<String> data=new ArrayList<>();
    protected BaseQuickAdapter<String, MyBaseViewHolder> adapter;
    private RecyclerView my_recycle_view;

    protected void initTimeLineActivity(RecyclerView my_recycle_view,int RrrayRes){
        this.my_recycle_view=my_recycle_view;
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        my_recycle_view.setLayoutManager(linearLayoutManager);
        stringArray = getResources().getStringArray(RrrayRes);
        data.addAll(Arrays.asList(stringArray));

        adapter = new BaseQuickAdapter<String, MyBaseViewHolder>(R.layout.timeline_item){
            @Override
            protected void convert(MyBaseViewHolder helper, String item) {
                helper.setText(R.id.tv_title,item);
                helper.setText(R.id.tv_sub_title,  App.Mancompany);
            }
        };
        adapter.setNewData(data);

        my_recycle_view.setAdapter(adapter);

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                handler.sendEmptyMessage(1);
            }
        },100);

        adapter.setOnItemClickListener(this);
    }

    private  InnerHandle handler=new InnerHandle(this){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            View byPosition = adapter.getViewByPosition(my_recycle_view, 0, R.id.rl_content_layout);
            SelectColorBg(byPosition);
            View rlTimeline = adapter.getViewByPosition(my_recycle_view, 0, R.id.rlTimeline);
            SelectLineDotBg(rlTimeline);

        }
    };

    //弱引用 Handle的写法
    public class InnerHandle extends Handler{
        private final WeakReference<WWTimeLineActivity> wwTimeLineActivityWeakReference;

        public InnerHandle(WWTimeLineActivity mActivity){
            this.wwTimeLineActivityWeakReference=new WeakReference<WWTimeLineActivity>(mActivity);
        }
    }


   protected static class MyBaseViewHolder extends BaseViewHolder {

        public MyBaseViewHolder(View view) {
            super(view);
        }
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

        SelectColorBg(view);
        view.setTag(Color.rgb(51,138,185));
        int itemCount = adapter.getItemCount();
        for (int i=0;i<itemCount;i++)
        {
            if (i!=position) {
                View byPosition = adapter.getViewByPosition(my_recycle_view, i, R.id.rl_content_layout);
                UnSelectColorBg(byPosition);
            }
            if (i<=position){
                View byPosition = adapter.getViewByPosition(my_recycle_view, i, R.id.rlTimeline);
                SelectLineDotBg(byPosition);
            }else {
                View byPosition = adapter.getViewByPosition(my_recycle_view, i, R.id.rlTimeline);
                UnSelectLineDotBg(byPosition);
            }
        }
        IntentToFragment(position);
    }

    protected abstract void IntentToFragment(int position);

    @Override
    protected void onResume() {
        super.onResume();
        data.clear();
        data.addAll(Arrays.asList(stringArray));
        adapter.notifyDataSetChanged();
    }

    private void UnSelectLineDotBg(View view) {
        TextView tvView = (TextView) view.findViewById(R.id.tvTopLine);
        TextView tvDot = (TextView) view.findViewById(R.id.tvDot);
        TextView tvBottom = (TextView) view.findViewById(R.id.tvBottom);

        tvView.setBackground(getResources().getDrawable(R.color.common_color_text_8));
        tvDot.setBackground(getResources().getDrawable(R.drawable.timelline_dot_normal));
        tvBottom.setBackground(getResources().getDrawable(R.color.common_color_text_8));
    }

    private void SelectLineDotBg(View view) {
        TextView tvView = (TextView) view.findViewById(R.id.tvTopLine);
        TextView tvDot = (TextView) view.findViewById(R.id.tvDot);
        TextView tvBottom = (TextView) view.findViewById(R.id.tvBottom);

        tvView.setBackground(getResources().getDrawable(R.color.fc5));
        tvDot.setBackground(getResources().getDrawable(R.drawable.bg_timeline_btn_select));
        tvBottom.setBackground(getResources().getDrawable(R.color.fc5));
    }

    private void SelectColorBg(View view) {
        RelativeLayout rel = (RelativeLayout) view.findViewById(R.id.rl_content_layout);
        Drawable drawable = getResources().getDrawable(R.drawable.bg_timeline_btn_select);
        rel.setBackground(drawable);
        TextView textTitle = (TextView) view.findViewById(R.id.tv_title);
        TextView subTitle = (TextView) view.findViewById(R.id.tv_sub_title);

        textTitle.setTextColor(getResources().getColor(R.color.white));
        subTitle.setTextColor(getResources().getColor(R.color.white));
    }

    private void UnSelectColorBg(View view) {
        RelativeLayout rel = (RelativeLayout) view.findViewById(R.id.rl_content_layout);
        Drawable drawable = getResources().getDrawable(R.drawable.bg_timeline_btn_normal);
        rel.setBackground(drawable);
        TextView textTitle = (TextView) view.findViewById(R.id.tv_title);
        TextView subTitle = (TextView) view.findViewById(R.id.tv_sub_title);
        textTitle.setTextColor(getResources().getColor(R.color.font_6));
        subTitle.setTextColor(getResources().getColor(R.color.common_color_text_30));
    }
}
