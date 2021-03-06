package com.system.bhouse.bhouse.CommonTask.MaintainManagement.LoadingIntoWareHouseFragment.Base;

import android.app.Dialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.system.bhouse.api.ApiWebService;
import com.system.bhouse.base.App;
import com.system.bhouse.base.BHBaseSubscriber;
import com.system.bhouse.base.StatusBean;
import com.system.bhouse.bhouse.CommonTask.BaseTaskFragment.BaseContentMessageActivity;
import com.system.bhouse.bhouse.R;
import com.system.bhouse.utils.TenUtils.L;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by Administrator on 2018-08-23.
 * <p>
 * by author wz
 * <p>
 * com.system.bhouse.bhouse.CommonTask.MaintainManagement.LoadingIntoWareHouseFragment.Base
 */

public abstract class BaseContentMessageFragment extends BaseBackFragment {

    protected Dialog bottomDialog;
    private TextView tvQrcode;
    protected LinearLayout llQrcodeAdd;
    protected TextView tvQrcodeAdd;
    private StatusBean mStatusBean;

    /**
     * show1 展示 dialog
     */
    protected void show1(StatusBean mStatusBean) {
        this.mStatusBean=mStatusBean;
        bottomDialog = new Dialog(_mActivity, R.style.BottomDialog);
        View contentView = LayoutInflater.from(_mActivity).inflate(R.layout.taskmessage_dialog_content_normal, null);
        bottomDialog.setContentView(contentView);
        LinearLayout llModify = (LinearLayout) contentView.findViewById(R.id.ll_modify);
        LinearLayout llSubmit = (LinearLayout) contentView.findViewById(R.id.ll_submit);
        LinearLayout llCheck = (LinearLayout) contentView.findViewById(R.id.ll_check);
        LinearLayout llFanCheck = (LinearLayout) contentView.findViewById(R.id.ll_fanCheck);
        LinearLayout llQrcode = (LinearLayout) contentView.findViewById(R.id.ll_qrcode);
        llQrcodeAdd =(LinearLayout)contentView.findViewById(R.id.ll_qrcode_add);

        TextView tvModify = (TextView) contentView.findViewById(R.id.tv_modify);
        TextView tvSubmit = (TextView) contentView.findViewById(R.id.tv_submit);
        TextView tvCheck = (TextView) contentView.findViewById(R.id.tv_check);
        TextView tvFanCheck = (TextView) contentView.findViewById(R.id.tv_fanCheck);
        TextView tvDelete = (TextView)contentView.findViewById(R.id.tv_delete);
        tvQrcode = (TextView)contentView.findViewById(R.id.tv_qrcode);
        tvQrcodeAdd = (TextView)contentView.findViewById(R.id.tv_qrcode_add);

        Observable observableMobileKey = ApiWebService.Get_KeyTimestr(App.MobileKey);

        Subscriber subscriberMobileKey= new BHBaseSubscriber<Object>() {
            @Override
            public void onCompleted() {
                super.onCompleted();
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
            }

            @Override
            public void onNext(Object o) {
                super.onNext(o);
                App.KeyTimestring = o.toString();
            }
        };

        tvQrcodeAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mSetOnAddItemClickListener!=null)
                {
                    observableMobileKey.concatWith(Observable.create(new Observable.OnSubscribe<Object>() {
                        @Override
                        public void call(Subscriber<? super Object> subscriber) {
                            mSetOnAddItemClickListener.onAddItemClick(v);
                        }
                    })).subscribe(subscriberMobileKey);
                }
                bottomDialog.dismiss();
            }
        });

        ShowPolicy(mStatusBean, llModify, llSubmit, llCheck, llFanCheck, llQrcode, tvDelete);
        /**
         * 增加的qrAdd item条目 产线分配
         */


        Observable.create(subscriber -> {
            tvQrcode.setOnClickListener(v ->{subscriber.onNext(v);
            });
        }).debounce(350, TimeUnit.MILLISECONDS).observeOn(AndroidSchedulers.mainThread()).subscribe(V -> {

            observableMobileKey.concatWith(Observable.create(new Observable.OnSubscribe<Object>() {
                @Override
                public void call(Subscriber<? super Object> subscriber) {
                    bottomDialog.dismiss();
                    tvQrcodeAction(tvQrcode);
                }
            })).subscribe(subscriberMobileKey);

        });


        Observable.create(subscriber -> {
            tvDelete.setOnClickListener(v ->{subscriber.onNext(v);
            });
        }).debounce(350, TimeUnit.MILLISECONDS).observeOn(AndroidSchedulers.mainThread()).subscribe(V -> {
            observableMobileKey.concatWith(Observable.create(new Observable.OnSubscribe<Object>() {
                @Override
                public void call(Subscriber<? super Object> subscriber) {
                    bottomDialog.dismiss();
                    tvDeleteAction(tvDelete);
                }
            })).subscribe(subscriberMobileKey);
        });


        Observable.create(subscriber -> {
            tvFanCheck.setOnClickListener(v ->{subscriber.onNext(v);
            });
        }).debounce(350, TimeUnit.MILLISECONDS).observeOn(AndroidSchedulers.mainThread()).subscribe(V -> {
            observableMobileKey.concatWith(Observable.create(new Observable.OnSubscribe<Object>() {
                @Override
                public void call(Subscriber<? super Object> subscriber) {
                    bottomDialog.dismiss();
                    tvFanCheckAction(tvFanCheck);
                }
            })).subscribe(subscriberMobileKey);
        });


        tvModify.setOnClickListener(v -> {
            observableMobileKey.concatWith(Observable.create(new Observable.OnSubscribe<Object>() {
                @Override
                public void call(Subscriber<? super Object> subscriber) {
                    bottomDialog.dismiss();
                    tvModifyAction(tvModify);
                }
            })).subscribe(subscriberMobileKey);
        });

        Observable.create(subscriber -> {
            tvSubmit.setOnClickListener(v ->{subscriber.onNext(v);
            });
        }).debounce(350, TimeUnit.MILLISECONDS).observeOn(AndroidSchedulers.mainThread()).subscribe(V -> {
            observableMobileKey.concatWith(Observable.create(new Observable.OnSubscribe<Object>() {
                @Override
                public void call(Subscriber<? super Object> subscriber) {
                    L.e("double click");
                    bottomDialog.dismiss();
                    tvSubmitActionforList(tvSubmit);
                }
            })).subscribe(subscriberMobileKey);
        });


        Observable.create(subscriber -> {
            tvCheck.setOnClickListener(v ->{subscriber.onNext(v);
            });
        }).debounce(350, TimeUnit.MILLISECONDS).observeOn(AndroidSchedulers.mainThread()).subscribe(V -> {
            observableMobileKey.concatWith(Observable.create(new Observable.OnSubscribe<Object>() {
                @Override
                public void call(Subscriber<? super Object> subscriber) {
                    L.e("double click");
                    bottomDialog.dismiss();
                    tvCheckAction(tvCheck);
                }
            })).subscribe(subscriberMobileKey);
        });


        ViewGroup.LayoutParams layoutParams = contentView.getLayoutParams();
        layoutParams.width = getResources().getDisplayMetrics().widthPixels;
        contentView.setLayoutParams(layoutParams);
        bottomDialog.getWindow().setGravity(Gravity.BOTTOM);
        bottomDialog.setCanceledOnTouchOutside(true);
        bottomDialog.getWindow().setWindowAnimations(R.style.BottomDialog_Animation);
        bottomDialog.show();
    }

    /**
     *  用于子类 重写 Policy方法
     * @param mStatusBean
     * @param llModify
     * @param llSubmit
     * @param llCheck
     * @param llFanCheck
     * @param llQrcode
     * @param tvDelete
     */
    protected void ShowPolicy(StatusBean mStatusBean, LinearLayout llModify, LinearLayout llSubmit, LinearLayout llCheck, LinearLayout llFanCheck, LinearLayout llQrcode, TextView tvDelete) {

        llCheck.setVisibility(mStatusBean.getBean().visCheckBtn? View.VISIBLE:View.GONE);
        llModify.setVisibility(mStatusBean.getBean().visModifyBtn?View.VISIBLE:View.GONE);
        llFanCheck.setVisibility(mStatusBean.getBean().visCheckFBtn?View.VISIBLE:View.GONE);
        tvDelete.setVisibility(mStatusBean.getBean().visDeleteBtn?View.VISIBLE:View.GONE);
        llQrcode.setVisibility(mStatusBean.getBean().visQRBtn?View.VISIBLE:View.GONE);
        llSubmit.setVisibility(mStatusBean.getBean().visSubmitBtn?View.VISIBLE:View.GONE);

    }

    protected void setTvQrcodeContext(String text,int invisiable){
        tvQrcode.setText(text);
        tvQrcode.setVisibility(invisiable);
    }

    /**
     * 设置显示 tvAddContext item
     */
    protected boolean addInvisiable;
    protected void setTvQrAddContext(String text,boolean invisiable)
    {
        tvQrcodeAdd.setText(text);
        addInvisiable=invisiable;
        llQrcodeAdd.setVisibility(invisiable?View.VISIBLE:View.GONE);
        if (addInvisiable)
            llQrcodeAdd.setVisibility(mStatusBean.getBean().visQRBtn?View.VISIBLE:View.GONE);
    }

    protected abstract void tvQrcodeAction(TextView tvQrcode);

    protected abstract void tvDeleteAction(TextView tvDelete);

    protected abstract void tvFanCheckAction(TextView tvFanCheck);

    protected abstract void tvModifyAction(TextView tvModify);

    protected abstract void tvSubmitActionforList(TextView tvSubmit);

    protected abstract void tvCheckAction(TextView tvCheck);

    protected boolean isVisBottom(RecyclerView recyclerView){
        LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
        //屏幕中最后一个可见子项的position
        int lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition();
        //当前屏幕所看到的子项个数
        int visibleItemCount = layoutManager.getChildCount();
        //当前RecyclerView的所有子项个数
        int totalItemCount = layoutManager.getItemCount();
        //RecyclerView的滑动状态
        int state = recyclerView.getScrollState();
        if(visibleItemCount > 0 && lastVisibleItemPosition == totalItemCount - 1 && state == recyclerView.SCROLL_STATE_IDLE){
            return true;
        }else {
            return false;
        }
    }

    public void setmSetOnAddItemClickListener(BaseContentMessageActivity.SetOnAddItemClickListener mSetOnAddItemClickListener) {
        this.mSetOnAddItemClickListener = mSetOnAddItemClickListener;
    }

    private BaseContentMessageActivity.SetOnAddItemClickListener mSetOnAddItemClickListener;



    public interface SetOnAddItemClickListener{
        void onAddItemClick(View view);
    }
}
