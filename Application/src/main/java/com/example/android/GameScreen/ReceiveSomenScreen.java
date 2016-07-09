package com.example.android.GameScreen;

import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.WindowManager;

public class ReceiveSomenScreen extends Fragment{
    // スレッドクラス
    Thread mainLoop = null;
    // 描画用
    Paint paint = null;
    // 画面サイズ取得
    Point displayPoint = new Point(0, 0);
    //タッチイベント用x座標、y座標
    Point eventRange = new Point(0, 0);
    //移動画像の停止フラグ
    Boolean isStop = false;
    //タップフラグ
    Boolean isTach = false;

    Context context;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        LinearLayout ll = new LinearLayout(getActivity());
        TextView tv = new TextView(getActivity());
        tv.setText("フラグメントReceiveSomenScreen画面！");
        ll.addView(tv);
        return ll;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowManager wm = (WindowManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        //WindowManager wm = (WindowManager) getSystemService(WINDOW_SERVICE);
        // ディスプレイサイズを取得する.
        Display disp = wm.getDefaultDisplay();
        disp.getSize(displayPoint);
        displayPoint.set(displayPoint.x, displayPoint.y);
        //描画Viewを画面に設定する.
        //setContentView(new DrawSurfaceView(this));
    }

    // SurfaceViewを描画するクラス
    class DrawSurfaceView extends SurfaceView implements SurfaceHolder.Callback, Runnable {

        // 円のX,Y座標
        private int circleX = displayPoint.x / 2;
        private int circleY = 0;
        // 円の移動量
        private int circleVx = 5;
        private int circleVy = 5;
        private int range = 40;

        public DrawSurfaceView(Context context) {
            super(context);
            // SurfaceView描画に用いるコールバックを登録する。
            getHolder().addCallback(this);
            // 描画用の準備
            paint = new Paint();
            paint.setColor(Color.WHITE);
            // スレッド開始
            mainLoop = new Thread(this);
            mainLoop.start();
        }

        @Override
        public void surfaceChanged(SurfaceHolder holder, int format, int width,
                                   int height) {
            // TODO 今回は何もしない。
        }

        @Override
        public void surfaceCreated(SurfaceHolder holder) {
            // SurfaceView生成時に呼び出されるメソッド。
            // 今はとりあえず背景を白にするだけ。
            Canvas canvas = holder.lockCanvas();
            canvas.drawColor(Color.BLACK);
            holder.unlockCanvasAndPost(canvas);
        }

        @Override
        public void surfaceDestroyed(SurfaceHolder holder) {
            // TODO 今回は何もしない。
        }

        @Override
        public void run() {
            // Runnableインターフェースをimplementsしているので、runメソッドを実装する
            while (!isStop) {
                isTach = false;
                Canvas canvas = getHolder().lockCanvas();
                if (canvas != null) {
                    canvas.drawColor(Color.BLACK);
                    // 円を描画する
                    canvas.drawCircle(circleX, circleY, 30, paint);
                    // 青の太い線
                    paint.setColor(Color.WHITE);
                    paint.setStrokeWidth(10);
                    float[] pts2 = {0, displayPoint.y / 2 + range, displayPoint.x, displayPoint.y / 2 + range};
                    canvas.drawLines(pts2, paint);

                    float[] pts3 = {0, displayPoint.y / 2 - range, displayPoint.x, displayPoint.y / 2 - range};
                    canvas.drawLines(pts3, paint);

                    getHolder().unlockCanvasAndPost(canvas);

                    // 円の座標を移動させる
                    circleY += circleVy;
                    // 画面の領域を超えた？
                    if (circleY < 0 || getHeight() < circleY) circleVy *= -1;
                }

                if (circleY <= displayPoint.y / 2 + range && circleY >= displayPoint.y / 2 - range) {
                    if (isTach) {
                        isStop = true;
                        Log.i("ログ", "移動画像とタッチ位置がマッチしました");
                    }
                }
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Thread.interrupted();
    }

//    @Override
//    public boolean onTouchEvent(MotionEvent event) {
//        Log.i("ログ", "タッチイベント発生");
//        isTach = true;
//        return true;
//    }
}
