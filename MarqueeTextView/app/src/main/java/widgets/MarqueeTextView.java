package widgets;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.widget.TextView;

import java.lang.ref.WeakReference;


/**
 * Created by liuyi on 15/7/7.
 */
public class MarqueeTextView extends TextView {
    private boolean mIsFocused = false;
    private boolean mStopMarquee = true;
    private String mText;
    private float mCoordinateX;
    private float mTextWidth;
    private float mTextHeigth;
    private MarqueeHandler mHandler;

    public MarqueeTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mHandler = new MarqueeHandler(this);
    }

    @Override
    public boolean isSelected() {
        return mIsFocused;
    }

    @Override
    public boolean isFocused() {
        return mIsFocused;
    }

    public boolean isIsFocused() {
        return mIsFocused;
    }

    public void setIsFocused(boolean mIsFocused) {
        this.mIsFocused = mIsFocused;
    }

    public void setMarquee(boolean isMarquee) {
        mText = (String) getText();
        mTextWidth = getPaint().measureText(mText);
        mStopMarquee = !isMarquee;
        if (mTextWidth <= getWidth() - getPaddingLeft() - getPaddingRight()) {
            mStopMarquee = true;
            return;
        }
        if (isMarquee) {
            Paint.FontMetrics fm = getPaint().getFontMetrics();
            mTextHeigth = (float) Math.ceil(fm.descent - fm.ascent);
            resetStatus();
            mHandler.sendEmptyMessage(0);
        } else {
            if (mHandler.hasMessages(0)) {
                mHandler.removeMessages(0);
            }
            invalidate();
        }
    }

    private void resetStatus() {
        mCoordinateX = getPaddingLeft();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (!mStopMarquee) {
            if (mText != null && !mText.equals("") && !mStopMarquee)
                canvas.drawText(mText, mCoordinateX, (float) (((getHeight() - mTextHeigth) / 2 + mTextHeigth) * 0.85), getPaint());
        } else {
            super.onDraw(canvas);
        }

    }

    @Override
    protected void onDetachedFromWindow() {
        mStopMarquee = true;
        setText(mText);
        if (mHandler.hasMessages(0))
            mHandler.removeMessages(0);
        super.onDetachedFromWindow();
    }

    static class MarqueeHandler extends Handler {
        WeakReference<MarqueeTextView> ref;

        public MarqueeHandler(MarqueeTextView marqueeTextView) {
            ref = new WeakReference<MarqueeTextView>(marqueeTextView);
        }

        @Override
        public void handleMessage(Message msg) {
            MarqueeTextView view = ref.get();
            if (view != null) {
                switch (msg.what) {
                    case 0:
                        if (Math.abs(view.mCoordinateX) > (view.mTextWidth + 100)) {
                            view.resetStatus();
                            view.invalidate();
                            if (!view.mStopMarquee) {
                                sendEmptyMessageDelayed(0, 2000);
                            }
                        } else {
                            view.mCoordinateX -= 4;
                            view.invalidate();
                            if (!view.mStopMarquee) {
                                sendEmptyMessageDelayed(0, 30);
                            }
                        }
                        break;
                }
            }
            super.handleMessage(msg);
        }
    }
}
