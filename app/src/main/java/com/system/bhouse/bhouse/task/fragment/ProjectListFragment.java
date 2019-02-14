package com.system.bhouse.bhouse.task.fragment;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.system.bhouse.bhouse.R;
import com.system.bhouse.bhouse.setup.WWCommon.RefreshBaseFragment;
import com.system.bhouse.bhouse.task.bean.ProjectObject;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import se.emilsjolander.stickylistheaders.StickyListHeadersAdapter;
import se.emilsjolander.stickylistheaders.StickyListHeadersListView;

import static com.system.bhouse.bhouse.R.id.txtDesc;


/**
 * Created by Administrator on 2017-11-9.
 */
@EFragment(R.layout.project_list_fragment)
public class ProjectListFragment extends RefreshBaseFragment implements View.OnClickListener {


    @ViewById
    Button btn_action;

    @ViewById
    StickyListHeadersListView listView;
    private Myadpter myAdapter;

    //data 这个
    ArrayList<ProjectObject> mData = new ArrayList<>();


    @Override
    public void onRefresh() {
        exampleLoadingFinishe();
    }

    @AfterViews
    protected final void initProjectListFragment() {
        initRefreshLayout();

        btn_action.setOnClickListener(this);
        listView.setAreHeadersSticky(false);
        View listviewfoot = getActivity().getLayoutInflater().inflate(R.layout.divide_bottom_15, listView.getWrappedList(), false);
        listView.addFooterView(listviewfoot);

        passData();

        if (myAdapter == null) {
            myAdapter = new Myadpter();
        }
        listView.setAdapter(myAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ProjectObject item = (ProjectObject) myAdapter.getItem(position);
                listview(item);
            }
        });
    }

    private void listview(ProjectObject item) {

        Intent intent = new Intent();
        intent.putExtra("data", item);
        getActivity().setResult(Activity.RESULT_OK, intent);
        getActivity().finish();

    }

    private void passData() {

//        String ownerName = item.owner_user_name;
//        viewHolder.comment.setText(ownerName); //名字
//
//        viewHolder.name.setText(item.name);
//        viewHolder.txtDesc.setText(item.getDescription());

        ProjectObject projectObject=new ProjectObject();
        projectObject.owner_user_name="wuzeng";
        projectObject.name="富基公园的生产楼盘";
        projectObject.setDescription("要楼盘看看，看看环境和地基");
        mData.add(projectObject);
    }

    @Override
    public void onClick(View v) {

    }

    static class ViewHolder {
        @BindView(R.id.icon)
        ImageView icon;
        @BindView(R.id.privatePin)
        ImageView privatePin;
        @BindView(R.id.privateIcon)
        View privateIcon;
        @BindView(R.id.name)
        TextView name;
        @BindView(R.id.name2)
        TextView name2;
        @BindView(R.id.txtDesc)
        TextView txtDesc;
        @BindView(R.id.tv_start_count)
        TextView tvStartCount;
        @BindView(R.id.tv_follow_count)
        TextView tvFollowCount;
        @BindView(R.id.tv_fork_count)
        TextView tvForkCount;
        @BindView(R.id.ll_bottom_menu)
        LinearLayout llBottomMenu;
        @BindView(R.id.comment)
        TextView comment;
        @BindView(R.id.flayoutAction)
        FrameLayout flayoutAction;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }


    class Myadpter extends BaseAdapter implements StickyListHeadersAdapter {

        @Override
        public View getHeaderView(int position, View convertView, ViewGroup parent) {

            if (convertView==null)
            {
                convertView = inflater.inflate(R.layout.divide_top_15, parent, false);
            }
            return convertView;
        }

        @Override
        public long getHeaderId(int position) {
            return 0;
        }

        @Override
        public int getCount() {
            return 1;
        }

        @Override
        public Object getItem(int position) {

            return mData.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = convertView;

            ViewHolder viewHolder=null;

            if (convertView==null)
            {
                view = inflater.inflate(R.layout.project_all_list_item, parent, false);
                viewHolder = new ViewHolder(view);
                viewHolder.name = (TextView) view.findViewById(R.id.name);
                viewHolder.icon = (ImageView) view.findViewById(R.id.icon);
                viewHolder.comment = (TextView) view.findViewById(R.id.comment);
                viewHolder.privateIcon = view.findViewById(R.id.privateIcon);
                viewHolder.flayoutAction = (FrameLayout) view.findViewById(R.id.flayoutAction);
                viewHolder.txtDesc = (TextView) view.findViewById(txtDesc);
                viewHolder.privatePin = (ImageView) view.findViewById(R.id.privatePin);
                viewHolder.name2 = (TextView) view.findViewById(R.id.name2);
                view.setTag(viewHolder);
            }else
            {
                viewHolder= (ViewHolder) view.getTag();
            }

            ProjectObject item = (ProjectObject) getItem(position);

            String ownerName = item.owner_user_name;
            viewHolder.comment.setText(ownerName); //名字

            viewHolder.name.setText(item.name);
            viewHolder.txtDesc.setText(item.getDescription());



            return view;
        }

    }
}
