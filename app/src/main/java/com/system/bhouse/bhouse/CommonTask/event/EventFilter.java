package com.system.bhouse.bhouse.CommonTask.event;

/**
 * Created by chenchao on 16/9/18.
 */
public class EventFilter {
    public EventFilter(Object obj) {
        if (obj == null) {
            return;
        }
        try {
            this.index = Integer.parseInt(obj.toString());
        } catch (Exception e) {
//            Global.errorLog(e);
        }
    }

    public int index;//0 我的项目，1我的任务
}
