package com.result.tools;

import com.result.homepage.bean.HpBean;

import java.io.Serializable;

/**
 * autour: 刘东东
 * date: 2016/12/16 18:37
 * update: 2016/12/16
 */

public class FirstEvent implements Serializable{
    private HpBean.ResultBean data;

    public FirstEvent(HpBean.ResultBean data) {
        this.data = data;
    }

    public HpBean.ResultBean getData() {
        return data;
    }
}
