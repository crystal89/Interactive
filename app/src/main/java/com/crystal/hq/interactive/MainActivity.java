package com.crystal.hq.interactive;

import android.app.Activity;
import android.appwidget.AppWidgetProvider;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.MotionEvent;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.ViewFlipper;

public class MainActivity extends Activity {

    private Button btn_interactive;
    private TextView tv_interactiveInfo;

    private ViewFlipper myVF;
    private ImageView iv01;
    private float oldTouchValue = 0.0f;

    private ProgressBar progressBar;
    private TextView progressInfo;
    private Button progressBtn;
    int inCounter = 0;
    protected static final int GUI_STOP_NOTIFIER = 0x108;
    protected static final int GUI_THREADING_NOTIFIER = 0x109;

    Handler phandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case GUI_STOP_NOTIFIER:
                    progressInfo.setText(R.string.loadEnd);
                    progressBar.setVisibility(View.GONE);
                    Thread.currentThread().interrupt();
                    break;
                case GUI_THREADING_NOTIFIER:
                    if (!Thread.currentThread().isInterrupted()) {
                        progressBar.setProgress(inCounter);
                        progressInfo.setText(getResources().getText(R.string.loading) + Integer.toString(inCounter) + "%\n"
                                + "Progress:" + Integer.toString(progressBar.getProgress()) + "\n"
                                + "Indeterminate:" + Boolean.toString(progressBar.isIndeterminate())
                        );
                    }
                    break;
            }
            super.handleMessage(msg);
        }
    };

    MyBroadcast mbc;

    private GridView gvlist;
    private String[] gvitems1, gvitems2;
    private ArrayAdapter<String> gvAdapter;
    private Button twoBtn;
    private Button threeBtn;
    private TextView gvshowItem;

    private ImageView iv02;
    private Button rotleft;
    private Button rotRight;
    private int scaleTimes;
    private int rotAngle;

    enum rotDir {NORMAl, LEFT, RIGHT}

    ;
    private rotDir rotdir = rotDir.NORMAl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /*全屏幕画面*/
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
        );
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_main);

        //用来显示progress
        progressInfo = (TextView) findViewById(R.id.p_info);
        progressBar = (ProgressBar) findViewById(R.id.p_bar);
        progressBar.setIndeterminate(false);
        progressBtn = (Button) findViewById(R.id.p_btn);
        progressBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                progressBar.setMax(100);
                progressBar.setProgress(0);

                //开启progress进程
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        for (int i = 0; i < 10; i++) {
                            try {
                                inCounter = (i + 1) * 20;
                                Thread.sleep(1000);
                                if (i == 4) {
                                    Message m = new Message();
                                    m.what = GUI_STOP_NOTIFIER;
                                    MainActivity.this.phandler.sendMessage(m);
                                    break;
                                } else {
                                    Message m = new Message();
                                    m.what = GUI_THREADING_NOTIFIER;
                                    MainActivity.this.phandler.sendMessage(m);
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }).start();
            }
        });

        /*动态注册一个广播*/
        mbc = new MyBroadcast();
        IntentFilter inF = new IntentFilter();
        inF.addAction("android.intent.action.MyBroadcast");
        registerReceiver(mbc, inF);



        myVF = (ViewFlipper) findViewById(R.id.myViewFlipper);

        iv01 = (ImageView) findViewById(R.id.iv01);
        iv01.setAnimation(AnimationHelper.IVAnimation());
        AnimationHelper.IVAnimation().startNow();

        btn_interactive = (Button) findViewById(R.id.btn_interactive);
        btn_interactive.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                try {
                    Intent in = new Intent();
                    in.setClassName("com.crystal.hq.myapp", "com.crystal.hq.myapp.MainActivity");
                    Bundle bundle = new Bundle();
                    bundle.putString("Str_In", "Hi,我来自Interactive...");
                    in.putExtras(bundle);
                    startActivityForResult(in, 0);

                    /*发送广播*/
                    Intent intent = new Intent("android.intent.action.MyBroadcast");
                    intent.putExtra("MSG", "此消息来自于广播");
                    sendBroadcast(intent);

                } catch (Exception e) {
                    e.printStackTrace();
                    tv_interactiveInfo = (TextView) findViewById(R.id.tv_interactiveInfo);
                    tv_interactiveInfo.setText("请安装MyAPP...");
                }
            }
        });


        gvlist = (GridView) findViewById(R.id.gv_list);
        gvitems1 = new String[]{
                getResources().getString(R.string.gv_item1),
                getResources().getString(R.string.gv_item2),
                getResources().getString(R.string.gv_item3),
                getResources().getString(R.string.gv_item4)};
        gvitems2 = new String[]{
                getResources().getString(R.string.gv_item1),
                getResources().getString(R.string.gv_item2),
                getResources().getString(R.string.gv_item3),
                getResources().getString(R.string.gv_item4),
                getResources().getString(R.string.gv_item1),
                getResources().getString(R.string.gv_item2),
                getResources().getString(R.string.gv_item3),
                getResources().getString(R.string.gv_item4),
                getResources().getString(R.string.gv_item1)};
        twoBtn = (Button) findViewById(R.id.twoBtn);
        twoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gvlist.setNumColumns(2);
                gvAdapter = new ArrayAdapter<String>(MainActivity.this, R.layout.gridview_layout, gvitems1);
                gvlist.setAdapter(gvAdapter);
                gvlist.setSelection(2);
                gvlist.refreshDrawableState();
            }
        });
        threeBtn = (Button) findViewById(R.id.threeBtn);
        threeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gvlist.setNumColumns(3);
                gvAdapter = new ArrayAdapter<String>(MainActivity.this, R.layout.gridview_layout, gvitems2);
                gvlist.setAdapter(gvAdapter);
                gvlist.setSelection(3);
                gvlist.refreshDrawableState();
            }
        });

        gvshowItem = (TextView) findViewById(R.id.gv_show_item);
        gvlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (gvAdapter.getCount()) {
                    case 4:
                        gvshowItem.setText(gvitems1[position].toString());
                        break;
                    case 9:
                        gvshowItem.setText(gvitems2[position].toString());
                        break;
                }
            }
        });

        iv02 = (ImageView) findViewById(R.id.iv02);
        scaleTimes = 1;
        rotAngle = 0;
        final Bitmap mysrcBmp = BitmapFactory.decodeResource(getResources(), R.drawable.im02);
        final int oriWidth = mysrcBmp.getWidth();
        final int oriHeight = mysrcBmp.getHeight();
        rotleft = (Button) findViewById(R.id.rot_left);
        rotleft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rotdir = rotDir.LEFT;
                rotateImageView(mysrcBmp, oriWidth, oriHeight);
            }
        });
        rotRight = (Button) findViewById(R.id.rot_right);
        rotRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rotdir = rotDir.RIGHT;
                rotateImageView(mysrcBmp, oriWidth, oriHeight);
            }
        });
    }

    void rotateImageView(Bitmap srcmap, int width, int height) {
        //左转
        if (rotdir == rotDir.LEFT) {
            rotAngle--;
            if (rotAngle < -8)
                rotAngle = -8;
        } else if (rotdir == rotDir.RIGHT) {
            rotAngle++;
            if (rotAngle > 8)
                rotAngle = 8;
        }
        int newWidth = width * scaleTimes;
        int newHeight = height * scaleTimes;
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        Matrix mat = new Matrix();
        mat.postScale(scaleWidth, scaleHeight);

        mat.setRotate(rotAngle * 45);
        Bitmap resizeBmp = Bitmap.createBitmap(srcmap, 0, 0, width, height, mat, true);
        iv02.setImageBitmap(resizeBmp);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //获取来自com.crystal.hq.myapp的数据
        if (resultCode != RESULT_OK)
            return;

        switch (resultCode) {
            case RESULT_OK:
                String str = data.getStringExtra("myAPP_Back");
                ((TextView) findViewById(R.id.tv_interactiveInfo)).setText("MyAPP的返回:" + str);
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    /*onTouchEvent事件重写*/
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                oldTouchValue = event.getX();
                break;
            case MotionEvent.ACTION_UP:
                float newTouchValue = event.getX();
                //向右滑动
                if (oldTouchValue < newTouchValue) {
                    myVF.setInAnimation(AnimationHelper.inFromLeftAnimaton());
                    myVF.setOutAnimation(AnimationHelper.outToRightAnimation());
                    myVF.showNext();
                }
                //向左滑动
                if (oldTouchValue > newTouchValue) {
                    myVF.setInAnimation(AnimationHelper.inFromRightAnimation());
                    myVF.setOutAnimation(AnimationHelper.outToLeftAnimation());
                    myVF.showPrevious();
                }
                break;
        }
        return super.onTouchEvent(event);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //解除注册
        unregisterReceiver(mbc);
    }
}
