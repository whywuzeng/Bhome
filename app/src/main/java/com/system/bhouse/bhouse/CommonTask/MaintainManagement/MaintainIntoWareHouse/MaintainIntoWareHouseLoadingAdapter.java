package com.system.bhouse.bhouse.CommonTask.MaintainManagement.MaintainIntoWareHouse;

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
import com.system.bhouse.bhouse.CommonTask.TechnologyExecution.MaintenanceIntoWarehouse.Bean.MaintenanceWarehouseBean;
import com.system.bhouse.bhouse.CommonTask.Widget.LoadingAdapter;
import com.system.bhouse.bhouse.R;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018-03-19.
 * <p>
 * by author wz
 * <p>
 * com.system.bhouse.bhouse.ConfirmationReceipt.adapter
 */

public class MaintainIntoWareHouseLoadingAdapter extends LoadingAdapter<MaintenanceWarehouseBean> {

    private final String[] TitlestringArray;
    private ArrayList<MaintenanceWarehouseBean> mComTaskBeans = new ArrayList<>();

    public MaintainIntoWareHouseLoadingAdapter(ArrayList<MaintenanceWarehouseBean> mComTaskBeans) {
        this.mComTaskBeans = mComTaskBeans;
        TitlestringArray = App.getContextApp().getResources().getStringArray(R.array.maintaininto_warehouse);
    }

