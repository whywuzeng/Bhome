package com.system.bhouse.bhouse.task;

import android.graphics.PorterDuff;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.system.bhouse.bhouse.R;
import com.system.bhouse.bhouse.setup.WWCommon.RefreshBaseFragment;
import com.system.bhouse.bhouse.setup.utils.ListModify;
import com.system.bhouse.bhouse.task.Interface.TaskListParentUpdate;
import com.system.bhouse.bhouse.task.Interface.TaskListUpdate;
import com.system.bhouse.bhouse.task.bean.TaskObject;
import com.system.bhouse.bhouse.task.bean.TaskParams;
import com.system.bhouse.bhouse.task.bean.UserObject;
import com.system.bhouse.utils.ObjectSaveUtil;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import se.emilsjolander.stickylistheaders.StickyListHeadersAdapter;
import se.emilsjolander.stickylistheaders.StickyListHeadersListView;

@EFragment(R.layout.fragment_task_list)
public class TaskListFragment extends RefreshBaseFragment implements TaskListUpdate {

//    public static final String hostTaskDelete = Global.HOST_API + "/user/%s/project/%s/task/%s";
    final SimpleDateFormat mDateFormat = new SimpleDateFormat("yyyy-MM-dd");
//    //统计，已完成，进行中数量
//    final String urlTaskCountProject = Global.HOST_API + "/project/%d/task/user/count";
//    final String urlTaskCountMy = Global.HOST_API + "/tasks/projects/count";
//    final String URL_TASK_SATUS = Global.HOST_API + "/task/%s/status";
//    //筛选
//    final String URL_TASK_FILTER = Global.HOST_API + "/tasks/search?";
//
//
//    @FragmentArg
//    boolean mShowAdd = false;
//
//    //筛选 有4种类型，
//    // https://coding.net/api/tasks/search?creator=52353&label=bug&status=2&keyword=Bug
//    //-------------------
//    // 1.我的任务，我关注的，我创建的
//    // https://coding.net/api/tasks/search?owner=52353
//    // https://coding.net/api/tasks/search?watcher=52353
//    // https://coding.net/api/tasks/search?creator=52353
//
//    // 2.进行中，已完成
//    // https://coding.net/api/tasks/search?status=1
//    // https://coding.net/api/tasks/search?status=2
//
//    // 3.标签筛选 标签内容
//    // https://coding.net/api/tasks/search?label=Bug
//
//    // 4.关键字筛选
//    // https://coding.net/api/tasks/search?keyword=Bug
//    @FragmentArg
//    String mMeAction;
//    @FragmentArg
//    String mStatus;
//    @FragmentArg
//    String mLabel;
//    @FragmentArg
//    String mKeyword;
//
//    @FragmentArg
//    TaskObject.Members mMembers;
//    @FragmentArg
//    ProjectObject mProjectObject;
//
//    @ViewById
//    View blankLayout;
//    @ViewById
//    FloatingActionButton fab;
    @ViewById
    StickyListHeadersListView listView;
//
//    @StringArrayRes
//    String[] task_titles;
//
    boolean mNeedUpdate = true;
    ArrayList<TaskObject.SingleTask> mData = new ArrayList<>();
//    int mSectionId;
//
//    int mTaskCount[] = new int[2];
//    boolean mUpdateAll = true;
//    String urlAll = "";
//    View.OnClickListener onClickRetry = v -> onRefresh();
    TestBaseAdapter mAdapter;
    String mToday = "";
    String mTomorrow = "";
//    WeakHashMap<View, Integer> mOriginalViewHeightPool = new WeakHashMap<>();
    private TaskListParentUpdate mParent;
    private View listFooter;

//
    public void setParent(TaskListParentUpdate parent) {
        mParent = parent;
    }


//
//    @Override
//    public void onCreate(Bundle saveInstanceState) {
//        super.onCreate(saveInstanceState);
//        //setHasOptionsMenu(true);
//    }
//
//    @Override
//    public void taskListUpdate(boolean must) {
//        if (must) {
//            mNeedUpdate = true;
//        }
//
//        if (mNeedUpdate) {
//            mNeedUpdate = false;
//            initSetting();
//            loadData();
//        }
//    }
//
//    @Override
//    public void onRefresh() {
//        initSetting();
//        loadData();
//    }
//
//    @OptionsItem
//    public void action_add() {
//        mNeedUpdate = true;
//        Intent intent = new Intent(getActivity(), TaskAddActivity_.class);
//        TaskObject.SingleTask task = new TaskObject.SingleTask();
//        task.project = mProjectObject;
//        task.project_id = mProjectObject.getId();
//        task.owner = AccountInfo.loadAccount(getActivity());
//        task.owner_id = task.owner.id;
//
//        intent.putExtra("mSingleTask", task);
//        intent.putExtra("mUserOwner", mMembers.user);
//
//        getParentFragment().startActivityForResult(intent, ListModify.RESULT_EDIT_LIST);
//    }
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        mNeedUpdate = true;
//        return super.onCreateView(inflater, container, savedInstanceState);
//    }
//
//    //检查是否有筛选条件
//    String checkHostFilter() {
//        String host = "";
//        int userId = mMembers.user_id;
//
//        //项目内 不是全部任务
//        if (mShowAdd && userId != 0) {
//            if (!TextUtils.isEmpty(mMeAction)) {
//                host += String.format("owner=%s&", userId);
//            }
//            //关注，创建可以返回数据
//            if (!TextUtils.isEmpty(mMeAction) && !mMeAction.equals("owner")) {
//                if (!TextUtils.isEmpty(mMeAction)) {
//                    host += String.format("%s=%s&", mMeAction, MyApp.sUserObject.id);
//                }
//            }
//        } else if (mShowAdd) {
//            //项目内 全部任务
//            if (!TextUtils.isEmpty(mMeAction) && !mMeAction.equals("owner")) {
//                host += String.format("%s=%s&", mMeAction, MyApp.sUserObject.id);
//            }
//        } else {
//            //项目外
//            if (!TextUtils.isEmpty(mMeAction) && userId != 0) {
//                host += String.format("%s=%s&", mMeAction, userId);
//            }
//        }
//
//        if (!TextUtils.isEmpty(mStatus) && !mStatus.equals("0")) {
//            host += String.format("status=%s&", mStatus);
//        }
//        if (!TextUtils.isEmpty(mLabel)) {
//            host += String.format("label=%s&", Global.encodeUtf8(mLabel));
//        }
//        if (!TextUtils.isEmpty(mKeyword)) {
//            host += String.format("keyword=%s&", Global.encodeUtf8(mKeyword));
//        }
//        if (mProjectObject != null && !mProjectObject.isEmpty()) {
//            host += String.format("project_id=%s&", mProjectObject.getId());
//        }
//        //去掉最后一个 &
//        if (!TextUtils.isEmpty(host)) {
//            return host.substring(0, host.length() - 1);
//        }
//
//        return host;
//    }
//
//    String createHost(String userId, String type) {
//        //检查是否有筛选条件
//        String searchUrl = checkHostFilter();
//        if (!TextUtils.isEmpty(searchUrl)) {
//            return URL_TASK_FILTER + searchUrl;
//        }
//
//        String BASE_HOST = Global.HOST_API + "%s/tasks%s?";
//        String userType;
//        if (mProjectObject.isEmpty()) {
//            userType = type;
//
//        } else {
//            if (userId.isEmpty()) {
//                userType = type;
//            } else {
//                userType = "/user/" + userId + type;
//            }
//        }
//
//        return String.format(BASE_HOST, mProjectObject.backend_project_path, userType);
//    }

