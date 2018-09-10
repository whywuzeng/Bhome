package com.system.bhouse.bhouse.setup.WWCommon;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.socks.library.KLog;
import com.system.bhouse.base.Global;
import com.system.bhouse.bhouse.CommonTask.TransportationManagement.adapter.BaseQuickAdapter;
import com.system.bhouse.bhouse.R;
import com.system.bhouse.bhouse.setup.SingleToast;
import com.system.bhouse.bhouse.setup.program.FootUpdate;
import com.system.bhouse.utils.TenUtils.T;

import org.json.JSONObject;

import java.lang.reflect.Field;

/**
 * Created by Administrator on 2017-10-31.
 */

public class WWBaseFragment extends Fragment implements FootUpdate.LoadMore, Global.StartActivity {


    private static final String TAG = "WWBaseFragment";
    
    protected LayoutInflater inflater;
    private View contentView;
    private Context context;
    private ViewGroup container;
    protected View notDataView;
    protected View errorView;

    private ProgressDialog mProgressDialog;
    private SingleToast mSingleToast;

    protected void showProgressBar(boolean show) {
        showProgressBar(show, "");
    }

    protected void setProgressBarProgress() {
        if (mProgressDialog == null) {
            return;
        }

        mProgressDialog.setIndeterminate(false);
        mProgressDialog.setProgress(30);
    }

    protected void listViewAddHeaderSection(ListView listView) {
        View listViewHeader = LayoutInflater.from(getContext()).inflate(R.layout.divide_top_15, listView, false);
        listView.addHeaderView(listViewHeader, null, false);
    }

    protected void listViewAddFooterSection(ListView listView) {
        View listViewFooter = inflater.inflate(R.layout.divide_bottom_15, listView, false);
        listView.addFooterView(listViewFooter, null, false);
    }

    protected void recycleViewAddHeaderSection(RecyclerView recyclerView, BaseQuickAdapter adapter)
    {
        View listViewHeader = LayoutInflater.from(getContext()).inflate(R.layout.divide_top_15, recyclerView, false);
        adapter.addHeaderView(listViewHeader);
    }

    protected void recycleViewAddFooterSection(RecyclerView recyclerView, BaseQuickAdapter adapter)
    {
        View listViewFooter = inflater.inflate(R.layout.divide_bottom_15, recyclerView, false);
        adapter.addFooterView(listViewFooter);
    }

    protected void recycleViewAddEmptySection(RecyclerView mRecyclerView)
    {
        notDataView = getActivity().getLayoutInflater().inflate(R.layout.taskcomon_empty_view, (ViewGroup) mRecyclerView.getParent(), false);
        errorView = getActivity().getLayoutInflater().inflate(R.layout.taskcommon_error_view,(ViewGroup)mRecyclerView.getParent(), false);
    }

    protected void setErrorViewContext(String errorMsg)
    {
        if (errorView!=null){
            TextView viewById = (TextView) errorView.findViewById(R.id.tv_msg_context);
            viewById.setText(errorMsg);
        }
    }

    protected ActionBar getActionBar() {
        Activity activity = getActivity();
        if (activity instanceof AppCompatActivity) {
            ActionBar actionBar = ((AppCompatActivity) activity).getSupportActionBar();
            return actionBar;
        }

        return null;
    }

    protected void setActionBarShadow(int dp) {
        ActionBar actionBar = getActionBar();
        if (actionBar != null) {
            actionBar.setElevation(dp);
        }
    }

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

    public AppCompatActivity getActionBarActivity() {
        return (AppCompatActivity) getActivity();
    }

    protected void showProgressBar(int messageId) {
        String message = getString(messageId);
        showProgressBar(true, message);
    }

    protected boolean progressBarIsShowing() {
        return mProgressDialog.isShowing();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        KLog.e("onAttach");
    }

    @Override
    public void onStart() {
        super.onStart();
        KLog.e("onStart");
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        KLog.e("onActivityCreated");
    }

    @Override
    public void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        KLog.e("onCreate");
        inflater = LayoutInflater.from(getActivity());
        context=getActivity().getApplicationContext();

        mProgressDialog = new ProgressDialog(getActivity());
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setCancelable(false);

        mSingleToast = new SingleToast(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        KLog.e("onCreateView");
        this.inflater=inflater;
        this.container=container;
        onCreateView(savedInstanceState);
        if(contentView==null)
            return super.onCreateView(inflater,container,savedInstanceState);
        return contentView;
    }

    @Override
    public void onPause() {
        super.onPause();
        KLog.e("onPause");
    }

    @Override
    public void onResume() {
        super.onResume();
        KLog.e("onResume");
    }

    @Override
    public void onStop() {
        super.onStop();
        KLog.e("onStop");
    }

    @Override
    public void onDestroyView()
    {
        super.onDestroyView();
        KLog.e("onDestroyView");
        contentView=null;
        container=null;
        inflater=null;
    }


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

    @Override
    public void onDestroy() {
        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
            mProgressDialog = null;
        }

        super.onDestroy();
    }


    @Override
    public void loadMore() {

    }

    //初始化 fragment入口
    protected void onCreateView(Bundle savedInstanceState)
    {

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

    public Context getApplicationContext()
    {
        return context;
    }

    //设置 contentView
    public void setContentView(int layoutResID)
    {
        setContentView((ViewGroup)inflater.inflate(layoutResID,container,false));
    }

    public void setContentView(View view)
    {
        contentView=view;
    }

    public View getContentView()
    {
        return contentView;
    }

    public View findViewById(int id)
    {
        if(contentView!=null)
        {
            return contentView.findViewById(id);
        }
        return null;
    }

    protected void showDialog(String title, String msg, DialogInterface.OnClickListener clickOk) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        AlertDialog dialog = builder.setTitle(title)
                .setMessage(msg)
                .setPositiveButton("确定", clickOk)
                .setNegativeButton("取消", null)
                .show();
    }

    protected void showDialog(String[] titles, DialogInterface.OnClickListener clickOk) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setItems(titles, clickOk).show();
    }

    protected void setActionBarTitle(String title) {

        TextView mTextView = (TextView)getActivity().findViewById(R.id.toolbar_menu_title);
        if (mTextView!=null) {
//            setSupportActionBar(viewById);
//            getSupportActionBar().setTitle(title);
            mTextView.setText(title);

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

    public void showErrorMsg(int code, JSONObject json) {
            showButtomToast(R.string.connect_service_fail);
    }

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

    protected void showDialogLoading() {
        if (getActivity() instanceof WWBaseActivity) {
            ((WWBaseActivity) getActivity()).showDialogLoading();
        }
    }

    protected void hideDialogLoading() {
        if (getActivity() instanceof WWBaseActivity) {
            ((WWBaseActivity) getActivity()).hideProgressDialog();
        }
    }

    /**
     * 显示出toast
     * @param context
     */
    public void ShowShort(CharSequence context){
        T.showShort(getActivity(),context);
    }
}
