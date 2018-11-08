package com.system.bhouse.bhouse.phone.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.system.bhouse.Common.Global;
import com.system.bhouse.base.TimeLineItemTopBottomDecoration;
import com.system.bhouse.bhouse.CommonTask.ProduceManagement.adapter.entity.MultipleItem;
import com.system.bhouse.bhouse.R;
import com.system.bhouse.bhouse.phone.activity.adapter.ScanMultipleItemQuickAdapter;
import com.system.bhouse.bhouse.phone.activity.bean.ScanBean;
import com.system.bhouse.bhouse.phone.activity.bean.ScanSectionMultipleiItem;
import com.system.bhouse.bhouse.setup.WWCommon.SmartRefreshBaseFragment;
import com.system.bhouse.utils.MeasureUtil;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.Bind;

/**
 * Created by Administrator on 2018-10-16.
 * <p>
 * by author wz
 * <p>
 * com.system.bhouse.bhouse.phone.activity
 */

public class ScanResultFragment extends SmartRefreshBaseFragment {

    @Bind(R.id.listView)
    RecyclerView listView;
    @Bind(R.id.swipeRefreshLayout)
    SmartRefreshLayout swipeRefreshLayout;
    @Bind(R.id.icon)
    View icon;
    @Bind(R.id.message)
    TextView message;
    @Bind(R.id.btnRetry)
    Button btnRetry;
    private String sToday;
    private String sYesterday;
    private int mLastId;

    @Override
    protected void onCreateView(Bundle savedInstanceState) {
        super.onCreateView(savedInstanceState);
        setContentView(R.layout.fragment_scan_result);

        inflater = LayoutInflater.from(getActivity());


        Calendar calendar = Calendar.getInstance();
        Long today = calendar.getTimeInMillis();
        sToday = Global.mDateFormat.format(today);
        Long yesterday = calendar.getTimeInMillis() - 1000 * 60 * 60 * 24;
        sYesterday = Global.mDateFormat.format(yesterday);


        init();
    }

    private void init() {
        initRefreshView();
        List<ScanSectionMultipleiItem> list = new ArrayList<>();


        list.add(new ScanSectionMultipleiItem(new ScanBean("吴增","布模了构件201803-155451-11002","布模工序","18.32","",sToday), MultipleItem.IMG,4));

        list.add(new ScanSectionMultipleiItem(new ScanBean("杜俊","投料了构件201803-155451-11002","投料工序","18.32","",sToday), MultipleItem.IMG,4));

        list.add(new ScanSectionMultipleiItem(new ScanBean("沈灵敏","投料了构件201803-155451-11002","投料工序","18.32","",sToday), MultipleItem.IMG,4));

        list.add(new ScanSectionMultipleiItem(new ScanBean("小伙子","投料了构件201803-155451-11002","投料工序","18.32","",sToday), MultipleItem.IMG,4));


        list.add(new ScanSectionMultipleiItem(new ScanBean("吴增","布模了构件201803-155451-11002","布模工序","18.32","",sYesterday), MultipleItem.IMG,4));

        list.add(new ScanSectionMultipleiItem(new ScanBean("杜俊","投料了构件201803-155451-11002","投料工序","18.32","",sYesterday), MultipleItem.IMG,4));

        list.add(new ScanSectionMultipleiItem(new ScanBean("沈灵敏","投料了构件201803-155451-11002","投料工序","18.32","",sYesterday), MultipleItem.IMG,4));

        list.add(new ScanSectionMultipleiItem(new ScanBean("小伙子","投料了构件201803-155451-11002","投料工序","18.32","",sYesterday), MultipleItem.IMG,4));
        list.add(new ScanSectionMultipleiItem(new ScanBean("小伙子","投料了构件201803-155451-11002","投料工序","18.32","",sYesterday), MultipleItem.IMG,4));
        list.add(new ScanSectionMultipleiItem(new ScanBean("小伙子","投料了构件201803-155451-11002","投料工序","18.32","",sYesterday), MultipleItem.IMG,4));
        list.add(new ScanSectionMultipleiItem(new ScanBean("小伙子","投料了构件201803-155451-11002","投料工序","18.32","",sYesterday), MultipleItem.IMG,4));
        list.add(new ScanSectionMultipleiItem(new ScanBean("小伙子","投料了构件201803-155451-11002","投料工序","18.32","",sYesterday), MultipleItem.IMG,4));

        final ScanMultipleItemQuickAdapter adapter = new ScanMultipleItemQuickAdapter(list);
        final GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 4);
        listView.setLayoutManager(gridLayoutManager);
        listView.setAdapter(adapter);
        final TimeLineItemTopBottomDecoration decoration = new TimeLineItemTopBottomDecoration();
        decoration.setmGroupHeight(MeasureUtil.dip2px(getActivity(),41));
        decoration.setGroupNameListener(adapter);
        listView.addItemDecoration(decoration);
    }


    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {

    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {

    }

}
