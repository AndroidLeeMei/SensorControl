package com.example.auser.sensorcontrol;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


//取得手機上有那些sensor,包括手機公司..
//LInear layout verti   + ListView

public class SensorActivity extends Activity {
    private ListView sensorListView;
    private SensorManager sensor_manager;
    private Context context;
    private final String TAD="Sensor_List";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensor);
        context=this;
        //強制轉型至子類別ListView
        sensorListView =(ListView) findViewById(R.id.listView_id);
        //取得系統sensor的控制權,再強制轉型至SensorManager
        //getSystemService{return 所有物件的父類}
        sensor_manager=(SensorManager)getSystemService(SENSOR_SERVICE);
        //getSensorList{retrun Sensor ObjectAAA}
        List<Sensor> sensorList=sensor_manager.getSensorList(Sensor.TYPE_ALL);

        List<String> listName=new ArrayList<>();
        for (Sensor sensor:sensorList){
            listName.add(sensor.getType()
                    + "-"
                    +sensor.getName() + " : "
                    +sensor.getPower()  //取得耗電量
                    +"mA"
            );
        }
        System.out.println(listName);
        //第一個參數若有將simple_list_item_1複製到layout底下,則可以不用"android." 只用 R.layout.simple_list_item_1
        //用系統的android.R.layout.simple_list_item_1
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,R.layout.simple_list_item_1,listName);
        sensorListView.setAdapter(adapter);
        //取得目元件有幾個
        setTitle("Sensor List number : " +listName.size());
        System.out.print(listName.size());

        sensorListView.setOnItemClickListener(itemOnClick);

    }


    private AdapterView.OnItemClickListener itemOnClick=new AdapterView.OnItemClickListener(){

        @Override
        public void onItemClick(AdapterView<?> adapter, View view, int position, long id) {
            Sensor sensor;
            Log.d(TAD,"Item on click");
            //取得現在目前位置及資料,由position傳入
            String sensorName=adapter.getItemAtPosition(position).toString();
            Toast.makeText(context,sensorName,Toast.LENGTH_SHORT).show();
//            Toast.makeText(SensorActivity.this,sensorName,Toast.LENGTH_SHORT).show();

            int index=sensorName.indexOf('-');
            String sensorType=sensorName.substring(0,index);
            int senorID=Integer.valueOf(sensorType);

            switch(senorID){
                case Sensor.TYPE_ACCELEROMETER:
                    sensor=sensor_manager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
                    if(sensor==null){
                        Toast.makeText(context," there is no ACC sensor.",Toast.LENGTH_SHORT).show();
                    }else {
                        Log.d("TAG","ELSE MESSAGE");
                        Intent newActivityIntent = new Intent(context, AccMeterActivity.class);
                        startActivity(newActivityIntent);
                    }
                    break;
                case Sensor.TYPE_LIGHT:
                    break;
                case Sensor.TYPE_PROXIMITY:
                    break;
                default:
                    Toast.makeText(context
                            ," This function does not work."
                            ,Toast.LENGTH_SHORT).show();
            }

        }
    };

}
