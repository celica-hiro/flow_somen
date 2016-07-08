package com.example.android.bluetoothchat;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import com.example.android.common.activities.SampleActivityBase;

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
public class MainActivity extends SampleActivityBase {

    public static final String TAG = "MainActivity";

    //Activityの最初に呼ばれる、メインで記述するメソッド
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Fragmentを追加、削除、他のFragmentと差し替えたりという一連の操作は、FragmentTransactionを使う
        if (savedInstanceState == null) {
            //Fragmentを使用する時に以下2行がデフォルトみたい
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            BluetoothChatFragment fragment = new BluetoothChatFragment();
            //ActivityへFragmentを組み込む
            transaction.replace(R.id.main_content_fragment, fragment);
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
