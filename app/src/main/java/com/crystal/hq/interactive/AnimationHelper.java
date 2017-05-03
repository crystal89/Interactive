package com.crystal.hq.interactive;

import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;

/**
 * Created by 102003449 on 2017/4/26.
 */

//创建的TranslateAnimation可以直接调用在ImageView上，让图片通过创建好的Animation对象来显示动画。
public class AnimationHelper {
    //由右向左滑动的Set In事件
    public static Animation inFromRightAnimation() {
        Animation inFromRight = new TranslateAnimation(
                Animation.RELATIVE_TO_PARENT + 1.0f,
                Animation.RELATIVE_TO_PARENT + 0.0f,
                Animation.RELATIVE_TO_PARENT + 0.0f,
                Animation.RELATIVE_TO_PARENT + 0.0f
        );
        //持续时间
        inFromRight.setDuration(350);
        //插值
        inFromRight.setInterpolator(new AccelerateInterpolator());
        return inFromRight;
    }

    //由右向左滑动的Set Out事件
    public static Animation outToLeftAnimation() {
        Animation outToLeft = new TranslateAnimation(
                Animation.RELATIVE_TO_PARENT + 0.0f,
                Animation.RELATIVE_TO_PARENT - 1.0f,
                Animation.RELATIVE_TO_PARENT + 0.0f,
                Animation.RELATIVE_TO_PARENT + 0.0f
        );
        outToLeft.setDuration(350);
        outToLeft.setInterpolator(new AccelerateInterpolator());
        return outToLeft;
    }

    //由左向右滑动的Set In事件
    public static Animation inFromLeftAnimaton() {
        Animation inFromLeft = new TranslateAnimation(
                Animation.RELATIVE_TO_PARENT - 1.0f,
                Animation.RELATIVE_TO_PARENT + 0.0f,
                Animation.RELATIVE_TO_PARENT + 0.0f,
                Animation.RELATIVE_TO_PARENT + 0.0f
        );
        inFromLeft.setDuration(350);
        inFromLeft.setInterpolator(new AccelerateInterpolator());
        return inFromLeft;
    }

    //由左向右滑动的Set Out事件
    public static Animation outToRightAnimation() {
        Animation outToRight = new TranslateAnimation(
                Animation.RELATIVE_TO_PARENT + 0.0f,
                Animation.RELATIVE_TO_PARENT + 1.0f,
                Animation.RELATIVE_TO_PARENT + 0.0f,
                Animation.RELATIVE_TO_PARENT + 0.0f
        );
        outToRight.setDuration(350);
        outToRight.setInterpolator(new AccelerateInterpolator());
        return outToRight;
    }

    /*扩展：设置两张ImageView的动画效果，显示切换画面时也能看到动画的样貌*/
    public  static  Animation IVAnimation()
    {
        Animation anim = new TranslateAnimation(10,200,10,400);
        anim.setDuration(2000);
        //设置动画重复次数，-1表示不断重复
        anim.setRepeatCount(-1);
        anim.setRepeatMode(Animation.REVERSE);
        return anim;
    }
}
