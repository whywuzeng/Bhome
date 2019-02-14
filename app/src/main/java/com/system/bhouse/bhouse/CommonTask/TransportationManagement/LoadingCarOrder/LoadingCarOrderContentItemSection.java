package com.system.bhouse.bhouse.CommonTask.TransportationManagement.LoadingCarOrder;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.system.bhouse.base.StatusBean;
import com.system.bhouse.bean.LoadingCarBean;
import com.system.bhouse.bhouse.R;
import com.system.bhouse.ui.sectioned.Section;
import com.system.bhouse.utils.TenUtils.L;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
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
public class LoadingCarOrderContentItemSection extends Section {


    private static final int REFRESH_DATA_COMTASK = 8888;
    private static final String POSITION = "position";
    private static final String VIEWHOLDER = "viewholder";
    private static final String POSITION_ADAPTER = "position_adapter";



    private StatusBean statusBean;
    private String[] stringArray;


    public void setSearchHistroyBeans(ArrayList<LoadingCarBean> searchHistroyBeans) {
        this.searchHistroyBeans = searchHistroyBeans;
    }

    private ArrayList<LoadingCarBean> searchHistroyBeans = new ArrayList<>();


    public LoadingCarOrderContentItemSection(ArrayList<LoadingCarBean> searchHistroyBeans, StatusBean mStatusBean) {
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
            holder1.tvCount1.setText(searchHistroyBeans.get(position).materialsNumber);
            holder1.tvCount2.setText(searchHistroyBeans.get(position).materialsNames);
            holder1.tvCount.setText(searchHistroyBeans.get(position).Specification);

            holder1.tvUnit.setText(searchHistroyBeans.get(position).measureUnit);
            holder1.tvRequireNum.setText(searchHistroyBeans.get(position).amount+"");
            holder1.tvReceiptAmount.setText(searchHistroyBeans.get(position).projectName);
            holder1.tvProjectName.setText(searchHistroyBeans.get(position).dong);
            holder1.tvDong.setText(searchHistroyBeans.get(position).ceng);
            holder1.tvCengAmount.setText(searchHistroyBeans.get(position).sourceType);
            holder1.tvShifouproduce.setText(searchHistroyBeans.get(position).isIsProduce()?"是":"否");
            holder1.cb_produce_pos.setChecked(searchHistroyBeans.get(position).Is_OutPur);

            holder1.cb_produce_pos.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnCBItemClickListener!=null){
                        mOnCBItemClickListener.onItemClick(v,position);
                    }
                }
            });

            holder1.lll_bottom_1waibao.setVisibility(View.VISIBLE);

            holder1.lll_bottom_1.setVisibility(View.VISIBLE);
            holder1.vvv_bottom_1.setVisibility(View.VISIBLE);


            holder1.ll_top_1.setVisibility(View.GONE);
            holder1.vv_top_2.setVisibility(View.GONE);
//            holder1.ll_bottom_4.setVisibility(View.GONE);
//            holder1.vv_bottom_4.setVisibility(View.GONE);

//            holder1.tvWuliaoId,
//            holder1.tvReceiptAmountId, 项目名称前
            TextView[] titleContent = { holder1.tvWuliaoId, holder1.tvCountMeasureId, holder1.tvCountAmountId, holder1.tvCountId,holder1.tvUnitId,holder1.tvRequireNumId,holder1.tvReceiptAmountId,holder1.tvProjectNameId,holder1.tvDongId,holder1.tvCengAmountId,holder1.tvShifouprodeceId,holder1.tv_shifouproduce_idwaibao};
            int min = Math.min(this.stringArray.length, titleContent.length);
            for (int i = 0; i < min; i++) {
                titleContent[i].setText(this.stringArray[i]);
            }
            if (this.onItemClickListener != null) {
//                holder1.itemView.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//
//                        onItemClickListener.onItemClick(v, holder1.mianLayout, position);
//                    }
//                });
            }