    @AfterViews
    protected void initTaskListFragment() {
        initRefreshLayout();

        Calendar calendar = Calendar.getInstance();
        mToday = mDateFormat.format(calendar.getTimeInMillis());
        mTomorrow = mDateFormat.format(calendar.getTimeInMillis() + 1000 * 60 * 60 * 24);

        mNeedUpdate = true;

        mAdapter = new TestBaseAdapter();

//        fab.attachToListView(listView.getWrappedList());
//        fab.setVisibility(View.GONE);

        listFooter = getActivity().getLayoutInflater().inflate(R.layout.divide_bottom_15, listView.getWrappedList(), false);
        listView.setAreHeadersSticky(false);
        listView.addFooterView(listFooter, null, false);
        listView.setAdapter(mAdapter);

//        updateFootStyle();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                TaskObject.SingleTask singleTask = (TaskObject.SingleTask) mAdapter.getItem(position);
                mNeedUpdate = true;

                TaskAddActivity_.intent(getParentFragment())
                        .mSingleTask(singleTask)
                        .canPickProject(false)
                        .startForResult(ListModify.RESULT_EDIT_LIST);
            }
        });


//        listView.setOnItemLongClickListener((parent, view, position, id) -> {
//            String content = mData.get(position).content;
//            showDialog("删除任务", content, (dialog, which) -> deleteTask(position));
//            return true;
//        });


