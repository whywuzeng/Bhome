package com.system.bhouse.bhouse.CommonTask.MaterialControl.PlateMaterial;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.system.bhouse.api.ApiWebService;
import com.system.bhouse.base.App;
import com.system.bhouse.base.StatusBean;
import com.system.bhouse.bhouse.CommonTask.BaseTaskFragment.BaseCommonListFragment;
import com.system.bhouse.bhouse.CommonTask.MaterialControl.entity.PlatematerialBean;
import com.system.bhouse.bhouse.R;
import com.system.bhouse.utils.TenUtils.L;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018-03-19.
 * <p>
 * by author wz
 * 完工入库
 * <p>
 * com.system.bhouse.bhouse.ConfirmationReceipt
 */
@EFragment(R.layout.fragment_plate_material_list)
public class PlateMaterialListFragment extends BaseCommonListFragment<PlateMaterialLoadingAdapter,PlatematerialBean> implements PlateMaterialLoadingAdapter.onItemClickListener {

    PlateMaterialLoadingAdapter mAdapter;
    private boolean isDealWith=false;

    //默认是 提交保存 。即可在此数组进行修改
    String[] ContentTitle= new String[]{"", "保存", "审核"};

    //接收更新数据
    protected Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == REFRESH_DATA) {
                setRefreshing(false);
                ArrayList<PlatematerialBean> loadedRequires = msg.getData().getParcelableArrayList(LOADEDREQUIREKEY);
                if (loadedRequires.size()==0)
                {
                    if (mNoData) {
                        mAdapter.setEmptyView(notDataView);
                        AllCopymData.clear();
                        AllCopymData.addAll(loadedRequires);
                        mData.clear();
                        mAdapter.setDataList(mData);
                        mNoData=false;
                    }
                }else if (loadedRequires.size() > 0) {
                    AllCopymData.clear();
                    AllCopymData.addAll(loadedRequires);
                    mData.clear();
                    SequenceData();
                }
            }
        }
    };
    private TextView TvDealWith;
    private Drawable drawableGouSeleted;
    private Drawable drawablePositiveGou;

    @AfterViews
    public void initConfirmation() {
        initRefreshLayout();
        mAdapter = new PlateMaterialLoadingAdapter(mData);
        baseCommoninitView(mAdapter);
        mAdapter.setDataList(mData);
        listView.setAdapter(mAdapter);
        mAdapter.setmOnItemClickListener(this);
        setStatusContent(ContentTitle);
        TvDealWith = (TextView) rootView.findViewById(R.id.tv_finish_dealwith);
        ImageView arrowRight = rootView.findViewById(R.id.tv_arrow_right);
        arrowRight.setVisibility(View.GONE);
        TvDealWith.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.ic_positive_gou), null, null, null);
        LinearLayout tasklistHead = (LinearLayout) rootView.findViewById(R.id.head_tasklist);
        tasklistHead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //查看已处理
                setTvDealWithBg();
                mUpdateAll = true;

                onRefresh();
            }
        });
        drawableGouSeleted = rootView.getResources().getDrawable(R.drawable.ic_gou_selected);
        drawablePositiveGou = rootView.getResources().getDrawable(R.drawable.ic_positive_gou);

    }

    private void setTvDealWithBg(){
        if (!isDealWith)
        {
            drawableGouSeleted.setBounds(0,0,drawableGouSeleted.getIntrinsicWidth(),drawableGouSeleted.getIntrinsicHeight());
            TvDealWith.setCompoundDrawables(drawableGouSeleted,null,null,null);
            mStatus=ContentTitle[2];
        }else{
            //审核状态
            drawablePositiveGou.setBounds(0,0,drawablePositiveGou.getIntrinsicWidth(),drawablePositiveGou.getIntrinsicHeight());
            TvDealWith.setCompoundDrawables(drawablePositiveGou,null,null,null);
        }
        isDealWith=!isDealWith;
    }


    //加载主数据
    protected void loadData() {
        if (mUpdateAll) {
            mUpdateAll = false;
            ApiWebService.Get_Production_order_Tray_Json(getActivity(), new ApiWebService.SuccessCall() {

                @Override
                public void SuccessBack(String result) {
                    L.e(result);
                    ArrayList<PlatematerialBean> loadedRequires = App.getAppGson().fromJson(result, new TypeToken<List<PlatematerialBean>>() {
                    }.getType());

                    Message message = Message.obtain();
                    Bundle bundle = new Bundle();
                    bundle.putParcelableArrayList(LOADEDREQUIREKEY, loadedRequires);
                    message.setData(bundle);
                    message.what = REFRESH_DATA;
                    handler.sendMessage(message);

                    if (isRefreshing()) {
                        setRefreshing(false);
                    }
                }

                @Override
                public void ErrorBack(String error) {
                    if (mError) {
                        mError=false;
                        mAdapter.setEmptyView(errorView);
                    }
                    L.e(error);
//                    taskFragmentLoading(false);
                    if (isRefreshing()) {
                        setRefreshing(false);
                    }
                }
            }, TextUtils.isEmpty(mLabel) ? 50 : Integer.valueOf(mLabel), TextUtils.isEmpty(mStatus) ? ContentTitle[1] : mStatus, TextUtils.isEmpty(mKeyword) ? "" : mKeyword,isDealWith);

//      ApiWebService.Get_Production_order_TrayView_Json(getActivity(), new ApiWebService.SuccessCall() {
//                @Override
//                public void SuccessBack(String result) {
//
//                }
//
//                @Override
//                public void ErrorBack(String error) {
//
//                }
//            }, "fe6e3bb77d4a4b01a1b3426083a8e7e7");
        }

    }

    //刷新本数据，以及根据业务获取，字段。Hnumber->订单编号
    @Override
    protected void AddDeleteUpdateListData(Object object) {
        if (object.toString().contains("tvSubmitAction"))
        {
        }
        //重新加载所有
        mUpdateAll = true;
        onRefresh();
    }

    //list Item的点击事件
    @Override
    public void ItemClick(PlateMaterialLoadingAdapter.ItemViewHolder holder, int position) {
        mNeedUpdate = true;
        StatusBean statusBean = getStatusBean();
        if (DefaultStatus.equals(ContentTitle[1])) {
            statusBean.getBean().setVisModifyBtn(true);
        }
        statusBean.setLookStatus(true);
        PlateMaterialContentMessageActivity_.intent(getParentFragment()).HId(mData
                .get(position).getID() + "").receiptHnumber(mData.get(position).getHNumbe()).mStatus(statusBean).start();
    }

}
