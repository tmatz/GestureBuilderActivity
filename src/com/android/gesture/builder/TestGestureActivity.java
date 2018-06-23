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
import android.widget.*;

public class TestGestureActivity extends Activity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.test_gesture);
        final TextView text = findViewById(R.id.text);
        final TextView info = findViewById(R.id.info);
        final GestureOverlayView overlay = findViewById(R.id.gestures_overlay);

        overlay.addOnGestureListener(
            new GestureOverlayView.OnGestureListener()
            {
                @Override
                public void onGestureStarted(GestureOverlayView overlay, MotionEvent event)
                {
                }

                @Override
                public void onGestureEnded(GestureOverlayView overlay, MotionEvent event)
                {
                    final Gesture gesture = overlay.getGesture();
                    final GestureLibrary store = GestureBuilderActivity.getStore();
                    ArrayList<Prediction> predictions = store.recognize(gesture);
                    if (predictions.size() > 0)
                    {
                        Prediction prediction = predictions.get(0);
                        StringBuffer sb = new StringBuffer();
                        sb.append(String.format("%s %f", prediction.name, prediction.score));
                        for (int i = 1; i < Math.min(3, predictions.size()); i++)
                        {
                            Prediction p = predictions.get(i);
                            sb.append("\n");
                            sb.append(String.format("%s %f", p.name, p.score));
                        }
                        info.setText(sb.toString());
                        if (prediction.score > 2.0)
                        {
                            switch (prediction.name)
                            {
                                case "space":
                                    text.append(" ");
                                    break;
                                case "return":
                                    text.append("\n");
                                    break;
                                case "delete":
                                    CharSequence value = text.getText();
                                    text.setText(value.subSequence(0, Math.max(value.length() - 1, 0)));
                                    break;
                                default:
                                    text.append(prediction.name);
                            }
                        }
                    }
                }

                @Override
                public void onGestureCancelled(GestureOverlayView overlay, MotionEvent event)
                {
                }

                @Override
                public void onGesture(GestureOverlayView overlay, MotionEvent event)
                {
                }
            });
    }

    public void done(View v)
    {
        setResult(RESULT_OK);
        finish();
    }
}
