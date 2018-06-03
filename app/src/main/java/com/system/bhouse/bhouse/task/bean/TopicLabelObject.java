package com.system.bhouse.bhouse.task.bean;

import android.graphics.Color;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by Neutra on 2015/4/23.
 * 标签的数据结构
 */
public class TopicLabelObject implements Serializable {
    public int id;
    public int count;
    public String name;
    public int color;

    public TopicLabelObject(JSONObject json) throws JSONException {
        id = json.optInt("id");
        name = json.optString("name", "");
        count = json.optInt("count", 0);
        String colorString = json.optString("color", "#222222");
        try {
            color = Color.parseColor(colorString);
        } catch (IllegalArgumentException e){
            color = 0xff222222;
        }
    }

    public TopicLabelObject(int id, String name, int color) {
        this.id = id;
        this.name = name;
        this.color = color;
    }

    public TopicLabelObject(TopicLabelObject src) {
        id = src.id;
        name = src.name;
        count = src.count;
        color = src.getColor();
    }

    public int getColor() {
        return color;
    }

    public boolean isEmpty() {
        return name == null || name.isEmpty();
    }
}
