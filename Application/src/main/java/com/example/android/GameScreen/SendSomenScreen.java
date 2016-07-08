package com.example.android.GameScreen;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Point;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.RelativeLayout;


import java.util.List;
import java.util.Random;

/**
 * Created by ishiiyoshikazu on 16/07/07.
 */
public class SendSomenScreen extends Activity implements Runnable, SensorEventListener {
    SensorManager manager;
    com.example.android.GameScreen.Ball ball;
    Handler handler;
    int width, height, time;
    float gx, gy, dpi;
    FrameLayout framelayout;
    TextView tv;
    float x;		 	//タッチしたＸ座標
    float y; 			 	//タッチしたＹ座標
    float size;		 	//押された画面領域サイズ
    float press;   	//画面が押された際の圧力
    int count; 	//画面タッチ数の取得
    long downTime; 	//押されていた時間(ms単位)の取得
    int actionID;		//タッチイベントのアクション種別

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        framelayout = new FrameLayout(this);
        framelayout.setBackgroundColor(Color.GREEN);
        setContentView(framelayout);

        tv = new TextView(this);
        framelayout.addView(tv);

        time = 10;
        handler = new Handler();
        handler.postDelayed(this, 300);
        WindowManager windowManager =
                (WindowManager) getSystemService(WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();
        width = display.getWidth();
        height = display.getHeight();
        dpi = getResources().getDisplayMetrics().densityDpi;

        ball = new com.example.android.GameScreen.Ball(this);
        ball.x = width / 1;
        ball.y = height / 2;

        framelayout.addView(ball);
    }
    @Override
    public void run() {

        if (actionID == 1 && gx < 0) {
            ball.vx += (float) (gx * time / 10000);
            ball.x += dpi * ball.vx * time / 25.4;
        }
        if (ball.x <= ball.radius) {
            ball.x = ball.radius;
            ball.vx = -ball.vx / 3;
        } else if (ball.x >= width - ball.radius) {
            ball.x = width - ball.radius;
            ball.vx = -ball.vx / 3;
        }

        ball.invalidate();
        handler.postDelayed(this, time);
    }
    public void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(this);
    }
    @Override
    protected void onResume() {
        super.onResume();
        manager = (SensorManager)getSystemService(
                SENSOR_SERVICE);
        List<Sensor> sensors =
                manager.getSensorList(
                        Sensor.TYPE_ACCELEROMETER);
        if (0 < sensors.size()) {
            manager.registerListener(
                    this, sensors.get(0),
                    SensorManager.SENSOR_DELAY_NORMAL);
        }
    }
    @Override
    protected void onPause() {
        super.onPause();
        manager.unregisterListener(this);
    }
    @Override
    public void onSensorChanged(SensorEvent event) {
        gx = event.values[1];
    }
    @Override
    public void onAccuracyChanged(
            Sensor sensor, int accuracy) {
    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // TODO 自動生成されたメソッド・スタブ
        x = event.getX();				 	//タッチしたＸ座標
        y = event.getY(); 			 	//タッチしたＹ座標
        size = event.getSize();		 	//押された画面領域サイズ
        press = event.getPressure();   	//画面が押された際の圧力
        count = event.getPointerCount(); 	//画面タッチ数の取得
        downTime = event.getDownTime(); 	//押されていた時間(ms単位)の取得
        actionID = event.getAction();		//タッチイベントのアクション種別
        String actionKind = "";
        switch (actionID) {
            case MotionEvent.ACTION_DOWN:
                //タッチされた
                actionKind += "ACTION_DOWN";
                break;
            case MotionEvent.ACTION_UP:
                //タッチが離れた
                actionKind += "ACTION_UP";
                break;
            case MotionEvent.ACTION_MOVE:
                //タッチしたまま動いた
                actionKind += "ACTION_MOVE";
                break;
            case MotionEvent.ACTION_CANCEL:
                //タッチがキャンセルされた
                actionKind += "ACTION_CANCEL";
                break;
            default :
                //ほかの操作
                actionKind += "default";
                break;
        }

        String message = String.format(
                "x=%s y=%s size=%s press=%s count=%s downTime=%s action=%s"
                ,String.valueOf(x)
                ,String.valueOf(y)
                ,String.valueOf(size)
                ,String.valueOf(press)
                ,String.valueOf(count)
                ,String.valueOf(downTime)
                ,actionKind);
        return super.onTouchEvent(event);
    }

}
