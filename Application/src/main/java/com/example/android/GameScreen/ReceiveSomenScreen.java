package com.example.android.GameScreen;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.example.android.bluetoothchat.MainActivity;
import com.example.android.bluetoothchat.R;

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
    Boolean isTouch = false;

    Bitmap bmp = null;
    Bitmap soumen = null;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        DrawSurfaceView dsv = new DrawSurfaceView(getActivity());
        dsv.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                Log.i("ログ", "タッチイベント発生");
                isTouch = true;
                Log.i("ログ","タッチフラグ" + isTouch.toString());
                return true;
            }
        });
        return dsv;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    // SurfaceViewを描画するクラス
    class DrawSurfaceView extends SurfaceView implements SurfaceHolder.Callback, Runnable {

        // 円のX,Y座標
        private int circleX = MainActivity.displayWidth / 2;
        private int circleY = 0;
        // 円の移動量
        private int circleVy = 5;
        private int range = 40;

        public DrawSurfaceView(Context context) {
            super(context);
            bmp = BitmapFactory.decodeResource(getResources(), R.drawable.flow_take);
            soumen = BitmapFactory.decodeResource(getResources(), R.drawable.soumen);
            // SurfaceView描画に用いるコールバックを登録する。
            getHolder().addCallback(this);
            // 描画用の準備
            paint = new Paint();
            paint.setColor(Color.BLACK);
        }

        @Override
        public void surfaceChanged(SurfaceHolder holder, int format, int width,
                                   int height) {
            //画面サイズを設定する.
            displayPoint.set(width,height);
        }

        @Override
        public void surfaceCreated(SurfaceHolder holder) {
            // SurfaceView生成時に呼び出されるメソッド。
            // 今はとりあえず背景を白にするだけ。
            Canvas canvas = holder.lockCanvas();
            canvas.drawColor(Color.BLACK);
            holder.unlockCanvasAndPost(canvas);
            // スレッド開始
            mainLoop = new Thread(this);
            mainLoop.start();
        }

        @Override
        public void surfaceDestroyed(SurfaceHolder holder) {
            // TODO 今回は何もしない。
            isStop = true;
        }

        @Override
        public void run() {
            // Runnableインターフェースをimplementsしているので、runメソッドを実装する
            while (!isStop) {
                Canvas canvas = getHolder().lockCanvas();
                if (canvas != null) {
                    canvas.drawColor(Color.WHITE);

                    canvas.drawBitmap(bmp, 0, 0, null);
                    // 円を描画する
                    //canvas.drawCircle(circleX, circleY, 30, paint);
                    canvas.drawBitmap(soumen, circleX-200, circleY, null);
                    // 黒い太い線
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
                    Log.i("ログ", "移動画像とタッチ位置がマッチしました");
                    if (isTouch) {
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

}
