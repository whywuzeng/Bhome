package com.system.bhouse.bhouse.phone.utlis;

import org.json.JSONArray;

public class CheckRule {
    boolean avaiable = true;
    boolean permhasion;
    boolean startWork;
    boolean stopWork;
    String turnName;
    JSONArray workTimeDesc;

    public CheckRule(boolean z, String str, JSONArray jSONArray) {
        this.permhasion = z;
        this.turnName = str;
        this.workTimeDesc = jSONArray;
    }

    public boolean getPermission() {
        return this.permhasion;
    }

    public String getTurnName() {
        return this.turnName;
    }

    public JSONArray getWorkTimeDesc() {
        return this.workTimeDesc;
    }

    public boolean haveStartWork() {
        return this.startWork;
    }

    public boolean haveStopWork() {
        return this.stopWork;
    }

    public boolean isAvaiable() {
        return this.avaiable;
    }

    public void setAvaiable(boolean z) {
        this.avaiable = z;
    }

    public void setPermission(boolean z) {
        this.permhasion = z;
    }

    public void setStartWork(boolean z) {
        this.startWork = z;
    }

    public void setStopWork(boolean z) {
        this.stopWork = z;
    }

    public void setTurnName(String str) {
        this.turnName = str;
    }

    public void setWorkTimeDesc(JSONArray jSONArray) {
        this.workTimeDesc = jSONArray;
    }
}
