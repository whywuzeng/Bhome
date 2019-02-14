package com.system.bhouse.bhouse.CommonTask.MaintainManagement.LoadingIntoWareHouseFragment.Base;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.socks.library.KLog;
import com.system.bhouse.bhouse.R;
import com.system.bhouse.bhouse.setup.SingleToast;
import com.system.bhouse.bhouse.setup.WWCommon.WWBaseActivity;

import java.lang.reflect.Field;

import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018-08-24.
 * <p>
 * by author wz
 * <p>
 * com.system.bhouse.bhouse.CommonTask.MaintainManagement.LoadingIntoWareHouseFragment.Base
 */

public class BaseMySupportFragment extends MySupportFragment{

    private static final String TAG = "BaseMySupportFragment";
    
    private Context context;
    protected LayoutInflater inflater;
    private SingleToast mSingleToast;
    private ProgressDialog mProgressDialog;
    protected View errorView;
    protected View notDataView;
    private ViewGroup container;
    private View contentView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context=_mActivity.getApplicationContext();
        inflater = LayoutInflater.from(_mActivity);
        mSingleToast = new SingleToast(_mActivity);

        mProgressDialog = new ProgressDialog(_mActivity);
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setCancelable(false);
    }

//                            错误，空数据view
    /////////////////////////////////////////////////////////////////////////////////////////////////////////
    protected void recycleViewAddEmptySection(RecyclerView mRecyclerView)
    {
        notDataView = inflater.inflate(R.layout.taskcomon_empty_view, (ViewGroup) mRecyclerView.getParent(), false);
        errorView = inflater.inflate(R.layout.taskcommon_error_view,(ViewGroup)mRecyclerView.getParent(), false);
    }

    protected void setErrorViewContext(String errorMsg)
    {
        if (errorView !=null){
            TextView viewById = (TextView) errorView.findViewById(R.id.tv_msg_context);
            viewById.setText(errorMsg);
        }
    }


    @Override
    public void onDestroy() {
        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
            mProgressDialog = null;
        }

        super.onDestroy();
    }

    //添加滑动响应
    protected void setScrollViewFirst() {

        NestedScrollView mNestedScrollView = (NestedScrollView) findViewById(R.id.nested_view);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mNestedScrollView.setScrollY(0);
            }
        });
    }

//                                        简化写法,没什么鸟用
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * 封装为 findbyid 统一写法
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        KLog.e("onCreateView");
        this.inflater=inflater;
        this.container =container;
        onCreateView(savedInstanceState);
        if(contentView ==null)
            return super.onCreateView(inflater,container,savedInstanceState);
        return contentView;
    }

    //fragment初始化的地方
    protected void onCreateView(Bundle savedInstanceState) {

    }

    //设置 contentView
    public void setContentView(int layoutResID)
    {
        setContentView((ViewGroup)inflater.inflate(layoutResID,container,false));
    }

    public void setContentView(View view)
    {
        contentView=view;
        ButterKnife.bind(this,contentView);
    }

    public View getContentView()
    {
        return contentView;
    }

    public View findViewById(int id)
    {
        if(contentView !=null)
        {
            return contentView.findViewById(id);
        }
        return null;
    }

    //没有仔细研究这是什么作用
    // http://stackoverflow.com/questions/15207305/getting-the-error-java-lang-illegalstateexception-activity-has-been-destroyed
    @Override
    public void onDetach() {
        KLog.e(TAG, "onDetach() : ");
        super.onDetach();
        try {
            Field childFragmentManager = Fragment.class.getDeclaredField("mChildFragmentManager");
            childFragmentManager.setAccessible(true);
            childFragmentManager.set(this, null);

        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
//                        防装下toast
    ////////////////////////////////////////////////////////////////////////////////////////
public void showButtomToast(String msg) {
    if (!isResumed() || mSingleToast == null) {
        return;
    }

    mSingleToast.showButtomToast(msg);
}

    public void showMiddleToast(int id) {
        if (!isResumed() || mSingleToast == null) {
            return;
        }
        mSingleToast.showMiddleToast(id);
    }

    public void showMiddleToast(String msg) {
        if (!isResumed() || mSingleToast == null) {
            return;
        }
        mSingleToast.showMiddleToast(msg);
    }

    public void showButtomToast(int messageId) {
        if (!isResumed() || mSingleToast == null) {
            return;
        }

        mSingleToast.showButtomToast(messageId);
    }

//                                    实现下简易progressView ,要封装成 黑框的背景
    ////////////////////////////////////////////////////////////////////////////////////////


    protected void showProgressBar(boolean show, String message) {
        if (mProgressDialog == null) {
            return;
        }

        if (show) {
            mProgressDialog.setMessage(message);
            mProgressDialog.show();
        } else {
            mProgressDialog.hide();
        }
    }

    protected void showProgressBar(boolean show) {
    showProgressBar(show, "");
    }

    protected void setProgressBarProgress() {
        if (mProgressDialog == null) {
            return;
        }

        mProgressDialog.setIndeterminate(false); //进度条是否有明确的数值
        mProgressDialog.setProgress(30);//设置进度条为多少
    }

    //                                    实现下简易DialogProgressView ,引用Activity下封装的
////////////////////////////////////////////////////////////////////////////////////////////////////

    protected void showDialogLoading() {
        if (_mActivity instanceof WWBaseActivity) {
            ((WWBaseActivity) _mActivity).showDialogLoading();
        }
    }

    protected void hideDialogLoading() {
        if (_mActivity instanceof WWBaseActivity) {
            ((WWBaseActivity) _mActivity).hideProgressDialog();
        }
    }

    protected void setActionBarMidlleTitle(String title)
    {
        TextView toolbar_title = (TextView)getActivity().findViewById(R.id.toolbar_title);
        if (toolbar_title!=null)
        {
            toolbar_title.setText(title);
        }
    }

    protected void setParentActionBarMidleTitle(String title)
    {
        TextView toolbar_title = (TextView)getView().findViewById(R.id.toolbar_title);
        if (toolbar_title!=null)
        {
            toolbar_title.setText(title);
        }
    }

}
