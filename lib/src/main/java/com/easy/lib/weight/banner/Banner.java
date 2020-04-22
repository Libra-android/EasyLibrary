package com.easy.lib.weight.banner;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleEventObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.viewpager.widget.ViewPager;

import java.util.List;

/**
 * @author liu
 * @date 2017/11/6
 */
public class Banner extends FrameLayout {

    private Context context;
    private ViewPager viewPager;
    private BannerAdapter adapter;
    private boolean isFirst = true;//判断是不是第一次创建用于调整adapter 到正确的显示位置
    private boolean isTuning = false;//判断是否正在轮播
    private Paint selectPaint;//选中的画笔
    private Paint paint;//未选中的画笔
    private int pointCount = 0; //点的数量
    private float locationX = 0;//计算出的点的X坐标
    private float locationY = 0;//计算出的点的Y坐标
    //定义轮播的间隔时间
    private long delayMillis;
    //手动控制是否可以轮播
    private boolean isLoop = false;
    //点之间的间距
    private float width;
    //点的大小
    private float pointSize;
    //点距离底部的距离
    private float marginBottom;
    /**
     * 无限轮播任务
     */
    private Runnable task = new Runnable() {
        @Override
        public void run() {
            viewPager.setCurrentItem(viewPager.getCurrentItem() + 1, true);
            postDelayed(task, delayMillis);
        }
    };
    private boolean isTouch = false;
    /**
     * 当页面切换到第1个和最后一个时，页面调整至合适的位置
     */
    private ViewPager.OnPageChangeListener pageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {

        }

