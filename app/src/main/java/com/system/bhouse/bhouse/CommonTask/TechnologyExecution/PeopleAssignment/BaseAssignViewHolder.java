package com.system.bhouse.bhouse.CommonTask.TechnologyExecution.PeopleAssignment;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.system.bhouse.bhouse.CommonTask.TransportationManagement.adapter.BaseViewHolder;
import com.system.bhouse.bhouse.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018-08-02.
 * <p>
 * by author wz
 * <p>
 * com.system.bhouse.bhouse.CommonTask.TechnologyExecution.PeopleAssignment
 */
public class BaseAssignViewHolder {

    public TextView tv_wuliao;
    public TextView WuliaoUnit;
    public TextView tv_count;
    public LinearLayout mianLayout;
    public ImageView imgDeleteItem;
    public TextView tv_count_measure;
    public TextView tv_count_amount;
    public ImageView ivLitteGreyRightArrow;

    public TextView tv_wuliao_id;
    public TextView tv_WuliaoUnit_id;
    public TextView tv_count_measure_id;
    public TextView tv_count_amount_id;
    public TextView tv_count_id;


    public TextView tv_unit_id;
    public TextView tv_unit;
    public TextView tv_requireNum_id;
    public TextView tv_requireNum;
    public TextView tv_ReceiptAmount_id;
    public TextView tv_ReceiptAmount;
    public TextView tv_projectName_id;
    public TextView tv_projectName;
    public TextView tv_dong_id;
    public TextView tv_dong;
    public TextView tv_cengAmount_id;
    public TextView tv_cengAmount;

    public TextView tv_shifouproduce_id;
    public TextView tv_shifouproduce;
    public TextView tv_subBeizhu_id;
    public TextView tv_subBeizhu;

    public LinearLayout lll_bottom_1;
    public View vvv_bottom_1;

    public LinearLayout lll_bottom_2;
    public View vvv_bottom_2;

    public LinearLayout ll_top_1;
    public LinearLayout ll_bottom_4;
    public View vv_bottom_4;
    public View vv_top_2;

    public ImageView ivprojectname;
    public ImageView ivsubBeizhu;

    public LinearLayout ll_projectName;

    //6-21新增的id
    public LinearLayout ll_top1_deleteIv;
    public View vv_top1_deleteIv;
    public LinearLayout ll_top2;
    public View vv_top_22;
    public LinearLayout ll_top3;
    public View vv_top_3;
    public LinearLayout ll_top4;
    public View vv_top_4;
    public LinearLayout ll_top5;
    public View vv_top_5;
    public LinearLayout ll_top6;
    public View vv_top_6;
    public View vv_projectName;

    public LinearLayout ll_ll_view_4;
    public View ll_view_4;
    public LinearLayout ll_linearlayout_4;
    public View vvvv_bottom_2;


    public BaseAssignViewHolder(BaseViewHolder help) {
        this.tv_wuliao = help.getView(R.id.tv_wuliao);
        this.ll_top1_deleteIv = help.getView(R.id.ll_top1_deleteIv);
        this.tv_wuliao_id = help.getView(R.id.tv_wuliao_id);
        this.vv_top1_deleteIv = help.getView(R.id.vv_top1_deleteIv);
        this.ll_top_1 = help.getView(R.id.ll_top_1);
        this.tv_WuliaoUnit_id = help.getView(R.id.tv_WuliaoUnit_id);
        this.WuliaoUnit = help.getView(R.id.WuliaoUnit);
        this.vv_top_2 = help.getView(R.id.vv_top_2);
        this.ll_top2 = help.getView(R.id.ll_top2);
        this.tv_count_measure_id = help.getView(R.id.tv_count_measure_id);
        this.tv_count_measure = help.getView(R.id.tv_count_measure);
        this.vv_top_22 = help.getView(R.id.vv_top_22);

        this.ll_top3 = help.getView(R.id.ll_top3);
        this.tv_count_amount_id = help.getView(R.id.tv_count_amount_id);
        this.tv_count_amount = help.getView(R.id.tv_count_amount);
        this.vv_top_3 = help.getView(R.id.vv_top_3);
        this.ll_top4 = help.getView(R.id.ll_top4);
        this.tv_count_id = help.getView(R.id.tv_count_id);
        this.tv_count = help.getView(R.id.tv_count);
        this.vv_top_4 = help.getView(R.id.vv_top_4);
        this.ll_top5 = help.getView(R.id.ll_top5);
        this.tv_unit_id = help.getView(R.id.tv_unit_id);
        this.tv_unit = help.getView(R.id.tv_unit);
        this.vv_top_5 = help.getView(R.id.vv_top_5);
        this.ll_top6 = help.getView(R.id.ll_top6);
        this.tv_requireNum_id = help.getView(R.id.tv_requireNum_id);
        this.tv_requireNum = help.getView(R.id.tv_requireNum);
        this.vv_top_6 = help.getView(R.id.vv_top_6);
        this.ll_bottom_4 = help.getView(R.id.ll_bottom_4);
        this.tv_ReceiptAmount_id = help.getView(R.id.tv_ReceiptAmount_id);
        this.tv_ReceiptAmount = help.getView(R.id.tv_ReceiptAmount);
        this.vv_bottom_4 = help.getView(R.id.vv_bottom_4);
        this.ll_projectName = help.getView(R.id.ll_projectName);
        this.tv_projectName_id = help.getView(R.id.tv_projectName_id);
        this.tv_projectName = help.getView(R.id.tv_projectName);
        this.vv_projectName = help.getView(R.id.vv_projectName);
        this.ll_ll_view_4 = help.getView(R.id.ll_ll_view_4);
        this.tv_dong_id = help.getView(R.id.tv_dong_id);
        this.tv_dong = help.getView(R.id.tv_dong);
        this.ll_view_4 = help.getView(R.id.ll_view_4);
        this.ll_linearlayout_4 = help.getView(R.id.ll_linearlayout_4);
        this.tv_cengAmount_id = help.getView(R.id.tv_cengAmount_id);
        this.tv_cengAmount = help.getView(R.id.tv_cengAmount);
        this.vvv_bottom_1 = help.getView(R.id.vvv_bottom_1);
        this.lll_bottom_1 = help.getView(R.id.lll_bottom_1);
        this.tv_shifouproduce_id = help.getView(R.id.tv_shifouproduce_id);
        this.tv_shifouproduce = help.getView(R.id.tv_shifouproduce);
        this.vvv_bottom_2 = help.getView(R.id.vvv_bottom_2);
        this.lll_bottom_2 = help.getView(R.id.lll_bottom_2);
        this.tv_subBeizhu_id = help.getView(R.id.tv_subBeizhu_id);
        this.tv_subBeizhu = help.getView(R.id.tv_subBeizhu);
        this.vvvv_bottom_2 = help.getView(R.id.vvvv_bottom_2);

    }

