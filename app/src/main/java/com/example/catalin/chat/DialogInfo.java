package com.example.catalin.chat;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.support.constraint.ConstraintLayout;
import android.util.Log;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

public class DialogInfo {

    //private Context context;
    private View view;
    private FrameLayout loginFrameLayout;
    private TextView loginInfoText;
    private Handler animHandler;
    private Runnable animRunnable;
    private Context context;
    private ConstraintLayout mainLayout;
    private int delaytime = 2000; //default

    private static final int COLOR_SUCCESS = 0x4BB54300;
    private static final int COLOR_ERROR = 0xD8000C00;

    public enum InfoType {
        INFO_ERROR,
        INFO_SUCCESS
    }


    DialogInfo(Context context, ConstraintLayout constraintLayout) {
        this.context = context;
        this.mainLayout = constraintLayout;
        animRunnable = this::HideDialogInfo; //default
        animHandler = new Handler();
    }

    private void Init() {
        if (loginFrameLayout == null || loginInfoText == null) {
            Log.e("DIALOGINFO", "NULLL");
            view = LayoutInflater.from(context).inflate(R.layout.dialoginfo, mainLayout, false);
            mainLayout.addView(view);
            loginFrameLayout = view.findViewById(R.id.loginInfoLayout);
            loginInfoText = view.findViewById(R.id.loginInfoText);
        }
    }

    public void ShowDialogInfo(String string, InfoType infoType) {
        Init();
        Log.d("call", "call");
        switch (infoType) {
            case INFO_ERROR: {
                loginFrameLayout.setBackgroundColor(Color.argb(255,224, 22, 22));
                break;
            }
            case INFO_SUCCESS: {
                loginFrameLayout.setBackgroundColor(Color.argb(255,75,181,67));
                break;
            }
        }
        loginFrameLayout.setVisibility(View.VISIBLE);
        loginInfoText.setText(string);
        animHandler.removeCallbacksAndMessages(null);
        animHandler.postDelayed(animRunnable, delaytime);
    }

    public void ShowDialogInfoEx(String string, InfoType infoType, int duration) {
        delaytime = duration;
        ShowDialogInfo(string, infoType);
        delaytime = 2000; //reset
    }

    public void ShowDialogInfoEx(String string, InfoType infoType, int duration, Runnable runnable)
    {
        animRunnable = runnable;
        ShowDialogInfoEx(string, infoType, duration);
        animRunnable = this::HideDialogInfo; //reset
    }

    public void HideDialogInfo()
    {
        //Log.e("TEST", "TESTTTT");
        //if(((Activity)context).findViewById(R.id.loginInfoLayout) != null) {
        if(loginFrameLayout != null && loginInfoText != null){
            loginFrameLayout.animate().alpha(0.0f).setDuration(500).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    loginFrameLayout.setVisibility(View.GONE);
                    loginFrameLayout.setAlpha(1.0f);
                    Log.d("dadad", "Dada");
                }
            });
            //loginFrameLayout.setVisibility(View.GONE);
        }
    }
}
