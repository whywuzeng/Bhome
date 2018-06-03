package com.system.bhouse.bhouse.CommonTask.TransportationManagement.CarManagement;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.system.bhouse.Custom.ShowDeviceMessageCustomDialog;
import com.system.bhouse.api.ApiWebService;
import com.system.bhouse.base.App;
import com.system.bhouse.base.StatusBean;
import com.system.bhouse.bean.CarManageBean;
import com.system.bhouse.bhouse.R;
import com.system.bhouse.utils.TenUtils.T;

import org.greenrobot.greendao.annotation.NotNull;

import java.util.ArrayList;

import de.greenrobot.event.EventBus;
import rx.Observable;
import rx.Subscriber;

/**
 * Created by Administrator on 2017-12-26.
 * 车次管理
 */

public class CarManagementDialogFragment extends DialogFragment {

    ShowDeviceMessageCustomDialog mDialog;

    private  ArrayList<CarManageBean> loadedRequires;
    private ShowDeviceMessageCustomDialog.Builder builder;
    private CarManageBean parcelable;
    private StatusBean statusBean;

    public CarManagementDialogFragment() {
    }

    @Override
    public void onStart() {
        // TODO Auto-generated method stub
        super.onStart();

//        Window window = getDialog().getWindow();
//        window.getDecorView().setPadding(0,0,0,0);
//        WindowManager.LayoutParams windowParams = window.getAttributes();
//        windowParams.dimAmount = 0.0f;
//        windowParams.width=WindowManager.LayoutParams.MATCH_PARENT;
//        window.setAttributes(windowParams);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        if (arguments!=null)
        {
            parcelable = (CarManageBean) arguments.getParcelable(CarManagementListFragment.ITEMDATA);
            statusBean = (StatusBean) arguments.getParcelable(CarManagementListFragment.ITEMSTATUS);
        }
    }

    public static CarManagementDialogFragment getInstanceFrg(StatusBean statusBean, @NotNull CarManageBean loadedRequires) {

        CarManagementDialogFragment viewUtils = null;
        if (viewUtils == null) {
            Bundle bundle = new Bundle();
            bundle.putParcelable(CarManagementListFragment.ITEMSTATUS, statusBean);
            if (loadedRequires!=null) {
                bundle.putParcelable(CarManagementListFragment.ITEMDATA, loadedRequires);
            }
            viewUtils = new CarManagementDialogFragment();
            viewUtils.setArguments(bundle);
        }
        return viewUtils;
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        return getDialogWithData(loadedRequires);
    }

    private Dialog getDialogWithData(@NonNull ArrayList<CarManageBean> loadedRequires) {
        if (mDialog==null) {

            builder = new ShowDeviceMessageCustomDialog.Builder(getActivity());
            View inflate = LayoutInflater.from(getActivity()).inflate(R.layout.carmanagement_edittext, null, false);
            builder.setContentView(inflate);
            EditText viewDomian = (EditText) inflate.findViewById(R.id.edt_domian);
            EditText viewContainer = (EditText) inflate.findViewById(R.id.edt_container);
            EditText viewBeizhu = (EditText) inflate.findViewById(R.id.edt_beizhu);

            if (parcelable ==null) {
                parcelable=new CarManageBean();
            }
            if (statusBean.isModifyStatus())
            {
                builder.setMiddleShow(true);
            }
            viewDomian.setText(parcelable.getCarNo());
            viewContainer.setText(parcelable.getContainernum()+"");
            viewBeizhu.setText(parcelable.getBeizhu());

            builder.setStyle(R.style.cart_dialog);

            builder.setTitle("车次管理");
            builder.setPositiveButton("保存", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {

                    Observable<Object> objectObservable = Observable.create(new Observable.OnSubscribe<Object>() {
                        @Override
                        public void call(Subscriber<? super Object> subscriber) {
                            SubmitAction(viewDomian, viewContainer, viewBeizhu,dialog);

                        }
                    });
                    Observable observableMobileKey = ApiWebService.Get_KeyTimestr(App.MobileKey);
                    observableMobileKey.concatWith(objectObservable).subscribe(new Subscriber() {
                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(Throwable e) {

                        }

                        @Override
                        public void onNext(Object o) {
                            App.KeyTimestring= o.toString();
                        }
                    });
                }
            });

            builder.setNegativeButton("取消",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });

