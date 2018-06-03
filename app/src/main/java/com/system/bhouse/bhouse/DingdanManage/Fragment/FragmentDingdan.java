package com.system.bhouse.bhouse.DingdanManage.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.socks.library.KLog;
import com.system.bhouse.Common.LoadOrder;
import com.system.bhouse.base.ActivityFragmentInject;
import com.system.bhouse.base.App;
import com.system.bhouse.base.BaseFragment;
import com.system.bhouse.base.BaseRecyclerAdapter;
import com.system.bhouse.base.BaseRecyclerViewHolder;
import com.system.bhouse.base.BaseSpacesItemDecoration;
import com.system.bhouse.bean.DingdanZhuangTai;
import com.system.bhouse.bhouse.DingdanManage.DingdanConameListActivity;
import com.system.bhouse.bhouse.DingdanManage.Presenter.DingdanPresenter;
import com.system.bhouse.bhouse.DingdanManage.Presenter.DingdanPresenterImpl;
import com.system.bhouse.bhouse.DingdanManage.UiInterface.DingdanViewInterface;
import com.system.bhouse.bhouse.DingdanManage.UiInterface.SecondPageListener;
import com.system.bhouse.bhouse.R;
import com.system.bhouse.utils.TenUtils.DensityUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016-4-15.
 * ClassName: com.system.bhouse.bhouse.DingdanManage.Fragment
 * Author:Administrator
 * Fuction:
 * UpdateUser:
 * UpdateDate:
 */
@ActivityFragmentInject(contentViewId = R.layout.dingdanfragment)
public class FragmentDingdan extends BaseFragment<DingdanPresenter> implements DingdanViewInterface,SecondPageListener {

    protected static String Id = "";

    private RecyclerView id_recyclerview;
    private BaseRecyclerAdapter<DingdanZhuangTai> mBaseRecyclerAdapter;
    private boolean isDeal;
    private SwipeRefreshLayout swipeRefreshLayout;
    private LinearLayout layoutLoading;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            Id = getArguments().getString("Id");
            isDeal = getArguments().getBoolean("isDeal",false);
        }
    }

    public static FragmentDingdan newInstance(String id,boolean isDeal) {
        FragmentDingdan mFragmentDingdan = new FragmentDingdan();
        Bundle mBundle = new Bundle();
        mBundle.putString("Id", id);
        mBundle.putBoolean("isDeal",isDeal);
        mFragmentDingdan.setArguments(mBundle);
        return mFragmentDingdan;
    }

    @Override
    public void ErrorRefresh(LoadOrder type) {
        if(type==LoadOrder.Second){
            mPresenter.refreshData(LoadOrder.Second);
        }
    }

//    @Override
//    public void onResume() {
//        super.onResume();
//        mPresenter = new DingdanPresenterImpl(FragmentDingdan.this,App.USER_INFO, App.MID,Integer.valueOf(Id),isDeal);
//    }



    @Override
    public void showProgress() {
        super.showProgress();
        if(swipeRefreshLayout!=null){
            swipeRefreshLayout.setRefreshing(true);
            swipeRefreshLayout.isRefreshing();
        }
        if(layoutLoading!=null){
            layoutLoading.setVisibility(View.VISIBLE);
        }

    }


    @Override
    public void hideProgress() {
        super.hideProgress();
        if(swipeRefreshLayout!=null){
            swipeRefreshLayout.setRefreshing(false);
        }
        if(layoutLoading!=null){
            layoutLoading.setVisibility(View.GONE);
        }
    }

    @Override
    protected void initview(View fragmentrootview) {
        id_recyclerview = (RecyclerView) fragmentrootview.findViewById(R.id.id_recyclerview);
        swipeRefreshLayout=(SwipeRefreshLayout)fragmentrootview.findViewById(R.id.swipeRefreshLayout);
        layoutLoading=(LinearLayout)fragmentrootview.findViewById(R.id.layoutLoading);

        String username="";
        int mid=-1;
        if(App.USER_INFO!=null) {
            username = App.USER_INFO;
            mid= App.MID;
        }else{
            Toast.makeText(getActivity(),"数据失效，重新登录",Toast.LENGTH_SHORT).show();
        }
        int statusid=Integer.valueOf(Id);
        boolean checktrue=true;

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.refreshData(LoadOrder.Second);
//                mPresenter = new DingdanPresenterImpl(FragmentDingdan.this,App.USER_INFO, App.MID,Integer.valueOf(Id),isDeal);
            }
        });

        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);


