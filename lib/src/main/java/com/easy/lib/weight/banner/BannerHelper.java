package com.easy.lib.weight.banner;


import android.view.View;

import java.util.HashMap;
import java.util.Map;

/**
 * @author liu
 * @date 2017/11/9
 */
public class BannerHelper {

    private static BannerHelper instance;

    private Map<View, Boolean> viewBooleanMap;

    public BannerHelper() {
        this.viewBooleanMap = new HashMap<>();
    }

    public synchronized static BannerHelper getInstance() {
        if (instance == null) {
            instance = new BannerHelper();
        }
        return instance;
    }

    public Map<View, Boolean> getBannerViewMap() {
        return viewBooleanMap;
    }


}
