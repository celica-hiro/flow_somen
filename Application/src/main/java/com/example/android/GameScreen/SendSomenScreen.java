package com.example.android.GameScreen;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by ishiiyoshikazu on 16/07/07.
 */
public class SendSomenScreen{

    public View CreateView(Context context){
        LinearLayout ll = new LinearLayout(context);
        TextView tv = new TextView(context);
        tv.setText("SendSomenScreen画面！");
        ll.addView(tv);
        return ll;
    }

//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        LinearLayout ll = new LinearLayout(getActivity());
//
//        TextView tv = new TextView(getActivity());
//        tv.setText("SendSomenScreen画面！");
//
//        ll.addView(tv);
//    }
}
