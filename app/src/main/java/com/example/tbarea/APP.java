package com.example.tbarea;

import android.app.Application;

/**
 * Description :
 *
 * @author WSoBan
 * @date 2018/05/03
 */
public class APP extends Application {

    private static APP sInstance;

    public APP() {
        super();
        sInstance = this;
    }

    public static APP getInstance() {
        return sInstance;
    }
}
