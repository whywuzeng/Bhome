package com.system.bhouse.Common.filewidget.maopao;

import java.util.List;

/**
 * Created by Administrator on 2018-11-27.
 * <p>
 * by author wz
 * <p>
 * com.system.bhouse.Common.filewidget.maopao
 */
public class maopaoBean {
    /**
     * code : 0
     * data : [{"comment_list":[{"content":"这个评论的，也感觉比较牛逼","created_at":1539349885000,"id":6605,"owner":{"avatar":"https://dn-coding-net-production-static.codehub.cn/a800c940-5d1b-43e6-a8a2-d48612d16b42.jpg ","company":"","created_at":1470293422000,"fans_count":0,"follow":false,"followed":false,"follows_count":0,"global_key":"shouji","gravatar":"https://dn-coding-net-avatar.codehub.cn/ebc2f1c1-0e47-4a3c-91f7-bc0efb8c525c.jpg ","id":252055,"introduction":"","is_member":0,"is_tencent_user":false,"last_logined_at":1542852327000,"lavatar":"https://dn-coding-net-production-static.codehub.cn/a800c940-5d1b-43e6-a8a2-d48612d16b42.jpg ","location":"","name":"shouji","name_pinyin":"","path":"/u/shouji","sex":2,"slogan":"","status":1,"tweets_count":0,"updated_at":1542852329000,"vip":3,"vip_expired_at":1546617600000,"website":""},"owner_id":252055,"tweet_id":33182}],"comments":1,"created_at":1539349844000,"editable":true,"id":33182,"owner":{"avatar":"https://dn-coding-net-production-static.codehub.cn/a800c940-5d1b-43e6-a8a2-d48612d16b42.jpg ","company":"","created_at":1470293422000,"fans_count":0,"follow":false,"followed":false,"follows_count":0,"global_key":"shouji","gravatar":"https://dn-coding-net-avatar.codehub.cn/ebc2f1c1-0e47-4a3c-91f7-bc0efb8c525c.jpg","id":252055,"introduction":"","is_member":0,"is_tencent_user":false,"last_logined_at":1542852327000,"lavatar":"https://dn-coding-net-production-static.codehub.cn/a800c940-5d1b-43e6-a8a2-d48612d16b42.jpg ","location":"","name":"shouji","name_pinyin":"","path":"/u/shouji","sex":2,"slogan":"","status":1,"tweets_count":0,"updated_at":1542852329000,"vip":3,"vip_expired_at":1546617600000,"website":""},"owner_id":252055,"path":"/u/shouji/p/zhrgi/setting/notice/33182","project_id":3799703,"updated_at":1539349885000}]
     */

    private int code;
    private List<DataBean> data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * comment_list : [{"content":"这个评论的，也感觉比较牛逼","created_at":1539349885000,"id":6605,"owner":{"avatar":"https://dn-coding-net-production-static.codehub.cn/a800c940-5d1b-43e6-a8a2-d48612d16b42.jpg ","company":"","created_at":1470293422000,"fans_count":0,"follow":false,"followed":false,"follows_count":0,"global_key":"shouji","gravatar":"https://dn-coding-net-avatar.codehub.cn/ebc2f1c1-0e47-4a3c-91f7-bc0efb8c525c.jpg ","id":252055,"introduction":"","is_member":0,"is_tencent_user":false,"last_logined_at":1542852327000,"lavatar":"https://dn-coding-net-production-static.codehub.cn/a800c940-5d1b-43e6-a8a2-d48612d16b42.jpg ","location":"","name":"shouji","name_pinyin":"","path":"/u/shouji","sex":2,"slogan":"","status":1,"tweets_count":0,"updated_at":1542852329000,"vip":3,"vip_expired_at":1546617600000,"website":""},"owner_id":252055,"tweet_id":33182}]
         * comments : 1
         * created_at : 1539349844000
         * editable : true
         * id : 33182
         * owner : {"avatar":"https://dn-coding-net-production-static.codehub.cn/a800c940-5d1b-43e6-a8a2-d48612d16b42.jpg ","company":"","created_at":1470293422000,"fans_count":0,"follow":false,"followed":false,"follows_count":0,"global_key":"shouji","gravatar":"https://dn-coding-net-avatar.codehub.cn/ebc2f1c1-0e47-4a3c-91f7-bc0efb8c525c.jpg","id":252055,"introduction":"","is_member":0,"is_tencent_user":false,"last_logined_at":1542852327000,"lavatar":"https://dn-coding-net-production-static.codehub.cn/a800c940-5d1b-43e6-a8a2-d48612d16b42.jpg ","location":"","name":"shouji","name_pinyin":"","path":"/u/shouji","sex":2,"slogan":"","status":1,"tweets_count":0,"updated_at":1542852329000,"vip":3,"vip_expired_at":1546617600000,"website":""}
         * owner_id : 252055
         * path : /u/shouji/p/zhrgi/setting/notice/33182
         * project_id : 3799703
         * updated_at : 1539349885000
         */