    @Override
    public RecyclerView.ViewHolder onCreateHolder(ViewGroup parent, int viewType) {
        return new ItemViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_task_list_timeitem, parent, false));
    }


    @Override
    protected void onBindHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ItemViewHolder) {
            ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
            initViewForData(itemViewHolder);
            //给content设置Data数据
            List<TextView> tagContent = setContentstag(itemViewHolder);
            List<String> dataList = getListStrings(position,tagContent);
            //设置 显示的Item和数据
            setCountViewShow(itemViewHolder,TitlestringArray.length);

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

    private void initViewForData(ItemViewHolder itemViewHolder) {
        //让所有的都隐藏
        List<View> views = setLLTagContent(itemViewHolder);
        for (View llid : views)
        {
            llid.setVisibility(View.GONE);
        }
        //给Item 设置title数据
        List<TextView> titlesTag = setTitlesTag(itemViewHolder);
        for (int i=0;i<TitlestringArray.length;i++)
        {
            titlesTag.get(i).setText(TitlestringArray[i]);
        }
//        titlesTag.get(0).setText("生产订单号");
//        titlesTag.get(1).setText("领料单编号");
//        titlesTag.get(2).setText("状态");
//        titlesTag.get(3).setText("录入时间");
//        titlesTag.get(4).setText("录入人");
//        titlesTag.get(5).setText("业务日期");

    }

    private void setCountViewShow(ItemViewHolder itemViewHolder,int countItem){
        List<View> views = setLLTagContent(itemViewHolder);
        for (int i=0;i<countItem;i++)
        {
            views.get(i).setVisibility(View.VISIBLE);
        }
    }

    /**
     * 1--->ll_text_tableId_1 -- text_tableId -- text_tableId_content
     *  2--->ll_text_projectName_2 -- text_projectName -- text_projectName_content
     *   3--->ll_text_dong_3 -- text_dong -- text_dong_content
     *    4--->ll_bottom_6 -- text_ceng -- text_ceng_content
     *     5--->ll_bottom_5 -- text_reqiuerDate -- text_reqiuerDate_content
     *      6--->ll_bottom_4 -- text_description -- text_description_content
     *       7--->ll_bottom_3 -- text_status -- text_status_content
     *        8--->ll_bottom_2 -- text_entryPeople -- text_entryPeople_content
     *         9--->ll_bottom_1 -- text_entryTime -- text_entryTime_content
     *         //只有9个Item
     */

    /**
     *  整体布局的隐藏集合
     */
    private List<View> setLLTagContent(ItemViewHolder itemViewHolder){
        List<View> tagres=new ArrayList<>();
        tagres.add(itemViewHolder.ll_text_tableId_1);
        tagres.add(itemViewHolder.ll_text_projectName_2);
        tagres.add(itemViewHolder.ll_text_dong_3);
        tagres.add(itemViewHolder.ll_bottom_6);
        tagres.add(itemViewHolder.ll_bottom_5);
        tagres.add(itemViewHolder.ll_bottom_4);
        tagres.add(itemViewHolder.llBottom3);
        tagres.add(itemViewHolder.llBottom2);
        tagres.add(itemViewHolder.llBottom1);
        return tagres;
    }
    /**
     *  整体TitleTag的 控件集合
     */
    private List<TextView> setTitlesTag(ItemViewHolder itemViewHolder){
        List<TextView> tagres=new ArrayList<>();
        tagres.add(itemViewHolder.textTableId);
        tagres.add(itemViewHolder.textProjectName);
        tagres.add(itemViewHolder.textDong);
        tagres.add(itemViewHolder.textCeng);
        tagres.add(itemViewHolder.textReqiuerDate);
        tagres.add(itemViewHolder.textDescription);
        tagres.add(itemViewHolder.textStatus);
        tagres.add(itemViewHolder.textEntryPeople);
        tagres.add(itemViewHolder.textEntryTime);
        return tagres;
    }

    /**
     * 获取内容的 控件资源集合
     */
    private List<TextView> setContentstag(ItemViewHolder itemViewHolder){
        List<TextView> tagres=new ArrayList<>();
        tagres.add(itemViewHolder.textTableIdContent);
        tagres.add(itemViewHolder.textProjectNameContent);
        tagres.add(itemViewHolder.textDongContent);
        tagres.add(itemViewHolder.textCengContent);
        tagres.add(itemViewHolder.textReqiuerDateContent);
        tagres.add(itemViewHolder.textDescriptionContent);
        tagres.add(itemViewHolder.textStatusContent);
        tagres.add(itemViewHolder.textEntryPeopleContent);
        tagres.add(itemViewHolder.textEntryTimeContent);
        return tagres;
    }

    @NonNull
    private List<String> getListStrings(int position, List<TextView> tagContent) {
        List<String> dataList = new ArrayList<>();
        tagContent.get(0).setText(mComTaskBeans
                .get(position).gethNumbe() == null ? "" : mComTaskBeans
                .get(position).gethNumbe());

        tagContent.get(1).setText(mComTaskBeans
                .get(position).wareHouse == null ? "" : mComTaskBeans
                .get(position).wareHouse);

        tagContent.get(2).setText(mComTaskBeans
                .get(position).stationCarName == null ? "" : mComTaskBeans
                .get(position).stationCarName);


        tagContent.get(3).setText(mComTaskBeans
                .get(position).getEntryPeople() == null ? "" : mComTaskBeans
                .get(position).getEntryPeople());

        tagContent.get(4).setText(mComTaskBeans
                .get(position).getEntryTime() == null ? "" : mComTaskBeans
                .get(position).getEntryTime());


//        tagContent.get(position).setText(mComTaskBeans
//                .get(position).getEntryTime() == null ? "" : mComTaskBeans
//                .get(position).getEntryTime());

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
        @BindView(R.id.timeClock)
        ImageView timeClock;
        @BindView(R.id.tvMonthDay)
        TextView tvMonthDay;
        @BindView(R.id.tvWeek)
        TextView tvWeek;
        @BindView(R.id.text_tableId)
        TextView textTableId;
        @BindView(R.id.text_tableId_content)
        TextView textTableIdContent;
        @BindView(R.id.text_projectName)
        TextView textProjectName;
        @BindView(R.id.text_projectName_content)
        TextView textProjectNameContent;
        @BindView(R.id.text_dong)
        TextView textDong;
        @BindView(R.id.text_dong_content)
        TextView textDongContent;
        @BindView(R.id.text_ceng)
        TextView textCeng;
        @BindView(R.id.text_ceng_content)
        TextView textCengContent;
        @BindView(R.id.text_reqiuerDate)
        TextView textReqiuerDate;
        @BindView(R.id.text_reqiuerDate_content)
        TextView textReqiuerDateContent;
        @BindView(R.id.text_description)
        TextView textDescription;
        @BindView(R.id.text_description_content)
        TextView textDescriptionContent;
        @BindView(R.id.text_status)
        TextView textStatus;
        @BindView(R.id.text_status_content)
        TextView textStatusContent;
        @BindView(R.id.text_entryPeople)
        TextView textEntryPeople;
        @BindView(R.id.text_entryPeople_content)
        TextView textEntryPeopleContent;
        @BindView(R.id.text_entryTime)
        TextView textEntryTime;
        @BindView(R.id.text_entryTime_content)
        TextView textEntryTimeContent;
        @BindView(R.id.main_layout)
        LinearLayout mainLayout;
        @BindView(R.id.ll_bottom_3)
        LinearLayout llBottom3;
        @BindView(R.id.ll_bottom_2)
        LinearLayout llBottom2;
        @BindView(R.id.ll_bottom_1)
        LinearLayout llBottom1;
        @BindView(R.id.ll_text_tableId_1)
        LinearLayout ll_text_tableId_1;
        @BindView(R.id.ll_text_projectName_2)
        LinearLayout ll_text_projectName_2;
        @BindView(R.id.ll_text_dong_3)
        LinearLayout ll_text_dong_3;
        @BindView(R.id.ll_bottom_6)
        LinearLayout ll_bottom_6;
        @BindView(R.id.ll_bottom_5)
        LinearLayout ll_bottom_5;
        @BindView(R.id.ll_bottom_4)
        LinearLayout ll_bottom_4;


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


