package com.system.bhouse.bhouse.task;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.system.bhouse.base.Global;
import com.system.bhouse.bhouse.R;
import com.system.bhouse.bhouse.setup.WWCommon.WWBackActivity;
import com.system.bhouse.bhouse.setup.utils.DatePickerFragment;
import com.system.bhouse.bhouse.task.Interface.NewTaskParam;
import com.system.bhouse.bhouse.task.Interface.SimpleTextWatcher;
import com.system.bhouse.bhouse.task.adpter.StatusAdapter;
import com.system.bhouse.bhouse.task.adpter.TaskListHolder;
import com.system.bhouse.bhouse.task.bean.DynamicObject;
import com.system.bhouse.bhouse.task.bean.ProjectObject;
import com.system.bhouse.bhouse.task.bean.TaskObject;
import com.system.bhouse.bhouse.task.bean.TaskParams;
import com.system.bhouse.bhouse.task.bean.UserObject;
import com.system.bhouse.bhouse.task.program.EnterLayout;
import com.system.bhouse.bhouse.task.program.ImageCommentLayout;
import com.system.bhouse.bhouse.task.view.TaskAttrItem;
import com.system.bhouse.utils.ObjectSaveUtil;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.OnActivityResult;
import org.androidannotations.annotations.ViewById;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;

import io.github.lijunguan.imgselector.model.entity.ImageInfo;


@EActivity(R.layout.activity_task_add)
public class TaskAddActivity extends WWBackActivity implements Global.StartActivity, DatePickerFragment.DateSet, NewTaskParam {

//    private static final String TAG = makeLogTag(TaskAddActivity.class);

    public static final String RESULT_GLOBARKEY = "RESULT_GLOBARKEY";

    public static final int RESULT_REQUEST_FOLLOW = 1002;
    public static final int RESULT_REQUEST_SELECT_USER = 3;
    public static final int RESULT_REQUEST_DESCRIPTION = 4;
    public static final int RESULT_REQUEST_DESCRIPTION_CREATE = 5;
    public static final int RESULT_REQUEST_PICK_PROJECT = 6;
    public static final int RESULT_LABEL = 7;
    public static final int RESULT_REQUEST_PICK_WATCH_USER = 8;
    public static final int RESULT_RESUSE_REFRESOURCE = 9;

    private static final String TAG_HTTP_REMOVE_LABEL = "TAG_HTTP_REMOVE_LABEL";

//    final String HOST_COMMENT_ADD = Global.HOST_API + "/task/%s/comment";

//    private final MyImageGetter myImageGetter = new MyImageGetter(this);
//    private final ClickSmallImage onClickImage = new ClickSmallImage(this);

//    public static final int priorityDrawable[] = new int[]{
//            R.drawable.ic_task_priority_0,
//            R.drawable.ic_task_priority_1,
//            R.drawable.ic_task_priority_2,
//            R.drawable.ic_task_priority_3
//    };

    View listFooter;

//    final String HOST_TASK_ADD = Global.HOST_API + "%s/task";
//    final String HOST_FORMAT_TASK_COMMENT = Global.HOST_API + "/activity/task/%s?last_id=999999999";
//    final String HOST_TASK_UPDATE = Global.HOST_API + "/task/%s/update";
//    final String TAG_TASK_UPDATE = "TAG_TASK_UPDATE";
//    final String hostDeleteComment = Global.HOST_API + "/task/%s/comment/%s";
//    final String tagTaskDetail = "tagTaskDetail";
//    final String HOST_PREVIEW = Global.HOST_API + "/markdown/preview";

    @ViewById
    protected View blankLayout;

    @Extra
    TaskObject.SingleTask mSingleTask;

    @Extra
    ProjectObject mProjectObject = new ProjectObject();
    @Extra
    UserObject mUserOwner;

//    @Extra
//    TaskJumpParams mJumpParams;

    @Extra
    boolean canPickProject=true ;

    @ViewById
    ListView listView;

    @Bean
    StatusAdapter mStatusAdapter;

    View mHeadView;
    EditText title;


//    TopicLabelBar labelBar;

    TaskAttrItem layoutProjectName;
    TaskAttrItem layoutName;
    TaskAttrItem layoutPriovity;
    TaskAttrItem layoutDeadline;
    TaskAttrItem layoutPhase;

    TaskAttrItem layoutWatch;
    ViewGroup layoutRefResourceParent;

    TextView description;
    ViewGroup descriptionLayout;
    TextView descriptionButton;

//    TaskObject.TaskDescription descriptionData = new TaskObject.TaskDescription();
//    TaskObject.TaskDescription descriptionDataNew = new TaskObject.TaskDescription();

