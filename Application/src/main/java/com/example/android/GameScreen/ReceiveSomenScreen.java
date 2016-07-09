package com.example.android.GameScreen;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by ishiiyoshikazu on 16/07/07.
 */
public class ReceiveSomenScreen {
    public View CreateView(Context context){
        LinearLayout ll = new LinearLayout(context);
        TextView tv = new TextView(context);
        tv.setText("ReceiveSomenScreen画面！");
        ll.addView(tv);
        return ll;
    }
}
