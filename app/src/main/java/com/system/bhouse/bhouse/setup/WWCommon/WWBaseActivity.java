package com.system.bhouse.bhouse.setup.WWCommon;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.system.bhouse.base.Global.StartActivity;
import com.system.bhouse.base.rxlife.RxSubscriptionManager;
import com.system.bhouse.bhouse.R;
import com.system.bhouse.bhouse.setup.SingleToast;
import com.system.bhouse.bhouse.setup.program.FootUpdate;
import com.system.bhouse.bhouse.setup.utils.DialogUtil;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import java.util.concurrent.TimeUnit;

import butterknife.ButterKnife;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2017-10-30.
 */

public class WWBaseActivity extends RxAppCompatActivity implements StartActivity {
    protected LayoutInflater mInflater;
    protected FootUpdate mFootUpdate = new FootUpdate();
    protected View.OnClickListener mOnClickUser = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String globalKey = (String) v.getTag();

//            UserDetailActivity_.intent(WWBaseActivity.this)
//                    .globalKey(globalKey)
//                    .start();
        }
    };
    SingleToast mSingleToast;

    private ProgressDialog mProgressDialog;

    protected int REFRESH_DATA=0x4564654;
    protected View notDataView;
    protected View errorView;

    protected void listViewAddFootSection(ListView listView) {
        View listViewFooter = getLayoutInflater().inflate(R.layout.divide_bottom_15, listView, false);
        listView.addFooterView(listViewFooter, null, false);
    }

    protected void listViewAddHeaderSection(ListView listView) {
        View listViewHeader = getLayoutInflater().inflate(R.layout.divide_top_15, listView, false);
        listView.addHeaderView(listViewHeader, null, false);
    }

    protected void recycleViewAddEmptySection(RecyclerView mRecyclerView)
    {
        notDataView = getLayoutInflater().inflate(R.layout.taskcomon_empty_view, (ViewGroup) mRecyclerView.getParent(), false);
        errorView = getLayoutInflater().inflate(R.layout.taskcommon_error_view,(ViewGroup)mRecyclerView.getParent(), false);
    }

    protected void setErrorViewContext(String errorMsg)
    {
        if (errorView!=null){
            TextView viewById = (TextView) errorView.findViewById(R.id.tv_msg_context);
            viewById.setText(errorMsg);
        }
    }

    /**
     * 载入动画  loading框
     */
    private DialogUtil.LoadingPopupWindow mDialogProgressPopWindow = null;

    /**
     * 显示ProgressBar
     */
    protected void showProgressBar(boolean show) {
        showProgressBar(show, "");
    }

    public void showProgressBar(boolean show, String message) {
        if (show) {
            mProgressDialog.setMessage(message);
            mProgressDialog.show();
        } else {
            mProgressDialog.hide();
        }
    }

    protected void showProgressBar(boolean show, int message) {
        String s = getString(message);
        showProgressBar(show, s);
    }

    protected void showProgressBar(int messageId) {
        String message = getString(messageId);
        showProgressBar(true, message);
    }

    //更新UI关闭ProgressBar
    protected Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what==REFRESH_DATA)
            {
                showProgressBar(false);
                OnNextUiChange();
            }
        }
    };

    protected void OnNextUiChange()
    {

    }



    //模拟等待
    protected final void exampleLoadingFinishe(final String msg)
    {
        final Observable.OnSubscribe<Object> objectOnSubscribe = new Observable.OnSubscribe<Object>(){
            @Override
            public void call(Subscriber<? super Object> subscriber) {
                subscriber.onCompleted();
            }
        };

        Observable.create(objectOnSubscribe).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io())
                .delay(2, TimeUnit.SECONDS).subscribe(new Subscriber<Object>() {

            @Override
            public void onStart() {
                super.onStart();
                showProgressBar(true,msg);
            }

            @Override
            public void onCompleted() {
                Message message=Message.obtain();
                handler.sendEmptyMessage(REFRESH_DATA);
            }

            @Override
            public void onError(Throwable e) {
                handler.sendEmptyMessage(REFRESH_DATA);
            }

            @Override
            public void onNext(Object o) {

            }
        });

    }

//    public void showErrorMsg(int code, JSONObject json) {
//        if (code == NetworkImpl.NETWORK_ERROR) {
//            showButtomToast(R.string.connect_service_fail);
//        } else {
//            String msg = Global.getErrorMsg(json);
//            if (!msg.isEmpty()) {
//                showButtomToast(msg);
//            }
//        }
//    }

    protected void setActionBarTitle(String title) {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
//            actionBar.setTitle(title);
        }
