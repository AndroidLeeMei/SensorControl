package com.example.auser.sensorcontrol;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class AccMeterActivity extends Activity {
    ImageView imageViewTop,imageViewRight,imageViewLeft,imageViewDown;
    TextView textView;
    Context context;
    private final String TAG="Sensor_ACC";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acc_meter);
        context=this;
        Log.d("TAG","ACC START");
        Toast.makeText(context," Enter AccMmeter Activity",Toast.LENGTH_SHORT).show();
        findViews();
        textView.setText("");
        Log.d("TAG","ACC END");


        sensor_managet=(SensorManager) getSystemService(SENSOR_SERVICE);
        Sensor sensor=sensor_managet.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
//        建立一個物件,來實作sensor event lisenter

        MyListener listener=new MyListener();
        //要註冊監聽器,  SENSOR_DELAY_UI更新速度會比較慢
        sensor_managet.registerListener(listener,sensor,SensorManager.SENSOR_DELAY_UI);
    }

    private class MyListener implements SensorEventListener{
        //當數值改變時
        @Override
        public void onSensorChanged(SensorEvent event) {
            //步適用多執行緒,但沒有同
            StringBuilder sb=new StringBuilder();
            sb.append("sensor: " + event.sensor.getName()+"\n");
            //可看看取得的數值夠夠精確,暫時先不做

            sb.append("values : \n");
            sb.append("X : " + event.values[0] + "\n");
            sb.append("Y : " + event.values[1] + "\n");
            sb.append("Z : " + event.values[2] + "\n");
            String msg=sb.toString();
            textView.setText(msg);

            float X_value=event.values[0];
            float Y_value=event.values[1];
            float Z_value=event.values[2];

            //向右轉時,顯示向右的
            if (X_value<0){
                imageViewTop.setVisibility(View.INVISIBLE);
                imageViewDown.setVisibility(View.INVISIBLE);
                imageViewLeft.setVisibility(View.INVISIBLE);
                imageViewRight.setVisibility(View.VISIBLE);

            }else if(X_value<1) {
                imageViewTop.setVisibility(View.INVISIBLE);
                imageViewDown.setVisibility(View.INVISIBLE);
                imageViewLeft.setVisibility(View.VISIBLE);
                imageViewRight.setVisibility(View.INVISIBLE);
            }else if (Z_value>3) {
                imageViewTop.setVisibility(View.VISIBLE);
                imageViewDown.setVisibility(View.INVISIBLE);
                imageViewRight.setVisibility(View.INVISIBLE);
                imageViewLeft.setVisibility(View.INVISIBLE);
            }else if (Z_value<1) {
                imageViewTop.setVisibility(View.INVISIBLE);
                imageViewDown.setVisibility(View.VISIBLE);
                imageViewRight.setVisibility(View.INVISIBLE);
                imageViewLeft.setVisibility(View.INVISIBLE);
            }else {
                imageViewTop.setVisibility(View.INVISIBLE);
                imageViewDown.setVisibility(View.INVISIBLE);
                imageViewRight.setVisibility(View.INVISIBLE);
                imageViewLeft.setVisibility(View.INVISIBLE);

            }

        }
        //自定一個參數,當數值改變時,產生終端
        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    }
    SensorManager sensor_managet;
    private void findViews(){
        textView=(TextView) findViewById(R.id.acc_TextView);
        imageViewDown=(ImageView)findViewById(R.id.imageViewDown);
        imageViewTop=(ImageView)findViewById(R.id.imageViewTop);
        imageViewRight=(ImageView)findViewById(R.id.imageViewRight);
        imageViewLeft=(ImageView)findViewById(R.id.imageViewLeft);

        imageViewDown.setVisibility(View.INVISIBLE);
        imageViewTop.setVisibility(View.INVISIBLE);
        imageViewRight.setVisibility(View.INVISIBLE);
        imageViewLeft.setVisibility(View.INVISIBLE);


    }




}
