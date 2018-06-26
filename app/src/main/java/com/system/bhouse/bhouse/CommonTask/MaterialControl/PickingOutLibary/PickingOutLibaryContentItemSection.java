package com.system.bhouse.bhouse.CommonTask.MaterialControl.PickingOutLibary;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.system.bhouse.base.StatusBean;
import com.system.bhouse.bhouse.CommonTask.MaterialControl.entity.PickingOutBean;
import com.system.bhouse.bhouse.R;
import com.system.bhouse.ui.sectioned.Section;
import com.system.bhouse.utils.TenUtils.L;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Observable;

/**
 * Created by Administrator on 2017-03-20.
 * ClassName: com.system.bhouse.bhouse.Workflowlist.adapter.section
 * Author:Administrator
 * Fuction:
 * UpdateUser:
 * UpdateDate:
 */
public class PickingOutLibaryContentItemSection extends Section {

    private static final int REFRESH_DATA_COMTASK = 8888;
    private static final String POSITION = "position";
    private static final String VIEWHOLDER = "viewholder";
    private static final String POSITION_ADAPTER = "position_adapter";

    private StatusBean statusBean;
    private String[] stringArray;


    public ArrayList<PickingOutBean> getSearchHistroyBeans() {
        return searchHistroyBeans;
    }

    public PickingOutBean getSearchHistroyBeans(int position) {
        PickingOutBean PickingOutBean = searchHistroyBeans.get(position);
        return PickingOutBean;
    }

    private ArrayList<PickingOutBean> searchHistroyBeans;


    public PickingOutLibaryContentItemSection(ArrayList<PickingOutBean> searchHistroyBeans, StatusBean mStatusBean) {
        super(R.layout.layout_home_recommend_empty_noheight, R.layout.comtask_content_item_footer, R.layout.activity_comtask_content_layout_item, R.layout.layout_home_recommend_empty, R.layout.layout_home_recommend_empty);
        this.searchHistroyBeans = searchHistroyBeans;
        this.statusBean = mStatusBean;
    }

    public void setTVIDContent(String[] stringArray) {
        this.stringArray = stringArray;
    }

    @Override
    public int getContentItemsTotal() {
        return searchHistroyBeans.size();
    }

    @Override
    public RecyclerView.ViewHolder getItemViewHolder(View view) {
        ViewHolder workflowViewHolder = new ViewHolder(view);
        return workflowViewHolder;
    }

    @Override
    public void onBindItemViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof ViewHolder) {
            final ViewHolder holder1 = (ViewHolder) holder;

            initDataForView(holder1,position);

            if (this.onItemClickListener != null) {
//                holder1.itemView.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        if (position==8||position==12)
//                        onItemClickListener.onItemClick(v, holder1.mianLayout, position);
//                    }
//                });
                if (statusBean.isNewStatus() || statusBean.isModifyStatus()) {
                    holder1.tv_count.setTag("1111");
                    holder1.tv_count.setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View v) {
                            onItemClickListener.onItemClick(v, holder1.tv_count, position);
                        }
                    });
                }
            }
