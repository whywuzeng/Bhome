package com.system.bhouse.bhouse.CommonTask.TechnologyExecution.selectploy;

import android.view.View;
import android.widget.TextView;

import com.system.bhouse.base.App;
import com.system.bhouse.bhouse.R;

/**
 * Created by Administrator on 2018-07-09.
 * <p>
 * by author wz
 * <p>
 * com.system.bhouse.bhouse.CommonTask.TechnologyExecution.selectploy
 */

public class UnSelectLineDotBg implements SelectPloy,UnSelectPloy {

    @Override
    public void selectBg(View view) {
        if (view==null)
        {
            return;
        }
        TextView tvView = (TextView) view.findViewById(R.id.tvTopLine);
        TextView tvDot = (TextView) view.findViewById(R.id.tvDot);
        TextView tvBottom = (TextView) view.findViewById(R.id.tvBottom);

        tvView.setBackground(App.getContextApp().getResources().getDrawable(R.color.fc5));
        tvDot.setBackground(App.getContextApp().getResources().getDrawable(R.drawable.bg_timeline_btn_select));
        tvBottom.setBackground(App.getContextApp().getResources().getDrawable(R.color.fc5));
    }

    @Override
    public void UnSelectbg(View view) {
        if (view==null)
        {
            return;
        }
        TextView tvView = (TextView) view.findViewById(R.id.tvTopLine);
        TextView tvDot = (TextView) view.findViewById(R.id.tvDot);
        TextView tvBottom = (TextView) view.findViewById(R.id.tvBottom);

        tvView.setBackground(App.getContextApp().getResources().getDrawable(R.color.common_color_text_8));
        tvDot.setBackground(App.getContextApp().getResources().getDrawable(R.drawable.timelline_dot_normal));
        tvBottom.setBackground(App.getContextApp().getResources().getDrawable(R.color.common_color_text_8));
    }
}