        private int comments;
        private long created_at;
        private String content;
        private boolean editable;
        private int id;
        private OwnerBean owner;
        private int owner_id;
        private String path;
        private int project_id;
        private long updated_at;
        private List<CommentListBean> comment_list;

        public int getComments() {
            return comments;
        }

        public void setComments(int comments) {
            this.comments = comments;
        }

        public long getCreated_at() {
            return created_at;
        }

        public void setCreated_at(long created_at) {
            this.created_at = created_at;
        }

        public boolean isEditable() {
            return editable;
        }

        public void setEditable(boolean editable) {
            this.editable = editable;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public OwnerBean getOwner() {
            return owner;
        }

        public void setOwner(OwnerBean owner) {
            this.owner = owner;
        }

        public int getOwner_id() {
            return owner_id;
        }

        public void setOwner_id(int owner_id) {
            this.owner_id = owner_id;
        }

        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }

        public int getProject_id() {
            return project_id;
        }

        public void setProject_id(int project_id) {
            this.project_id = project_id;
        }

        public long getUpdated_at() {
            return updated_at;
        }

        public void setUpdated_at(long updated_at) {
            this.updated_at = updated_at;
        }

        public List<CommentListBean> getComment_list() {
            return comment_list;
        }

        public void setComment_list(List<CommentListBean> comment_list) {
            this.comment_list = comment_list;
        }

        public static class OwnerBean {
            /**
             * avatar : https://dn-coding-net-production-static.codehub.cn/a800c940-5d1b-43e6-a8a2-d48612d16b42.jpg
             * company :
             * created_at : 1470293422000
             * fans_count : 0
             * follow : false
             * followed : false
             * follows_count : 0
             * global_key : shouji
             * gravatar : https://dn-coding-net-avatar.codehub.cn/ebc2f1c1-0e47-4a3c-91f7-bc0efb8c525c.jpg
             * id : 252055
             * introduction :
             * is_member : 0
             * is_tencent_user : false
             * last_logined_at : 1542852327000
             * lavatar : https://dn-coding-net-production-static.codehub.cn/a800c940-5d1b-43e6-a8a2-d48612d16b42.jpg
             * location :
             * name : shouji
             * name_pinyin :
             * path : /u/shouji
             * sex : 2
             * slogan :
             * status : 1
             * tweets_count : 0
             * updated_at : 1542852329000
             * vip : 3
             * vip_expired_at : 1546617600000
             * website :
             */

            private String avatar;
            private String company;
            private long created_at;
            private int fans_count;
            private boolean follow;
            private boolean followed;
            private int follows_count;
            private String global_key;
            private String gravatar;
            private int id;
            private String introduction;
            private int is_member;
            private boolean is_tencent_user;
            private long last_logined_at;
            private String lavatar;
            private String location;
            private String name;
            private String name_pinyin;
            private String path;
            private int sex;
            private String slogan;
            private int status;
            private int tweets_count;
            private long updated_at;
            private int vip;
            private long vip_expired_at;
            private String website;

