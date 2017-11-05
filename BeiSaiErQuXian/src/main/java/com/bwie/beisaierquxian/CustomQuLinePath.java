package com.bwie.beisaierquxian;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
/**
 * 贝赛尔曲线的简单使用,详细参考：https://github.com/GcsSloop/AndroidNote
 * 贝塞尔曲线奠定了计算机绘图的基础(因为它可以将任何复杂的图形用精确的数学语言进行描述
 * 一阶曲线： 是一条线段，非常简单。
   二阶曲线： 我们知道二阶曲线是由两个数据点，一个控制点构成。两个数据点是控制贝塞尔曲线开始和结束的位置，比较容易理解，
             而控制点则是控制贝塞尔的弯曲状态
   三阶曲线：由两个数据点和两个控制点来控制曲线状态。
 */
public class CustomQuLinePath extends View{
    private PointF start, end, control1, control2;
    private int centerX, centerY;
    private boolean mode = true;
    private Paint paint;

    public CustomQuLinePath(Context context) {
        super(context);
    }

    public CustomQuLinePath(Context context, AttributeSet attrs) {
        super(context, attrs);
        //创建画笔,设置画笔颜色,设置空心线宽（参数width为线宽，浮点型数据）
        paint = new Paint();
        paint.setColor(Color.GREEN);
        paint.setStrokeWidth(18);
        paint.setStyle(Paint.Style.STROKE);
        paint.setTextSize(58);

        //开始，终点坐标
        start = new PointF(0, 0);
        end = new PointF(0, 0);
        control1 = new PointF(0, 0);
        control2 = new PointF(0, 0);
    }

    public CustomQuLinePath(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    float x ;
    float y ;

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        centerX = w/2;
        centerY = h/2;

        // 初始化数据点和控制点的位置
        start.x = centerX-200;
        start.y = centerY;
        end.x = centerX + 200;
        end.y = centerY;

        control1.x = centerX;
        control1.y = centerY - 100;

        control2.x = centerX;
        control2.y = centerY - 100;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // 根据触摸位置更新控制点，并提示重绘
        if (mode){
            control1.x = event.getX();
            control1.y = event.getY();
        }else {
            control2.x = event.getX();
            control2.y = event.getY();
        }
        invalidate();
        return true;
    }

    public boolean getMode() {
        return mode;
    }
    public void setMode(boolean mode) {
        this.mode = mode ;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // 绘制数据点和控制点
        paint.setColor(Color.GRAY);
        paint.setStrokeWidth(20);
        canvas.drawPoint(start.x, start.y, paint);
        canvas.drawPoint(end.x, end.y, paint);
        canvas.drawPoint(control1.x, control1.y, paint);
        canvas.drawPoint(control2.x, control2.y, paint);

        // 绘制辅助线
        paint.setStrokeWidth(4);
        canvas.drawLine(start.x, start.y, control1.x, control1.y, paint);
        canvas.drawLine(control1.x, control1.y,control2.x, control2.y, paint);
        canvas.drawLine(control2.x, control2.y,end.x, end.y, paint);

        // 绘制贝塞尔曲线
        paint.setColor(Color.RED);
        paint.setStrokeWidth(8);

        Path path = new Path();

        path.moveTo(start.x, start.y);
        path.cubicTo(control1.x, control1.y, control2.x,control2.y, end.x, end.y);

        canvas.drawPath(path, paint);
    }
}
