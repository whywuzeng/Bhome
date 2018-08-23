package com.system.bhouse.bhouse.CommonTask.common;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.system.bhouse.bean.BProBOM;
import com.system.bhouse.bhouse.CommonTask.MaintainManagement.LoadingIntoWareHouse.adpter.LoadingIntoSectionAdapter;
import com.system.bhouse.bhouse.CommonTask.TransportationManagement.LoadingCarOrder.CommonSectionAdapter;
import com.system.bhouse.bhouse.CommonTask.TransportationManagement.adapter.BaseQuickAdapter;
import com.system.bhouse.bhouse.CommonTask.TransportationManagement.adapter.BaseSectionQuickAdapter;
import com.system.bhouse.bhouse.CommonTask.TransportationManagement.entity.MySection;
import com.system.bhouse.bhouse.CommonTask.Widget.LRecyclerView;
import com.system.bhouse.bhouse.R;
import com.system.bhouse.bhouse.setup.WWCommon.WWBackActivity;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
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
public class CommonPickerActivity extends WWBackActivity implements AdapterView.OnItemSelectedListener {

    @ViewById(R.id.et_query)
    EditText etQuery;
    @ViewById(R.id.tv_Empty)
    TextView tvEmpty;
    @ViewById(R.id.spDwon)
    Spinner spDwon;
    private ArrayAdapter<String> stringArrayAdapter;
    private ArrayList<MySection> mySections;
    private BaseSectionQuickAdapter commonSectionAdapter;

    @AfterViews
    public void initFragment() {
        setActionBarMidlleTitle(title);
        initData();
        if (!isNewsAdapter) {
            commonSectionAdapter = new CommonSectionAdapter(LayoutID, R.layout.layout_home_recommend_empty, mySections);
        }else {
            commonSectionAdapter=new LoadingIntoSectionAdapter(LayoutID, R.layout.layout_home_recommend_empty, mySections);
        }
//        myAdapter = new MyAdapter();
        commonSectionAdapter.setFriends(mySections);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        listView.setLayoutManager(linearLayoutManager);
        listView.setAdapter(commonSectionAdapter);
        queryEdit();
    }

    private  void initData() {
        mySections=new ArrayList<>();
        if (!bProBOMs.isEmpty())
        {
            for (BProBOM bom:bProBOMs)
            {
                mySections.add(new MySection(bom));
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
                commonSectionAdapter.getFilter().filter(s);
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

        commonSectionAdapter.setmRefreshListener(new BaseSectionQuickAdapter.FilterRefreshListener<MySection>() {
            @Override
            public String getIsFilter(String prefixString, MySection value) {
                String username = value.t.getCoding();
                // First match against the whole ,non-splitted value，假如含有关键字的时候，添加
                username.contains(prefixString);
                return username;
            }

            @Override
            public void onAdapterRefresh() {
                commonSectionAdapter.notifyDataSetChanged();
            }
        });

        commonSectionAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                Intent data = new Intent();
                data.putExtra("projectname", bProBOMs.get(position).getProjectname());
                data.putExtra("coding", bProBOMs.get(position).getCoding());
                data.putExtra("HId", bProBOMs.get(position).getID());
                data.putExtra("position", Position);
                data.putExtra("BOMID",bProBOMs.get(position).getBOMID());
                data.putExtra("title",title);
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
    ArrayList<BProBOM> bProBOMs;

    @Extra
    String HId;

    @Extra
    Integer Position;

    @Extra
    Integer LayoutID;

    @Extra
    boolean isNewsAdapter;

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
            ArrayList<MySection> clone = (ArrayList<MySection>)this.mySections.clone();
            List<MySection> mySections = clone.subList(0,min);
            this.mySections.clear();
            this.mySections.addAll(mySections);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            Count=9999;
        }
        commonSectionAdapter.notifyDataSetChanged();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

//    class MyAdapter extends RecyclerView.Adapter<MyAdapter.ItemViewHolder> implements android.widget.Filterable {
//        private ArrayList<BProBOM> copyBProBOMs= new ArrayList<>();
//
//        public void setFriends(ArrayList<BProBOM> data) {
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
//                        final ArrayList<BProBOM> newValues = new ArrayList<BProBOM>();
//                        for (int i = 0; i < count; i++) {
//                            final BProBOM value = bProBOMs.get(i);
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
//                    bProBOMs.addAll((ArrayList<BProBOM>) results.values);//将过滤结果添加到这个对象
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
