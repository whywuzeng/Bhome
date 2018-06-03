package com.system.bhouse.bhouse.task;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.system.bhouse.bhouse.R;
import com.system.bhouse.bhouse.setup.WWCommon.WWBackActivity;
import com.system.bhouse.bhouse.setup.program.FootUpdate;
import com.system.bhouse.bhouse.task.bean.TaskObject;
import com.system.bhouse.bhouse.task.bean.UserObject;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;


@EActivity(R.layout.activity_members)
public class MembersActivity extends WWBackActivity implements FootUpdate.LoadMore {

    @Extra
    protected int mProjectObjectId;

    protected String getProjectMembers = "getProjectMembers";
    protected String urlMembers = "";

    protected ArrayList<TaskObject.Members> mMembersArray = new ArrayList<>();

    @ViewById
    protected ListView listView;

    protected BaseAdapter adapter = new BaseAdapter() {
        @Override
        public int getCount() {
            return mMembersArray.size();
        }

        @Override
        public Object getItem(int position) {
            return mMembersArray.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                convertView = mInflater.inflate(R.layout.activity_members_list_item, parent, false);
                holder = new ViewHolder();
                holder.name = (TextView) convertView.findViewById(R.id.name);
                holder.icon = (ImageView) convertView.findViewById(R.id.icon);
                holder.watchCheck = convertView.findViewById(R.id.watchCheck);
                holder.ic = (ImageView) convertView.findViewById(R.id.ic);
                holder.alias = (TextView) convertView.findViewById(R.id.alias);

                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            TaskObject.Members data = mMembersArray.get(position);
            holder.name.setText(data.user.name);

//            iconfromNetwork(holder.icon, data.user.avatar);

            updateChecked(holder, data);

            UserObject user = data.user;

            TaskObject.Members.Type memberType = data.getType();
            int iconRes = memberType.getIcon();
//            if (iconRes == 0) {
//                holder.ic.setVisibility(View.GONE);
//            } else {
                holder.ic.setVisibility(View.VISIBLE);
                holder.ic.setImageResource(R.drawable.ic_project_member_manager);
//            }

            if (!data.alias.isEmpty()) {
                holder.alias.setText(data.alias);
                holder.alias.setVisibility(View.VISIBLE);
            } else {
                holder.alias.setVisibility(View.GONE);
            }

            if (position == mMembersArray.size() - 1) {
                loadMore();
            }

            return convertView;
        }

    };

    protected void updateChecked(ViewHolder holder, TaskObject.Members data) {
    }

    @AfterViews
    protected final void initMembersActivity() {
        //拼接ul
//        final String format = Global.HOST_API + "/project/%d/members?";
//        urlMembers = String.format(format, mProjectObjectId);

        passData();

        mFootUpdate.init(listView, mInflater, this);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();
                TaskObject.Members members = mMembersArray.get(position);
                intent.putExtra("members", members);
                setResult(Activity.RESULT_OK, intent);
                finish();

            }
        });

        loadMore();
    }

    private void passData() {
//        data.user.name);
//        data.getType()
//        data.alias
        TaskObject.Members members = new TaskObject.Members();
        members.user.name="wuzeng";
        members.alias="副总";

        mMembersArray.add(members);

    }

    @Override
    public void loadMore() {
//        getNextPageNetwork(urlMembers, getProjectMembers);
        showMiddleToast("加载啊啊 ~！！~~ 加不动呀");
    }

//    @Override
//    public void parseJson(int code, JSONObject respanse, String tag, int pos, Object data) throws JSONException {
//        if (tag.equals(getProjectMembers)) {
//            if (code == 0) {
//                ArrayList<TaskObject.Members> usersInfo = new ArrayList<>();
//
//                JSONArray members = respanse.getJSONObject("data").getJSONArray("list");
//
//                for (int i = 0; i < members.length(); ++i) {
//                    mMembersArray.add(new TaskObject.Members(members.getJSONObject(i)));
//                }
//
//                adapter.notifyDataSetChanged();
//            } else {
//                showErrorMsg(code, respanse);
//            }
//        }
//    }

//    @OptionsItem
//    public void action_add() {
//
//    }

    protected static class ViewHolder {
        public ImageView icon;
        public TextView name;
        public View watchCheck;
        TextView alias;
        ImageView ic;
    }
}