//        initUrlAndLoadData();

        passData(0);
    }



    private void passData(int task_orderid) {
        //这里可以直接送  数据过来。
        HashMap<Integer, TaskParams> mHashTaskParam = (HashMap<Integer, TaskParams>)ObjectSaveUtil.readObject(getActivity());
        if (mHashTaskParam==null)
        {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-mm-dd");
        Date curdate = new Date(System.currentTimeMillis());
        String format = simpleDateFormat.format(curdate);
        TaskObject.SingleTask singleTask=new TaskObject.SingleTask();
        singleTask.content="生产任务要速度搞起来。。。";
        singleTask.deadline=format;
        UserObject userObject = new UserObject();
        userObject.name="wuzeng";
        singleTask.creator=userObject;
        mData.add(singleTask);

        }else {

            for (Integer key : mHashTaskParam.keySet()) {
                TaskObject.SingleTask singleTask = new TaskObject.SingleTask();
                if (key==task_orderid) {
                    TaskParams taskParams = mHashTaskParam.get(key);
                    singleTask.content = taskParams.content;
                    singleTask.deadline = taskParams.deadline;

                    singleTask.creator = taskParams.owner;
                    mData.add(singleTask);
                }else  if (mData.size()==0)
                {
                    for (Integer key1 : mHashTaskParam.keySet()) {
                        TaskObject.SingleTask singleTask1 = new TaskObject.SingleTask();
                        TaskParams taskParams = mHashTaskParam.get(key1);
                        singleTask1.content = taskParams.content;
                        singleTask1.deadline = taskParams.deadline;

                        singleTask1.creator = taskParams.owner;
                        mData.add(singleTask1);
                    }
                }
            }

        }

    }

    @Override
    public void onRefresh() {
        exampleLoadingFinishe();
    }

    @Override
    public void taskListUpdate(boolean must,int task_orderid) {
        if(must){
            passData(task_orderid);
        }

    }

    //