//        mPresenter = new DingdanPresenterImpl(this,username,mid,statusid,isDeal);
        mPresenter = new DingdanPresenterImpl(this, App.USER_INFO, App.MID,Integer.valueOf(Id),isDeal);

        test_beans=new ArrayList<>();
        getAdapter(test_beans);

    }

    List<DingdanZhuangTai> test_beans;

    public void getAdapter(final List<DingdanZhuangTai> beans){
        if (mBaseRecyclerAdapter == null) {
            mBaseRecyclerAdapter = new BaseRecyclerAdapter<DingdanZhuangTai>(getActivity(), beans) {
                @Override
                public int getItemLayoutId(int viewType) {

                    return R.layout.item_list_twozhang;
                }

                @Override
                public void bindData(BaseRecyclerViewHolder holder, int position, DingdanZhuangTai item) {
                    holder.getTextView(R.id.tv_title).setText(item.ccNumber);
                    holder.getTextView(R.id.tv_time5).setText(item.ccType);
                    holder.getTextView(R.id.tv_describe).setText(item.cname);
                    holder.getTextView(R.id.tv_time).setText(item.Installationarea);
                    holder.getTextView(R.id.tv_time2).setText(item.ManCompany);
                    holder.getTextView(R.id.tv_time3).setText(item.stuname);
                    holder.getTextView(R.id.tv_time4).setText(item.addPer);
                    holder.getTextView(R.id.tv_phone).setText(item.billdate);
                    if (isDeal)
                    {
                        holder.getTextView(R.id.item_list_twozhang_kaiqi).setText("已确认");
                    }else{
                        holder.getTextView(R.id.item_list_twozhang_kaiqi).setText("待确认");
                    }
                }
            };

            //基础验收确认 的 item点击事件  跳转到DingdanConameListActivity 里面加上传值
            mBaseRecyclerAdapter.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
//                    Snackbar.make(view, "位置:" + position, Snackbar.LENGTH_SHORT).show();
                    if (mBaseRecyclerAdapter.getData().size() > 0) {
                        Intent intent = new Intent(FragmentDingdan.this.getActivity(), DingdanConameListActivity.class);
                     // Ccid
                        intent.putExtra("Ccid", mBaseRecyclerAdapter.getData().get(position).ID);
                        //ccnumber
                        intent.putExtra("ccNumber", mBaseRecyclerAdapter.getData().get(position).ccNumber);
                      // statusid
                        intent.putExtra("statusid", Id);

                        startActivity(intent);
                    }
                    else {
                        KLog.e("mBaseRecyclerAdapter.getData()没数据");
                    }
                }

                @Override
                public void onItemLongClick(View view, int position) {

                }
            });

            final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(),
                    LinearLayoutManager.VERTICAL, false);

            id_recyclerview.setLayoutManager(linearLayoutManager);
            id_recyclerview.setHasFixedSize(true);
            id_recyclerview.addItemDecoration(new BaseSpacesItemDecoration(DensityUtils.dp2px(getActivity(),2)));
            id_recyclerview.setItemAnimator(new DefaultItemAnimator());
            id_recyclerview.setAdapter(mBaseRecyclerAdapter);

            id_recyclerview.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);
                    int topRowVerticalPosition =
                            (recyclerView == null || recyclerView.getChildCount() == 0) ? 0 : recyclerView.getChildAt(0).getTop();
                    swipeRefreshLayout.setEnabled(topRowVerticalPosition >= 0);
                }
            });
        }
    }


    @Override
    public void updateNewsList(final List<DingdanZhuangTai> beans) {
//        viewById.setText(beans.toString());
        mBaseRecyclerAdapter.setData(beans);
    }

    @Override
    public void CompleteforV() {
    }

    @Override
    public void getSecondPage() {
        mPresenter.refreshData(LoadOrder.Second);
    }
}
