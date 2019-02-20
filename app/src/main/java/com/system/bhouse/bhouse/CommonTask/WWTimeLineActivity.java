package com.system.bhouse.bhouse.CommonTask;

import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.system.bhouse.base.App;
import com.system.bhouse.bhouse.CommonTask.TechnologyExecution.selectploy.DisablePloy;
import com.system.bhouse.bhouse.CommonTask.TechnologyExecution.selectploy.SelectColorBg;
import com.system.bhouse.bhouse.CommonTask.TechnologyExecution.selectploy.SelectPloy;
import com.system.bhouse.bhouse.CommonTask.TechnologyExecution.selectploy.UnSelectLineDotBg;
import com.system.bhouse.bhouse.CommonTask.TechnologyExecution.selectploy.UnSelectPloy;
import net.qiujuer.italker.common.adapter.BaseQuickAdapter;
import net.qiujuer.italker.common.adapter.BaseViewHolder;
import com.system.bhouse.bhouse.CommonTask.Widget.TimeLineItemTopBottomDecoration;
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
        my_recycle_view.addItemDecoration(new TimeLineItemTopBottomDecoration(),0);

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

    protected   InnerHandle handler=new InnerHandle(this){
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
        if (stringArray!=null) {
            data.addAll(Arrays.asList(stringArray));
        }
        if (adapter!=null) {
            adapter.notifyDataSetChanged();
        }
    }

    protected void DisableBg(View view)
    {
        DisablePloy ploy = new SelectColorBg();
        ploy.Disablebg(view);
    }

    protected void UnSelectLineDotBg(View view) {
        UnSelectPloy ploy = new UnSelectLineDotBg();
        ploy.UnSelectbg(view);
    }

    protected void SelectLineDotBg(View view) {
        SelectPloy ploy = new UnSelectLineDotBg();
        ploy.selectBg(view);
    }

    protected void SelectColorBg(View view) {
        SelectPloy ploy = new SelectColorBg();
        ploy.selectBg(view);
    }

    protected void UnSelectColorBg(View view) {
        UnSelectPloy ploy = new SelectColorBg();
        ploy.UnSelectbg(view);
    }
}