//    private void initUrlAndLoadData() {
//        urlAll = createHost(mMembers.user.global_key, "/all");
//
//        taskListUpdate(true);
//        taskFragmentLoading(true);
//    }
//
//    private void updateFootStyle() {
//        if (mData.isEmpty()) {
//            listFooter.setVisibility(View.INVISIBLE);
//        } else {
//            listFooter.setVisibility(View.VISIBLE);
//        }
//    }
//
//    @Click
//    void fab() {
//        action_add();
//    }
//
//    private void loadData() {
//        getNextPageNetwork(urlAll, urlAll);
//
//        if (mUpdateAll) {
//            if (mProjectObject.isEmpty()) {
//                getNetwork(urlTaskCountMy, urlTaskCountMy);
//            } else {
//                String url = String.format(urlTaskCountProject, mProjectObject.getId());
//                getNetwork(url, urlTaskCountProject);
//            }
//        }
//    }
//
//    @Override
//    protected void initSetting() {
//        super.initSetting();
//
//        mTaskCount[0] = 0;
//        mTaskCount[1] = 0;
//
//        mSectionId = 0;
//        mUpdateAll = true;
//    }
//
//    public void taskFragmentLoading(boolean isLoading) {
//        BlankViewHelp.setBlankLoading(blankLayout, isLoading);
//    }
//
//    @Override
//    public void parseJson(int code, JSONObject respanse, String tag, int pos, Object data) throws JSONException {
//        if (tag.equals(urlAll)) {
//            setRefreshing(false);
//            taskFragmentLoading(false);
//
//            if (code == 0) {
//                if (mUpdateAll) {
//                    mData.clear();
//                    mUpdateAll = false;
//                }
//
//                JSONObject jsonData = respanse.getJSONObject("data");
//                JSONArray array = jsonData.getJSONArray("list");
//                for (int i = 0; i < array.length(); ++i) {
//                    TaskObject.SingleTask task = new TaskObject.SingleTask(array.getJSONObject(i));
//                    mData.add(task);
//                }
//
//                mAdapter.notifyDataSetChanged();
//
//                AccountInfo.saveTasks(getActivity(), mData, mProjectObject.getId(), mMembers.id);
//                BlankViewDisplay.setBlank(mData.size(), this, true, blankLayout, onClickRetry);
//
//            } else {
//                showErrorMsg(code, respanse);
//                BlankViewDisplay.setBlank(mData.size(), this, false, blankLayout, onClickRetry);
//            }
//
//            mUpdateAll = false;
//        } else if (tag.equals(urlTaskCountMy)) {
//            if (code == 0) {
//                JSONArray array = respanse.getJSONArray("data");
//                for (int i = 0; i < array.length(); ++i) {
//                    JSONObject item = array.getJSONObject(i);
//                    if (item.optInt("project") == (mProjectObject.getId())) {
//                        mTaskCount[0] = item.getInt("processing");
//                        mTaskCount[1] = item.getInt("done");
//                        break;
//                    }
//                }
//                mAdapter.notifyDataSetChanged();
//            }
//
//        } else if (tag.equals(urlTaskCountProject)) {
//            if (code == 0) {
//                JSONArray array = respanse.getJSONArray("data");
//                for (int i = 0; i < array.length(); ++i) {
//                    JSONObject item = array.getJSONObject(i);
//                    if (Integer.valueOf(item.getString("user")) == mMembers.id) {
//                        mTaskCount[0] = item.getInt("processing");
//                        mTaskCount[1] = item.getInt("done");
//                        break;
//                    }
//                }
//                mAdapter.notifyDataSetChanged();
//            }
//
//        } else if (tag.equals(hostTaskDelete)) {
//            if (code == 0) {
//                umengEvent(UmengEvent.TASK, "删除任务");
//                mData.remove(pos);
//                mAdapter.notifyDataSetChanged();
//                if (mParent != null) {
//                    mNeedUpdate = false;
//                    mParent.taskListParentUpdate();
//                }
//
//            } else {
//                showErrorMsg(code, respanse);
//            }
//        } else if (tag.equals(URL_TASK_SATUS)) {
//            if (code == 0) {
//                umengEvent(UmengEvent.TASK, "修改任务");
//
//                TaskParam param = (TaskParam) data;
//                TaskObject.SingleTask task = param.mTask;
//                task.status = param.mStatus;
//
//            } else {
//                Toast.makeText(getActivity(), "修改任务失败", Toast.LENGTH_SHORT).show();
//            }
//        }
//    }
//
//    void deleteTask(final int pos) {
//        TaskObject.SingleTask task = mData.get(pos);
//        String url = String.format(hostTaskDelete, task.project.owner_user_name, task.project.name, task.getId());
//        deleteNetwork(url, hostTaskDelete, pos, null);
//    }
//
//    void statusTask(final int pos, final int id, final boolean complete) {
//        RequestParams params = new RequestParams();
//        int completeStatus = complete ? 2 : 1;
//        params.put("status", completeStatus); // 任务完成2，任务正在进行1
//        putNetwork(String.format(URL_TASK_SATUS, id), params, URL_TASK_SATUS, new TaskParam(mData.get(pos), completeStatus));
//    }
//
//    public interface FloatButton {
//        void showFloatButton(boolean show);
//    }
//
//    static class TaskParam {
//        TaskObject.SingleTask mTask;
//        int mStatus;
//
//        TaskParam(TaskObject.SingleTask mTask, int mStatus) {
//            this.mTask = mTask;
//            this.mStatus = mStatus;
//        }
//    }
//
    public class TestBaseAdapter extends BaseAdapter implements
            StickyListHeadersAdapter {

        public TestBaseAdapter() {
        }

        @Override
        public void notifyDataSetChanged() {
//            mSectionId = 0;
//            for (TaskObject.SingleTask item : mData) {
//                if (item.status == TaskObject.STATUS_PRECESS) {
//                    ++mSectionId;
//                } else {
//                    break;
//                }
//            }
//
//            updateFootStyle();

            super.notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            return mData.size();
//            return 1;
        }

        @Override
        public Object getItem(int position) {
            return mData.get(position);
//            return null;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                holder = new ViewHolder();
                convertView = mInflater.inflate(R.layout.fragment_task_list_item, parent, false);
                holder.mCheckBox = (CheckBox) convertView.findViewById(R.id.checkbox);
                holder.mTitle = (TextView) convertView.findViewById(R.id.title);
                holder.mDeadline = (TextView) convertView.findViewById(R.id.deadline);
                holder.mDeadline.setBackgroundResource(R.drawable.task_list_item_deadline_background);

                holder.mName = (TextView) convertView.findViewById(R.id.name);
                holder.mTime = (TextView) convertView.findViewById(R.id.time);

//                holder.mDiscuss = (TextView) convertView.findViewById(R.id.discuss);
                holder.mIcon = (ImageView) convertView.findViewById(R.id.icon);
                holder.mTaskPriority = convertView.findViewById(R.id.taskPriority);
//                holder.mTaskDes = convertView.findViewById(R.id.taskDes);
                holder.mLayoutDeadline = convertView.findViewById(R.id.layoutDeadline);
                holder.mRefId = (TextView) convertView.findViewById(R.id.referenceId);
//                holder.flowLabelLayout = (FlowLabelLayout) convertView.findViewById(R.id.flowLayout);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            final TaskObject.SingleTask data = (TaskObject.SingleTask) getItem(position);
            holder.mTitle.setText("      " + data.content);

//            holder.mRefId.setText(data.getNumber());
            holder.mName.setText(data.creator.name);
            holder.mTime.setText(data.deadline);
//            holder.mDiscuss.setText(String.valueOf(data.comments));
//            iconfromNetwork(holder.mIcon, data.owner.avatar);

//            int flowWidth = MyApp.sWidthPix - Global.dpToPx(100 + 15); // item 左边空 100 dp，右边空15dp
//            if (!data.deadline.isEmpty()) {
//                flowWidth -= Global.dpToPx(55);
//            }
//            holder.flowLabelLayout.setLabels(data.labels, flowWidth);

            final int pos = position;

//            holder.mCheckBox.setOnCheckedChangeListener(null);
//            if (data.status == 1) {
//                holder.mCheckBox.setChecked(false);
//            } else {
//                holder.mCheckBox.setChecked(true);
//            }

//            holder.mTaskDes.setVisibility(data.has_description ? View.VISIBLE : View.INVISIBLE);

//            final int priorityIcons[] = new int[]{
//                    R.drawable.task_mark_0,
//                    R.drawable.task_mark_1,
//                    R.drawable.task_mark_2,
//                    R.drawable.task_mark_3,
//            };

//            int priority = data.priority;
//            if (priorityIcons.length <= priority) {
//                priority = priorityIcons.length - 1;
//            }

//            holder.mTaskPriority.setBackgroundResource(priorityIcons[priority]);

//            if (data.deadline.isEmpty() && data.labels.isEmpty()) {
//                holder.mLayoutDeadline.setVisibility(View.GONE);
//            } else {
                holder.mLayoutDeadline.setVisibility(View.VISIBLE);
//            }

            int[] taskColors = new int[]{
                    0xfff49f31,
                    0xff97ba66,
                    0xfff24b4b,
                    0xffb2c6d0,
                    0xffc7c8c7
            };

            holder.mDeadline.setText("今天");

//            if (data.deadline.isEmpty()) {
//                holder.mDeadline.setVisibility(View.GONE);
//            } else {
//                holder.mDeadline.setVisibility(View.VISIBLE);
//
//                if (data.deadline.equals(mToday)) {
//                    holder.mDeadline.setText("今天");
//                    holder.setDeadlineColor(taskColors[0]);
//                } else if (data.deadline.equals(mTomorrow)) {
//                    holder.mDeadline.setText("明天");
//                    holder.setDeadlineColor(taskColors[1]);
//                } else {
//                    if (data.deadline.compareTo(mToday) < 0) {
//                        holder.setDeadlineColor(taskColors[2]);
//                    } else {
//                        holder.setDeadlineColor(taskColors[3]);
//                    }
//                    String num[] = data.deadline.split("-");
//                    holder.mDeadline.setText(String.format("%s/%s", num[1], num[2]));
//                }
//
//                if (data.isDone()) {
//                    holder.setDeadlineColor(taskColors[4]);
//                }
//            }

//            holder.mCheckBox.setOnCheckedChangeListener((buttonView, isChecked) ->
//                    statusTask(pos, data.getId(), isChecked)
//            );

                //下拉  加载
//            if (position == mData.size() - 1) {
//                loadData();
//            }

            return convertView;
        }

        @Override
        public View getHeaderView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = mInflater.inflate(R.layout.divide_top_15, parent, false);
            }

            return convertView;
        }

        @Override
        public long getHeaderId(int position) {
            return 0;
        }

        class ViewHolder {
            CheckBox mCheckBox;
            ImageView mIcon;
            TextView mTitle;

            TextView mDeadline;
            TextView mName;
            TextView mTime;
            TextView mDiscuss;
            View mTaskPriority;
            View mTaskDes;
            View mLayoutDeadline;
//            FlowLabelLayout flowLabelLayout;
            TextView mRefId;

            public void setDeadlineColor(int color) {
                mDeadline.getBackground().setColorFilter(color, PorterDuff.Mode.SRC_IN);
                mDeadline.setTextColor(color);
            }
        }
    }
//
//    @Override
//    public void onAttach(Activity activity) {
//        super.onAttach(activity);
//        EventBus.getDefault().register(this);
//    }
//
//    @Override
//    public void onDetach() {
//        super.onDetach();
//        EventBus.getDefault().unregister(this);
//    }
//
//    //筛选后刷新
//    public void onEventMainThread(Object object) {
//        if (object instanceof EventFilterDetail) {
//            EventFilterDetail eventFilter = (EventFilterDetail) object;
//            mMeAction = eventFilter.meAction;
//            mStatus = eventFilter.status;
//            mLabel = eventFilter.label;
//            mKeyword = eventFilter.keyword;
//
//            //重新加载所有
//            mUpdateAll = true;
//            initUrlAndLoadData();
//        }
//    }
}
