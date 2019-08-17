package cd.myview;

import android.view.View;
import android.view.ViewGroup;
import android.view.animation.RotateAnimation;

/**
 * 作者：chenda
 * 时间：2019/8/17:17:03
 * 邮箱：
 * 说明：
 */
public class ViewTool {

    public static void hideView(ViewGroup view, long startOffset){
        RotateAnimation ra = new RotateAnimation(0,180,view.getWidth()/2,view.getHeight());
        ra.setDuration(500);    //设置动画时间
        ra.setFillAfter(true);  //动画停留在播放完成时的状态
        ra.setStartOffset(startOffset);
        view.startAnimation(ra);//开始动画

        //View改成ViewGroup,隐藏ViewGroup，子view还能点击
//        view.setVisibility(View.INVISIBLE);
        for(int i=0;i<view.getChildCount();i++)
            view.getChildAt(i).setVisibility(View.INVISIBLE);
    }
    public static void showView(ViewGroup view){
//        view.setVisibility(View.VISIBLE);

        RotateAnimation ra = new RotateAnimation(180,360,view.getWidth()/2,view.getHeight());
        ra.setDuration(500);    //设置动画时间
        ra.setFillAfter(true);  //动画停留在播放完成时的状态
        view.startAnimation(ra);//开始动画

        for(int i=0;i<view.getChildCount();i++)
            view.getChildAt(i).setVisibility(View.VISIBLE);
    }
}
