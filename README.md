文本控件：TextView、EditText
按钮控件：Button ImageButton
进度条：ProgressBar
单选按钮：RadioButton、RadioGroup
复选按钮：CheckBox
状态开关按钮：TogleButton
时钟控件:AnalogClock DigitalClock
日期和时钟选择控件DatePicker TimePicker

系统控件重新组合
自定义类继承view
自定义继承viewgroup


View Animation 补间动画（普通动画、视图动画)，视图过去了，属性还在
Drawable Animation 帧动画
Property Animation 属性动画


屏幕适配
hdpi    高密度比
mdpi    中密度比
lhdpi   低密度比

我们在低密度比的手机加载高密度比的图片时，为了防止内存溢出，系统会将hdpi的图片进行压缩显示

View的测量
    假设我们xml文件中定义的view指定了背景blackground或者最小尺寸minWidth
    在测量onMeasure()方法中可以通过getSuggestMinimunWidth获取布局中最小尺寸
    onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    可以获得父布局给我们的specMode和specSize
    mode = AT_MOST,     父布局(wrap_content)包裹子布局，大小受子布局影响。size是父布局给子布局最大的尺寸
    mode = EXACTLY      父布局希望子布局(fill_parent)的大小 clild.size=specSize
    mode = UNSPECIFIED  子布局大小不受限制

    private int setMeasure(int size,int measureSpec){
            int specMode = MeasureSpec.getMode(measureSpec);
            int specSize = MeasureSpec.getSize(measureSpec);
            int result = size;

            switch (specMode){
                case MeasureSpec.AT_MOST:       //wrap_content,父控件大小受子控件影响，specSize父控件给的控件最大值
                    if(specSize<size){
                        //当specMode为AT_MOST，并且父控件指定的尺寸specSize小于View自己想要的尺寸时，
                        //我们就会用掩码MEASURED_STATE_TOO_SMALL向量算结果加入尺寸太小的标记
                        //这样其父ViewGroup就可以通过该标记其给子View的尺寸太小了，
                        //然后可能分配更大一点的尺寸给子View
                        result = size | MEASURED_STATE_TOO_SMALL;
                    }else {
                        result = size;
                    }
                    break;
                case MeasureSpec.EXACTLY:       //fill_parent,8dp，父控件大小希望子控件的大小为specSize
                    result = specSize;
                    break;
                case MeasureSpec.UNSPECIFIED:   //子控件大小不受限制
                    break;
                default:
                    result = size;
            }
            return result;
        }

View的Draw()
    可以根据上面测量的width和height自适应

viewGroup的测量
    调用childView.measure()，对子View进行测量
    for(int i=0;i<getChildCount();i++){
                View child = getChildAt(i);
                child.measure(widthMeasureSpec,heightMeasureSpec);
            }


viewGroup的onlay()，为子View指定位置


onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    //https://www.cnblogs.com/yishujun/p/5560838.html
    //https://blog.csdn.net/iispring/article/details/49403315
    //MeasureSpec封装父布局
    //父布局给的尺寸和模式
    int widthMode = MeasureSpec.getMode(widthMeasureSpec);
    int widthSize = MeasureSpec.getSize(widthMeasureSpec);
    int heightMode = MeasureSpec.getMode(heightMeasureSpec);
    int heightSize = MeasureSpec.getSize(heightMeasureSpec);

    match_parent    填充
    wrap_content    包裹
    8dp             固定的

    MeasureSpec.AT_MOST;        子元素最大达到指定大小值，（当设置为wrap_content时，模式为AT_MOST, 表示子view的大小最多是多少，这样子view会根据这个上限来设置自己的尺寸）
    MeasureSpec.UNSPECIFIED;    父元素对子元素没限制，
    MeasureSpec.EXACTLY;        表示父视图希望子视图的大小应该是由specSize的值来决定的，系统默认会按照这个规则来设置子视图的大小，简单的说（当设置width或height为match_parent时，模式为EXACTLY，因为子view会占据剩余容器的空间，所以它大小是确定的）



事件消费和传递
//MotionEvent.ACTION_DOWN过来一个事件，调onInterceptTouchEvent()要不要拦截，哦，不拦截
//MotionEvent.ACTION_MOVE过来，onInterceptTouchEvent()要不要拦截，哦，拦截
//拦截之后剩下的Event交给onTouchEvent()处理
//即每个事件过来都问你要不要拦截

    dispatchTouchEvent方法用于事件的分发，返回true表示不继续分发，但事件没有被消费。返回false则继续往下分发。
如果是ViewGroup则分发给onInterceptTouchEvent进行判断是否拦截该事件。

    onInterceptTouchEvent是ViewGroup才有的方法，负责事件的拦截，交给OnTouchEvent()。返回true表示拦截当前事件，不继续分发。

    onTouchEvent方法用于事件的处理，返回true消费当前事件，返回false则不处理，交给子空间继续分发