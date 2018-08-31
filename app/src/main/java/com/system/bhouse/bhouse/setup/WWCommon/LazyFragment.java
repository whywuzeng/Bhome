package com.system.bhouse.bhouse.setup.WWCommon;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import com.system.bhouse.bhouse.R;

import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018-07-17.
 * <p>
 * by author wz
 * <p>
 * com.system.bhouse.bhouse.setup.WWCommon
 */

public class LazyFragment extends WWBaseFragment {

    private boolean isInit = false;//真正要显示的view是否已经被初始化(正常加载)
    private Bundle savedInstancseState;
    public static final String INTENT_BOOLEAN_LAZYLOAD = "intent_boolean_lazyLoad";
    private boolean isLazyLoad = true;//默认状态需要懒加载
    private FrameLayout layout;
    private boolean isStart = false;// 是否处于可见状态，in the screen

    @Override
    protected final void onCreateView(Bundle savedInstancseState) {
        super.onCreateView(savedInstancseState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            isLazyLoad = bundle.getBoolean(INTENT_BOOLEAN_LAZYLOAD, isLazyLoad);
        }
        //判断是否懒加载
        if (isLazyLoad) {
            //一旦isVisibleToUser==true即可对真正需要的显示内容进行加载
            if (getUserVisibleHint() && !isInit) {
                this.savedInstancseState = savedInstancseState;
                onCreateViewLazy(savedInstancseState);
                isInit = true;
            }
            else {
                //进行懒加载
                layout=new FrameLayout(getApplicationContext());
                layout.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT));
                View view= LayoutInflater.from(getApplicationContext()).inflate(R.layout.taskcomon_empty_view,null);
                layout.addView(view);
                super.setContentView(layout);
            }
        }
        else {
            //不需要懒加载，开门见山，调用onCreateViewLazy正常加载显示内容即可
            onCreateViewLazy(savedInstancseState);
            isInit = true;
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        Log.d("TAG", "setUserVisibleHint() called with: " + "isVisibleToUser = [" + isVisibleToUser + "]");
        //一旦isVisibleToUser==true即可进行对真正需要的显示内容的加载
        //可见，但还没被初始化
        if (isVisibleToUser && !isInit && getContentView() != null) {
            onCreateViewLazy(savedInstancseState);
            isInit = true; //标志 是否执行了 onCreateViewLazy
            onResumeLazy();//TODO
        }
        //已经被初始化(正常加载)过了
        if (isInit && getContentView() != null) {
            if (isVisibleToUser) {
                isStart = true;
                onFragmentStartLazy();//TODO
            }
            else {
                isStart = false;
                onFragmentStopLazy();
            }
        }
    }

    @Override
    public void setContentView(int layoutResID) {
        //判断若isLazyLoad=true,移除所有lazy view，加载真正要显示的view
        if (isLazyLoad && getContentView() != null && getContentView().getParent() != null) {
            layout.removeAllViews();
            View view = inflater.inflate(layoutResID, layout, false);
            layout.addView(view);
        }
        else {
            super.setContentView(layoutResID);
        }
        ButterKnife.bind(this,getContentView());
    }

    @Override
    public void setContentView(View view) {
        //判断若isLazyLoad=true,移除所有lazy view，加载真正要显示的view
        if (isLazyLoad && getContentView() != null && getContentView().getParent() != null) {
            layout.removeAllViews();
            layout.addView(view);
        }
        else {
            //否则开门见山，直接加载  不需要lazy加载
            super.setContentView(view);
        }
    }

    @Override
    public final void onStart() {
        Log.d("TAG", "onStart() : " + "getUserVisibleHint():" + getUserVisibleHint());
        super.onStart();
        if (isInit && !isStart && getUserVisibleHint()) {
            isStart = true;
            onFragmentStartLazy();
        }
    }


    @Override
    public final void onStop() {
        super.onStop();
        if (isInit && isStart && getUserVisibleHint()) {
            isStart = false;
            onFragmentStopLazy();
        }
    }

    @Override
    @Deprecated
    public final void onResume() {
        Log.d("TAG", "onResume() : " + "getUserVisibleHint():" + getUserVisibleHint());
        super.onResume();
        if (isInit) {
            onResumeLazy();
        }
    }

    @Override
    @Deprecated
    public final void onPause() {
        Log.d("TAG", "onPause() : " + "getUserVisibleHint():" + getUserVisibleHint());
        super.onPause();
        if (isInit) {
            onPauseLazy();
        }
    }

    @Override
    @Deprecated
    public final void onDestroyView() {
        Log.d("TAG", "onDestroyView() : " + "getUserVisibleHint():" + getUserVisibleHint());
        super.onDestroyView();
        if (isInit) {
            onDestroyViewLazy();
        }
        isInit = false;
        ButterKnife.unbind(this);
    }

    //当Fragment被滑到可见的位置时，调用
    protected void onFragmentStartLazy() {
        Log.d("TAG", "onFragmentStartLazy() called with: " + "");

    }

    //当Fragment被滑到不可见的位置，offScreen时，调用
    protected void onFragmentStopLazy() {
        Log.d("TAG", "onFragmentStopLazy() called with: " + "");
    }

    //初始化数据加载地方
    protected void onCreateViewLazy(Bundle savedInstanceState) {
        Log.d("TAG", "onCreateViewLazy() called with: " + "savedInstanceState = [" + savedInstanceState + "]");
    }
    //
    protected void onResumeLazy() {
        Log.d("TAG", "onResumeLazy() called with: " + "");
    }
    //类似关闭屏幕 已经初始化 调用
    protected void onPauseLazy() {
        Log.d("TAG", "onPauseLazy() called with: " + "");
    }

    protected void onDestroyViewLazy() {

    }



}
