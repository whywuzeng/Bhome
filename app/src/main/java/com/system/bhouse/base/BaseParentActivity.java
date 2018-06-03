package com.system.bhouse.base;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Administrator on 2017-03-21.
 * ClassName: com.system.bhouse.base
 * Author:Administrator
 * Fuction:
 * UpdateUser:
 * UpdateDate:
 */
public abstract class BaseParentActivity<T extends BasePresenter> extends AppCompatActivity implements Baseview{

    private T presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getClass().isAnnotationPresent(ActivityFragmentInject.class))
        {
            getClass().getAnnotation(ActivityFragmentInject.class);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (presenter!=null)
        {
            presenter.onResume();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (presenter!=null)
        {
            presenter.onDestroy();
        }
    }


}
