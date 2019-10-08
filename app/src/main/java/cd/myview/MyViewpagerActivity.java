package cd.myview;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class MyViewpagerActivity extends Activity {

    private MyViewpager myViewpager;
    private RadioGroup rg_myvp;
    private int childViews[] = {R.drawable.a1,R.drawable.a2,R.drawable.a3,R.drawable.a4,R.drawable.a5,R.drawable.a6};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("chenda","MyViewpagerActivity onCreate()");
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_viewpager);
        Log.d("chenda","MyViewpagerActivity setContentView()");

        myViewpager = findViewById(R.id.myvp);
        rg_myvp = findViewById(R.id.rg_myvp);

        for(int i=0;i<6;i++){
            ImageView imageView = new ImageView(this);
            imageView.setBackgroundResource(childViews[i]);

            myViewpager.addView(imageView);
        }

        View ll_test = View.inflate(this,R.layout.ll_test,null);
        myViewpager.addView(ll_test,2);

        for (int i=0;i<myViewpager.getChildCount();i++){
            RadioButton radioButton = new RadioButton(this);
            radioButton.setId(i);

            if(i==0)
                radioButton.setChecked(true);

            rg_myvp.addView(radioButton);
        }

        rg_myvp.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                myViewpager.setPagerId(checkedId);
            }
        });

        myViewpager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                ((RadioButton)rg_myvp.getChildAt(i)).setChecked(true);
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }
}
