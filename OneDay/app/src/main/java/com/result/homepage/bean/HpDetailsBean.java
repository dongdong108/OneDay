package com.result.homepage.bean;

import java.util.List;

/**
 * autour: 刘东东
 * date: 2016/12/19 15:41
 * update: 2016/12/19
 */
public class HpDetailsBean {


    /**
     * reason : success
     * result : [{"e_id":"14046","title":"元朝颁行著名天文学家郭守敬的《授时历》","content":"    在736年前的今天，1280年12月19日 (农历冬月廿六)，元朝颁行著名天文学家郭守敬的《授时历》。\r\n    郭守敬(1231-1316)，中国元朝的大天文学家、数学家、水利专家和仪器制造家。字若思，顺德邢台(今河北邢台)人，汉族。生于元太宗三年，卒于元仁宗延祐二年。\r\n    郭守敬幼承祖父郭荣家学，攻研天文、算学、水利。至元十三年(公元1276年)元世祖忽必烈攻下南宋首都临安，在统一前夕，命令制订新历法，由张文谦等主持成立新的治历机构太史局。太史局由王恂负责，郭守敬辅助。在学术上则王恂主推算，郭主制仪和观测。\r\n    至元十五年(或十六年)，太史局改称太史院，王恂任太史令，郭守敬为同知太史院事，建立天文台。当时，有杨恭懿等来参予共事。经过四年努力，终于在至元十七年编出新历，经忽必烈定名为《授时历》。\r\n    《授时历》是中国古代一部很精良的历法。王恂、郭守敬等人曾研究分析汉代以来的四十多家历法，吸取各历之长，力主制历应\u201c明历之理\u201d(王恂)和\u201c历之本在于测验，而测验之器莫先仪表\u201d(郭守敬)，采取理论与实践相结合的科学态度，取得许多重要成就。\r\n\r\n","picNo":"2","picUrl":[{"pic_title":"","id":1,"url":"http://images.juheapi.com/history/14046_1.jpg"},{"pic_title":"","id":2,"url":"http://images.juheapi.com/history/14046_2.jpg"}]}]
     * error_code : 0
     */

    private String reason;
    private int error_code;
    /**
     * e_id : 14046
     * title : 元朝颁行著名天文学家郭守敬的《授时历》
     * content :     在736年前的今天，1280年12月19日 (农历冬月廿六)，元朝颁行著名天文学家郭守敬的《授时历》。
     郭守敬(1231-1316)，中国元朝的大天文学家、数学家、水利专家和仪器制造家。字若思，顺德邢台(今河北邢台)人，汉族。生于元太宗三年，卒于元仁宗延祐二年。
     郭守敬幼承祖父郭荣家学，攻研天文、算学、水利。至元十三年(公元1276年)元世祖忽必烈攻下南宋首都临安，在统一前夕，命令制订新历法，由张文谦等主持成立新的治历机构太史局。太史局由王恂负责，郭守敬辅助。在学术上则王恂主推算，郭主制仪和观测。
     至元十五年(或十六年)，太史局改称太史院，王恂任太史令，郭守敬为同知太史院事，建立天文台。当时，有杨恭懿等来参予共事。经过四年努力，终于在至元十七年编出新历，经忽必烈定名为《授时历》。
     《授时历》是中国古代一部很精良的历法。王恂、郭守敬等人曾研究分析汉代以来的四十多家历法，吸取各历之长，力主制历应“明历之理”(王恂)和“历之本在于测验，而测验之器莫先仪表”(郭守敬)，采取理论与实践相结合的科学态度，取得许多重要成就。


     * picNo : 2
     * picUrl : [{"pic_title":"","id":1,"url":"http://images.juheapi.com/history/14046_1.jpg"},{"pic_title":"","id":2,"url":"http://images.juheapi.com/history/14046_2.jpg"}]
     */

    private List<ResultBean> result;

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public int getError_code() {
        return error_code;
    }

    public void setError_code(int error_code) {
        this.error_code = error_code;
    }

    public List<ResultBean> getResult() {
        return result;
    }

    public void setResult(List<ResultBean> result) {
        this.result = result;
    }

    public static class ResultBean {
        private String e_id;
        private String title;
        private String content;
        private String picNo;
        /**
         * pic_title :
         * id : 1
         * url : http://images.juheapi.com/history/14046_1.jpg
         */

        private List<PicUrlBean> picUrl;

        public String getE_id() {
            return e_id;
        }

        public void setE_id(String e_id) {
            this.e_id = e_id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getPicNo() {
            return picNo;
        }

        public void setPicNo(String picNo) {
            this.picNo = picNo;
        }

        public List<PicUrlBean> getPicUrl() {
            return picUrl;
        }

        public void setPicUrl(List<PicUrlBean> picUrl) {
            this.picUrl = picUrl;
        }

        public static class PicUrlBean {
            private String pic_title;
            private int id;
            private String url;

            public String getPic_title() {
                return pic_title;
            }

            public void setPic_title(String pic_title) {
                this.pic_title = pic_title;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }
        }
    }
}
