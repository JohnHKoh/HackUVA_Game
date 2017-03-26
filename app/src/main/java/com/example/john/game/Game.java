package com.example.john.game;

        import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.widget.TextView;
import java.util.Timer;

public class Game extends Activity {

    float xVelocity;
    float maxVelocity;
    public VelocityTracker tracker = null;
    public TextView textViewV, textViewMax, textFire;
    boolean started = false;
    long startTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        textViewV = (TextView) findViewById(R.id.velocityx);
        textViewMax = (TextView) findViewById(R.id.maxVelocity);
        textFire = (TextView) findViewById(R.id.fire);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getActionMasked();

        switch (action) {
            case MotionEvent.ACTION_DOWN:
                if(tracker == null) {
                    tracker = VelocityTracker.obtain();
                    xVelocity = 0;
                    startTime = System.currentTimeMillis();
                }
                else {
                    tracker.clear();
                }
            case MotionEvent.ACTION_MOVE:
                tracker.addMovement(event);
                tracker.computeCurrentVelocity(1000);
                float xVelocity = Math.abs(tracker.getXVelocity());
                if(xVelocity > 100) { //Speed threshold
                    long millis = System.currentTimeMillis() - startTime;
                    int seconds = (int) (millis/1000);
                    textViewV.setText("fast enough" + millis);
                    started = true;
                    if(seconds > 1) { //Time threshold
                        textViewV.setText("fast enough for long");
                    }
                }
                else {
                    textViewV.setText("not fast enough" + xVelocity);
                    if(started) {
                        startTime = System.currentTimeMillis();
                    }
                }
//                if(xVelocity == 0) {
//                    maxVelocity = 0;
//                    while(xVelocity != 0) {
//                        if(xVelocity > maxVelocity) {
//                            maxVelocity = xVelocity;
//                            textViewMax.setText("max velocity" + maxVelocity);
//                        }
//                    }
//                }

                //textViewV.setText("velocity:" + xVelocity);
                break;
            case MotionEvent.ACTION_UP:
                started = false;
                textFire.setText("");
                textViewV.setText("velocity: 0");
                break;
            case MotionEvent.ACTION_CANCEL:
                tracker.recycle();
                break;
        }
        return true;
    }
}