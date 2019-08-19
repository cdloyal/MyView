package cd.myview;

import android.animation.ObjectAnimator;
import android.view.ViewGroup;

/**
 * 作者：chenda
 * 时间：2019/8/17:17:03
 * 邮箱：
 * 说明：
 */
public class ViewTool {

    public static void hideView(ViewGroup view, long startOffset){
//        RotateAnimation ra = new RotateAnimation(0,180,view.getWidth()/2,view.getHeight());
//        ra.setDuration(500);    //设置动画时间
//        ra.setFillAfter(true);  //动画停留在播放完成时的状态
//        ra.setStartOffset(startOffset);
//        view.startAnimation(ra);//开始动画
//
//        //View改成ViewGroup,隐藏ViewGroup，子view还能点击
////        view.setVisibility(View.INVISIBLE);
//        for(int i=0;i<view.getChildCount();i++)
//            view.getChildAt(i).setVisibility(View.INVISIBLE);

        //属性动画
        ObjectAnimator animator = ObjectAnimator.ofFloat(view,"rotation",0,180);
        animator.setDuration(500);
        animator.setStartDelay(startOffset);
        animator.start();
//        view.setRotationX(view.getWidth()/2);
//        view.setRotationY(view.getHeight());
        view.setPivotX(view.getWidth()/2);
        view.setPivotY(view.getHeight());
    }
    public static void showView(ViewGroup view, long startOffset){
//        view.setVisibility(View.VISIBLE);

//        RotateAnimation ra = new RotateAnimation(180,360,view.getWidth()/2,view.getHeight());
//        ra.setDuration(500);    //设置动画时间
//        ra.setFillAfter(true);  //动画停留在播放完成时的状态
//        view.startAnimation(ra);//开始动画
//
//        for(int i=0;i<view.getChildCount();i++)
//            view.getChildAt(i).setVisibility(View.VISIBLE);

        //属性动画
        ObjectAnimator animator = ObjectAnimator.ofFloat(view,"rotation",180,360);
        animator.setDuration(500);
        animator.setStartDelay(startOffset);
        animator.start();
//        view.setRotationX(view.getWidth()/2);
//        view.setRotationY(view.getHeight());
        view.setPivotX(view.getWidth()/2);
        view.setPivotY(view.getHeight());
    }
}
