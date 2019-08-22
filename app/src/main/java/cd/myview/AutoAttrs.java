package cd.myview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;

/**
 * 作者：chenda
 * 时间：2019/8/21:19:02
 * 邮箱：
 * 说明：
 */
public class AutoAttrs extends View {

    private String name;
    private Bitmap bitmap;
    public AutoAttrs(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray a = context.obtainStyledAttributes(
                attrs, R.styleable.AutoAttrs);
        for(int i=0; i<a.getIndexCount();i++){
            int index = a.getIndex(i);
            switch (index){
                case R.styleable.AutoAttrs_my_name:
                    name = a.getString(index);
                    break;
                case R.styleable.AutoAttrs_my_bg:
                    Drawable drawable = a.getDrawable(index);
                    BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
                    bitmap = bitmapDrawable.getBitmap();
                    break;
            }
        }
        a.recycle();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint paint = new Paint();
        canvas.drawText(name,50,50,paint);
        canvas.drawBitmap(bitmap,70,70,paint);
    }
}
