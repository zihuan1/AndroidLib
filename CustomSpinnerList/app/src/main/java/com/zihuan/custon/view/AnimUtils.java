package com.zihuan.custon.view;

import android.transition.ChangeBounds;
import android.transition.TransitionManager;
import android.view.View;
import android.view.ViewGroup;


public class AnimUtils {

    // 扩散动画

    //    过场动画 true显示false隐藏
    public static void transitionAnim(ViewGroup root, View view, boolean isOpen) {
        ChangeBounds changeBounds = new ChangeBounds();
        changeBounds.setDuration(200);
//        new TransitionSet().addTransition(changeBounds);// 多个view组合动画使用

        TransitionManager.beginDelayedTransition(root, changeBounds);
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        if (isOpen) {
            layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        } else {
            layoutParams.height = 0;
        }
        view.setLayoutParams(layoutParams);
    }


}
