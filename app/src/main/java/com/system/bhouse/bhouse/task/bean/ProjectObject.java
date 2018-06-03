package com.system.bhouse.bhouse.task.bean;

import java.io.Serializable;

/**
 * Created by cc191954 on 14-8-8.
 */

public class ProjectObject implements Serializable {

    public String name = "";
    public int owner_id;
    public String owner_user_home = "";
    public String owner_user_name = "";
    public String owner_user_picture = "";
    public String current_user_role = "";
    public String description = "";
    public String icon = "";
    public int fork_count;
    public boolean forked;
    public long created_at;
    public int star_count;
    public boolean stared;
    public int status;
    public int un_read_activities_count;
    public long update_at;
    public int watch_count;
    public boolean watched;
    private int id;
    public boolean is_public;
    private boolean pin;
    private int type;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getOwner_id() {
        return owner_id;
    }

    public void setOwner_id(int owner_id) {
        this.owner_id = owner_id;
    }

    public String getOwner_user_home() {
        return owner_user_home;
    }

    public void setOwner_user_home(String owner_user_home) {
        this.owner_user_home = owner_user_home;
    }

    public String getOwner_user_name() {
        return owner_user_name;
    }

    public void setOwner_user_name(String owner_user_name) {
        this.owner_user_name = owner_user_name;
    }

    public String getOwner_user_picture() {
        return owner_user_picture;
    }

    public void setOwner_user_picture(String owner_user_picture) {
        this.owner_user_picture = owner_user_picture;
    }

    public String getCurrent_user_role() {
        return current_user_role;
    }

    public void setCurrent_user_role(String current_user_role) {
        this.current_user_role = current_user_role;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public int getFork_count() {
        return fork_count;
    }

    public void setFork_count(int fork_count) {
        this.fork_count = fork_count;
    }

    public boolean isForked() {
        return forked;
    }

    public void setForked(boolean forked) {
        this.forked = forked;
    }

    public long getCreated_at() {
        return created_at;
    }

    public void setCreated_at(long created_at) {
        this.created_at = created_at;
    }

    public int getStar_count() {
        return star_count;
    }

    public void setStar_count(int star_count) {
        this.star_count = star_count;
    }

    public boolean isStared() {
        return stared;
    }

    public void setStared(boolean stared) {
        this.stared = stared;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getUn_read_activities_count() {
        return un_read_activities_count;
    }

    public void setUn_read_activities_count(int un_read_activities_count) {
        this.un_read_activities_count = un_read_activities_count;
    }

    public long getUpdate_at() {
        return update_at;
    }

    public void setUpdate_at(long update_at) {
        this.update_at = update_at;
    }

    public int getWatch_count() {
        return watch_count;
    }

    public void setWatch_count(int watch_count) {
        this.watch_count = watch_count;
    }

    public boolean isWatched() {
        return watched;
    }

    public void setWatched(boolean watched) {
        this.watched = watched;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean is_public() {
        return is_public;
    }

    public void setIs_public(boolean is_public) {
        this.is_public = is_public;
    }

    public boolean isPin() {
        return pin;
    }

    public void setPin(boolean pin) {
        this.pin = pin;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
