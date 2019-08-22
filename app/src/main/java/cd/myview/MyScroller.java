package cd.myview;

import android.os.SystemClock;

/**
 * 作者：chenda
 * 时间：2019/8/22:10:50
 * 邮箱：
 * 说明：
 */
public class MyScroller {
    /**
     * 计算使用总时间
     * */
    private static final long TOTALTIME = 500;

    private float startX, startY,currentX,currentY,stepX,stepY;
    private int distanceX, distanceY;
    private long startTime=0;
    private boolean computeFinished = true;

    public void startScroll(float startX,float startY, int distanceX,int distanceY){
        this.currentX=this.startX = startX;
        this.currentY=this.startY = startY;
        this.distanceX = distanceX;
        this.distanceY = distanceY;
        this.startTime = SystemClock.uptimeMillis();    //系统开机时间
        this.stepX = (float)distanceX/TOTALTIME;
        this.stepY = (float)distanceY/TOTALTIME;
        computeFinished = false;
    }

    public float getCurrX(){
        return currentX;
    }

    public float getCurrY(){
        return currentY;
    }

    /**
     * @method           计算currentX、currentY
     * @description     描述一下方法的作用
     * @date:            2019/8/22 11:02
     * @author:          陈达
     * @param
     * @return           true:正在计算
     *                   false:计算完成
     */
    public boolean computeScrollOffset(){
        if(computeFinished)
            return false;

        long currTime = SystemClock.uptimeMillis();
        if(currTime-startTime>TOTALTIME && currentX == startX+distanceX
                && currentY == startY+distanceY){
            computeFinished=true;
            return false;
        }

        currentX = startX+((float)(currTime-startTime))*stepX;
        if(stepX>0){
            currentX=currentX>startX+distanceX?startX+distanceX:currentX;
        }else {
            currentX=currentX<startX+distanceX?startX+distanceX:currentX;
        }
//        Log.d("chenda","currentX="+currentX);

        currentY = startY+((float)(currTime-startTime))*stepY;
        if(stepY>0){
            currentY=currentY>startY+distanceY?startY+distanceY:currentY;
        }else {
            currentY=currentY<startY+distanceY?startY+distanceY:currentY;
        }

        return true;
    }

}
