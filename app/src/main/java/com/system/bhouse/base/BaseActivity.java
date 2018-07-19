package com.system.bhouse.base;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import com.system.bhouse.bhouse.R;
import com.system.bhouse.utils.AppManager;
import com.system.bhouse.utils.LogUtil;
import com.zhy.autolayout.AutoLayoutActivity;

public abstract class BaseActivity extends AutoLayoutActivity {
    protected final static String TAG = "BaseActivity";
    public Context mContext;
    private ToolbarHelper mToolbarHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //设置无标题
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //添加Activity到堆栈
        AppManager.getAppManager().addActivity(this);
        setContentView(getContentViewId());
        mContext = this;

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_com);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            // 默认不显示原生标题
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            mToolbarHelper = new ToolbarHelper(toolbar);

            if (!TextUtils.isEmpty(getToolbarTitle())) {
                mToolbarHelper.setTitle(getToolbarTitle());
            }

            hanldeToolbar(mToolbarHelper);

            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBackPressed();
                }
            });
        }
    }

    protected  String getToolbarTitle()
    {
        return "";
    }

    //可以给他赋值。。title
    protected void hanldeToolbar(ToolbarHelper toolbarHelper) {

    }

    protected abstract int getContentViewId();

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                FragmentManager fm = getSupportFragmentManager();
                if (fm != null && fm.getBackStackEntryCount() > 0) {
                    fm.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                } else {
                    finish();
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        onFindViews();
        onSetListener();
        onLoadData();
    }

    /**
     * 以Toolbar 以例  扩充toolbar高度
     * @param view
     */
    public void setHeight(View view) {
        // 获取actionbar的高度
        TypedArray actionbarSizeTypedArray = obtainStyledAttributes(new int[]{
                android.R.attr.actionBarSize
        });
        float height = actionbarSizeTypedArray.getDimension(0, 0);
        // ToolBar的top值
        ViewGroup.MarginLayoutParams lp = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
        double statusBarHeight = getStatusBarHeight(this);
        lp.height = (int) (statusBarHeight + height);
        view.setPadding(0,(int) statusBarHeight,0, 0);
        view.setLayoutParams(lp);
    }

    private double getStatusBarHeight(Context context) {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen",
                "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    /**
     * 控件初始化
     */
    protected void onFindViews(){
        LogUtil.i(TAG, "onFindViews()---->");
    }

    /**
     * 设置控件事件
     */
    protected void onSetListener(){
        LogUtil.i(TAG, "onSetListener()---->");
    }

    protected void onLoadData(){
        LogUtil.i(TAG, "onLoadData()---->");
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // 点击空白处，隐藏软键盘
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            if (getCurrentFocus() != null
                    && getCurrentFocus().getWindowToken() != null) {
                InputMethodManager manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                manager.hideSoftInputFromWindow(getCurrentFocus()
                        .getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
        return super.onTouchEvent(event);
    }

    /* (non-Javadoc)
     * @see android.app.Activity#onDestroy()
     */
    @Override
    protected void onDestroy() {

        try{
//            UIHelper.cloesLoadDialog();
            LogUtil.i(TAG, "onDestroy()---->");
        }catch (Exception e) {
            System.out.println("myDialog取消，失败！");

        }
        super.onDestroy();
    }

    public static class ToolbarHelper {

        private Toolbar mToolbar;

        public ToolbarHelper(Toolbar toolbar) {
            this.mToolbar = toolbar;
        }

        public Toolbar getToolbar() {
            return mToolbar;
        }

        public void setTitle(String title) {
            TextView titleTV = (TextView) mToolbar.findViewById(R.id.toolbar_title);
            titleTV.setText(title);
        }

        public void setMenuTitle(String menuTitle, View.OnClickListener listener) {
            TextView menuTitleTV = (TextView) mToolbar.findViewById(R.id.toolbar_menu_title);
            menuTitleTV.setText(menuTitle);
            menuTitleTV.setOnClickListener(listener);
        }


    }

}

