package com.fangzhich.ivankajingle.base.widget;

import android.content.Context;
import android.view.View;
import android.view.WindowManager;

import com.fangzhich.ivankajingle.R;

/**
 * ProgressBar
 * Created by Khorium on 2016/10/11.
 */

public class ProgressBar {

    private ProgressBar(){}

    public static ProgressBar getInstance() {
        return new ProgressBar();
    }

    private WindowManager windowManager;
    private View progressBar;

    public ProgressBar init(Context context, final Callback callback) {

        progressBar = View.inflate(context, R.layout.progress_bar, null);

        windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);

        progressBar.findViewById(R.id.progress_bar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callback.onProgressBarClick(v);
            }
        });
        return this;
    }

    public void show() {
        if (windowManager != null) {
            WindowManager.LayoutParams params = new WindowManager.LayoutParams();
            params.height = WindowManager.LayoutParams.WRAP_CONTENT;
            params.width = WindowManager.LayoutParams.WRAP_CONTENT;
            windowManager.addView(progressBar, params);
        }
    }

    public interface Callback {
        void onProgressBarClick(View v);
    }

    public void cancel() {
        if (windowManager!=null) {
            windowManager.removeViewImmediate(progressBar);
        }
    }
}
