package com.example.i851409.arttherapy;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

public class DrawActivity extends AppCompatActivity implements SensorEventListener {

    //Initializing the variables
    private SensorManager sensor_manager;
    private static final int force_th = 50;
    private static final int time_th = 350;
    private static final int timeout_shake = 750;
    private static final int interval_shake = 2000;

    //This variable indicates that the Drawing will be erased from the canvas when the device is shaked for atleast 2 times
    private static final int shake_count = 2;


    private float sensor_x_point = -1.0f, sensor_y_point = -1.0f, sensor_z_point = -1.0f;
    private long previous_sensor_time;
    private int shakeCount = 0;
    private long previous_sensor_shake;
    private long previous_sensor_force;
    private float accelarator;
    DrawCustomView dcv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Setting the Main View of the Application to this customized drawing view
        setContentView(R.layout.activity_draw);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Instantiating the DrawCustomView class
        dcv = (DrawCustomView) findViewById(R.id.customId);

        //Initialize the Sensor Manager object to fetch the services of the Sensor
        sensor_manager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        //Register the listener for the Sensor with the Sensor Mnager
        sensor_manager.registerListener(this, sensor_manager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);

        //Refreshoing the values of some of the Accelerometer properties
        previous_sensor_force = System.currentTimeMillis();
        previous_sensor_time = previous_sensor_force;
        previous_sensor_shake = previous_sensor_force;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_draw, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    //Method that will clear the contents and the drawing from the canvas
    public void clearCanvas(View v) {

        dcv.clearCanvas();

    }


    //Register the listener for the Sensor
    protected void OnResume() {
        super.onResume();
        sensor_manager.registerListener(this,
                sensor_manager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_NORMAL);

    }

    @Override
    //Unregister the listener for the Sensor
    protected void onPause() {
        sensor_manager.unregisterListener(this);
        super.onPause();
    }

    //Implementing two compulsory methods of the Sensor Listener
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        long now = System.currentTimeMillis();

        if ((now - previous_sensor_force) > timeout_shake) {
            shakeCount = 0;
        }

        if ((now - previous_sensor_time) > time_th) {
            long diff = now - previous_sensor_time;

            accelarator = Math.abs(event.values[0] + event.values[1] + event.values[2] - sensor_x_point - sensor_y_point - sensor_z_point) / diff * 10000;
            if (accelarator > force_th) {

                if ((++shakeCount >= shake_count) && ((now - previous_sensor_shake) > interval_shake)) {
                    Vibrator v = (Vibrator)this.getSystemService(Context.VIBRATOR_SERVICE);
                    v.vibrate(100);

                    //Clearing and erasing from the canvas
                    clearCanvas(dcv);

                    //Starting a service inside a separate thread to play the music
                    Intent intent = new Intent(this,MusicPlay.class);
                    startService(intent);


                    previous_sensor_shake = now;
                    shakeCount = 0;
                }
                previous_sensor_force = now;
            }
            else
            {


            }

            previous_sensor_time = now;
            sensor_x_point = event.values[0];
            sensor_y_point = event.values[1];
            sensor_z_point = event.values[2];
        }

    }


}
