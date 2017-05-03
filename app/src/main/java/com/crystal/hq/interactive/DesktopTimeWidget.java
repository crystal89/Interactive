package com.crystal.hq.interactive;

import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.widget.RemoteViews;

/**
 * Created by 102003449 on 2017/5/2.
 */

public class DesktopTimeWidget extends AppWidgetProvider {
    @Override
    public void onReceive(Context context, Intent intent) {
        //每接收一次广播消息就调用一次
        System.out.print("receive++++++++++++++++++++++++++");
        super.onReceive(context, intent);
    }

    //每次更新都调用一次该方法
    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);

        Intent in = new Intent(context, UpdateService.class);
        context.startService(in);

        System.out.print("update+++++++++++++++++++++++++++");
    }

    //当widget第一次添加到桌面时调用，可添加多次但只有第一次调用
    @Override
    public void onEnabled(Context context) {
        super.onEnabled(context);
    }

    //当最后一个该widget删除时调用
    @Override
    public void onDisabled(Context context) {
        super.onDisabled(context);
    }

    //没删除一次就调用一次
    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        super.onDeleted(context, appWidgetIds);
    }

    public static class UpdateService extends Service {
        @Override
        public IBinder onBind(Intent intent) {
            return null;
        }

/*        @Override
        public void onStart(Intent intent, int startId) {
            super.onStart(intent, startId);

            //获取widget的view
            RemoteViews updateViews = new RemoteViews(this.getPackageName(), R.layout.time_widget_layout);

            SimpleDateFormat sdf = new SimpleDateFormat("hh:mm");
            updateViews.setTextViewText(
                    R.id.tv_timeWidget,
                    "" + sdf.format(new Date())
            );
            ComponentName widget = new ComponentName(this, DesktopTimeWidget.class);
            AppWidgetManager manager = AppWidgetManager.getInstance(this);
            manager.updateAppWidget(widget, updateViews);
        }*/

        @Override
        public int onStartCommand(Intent intent, int flags, int startId) {
            System.out.print("updateservice - onstartcommand ++++++++++++++++++++++++++++");
            //获取widget的view
            RemoteViews updateViews = new RemoteViews(UpdateService.this.getPackageName(), R.layout.time_widget_layout);
            updateViews.setTextViewText(R.id.tv_timeWidget, "你好，这是一个测试");
            ComponentName widget = new ComponentName(UpdateService.this, DesktopTimeWidget.class);
            AppWidgetManager manager = AppWidgetManager.getInstance(UpdateService.this);
            manager.updateAppWidget(widget, updateViews);
            return super.onStartCommand(intent, flags, startId);
        }
    }
}