    TaskParams mNewParam;
    TaskParams mOldParam;
//    ImageCommentLayout mEnterComment;
//    String HOST_DESCRIPTER = Global.HOST_API + "/task/%s/description";
    MenuItem mMenuSave;
    String urlComments = "";

    private ArrayList<UserObject> watchUsers = new ArrayList<>();

//    @Bean
//    PriorityAdapter mPriorityAdapter;
//    @Bean
//    StatusAdapter mStatusAdapter;

    ArrayList<DynamicObject.DynamicTask> mData = new ArrayList<>();

    HashMap<String, String> mSendedImages = new HashMap<>();
    String tagUrlCommentPhoto = "";

     private ImageCommentLayout mEnterComment;

    boolean mTaskNoFound = true;

//    private View.OnClickListener mOnClickComment = new View.OnClickListener() {
//        @Override
//        public void onClick(View v) {
//            final TaskObject.TaskComment comment = (TaskObject.TaskComment) v.getTag();
//            if (comment.isMy()) {
//                showDialog("任务", "删除评论？", (dialog, which) -> {
//                    String url = String.format(hostDeleteComment, comment.taskId, comment.id);
//                    deleteNetwork(url, hostDeleteComment, comment.id);
//                });
//            } else {
//                EnterLayout mEnterLayout = mEnterComment.getEnterLayout();
//                mEnterLayout.content.setTag(comment);
//                String format = "回复 %s";
//                mEnterLayout.content.setHint(String.format(format, comment.owner.name));
//
//                mEnterLayout.restoreLoad(comment);
//
//                mEnterLayout.popKeyboard();
//            }
//        }
//    };
//
    BaseAdapter commentAdpter = new BaseAdapter() {
        @Override
        public int getCount() {
            return mData==null ? 0:mData.size();
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
        public int getItemViewType(int position) {

                return 0;
        }

        @Override
        public void notifyDataSetChanged() {
            super.notifyDataSetChanged();

            if (getCount() == 0) {
                listFooter.setVisibility(View.INVISIBLE);
            } else {
                listFooter.setVisibility(View.VISIBLE);
            }
        }

        @Override
        public int getViewTypeCount() {
            return 1;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            int count = getCount();
            if (getItemViewType(position) == 0) {
//                CommentHolder holder;
//                if (convertView == null) {
//                    convertView = mInflater.inflate(R.layout.activity_task_comment_much_image_task, parent, false);
//                    holder = new CommentHolder(convertView, mOnClickComment, myImageGetter, getImageLoad(), mOnClickUser, onClickImage);
//                    convertView.setTag(R.id.layout, holder);
//
//                } else {
//                    holder = (CommentHolder) convertView.getTag(R.id.layout);
//                }
//
//                TaskObject.TaskComment data = mData.get(position).getTaskComment();
//                holder.setContent(data);
//
//                holder.updateLine(position, count);
//
//                return convertView;
                return null;
            } else {
                TaskListHolder holder;
                if (convertView == null) {
                    convertView = mInflater.inflate(R.layout.task_list_item_dynamic, parent, false);
                    holder = new TaskListHolder(convertView);
                }
                else {
                    holder = (TaskListHolder) convertView.getTag(TaskListHolder.getTagId());
                }

                DynamicObject.DynamicTask data = mData.get(position);

//                holder.mContent.setText(data.dynamicTitle());

                int iconResId = R.drawable.ic_task_dynamic_update;
                try {
                    String resName = "ic_task_dynamic_" + data.action;
                    Field field = R.drawable.class.getField(resName);
                    iconResId = Integer.parseInt(field.get(null).toString());
                } catch (Exception e) {
                    Global.errorLog(e);
                }
                holder.mIcon.setImageResource(iconResId);

                holder.updateLine(position, count);

                return convertView;
            }

            }
    };



//    private View.OnClickListener onClickCreateDescription = new View.OnClickListener() {
//        @Override
//        public void onClick(View v) {
//            if (noSelectProject()) {
//                showMiddleToast(R.string.pick_project_first);
//                return;
//            }
//
//            TaskDescriptionActivity_
//                    .intent(TaskAddActivity.this)
//                    .taskId(0)
//                    .projectPath(mSingleTask.project.getProjectPath())
//                    .descriptionData(descriptionDataNew)
//                    .startForResult(RESULT_REQUEST_DESCRIPTION_CREATE);
//        }
//    };

    @Override
    public TaskParams getNewParam() {
        return mNewParam;
    }

    @AfterViews
    protected final void initTaskAddActivity() {
        initControl();

        if (mSingleTask != null) {
            initData();

// else if (mJumpParams != null) { // 跳转过来的，要先取得任务数据
//            requestTaskFromNetwork();
        } else {
            mSingleTask = new TaskObject.SingleTask();
         mNewParam =new TaskParams();
//
//            if (!mProjectObject.isEmpty()) {
//                mSingleTask.project = mProjectObject;
//                mSingleTask.project_id = mProjectObject.getId();
//                mSingleTask.priority = mProjectObject.getId();
//            }
//
            initData();
        }

        setHeadData();
    }


    private void initControl() {
        mEnterComment = new ImageCommentLayout(this, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendCommentAll();
            }
        });

