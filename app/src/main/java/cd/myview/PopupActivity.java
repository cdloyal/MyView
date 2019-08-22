package cd.myview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import java.util.ArrayList;

public class PopupActivity extends AppCompatActivity {
    private static final String TAG = PopupActivity.class.getSimpleName();
    private EditText et_input;
    private ImageView iv_down_arrow;
    private PopupWindow popupWindow;
    private ListView listview;
    private ArrayList<String> lvData;
    private MyListAdapter myListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popup);

        et_input = findViewById(R.id.et_input);
        iv_down_arrow = findViewById(R.id.iv_down_arrow);

        //listview数据
        lvData = new ArrayList<>();
        for(int i=0;i<500;i++)
            lvData.add(i+"--msg--"+i);
        //
        listview = new ListView(this);
        listview.setBackgroundResource(R.drawable.listview_background);
        myListAdapter = new MyListAdapter();
        listview.setAdapter(myListAdapter);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ViewHolder viewHolder = (ViewHolder) view.getTag();
                Log.d(TAG,"viewHolder="+viewHolder.tv_msg.getText());
                et_input.setText(viewHolder.tv_msg.getText());
                if(popupWindow!=null && popupWindow.isShowing()){
                    popupWindow.dismiss();
                    popupWindow=null;
                }
            }
        });

        et_input.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(popupWindow==null){
                    popupWindow = new PopupWindow(PopupActivity.this);
                    popupWindow.setWidth(et_input.getWidth());
//                    popupWindow.setHeight(200);     //这里代表像素，在大分辨率下，单位尺寸含有的像素多，就显得小
                    popupWindow.setHeight(DensityUtil.dip2px(PopupActivity.this,200));     //200dp转成px


                    popupWindow.setContentView(listview);
                    popupWindow.setFocusable(true);     //设置焦点
                }

                popupWindow.showAsDropDown(et_input,0,0);   //设置
            }
        });


    }

    private class MyListAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return lvData.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            Log.d(TAG,"getView position="+position+", convertView="+convertView);
            ViewHolder viewHolder;
            if(convertView==null){
                convertView = View.inflate(PopupActivity.this,R.layout.item_popup,null);
                viewHolder = new ViewHolder();
                viewHolder.tv_msg= convertView.findViewById(R.id.tv_msg);
                viewHolder.iv_delete= convertView.findViewById(R.id.iv_delete);
                convertView.setTag(viewHolder);
            }else {
                viewHolder = (ViewHolder) convertView.getTag();
            }

            viewHolder.tv_msg.setText(lvData.get(position));
            viewHolder.iv_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //1.从集合删除
                    lvData.remove(position);
                    //2.删除ui,刷新适配器
                    myListAdapter.notifyDataSetChanged();   //getCount()--getView()，重新渲染
                }
            });


            return convertView;
        }
    }

    static class ViewHolder{
        TextView tv_msg;
        ImageView iv_delete;
    }

}
