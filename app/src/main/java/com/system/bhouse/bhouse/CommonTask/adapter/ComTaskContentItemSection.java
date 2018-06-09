package com.system.bhouse.bhouse.CommonTask.adapter;

import android.os.Bundle;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.system.bhouse.base.StatusBean;
import com.system.bhouse.bean.ComTaskBean;
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
public class ComTaskContentItemSection extends Section {


    private static final int REFRESH_DATA_COMTASK = 8888;
    private static final String POSITION = "position";
    private static final String VIEWHOLDER = "viewholder";
    private static final String POSITION_ADAPTER = "position_adapter";
    private StatusBean mStatus;


    public void setSearchHistroyBeans(ArrayList<ComTaskBean> searchHistroyBeans) {
        this.searchHistroyBeans = searchHistroyBeans;
    }

    private ArrayList<ComTaskBean> searchHistroyBeans = new ArrayList<>();


    public ComTaskContentItemSection(ArrayList<ComTaskBean> searchHistroyBeans, StatusBean mStatus) {
        super(R.layout.layout_home_recommend_empty_noheight, R.layout.comtask_content_item_footer, R.layout.activity_comtask5item_content_layout_item, R.layout.layout_home_recommend_empty, R.layout.layout_home_recommend_empty);
        this.searchHistroyBeans = searchHistroyBeans;
        this.mStatus=mStatus;
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
            holder1.tvWuliao.setText(searchHistroyBeans.get(position).goodsCoding);
            holder1.WuliaoUnit.setText(searchHistroyBeans.get(position).goodsName);
            holder1.tvCount.setText(searchHistroyBeans.get(position).amount+"");
            holder1.tvCount1.setText(searchHistroyBeans.get(position).Specification);
            holder1.tvCount2.setText(searchHistroyBeans.get(position).measure);


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
//            if (!IsNew)
                holder1.imgDeleteItem.setVisibility(View.INVISIBLE);

            Observable.create(subscriber -> {
                holder1.imgDeleteItem.setOnClickListener(v ->{subscriber.onNext(v);

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


    protected android.os.Handler handler=new android.os.Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what==REFRESH_DATA_COMTASK)
            {
                int anInt = msg.getData().getInt(POSITION);
                int positionAdapter = msg.getData().getInt(POSITION_ADAPTER);
                onItemClickListener.onImgItemDelete(anInt,positionAdapter);
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

        void onImgItemDelete( int position,int positionAdapter);

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