        // 单独提出来是因为弹出软键盘时，由于head太长，导致 title 会被顶到消失，现在的解决方法是 edit作为一个单独的head加载
        View headEdit = mInflater.inflate(R.layout.activity_task_add_head_edit, listView, false);
        title = (EditText) headEdit.findViewById(R.id.title);

        listView.addHeaderView(headEdit, null, false);

        mHeadView = mInflater.inflate(R.layout.activity_task_add_head, listView, false);

        layoutProjectName = (TaskAttrItem) mHeadView.findViewById(R.id.layoutProjectName);
        layoutName = (TaskAttrItem) mHeadView.findViewById(R.id.layoutName);
        layoutPriovity = (TaskAttrItem) mHeadView.findViewById(R.id.layoutPriovity);
        layoutDeadline = (TaskAttrItem) mHeadView.findViewById(R.id.layoutDeadline);
        layoutPhase = (TaskAttrItem) mHeadView.findViewById(R.id.layoutPhase);
        layoutWatch = (TaskAttrItem) mHeadView.findViewById(R.id.layoutWatch);
        layoutRefResourceParent = (ViewGroup) mHeadView.findViewById(R.id.layoutRefResourceParent);
//        layoutRefResourceParent.setOnClickListener(clickRefResource);

        descriptionLayout = (ViewGroup) mHeadView.findViewById(R.id.descriptionLayout);
        description = (TextView) mHeadView.findViewById(R.id.description);
        descriptionButton = (TextView) mHeadView.findViewById(R.id.descriptionButton);
        listView.addHeaderView(mHeadView, null, false);

        listFooter = mInflater.inflate(R.layout.task_add_footer, listView, false);
        listView.addFooterView(listFooter);

        listFooter.setVisibility(View.INVISIBLE);

        listView.setAdapter(commentAdpter);

    }


//    private void updateLabels(List<TopicLabelObject> labels) {
//        labelBar.bind(labels, new TopicLabelBar.Controller() {
//            @Override
//            public boolean canShowLabels() {
//                return true;
//            }
//
//            @Override
//            public boolean canEditLabels() {
//                return true;
//            }
//
//            @Override
//            public void onEditLabels(TopicLabelBar view) {
//                if (!mSingleTask.project.isEmpty()) {
//                    TopicLabelActivity_.intent(TaskAddActivity.this)
//                            .labelType(TopicLabelActivity.LabelType.Task)
//                            .projectPath(mSingleTask.project.getProjectPath())
//                            .id(mSingleTask.getId())
//                            .checkedLabels(mSingleTask.labels)
//                            .startForResult(RESULT_LABEL);
//                } else {
//                    showMiddleToast("请先选择项目");
//                }
//            }
//
//            @Override
//            public void onRemoveLabel(TopicLabelBar view, int labelId) {
//                String url = mSingleTask.getHttpRemoveLabal(labelId);
//                deleteNetwork(url, TAG_HTTP_REMOVE_LABEL, labelId);
//            }
//        });
//    }

    //那个 发送的按钮  出现
    private void updateSendButton() {
        if (title.getText().toString().isEmpty()
                || isContentUnmodify() ) {
            enableSendButton(false);
        } else {
            enableSendButton(true);
        }
    }

    private boolean isContentUnmodify() {
        return ((mNewParam != null && mNewParam.equals(mOldParam)) && !descripChange()) ||
                mTaskNoFound;
    }

    private boolean descripChange() {
//        return !mSingleTask.isEmpty() &&
//                !descriptionData.markdown.equals(descriptionDataNew.markdown);
        return false;
    }

    private void enableSendButton(boolean enable) {
        if (mMenuSave == null) {
            return;
        }

        if (enable) {
            mMenuSave.setIcon(R.drawable.ic_menu_ok);
            mMenuSave.setEnabled(true);
        } else {
            mMenuSave.setIcon(R.drawable.ic_menu_ok_unable);
            mMenuSave.setEnabled(false);
        }
    }
