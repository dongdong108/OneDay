package com.result.tools;

import java.io.Serializable;

/**
 * autour: 刘东东
 * date: 2016/12/16 18:37
 * update: 2016/12/16
 */

public class GirlEvent implements Serializable{
    private String data;

    public GirlEvent(String data) {
        this.data = data;
    }

    public String getData() {
        return data;
    }
}
