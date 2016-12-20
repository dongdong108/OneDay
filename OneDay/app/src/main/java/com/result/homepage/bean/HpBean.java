package com.result.homepage.bean;

import java.util.List;

/**
 * autour: 刘东东
 * date: 2016/12/16 16:42
 * update: 2016/12/16
 */
public class HpBean {


    /**
     * error_code : 0
     * reason : success
     * result : [{"date":"1280年12月19日","day":"12/19","e_id":"14046","title":"元朝颁行著名天文学家郭守敬的《授时历》"},{"date":"1742年12月19日","day":"12/19","e_id":"14047","title":"瑞典著名化学家舍勒诞辰"},{"date":"1793年12月19日","day":"12/19","e_id":"14048","title":"土伦之役"},{"date":"1893年12月19日","day":"12/19","e_id":"14049","title":"清洋务运动首领李鸿章在天津开办医学堂"},{"date":"1903年12月19日","day":"12/19","e_id":"14050","title":"《中国白话报》在上海创刊"},{"date":"1906年12月19日","day":"12/19","e_id":"14051","title":"勃列日涅夫诞辰"},{"date":"1912年12月19日","day":"12/19","e_id":"14052","title":"荣氏兄弟创立福新面粉公司"},{"date":"1916年12月19日","day":"12/19","e_id":"14053","title":"凡尔登战役宣告结束"},{"date":"1945年12月19日","day":"12/19","e_id":"14054","title":"越南、老挝、柬埔寨抗法救国战争全面爆发"},{"date":"1955年12月19日","day":"12/19","e_id":"14055","title":"新中国制糖厂建成投产"},{"date":"1956年12月19日","day":"12/19","e_id":"14056","title":"中国摄影家协会成立40年"},{"date":"1957年12月19日","day":"12/19","e_id":"14057","title":"欧洲各国同意在欧洲部署核武器"},{"date":"1972年12月19日","day":"12/19","e_id":"14058","title":"最后一次\u201c阿波罗\u201d登月计划结束"},{"date":"1979年12月19日","day":"12/19","e_id":"14059","title":"日本学者首次确定致癌遗传基因结构"},{"date":"1984年12月19日","day":"12/19","e_id":"14060","title":"《中英关于香港问题的联合声明》签署"},{"date":"1984年12月19日","day":"12/19","e_id":"14061","title":"美国正式退出联合国教科文组织"},{"date":"1985年12月19日","day":"12/19","e_id":"14062","title":"中国人民解放军国防大学建立"},{"date":"1985年12月19日","day":"12/19","e_id":"14063","title":"一架苏联客机被劫持到中国"},{"date":"1988年12月19日","day":"12/19","e_id":"14064","title":"科普作家高士其逝世"},{"date":"1988年12月19日","day":"12/19","e_id":"14065","title":"印度总理拉·甘地访问我国"},{"date":"1991年12月19日","day":"12/19","e_id":"14066","title":"\u201c海峡第一桥\u201d正式通车"},{"date":"1994年12月19日","day":"12/19","e_id":"14067","title":"香港回归倒计时牌在天安门广场矗立"},{"date":"1995年12月19日","day":"12/19","e_id":"14068","title":"索拉纳正式就任北约秘书长"},{"date":"1996年12月19日","day":"12/19","e_id":"14069","title":"美国电视节目实行分级"},{"date":"1997年12月19日","day":"12/19","e_id":"14070","title":"俄罗斯发生恐怖分子劫持瑞典外交官事件"},{"date":"1997年12月19日","day":"12/19","e_id":"14071","title":"金大中当选韩国第十五届总统"},{"date":"1998年12月19日","day":"12/19","e_id":"14072","title":"美众院通过两项弹劾克林顿条款"},{"date":"1998年12月19日","day":"12/19","e_id":"14073","title":"学者、作家钱钟书先生逝世"},{"date":"2001年12月19日","day":"12/19","e_id":"14074","title":"国画大师黎雄才逝世"},{"date":"2001年12月19日","day":"12/19","e_id":"14075","title":"马向东郭久嗣伏法"},{"date":"2010年12月19日","day":"12/19","e_id":"14076","title":"中国计划生育协会第七次全国会员代表大会在京闭幕"}]
     */

    private int error_code;
    private String reason;
    /**
     * date : 1280年12月19日
     * day : 12/19
     * e_id : 14046
     * title : 元朝颁行著名天文学家郭守敬的《授时历》
     */

    private List<ResultBean> result;

    public int getError_code() {
        return error_code;
    }

    public void setError_code(int error_code) {
        this.error_code = error_code;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public List<ResultBean> getResult() {
        return result;
    }

    public void setResult(List<ResultBean> result) {
        this.result = result;
    }

    public static class ResultBean {
        private String date;
        private String day;
        private String e_id;
        private String title;

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getDay() {
            return day;
        }

        public void setDay(String day) {
            this.day = day;
        }

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
    }
}