//
    void initData() {

//        if (mSingleTask.isEmpty()) {
//            if (mUserOwner.id == 0) {
//                mSingleTask.owner = AccountInfo.loadAccount(this);
//            } else {
//                mSingleTask.owner = mUserOwner;
//            }
//            mSingleTask.owner_id = mSingleTask.owner.id;
//            mSingleTask.priority = 1; // 默认优先级是 1：正常处理
//        }

//
//        updateLabels(mSingleTask.labels);


        invalidateOptionsMenu();
        mNewParam = new TaskParams(mSingleTask);
        mOldParam = new TaskParams(mSingleTask);

//        mEnterComment.getEnterLayout().content.addTextChangedListener(new TextWatcherAt(this, this, RESULT_REQUEST_FOLLOW, mSingleTask.project));  处理 @联系人的 关系。

//        setHeadData();
//        listView.setAdapter(commentAdpter);

        selectMember();

//        if (mSingleTask.isEmpty()) {
//            setActionBarTitle("新建任务");
//            layoutPhase.setText2("未完成");
//            layoutPhase.setVisibility(View.GONE);
//            mEnterComment.hide();
//
//            findViewById(R.id.line2_comment_off).setVisibility(View.VISIBLE);
//            findViewById(R.id.line2_comment_on).setVisibility(View.GONE);
//
//            findViewById(R.id.descriptionLayout).setVisibility(View.GONE);
//
//        } else {
            if (mSingleTask.project.name!=null&&mSingleTask.project.name.length()>0) {
                setActionBarMidlleTitle(mSingleTask.project.name);
            }else {
                setActionBarMidlleTitle("任务");
            }
            title.setText(mSingleTask.content);

//            setStatus();
//            setPriority();

            layoutPhase.setVisibility(View.VISIBLE);

            findViewById(R.id.line2_comment_off).setVisibility(View.GONE);
            findViewById(R.id.line2_comment_on).setVisibility(View.VISIBLE);
//        }

        setDeadline();

//        initDescription();
//
//        // 获取任务的评论
//        if (!mSingleTask.isEmpty()) {
//            urlComments = String.format(HOST_FORMAT_TASK_COMMENT, mSingleTask.getId());
//            updateDynamicFromNetwork();
//        }
//
//        if (!mSingleTask.isEmpty()) {
//            CommentBackup.BackupParam param = new CommentBackup.BackupParam(CommentBackup.Type.Task, mSingleTask.getId(), 0);
//            EnterLayout mEnterLayout = mEnterComment.getEnterLayout();
//            mEnterLayout.content.setTag(param);
//            mEnterLayout.restoreLoad(param);
//        }
//
//        TextView time = (TextView) mHeadView.findViewById(R.id.time);
//        TextView createName = (TextView) mHeadView.findViewById(R.id.createrName);
//        if (mSingleTask.isEmpty()) {
//            createName.setText(mNewParam.owner.name);
//            time.setText("现在");
//        } else {
//            createName.setText(mSingleTask.creator.name);
//            time.setText(Global.dayToNow(mSingleTask.created_at));
//        }
//
//        TextView refrenceId = (TextView) mHeadView.findViewById(R.id.referenceId);
//        if (mSingleTask.isEmpty()) {
//            refrenceId.setVisibility(View.GONE);
//        } else {
//            refrenceId.setVisibility(View.VISIBLE);
//            refrenceId.setText(mSingleTask.getNumber());
//        }
//
//        // 设置任务的关注者
//        watchUserUpdateFromNetwork();
//        uiBindDataWatch();
//
//        uiBindDataProject();

        if (!canPickProject) {
            layoutProjectName.setVisibility(View.GONE);
        }

//        updateRefResourceNetwork();
    }

//    private void watchUserUpdateFromNetwork() {
//        if (mSingleTask.isEmpty()) {
//            return;
//        }

