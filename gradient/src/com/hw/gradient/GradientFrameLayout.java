// Copyright 2015 HenryWarner
package com.hw.gradient;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Path;
import android.util.AttributeSet;
import android.widget.FrameLayout;

public class GradientFrameLayout extends FrameLayout {

	private boolean mAnimationStarted;
	protected ValueAnimator mAnimator;
	private int mDuration;
	private int width;
	private int height;
	private Path path;
	
	private float progress;
	
	private GRADIENT_TYPE type = GRADIENT_TYPE.LEFT_TO_RIGHT;

	public enum GRADIENT_TYPE {
		LEFT_TO_RIGHT, RIGHT_TO_LEFT, TOP_TO_BOTTOM, BOTTOM_TO_TOP, LEFT_TOP_TO_RIGHT_BOTTOM, LEFT_BOTTOM_TO_RIGHT_TOP, RIGHT_TOP_TO_LEFT_BOTTOM, RIGHT_BOTTOM_TO_LEFT_TOP,
	}
	
	public GradientFrameLayout(Context context) {
		super(context);
	}

	public GradientFrameLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public GradientFrameLayout(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);

		setWillNotDraw(false);
	}
	
	@Override
	protected void dispatchDraw(Canvas canvas) {
		if (!mAnimationStarted || getWidth() <= 0 || getHeight() <= 0) {
			super.dispatchDraw(canvas);
			return;
		}
		dispatchDrawPart(canvas);
	}

	private void dispatchDrawPart(Canvas canvas) {
		if(path != null){
			canvas.clipPath(path);
			super.dispatchDraw(canvas);
		}
	}

	public void startAnimation() {
		if (mAnimationStarted) {
			return;
		}
		Animator animator = getShimmerAnimation();
		animator.start();
		mAnimationStarted = true;
	}
	
	public void stopAnimation() {
	    if (mAnimator != null) {
	      mAnimator.end();
	      mAnimator.removeAllUpdateListeners();
	      mAnimator.cancel();
	    }
	    mAnimator = null;
	    mAnimationStarted = false;
	  }

	private Animator getShimmerAnimation() {
		if (mAnimator != null) {
			return mAnimator;
		}
		width = getWidth();
		height = getHeight();
		
		mAnimator = ValueAnimator.ofFloat(0.0f, 1.0f);
		mAnimator.setDuration(mDuration);
		mAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
			@Override
			public void onAnimationUpdate(ValueAnimator animation) {
				float value = Math.max(0.0f, Math.min(1.0f, (Float) animation.getAnimatedValue()));
				setProgress(value);
			}
		});
		return mAnimator;
	}

	protected void setProgress(float value) {
		if (this.progress == value) {
			return;
		}
		this.progress = value;

		path = new Path();  
		
		switch (type) {
		case LEFT_TO_RIGHT:
			path.moveTo(0, 0);
			path.lineTo((int) (width * value), 0);
			path.lineTo((int) (width * value), height);
			path.lineTo(0, height);
			break;
		case RIGHT_TO_LEFT:
			path.moveTo((int) (width * (1 - value)), 0);
			path.lineTo(width, 0);
			path.lineTo(width, height);
			path.lineTo((int) (width * (1 - value)), height);
			break;
		case TOP_TO_BOTTOM:
			path.moveTo(0, 0);
			path.lineTo(width, 0);
			path.lineTo(width, (int) (height * value));
			path.lineTo(0, (int) (height * value));
			break;
		case BOTTOM_TO_TOP:
			path.moveTo(0, (int) (height * (1-value)));
			path.lineTo(width, (int) (height * (1-value)));
			path.lineTo(width, height);
			path.lineTo(0, height);
			break;
		case LEFT_TOP_TO_RIGHT_BOTTOM:
			path.moveTo(0, 0);
			path.lineTo((int) (2 * width * value), 0);
			path.lineTo(0, (int) (2 * height * value));
			break;
		case LEFT_BOTTOM_TO_RIGHT_TOP:
			path.moveTo(0, (int) (height - 2 * height * value));
			path.lineTo((int) (2 * width * value), height);
			path.lineTo(0, height);
			break;
		case RIGHT_TOP_TO_LEFT_BOTTOM:
			path.moveTo((int) (width - 2 * width * value), 0);
			path.lineTo(width, 0);
			path.lineTo(width, (int) (2 * height * value));
			break;
		case RIGHT_BOTTOM_TO_LEFT_TOP:
			path.moveTo((int) (width - 2 * width * value), height);
			path.lineTo(width, (int) (height - 2 * height * value));
			path.lineTo(width, height);
			break;
		default:
			break;
		}
		
		path.close();

		invalidate();
	}

	public int getDuration() {
		return mDuration;
	}

	public void setDuration(int duration) {
		mDuration = duration;
		resetAll();
	}

	private void resetAll() {
		stopAnimation();
	}
	
	public GRADIENT_TYPE getType() {
		return type;
	}

	public void setType(GRADIENT_TYPE type) {
		this.type = type;
	}
}
