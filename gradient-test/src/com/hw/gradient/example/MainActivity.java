// Copyright 2015 HenryWarner
package com.hw.gradient.example;

import android.app.Activity;
import android.os.Bundle;

import com.hw.gradient.GradientFrameLayout;

public class MainActivity extends Activity {

	private GradientFrameLayout mGradientFrameLayout;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.main);
		mGradientFrameLayout = (GradientFrameLayout) findViewById(R.id.GradientFrameLayout);
		mGradientFrameLayout.setType(GradientFrameLayout.GRADIENT_TYPE.LEFT_TO_RIGHT);
	}

	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		if(hasFocus){
			mGradientFrameLayout.setDuration(5000);
			mGradientFrameLayout.startAnimation();
		}
	}
	
	@Override
	protected void onStart() {
		super.onStart();
	}

	@Override
	public void onResume() {
		super.onResume();
	}

	@Override
	public void onPause() {
		mGradientFrameLayout.stopAnimation();
		super.onPause();
	}
}