//        ProjectObject project = mSingleTask.project;
//        String url = String.format(project.getHttpProjectApi() + "/task/%d/watchers?pageSize=1000", mSingleTask.getId());
//        MyAsyncHttpClient.get(this, url, new MyJsonResponse(TaskAddActivity.this) {
//            @Override
//            public void onMySuccess(JSONObject response) {
//                super.onMySuccess(response);
//                watchUsers.clear();
//                JSONArray jsonList = response.optJSONObject("data").optJSONArray("list");
//                if (jsonList != null && jsonList.length() > 0) {
//                    for (int i = 0; i < jsonList.length(); ++i) {
//                        watchUsers.add(new UserObject(jsonList.optJSONObject(i)));
//                    }
//                }
//
//                uiBindDataWatch();
//            }
//        });
//    }
//
//    private void uiBindDataWatch() {
//        int watchUserCount = watchUsers.size();
//        String countString;
//        if (watchUserCount == 0) {
//            countString = "添加";
//        } else {
//            countString = String.format("%d人关注", watchUserCount);
//        }
//        layoutWatch.setText2(countString);
//    }
//
//    private void updateDynamicFromNetwork() {
//        getNetwork(urlComments, HOST_FORMAT_TASK_COMMENT);
//    }
//
//    private void initDescription() {
////        if (mSingleTask.isEmpty()) {
////            descriptionLayout.setOnClickListener(onClickCreateDescription);
////        } else {
////            if (mSingleTask.has_description) {
////                description.setText("载入备注中...");
////                HOST_DESCRIPTER = String.format(HOST_DESCRIPTER, mSingleTask.getCreateTime());
////                getNetwork(HOST_DESCRIPTER, HOST_DESCRIPTER);
////            } else {
////                descriptionLayout.setOnClickListener(onClickCreateDescription);
////            }
////        }
//        if (mSingleTask.isEmpty()) {
//            descriptionButtonUpdate(false);
//            descriptionButton.setOnClickListener(onClickCreateDescription);
//        } else {
//            if (mSingleTask.has_description) {
//                descriptionButtonUpdate(true);
//                HOST_DESCRIPTER = String.format(HOST_DESCRIPTER, mSingleTask.getId());
//                getNetwork(HOST_DESCRIPTER, HOST_DESCRIPTER);
//            } else {
//                descriptionButtonUpdate(false);
//                descriptionButton.setOnClickListener(onClickCreateDescription);
//            }
//        }
//    }
//
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
//        if (!mTaskNoFound) {

            MenuInflater menuInflater = getMenuInflater();
//            if (mJumpParams == null && mSingleTask.isEmpty()) {
            menuInflater.inflate(R.menu.task_add, menu);
//            } else {
//                menuInflater.inflate(R.menu.task_add_edit, menu);
//                if (mSingleTask != null && !mSingleTask.isEmpty() && !mSingleTask.creator.isMe()) {
//                    menu.findItem(R.id.action_delete).setVisible(false);
//                }
//            }
//            mMenuSave = menu.findItem(R.id.action_save);
//            updateSendButton();
//        }

//        }
        return super.onCreateOptionsMenu(menu);
    }
//
//    private void deleteTask() {
//        showDialog("任务", "删除任务？", (dialog, which) -> {
//            TaskObject.SingleTask task = mSingleTask;
//            String url = String.format(BaseCommonListFragment.hostTaskDelete, task.project.owner_user_name, task.project.name, task.getId());
//            deleteNetwork(url, BaseCommonListFragment.hostTaskDelete);
//            showProgressBar(true, "删除任务中...");
//        });
//    }
//
    private void setHeadData() {
        title.addTextChangedListener(new SimpleTextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                mNewParam.content = s.toString();
                updateSendButton();
            }
        });
//        title.setText("");
//
////        if (mSingleTask.isEmpty() && mSingleTask.project.isEmpty() && mProjectObject.isEmpty()) {
////            layoutProjectName.setVisibility(View.VISIBLE);
        layoutProjectName.setOnClickListener(new View.OnClickListener() {
                                                 @Override
                                                 public void onClick(View v) {
                                                     PickProjectActivity_.intent(TaskAddActivity.this)
                                                             .startForResult(RESULT_REQUEST_PICK_PROJECT);
                                                 }
                                             }

        );
//       } else {
//           layoutProjectName.setVisibility(View.GONE);
//      }
//


        layoutName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mSingleTask.project==null) {
                    showMiddleToast("请先选择一个任务!");
                    return;
                }
                MembersActivity_
                        .intent(TaskAddActivity.this)
                        .mProjectObjectId(mSingleTask.project_id)
                        .startForResult(RESULT_REQUEST_SELECT_USER);
            }
        });

        layoutPhase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popListSelectDialog(mStatusAdapter, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (which == 0) {
                            mNewParam.status = TaskObject.STATUS_PRECESS; // "未完成"
                        }
                        else {
                            mNewParam.status = TaskObject.STATUS_FINISH;
                        }

                        updateSendButton();
                    }
                });
            }
        });

        layoutDeadline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment newFragment = new DatePickerFragment();

                Bundle bundle = new Bundle();
                bundle.putString("date", mNewParam.deadline);
                bundle.putBoolean("clear", true);
                newFragment.setArguments(bundle);

                newFragment.setCancelable(true);
                newFragment.show(getSupportFragmentManager(), "datePicker");
                getSupportFragmentManager().executePendingTransactions();
            }
        });
