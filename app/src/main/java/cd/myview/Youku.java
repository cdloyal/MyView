package cd.myview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class Youku extends AppCompatActivity {

    private ImageView icon_menu,icon_home;
    private RelativeLayout level1,level2,level3;
    private boolean islevel1Show = true;
    private boolean islevel2Show = true;
    private boolean islevel3Show = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_youku);

        icon_menu = findViewById(R.id.icon_menu);
        icon_home = findViewById(R.id.icon_home);
        level1 = findViewById(R.id.level1);
        level2 = findViewById(R.id.level2);
        level3 = findViewById(R.id.level3);
        MyOnclick myOnclick = new MyOnclick();
        icon_menu.setOnClickListener(myOnclick);
        icon_home.setOnClickListener(myOnclick);
    }

    private class MyOnclick implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.icon_home:
                    //如果三级菜单和二级菜单时显示的，都设置隐藏
                    //如果都是隐藏的，二级菜单显示
                    if(islevel2Show){
                        islevel2Show=false;
                        ViewTool.hideView(level2,0);
                        if(islevel3Show){
                            //隐藏
                            ViewTool.hideView(level3,200);
                            islevel3Show = false;
                        }
                    }else {
                        islevel2Show=true;
                        ViewTool.showView(level2);
                    }
                    break;
                case R.id.icon_menu:
                    if(islevel3Show){
                        //隐藏
                        ViewTool.hideView(level3,0);
                        islevel3Show = false;
                    }else {
                        //显示
                        ViewTool.showView(level3);
                        islevel3Show = true;
                    }
                    break;
            }
        }
    }
}
