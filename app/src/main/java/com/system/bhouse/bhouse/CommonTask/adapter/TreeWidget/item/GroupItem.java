package com.system.bhouse.bhouse.CommonTask.adapter.TreeWidget.item;

import com.system.bhouse.bhouse.CommonTask.adapter.TreeWidget.base.ViewHolder;
import com.system.bhouse.bhouse.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018-03-08.
 * <p>
 * by author wz
 * <p>
 * com.system.bhouse.bhouse.CommonTask.adapter.TreeWidget.item
 */

public class GroupItem extends TreeItemGroup<ArrayList<SortChildItem.ViewModel>> implements SortChildItem.OnItemClickListener {

    public String TitleKey;

    @Override
    protected int initLayoutId() {

        return R.layout.recycleview_comtask_top_item_groupitem;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder) {
        viewHolder.setText(R.id.tv_task_done1,TitleKey);
    }

    @Override
    protected List<TreeItem> initChildList(ArrayList<SortChildItem.ViewModel> viewModels) {

        ArrayList<TreeItem> treeItems = new ArrayList<>();
        SortChildItem sortChildItem;

        for (int i = 0; i < viewModels.size(); i++) {
            sortChildItem = new SortChildItem();
            sortChildItem.setData(viewModels.get(i));
            if (viewModels.get(i).isClick) {
                sortChildItem.setmOnItemClickListener(this);
            }
            treeItems.add(sortChildItem);
        }
        return treeItems;
    }

    //把最小单位的 ITEM点击事件传出去
    @Override
    public void onItemClick(SortChildItem.ViewModel data, ViewHolder holder) {
        if (mOnChildItemClickListener!=null)
        {
            mOnChildItemClickListener.onChildItemClick(data,holder);
        }
    }

    public void setmOnChildItemClickListener(onChildItemClickListener mOnChildItemClickListener) {
        this.mOnChildItemClickListener = mOnChildItemClickListener;
    }

    private onChildItemClickListener mOnChildItemClickListener;

    public interface onChildItemClickListener
    {
        void onChildItemClick(SortChildItem.ViewModel data, ViewHolder holder);
    }
}


/**
 * //    ID = "1ea66b7bb9674a18a8794bd943c212bb"
 //    Specification = "测试" 规格型号
 //    amount = 30            需求数量
 //    ceng = "2"             层
 //    childTableID = "599d49d6ad2c4eabb43c7314cd68a467"
 //    description = ""
 //    dong = "1"
 //    enterPeople = "管理员"
 //    entryTime = "2018/3/8 11:12:00"
 //    goodsCoding = "1002.1084.0100.003"    物料编码
 //    goodsID = "acb6fd62b2f0405292fe8c0de0737f2f"
 //    goodsName = "PC构件"                  物料名称
 //    hNumbe = "DZXQ-7-201803-0001"         单据编号
 //    measure = "块"
 //    measureID = "c8e082b5f5f34d5f934f071e6b464238"
 //    projectName = "麓谷一期项目"
 //    requireData = "2018/3/8 11:11:40"
 //    status = "审核"
 public String ID;
 @SerializedName("订单编号")
 public String hNumbe;
 @SerializedName("项目名称")
 public String projectName;
 @SerializedName("栋")
 public String dong;
 @SerializedName("层")
 public String ceng;
 @SerializedName("需求日期")
 public String requireData;
 @SerializedName("描述")
 public String description;
 @SerializedName("状态")
 public String status;
 @SerializedName("录入人")
 public String enterPeople;
 @SerializedName("录入时间")
 public String entryTime;
 @SerializedName("分录ID")
 public String childTableID;
 @SerializedName("物料ID")
 public String goodsID;
 @SerializedName("物料编码")
 public String goodsCoding;
 @SerializedName("物料名称")
 public String goodsName;
 @SerializedName("规格型号")
 public String Specification;
 @SerializedName("计量单位ID")
 public String measureID;
 @SerializedName("计量单位")
 public String measure;
 @SerializedName("数量")
 public int amount;
 */