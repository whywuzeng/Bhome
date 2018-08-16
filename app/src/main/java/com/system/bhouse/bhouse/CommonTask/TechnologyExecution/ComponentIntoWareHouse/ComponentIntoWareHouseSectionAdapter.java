package com.system.bhouse.bhouse.CommonTask.TechnologyExecution.ComponentIntoWareHouse;

import android.widget.TextView;

import com.system.bhouse.base.StatusBean;
import com.system.bhouse.bhouse.CommonTask.TechnologyExecution.ComponentIntoWareHouse.Bean.ComponentIntoWareHouseBeanSection;
import com.system.bhouse.bhouse.CommonTask.TechnologyExecution.ModuleAssignMent.HeaderAndFooterSectionQuickAdapter;
import com.system.bhouse.bhouse.CommonTask.TechnologyExecution.PeopleAssignment.BaseAssignViewHolder;
import com.system.bhouse.bhouse.CommonTask.TransportationManagement.adapter.BaseViewHolder;
import com.system.bhouse.bhouse.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018-05-11.
 * <p>
 * by author wz
 * <p>
 * com.system.bhouse.bhouse.CommonTask.TransportationManagement.LoadingCarOrder
 */

public class ComponentIntoWareHouseSectionAdapter extends HeaderAndFooterSectionQuickAdapter<ComponentIntoWareHouseBeanSection, BaseViewHolder>{

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

    public ComponentIntoWareHouseSectionAdapter(int layoutResId, int sectionHeadResId, int sectionFooterResId, List data, String[] stringArray, StatusBean statusBean) {
        super(layoutResId, sectionHeadResId, sectionFooterResId, data);
        this.stringArray=stringArray;
        this.statusBean=statusBean;
    }

    @Override
    protected void convertHead(BaseViewHolder helper, ComponentIntoWareHouseBeanSection item) {

    }

    @Override
    protected void converFooter(BaseViewHolder helper, ComponentIntoWareHouseBeanSection item) {
        helper.setVisible(R.id.btn_addItem, false);
        helper.addOnClickListener(R.id.btn_addItem);
    }

    @Override
    protected void convert(BaseViewHolder helper, ComponentIntoWareHouseBeanSection item) {
        BaseAssignViewHolder baseAssignViewHolder = new BaseAssignViewHolder(helper);
        baseAssignViewHolder.initDataForViewList(stringArray);
        initDataForView(baseAssignViewHolder,item);

        if (statusBean.isNewStatus() || statusBean.isModifyStatus()) {
            helper.setVisible(R.id.img_delete_item, false);
            helper.setVisible(R.id.iv_projectName, false);
            helper.setVisible(R.id.iv_subBeizhu, false);
            helper.setVisible(R.id.ll_bottom_4_iv_right_arrow,true);
            helper.setImageResource(R.id.ll_bottom_4_iv_right_arrow,R.drawable.ic_qrcode);
        }
        else if (statusBean.isLookStatus()) {
            helper.setVisible(R.id.img_delete_item, false);
            helper.setVisible(R.id.iv_projectName, false);
            helper.setVisible(R.id.iv_subBeizhu, false);
            helper.setVisible(R.id.ll_bottom_4_iv_right_arrow,false);
        }


        //item 点击事件
        helper.addOnClickListener(R.id.mianLayout);

        //防止doubleClick
        helper.addOnClickListener(R.id.img_delete_item);

        //分录的点击事件
        helper.addOnClickListener(R.id.ll_bottom_4);

    }


    /**
     * 设置控件的固定值  和 获取值 和 是否该显示
     */
    private void initDataForView(BaseAssignViewHolder moduleAssignViewHolder, ComponentIntoWareHouseBeanSection item) {

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
        contvs.get(4).setText(item.t.measureUnit);
        contvs.get(5).setText(item.t.checkType);

        contvs.get(6).setText(item.t.entrybeizhu);
        contvs.get(7).setText(item.t.wareHouse);
        contvs.get(8).setText(item.t.number + "");
    }
}
