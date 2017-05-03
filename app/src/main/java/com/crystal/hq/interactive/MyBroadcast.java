package com.crystal.hq.interactive;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by 102003449 on 2017/4/26.
 */

public class MyBroadcast extends BroadcastReceiver {

    public static final String flag = "MyBroadcast";

    @Override
    public void onReceive(Context context, Intent intent) {
        String msg = intent.getStringExtra("MSG");
        Log.i(flag,msg);
        Toast.makeText(context, msg,Toast.LENGTH_LONG).show();
    }

//    @Override
//    public IBinder peekService(Context myContext, Intent service) {
//        return super.peekService(myContext, service);
//    }
}