            public String getAvatar() {
                return avatar;
            }

            public void setAvatar(String avatar) {
                this.avatar = avatar;
            }

            public String getCompany() {
                return company;
            }

            public void setCompany(String company) {
                this.company = company;
            }

            public long getCreated_at() {
                return created_at;
            }

            public void setCreated_at(long created_at) {
                this.created_at = created_at;
            }

            public int getFans_count() {
                return fans_count;
            }

            public void setFans_count(int fans_count) {
                this.fans_count = fans_count;
            }

            public boolean isFollow() {
                return follow;
            }

            public void setFollow(boolean follow) {
                this.follow = follow;
            }

            public boolean isFollowed() {
                return followed;
            }

            public void setFollowed(boolean followed) {
                this.followed = followed;
            }

            public int getFollows_count() {
                return follows_count;
            }

            public void setFollows_count(int follows_count) {
                this.follows_count = follows_count;
            }

            public String getGlobal_key() {
                return global_key;
            }

            public void setGlobal_key(String global_key) {
                this.global_key = global_key;
            }

            public String getGravatar() {
                return gravatar;
            }

            public void setGravatar(String gravatar) {
                this.gravatar = gravatar;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getIntroduction() {
                return introduction;
            }

            public void setIntroduction(String introduction) {
                this.introduction = introduction;
            }

            public int getIs_member() {
                return is_member;
            }

            public void setIs_member(int is_member) {
                this.is_member = is_member;
            }

            public boolean isIs_tencent_user() {
                return is_tencent_user;
            }

            public void setIs_tencent_user(boolean is_tencent_user) {
                this.is_tencent_user = is_tencent_user;
            }

            public long getLast_logined_at() {
                return last_logined_at;
            }

            public void setLast_logined_at(long last_logined_at) {
                this.last_logined_at = last_logined_at;
            }

            public String getLavatar() {
                return lavatar;
            }

            public void setLavatar(String lavatar) {
                this.lavatar = lavatar;
            }

            public String getLocation() {
                return location;
            }

            public void setLocation(String location) {
                this.location = location;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getName_pinyin() {
                return name_pinyin;
            }

            public void setName_pinyin(String name_pinyin) {
                this.name_pinyin = name_pinyin;
            }

            public String getPath() {
                return path;
            }

            public void setPath(String path) {
                this.path = path;
            }

            public int getSex() {
                return sex;
            }

            public void setSex(int sex) {
                this.sex = sex;
            }

            public String getSlogan() {
                return slogan;
            }

            public void setSlogan(String slogan) {
                this.slogan = slogan;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public int getTweets_count() {
                return tweets_count;
            }

            public void setTweets_count(int tweets_count) {
                this.tweets_count = tweets_count;
            }

            public long getUpdated_at() {
                return updated_at;
            }

            public void setUpdated_at(long updated_at) {
                this.updated_at = updated_at;
            }

            public int getVip() {
                return vip;
            }

            public void setVip(int vip) {
                this.vip = vip;
            }

            public long getVip_expired_at() {
                return vip_expired_at;
            }

            public void setVip_expired_at(long vip_expired_at) {
                this.vip_expired_at = vip_expired_at;
            }

            public String getWebsite() {
                return website;
            }

            public void setWebsite(String website) {
                this.website = website;
            }
        }

        public static class CommentListBean {
            /**
             * content : 这个评论的，也感觉比较牛逼
             * created_at : 1539349885000
             * id : 6605
             * owner : {"avatar":"https://dn-coding-net-production-static.codehub.cn/a800c940-5d1b-43e6-a8a2-d48612d16b42.jpg ","company":"","created_at":1470293422000,"fans_count":0,"follow":false,"followed":false,"follows_count":0,"global_key":"shouji","gravatar":"https://dn-coding-net-avatar.codehub.cn/ebc2f1c1-0e47-4a3c-91f7-bc0efb8c525c.jpg ","id":252055,"introduction":"","is_member":0,"is_tencent_user":false,"last_logined_at":1542852327000,"lavatar":"https://dn-coding-net-production-static.codehub.cn/a800c940-5d1b-43e6-a8a2-d48612d16b42.jpg ","location":"","name":"shouji","name_pinyin":"","path":"/u/shouji","sex":2,"slogan":"","status":1,"tweets_count":0,"updated_at":1542852329000,"vip":3,"vip_expired_at":1546617600000,"website":""}
             * owner_id : 252055
             * tweet_id : 33182
             */

            private String content;
            private long created_at;
            private int id;
            private OwnerBeanX owner;
            private int owner_id;
            private int tweet_id;

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public long getCreated_at() {
                return created_at;
            }

            public void setCreated_at(long created_at) {
                this.created_at = created_at;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public OwnerBeanX getOwner() {
                return owner;
            }

            public void setOwner(OwnerBeanX owner) {
                this.owner = owner;
            }

            public int getOwner_id() {
                return owner_id;
            }

            public void setOwner_id(int owner_id) {
                this.owner_id = owner_id;
            }

            public int getTweet_id() {
                return tweet_id;
            }

            public void setTweet_id(int tweet_id) {
                this.tweet_id = tweet_id;
            }

            public static class OwnerBeanX {
                /**
                 * avatar : https://dn-coding-net-production-static.codehub.cn/a800c940-5d1b-43e6-a8a2-d48612d16b42.jpg
                 * company :
                 * created_at : 1470293422000
                 * fans_count : 0
                 * follow : false
                 * followed : false
                 * follows_count : 0
                 * global_key : shouji
                 * gravatar : https://dn-coding-net-avatar.codehub.cn/ebc2f1c1-0e47-4a3c-91f7-bc0efb8c525c.jpg
                 * id : 252055
                 * introduction :
                 * is_member : 0
                 * is_tencent_user : false
                 * last_logined_at : 1542852327000
                 * lavatar : https://dn-coding-net-production-static.codehub.cn/a800c940-5d1b-43e6-a8a2-d48612d16b42.jpg
                 * location :
                 * name : shouji
                 * name_pinyin :
                 * path : /u/shouji
                 * sex : 2
                 * slogan :
                 * status : 1
                 * tweets_count : 0
                 * updated_at : 1542852329000
                 * vip : 3
                 * vip_expired_at : 1546617600000
                 * website :
                 */

                private String avatar;
                private String company;
                private long created_at;
                private int fans_count;
                private boolean follow;
                private boolean followed;
                private int follows_count;
                private String global_key;
                private String gravatar;
                private int id;
                private String introduction;
                private int is_member;
                private boolean is_tencent_user;
                private long last_logined_at;
                private String lavatar;
                private String location;
                private String name;
                private String name_pinyin;
                private String path;
                private int sex;
                private String slogan;
                private int status;
                private int tweets_count;
                private long updated_at;
                private int vip;
                private long vip_expired_at;
                private String website;

                public String getAvatar() {
                    return avatar;
                }

                public void setAvatar(String avatar) {
                    this.avatar = avatar;
                }

                public String getCompany() {
                    return company;
                }

                public void setCompany(String company) {
                    this.company = company;
                }

                public long getCreated_at() {
                    return created_at;
                }

                public void setCreated_at(long created_at) {
                    this.created_at = created_at;
                }

                public int getFans_count() {
                    return fans_count;
                }

                public void setFans_count(int fans_count) {
                    this.fans_count = fans_count;
                }

                public boolean isFollow() {
                    return follow;
                }

                public void setFollow(boolean follow) {
                    this.follow = follow;
                }

                public boolean isFollowed() {
                    return followed;
                }

                public void setFollowed(boolean followed) {
                    this.followed = followed;
                }

                public int getFollows_count() {
                    return follows_count;
                }

                public void setFollows_count(int follows_count) {
                    this.follows_count = follows_count;
                }

                public String getGlobal_key() {
                    return global_key;
                }

                public void setGlobal_key(String global_key) {
                    this.global_key = global_key;
                }

                public String getGravatar() {
                    return gravatar;
                }

                public void setGravatar(String gravatar) {
                    this.gravatar = gravatar;
                }

                public int getId() {
                    return id;
                }

                public void setId(int id) {
                    this.id = id;
                }

                public String getIntroduction() {
                    return introduction;
                }

                public void setIntroduction(String introduction) {
                    this.introduction = introduction;
                }

                public int getIs_member() {
                    return is_member;
                }

                public void setIs_member(int is_member) {
                    this.is_member = is_member;
                }

                public boolean isIs_tencent_user() {
                    return is_tencent_user;
                }

                public void setIs_tencent_user(boolean is_tencent_user) {
                    this.is_tencent_user = is_tencent_user;
                }

                public long getLast_logined_at() {
                    return last_logined_at;
                }

                public void setLast_logined_at(long last_logined_at) {
                    this.last_logined_at = last_logined_at;
                }

                public String getLavatar() {
                    return lavatar;
                }

                public void setLavatar(String lavatar) {
                    this.lavatar = lavatar;
                }

                public String getLocation() {
                    return location;
                }

                public void setLocation(String location) {
                    this.location = location;
                }

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public String getName_pinyin() {
                    return name_pinyin;
                }

                public void setName_pinyin(String name_pinyin) {
                    this.name_pinyin = name_pinyin;
                }

                public String getPath() {
                    return path;
                }

                public void setPath(String path) {
                    this.path = path;
                }

                public int getSex() {
                    return sex;
                }

                public void setSex(int sex) {
                    this.sex = sex;
                }

                public String getSlogan() {
                    return slogan;
                }

                public void setSlogan(String slogan) {
                    this.slogan = slogan;
                }

                public int getStatus() {
                    return status;
                }

                public void setStatus(int status) {
                    this.status = status;
                }

                public int getTweets_count() {
                    return tweets_count;
                }

                public void setTweets_count(int tweets_count) {
                    this.tweets_count = tweets_count;
                }

                public long getUpdated_at() {
                    return updated_at;
                }

                public void setUpdated_at(long updated_at) {
                    this.updated_at = updated_at;
                }

                public int getVip() {
                    return vip;
                }

                public void setVip(int vip) {
                    this.vip = vip;
                }

                public long getVip_expired_at() {
                    return vip_expired_at;
                }

                public void setVip_expired_at(long vip_expired_at) {
                    this.vip_expired_at = vip_expired_at;
                }

                public String getWebsite() {
                    return website;
                }

                public void setWebsite(String website) {
                    this.website = website;
                }
            }
        }
    }
    /**
     * "content": "<p><a href="" rel="nofollow noopener noreferrer">这个是链接</a><a href="https://dn-coding-net-production-pp.codehub.cn/399ba145-b936-478d-9ad0-419a9a521fda.jpg" target="_blank" class="bubble-markdown-image-link" rel="nofollow noopener noreferrer"><img src="https://dn-coding-net-production-pp.codehub.cn/399ba145-b936-478d-9ad0-419a9a521fda.jpg" alt="图片" class="bubble-markdown-image" origin-src="https://dn-coding-net-production-pp.codehub.cn/399ba145-b936-478d-9ad0-419a9a521fda.jpg"></a><br> 图片直接就上传了。 <strong>这个文字</strong></p>
     <ul>
     <li>这个列表是可以的</li>
     <li>项目这个
     <blockquote>
     <p>这个是引用</p>
     </blockquote> </li>
     </ul>
     <pre><code>zhegecode 输入代码，这个代码的效果还是阔以的。很强。
     </code></pre>
     <h2 id="user-content-这个是标题">这个是标题</h2>
     <hr>
     <p>分割线。。。。。</p>",
     */


}
