package com.android.gesture.builder;

import java.util.ArrayList;

import android.app.Activity;
import android.gesture.Gesture;
import android.gesture.GestureLibrary;
import android.gesture.GestureOverlayView;
import android.gesture.Prediction;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

public class TestGestureActivity extends Activity {

		@Override
		protected void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);

			setContentView(R.layout.test_gesture);
			final TextView text = (TextView) findViewById(R.id.text);
			final GestureOverlayView overlay = (GestureOverlayView) findViewById(R.id.gestures_overlay);

			overlay.addOnGestureListener(new GestureOverlayView.OnGestureListener() {
				
				@Override
				public void onGestureStarted(GestureOverlayView overlay, MotionEvent event) {
				}
				
				@Override
				public void onGestureEnded(GestureOverlayView overlay, MotionEvent event) {
					final Gesture gesture = overlay.getGesture();
					final GestureLibrary store = GestureBuilderActivity.getStore();
					ArrayList<Prediction> predictions = store.recognize(gesture);
					if (predictions.size() > 0) {
						Prediction prediction = predictions.get(0);
						if (prediction.score > 1.0) {
							text.append(prediction.name);
						}
					}
				}
				
				@Override
				public void onGestureCancelled(GestureOverlayView overlay, MotionEvent event) {
				}
				
				@Override
				public void onGesture(GestureOverlayView overlay, MotionEvent event) {
				}
			});
		}

		public void done(View v) {
			setResult(RESULT_OK);
			finish();
		}
}
