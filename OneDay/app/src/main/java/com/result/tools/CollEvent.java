package com.result.tools;

import com.result.dao.ScUser;

import java.io.Serializable;

/**
 * autour: 刘东东
 * date: 2016/12/16 18:37
 * update: 2016/12/16
 */

public class CollEvent implements Serializable{
    private ScUser data;

    public CollEvent(ScUser data) {
        this.data = data;
    }

    public ScUser getData() {
        return data;
    }
}
