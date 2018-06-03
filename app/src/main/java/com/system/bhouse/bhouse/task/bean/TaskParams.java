package com.system.bhouse.bhouse.task.bean;

import java.io.Serializable;

/**
 * Created by chenchao on 15/7/7.
 * 任务数据
 */
public class TaskParams implements Serializable{
  public   String content = "";
   public int status;
   public int ownerId;
   public int priority;
   public String deadline = "";
    public int orderId;

    public UserObject owner;

    public TaskParams()
    {
        this.orderId=(int) (Math.random() * 1000000000);
    }


    public TaskParams(TaskObject.SingleTask singleTask){
        this.orderId=(int) (Math.random() * 1000000000);
        content = singleTask.content;
        status = singleTask.status;
        ownerId = singleTask.owner_id;
        priority = singleTask.priority;
        owner = singleTask.owner;
        deadline = singleTask.deadline;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(int ownerId) {
        this.ownerId = ownerId;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public String getDeadline() {
        return deadline;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }
}