            builder.setMiddleButton("删除", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    Observable<Object> objectObservable = Observable.create(new Observable.OnSubscribe<Object>() {
                        @Override
                        public void call(Subscriber<? super Object> subscriber) {
                            DeleteAction(dialog);
                        }
                    });
                    Observable observableMobileKey = ApiWebService.Get_KeyTimestr(App.MobileKey);
                    observableMobileKey.concatWith(objectObservable).subscribe(new Subscriber() {
                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(Throwable e) {

                        }

                        @Override
                        public void onNext(Object o) {
                            App.KeyTimestring= o.toString();
                        }
                    });
                }
            });

            mDialog = builder.create();

            mDialog.setCanceledOnTouchOutside(true);
            mDialog.getWindow().setGravity(Gravity.CENTER);
            mDialog.getWindow().getAttributes().windowAnimations = R.style.fade_dialog;

            View view = mDialog.getWindow().getDecorView();
        }
        return mDialog;
    }

    private void DeleteAction(DialogInterface dialog) {
        ApiWebService.Get_Car_list_Del(getActivity(), new ApiWebService.SuccessCall() {
            @Override
            public void SuccessBack(String result) {
                sureDataRefresh("tvSubmitAction",result, dialog);
            }

            @Override
            public void ErrorBack(String error) {

            }
        },parcelable.getID(),parcelable.getEntrypeople());
    }

    private void SubmitAction(EditText viewDomian, EditText viewContainer, EditText viewBeizhu, DialogInterface dialog) {
        //触发保存
        if (TextUtils.isEmpty(viewDomian.getText()))
        {
            T.showShort(getActivity(),"车次为空，不能保存");
            return;
        }else if (TextUtils.isEmpty(viewContainer.getText()))
        {
            T.showShort(getActivity(),"货柜为空，不能保存");
            return;
        }else {
            parcelable.setCarNo(viewDomian.getText()+"");
            parcelable.setContainernum(Integer.valueOf(String.valueOf(viewContainer.getText())));
            parcelable.setBeizhu(String.valueOf(viewBeizhu.getText()));
        }

        String[][] billtable = null;
        billtable = new String[1][4];

        billtable[0][0] = parcelable.getCarNo();
        billtable[0][1] = parcelable.getContainernum() + "";
        billtable[0][2] = parcelable.getBeizhu();
        billtable[0][3] = parcelable.getEntrypeople()==null? App.menname:parcelable.getEntrypeople();

        if (statusBean.isNewStatus()) {

            ApiWebService.Get_Car_list_Add(getActivity(), new ApiWebService.SuccessCall() {
                @Override
                public void SuccessBack(String result) {
                    sureDataRefresh("tvSubmitAction",result,dialog);
                }

                @Override
                public void ErrorBack(String error) {

                }
            }, billtable);
        }
        else if (statusBean.isModifyStatus()) {
            ApiWebService.Get_Car_list_Eedit(getActivity(), new ApiWebService.SuccessCall() {
                @Override
                public void SuccessBack(String result) {
                    sureDataRefresh("tvEditAction",result, dialog);
                }

                @Override
                public void ErrorBack(String error) {

                }
            },billtable,parcelable.getID());
        }
    }

    protected void sureDataRefresh(String type, String result, DialogInterface dialog) {
        EventBus.getDefault().post("刷新"+type+"数据"+"result:"+result);
        if (dialog!=null)
        dialog.dismiss();
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        mDialog = null;
        System.gc();
    }
}
