package cd.myview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class AutoAttrsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auto_attrs);

        /**
         * 常用属性：    在SDK\platforms\android-28\data\res\values\attrs.xml
         *      reference   引用
         *      color       颜色
         *      boolean     布尔值
         *      dimension   尺寸值
         *      float       浮点值
         *      integer     整形值
         *      string      字符型
         *      enum        枚举
         *
         * 自定义属性：
         *  自定义view
         *  布局中添加自定义view
         *      添加属性    xxx:yyy=
         *  新建sttrs.xml,定义属性集合
         *      <declare-styleable
         *
         * 进程从布局文件中的到View名，通过反射调用view的构造方法，并传进属性，从而实例化view类
         *
         * 获取属性的三种方式
         *  1、用命名控件取属性
         *      String age = attrs.getAttributeValue("http://schemas.android.com/apk/res-auto","my_age");
         *  2、遍历属性集合取属性
         *      for(int i=0;i<attrs.getAttributeCount();i++){
         *             System.out.println(attrs.getAttributeName(i)+"====="+attrs.getAttributeValue(i));
         *         }
         *  3、使用系统工具取属性
         *      TypedArray typedArray = context.obtainStyledAttributes(attrs,R.styleable.MyAttributeView);
         * */


    }
}
