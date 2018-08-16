package com.system.bhouse.bhouse.Bean;

import java.util.List;

/**
 * Created by Administrator on 2018-08-16.
 * <p>
 * by author wz
 * <p>
 * com.system.bhouse.bhouse.Bean
 */

public class NewsModel {

    /**
     * code : 0
     * body : [{"id":0,"title":"干百风再研目热部质政国","desc":"作划系还许影角角许局说意什今。体听整边集为越海往写没置周层育。收地来其支干界东子","tag":"史业内断区","views":2794,"images":["http://dummyimage.com/200x100/f2797b&text=cx","http://dummyimage.com/200x100/799ef2&text=wlp"]},{"id":1,"title":"红中图都事集历多加马命","desc":"通海边线风格量联验家子然到利片才。收同员到经类务也联军象教接。总家心断用维期政制","tag":"极识展青元识","views":3537,"images":["http://dummyimage.com/200x100/f2797b&text=cx","http://dummyimage.com/200x100/799ef2&text=wlp"]},{"id":2,"title":"至提再区委证须组开万重新安标量集","desc":"应适验始数手育院至龙员深。何究到同动了将少深十百将传商去场置全。","tag":"前易常展更积","views":1423,"images":["http://dummyimage.com/200x100/f2797b&text=cx","http://dummyimage.com/200x100/799ef2&text=wlp"]},{"id":3,"title":"百变养越海专示即大层温力间代正化两动难","desc":"际内想四风备组计效毛没开往人花清。","tag":"例数边","views":2776,"images":["http://dummyimage.com/200x100/f2797b&text=cx","http://dummyimage.com/200x100/799ef2&text=wlp"]},{"id":4,"title":"志满王便观它明周算成打加员验据","desc":"无铁土几月和权七易西路以亲话意最。","tag":"处法龙少","views":781,"images":["http://dummyimage.com/200x100/f2797b&text=cx","http://dummyimage.com/200x100/799ef2&text=wlp"]},{"id":5,"title":"例际北即老消天维问属看计亲声","desc":"值情北北效标价清查者就其会没。决色平布王面统太老特象酸眼给么音。长列严片处物说导","tag":"方值由","views":3358,"images":["http://dummyimage.com/200x100/f2797b&text=cx","http://dummyimage.com/200x100/799ef2&text=wlp","http://dummyimage.com/200x100/c2f279&text=werv"]},{"id":6,"title":"深行山速存气当存很","desc":"支重等消业发现一数片工强务数太。进向品学府见行变联东十我第都素性。两制完目始权复","tag":"白历做公","views":1060,"images":["http://dummyimage.com/200x100/f2797b&text=cx"]},{"id":7,"title":"维界深将型书路完类一难色在社备特","desc":"合光条面头少支他具美定单越切情。已外水证始队集易示精过同整。起必区过完领九内清热","tag":"而己前变及土","views":4110,"images":["http://dummyimage.com/200x100/f2797b&text=cx","http://dummyimage.com/200x100/799ef2&text=wlp","http://dummyimage.com/200x100/c2f279&text=werv"]},{"id":8,"title":"只声其响手高准东算快","desc":"回自地山动条青美向两号专往。许公儿调等体真九龙立确算些这处道非。马代时道强拉集候","tag":"单几国复","views":1855,"images":["http://dummyimage.com/200x100/f2797b&text=cx","http://dummyimage.com/200x100/799ef2&text=wlp"]},{"id":9,"title":"么市角百很查心教后原行装着放观","desc":"流三三除时空世外利规了局增又三。八集因资府往公直话能江几我。军对应内构深等适那经","tag":"别人温","views":2057,"images":["http://dummyimage.com/200x100/f2797b&text=cx","http://dummyimage.com/200x100/799ef2&text=wlp"]}]
     */

    private int code;
    private List<BodyBean> body;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public List<BodyBean> getBody() {
        return body;
    }

    public void setBody(List<BodyBean> body) {
        this.body = body;
    }

    public static class BodyBean {
        /**
         * id : 0
         * title : 干百风再研目热部质政国
         * desc : 作划系还许影角角许局说意什今。体听整边集为越海往写没置周层育。收地来其支干界东子
         * tag : 史业内断区
         * views : 2794
         * images : ["http://dummyimage.com/200x100/f2797b&text=cx","http://dummyimage.com/200x100/799ef2&text=wlp"]
         */

        private int id;
        private String title;
        private String desc;
        private String tag;
        private int views;
        private List<String> images;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public String getTag() {
            return tag;
        }

        public void setTag(String tag) {
            this.tag = tag;
        }

        public int getViews() {
            return views;
        }

        public void setViews(int views) {
            this.views = views;
        }

        public List<String> getImages() {
            return images;
        }

        public void setImages(List<String> images) {
            this.images = images;
        }
    }
}
