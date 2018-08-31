package com.system.bhouse.bhouse.CommonTask.MaintainManagement.LoadingIntoWareHouseFragment.Base;

import android.support.v7.widget.Toolbar;
import android.view.View;

import com.system.bhouse.bhouse.R;


/**
 * Created by Administrator on 2018-08-23.
 * <p>
 * by author wz
 * <p>
 * com.example.wz1.mysigninapplication.Base
 */

public class BaseBackFragment extends BaseMySupportFragment {

    protected void initToolbarNav(Toolbar toolbar) {
        toolbar.setNavigationIcon(R.drawable.ic_keyboard_arrow_left);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pop();
            }
        });
    }
}
