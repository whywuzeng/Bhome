package com.system.bhouse.Common.message.bean;

import com.system.bhouse.bhouse.task.bean.UserObject;

import java.io.Serializable;

/**
 * Created by Administrator on 2017-12-19.
 */

public class Message {
    public static class MessageObject implements Serializable {

        public String content = "";
        public int count = 0;
        public long created_at = 0;
        public UserObject friend = new UserObject();
        private int id = 0;
        public int read_at;
        public UserObject sender = new UserObject();
        public int status;
        public int unreadCount;
        public int duration;
        public String file;
        public int type;
        public int played;
        public String extra;

        public MessageObject() {
        }

        public int getId() {
            return id;
        }
    }
}