//
//        layoutPriovity.setOnClickListener(v -> popListSelectDialog(
//                mPriorityAdapter,
//                (dialog, which) -> {
//                    mNewParam.priority = priorityDrawable.length - 1 - which;
//                    setPriority();
//                    updateSendButton();
//                }));
//
//        layoutWatch.setOnClickListener(v -> {
//            if (noSelectProject()) {
//                showMiddleToast(R.string.pick_project_first);
//                return;
//            }
//
//            WatcherListActivity_.intent(TaskAddActivity.this)
//                    .mProjectObjectId(mSingleTask.project_id)
//                    .mTaskId(mSingleTask.getId())
//                    .mWatchUsers(watchUsers)
//                    .startForResult(RESULT_REQUEST_PICK_WATCH_USER);
//        });
    }
//
//    private boolean noSelectProject() {
//        return mSingleTask.project.isEmpty();
//    }
//
//    private void setStatus() {
//        layoutPhase.setText2(mNewParam.status == 1 ? "未完成" : "已完成");
//    }
//
    @Override
    public void dateSetResult(String date, boolean clear) {
        if (clear) {
            mNewParam.deadline = "";
        } else {
            mNewParam.deadline = date;
        }

        setDeadline();
        updateSendButton();
    }
//
//    private void setPriority() {
//        String[] strings_priority = getResources().getStringArray(R.array.strings_priority);
//
//        String priority = strings_priority[strings_priority.length - 1];
//        if (mNewParam.priority < strings_priority.length) {
//            priority = strings_priority[mNewParam.priority];
//        }
//        layoutPriovity.setText2(priority);
//    }
//
    private void setDeadline() {
        String s = mNewParam.deadline;
        if (s.isEmpty()) {
            s = "未指定";
        }

        layoutDeadline.setText2(s);
    }
//
//    private void setDescription() {
//        Global.MessageParse parseData = HtmlContent.parseReplacePhoto(descriptionDataNew.description);
//        description.setText(Html.fromHtml(parseData.text, myImageGetter, Global.tagHandler));
//    }
//
//    @OptionsItem
//    void action_save() {
//        String content = title.getText().toString();
//        mNewParam.content=content;
////        if (EmojiFilter.containsEmoji(this, content)) {
////            return;
////        }
//
//        App.popSoftkeyboard(this, title, false);
//
//        ObjectSaveUtil.saveObject(this,CountData(mNewParam));
//
//        closeActivity("新建任务成功.");
//
//    }

    private HashMap<Integer, TaskParams> CountData(TaskParams mNewParam) {
        HashMap<Integer, TaskParams> paramsHashMap = (HashMap<Integer, TaskParams>) ObjectSaveUtil.readObject(this);
        if (paramsHashMap==null) {
            paramsHashMap = new HashMap<>();
            paramsHashMap.put(mNewParam.orderId, mNewParam);
        }else {
            paramsHashMap.put(mNewParam.orderId, mNewParam);
        }

        return paramsHashMap;
    }


    //
//    @OptionsItem
//    void action_copy() {
//        final String urlTemplate = Global.HOST + "/u/%s/p/%s/task/%d";
//        String url = String.format(urlTemplate, mSingleTask.project.owner_user_name, mSingleTask.project.name, mSingleTask.getId());
//        Global.copy(this, url);
//        showButtomToast("已复制 " + url);
//    }
//
//    @OptionsItem
//    void action_delete() {
//        deleteTask();
//    }
//
    void selectMember() {
        if (mSingleTask.project==null) {
            layoutName.setText2(R.string.no_pick);
            layoutName.getImage().setImageResource(R.drawable.ic_task_user);

        } else {
            layoutName.setText2(mNewParam.owner.name);

//            ImageLoader.getInstance().displayImage(Global.makeSmallUrlSquare(mNewParam.owner.avatar, getResources().getDimensionPixelSize(R.dimen.task_add_user_icon_width)), layoutName.getImage());
        }
    }
//
    private void closeActivity(String msg) {
        Intent intent = new Intent();
        if (mNewParam != null && mNewParam.owner != null) { // 友盟显示有可能为空
            intent.putExtra(RESULT_GLOBARKEY, mNewParam.orderId);
            setResult(Activity.RESULT_OK, intent);
        }

        if (!msg.isEmpty()) {
            showButtomToast(msg);
        }

        finish();
    }