    /**
     * ll_top1_deleteIv --- tv_wuliao_id ---tv_wuliao ---vv_top1_deleteIv
     * ll_top_1 --- tv_WuliaoUnit_id ---WuliaoUnit---vv_top_2
     * ll_top2 --- tv_count_measure_id ---tv_count_measure---vv_top_22
     * ll_top3 --- tv_count_amount_id ---tv_count_amount---vv_top_3
     * ll_top4 --- tv_count_id ---tv_count---vv_top_4
     * ll_top5 --- tv_unit_id ---tv_unit---vv_top_5
     * ll_top6 --- tv_requireNum_id ---tv_requireNum---vv_top_6
     * ll_bottom_4 --- tv_ReceiptAmount_id ---tv_ReceiptAmount---ll_bottom_4_iv_right_arrow-- vv_bottom_4
     * ll_projectName --- tv_projectName_id ---tv_projectName---vv_projectName
     * ll_ll_view_4 --- tv_dong_id ---tv_dong---ll_view_4
     * ll_linearlayout_4 --- tv_cengAmount_id ---tv_cengAmount---vvv_bottom_1
     * lll_bottom_1 --- tv_shifouproduce_id ---tv_shifouproduce---vvv_bottom_2
     * lll_bottom_2 --- tv_subBeizhu_id ---tv_subBeizhu---vvvv_bottom_2
     */

    /**
     * 设定静态固定值
     */
    public void initDataForViewList(String[] stringArray) {
        //1.让所有的 条目 不可见
        List<LinearLayout> lls = new ArrayList<>();
        lls.add(this.ll_top1_deleteIv);
        lls.add(this.ll_top_1);
        lls.add(this.ll_top2);
        lls.add(this.ll_top3);
        lls.add(this.ll_top4);
        lls.add(this.ll_top5);
        lls.add(this.ll_top6);
        lls.add(this.ll_bottom_4);
        lls.add(this.ll_projectName);
        lls.add(this.ll_ll_view_4);
        lls.add(this.ll_linearlayout_4);
        lls.add(this.lll_bottom_1);
        lls.add(this.lll_bottom_2);

        List<View> VVs = new ArrayList<>();
        VVs.add(this.vv_top1_deleteIv);
        VVs.add(this.vv_top_2);
        VVs.add(this.vv_top_22);
        VVs.add(this.vv_top_3);
        VVs.add(this.vv_top_4);
        VVs.add(this.vv_top_5);
        VVs.add(this.vv_top_6);
        VVs.add(this.vv_bottom_4);
        VVs.add(this.vv_projectName);
        VVs.add(this.ll_view_4);
        VVs.add(this.vvv_bottom_1);
        VVs.add(this.vvv_bottom_2);
        VVs.add(this.vvvv_bottom_2);

        for (LinearLayout ll : lls) {
            ll.setVisibility(View.GONE);
        }

        for (View vv : VVs) {
            vv.setVisibility(View.GONE);
        }

        //2.给 title设置固定值
        List<TextView> tvs = new ArrayList<>();
        tvs.add(this.tv_wuliao_id);
        tvs.add(this.tv_WuliaoUnit_id);
        tvs.add(this.tv_count_measure_id);
        tvs.add(this.tv_count_amount_id);
        tvs.add(this.tv_count_id);
        tvs.add(this.tv_unit_id);
        tvs.add(this.tv_requireNum_id);
        tvs.add(this.tv_ReceiptAmount_id);
        tvs.add(this.tv_projectName_id);
        tvs.add(this.tv_dong_id);
        tvs.add(this.tv_cengAmount_id);
        tvs.add(this.tv_shifouproduce_id);
        tvs.add(this.tv_subBeizhu_id);

        for (int i = 0; i < stringArray.length; i++) {
            if (stringArray != null && stringArray.length > 0)
                tvs.get(i).setText(stringArray[i]);
        }

        //4.显示 业务个数的 条目

        for (int i = 0; i < stringArray.length; i++) {
            lls.get(i).setVisibility(View.VISIBLE);
            VVs.get(i).setVisibility(View.VISIBLE);
            this.vvvv_bottom_2.setVisibility(View.VISIBLE);
        }

    }

}
