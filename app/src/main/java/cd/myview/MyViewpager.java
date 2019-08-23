package cd.myview;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Scroller;

/**
 * 作者：chenda
 * 时间：2019/8/21:19:22
 * 邮箱：
 * 说明：
 */
public class MyViewpager extends ViewGroup {

    private static final String TAG = MyViewpager.class.getSimpleName();
    /**
     * 手势识别器
     * */
    private GestureDetector gestureDetector;
//    private MyScroller myScroller;
    private Scroller myScroller;
    private ViewPager.OnPageChangeListener onPageChangeListener;
    private int preIndex;
    private float downX;
    private float downY;

    public MyViewpager(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
//        myScroller = new MyScroller();
        myScroller = new Scroller(context);
        preIndex=0;
    }

    private void initView(Context context) {
        gestureDetector = new GestureDetector(context,new GestureDetector.SimpleOnGestureListener(){
            @Override
            public void onLongPress(MotionEvent e) {
                //长按
                super.onLongPress(e);
            }

            /**
             * @method           onScroll
             * @description      手势滑动
             * @date:            2019/8/22 9:32
             * @author:          陈达
             * @param            distanceX  手指向左移动距离，那么视图应该向右移动distanceX
             *                   distanceY
             * @return           void
             */
            @Override
            public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
                //滑动
                super.onScroll(e1, e2, distanceX, distanceY);

//                Log.d(TAG,"scrollBy bef getScrollX()="+getScrollX());

                //视图移动
                //getScrollY() 视图原点Y坐标相对于屏幕原点Y坐标移动的绝对坐标
                scrollBy((int)distanceX,getScrollY());

//                Log.d(TAG,"scrollBy aft getScrollX()="+getScrollX());

                return true;
            }

            @Override
            public boolean onDoubleTap(MotionEvent e) {
                //双击
                return super.onDoubleTap(e);
            }
        });

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        for(int i=0;i<getChildCount();i++){
            View view = getChildAt(i);
            view.measure(widthMeasureSpec,heightMeasureSpec);
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        //遍历每个还是，给每个孩子指定在屏幕的坐标位置
        for(int i=0;i<getChildCount();i++){
            View childView = getChildAt(i);

            childView.layout(i*getWidth(),0,(i+1)*getWidth(),getHeight());
        }
    }

    /**
     * @method           事件拦截
     * @description     描述一下方法的作用
     * @date:            2019/8/23 16:24
     * @author:          陈达
     * @param
     * @return
     */
    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        super.onInterceptTouchEvent(event);

//        gestureDetector.onTouchEvent(event);    //为什么onTouchEvent()也要调这个方法?
                                                //MotionEvent.ACTION_DOWN过来一个事件，调onInterceptTouchEvent()要不要拦截，哦，不拦截
                                                //第一个MotionEvent.ACTION_MOVE过来，onInterceptTouchEvent()要不要拦截，哦，拦截
                                                //拦截之后剩下的Event（包括剩下的ACTION_MOVE，ACTION_UP）交给onTouchEvent()处理
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                downX = event.getX();
                downY = event.getY();
                gestureDetector.onTouchEvent(event);
                break;
            case MotionEvent.ACTION_MOVE:
                float endX = event.getX();
                float endY = event.getY();
                float distanceX = Math.abs(endX-downX);
                float distanceY = Math.abs(endY-downY);
                if(distanceX>=distanceY && distanceX>5){
//                    gestureDetector.onTouchEvent(event);
                    return true;
                }else { //(distanceX<distanceY，listview动，但是上面手势已经识别横向滑动了
//                    setPagerId(index);
                }
                break;
            case MotionEvent.ACTION_UP:

                break;
        }

        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);

        gestureDetector.onTouchEvent(event);

        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_MOVE:

                break;
            case MotionEvent.ACTION_UP:
                //手提起时，要调整视图
                if(getScrollX()<0){ //回原点
                    myScroller.startScroll(getScrollX(),getScrollY(),  -getScrollX(),0);
                    invalidate();
                    return true;
                }

                int remainderX = getScrollX()%getWidth();

                if(remainderX!=0){
                    int index = getScrollX()/getWidth();    //第index和index+1之间
                    index = remainderX<=getWidth()/2?index:index+1;

                    setPagerId(index);

                }

                break;
        }
        return true;
    }

    @Override
    public void computeScroll() {
        super.computeScroll();

        if(myScroller.computeScrollOffset()){
            scrollTo((int) myScroller.getCurrX(),getScrollY());
            invalidate();
        }
    }

    public void setPagerId(int index) {
//                    scrollTo(index*getWidth(),getScrollY());    //太生硬
//                    Log.d(TAG,"from x="+getScrollX()+",y="+getScrollY()+";to x="+(index*getWidth()-getScrollX())+",y="+0);
        //使用插值计算器
        myScroller.startScroll(getScrollX(),getScrollY(),  index*getWidth()-getScrollX(),Math.abs(index*getWidth()-getScrollX()));
        invalidate();   //调用onDraw()、computeScroll();
        if(index!=preIndex && onPageChangeListener!=null){
            onPageChangeListener.onPageSelected(index);
        }
        preIndex=index;
    }

    public void setOnPageChangeListener(ViewPager.OnPageChangeListener onPageChangeListener){
        this.onPageChangeListener = onPageChangeListener;
    }


}