//
//    @Override
//    public void parseJson(int code, JSONObject respanse, String tag, int pos, Object data) throws JSONException
//    {
//
//    }
//
//    private void descriptionButtonUpdate(boolean loading) {
//        if (!loading && descriptionDataNew.markdown.isEmpty()) {
//            descriptionButton.setText("添加描述");
//            descriptionButton.setTextColor(getResources().getColor(R.color.font_6));
//        } else {
//            descriptionButton.setText("查看描述");
//            descriptionButton.setTextColor(getResources().getColor(R.color.font_green));
//        }
//    }
//
    @OnActivityResult(RESULT_REQUEST_PICK_PROJECT)
    final void pickProject(int result, Intent data) {
        if (result == RESULT_OK) {
            ProjectObject project = (ProjectObject) data.getSerializableExtra("data");
            mSingleTask.project = project;
            mSingleTask.project_id = project.getId();

//            TaskObject.Members member = new TaskObject.Members(MyApp.sUserObject);
//            setPickUser(member);

            uiBindDataProject();
        }
    }

    private void uiBindDataProject() {
        if (mSingleTask.project==null) {
            return;
        }

//        iconfromNetwork(layoutProjectName.getImage(), mSingleTask.project.icon);
        layoutProjectName.setText2(mSingleTask.project.name);

//        updateLabels(mSingleTask.labels);
    }

    @OnActivityResult(ImageCommentLayout.RESULT_REQUEST_COMMENT_IMAGE)
    final void commentImage(int result, Intent data) {
        if (result == RESULT_OK) {
            mEnterComment.onActivityResult(
                    ImageCommentLayout.RESULT_REQUEST_COMMENT_IMAGE,
                    data);
        }
    }

    @OnActivityResult(ImageCommentLayout.RESULT_REQUEST_COMMENT_IMAGE_DETAIL)
    final void commentImageDetail(int result, Intent data) {
        if (result == RESULT_OK) {
            mEnterComment.onActivityResult(
                    ImageCommentLayout.RESULT_REQUEST_COMMENT_IMAGE_DETAIL,
                    data);
        }
    }
//
//    @OnActivityResult(RESULT_REQUEST_DESCRIPTION)
//    void resultDescription(int result, Intent data) {
//        if (result == RESULT_OK) {
//            updateDescriptionFromResult(data);
//            updateSendButton();
//
//            updateRefResourceNetwork();
//        }
//    }
//
//    @OnActivityResult(RESULT_REQUEST_DESCRIPTION_CREATE)
//    void resultDescriptionCreate(int result, Intent data) {
//        if (result == RESULT_OK) {
//            updateDescriptionFromResult(data);
//            updateSendButton();
//
//            updateRefResourceNetwork();
//        }
//    }
//
//    @OnActivityResult(RESULT_LABEL)
//    void resultLabels(int result, @OnActivityResult.Extra ArrayList<TopicLabelObject> labels) {
//        if (result == RESULT_OK) {
//            mSingleTask.labels = labels;
//            updateLabels(labels);
//        }
//    }
//
//    @OnActivityResult(RESULT_REQUEST_PICK_WATCH_USER)
//    void resultWatchUser(int result, @OnActivityResult.Extra ArrayList<UserObject> resultData) {
//        if (result == RESULT_OK) {
//            watchUsers = resultData;
//            uiBindDataWatch();
//        }
//    }
//
    @OnActivityResult(RESULT_REQUEST_SELECT_USER)
    void resultSelectUser(int resultCode, @OnActivityResult.Extra TaskObject.Members members) {
        if (resultCode == Activity.RESULT_OK) {
            setPickUser(members);
        }
    }
