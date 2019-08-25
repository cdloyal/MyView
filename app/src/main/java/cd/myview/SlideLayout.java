package cd.myview;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Scroller;

/**
 * @Description: java类作用描述
 * @Author: 陈达
 * @CreateDate: 2019/8/25 12:58
 * @UpdateUser: 陈达
 * @UpdateDate: 2019/8/25 12:58
 * @UpdateRemark: 更新说明：
 * @Version: 1.0
 */
public class SlideLayout extends FrameLayout {

    private final static String TAG = SlideLayout.class.getSimpleName();
    private View itemContent, itemMenu;
    private int contentWidth, menuWidth, viewHeight;
    private float downX, downY, startX, startY;
    private Scroller scroller;
    private OnStatusChangeLinstener onStatusChangeLinstener;

    public SlideLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        scroller = new Scroller(context);
    }

    /**
     * @param
     * @return
     * @method onFinishInflate
     * @description 当布局文件加载完成回调这个方法
     */
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        Log.d(TAG, "onFinishInflate()");
        itemContent = getChildAt(0);
        itemMenu = getChildAt(1);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        contentWidth = itemContent.getMeasuredWidth();
        menuWidth = itemMenu.getMeasuredWidth();

        viewHeight = getMeasuredHeight();
//        setMeasuredDimension(contentWidth+menuWidth,viewHeight);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

        itemMenu.layout(contentWidth, 0, contentWidth + menuWidth, viewHeight);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Log.d(TAG, "slide onDraw()");
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Log.d(TAG, "MotionEvent.ACTION_DOWN");
                downX = startX = event.getX();
                downY = startY = event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                Log.d(TAG, "MotionEvent.ACTION_MOVE");
                float endX = event.getX();
                float endY = event.getY();

                float scrollX = startX - endX;  //当前滑动距离
                float scrollY = startY - endY;

                if (getScrollX() + scrollX < 0)  //总滑动距离小于0
//                    closeMenu();
                    scrollX = -getScrollX();
                else if (getScrollX() + scrollX > menuWidth) //总滑动距离超出范围
//                    openMenu();
                    scrollX = menuWidth - getScrollX();

                scrollBy((int) scrollX, getScrollY());

                //ListView下ACTION_UP被ListView拦截,要进行反拦截
                //竖方向滑动，我这里不能消费，只需要横方向滑动进行反拦截
                if (Math.abs(endX-downX) > Math.abs(endY-downY))
                    getParent().requestDisallowInterceptTouchEvent(true);

                startX = endX;
                startY = endY;
                break;
            case MotionEvent.ACTION_UP:
                Log.d(TAG, "MotionEvent.ACTION_UP");

//                scrollX = downX - event.getX();
                if (getScrollX() <= menuWidth / 2)
                    closeMenu();
                else
                    openMenu();

                break;
        }

        return true;
    }


    /**
     * 解决孩子消费横向滑动的问题
     * */
    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        super.onInterceptTouchEvent(event);
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                startX = event.getX();
                if(onStatusChangeLinstener!=null)
                    onStatusChangeLinstener.onTouch(this);
                break;
            case MotionEvent.ACTION_MOVE:
                if (Math.abs(event.getX()-startX)>5)
                    return true;
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        return false;
    }

    public void setOnStatusChangeLinstener(OnStatusChangeLinstener onStatusChangeLinstener){
        this.onStatusChangeLinstener = onStatusChangeLinstener;
    }

    public interface OnStatusChangeLinstener{
        public void onTouch(SlideLayout slideLayout);
        public void onMenuOpen(SlideLayout slideLayout);
        public void onMenuClose(SlideLayout slideLayout);
    }

    private void openMenu() {
        scroller.startScroll(getScrollX(), getScrollY(), menuWidth - getScrollX(), getScrollY());
        invalidate();
        if(onStatusChangeLinstener!=null)
            onStatusChangeLinstener.onMenuOpen(this);
//        scrollTo(menuWidth,getScrollY());
    }

    public void closeMenu() {
        scroller.startScroll(getScrollX(), getScrollY(), -getScrollX(), getScrollY());
        invalidate();
        if(onStatusChangeLinstener!=null)
            onStatusChangeLinstener.onMenuClose(this);
//        scrollTo(0,getScrollY());
    }

    @Override
    public void computeScroll() {
        super.computeScroll();

        //是否正在计算
        if (scroller.computeScrollOffset()) {
            scrollTo(scroller.getCurrX(), scroller.getCurrY());
            invalidate();
        }
    }
}
