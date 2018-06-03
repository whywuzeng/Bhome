package com.system.bhouse.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * ClassName: NeteastNewsSummary<p>
 * Author:oubowu<p>
 * Fuction: 网易新闻详情<p>
 * CreateDate:2016/2/13 20:09<p>
 * UpdateUser:<p>
 * UpdateDate:<p>
 */
public class NeteastNewsSummary {
    @SerializedName("postid")
    public String postid;
    @SerializedName("hasCover")
    public boolean hasCover;
    @SerializedName("hasHead")
    public int hasHead;
    @SerializedName("replyCount")
    public int replyCount;
    @SerializedName("hasImg")
    public int hasImg;
    @SerializedName("digest")
    public String digest;
    @SerializedName("hasIcon")
    public boolean hasIcon;
    @SerializedName("docid")
    public String docid;
    @SerializedName("title")
    public String title;
    @SerializedName("order")
    public int order;
    @SerializedName("priority")
    public int priority;
    @SerializedName("lmodify")
    public String lmodify;
    @SerializedName("boardid")
    public String boardid;
    @SerializedName("photosetID")
    public String photosetID;
    @SerializedName("template")
    public String template;
    @SerializedName("votecount")
    public int votecount;
    @SerializedName("skipID")
    public String skipID;
    @SerializedName("alias")
    public String alias;
    @SerializedName("skipType")
    public String skipType;
    @SerializedName("cid")
    public String cid;
    @SerializedName("hasAD")
    public int hasAD;
    @SerializedName("imgsrc")
    public String imgsrc;
    @SerializedName("tname")
    public String tname;
    @SerializedName("ename")
    public String ename;
    @SerializedName("ptime")
    public String ptime;
    /**
     * title : 哈萨克斯坦中亚在建第1高楼爆炸起火
     * tag : photoset
     * imgsrc : http://img5.cache.netease.com/3g/2016/2/13/2016021318005710210.jpg
     * subtitle :
     * url : 00AN0001|110630
     */

    @SerializedName("ads")
    public List<AdsEntity> ads;
    /**
     * imgsrc : http://img5.cache.netease.com/3g/2016/2/13/201602131446132dc50.jpg
     */

    @SerializedName("imgextra")
    public List<ImgextraEntity> imgextra;

    public static class AdsEntity {
        @SerializedName("title")
        public String title;
        @SerializedName("tag")
        public String tag;
        @SerializedName("imgsrc")
        public String imgsrc;
        @SerializedName("subtitle")
        public String subtitle;
        @SerializedName("url")
        public String url;
    }

    public static class ImgextraEntity {
        @SerializedName("imgsrc")
        public String imgsrc;
    }
}
