package com.shinx.myapplication;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.lang.ref.WeakReference;

import widgets.MarqueeTextView;


public class MainActivity extends Activity {
    private View mBtn;
    private MarqueeTextView mMarqueeTv;
    private TextView mTv;
    private boolean mStarted = false;
    private int mNum = 100;
    private MarqueeHandler mHander;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mMarqueeTv = (MarqueeTextView) findViewById(R.id.marquee);
        mBtn = findViewById(R.id.marquee_btn);
        mTv = (TextView) findViewById(R.id.gradual_tv);
        mHander = new MarqueeHandler(this);
        mBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mStarted = !mStarted;
                mMarqueeTv.setMarquee(mStarted);
            }
        });
        mHander.sendEmptyMessage(0);
    }

    public static class MarqueeHandler extends Handler{
        private WeakReference<MainActivity> ref;

        public MarqueeHandler(MainActivity ref) {
            this.ref = new WeakReference<MainActivity>(ref);
        }

        @Override
        public void handleMessage(Message msg) {
            MainActivity activity= ref.get();
            if(activity != null){
                if(activity.mNum == -1)
                    activity.mNum = 100;
                activity.mTv.setText(activity.mNum+"");
                activity.mNum --;
                sendEmptyMessageDelayed(0,1000);
            }
            super.handleMessage(msg);
        }
    }

}
