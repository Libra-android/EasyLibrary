package com.easy.lib.weight.banner;


import android.view.View;

import java.util.Map;

/**
 * @author liu
 * @date 2017/11/8
 */
abstract public class LoopBannerHolder<T> {

    abstract public View createView();

    abstract public void bindData(View itemView, int position, T data);

    public View bindView(int position, T data) {
        View itemView = getView();
        bindData(itemView, position, data);
        return itemView;
    }

    private View getView() {

        Map<View, Boolean> viewMap = BannerHelper.getInstance().getBannerViewMap();

        for (View key : viewMap.keySet()) {
            if (viewMap.get(key) == false) {
                viewMap.put(key, true);
                return key;
            }
        }

        View view = createView();
        viewMap.put(view, true);
        return view;
    }


}
