package com.easy.lib.weight.banner;

import android.view.View;
import android.view.ViewGroup;

import androidx.viewpager.widget.PagerAdapter;

import java.util.ArrayList;
import java.util.List;

class BannerAdapter extends PagerAdapter {

    private List bannerList;
    private LoopBannerHolder loopBannerHolder;
    private int DAYTIME = 500;

    public BannerAdapter() {
        bannerList = new ArrayList();
    }

    public void setLoop(boolean loop) {
        if (loop) {
            DAYTIME = DAYTIME * 2;
        }
    }

    public void setData(List data) {
        bannerList.clear();
        bannerList.addAll(data);
    }

    public void createHolder(LoopBannerHolder holder) {
        loopBannerHolder = holder;
    }


    @Override
    public int getCount() {
        return bannerList.size() * DAYTIME;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        //得到真实的position
        int realPosition = position % bannerList.size();
        //得到复用的View
        View view = loopBannerHolder.bindView(realPosition, bannerList.get(realPosition));
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        View view = (View) object;
        container.removeView(view);
        BannerHelper.getInstance().getBannerViewMap().put(view, false);
    }

    public int getRealCount() {
        return bannerList.size();
    }


}