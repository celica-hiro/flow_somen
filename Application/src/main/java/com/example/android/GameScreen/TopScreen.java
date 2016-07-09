package com.example.android.GameScreen;

import android.app.Activity;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by ishiiyoshikazu on 16/07/07.
 */
public class TopScreen extends Activity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LinearLayout ll = new LinearLayout(this);
        ll.setOrientation(LinearLayout.VERTICAL);
        setContentView(ll);

        TextView tv  = new TextView(this);
        tv.setText("トップ画面");
        ll.addView(tv);
    }
}
