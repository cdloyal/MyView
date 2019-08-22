package cd.myview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 * 作者：chenda
 * 时间：2019/8/21:15:34
 * 邮箱：
 * 说明：
 */
public class MyToggleButton extends View implements View.OnClickListener {

    /**
     * 一个视图从创建到显示过程的主要步骤
     * 1、构造方法实例化类
     * 2、测量-measure(int,int)->onMeasure();  setMeasuredDimension()保存测量值
     * 如果当前view是一个viewGroup，还有义务测量孩子
     * 孩子有建议测量值
     * 3、指定位置-layout()->onLayout()
     * 指定控件的位置，一般view不用重写这个方法，viewGroup才需要
     * 4、绘制视图--draw()->onDraw(canvas)
     * 根据上面两个方法参数，进入绘制
     * */

    private Bitmap slide_button,switch_background;
    private Paint paint;
    private int slide_left_max,slide_left;

    /**
     * @method           在布局文件使用该类，将会用到这个构造方法实例该类
     * @description     描述一下方法的作用
     * @date:            2019/8/21 16:09
     * @author:          陈达
     * @param
     * @return
     */
    public MyToggleButton(Context context, AttributeSet attrs) {
        super(context, attrs);

        slide_button = BitmapFactory.decodeResource(getResources(),R.drawable.slide_button);
        switch_background = BitmapFactory.decodeResource(getResources(),R.drawable.switch_background);
        slide_left_max = switch_background.getWidth()-slide_button.getWidth();
        slide_left=0;

        paint = new Paint();
        paint.setAntiAlias(true);   //设置抗锯齿

        this.setOnClickListener(this);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);


        setMeasuredDimension(switch_background.getWidth(),switch_background.getHeight());
//        Log.d("chenda","switch_background.getWidth()="+switch_background.getWidth()+"，switch_background.getWidth()="+switch_background.getWidth());
//        Log.d("chenda","widthMeasureSpec="+widthMeasureSpec+"，heightMeasureSpec="+heightMeasureSpec);
//        setMeasuredDimension(widthMeasureSpec,heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawBitmap(switch_background,0,0,paint);
        canvas.drawBitmap(slide_button,slide_left,0,paint);
    }


    private float startX;
    private float downX;    //解决onclick和onTouchEvent冲突，两次移动的情况
    @Override
    public boolean onTouchEvent(MotionEvent event) {

        super.onTouchEvent(event);  //不写这句+renturn true，onclick被屏蔽
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                Log.d("chenda","MyToggleButton onTouchEvent ACTION_DOWN");
//                event.getX();   //相对于自身
//                event.getRawX();//相对于屏幕
                downX = startX=event.getX();

                break;
            case MotionEvent.ACTION_MOVE:
                float endX =event.getX();
                float distanceX = endX-startX;
                //非法判断
                slide_left+=distanceX;
                slide_left=slide_left<0?0:slide_left;
                slide_left=slide_left>slide_left_max?slide_left_max:slide_left;
                invalidate();
                startX=event.getX();

                break;
            case MotionEvent.ACTION_UP  :
                Log.d("chenda","MyToggleButton onTouchEvent ACTION_UP");
                if(downX!=startX){
                    slide_left=slide_left<slide_left_max/2?0:slide_left;
                    slide_left=slide_left>slide_left_max/2?slide_left_max:slide_left;
                    invalidate();
                    startX=event.getX();
                }

                break;
            default:
                break;
        }

        return true;    //消费事件
    }

    @Override
    public void onClick(View v) {
        Log.d("chenda","MyToggleButton onclick");
        if(slide_left==0){
            slide_left=slide_left_max;
        }else {
            slide_left=0;
        }
        invalidate();   //调用ondraw()重绘
    }
}
