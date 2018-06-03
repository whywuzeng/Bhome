package com.system.bhouse.bhouse.setup.WWCommon;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.system.bhouse.bhouse.R;
import com.system.bhouse.bhouse.setup.SingleToast;
import com.system.bhouse.bhouse.setup.program.FootUpdate;
import com.system.bhouse.base.Global;

import org.json.JSONObject;

/**
 * Created by Administrator on 2017-10-31.
 */

public class WWBaseFragment extends Fragment implements FootUpdate.LoadMore, Global.StartActivity {

    protected LayoutInflater mInflater;
    protected FootUpdate mFootUpdate = new FootUpdate();
    protected View.OnClickListener mOnClickUser = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String globalKey = (String) v.getTag();

//            Intent intent = new Intent(getActivity(), UserDetailActivity_.class);
//            intent.putExtra("globalKey", globalKey);
//            startActivity(intent);
        }
    };
    private ProgressDialog mProgressDialog;

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
        View listViewFooter = mInflater.inflate(R.layout.divide_bottom_15, listView, false);
        listView.addFooterView(listViewFooter, null, false);
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
    public void loadMore() {

    }


    @Override
    public void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        mInflater = LayoutInflater.from(getActivity());

        mProgressDialog = new ProgressDialog(getActivity());
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setCancelable(false);

        mSingleToast = new SingleToast(getActivity());
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
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

    SingleToast mSingleToast;

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
}
