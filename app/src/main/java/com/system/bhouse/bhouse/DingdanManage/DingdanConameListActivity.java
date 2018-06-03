package com.system.bhouse.bhouse.DingdanManage;

import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.system.bhouse.base.BaseActivity;
import com.system.bhouse.bhouse.DingdanManage.Fragment.DingdanConameListFragment;
import com.system.bhouse.bhouse.R;
import com.system.bhouse.ui.floatingtoolbar.FloatingToolbar;
import com.system.bhouse.utils.AppManager;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016-4-22.
 * ClassName: com.system.bhouse.bhouse.DingdanManage
 * Author:Administrator
 * Fuction:  一个管理标题  订单明细 的管理界面 activity
 * UpdateUser:
 * UpdateDate:
 */
public class DingdanConameListActivity extends BaseActivity implements FloatingToolbar.ItemClickListener{

    @Bind(R.id.topfanhuititle_textview_zhende)
    TextView topfanhuititle_textview_zhende;
    @Bind(R.id.rel_fragment)
    RelativeLayout rel_fragment;
    private LinearLayout fanhui_lin;
    private FragmentManager supportFragmentManager;
    private DingdanConameListFragment dingdanConameListFragment;
    private FloatingToolbar mFloatingToolbar;

    //按钮点击
    private Button btn_uploadpic_flo;
    private  Button btn_updatastatus_flo;


    @Override
    protected int getContentViewId() {
        return R.layout.dingdanconamelistactivity;
    }

    @Override
    protected String getToolbarTitle() {
        return this.getResources().getString(R.string.dingdanmingxi);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        AppManager.getAppManager().addActivity(this);
        btn_uploadpic_flo=(Button)findViewById(R.id.btn_uploadpic_flo);
        btn_updatastatus_flo=(Button)findViewById(R.id.btn_updatastatus_flo);
        initview();
        initviewFloating();
        initdate();
        initEvent();
        topfanhuititle_textview_zhende.setText(R.string.dingdanmingxi);
    }

    private void initviewFloating() {
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        mFloatingToolbar = (FloatingToolbar) findViewById(R.id.floatingToolbar);


        mFloatingToolbar.setClickListener(this);
        //fab这个直接加到里面去
        mFloatingToolbar.attachFab(fab);
        //在滚动的时候 hide 要用接口
//        mFloatingToolbar.attachRecyclerView(recyclerView);
        View customView = mFloatingToolbar.getCustomView();
        if (customView != null) {
            customView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mFloatingToolbar.hide();
                }
            });
        }

        //系统版本小于API18
        if (Build.VERSION.SDK_INT<=Build.VERSION_CODES.JELLY_BEAN_MR2) {
//        mFloatingToolbar.show();

            fab.bringToFront();
            mFloatingToolbar.bringToFront();
        }

        //上传图片
        btn_uploadpic_flo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //这里打印 响应点击事件
                mclickListenerToActivity.getClickbtnUploadpic();
            }
        });

        //更换状态
        btn_updatastatus_flo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mclickListenerToActivity.getClickbtnSwitchstatus();
            }
        });
    }

    private void initEvent() {
        fanhui_lin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /**
                 * 这里会出问题
                 */
                try {
                    AppManager.getAppManager().finishActivity(DingdanConameListActivity.class);
                } catch (Exception e) {
                    e.printStackTrace();
                    if (!DingdanConameListActivity.this.isFinishing()) {
                        DingdanConameListActivity.this.finish();
                    }
                }
            }
        });
    }
        //这个activity 是添加了一个fragment来显示  图片上传的fragment
    private void initdate() {

        supportFragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = supportFragmentManager.beginTransaction();
        fragmentTransaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);

        dingdanConameListFragment = new DingdanConameListFragment();
        Bundle data = new Bundle();
        data.putString("TEXT", "这是Activiy通过Bundle传递过来的值");
        dingdanConameListFragment.setArguments(data);//通过Bundle向Activity中传递值
        fragmentTransaction.add(R.id.rel_fragment, dingdanConameListFragment);//将fragment1设置到布局上
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commitAllowingStateLoss();
    }

    private void initview() {
        fanhui_lin=(LinearLayout)findViewById(R.id.fanhui_lin);
    }

    @Override
    public void onBackPressed() {
        try {
            AppManager.getAppManager().finishActivity(DingdanConameListActivity.class);
        } catch (Exception e) {
            e.printStackTrace();
            if (!DingdanConameListActivity.this.isFinishing()) {
                DingdanConameListActivity.this.finish();
            }
        }
    }

    //FloatingToolbar 点击事件
    @Override
    public void onItemClick(MenuItem item) {
        Snackbar.make(this.getWindow().getDecorView(), "Here's a SnackBar", Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void onItemLongClick(MenuItem item) {

    }

    //这个接口
    private getClickListenerToActivity mclickListenerToActivity;

    public void SetClickListenerToActivity(getClickListenerToActivity clickListenerToActivity)
    {
        this.mclickListenerToActivity=clickListenerToActivity;
    }

    public interface getClickListenerToActivity
    {
        void getClickbtnUploadpic();

        void getClickbtnSwitchstatus();
    }
}