//            holder1.imgDeleteItem.setOnClickListener(v -> {
//            });
            if (statusBean.isNewStatus() || statusBean.isModifyStatus()) {
                if (!searchHistroyBeans.get(0).isDisableDelete()) {
                    holder1.imgDeleteItem.setVisibility(View.VISIBLE);
                    holder1.ivprojectname.setVisibility(View.VISIBLE);
                    holder1.ivsubBeizhu.setVisibility(View.VISIBLE);
                }else {
                    holder1.imgDeleteItem.setVisibility(View.INVISIBLE);
                    holder1.ivprojectname.setVisibility(View.INVISIBLE);
                    holder1.ivsubBeizhu.setVisibility(View.INVISIBLE);
                }
            }
            else if (statusBean.isLookStatus()) {
                holder1.imgDeleteItem.setVisibility(View.INVISIBLE);
                holder1.ivprojectname.setVisibility(View.INVISIBLE);
                holder1.ivsubBeizhu.setVisibility(View.INVISIBLE);
            }

            Observable.create(subscriber -> {
                holder1.imgDeleteItem.setOnClickListener(v -> {
                    subscriber.onNext(v);
                });

            }).debounce(500, TimeUnit.MILLISECONDS).subscribe(V -> {
                L.e("double click");
                Message message = Message.obtain();
                Bundle bundle = new Bundle();
                bundle.putInt(POSITION, position);
                bundle.putInt(POSITION_ADAPTER, holder1.getAdapterPosition());
                message.setData(bundle);
                message.what = REFRESH_DATA_COMTASK;
                handler.sendMessage(message);
            });

        }
    }

    /**
     * 设置控件的固定值  和 获取值 和 是否该显示
     * @param holder1
     * @param position
     */
    private void initDataForView(ViewHolder holder1, int position) {
        //1.让所有的 条目 不可见
        List<LinearLayout> lls=new ArrayList<>();
        lls.add(holder1.ll_top1_deleteIv);
        lls.add(holder1.ll_top_1);
        lls.add(holder1.ll_top2);
        lls.add(holder1.ll_top3);
        lls.add(holder1.ll_top4);
        lls.add(holder1.ll_top5);
        lls.add(holder1.ll_top6);
        lls.add(holder1.ll_bottom_4);
        lls.add(holder1.ll_projectName);
        lls.add(holder1.ll_ll_view_4);
        lls.add(holder1.ll_linearlayout_4);
        lls.add(holder1.lll_bottom_1);
        lls.add(holder1.lll_bottom_2);

        List<View> VVs=new ArrayList<>();
        VVs.add(holder1.vv_top1_deleteIv);
        VVs.add(holder1.vv_top_2);
        VVs.add(holder1.vv_top_22);
        VVs.add(holder1.vv_top_3);
        VVs.add(holder1.vv_top_4);
        VVs.add(holder1.vv_top_5);
        VVs.add(holder1.vv_top_6);
        VVs.add(holder1.vv_bottom_4);
        VVs.add(holder1.vv_projectName);
        VVs.add(holder1.ll_view_4);
        VVs.add(holder1.vvv_bottom_1);
        VVs.add(holder1.vvv_bottom_2);
        VVs.add(holder1.vvvv_bottom_2);

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
        tvs.add(holder1.tv_wuliao_id);
        tvs.add(holder1.tv_WuliaoUnit_id);
        tvs.add(holder1.tv_count_measure_id);
        tvs.add(holder1.tv_count_amount_id);
        tvs.add(holder1.tv_count_id);
        tvs.add(holder1.tv_unit_id);
        tvs.add(holder1.tv_requireNum_id);
        tvs.add(holder1.tv_ReceiptAmount_id);
        tvs.add(holder1.tv_projectName_id);
        tvs.add(holder1.tv_dong_id);
        tvs.add(holder1.tv_cengAmount_id);
        tvs.add(holder1.tv_shifouproduce_id);
        tvs.add(holder1.tv_subBeizhu_id);

        for (int i=0;i<stringArray.length;i++)
        {
            if (stringArray!=null&&stringArray.length>0)
            tvs.get(i).setText(stringArray[i]);
        }

        //3.给 content 设置 获取值
        List<TextView> contvs=new ArrayList<>();
        contvs.add(holder1.tv_wuliao);
        contvs.add(holder1.WuliaoUnit);
        contvs.add(holder1.tv_count_measure);
        contvs.add(holder1.tv_count_amount);
        contvs.add(holder1.tv_count);
        contvs.add(holder1.tv_unit);
        contvs.add(holder1.tv_requireNum);
        contvs.add(holder1.tv_ReceiptAmount);
        contvs.add(holder1.tv_projectName);
        contvs.add(holder1.tv_dong);
        contvs.add(holder1.tv_cengAmount);
        contvs.add(holder1.tv_shifouproduce);
        contvs.add(holder1.tv_subBeizhu);

        contvs.get(0).setText(searchHistroyBeans.get(position).materialsNumber);
        contvs.get(1).setText(searchHistroyBeans.get(position).materialsNames);
        contvs.get(2).setText(searchHistroyBeans.get(position).Specification);
        contvs.get(3).setText(searchHistroyBeans.get(position).getMeasureUnit());
        contvs.get(4).setText(searchHistroyBeans.get(position).getWareHouseName());
        contvs.get(5).setText(searchHistroyBeans.get(position).measureUnit+"");

        //4.显示 业务个数的 条目

        for (int i=0;i<stringArray.length;i++)
        {
            lls.get(i).setVisibility(View.VISIBLE);
            VVs.get(i).setVisibility(View.VISIBLE);
            holder1.vvvv_bottom_2.setVisibility(View.VISIBLE);
        }
    }

    protected Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == REFRESH_DATA_COMTASK) {
                int anInt = msg.getData().getInt(POSITION);
                int positionAdapter = msg.getData().getInt(POSITION_ADAPTER);
                onItemClickListener.onImgItemDelete(anInt, positionAdapter);
            }
        }
    };

    @Override
    public void onBindFooterViewHolder(RecyclerView.ViewHolder holder) {
        super.onBindFooterViewHolder(holder);
        if (holder instanceof FooterViewHolder) {
            FooterViewHolder holder1 = (FooterViewHolder) holder;
//            if (!IsNew)
            holder1.btnAddItem.setVisibility(View.INVISIBLE);

            holder1.btnAddItem.setOnClickListener(v -> {
                onItemClickListener.onImgItemAdd(v, 0, holder);
            });
        }
    }

    @Override
    public RecyclerView.ViewHolder getFooterViewHolder(View view) {
        FooterViewHolder footerViewHolder = new FooterViewHolder(view);
        return footerViewHolder;
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
     *
     */

    protected class ViewHolder extends RecyclerView.ViewHolder {
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
        View  vvv_bottom_1;

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








        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, View textView, int position);

        void onImgItemDelete(int position, int positionAdapter);

        void onImgItemAdd(View view, int position, RecyclerView.ViewHolder holder);
    }

    protected class FooterViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.btn_addItem)
        Button btnAddItem;

        FooterViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

}
