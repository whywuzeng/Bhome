package com.system.bhouse.bhouse.task.bean;


import com.system.bhouse.base.App;

import org.json.JSONObject;

import java.io.Serializable;

//@Entity
public class BaseComment implements Serializable {
//    @Id
    public int id; // 9291,
//    @Property(nameInDb = "CONTENT")
    public String content = "";
//    @Property(nameInDb = "CREATED_AT")
    public long created_at; // 1408614375604,
    public DynamicObject.Owner owner = new DynamicObject.Owner();
//    @Property(nameInDb = "OWNER_ID")
    public int owner_id; // 8205,

    public BaseComment(JSONObject json) {
        if (json == null) {
            return;
        }

        content = json.optString("content");
        created_at = json.optLong("created_at");
        id = json.optInt("id");

        if (json.has("owner")) {

//            owner = new DynamicObject.Owner(json.optJSONObject("owner"));

        } else if (json.has("author")) {

//            owner = new DynamicObject.Owner(json.optJSONObject("author"));

        }

        if (json.has("owner_id")) {
            owner_id = json.optInt("owner_id");
        }
    }

    public BaseComment(int i) {
//        content = dynamic.getComment();
//        id = dynamic.id;
//        created_at = dynamic.created_at;
//        owner = dynamic.getOwner();
//        owner_id = 0;
    }

    public BaseComment() {
    }

    public boolean isEmpty() {
        return id == 0;
    }

    //判断是什么
    public boolean isMy() {
        if (owner_id != 0) {
            return App.sUserObject.id == owner_id;
        } else {
//            return App.sUserObject.global_key.equals(owner.global_key);
            return true;
        }
    }
}