package com.system.bhouse.bhouse.task.bean;

import android.text.Html;

import com.system.bhouse.bhouse.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Administrator on 2017-11-8.
 */

public class TaskObject {

        public static int STATUS_PRECESS = 1;
        public static int STATUS_FINISH = 2;

        //成员
        public static class Members implements Serializable {

            public enum Type {
                ower(100),
                member(80),
                manager(90),
                limited(75);

                Type(int type) {
                    this.type = type;
                }

                public int getIcon() {
                    switch (this) {
                        case ower:
                            return R.drawable.ic_project_member_create;
                        case manager:
                            return R.drawable.ic_project_member_manager;
                        case limited:
                            return R.drawable.ic_project_member_limited;
                        default: // member
                            return 0;
                    }
                }

                public static boolean canReadCode(int type) {
                    return type >= member.type;
                }

                public static boolean canManagerMember(int type) {
                    return type >= manager.type;
                }

                private int type;

                public int getType() {
                    return type;
                }

                public static Type idToEnum(int id) {
                    if (ower.type == id) {
                        return ower;
                    } else if (member.type == id) {
                        return member;
                    } else if (manager.type == id) {
                        return manager;
                    } else {
                        return limited;
                    }
                }
            }

            public long created_at;
            public int id;
            public long last_visit_at;
            public int project_id;
            public int type;
            public int user_id;
            public String alias = "";
            public UserObject user = new UserObject();

            public Members(JSONObject json) {
                created_at = json.optLong("created_at");
                id = json.optInt("id");
                last_visit_at = json.optLong("last_visit_at");
                project_id = json.optInt("project_id");
                type = json.optInt("type");
                user_id = json.optInt("user_id");
                alias = json.optString("alias");

                if (json.has("user")) {
//                    user = new UserObject(json.optJSONObject("user"));
                }
            }

            public Type getType() {
                for (Type item : Type.values()) {
                    if (item.type == type) {
                        return item;
                    }
                }

                return Type.member;
            }

            public boolean isOwner() {
                return getType() == Type.ower;
            }

            public boolean isMe() {
                return user.isMe();
            }

            public Members(UserObject data) {
                created_at = data.created_at;
                id = data.id;
                last_visit_at = data.last_activity_at;
                project_id = 0;
                type = 0;
                user_id = data.id;
                user = data;
            }

            public Members() {
            }
        }

        public static class TaskDescription implements Serializable {
            public String description = "";
            public String markdown = "";

            public TaskDescription(JSONObject json) throws JSONException {
                description = json.optString("description");
                markdown = json.optString("markdown");
            }

            public TaskDescription(TaskDescription t) {
                description = t.description;
                markdown = t.markdown;
            }

            public TaskDescription() {
            }
        }

        public static class TaskComment extends BaseComment implements Serializable {

            public int taskId;

            public TaskComment(JSONObject json) throws JSONException {
                super(json);

                taskId = json.optInt("taskId");

            }
        }

        public static class UserTaskCount {
            public String done;
            public String processing;
            public String user;

            public UserTaskCount(JSONObject json) throws JSONException {
                done = json.optString("done");
                processing = json.optString("processing");
                user = json.optString("user");
            }
        }

        public static class SingleTask implements Serializable {
            public String content = "";  //任务的 简要
            public long created_at;  //创立的 时间
            public UserObject creator = new UserObject();
            public String creator_id = "";
            public String current_user_role_id = "";
            public UserObject owner = new UserObject();
            public int owner_id;
            public ProjectObject project = new ProjectObject();
            public int project_id;
            public String deadline = "";
            public int status;
            public int priority;
            public long updated_at;
            public int comments;
            public boolean has_description;

            public ArrayList<TopicLabelObject> labels = new ArrayList<>();

            private int number;
            private int id;
            public String description = "";

            public SingleTask(JSONObject json) throws JSONException {
                this(json, false);
            }

            public SingleTask(JSONObject json, boolean useRaw) throws JSONException {
                comments = json.optInt("comments");
                this.content = json.optString("content", "");
                if (!useRaw) {
                    this.content = Html.fromHtml(content).toString();
                }

                created_at = json.optLong("created_at");

                if (json.has("creator")) {
//                    creator = new UserObject(json.optJSONObject("creator"));
                }
                if (json.has("description")) {
                    JSONArray jsonArray = json.optJSONArray("description");
                    if (jsonArray.length() > 0) {
                        description = jsonArray.getString(0).toString();
                    }
                }
                creator_id = json.optString("creator_id");
                current_user_role_id = json.optString("current_user_role_id");
                id = json.optInt("id");
                priority = json.optInt("priority");
//                if (priority >= TaskAddActivity.priorityDrawable.length) {
//                    priority = TaskAddActivity.priorityDrawable.length - 1;
//                }

                if (json.has("owner")) {
//                    owner = new UserObject(json.optJSONObject("owner"));
                }

                owner_id = json.optInt("owner_id");

                if (json.has("project")) {
//                    project = new ProjectObject(json.optJSONObject("project"));
                }

                project_id = json.optInt("project_id");
                status = json.optInt("status");
                updated_at = json.optLong("updated_at");
                deadline = json.optString("deadline");
                has_description = json.optBoolean("has_description", false);
                number = json.optInt("number");

                if (json.has("labels")) {
                    JSONArray jsonLabals = json.optJSONArray("labels");
                    for (int i = 0; i < jsonLabals.length(); ++i) {
                        TopicLabelObject label = new TopicLabelObject(jsonLabals.getJSONObject(i));
                        if (!label.isEmpty()) {
                            labels.add(label);
                        }
                    }
                }
            }

            public SingleTask() {
            }

            public boolean isDone() {
                return status == 2;
            }

            public boolean isEmpty() {
                return id == 0;
            }

            public int getId() {
                return id;
            }

            public void removeLabel(int labelId) {
                for (int i = 0; i < labels.size(); ++i) {
                    if (labels.get(i).id == labelId) {
                        labels.remove(i);
                        return;
                    }
                }
            }

//            public String getHttpRemoveLabal(int labelId) {
//                return String.format("%s/task/%d/label/%d", project.getHttpProjectApi(), id, labelId);
//            }

            public int getNumberValue() {
                return number;
            }

            public String getNumber() {
                return "#" + number;
            }
        }


}
