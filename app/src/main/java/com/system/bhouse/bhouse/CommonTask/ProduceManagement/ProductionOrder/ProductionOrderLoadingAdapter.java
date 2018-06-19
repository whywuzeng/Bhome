package com.system.bhouse.bhouse.CommonTask.ProduceManagement.ProductionOrder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.system.bhouse.base.App;
import com.system.bhouse.bhouse.CommonTask.ProduceManagement.entity.productionOrderBean;
import com.system.bhouse.bhouse.CommonTask.Widget.LoadingAdapter;
import com.system.bhouse.bhouse.R;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018-03-19.
 * <p>
 * by author wz
 * <p>
 * com.system.bhouse.bhouse.ConfirmationReceipt.adapter
 */

public class ProductionOrderLoadingAdapter extends LoadingAdapter<productionOrderBean> {

    private ArrayList<productionOrderBean> mComTaskBeans = new ArrayList<>();

    public ProductionOrderLoadingAdapter(ArrayList<productionOrderBean> mComTaskBeans) {
        this.mComTaskBeans = mComTaskBeans;
    }

    @Override
    public RecyclerView.ViewHolder onCreateHolder(ViewGroup parent, int viewType) {
        return new ItemViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_task_list_timeitem, parent, false));

    }


    @Override
    protected void onBindHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ItemViewHolder) {
            ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
            List<String> dataList = getListStrings(position);

            TextView[] tvViews = {itemViewHolder.textTableId, itemViewHolder.textTableIdContent, itemViewHolder.textProjectName, itemViewHolder.textProjectNameContent, itemViewHolder.textDong, itemViewHolder.textDongContent, itemViewHolder.textCeng, itemViewHolder.textCengContent, itemViewHolder.textReqiuerDate, itemViewHolder.textReqiuerDateContent, itemViewHolder.textDescription, itemViewHolder.textDescriptionContent, itemViewHolder.textStatus, itemViewHolder.textStatusContent, itemViewHolder.textEntryPeople, itemViewHolder.textEntryPeopleContent};

            itemViewHolder.llBottom1.setVisibility(View.GONE);
            itemViewHolder.llBottom2.setVisibility(View.GONE);
            itemViewHolder.llBottom3.setVisibility(View.GONE);

            for (int i = 0; i < dataList.size(); i++) {
                tvViews[i].setText(dataList.get(i));
            }


            itemViewHolder.mainLayout.setOnClickListener(v -> {
                mOnItemClickListener.ItemClick(itemViewHolder, position);
//                mNeedUpdate = true;
//                ComTaskContentMessageActivity_.intent(getParentFragment()).HId(mComTaskBeans
// .get(position).getID() + "").IsNew(false).start();
            });

            String entryTime = mComTaskBeans
                    .get(position).getEntryTime();
            Date parse = null;
            String format = "";
            if (!TextUtils.isEmpty(entryTime)) {
                try {
                    parse = App.sFormatThisYearSlashSECOND.parse(entryTime);
                    format = App.sFormatThisMonth.format(parse);
                } catch (ParseException e) {
                    e.printStackTrace();
                } catch (NullPointerException e1) {
                    e1.printStackTrace();
                }
            }
            Calendar calendar = Calendar.getInstance();
            if (parse != null) {
                itemViewHolder.tvMonthDay.setText(format);  //设置 多个月和日
                calendar.setTime(parse);
                int i = calendar.get(Calendar.DAY_OF_WEEK);
                itemViewHolder.tvWeek.setText(setWeek(i));
            }
        }
    }

    @NonNull
    private List<String> getListStrings(int position) {
        List<String> dataList = new ArrayList<>();
        dataList.add("单据编号:");
        dataList.add(mComTaskBeans
                .get(position).getHNumbe() == null ? "" : mComTaskBeans
                .get(position).getHNumbe());
        dataList.add("业务日期:");
        dataList.add(mComTaskBeans
                .get(position).getRequireDate() == null ? "" : mComTaskBeans
                .get(position).getRequireDate());
        dataList.add("备注:");
        dataList.add(mComTaskBeans
                .get(position).getDescription() == null ? "" : mComTaskBeans
                .get(position).getDescription());
        dataList.add("状态:");
        dataList.add(mComTaskBeans
                .get(position).getStatus() == null ? "" : mComTaskBeans
                .get(position).getStatus());
//        dataList.add("状态:");
//        dataList.add(mComTaskBeans
//                .get(position).getStatus() == null ? "" : mComTaskBeans
//                .get(position).getStatus());
        dataList.add("录入人:");
        dataList.add(mComTaskBeans
                .get(position).getEntryPeople() == null ? "" : mComTaskBeans
                .get(position).getEntryPeople());
        dataList.add("录入时间:");
        dataList.add(mComTaskBeans
                .get(position).getEntryTime() == null ? "" : mComTaskBeans
                .get(position).getEntryTime());
        return dataList;
    }


    private String setWeek(int week) {
        String weekStr = "";
        switch (week) {
            case 1:
                weekStr = "周日";
                break;
            case 2:
                weekStr = "周一";
                break;
            case 3:
                weekStr = "周二";
                break;
            case 4:
                weekStr = "周三";
                break;
            case 5:
                weekStr = "周四";
                break;
            case 6:
                weekStr = "周五";
                break;
            case 7:
                weekStr = "周六";
                break;
            default:
                break;
        }
        return weekStr;
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.timeClock)
        ImageView timeClock;
        @Bind(R.id.tvMonthDay)
        TextView tvMonthDay;
        @Bind(R.id.tvWeek)
        TextView tvWeek;
        @Bind(R.id.text_tableId)
        TextView textTableId;
        @Bind(R.id.text_tableId_content)
        TextView textTableIdContent;
        @Bind(R.id.text_projectName)
        TextView textProjectName;
        @Bind(R.id.text_projectName_content)
        TextView textProjectNameContent;
        @Bind(R.id.text_dong)
        TextView textDong;
        @Bind(R.id.text_dong_content)
        TextView textDongContent;
        @Bind(R.id.text_ceng)
        TextView textCeng;
        @Bind(R.id.text_ceng_content)
        TextView textCengContent;
        @Bind(R.id.text_reqiuerDate)
        TextView textReqiuerDate;
        @Bind(R.id.text_reqiuerDate_content)
        TextView textReqiuerDateContent;
        @Bind(R.id.text_description)
        TextView textDescription;
        @Bind(R.id.text_description_content)
        TextView textDescriptionContent;
        @Bind(R.id.text_status)
        TextView textStatus;
        @Bind(R.id.text_status_content)
        TextView textStatusContent;
        @Bind(R.id.text_entryPeople)
        TextView textEntryPeople;
        @Bind(R.id.text_entryPeople_content)
        TextView textEntryPeopleContent;
        @Bind(R.id.text_entryTime)
        TextView textEntryTime;
        @Bind(R.id.text_entryTime_content)
        TextView textEntryTimeContent;
        @Bind(R.id.main_layout)
        LinearLayout mainLayout;
        @Bind(R.id.ll_bottom_3)
        LinearLayout llBottom3;
        @Bind(R.id.ll_bottom_2)
        LinearLayout llBottom2;
        @Bind(R.id.ll_bottom_1)
        LinearLayout llBottom1;

        ItemViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public void setmOnItemClickListener(onItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    private onItemClickListener mOnItemClickListener;


    public interface onItemClickListener {
        void ItemClick(ItemViewHolder holder, int position);
    }

}


