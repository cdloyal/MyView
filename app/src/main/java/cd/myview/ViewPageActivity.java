package cd.myview;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class ViewPageActivity extends AppCompatActivity {

    private static final String TAG = ViewPageActivity.class.getSimpleName();
    private ViewPager viewpager;
    private TextView title;
    private LinearLayout ll_ponit_prout;
    private int startPos ;
    private boolean isDragged = false;
    //listview的使用
    //1、在布局文件定义listview
    //2、实例化listview
    //3、准备数据(集合)
    //4、设置适配器-item布局-绑定数据
    //5、刷新适配器

    private int prePosition;
    private final static int[] imageIds = {
            R.drawable.a,
            R.drawable.b,
            R.drawable.c,
            R.drawable.d,
            R.drawable.e,
    };

    private final static String[] imageDescriptions = {
            "title1",
            "title2",
            "title3",
            "title4",
            "title5"
    };

    private ArrayList<ImageView> imageViews;

    private MyHandler handler ;
    private class MyHandler extends Handler{
        private WeakReference<Activity> reference;
        public MyHandler(Activity activity){
            reference = new WeakReference<>(activity);
        }
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Log.d(TAG,"handleMessage");
            if(reference==null)
                return;

            int item = viewpager.getCurrentItem()+1==Integer.MAX_VALUE?
                    startPos+Integer.MAX_VALUE%imageViews.size():viewpager.getCurrentItem()+1;
            viewpager.setCurrentItem(item);

            //延时发消息，循环发消息
            handler.sendEmptyMessageDelayed(0,4000);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_page);

        viewpager = findViewById(R.id.viewpager);
        title = findViewById(R.id.title);
        ll_ponit_prout = findViewById(R.id.ll_ponit_prout);

        //ViewPager的使用
        //1、在布局文件定义ViewPager
        //2、实例化ViewPager
        //3、准备数据(集合)
        //4、设置适配器pagerAdapter-item布局-绑定数据
        //5、刷新适配器

        imageViews = new ArrayList();
        for (int i=0; i<imageIds.length ;i++ ){
            ImageView imageView = new ImageView(this);
            imageView.setBackgroundResource(imageIds[i]);

            imageViews.add(imageView);

            //添加原点位置,选择器
            ImageView point = new ImageView(this);
            point.setBackgroundResource(R.drawable.point_selector);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(32,32);

            if(i==0){
                point.setEnabled(true);
            }else {
                point.setEnabled(false);
                params.leftMargin=32;
            }
            point.setLayoutParams(params);

            ll_ponit_prout.addView(point);

            title.setText(imageDescriptions[0]);
        }

        MyPagerAdapter myPagerAdapter = new MyPagerAdapter();
        viewpager.setAdapter(myPagerAdapter);
        viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                //当页面滚动时回调的方法
                //position:当前页面位置
                //positionOffset:当前滑动页面的百分比
                //positionOffsetPixels:在屏幕上滑动的像素
//                Log.d(TAG,"onPageScrolled position="+position+",positionOffset="+positionOffset+",positionOffsetPixels="+positionOffsetPixels);
            }

            @Override
            public void onPageSelected(int position) {
                //当页面选中时回调的方法
                //设置页面文本信息
                //把上一个高亮点设置灰色
//                Log.d(TAG,"onPageSelected position="+position);

                int pos = position%imageViews.size();
                title.setText(imageDescriptions[pos]);
                ll_ponit_prout.getChildAt(prePosition).setEnabled(false);
                ll_ponit_prout.getChildAt(pos).setEnabled(true);
                prePosition=pos;
            }

            @Override
            public void onPageScrollStateChanged(int status) {
                //当页面滚动状态变化
                //静止--滑动
                //滑动--静止
                //静止--拖拽
//                Log.d(TAG,"onPageScrollStateChanged status="+status);
                switch (status){
                    case ViewPager.SCROLL_STATE_DRAGGING:   //拖拽
                        handler.removeCallbacksAndMessages(null);
                        isDragged = true;
                        break;
                    case ViewPager.SCROLL_STATE_SETTLING:   //滑动
                        break;
                    case ViewPager.SCROLL_STATE_IDLE:       //静止
                        if(isDragged){
                            handler.removeCallbacksAndMessages(null);   //有可能刚松手遇上消息就跳了两张图了，发消息前先取消
                            handler.sendEmptyMessageDelayed(0,4000);
                            isDragged = false;
                        }
                        break;
                }

            }
        });

        //设置中间位置位起始位置
        startPos = Integer.MAX_VALUE/2-(Integer.MAX_VALUE/2)%imageViews.size();
        viewpager.setCurrentItem(startPos);

        handler = new MyHandler(this);
        handler.sendEmptyMessageDelayed(0,4000);
    }

    class MyPagerAdapter extends PagerAdapter{


        @SuppressLint("ClickableViewAccessibility")
        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            int newPos = position%imageViews.size();
            ImageView imageView = imageViews.get(newPos);
            imageView.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    //手指按下，不滑动，松开。 ACTION_DOWN--ACTION_UP
                    //手指按下，滑动，静止，松开。ACTION_DOWN--ACTION_MOVE--ACTION_CANCEL，手指在静止的时候已经回调ACTION_CANCEL，不触发ACTION_UP，

                    switch (event.getAction()){
                        case MotionEvent.ACTION_DOWN:   //按下
                            Log.d(TAG,"view="+v+"MotionEvent.ACTION_DOWN");
//                            handler.removeCallbacksAndMessages(null);   //将消息队列和任务都移除
                            break;
                        case MotionEvent.ACTION_MOVE:   //移动
                            Log.d(TAG,"view="+v+"MotionEvent.ACTION_MOVE");
                            break;
                        case MotionEvent.ACTION_CANCEL:   //取消，不会再触发
                            Log.d(TAG,"view="+v+"MotionEvent.ACTION_CANCEL");
//                            handler.sendEmptyMessageDelayed(0,4000);
                            break;
                        case MotionEvent.ACTION_UP:     //离开
                            Log.d(TAG,"view="+v+"MotionEvent.ACTION_UP");
//                            handler.sendEmptyMessageDelayed(0,4000);
                            break;
                    }
                    return false;    //返回true，屏蔽onclick
                }
            });

            imageView.setTag(newPos);
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = (int) v.getTag();

                    Log.d(TAG,"onclick title="+imageDescriptions[position]);
                }
            });

            container.addView(imageView);

            Log.d(TAG,"instantiateItem=="+newPos+"imageView="+imageView);
            return imageView;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
//            super.destroyItem(container, position, object);

            /**
             * 刚开始不可以从尾到头，初始化是两个item，第二张图片是缓存，滑动到第二张，就是缓存三张图片了
             * */
            container.removeView((View) object);
            Log.d(TAG,"destroyItem=="+position+"object="+object);
        }

        @Override
        public int getCount() {
            //Integer.MAX_VALUE为了能够滑动到>imageViews.size()
//            return imageViews.size();
            return Integer.MAX_VALUE;
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
//            Log.d(TAG,"isViewFromObject()，view="+view+"o="+o);
            return view==o;
        }
    }
}
