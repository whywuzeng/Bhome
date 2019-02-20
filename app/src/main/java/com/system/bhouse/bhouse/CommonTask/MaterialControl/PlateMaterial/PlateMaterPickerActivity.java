package com.system.bhouse.bhouse.CommonTask.MaterialControl.PlateMaterial;

import android.app.Activity;
import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.widget.LinearLayoutManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.system.bhouse.bhouse.CommonTask.MaterialControl.PlateMaterial.StationCarBean.MyStation;
import com.system.bhouse.bhouse.CommonTask.MaterialControl.PlateMaterial.StationCarBean.StationCarBean;
import net.qiujuer.italker.common.adapter.BaseQuickAdapter;
import net.qiujuer.italker.common.adapter.BaseSectionQuickAdapter;
import com.system.bhouse.bhouse.CommonTask.Widget.LRecyclerView;
import com.system.bhouse.bhouse.CommonTask.Widget.TimeLineItemTopBottomDecoration;
import com.system.bhouse.bhouse.R;
import com.system.bhouse.bhouse.setup.WWCommon.WWBackActivity;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Administrator on 2018-03-12.
 * <p>
 * by author wz
 * 显示层 栋的 Activity 界面  返回界面
 * <p>
 * com.system.bhouse.bhouse.CommonTask.common
 */
@EActivity(R.layout.fragment_complexnumpicker)
public class PlateMaterPickerActivity extends WWBackActivity implements AdapterView.OnItemSelectedListener {

    @ViewById(R.id.et_query)
    EditText etQuery;
    @ViewById(R.id.tv_Empty)
    TextView tvEmpty;
    @ViewById(R.id.spDwon)
    Spinner spDwon;
    @ViewById(R.id.tv_countNum)
    TextView tv_countNum;
    @ViewById(R.id.tv_button)
    TextView tv_button;
    private ArrayAdapter<String> stringArrayAdapter;
    private ArrayList<MyStation> mySections;
    private plateMaterialAdapter plateMaterialAdapter;

    @AfterViews
    public void initFragment() {
        setActionBarMidlleTitle(title);
        initData();
         plateMaterialAdapter = new plateMaterialAdapter(LayoutID, R.layout.layout_home_recommend_empty, mySections);
//        myAdapter = new MyAdapter();
        plateMaterialAdapter.setFriends(mySections);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        listView.addItemDecoration(new TimeLineItemTopBottomDecoration(),0);
        listView.setLayoutManager(linearLayoutManager);
        listView.setAdapter(plateMaterialAdapter);

        queryEdit();
    }

    private void initData() {
        mySections=new ArrayList<>();
        if (!bProBOMs.isEmpty())
        {
            for (StationCarBean bom:bProBOMs)
            {
                mySections.add(new MyStation(bom));
            }
        }

    }

    private void queryEdit() {

        ArrayList<String> list = new ArrayList<>();
        list.add("50");
        list.add("100");
        list.add("500");
        list.add("1000");
        list.add("全部");
        stringArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, list);
        stringArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spDwon.setAdapter(stringArrayAdapter);
        spDwon.setOnItemSelectedListener(this);

        //清除搜索内容
        tvEmpty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etQuery.getText().clear();
            }
        });

        etQuery.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //过滤操作
                plateMaterialAdapter.getFilter().filter(s);
                if (s.length() > 0) {
                    //清空按钮
                    tvEmpty.setEnabled(true);
                } else {
                    tvEmpty.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        plateMaterialAdapter.setmRefreshListener(new BaseSectionQuickAdapter.FilterRefreshListener<MyStation>() {
            @Override
            public String getIsFilter(String prefixString, MyStation value) {
                String username = value.t.getProducationOrderNumber();
                // First match against the whole ,non-splitted value，假如含有关键字的时候，添加
                username.contains(prefixString);
                return username;
            }

            @Override
            public void onAdapterRefresh() {
                plateMaterialAdapter.notifyDataSetChanged();
            }
        });

        plateMaterialAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId())
                {
                    case R.id.main_layout:
                        List<String> boms=new ArrayList<>();
                        boolean select = bProBOMs.get(position).isSelect();
                        bProBOMs.get(position).setSelect(!select);

                        int i = 0;
                        for (StationCarBean bom:bProBOMs)
                        {
                            if (bom.isSelect())
                            {
                                boms.add(String.format("%d\t.%s" , i, bom.getStationCarName()));
                                i++;

                            }
                        }
                        tv_countNum.setText(Arrays.toString((boms.toArray())));
                        i=0;
                        plateMaterialAdapter.notifyItemChanged(position);
                        break;
                }
            }
        });

        tv_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<StationCarBean> boms=new ArrayList<>();

                for (StationCarBean bom:bProBOMs)
                {
                    if (bom.isSelect())
                    {
                        boms.add(bom);
                    }
                }

                Intent data = new Intent();
