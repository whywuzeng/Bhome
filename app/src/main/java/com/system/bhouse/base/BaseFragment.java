package com.system.bhouse.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.system.bhouse.Common.LoadOrder;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2016-4-15.
 * ClassName: com.system.bhouse.base
 * Author:Administrator
 * Fuction:
 * UpdateUser:
 * UpdateDate:
 */
public abstract class BaseFragment<T extends BasePresenter> extends Fragment implements Baseview {

    protected T mPresenter;

    protected View fragmentrootview;

    protected int mContentViewId;

    protected boolean isshow;

    protected boolean isstart=false;
    private Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if (fragmentrootview == null) {

            if (getClass().isAnnotationPresent(ActivityFragmentInject.class)) {
                ActivityFragmentInject annotation = getClass().getAnnotation(ActivityFragmentInject.class);
                mContentViewId = annotation.contentViewId();
            }
            else {
                throw new RuntimeException("Class must add annotations of ActivityFragmentInitPrams.class");
            }

            fragmentrootview = inflater.inflate(mContentViewId, container, false);
            unbinder = ButterKnife.bind(this, fragmentrootview);
            initview(fragmentrootview);
            isstart=!isstart;
        }
        return fragmentrootview;
    }

    protected abstract void initview(View fragmentrootview);



    @Override
    public void onResume() {
        super.onResume();
        //要第二次 onresume 才更新，这个单独写的请求网络
        if(!isstart) {
            if (mPresenter != null) mPresenter.onResume();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ViewGroup parent = (ViewGroup) fragmentrootview.getParent();
        if (parent != null) {
            parent.removeView(fragmentrootview);
        }
        if (unbinder!=null)
        {
            unbinder.unbind();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        isstart=!isstart;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(mPresenter!=null)mPresenter.onDestroy();
    }

    protected void showToastmsg(String msg) {
//        Toast.makeText(this.getActivity(), msg, Toast.LENGTH_SHORT).show();
//        com.system.bhouse.utils.TenUtils.T.showShort(this.getActivity(),"数据出错，麻烦刷新下...");
    }

    public BaseFragment() {
    }

    @Override
    public void basetoast(String msg) {
        showToastmsg(msg);
    }

    @Override
    public void showProgress() {
        isshow=true;
    }

    @Override
    public void hideProgress() {
        isshow=false;
    }

    @Override
    public void ErrorRefresh(LoadOrder type) {

    }
}