//        Toolbar viewById = (Toolbar) findViewById(R.id.toolbar_com);
        TextView mTextView = (TextView)findViewById(R.id.toolbar_menu_title);
        if (mTextView!=null) {
            mTextView.setText(title);
        }
    }

    protected void setActionBarMidlleTitle(String title)
    {
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar!=null)
        {
        }
        TextView toolbar_title = (TextView)findViewById(R.id.toolbar_title);
        if (toolbar_title!=null)
        {
            toolbar_title.setText(title);
        }
    }

    protected void setActionBarTitle(int title) {
        String titleString = getString(title);
        setActionBarTitle(titleString);
    }

//    public void showErrorMsgMiddle(int code, JSONObject json) {
//        if (code == NetworkImpl.NETWORK_ERROR) {
//            showMiddleToast(R.string.connect_service_fail);
//        } else {
//            String msg = Global.getErrorMsg(json);
//            if (!msg.isEmpty()) {
//                showMiddleToast(msg);
//            }
//        }
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSingleToast = new SingleToast(this);

//        networkImpl = new NetworkImpl(this, this);

        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setCancelable(false);

        mInflater = getLayoutInflater();
//        initSetting();

//        UnreadNotify.update(this);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setElevation(2);
        }

    }


    @Override
    protected void onDestroy() {
        ButterKnife.unbind(this);
        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
            mProgressDialog = null;
        }

//        GlobalSetting.getInstance().removeMessageNoNotify();
        RxSubscriptionManager.getInstanse().stopAllSubscription();

        super.onDestroy();
    }


    protected void showDialog(String title, String msg, DialogInterface.OnClickListener clickOk) {
        showDialog(title, msg, clickOk, null);
    }



    protected void showDialog(String title, String msg, DialogInterface.OnClickListener clickOk,
                              DialogInterface.OnClickListener clickCancel) {
        showDialog(title, msg, clickOk, clickCancel, "确定", "取消");
    }

    protected void showDialog(String title, String msg, DialogInterface.OnClickListener clickOk,
                              DialogInterface.OnClickListener clickCancel,
                              String okButton,
                              String cancelButton) {
        showDialog(title, msg, clickOk, clickCancel, null, okButton, cancelButton, "");
    }

    protected void showDialog(String title, String msg, DialogInterface.OnClickListener clickOk,
                              DialogInterface.OnClickListener clickCancel,
                              DialogInterface.OnClickListener clickNeutral,
                              String okButton,
                              String cancelButton,
                              String neutralButton) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title)
                .setMessage(msg);
        if (okButton != null) {
            builder.setPositiveButton(okButton, clickOk);
        }
        if (cancelButton != null) {
            builder.setNegativeButton(cancelButton, clickCancel);
        }

        if (clickNeutral != null && !neutralButton.isEmpty()) {
            builder.setNeutralButton(neutralButton, clickNeutral);
        }

        AlertDialog dialog = builder.show();
    }

    public void showButtomToast(String msg) {
        mSingleToast.showButtomToast(msg);
    }

    public void showMiddleToast(int id) {
        mSingleToast.showMiddleToast(id);
    }

    public void showMiddleToast(String msg) {
        mSingleToast.showMiddleToast(msg);
    }

    public void showMiddleToastLong(String msg) {
        mSingleToast.showMiddleToastLong(msg);
    }

    public void showButtomToast(int messageId) {
        mSingleToast.showButtomToast(messageId);
    }


    public void initDialogLoading() {
        if (mDialogProgressPopWindow == null) {
            PopupWindow.OnDismissListener onDismissListener = new PopupWindow.OnDismissListener() {
                public void onDismiss() {
                    hideProgressDialog();
                }
            };

            mDialogProgressPopWindow = DialogUtil.initProgressDialog(this, onDismissListener);
        }
    }

    //圈圈的loading框
    public void showDialogLoading(String title) {
        initDialogLoading();
        DialogUtil.showProgressDialog(this, mDialogProgressPopWindow, title);
    }

    public void showDialogLoading() {
        showDialogLoading("");
    }

    public void hideProgressDialog() {
        if (mDialogProgressPopWindow != null) {
            DialogUtil.hideDialog(mDialogProgressPopWindow);
        }
    }
}
