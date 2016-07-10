package com.example.android.GameScreen;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
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
import android.widget.TextView;

import com.example.android.bluetoothchat.MainActivity;
import com.example.android.bluetoothchat.BluetoothChatFragment;
import com.example.android.bluetoothchat.R;

public class SendSomenScreen extends Fragment{
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

    double count;

    Bitmap bmp = null;
    Bitmap soumen = null;

    private MediaPlayer mp;
    SoundPool soundPool;
    int sound1, sound2;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        DrawSurfaceView dsv = new DrawSurfaceView(getActivity());
        dsv.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                soundPool.play(sound1, 1.0F, 1.0F, 0, 0, 1.0F);
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
        mp = MediaPlayer.create(getActivity(), R.raw.shower);
        mp.start();
        soundPool = new SoundPool( 1, AudioManager.STREAM_MUSIC, 0 );
        sound2 = soundPool.load(getActivity(), R.raw.se_chime, 1);
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
                    canvas.drawBitmap(soumen, circleX-200, circleY, null);

                    getHolder().unlockCanvasAndPost(canvas);

                    // 円の座標を移動させる
                    if(isTouch) {
                        circleY += circleVy;
                    }
                    if (circleY < 0 || getHeight() < circleY) {
                        BluetoothChatFragment bcf = new BluetoothChatFragment();
                        Log.i("log","log" + bcf);
                        bcf.sendMessage("1");
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
