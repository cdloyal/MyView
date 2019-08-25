package cd.myview;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class SlideMenuActivity extends AppCompatActivity {


    private ListView iv_slm;
    private ArrayList<MyBean> myBeans;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slide_menu);



        myBeans = new ArrayList<>();
        for(int i=0;i<50;i++)
            myBeans.add(new MyBean("content-----"+i));

        MyListAdapter myListAdapter = new MyListAdapter(this);
        myListAdapter.setList(myBeans);
        iv_slm = findViewById(R.id.iv_slm);
        iv_slm.setAdapter(myListAdapter);
    }

    private class MyListAdapter extends BaseAdapter {

        private ArrayList<MyBean> list;
        private Context context;
        private SlideLayout slideLayoutOpened;

        MyListAdapter(Context context){
            this.context = context;
        }

        public void setList(ArrayList<MyBean> list){
            this.list = list;
        }


        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;
            if(convertView==null){
                //实例化
                convertView = View.inflate(context,R.layout.item_slidemenu,null);
                viewHolder = new ViewHolder();
                viewHolder.itemContent = convertView.findViewById(R.id.item_content);
                viewHolder.itemMenu = convertView.findViewById(R.id.item_menu);
            }else {
                viewHolder = (ViewHolder) convertView.getTag();
            }

            viewHolder.itemContent.setText(list.get(position).getSlideContent());
            viewHolder.itemContent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context,((TextView)v).getText(),Toast.LENGTH_SHORT).show();
                }
            });
            viewHolder.itemMenu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    /**
                     * delete，只是数据改变了，视图没有改变，重新getView(),填充数据
                     * 对应的item还是openMenu的，解决方案是先closeMenu
                     * */
                    SlideLayout slideLayout = (SlideLayout) v.getParent();
                    slideLayout.closeMenu();

                    list.remove(position);
                    notifyDataSetChanged();
                }
            });

            ((SlideLayout)convertView).setOnStatusChangeLinstener(new SlideLayout.OnStatusChangeLinstener() {
                @Override
                public void onTouch(SlideLayout slideLayout) {
                    //关闭已经打开的Menu
                    if(slideLayoutOpened!=null && slideLayout!=slideLayoutOpened)
                        slideLayoutOpened.closeMenu();
                }

                @Override
                public void onMenuOpen(SlideLayout slideLayout) {
                    slideLayoutOpened = slideLayout;
                }

                @Override
                public void onMenuClose(SlideLayout slideLayout) {
                    slideLayoutOpened=null;
                }
            });

            convertView.setTag(viewHolder);
            return convertView;
        }

        @Override
        public int getCount() {
            return list==null?0:list.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        class ViewHolder{
            TextView itemContent;
            TextView itemMenu;
        }

    }
}
