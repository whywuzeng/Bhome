package com.system.bhouse.bhouse.CommonTask.TechnologyExecution.ModuleAssignMent;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.system.bhouse.base.StatusBean;
import com.system.bhouse.bhouse.CommonTask.TechnologyExecution.ModuleAssignMent.Bean.ModuleAssignmentBeanSection;
import com.system.bhouse.bhouse.CommonTask.TransportationManagement.adapter.BaseViewHolder;
import com.system.bhouse.bhouse.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018-05-11.
 * <p>
 * by author wz
 * <p>
 * com.system.bhouse.bhouse.CommonTask.TransportationManagement.LoadingCarOrder
 */

public class ModuleAssignmentSectionAdapter extends HeaderAndFooterSectionQuickAdapter<ModuleAssignmentBeanSection, ModuleAssignmentSectionAdapter.ModuleAssignViewHolder>{

    private static final int REFRESH_DATA_COMTASK = 8888;
    private static final String POSITION = "position";
    private static final String VIEWHOLDER = "viewholder";
    private static final String POSITION_ADAPTER = "position_adapter";

    private StatusBean statusBean;
    //设置Additem 是否显示
    private boolean IsAddItem;

    public void setStringArray(String[] stringArray) {
        this.stringArray = stringArray;
    }

    private String[] stringArray;

    public ModuleAssignmentSectionAdapter(int layoutResId, int sectionHeadResId,int sectionFooterResId, List data,String[] stringArray,StatusBean statusBean) {
        super(layoutResId, sectionHeadResId, sectionFooterResId, data);
        this.stringArray=stringArray;
        this.statusBean=statusBean;
    }

    @Override
    protected void convertHead(ModuleAssignViewHolder helper, ModuleAssignmentBeanSection item) {

    }

    @Override
    protected void converFooter(ModuleAssignViewHolder helper, ModuleAssignmentBeanSection item) {
        helper.setVisible(R.id.btn_addItem, IsAddItem);
        helper.addOnClickListener(R.id.btn_addItem);
    }

    @Override
    protected void convert(ModuleAssignViewHolder helper, ModuleAssignmentBeanSection item) {
        initDataForView(helper,item);
        if (statusBean.isNewStatus() || statusBean.isModifyStatus()) {
            if (!item.t.isDisableDelete()) {
                helper.setVisible(R.id.img_delete_item,!item.t.isDisableDelete());
                helper.setVisible(R.id.iv_projectName,!item.t.isDisableDelete());
                helper.setVisible(R.id.iv_subBeizhu,!item.t.isDisableDelete());
            }
        }else if (statusBean.isLookStatus())
        {
            helper.setVisible(R.id.img_delete_item,false);
            helper.setVisible(R.id.iv_projectName,false);
            helper.setVisible(R.id.iv_subBeizhu,false);
        }

        //防止doubleClick
        helper.addOnClickListener(R.id.img_delete_item);

        //分录的点击事件
        helper.addOnClickListener(item.t.getListenerContext());
    }

    /**
     * 设定静态固定值
     */
    private void initDataForViewList(ModuleAssignViewHolder ModuleAssignViewHolder)
    {
        //1.让所有的 条目 不可见
        List<LinearLayout> lls=new ArrayList<>();
        lls.add(ModuleAssignViewHolder.ll_top1_deleteIv);
        lls.add(ModuleAssignViewHolder.ll_top_1);
        lls.add(ModuleAssignViewHolder.ll_top2);
        lls.add(ModuleAssignViewHolder.ll_top3);
        lls.add(ModuleAssignViewHolder.ll_top4);
        lls.add(ModuleAssignViewHolder.ll_top5);
        lls.add(ModuleAssignViewHolder.ll_top6);
        lls.add(ModuleAssignViewHolder.ll_bottom_4);
        lls.add(ModuleAssignViewHolder.ll_projectName);
        lls.add(ModuleAssignViewHolder.ll_ll_view_4);
        lls.add(ModuleAssignViewHolder.ll_linearlayout_4);
        lls.add(ModuleAssignViewHolder.lll_bottom_1);
        lls.add(ModuleAssignViewHolder.lll_bottom_2);

        List<View> VVs=new ArrayList<>();
        VVs.add(ModuleAssignViewHolder.vv_top1_deleteIv);
        VVs.add(ModuleAssignViewHolder.vv_top_2);
        VVs.add(ModuleAssignViewHolder.vv_top_22);
        VVs.add(ModuleAssignViewHolder.vv_top_3);
        VVs.add(ModuleAssignViewHolder.vv_top_4);
        VVs.add(ModuleAssignViewHolder.vv_top_5);
        VVs.add(ModuleAssignViewHolder.vv_top_6);
        VVs.add(ModuleAssignViewHolder.vv_bottom_4);
        VVs.add(ModuleAssignViewHolder.vv_projectName);
        VVs.add(ModuleAssignViewHolder.ll_view_4);
        VVs.add(ModuleAssignViewHolder.vvv_bottom_1);
        VVs.add(ModuleAssignViewHolder.vvv_bottom_2);
        VVs.add(ModuleAssignViewHolder.vvvv_bottom_2);

        for (LinearLayout ll :lls)
        {
            ll.setVisibility(View.GONE);
        }

        for (View vv:VVs)
        {
            vv.setVisibility(View.GONE);
        }

        //2.给 title设置固定值
        List<TextView> tvs=new ArrayList<>();
        tvs.add(ModuleAssignViewHolder.tv_wuliao_id);
        tvs.add(ModuleAssignViewHolder.tv_WuliaoUnit_id);
        tvs.add(ModuleAssignViewHolder.tv_count_measure_id);
        tvs.add(ModuleAssignViewHolder.tv_count_amount_id);
        tvs.add(ModuleAssignViewHolder.tv_count_id);
        tvs.add(ModuleAssignViewHolder.tv_unit_id);
        tvs.add(ModuleAssignViewHolder.tv_requireNum_id);
        tvs.add(ModuleAssignViewHolder.tv_ReceiptAmount_id);
        tvs.add(ModuleAssignViewHolder.tv_projectName_id);
        tvs.add(ModuleAssignViewHolder.tv_dong_id);
        tvs.add(ModuleAssignViewHolder.tv_cengAmount_id);
        tvs.add(ModuleAssignViewHolder.tv_shifouproduce_id);
        tvs.add(ModuleAssignViewHolder.tv_subBeizhu_id);

        for (int i=0;i<stringArray.length;i++)
        {
            if (stringArray!=null&&stringArray.length>0)
                tvs.get(i).setText(stringArray[i]);
        }

        //4.显示 业务个数的 条目

        for (int i=0;i<stringArray.length;i++)
        {
            lls.get(i).setVisibility(View.VISIBLE);
            VVs.get(i).setVisibility(View.VISIBLE);
            ModuleAssignViewHolder.vvvv_bottom_2.setVisibility(View.VISIBLE);
        }

    }


