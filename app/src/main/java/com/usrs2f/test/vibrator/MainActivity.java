package com.usrs2f.test.vibrator;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends Activity implements SensorEventListener {

    private float lastX, lastY, lastZ;
    private SensorManager mSensorManager;
    private Sensor mAccelerometre;
    private float deltaX = 3;
    private float deltaY = 3;
    private float deltaZ = 3;
    private float vibrateThreshold = 10;
    public Vibrator v;
    private static final int DELAI_MISE_A_JOUR = 500;
    private long mLastUpdate;
    private TextView mXValueView, mYValueView, mZValueView;
    private String mXValueLabel, mYValueLabel, mZValueLabel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mLastUpdate = System.currentTimeMillis();
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mAccelerometre = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

        mXValueLabel = getResources().getString(R.string.xValue);
        mYValueLabel = getResources().getString(R.string.yValue);
        mZValueLabel = getResources().getString(R.string.zValue);

        mXValueView = (TextView) findViewById(R.id.tvSensorX);
        mYValueView = (TextView) findViewById(R.id.tvSensorY);
        mZValueView = (TextView) findViewById(R.id.tvSensorZ);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(this, mAccelerometre, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    public void onSensorChanged(SensorEvent event){
        long actualTime = System.currentTimeMillis();

        if (actualTime - mLastUpdate > DELAI_MISE_A_JOUR){
            mLastUpdate = actualTime;

            float x = event.values[0], y = event.values[1], z = event.values[2];

            mXValueView.setText(String.valueOf(x));
            mYValueView.setText(String.valueOf(y));
            mZValueView.setText(String.valueOf(z));

            if (x - deltaX > 2 ){
                v.vibrate(VibrationEffect.createOneShot(500,VibrationEffect.DEFAULT_AMPLITUDE));
            }
            if (y - deltaY > 2){
                v.vibrate(VibrationEffect.createOneShot(500,VibrationEffect.DEFAULT_AMPLITUDE));
            }
            if (z - deltaZ > 10){
                v.vibrate(VibrationEffect.createOneShot(500,VibrationEffect.DEFAULT_AMPLITUDE));
            }
        }
    }


}