//            holder1.imgDeleteItem.setOnClickListener(v -> {
//            });
            if (statusBean.isNewStatus() || statusBean.isModifyStatus()) {
                if (!searchHistroyBeans.get(0).isDisableDelete()) {
                    holder1.imgDeleteItem.setVisibility(View.VISIBLE);
                }else {
                    holder1.imgDeleteItem.setVisibility(View.INVISIBLE);
                }
                holder1.cb_produce_pos.setClickable(true);
                holder1.cb_produce_pos.setFocusable(true);
            }
            else if (statusBean.isLookStatus()) {
                holder1.imgDeleteItem.setVisibility(View.INVISIBLE);
                holder1.cb_produce_pos.setClickable(false);
                holder1.cb_produce_pos.setFocusable(false);
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
        @BindView(R.id.tv_wuliao)
        TextView tvWuliao;
        @BindView(R.id.WuliaoUnit)
        TextView WuliaoUnit;
        @BindView(R.id.tv_count)
        TextView tvCount;
        @BindView(R.id.mianLayout)
        LinearLayout mianLayout;
        @BindView(R.id.img_delete_item)
        ImageView imgDeleteItem;
        @BindView(R.id.tv_count_measure)
        TextView tvCount1;
        @BindView(R.id.tv_count_amount)
        TextView tvCount2;
        @BindView(R.id.iv_litte_grey_right_arrow)
        ImageView ivLitteGreyRightArrow;

        @BindView(R.id.tv_wuliao_id)
        TextView tvWuliaoId;
        @BindView(R.id.tv_WuliaoUnit_id)
        TextView tvWuliaoUnitId;
        @BindView(R.id.tv_count_measure_id)
        TextView tvCountMeasureId;
        @BindView(R.id.tv_count_amount_id)
        TextView tvCountAmountId;
        @BindView(R.id.tv_count_id)
        TextView tvCountId;


        @BindView(R.id.tv_unit_id)
        TextView tvUnitId;
        @BindView(R.id.tv_unit)
        TextView tvUnit;
        @BindView(R.id.tv_requireNum_id)
        TextView tvRequireNumId;
        @BindView(R.id.tv_requireNum)
        TextView tvRequireNum;
        @BindView(R.id.tv_ReceiptAmount_id)
        TextView tvReceiptAmountId;
        @BindView(R.id.tv_ReceiptAmount)
        TextView tvReceiptAmount;
        @BindView(R.id.tv_projectName_id)
        TextView tvProjectNameId;
        @BindView(R.id.tv_projectName)
        TextView tvProjectName;
        @BindView(R.id.tv_dong_id)
        TextView tvDongId;
        @BindView(R.id.tv_dong)
        TextView tvDong;
        @BindView(R.id.tv_cengAmount_id)
        TextView tvCengAmountId;
        @BindView(R.id.tv_cengAmount)
        TextView tvCengAmount;

        @BindView(R.id.tv_shifouproduce_id)
        TextView tvShifouprodeceId;
        @BindView(R.id.tv_shifouproduce)
        TextView tvShifouproduce;
        @BindView(R.id.lll_bottom_1)
        LinearLayout lll_bottom_1;
        @BindView(R.id.vvv_bottom_1)
        View  vvv_bottom_1;

        @BindView(R.id.ll_top_1)
        LinearLayout ll_top_1;
        @BindView(R.id.ll_bottom_4)
        LinearLayout ll_bottom_4;
        @BindView(R.id.vv_bottom_4)
        View vv_bottom_4;
        @BindView(R.id.vv_top_2)
        View vv_top_2;

        //外包checkbox
        @BindView(R.id.tv_shifouproduce_idwaibao)
        TextView tv_shifouproduce_idwaibao;
        @BindView(R.id.cb_produce_pos)
        CheckBox cb_produce_pos;
        @BindView(R.id.lll_bottom_1waibao)
        LinearLayout lll_bottom_1waibao;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void setmOnCBItemClickListener(onCBItemClickListener mOnCBItemClickListener) {
        this.mOnCBItemClickListener = mOnCBItemClickListener;
    }

    private onCBItemClickListener mOnCBItemClickListener;


    public interface onCBItemClickListener{
        void onItemClick(View view, int position);
    }

    public interface OnItemClickListener {
        void onItemClick(View view, View textView, int position);

        void onImgItemDelete(int position, int positionAdapter);

        void onImgItemAdd(View view, int position, RecyclerView.ViewHolder holder);
    }

    protected class FooterViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.btn_addItem)
        Button btnAddItem;

        FooterViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

}
