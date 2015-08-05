package widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;
import android.graphics.Typeface;
import android.util.AttributeSet;

import com.shinx.myapplication.R;

public class GradualTextView extends MarqueeTextView{
	private Paint mPaint = new Paint();
	private int mFontheight = 0;
	private Shader mShader;
	private int mColorTop;
	private int mColorBottom;
	public GradualTextView(Context context, AttributeSet attrs) {
		super(context, attrs);

		TypedArray array = context.obtainStyledAttributes(attrs,R.styleable.ScaleText);
		mColorBottom = array.getColor(R.styleable.ScaleText_bottomColor, getTextColors().getDefaultColor());
		mColorTop = array.getColor(R.styleable.ScaleText_topColor, getTextColors().getDefaultColor());
		array.recycle();
		Typeface typeface = Typeface.createFromAsset(context.getAssets(), "fonts/ArialBlack.ttf");
		this.setTypeface(typeface);
	}
	
	public void setSelected(boolean isSelected){
		this.invalidate();
	}

	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
	}
	
	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		if(mFontheight == 0){
			mPaint = getPaint();
			mFontheight = getMeasuredHeight()* 7/9;
			if(mFontheight > 0){
				mShader = new LinearGradient(0, 0, 0, mFontheight, mColorTop, mColorBottom, Shader.TileMode.CLAMP);
				mPaint.setShader(mShader);
			}
		}
	}
}