//                data.putExtra("projectname", bProBOMs.get(position).getStationCarName());
                data.putParcelableArrayListExtra("coding", (ArrayList<? extends Parcelable>) boms);
                setResult(Activity.RESULT_OK, data);
                onBackPressed();
            }
        });
    }



    @ViewById
    LRecyclerView listView;

    @Extra
    String title;

    @Extra
    String extraPosition;

    @Extra
    ArrayList<StationCarBean> bProBOMs;

    @Extra
    String HId;

    @Extra
    Integer Position;

    @Extra
    Integer LayoutID;

    private int Count=50;

    //spDwon.setOnItemSelectedListener(this); 个数过滤回调
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position,
                               long id) {
        String cityName=stringArrayAdapter.getItem(position);   //获取选中的那一项
        try {
            //减少源数据的个数 然后再刷新。 clone()之后防止,ConcurrentModificationException sublist 之后不能清除clear

            Count = Integer.valueOf(cityName);
            int min = Math.min(Count, mySections.size());
            ArrayList<MyStation> clone = (ArrayList<MyStation>)this.mySections.clone();
            List<MyStation> mySections = clone.subList(0,min);
            this.mySections.clear();
            this.mySections.addAll(mySections);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            Count=9999;
        }
        plateMaterialAdapter.notifyDataSetChanged();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

//    class MyAdapter extends RecyclerView.Adapter<MyAdapter.ItemViewHolder> implements android.widget.Filterable {
//        private ArrayList<StationCarBean> copyBProBOMs= new ArrayList<>();
//
//        public void setFriends(ArrayList<StationCarBean> data) {
//            //复制数据
//            this.copyBProBOMs.addAll(data);
//            bProBOMs = data;
//            this.notifyDataSetChanged();
//        }
//
//        @Override
//        public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//            View inflate = getLayoutInflater().inflate(R.layout.commonpicker_item, null, false);
//            ItemViewHolder itemViewHolder = new ItemViewHolder(inflate);
//            return itemViewHolder;
//        }
//
//        @Override
//        public void onBindViewHolder(ItemViewHolder holder, int position) {
//            holder.tvTitle.setText(bProBOMs.get(position).getCoding() + "");
//            holder.tvContent.setText(bProBOMs.get(position).getProjectname());
//            holder.mainLayout.setOnClickListener(v -> {
//                Intent data = new Intent();
//                data.putExtra("projectname", bProBOMs.get(position).getProjectname());
//                data.putExtra("coding", bProBOMs.get(position).getCoding());
//                data.putExtra("HId", bProBOMs.get(position).getID());
//                data.putExtra("position", Position);
//                data.putExtra("BOMID",bProBOMs.get(position).getBOMID());
//                data.putExtra("title",title);
//                setResult(Activity.RESULT_OK, data);
//                onBackPressed();
//            });
//        }
//
//        @Override
//        public int getItemCount() {
//            if (Count < bProBOMs.size()) {
//                return Count;
//            }
//            else {
//                return bProBOMs == null ? 0 : bProBOMs.size();
//            }
//        }
//
//        @Override
//        public Filter getFilter() {
//            return new Filter() {
//                @Override
//                protected FilterResults performFiltering(CharSequence constraint) {
//                    //初始化过滤结果对象
//                    FilterResults results = new FilterResults();
//                    //假如搜索为空的时候，将复制的数据添加到原始数据，用于继续过滤操作
//                    if (results.values == null) {
//                        bProBOMs.clear();
//                        bProBOMs.addAll(copyBProBOMs);
//                    }
//                    //关键字为空的时候，搜索结果为复制的结果
//                    if (constraint == null || constraint.length() == 0) {
//                        results.values = copyBProBOMs;
//                        results.count = copyBProBOMs.size();
//                    }
//                    else {
//                        String prefixString = constraint.toString();
//                        final int count = bProBOMs.size();
//                        //用于存放暂时的过滤结果
//                        final ArrayList<StationCarBean> newValues = new ArrayList<StationCarBean>();
//                        for (int i = 0; i < count; i++) {
//                            final StationCarBean value = bProBOMs.get(i);
//                            String username = value.getCoding();
//                            // First match against the whole ,non-splitted value，假如含有关键字的时候，添加
//                            if (username.contains(prefixString)) {
//                                newValues.add(value);
//                            }
//                            else {
//                                //过来空字符开头
//                                final String[] words = username.split(" ");
//                                final int wordCount = words.length;
//
//                                // Start at index 0, in case valueText starts with space(s)
//                                for (int k = 0; k < wordCount; k++) {
//                                    if (words[k].contains(prefixString)) {
//                                        newValues.add(value);
//                                        break;
//                                    }
//                                }
//                            }
//                        }
//                        results.values = newValues;
//                        results.count = newValues.size();
//                    }
//                    return results;//过滤结果
//                }
//
//                @Override
//                protected void publishResults(CharSequence constraint, FilterResults results) {
//                    bProBOMs.clear();//清除原始数据
//                    bProBOMs.addAll((ArrayList<StationCarBean>) results.values);//将过滤结果添加到这个对象
//                    if (results.count > 0) {
//                        myAdapter.notifyDataSetChanged();//有关键字的时候刷新数据
//                    }
//                    else {
//                        //关键字不为零但是过滤结果为空刷新数据
//                        if (constraint.length() != 0) {
//                            myAdapter.notifyDataSetChanged();
//                            return;
//                        }
//                        //加载复制的数据，即为最初的数据
//                        myAdapter.setFriends(copyBProBOMs);
//                    }
//                }
//            };
//
//        }
//
//
//        class ItemViewHolder extends RecyclerView.ViewHolder {
//            @Bind(R.id.tv_title)
//            TextView tvTitle;
//            @Bind(R.id.tv_content)
//            TextView tvContent;
//            @Bind(R.id.main_layout)
//            RelativeLayout mainLayout;
//
//            ItemViewHolder(View view) {
//                super(view);
//                ButterKnife.bind(this, view);
//            }
//        }
//    }
}
