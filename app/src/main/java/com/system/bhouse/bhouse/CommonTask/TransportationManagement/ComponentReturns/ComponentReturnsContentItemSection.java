package com.system.bhouse.bhouse.CommonTask.TransportationManagement.ComponentReturns;

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
import com.system.bhouse.bean.ComponentReturnsBean;
import com.system.bhouse.bhouse.R;
import com.system.bhouse.ui.sectioned.Section;
import com.system.bhouse.utils.TenUtils.L;

import java.util.ArrayList;
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
public class ComponentReturnsContentItemSection extends Section {

    private static final int REFRESH_DATA_COMTASK = 8888;
    private static final String POSITION = "position";
    private static final String VIEWHOLDER = "viewholder";
    private static final String POSITION_ADAPTER = "position_adapter";

    private StatusBean statusBean;
    private String[] stringArray;


    public ArrayList<ComponentReturnsBean> getSearchHistroyBeans() {
        return searchHistroyBeans;
    }

    public ComponentReturnsBean getSearchHistroyBeans(int position) {
        ComponentReturnsBean componentReturnsBean = searchHistroyBeans.get(position);
        return componentReturnsBean;
    }

    private ArrayList<ComponentReturnsBean> searchHistroyBeans;


    public ComponentReturnsContentItemSection(ArrayList<ComponentReturnsBean> searchHistroyBeans, StatusBean mStatusBean) {
        super(R.layout.layout_home_recommend_empty_noheight, R.layout.comtask_content_item_footer, R.layout.activity_comtask_content_layout_item, R.layout.layout_home_recommend_empty, R.layout.layout_home_recommend_empty);
        this.searchHistroyBeans = searchHistroyBeans==null?new ArrayList<>():searchHistroyBeans;
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

            holder1.tvWuliao.setText(searchHistroyBeans.get(position).Qrcode);
            holder1.WuliaoUnit.setText(searchHistroyBeans.get(position).materialsNumber);
            holder1.tvCount1.setText(searchHistroyBeans.get(position).materialsNames);
            holder1.tvCount2.setText(searchHistroyBeans.get(position).Specification);
            holder1.tvCount.setText(searchHistroyBeans.get(position).getMeasureUnit());

            holder1.tvUnit.setText(searchHistroyBeans.get(position).qualityNum+"");
            holder1.tvRequireNum.setText(searchHistroyBeans.get(position).qualityType);
            holder1.tvReceiptAmount.setText(searchHistroyBeans.get(position).amount+"");
            holder1.tvProjectName.setText(searchHistroyBeans.get(position).wareHouseName);

            holder1.tvDong.setText(searchHistroyBeans.get(position).projectName);
            holder1.tvCengAmount.setText(searchHistroyBeans.get(position).dong);
            holder1.tvShifouproduce.setText(searchHistroyBeans.get(position).ceng);
            holder1.tvsubBeizhu.setText(searchHistroyBeans.get(position).subDirectoryBeizhu);

            holder1.lll_bottom_1.setVisibility(View.VISIBLE);
            holder1.vvv_bottom_1.setVisibility(View.VISIBLE);
            holder1.lll_bottom_2.setVisibility(View.VISIBLE);
            holder1.vvv_bottom_2.setVisibility(View.VISIBLE);


            holder1.llProjectName.setTag("1111");
            holder1.lll_bottom_2.setTag("2222");


//            holder1.ll_top_1.setVisibility(View.GONE);
//            holder1.vv_top_2.setVisibility(View.GONE);

//            holder1.ll_bottom_4.setVisibility(View.GONE);
//            holder1.vv_bottom_4.setVisibility(View.GONE);

//            holder1.tvWuliaoId,
//            holder1.tvReceiptAmountId, 项目名称前  tvWuliaoUnitId
            TextView[] titleContent = { holder1.tvWuliaoId,holder1.tvWuliaoUnitId, holder1.tvCountMeasureId, holder1.tvCountAmountId, holder1.tvCountId,holder1.tvUnitId,holder1.tvRequireNumId,holder1.tvReceiptAmountId,holder1.tvProjectNameId,holder1.tvDongId,holder1.tvCengAmountId,holder1.tvShifouprodeceId,holder1.tvsubBeizhuId};
            int min = Math.min(this.stringArray.length, titleContent.length);
            for (int i = 0; i < min; i++) {
                titleContent[i].setText(this.stringArray[i]);
            }
            if (this.onItemClickListener != null) {
//                holder1.itemView.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        if (position==8||position==12)
//                        onItemClickListener.onItemClick(v, holder1.mianLayout, position);
//                    }
//                });
                if (statusBean.isNewStatus() || statusBean.isModifyStatus()) {
                    holder1.llProjectName.setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View v) {
                            onItemClickListener.onItemClick(v, holder1.llProjectName, position);
                        }
                    });

                    holder1.lll_bottom_2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            onItemClickListener.onItemClick(v, holder1.lll_bottom_2, position);
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

    protected class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.tv_wuliao)
        TextView tvWuliao;
        @Bind(R.id.WuliaoUnit)
        TextView WuliaoUnit;
        @Bind(R.id.tv_count)
        TextView tvCount;
        @Bind(R.id.mianLayout)
        LinearLayout mianLayout;
        @Bind(R.id.img_delete_item)
        ImageView imgDeleteItem;
        @Bind(R.id.tv_count_measure)
        TextView tvCount1;
        @Bind(R.id.tv_count_amount)
        TextView tvCount2;
        @Bind(R.id.iv_litte_grey_right_arrow)
        ImageView ivLitteGreyRightArrow;

        @Bind(R.id.tv_wuliao_id)
        TextView tvWuliaoId;
        @Bind(R.id.tv_WuliaoUnit_id)
        TextView tvWuliaoUnitId;
        @Bind(R.id.tv_count_measure_id)
        TextView tvCountMeasureId;
        @Bind(R.id.tv_count_amount_id)
        TextView tvCountAmountId;
        @Bind(R.id.tv_count_id)
        TextView tvCountId;


        @Bind(R.id.tv_unit_id)
        TextView tvUnitId;
        @Bind(R.id.tv_unit)
        TextView tvUnit;
        @Bind(R.id.tv_requireNum_id)
        TextView tvRequireNumId;
        @Bind(R.id.tv_requireNum)
        TextView tvRequireNum;
        @Bind(R.id.tv_ReceiptAmount_id)
        TextView tvReceiptAmountId;
        @Bind(R.id.tv_ReceiptAmount)
        TextView tvReceiptAmount;
        @Bind(R.id.tv_projectName_id)
        TextView tvProjectNameId;
        @Bind(R.id.tv_projectName)
        TextView tvProjectName;
        @Bind(R.id.tv_dong_id)
        TextView tvDongId;
        @Bind(R.id.tv_dong)
        TextView tvDong;
        @Bind(R.id.tv_cengAmount_id)
        TextView tvCengAmountId;
        @Bind(R.id.tv_cengAmount)
        TextView tvCengAmount;

        @Bind(R.id.tv_shifouproduce_id)
        TextView tvShifouprodeceId;
        @Bind(R.id.tv_shifouproduce)
        TextView tvShifouproduce;
        @Bind(R.id.tv_subBeizhu_id)
        TextView tvsubBeizhuId;
        @Bind(R.id.tv_subBeizhu)
        TextView tvsubBeizhu;

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
         LinearLayout llProjectName;


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