        @Override
        public void onPageScrollStateChanged(int state) {
            if (isLoop && state == ViewPager.SCROLL_STATE_IDLE) {

                if (adapter.getCount() != 0) {
                    if (viewPager.getCurrentItem() == 0) {
                        viewPager.setCurrentItem(adapter.getCount() / 2, false);
                    } else if (viewPager.getCurrentItem() == adapter.getCount() - 1) {
                        viewPager.setCurrentItem(adapter.getCount() / 2 - 1, false);
                    }
                }


            }
        }

    };


    public Banner(Context context) {
        super(context);
        init(context);
    }


    public Banner(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);


    }

    /**
     * 创建holder 和添加data
     *
     * @param holder
     * @param data
     */
    public void createHolder(LoopBannerHolder holder, List data, boolean loop) {

        isLoop = loop;

        pointCount = data.size();
        locationX = 0;

        int lastCurtTime = 0;
        if (!isFirst && adapter.getRealCount() != 0) {
            int realCount = adapter.getRealCount();
            int curtCount = viewPager.getCurrentItem() / realCount;
            lastCurtTime = curtCount * data.size() + viewPager.getCurrentItem() % realCount;
        }
        adapter = new BannerAdapter();
        adapter.setData(data);
        adapter.createHolder(holder);
        adapter.setLoop(loop);
        viewPager.setAdapter(adapter);
        if (isFirst) {
            lastCurtTime = adapter.getCount() / 2;
            isFirst = false;
        }
        viewPager.setCurrentItem(lastCurtTime, false);

    }

    /**
     * 设置画笔颜色 长度为2的数组 第一个为未选中点的颜色 ，第二个为选中的点的颜色
     *
     * @param colors
     */
    public void setColors(int[] colors) {
        if (colors.length < 2) {
            Log.e("banner", "colors length is not 2");
            return;
        }
        paint.setColor(colors[0]);
        selectPaint.setColor(colors[1]);

    }

    /**
     * 设置距离底部的高度 dp
     *
     * @param marginBottom
     */
    public void setMarginBottom(float marginBottom) {
        this.marginBottom = dpToPx(marginBottom);
        locationY = 0;
    }

    /**
     * 设置点的大小 dp
     *
     * @param pointSize
     */
    public void pointSize(float pointSize) {
        this.pointSize = dpToPx(pointSize);
        paint.setStrokeWidth(this.pointSize);
        selectPaint.setStrokeWidth(this.pointSize);
        locationY = 0;
        locationX = 0;
    }

    /**
     * 设置点的距离 dp
     *
     * @param width
     */
    public void setWidth(float width) {
        this.width = dpToPx(width);
        locationX = 0;
    }

    /**
     * 用户调用开始轮播的方法 时间默认 2.5秒
     */
    public void start() {
        start(2500);
    }

    /**
     * 供外部调用的方法
     *
     * @param delayMillis 轮播时间间隔
     */
    public void start(long delayMillis) {
        if (!isLoop) {
            return;
        }
        this.delayMillis = delayMillis;
        if (isTuning) {
            return;
        }
        isTuning = true;
        if(enabled){
            postDelayed(task, delayMillis);
        }
    }

    /**
     * 供外部调用的方法
     * 停止轮播
     */
    public void stop() {
        if (isTuning) {
            isTuning = false;
            removeCallbacks(task);
        }
    }

    private boolean enabled =true;

    public void enabledTuning(boolean enabled){
        this.enabled = enabled;
    }

    /**
     * 初始化操作的 添加了一个 viewpager ，初始化了 2 个画笔
     *
     * @param context
     */
    private void init(Context context) {
        this.context = context;

        if (context instanceof AppCompatActivity) {
            ((AppCompatActivity) context).getLifecycle().addObserver(new LifecycleEventObserver() {
                @Override
                public void onStateChanged(@NonNull LifecycleOwner source, @NonNull Lifecycle.Event event) {
                    if (event == Lifecycle.Event.ON_DESTROY) {
                        stop();
                    }
                }
            });
        }

        viewPager = new ViewPager(context);
        addView(viewPager);
        viewPager.addOnPageChangeListener(pageChangeListener);

        width = dpToPx(4);
        pointSize = dpToPx(5);
        marginBottom = dpToPx(12);

        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStrokeWidth(pointSize);
        paint.setStrokeCap(Paint.Cap.ROUND);

        selectPaint = new Paint(paint);

    }

    /**
     * 当页面被用户触摸时，停止轮播
     * 用户离开 重新开始轮播
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        int action = ev.getAction();
        if (action == MotionEvent.ACTION_UP || action == MotionEvent.ACTION_CANCEL || action == MotionEvent.ACTION_OUTSIDE) {
            if(enabled){
                start();
            }
        } else {
            stop();
        }
        return super.dispatchTouchEvent(ev);
    }

    /**
     * 动态画点的方法
     *
     * @param canvas
     */
    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        if (!isLoop) {
            return;
        }
        if (locationX == 0) {
            locationX = getCenterLocationZero(canvas.getWidth() / 2);
        }
        if (locationY == 0) {
            locationY = canvas.getHeight() - marginBottom - pointSize / 2;
        }

        if (pointCount != 0) {
            int position = viewPager.getCurrentItem() % pointCount;

            for (int i = 0; i < pointCount; i++) {
                float locationX = this.locationX + (width + pointSize) * i;
                canvas.drawPoint(locationX, locationY, position == i ? selectPaint : paint);
            }
        }

    }

    /**
     * dp转 px 的方法
     *
     * @param size
     * @return
     */
    private int dpToPx(float size) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (scale * size + 0.5);
    }

    /**
     * 获取第一个点的位置
     *
     * @param centerX
     * @return
     */
    private float getCenterLocationZero(int centerX) {
        return centerX - (width + pointSize) * (pointCount - 1) / 2F;
    }

    private float viewHeight = 0F;

    public void setTransformer(final int layoutMargin, final int PageMargin, final int ScaleY) {
        viewPager.setOffscreenPageLimit(3);
        LayoutParams layoutParams= (FrameLayout.LayoutParams)viewPager.getLayoutParams();
        layoutParams.leftMargin = layoutMargin;
        layoutParams.rightMargin = layoutMargin;
        viewPager.setLayoutParams(layoutParams);
        setClipChildren(false);
        viewPager.setClipChildren(false);
        viewPager.setPageMargin(PageMargin);

        viewPager.setPageTransformer(false, new ViewPager.PageTransformer() {
            @Override
            public void transformPage(@NonNull View page, float position) {

                if (viewHeight == 0) {
                    viewHeight = viewPager.getHeight();
                }

                float maxHeight = viewHeight;
                float minHeight = maxHeight - ScaleY;

                if (position == 0) {
                    page.setScaleX(1);
                    page.setScaleY(1);

                } else if (position < 1 && position > -1) {
                    float changeHeight = (1 - Math.abs(position)) * ScaleY;
                    float scale = (changeHeight + minHeight) / maxHeight;
                    page.setScaleX(1);
                    page.setScaleY(scale);
                } else {
                    float scale = (minHeight) / maxHeight;
                    page.setScaleX(1);
                    page.setScaleY(scale);
                }

            }
        });
    }

}