//
//    @OnActivityResult(RESULT_REQUEST_FOLLOW)
//    void resultRequestFollow(int resultCode, @OnActivityResult.Extra String name) {
//        if (resultCode == RESULT_OK) {
//            mEnterComment.getEnterLayout().insertText(name);
//        }
//    }
//
//    @OnActivityResult(RESULT_RESUSE_REFRESOURCE)
//    void resultRefResource(int resultCode, @OnActivityResult.Extra ArrayList<RefResourceObject> resultData) {
//       if (resultCode == RESULT_OK) {
//           refResourceList = resultData;
//           updateRefResourceUI();
//       }
//    }
//
//    private ArrayList<RefResourceObject> refResourceList = new ArrayList<>();
//
//    private void updateRefResourceNetwork() {
//        if (mSingleTask.isEmpty()) {
//            return;
//        }
//
//        String url = mSingleTask.project.getHttpProjectApi() +
//                "/resource_reference/" + mSingleTask.getNumberValue();
//        MyAsyncHttpClient.get(this, url, new MyJsonResponse(this) {
//            @Override
//            public void onMySuccess(JSONObject response) {
//                super.onMySuccess(response);
//
//                refResourceList.clear();
//                JSONObject jsonData = response.optJSONObject("data");
//                Iterator<String> iter = jsonData.keys();
//                while (iter.hasNext()) {
//                    String key = iter.next();
//                    JSONArray array = jsonData.optJSONArray(key);
//                    for (int i = 0; i < array.length(); ++i) {
//                        JSONObject item = array.optJSONObject(i);
//                        try {
//                            refResourceList.add(new RefResourceObject(item));
//                        } catch (Exception e) {
//                            Global.errorLog(e);
//                        }
//                    }
//                }
//
//                updateRefResourceUI();
//            }
//        });
//
//    }
//
//    private void updateRefResourceUI() {
//        if (mSingleTask.isEmpty()) {
//            return;
//        }
//
//        if (refResourceList.isEmpty()) {
//            layoutRefResourceParent.setVisibility(View.GONE);
//        } else {
//            layoutRefResourceParent.setVisibility(View.VISIBLE);
//            TaskAttrItem item = (TaskAttrItem) layoutRefResourceParent.findViewById(R.id.layoutRefResource);
//            item.setText2(refResourceList.size() + "个资源");
//        }
//    }
//
//    View.OnClickListener clickRefResource = v -> {
//        RefResourceActivity.Param param = new RefResourceActivity.Param(mSingleTask.project.getProjectPath(),
//                mSingleTask.getNumberValue());
//
//        RefResourceActivity_.intent(TaskAddActivity.this)
//                .mData(refResourceList)
//                .mParam(param)
//                .startForResult(RESULT_RESUSE_REFRESOURCE);
//    };
//
//    void updateDescriptionFromResult(Intent data) {
//        descriptionDataNew.markdown = data.getStringExtra("data");
//
//        RequestParams params = new RequestParams();
//        params.put("content", descriptionDataNew.markdown);
//        postNetwork(HOST_PREVIEW, params, HOST_PREVIEW);
//
//        descriptionButtonUpdate(false);
//    }
//
    private void setPickUser(TaskObject.Members member) {
        mNewParam.ownerId = member.user.id;
        mNewParam.owner = member.user;
        selectMember();
        updateSendButton();
    }

    @Override
    public void onBackPressed() {
        if (!isContentUnmodify()) {
            showDialog("任务", "确定放弃此次编辑？", (new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    closeActivity("");
                }
            }));
        } else {
            closeActivity("");
        }
    }

    private void popListSelectDialog(BaseAdapter selectsAdapter, DialogInterface.OnClickListener clickList) {
        new AlertDialog.Builder(this)
                .setAdapter(selectsAdapter, clickList)
                .show();
    }
//
    private void sendComment(String input) {
        mEnterComment.getEnterLayout().hideKeyboard();

        if (mSingleTask == null) {
            showButtomToast("发送评论失败");
            return;
        }

        EnterLayout mEnterLayout = mEnterComment.getEnterLayout();
        String s = input;

//        if (EmojiFilter.containsEmptyEmoji(this, s)) {
//            showProgressBar(false);
//            return;
//        }

//        Object item = mEnterLayout.content.getTag();
//        if (item != null && (item instanceof TaskObject.TaskComment)) { //任务的评论
//            TaskObject.TaskComment comment = (TaskObject.TaskComment) item;
//            s = Global.encodeInput(comment.owner.name, s);
//        } else {
//            s = Global.encodeInput("", s);
//        }

//        RequestParams params = new RequestParams();
//
//        params.put("content", s);

//        postNetwork(String.format(HOST_COMMENT_ADD, mSingleTask.getId()), params, HOST_COMMENT_ADD, 0, item);
    }

    @Override
    protected void OnNextUiChange() {
        super.OnNextUiChange();
        title.setText(send);
    }

    String send="";

    private void sendCommentAll() {
//        showProgressBar(true);
        exampleLoadingFinishe(getResources().getString(R.string.comment_str_box));

        ArrayList<ImageInfo> photos = mEnterComment.getPickPhotos();
        for (ImageInfo item : photos) {
            String imagePath = item.getPath();
            if (!mSendedImages.containsKey(imagePath)) {
                try {
//                    String url = mSingleTask.project.getHttpUploadPhoto();
//                    RequestParams params = new RequestParams();
//                    params.put("dir", 0);

                    String mdPhotoUri = String.format("\n![图片](%s)", imagePath);
                    mSendedImages.put(item.getPath()+"", mdPhotoUri);
//                    Uri uri = Uri.parse(imagePath);
//                    File file = new PhotoOperate(this).scal(uri);
//                    params.put("file", file);
//                    tagUrlCommentPhoto = imagePath; // tag必须不同，否则无法调用下一次
//                    postNetwork(url, params, tagUrlCommentPhoto, 0, imagePath);
//                    showProgressBar(true);
                } catch (Exception e) {
                    showProgressBar(false);
                }

//                return;
            }
        }

         send = mEnterComment.getEnterLayout().getContent();
        for (ImageInfo item : photos) {
            send += mSendedImages.get(item.getPath());
        }
        sendComment(send);
    }


}