    /**
     * 设置控件的固定值  和 获取值 和 是否该显示
     */
    private void initDataForView(ModuleAssignViewHolder moduleAssignViewHolder, ModuleAssignmentBeanSection item) {

        //3.给 content 设置 获取值
        List<TextView> contvs=new ArrayList<>();
        contvs.add(moduleAssignViewHolder.tv_wuliao);
        contvs.add(moduleAssignViewHolder.WuliaoUnit);
        contvs.add(moduleAssignViewHolder.tv_count_measure);
        contvs.add(moduleAssignViewHolder.tv_count_amount);
        contvs.add(moduleAssignViewHolder.tv_count);
        contvs.add(moduleAssignViewHolder.tv_unit);
        contvs.add(moduleAssignViewHolder.tv_requireNum);
        contvs.add(moduleAssignViewHolder.tv_ReceiptAmount);
        contvs.add(moduleAssignViewHolder.tv_projectName);
        contvs.add(moduleAssignViewHolder.tv_dong);
        contvs.add(moduleAssignViewHolder.tv_cengAmount);
        contvs.add(moduleAssignViewHolder.tv_shifouproduce);
        contvs.add(moduleAssignViewHolder.tv_subBeizhu);

        contvs.get(0).setText(item.t.materialCoding);
        contvs.get(1).setText(item.t.Qrcode);
        contvs.get(2).setText(item.t.materialName);
        contvs.get(3).setText(item.t.specification);
        contvs.get(4).setText(item.t.getMeasureUnit());
        contvs.get(5).setText(item.t.startTime);
        contvs.get(6).setText(item.t.endTime);
        contvs.get(7).setText(item.t.moduleName);
        contvs.get(8).setText(item.t.number+"");

    }


    /**
     * ll_top1_deleteIv --- tv_wuliao_id ---tv_wuliao ---vv_top1_deleteIv
     * ll_top_1 --- tv_WuliaoUnit_id ---WuliaoUnit---vv_top_2
     * ll_top2 --- tv_count_measure_id ---tv_count_measure---vv_top_22
     * ll_top3 --- tv_count_amount_id ---tv_count_amount---vv_top_3
     * ll_top4 --- tv_count_id ---tv_count---vv_top_4
     * ll_top5 --- tv_unit_id ---tv_unit---vv_top_5
     * ll_top6 --- tv_requireNum_id ---tv_requireNum---vv_top_6
     * ll_bottom_4 --- tv_ReceiptAmount_id ---tv_ReceiptAmount---vv_bottom_4
     * ll_projectName --- tv_projectName_id ---tv_projectName---vv_projectName
     * ll_ll_view_4 --- tv_dong_id ---tv_dong---ll_view_4
     * ll_linearlayout_4 --- tv_cengAmount_id ---tv_cengAmount---vvv_bottom_1
     * lll_bottom_1 --- tv_shifouproduce_id ---tv_shifouproduce---vvv_bottom_2
     * lll_bottom_2 --- tv_subBeizhu_id ---tv_subBeizhu---vvvv_bottom_2
     */

