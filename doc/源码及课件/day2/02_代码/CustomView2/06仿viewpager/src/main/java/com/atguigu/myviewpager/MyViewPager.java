package com.atguigu.myviewpager;

import android.content.Context;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

/**
 * 作者：杨光福 on 2016/5/16 10:33
 * 微信：yangguangfu520
 * QQ号：541433511
 * 作用：仿ViewPager
 */
public class MyViewPager extends ViewGroup {

    /**
     * 手势识别器
     * 1.定义出来
     * 2.实例化-把想要的方法给重新
     * 3.在onTouchEvent()把事件传递给手势识别器
     *
     *
     */

    private GestureDetector detector;
    /**
     * 当前页面的下标位置
     */
    private int currentIndex;

    private MyScroller scroller;

    public MyViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    private void initView(final Context context) {
        scroller = new MyScroller();
        //2.实例化手势识别器
        detector = new GestureDetector(context,new GestureDetector.SimpleOnGestureListener(){
            @Override
            public void onLongPress(MotionEvent e) {
                super.onLongPress(e);
                Toast.makeText(context,"长按",Toast.LENGTH_SHORT).show();
            }

            /**
             *
             * @param e1
             * @param e2
             * @param distanceX 在X轴滑动了的距离
             * @param distanceY 在Y轴滑动了的距离
             * @return
             */
            @Override
            public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {


                /**
                 *x:要在X轴移动的距离
                 *y:要在Y轴移动的距离
                 */
                scrollBy((int)distanceX,0);

                return true;
            }

            @Override
            public boolean onDoubleTap(MotionEvent e) {
                Toast.makeText(context,"双击",Toast.LENGTH_SHORT).show();
                return super.onDoubleTap(e);
            }
        });
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        //遍历孩子，给每个孩子指定在屏幕的坐标位置
        for(int i=0;i<getChildCount();i++){
            View childView = getChildAt(i);

            childView.layout(i*getWidth(),0,(i+1)*getWidth(),getHeight());
        }

    }
    private float startX;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);
        //3.把事件传递给手势识别器
        detector.onTouchEvent(event);
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                //1.记录坐标
                startX = event.getX();
                break;
            case MotionEvent.ACTION_MOVE:

                break;
            case MotionEvent.ACTION_UP:

                //2.来到新的坐标
                float endX = event.getX();

                //下标位置
                int tempIndex = currentIndex;

                if((startX-endX)>getWidth()/2){

                    //显示下一个页面
                    tempIndex ++;

                }else if((endX-startX)>getWidth()/2){

                    //显示上一个页面
                    tempIndex --;
                }

                //根据下标位置移动到指定的页面
                scrollToPager(tempIndex);

                break;
        }
        return true;
    }

    /**
     * 屏蔽非法值，根据位置移动到指定页面
     * @param tempIndex
     */
    private void scrollToPager(int tempIndex) {

        if(tempIndex < 0){
            tempIndex = 0;
        }

        if(tempIndex > getChildCount()-1){
            tempIndex = getChildCount()-1;
        }
        //当前页面的下标位置
        currentIndex = tempIndex;

        int distanceX = currentIndex*getWidth() - getScrollX();
        //移动到指定的位置
//        scrollTo(currentIndex*getWidth(),getScrollY());
        scroller.startScroll(getScrollX(),getScrollY(),distanceX,0);

        //
        invalidate();;//onDraw();computeScroll();
    }

    @Override
    public void computeScroll() {
//        super.computeScroll();
       if( scroller.cuputeScrollOffset()){

           float currX = scroller.getCurrX();

           scrollTo((int) currX,0);

           invalidate();
       };
    }
}
