package com.example.android.bluetoothchat;

import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.Display;
import android.view.WindowManager;

import com.example.android.GameScreen.ReceiveSomenScreen;
import com.example.android.GameScreen.SendSomenScreen;

/**
 * A simple launcher activity containing a summary sample description, sample log and a custom
 * {@link android.support.v4.app.Fragment} which can display a view.
 * <p>
 * For devices with displays with a width of 720dp or greater, the sample log is always visible,
 * on other devices it's visibility is controlled by an item on the Action Bar.
 * 要約サンプル記述、サンプル・ログとカスタムを含む単純なランチャー活動
 * どのビューを表示することができます。
 * 720dp以上の幅を持つディスプレイを備えたデバイスの場合、サンプル・ログは、常に表示されます
 * 他のデバイス上では視認性がアクションバーの項目によって制御されます。
 */
public class MainActivity extends FragmentActivity {

    public static final String TAG = "MainActivity";

    //受け手、送り手画面のフラグ
    //0 : 未設定
    //1 : 送り手
    //2 : 受け手
    public static int currentScreen;

    //Activityの最初に呼ばれる、メインで記述するメソッド
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //トップ画面から受け手、送り手フラグを受け取る
        Intent intent = getIntent();
        currentScreen = intent.getIntExtra("CURRENTSCREEN", 0);

        //Fragmentを追加、削除、他のFragmentと差し替えたりという一連の操作は、FragmentTransactionを使う
        if (savedInstanceState == null) {
            //Fragmentを使用する時に以下2行がデフォルトみたい
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            BluetoothChatFragment fragment = new BluetoothChatFragment();
            //ActivityへFragmentを組み込む
            transaction.replace(R.id.main_content_fragment, fragment);

            Fragment somenScreenFragment = new Fragment();
            if(currentScreen==1) somenScreenFragment = new SendSomenScreen();
            else if(currentScreen==2) somenScreenFragment = new ReceiveSomenScreen();
            transaction.add(R.id.somen_screen, somenScreenFragment);
            //決定
            transaction.commit();
        }

    }
    //TODO 別の用途でメニューバーを使う時の為に残す
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.main, menu);
//        return true;
//    }
}