    protected class ModuleAssignViewHolder extends BaseViewHolder {
        @Bind(R.id.tv_wuliao)
        TextView tv_wuliao;
        @Bind(R.id.WuliaoUnit)
        TextView WuliaoUnit;
        @Bind(R.id.tv_count)
        TextView tv_count;
        @Bind(R.id.mianLayout)
        LinearLayout mianLayout;
        @Bind(R.id.img_delete_item)
        ImageView imgDeleteItem;
        @Bind(R.id.tv_count_measure)
        TextView tv_count_measure;
        @Bind(R.id.tv_count_amount)
        TextView tv_count_amount;
        @Bind(R.id.iv_litte_grey_right_arrow)
        ImageView ivLitteGreyRightArrow;

        @Bind(R.id.tv_wuliao_id)
        TextView tv_wuliao_id;
        @Bind(R.id.tv_WuliaoUnit_id)
        TextView tv_WuliaoUnit_id;
        @Bind(R.id.tv_count_measure_id)
        TextView tv_count_measure_id;
        @Bind(R.id.tv_count_amount_id)
        TextView tv_count_amount_id;
        @Bind(R.id.tv_count_id)
        TextView tv_count_id;


        @Bind(R.id.tv_unit_id)
        TextView tv_unit_id;
        @Bind(R.id.tv_unit)
        TextView tv_unit;
        @Bind(R.id.tv_requireNum_id)
        TextView tv_requireNum_id;
        @Bind(R.id.tv_requireNum)
        TextView tv_requireNum;
        @Bind(R.id.tv_ReceiptAmount_id)
        TextView tv_ReceiptAmount_id;
        @Bind(R.id.tv_ReceiptAmount)
        TextView tv_ReceiptAmount;
        @Bind(R.id.tv_projectName_id)
        TextView tv_projectName_id;
        @Bind(R.id.tv_projectName)
        TextView tv_projectName;
        @Bind(R.id.tv_dong_id)
        TextView tv_dong_id;
        @Bind(R.id.tv_dong)
        TextView tv_dong;
        @Bind(R.id.tv_cengAmount_id)
        TextView tv_cengAmount_id;
        @Bind(R.id.tv_cengAmount)
        TextView tv_cengAmount;

        @Bind(R.id.tv_shifouproduce_id)
        TextView tv_shifouproduce_id;
        @Bind(R.id.tv_shifouproduce)
        TextView tv_shifouproduce;
        @Bind(R.id.tv_subBeizhu_id)
        TextView tv_subBeizhu_id;
        @Bind(R.id.tv_subBeizhu)
        TextView tv_subBeizhu;

        @Bind(R.id.lll_bottom_1)
        LinearLayout lll_bottom_1;
        @Bind(R.id.vvv_bottom_1)
        View vvv_bottom_1;

        @Bind(R.id.lll_bottom_2)
        LinearLayout lll_bottom_2;
        @Bind(R.id.vvv_bottom_2)
        View vvv_bottom_2;

        @Bind(R.id.ll_top_1)
        LinearLayout ll_top_1;
        @Bind(R.id.ll_bottom_4)
        LinearLayout ll_bottom_4;
        @Bind(R.id.vv_bottom_4)
        View vv_bottom_4;
        @Bind(R.id.vv_top_2)
        View vv_top_2;

        @Bind(R.id.iv_projectName)
        ImageView ivprojectname;
        @Bind(R.id.iv_subBeizhu)
        ImageView ivsubBeizhu;

        @Bind(R.id.ll_projectName)
        LinearLayout ll_projectName;

        //6-21新增的id
        @Bind(R.id.ll_top1_deleteIv)
        LinearLayout ll_top1_deleteIv;
        @Bind(R.id.vv_top1_deleteIv)
        View vv_top1_deleteIv;
        @Bind(R.id.ll_top2)
        LinearLayout ll_top2;
        @Bind(R.id.vv_top_22)
        View vv_top_22;
        @Bind(R.id.ll_top3)
        LinearLayout ll_top3;
        @Bind(R.id.vv_top_3)
        View vv_top_3;
        @Bind(R.id.ll_top4)
        LinearLayout ll_top4;
        @Bind(R.id.vv_top_4)
        View vv_top_4;
        @Bind(R.id.ll_top5)
        LinearLayout ll_top5;
        @Bind(R.id.vv_top_5)
        View vv_top_5;
        @Bind(R.id.ll_top6)
        LinearLayout ll_top6;
        @Bind(R.id.vv_top_6)
        View vv_top_6;
        @Bind(R.id.vv_projectName)
        View vv_projectName;

        @Bind(R.id.ll_ll_view_4)
        LinearLayout ll_ll_view_4;
        @Bind(R.id.ll_view_4)
        View ll_view_4;
        @Bind(R.id.ll_linearlayout_4)
        LinearLayout ll_linearlayout_4;
        @Bind(R.id.vvvv_bottom_2)
        View vvvv_bottom_2;


        ModuleAssignViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            initDataForViewList(this);
        }
    }
}
