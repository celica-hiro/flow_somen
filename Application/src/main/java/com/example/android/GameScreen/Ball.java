package com.example.android.GameScreen;

/**
 * Created by Jaemin on 2016/07/08.
 */
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

/**
 * Created by Jaemin on 2016/07/01.
 */
public class Ball extends View {
    int x, y, radius;
    float vx, vy;
    Paint paint;

    public Ball(Context context) {
        super(context);
        radius = 50;
        vx = vy = x = y = 0;
        paint = new Paint();
        paint.setColor(Color.WHITE);
        paint.setStyle(Paint.Style.FILL);
    }
    protected void onDraw(Canvas canvas){
        super.onDraw(canvas);
        canvas.drawCircle(x, y, radius, paint);
    }
}
