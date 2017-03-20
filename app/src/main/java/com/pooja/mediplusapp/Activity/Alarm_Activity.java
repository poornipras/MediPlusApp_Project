package com.pooja.mediplusapp.Activity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.gson.Gson;
import com.pooja.mediplusapp.AlarmReceiver.AlarmReceiver;
import com.pooja.mediplusapp.Database.DBhelper;
import com.pooja.mediplusapp.R;
import com.pooja.mediplusapp.Util.Utility;

import java.util.ArrayList;
import java.util.Calendar;


public class Alarm_Activity extends AppCompatActivity{
    Toolbar alarmtoolbar;
    DatePicker pickerDate;
    TimePicker pickerTime;
    Button buttonSetAlarm;
    //static int counter=0;
    TextView info;
    TextView date_display,time_display;
    EditText alarm_name;
    Button setdate,settime;
    CheckBox everyday;
   // ArrayList<Alarm_Data> alarmdataarraylist;
    EditText enter_message;
    DBhelper helper;
    int every;
    int pi_no;

    //final static int RQS_1 = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_);
        alarmtoolbar = (Toolbar) findViewById(R.id.toolbar_alarm);
        setSupportActionBar(alarmtoolbar);

        helper=DBhelper.getInstance(Alarm_Activity.this);
        alarm_name=(EditText) findViewById(R.id.alarm_name);
        enter_message=(EditText)findViewById(R.id.enter_message);

       // alarmdataarraylist=new ArrayList<Alarm_Data>();


        setdate=(Button)findViewById(R.id.set_date);
        settime=(Button)findViewById(R.id.set_time);
        date_display=(TextView)findViewById(R.id.display_date);
        time_display=(TextView)findViewById(R.id.display_time);

        everyday=(CheckBox)findViewById(R.id.everyday_checkbox);

        info = (TextView) findViewById(R.id.info);
        pickerDate = (DatePicker) findViewById(R.id.pickerdate);
        pickerTime = (TimePicker) findViewById(R.id.pickertime);


        setdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pickerDate.setVisibility(View.VISIBLE);
            }
        });

        settime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pickerDate.setVisibility(View.GONE);
                pickerTime.setVisibility(View.VISIBLE);

            }
        });

        Calendar now = Calendar.getInstance();

        pickerDate.init(
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH),
                null);

        pickerTime.setCurrentHour(now.get(Calendar.HOUR_OF_DAY));
        pickerTime.setCurrentMinute(now.get(Calendar.MINUTE));

        buttonSetAlarm = (Button)findViewById(R.id.setalarm);
        buttonSetAlarm.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View arg0) {
                Calendar current = Calendar.getInstance();

                Calendar cal = Calendar.getInstance();
                cal.set(pickerDate.getYear(),
                        pickerDate.getMonth(),
                        pickerDate.getDayOfMonth(),
                        pickerTime.getCurrentHour(),
                        pickerTime.getCurrentMinute(),
                        00);
               ///////////////////////////////////////////
                StringBuffer buffer=new StringBuffer();
                 String day_final=String.valueOf(cal.get(Calendar.DAY_OF_MONTH));
                String month_final=String.valueOf(cal.get(Calendar.MONTH)+1);
                String year_final= String.valueOf(cal.get(Calendar.YEAR));
                buffer.append(day_final+"/");
                buffer.append(month_final+"/");
                buffer.append(year_final);
                date_display.setText(buffer.toString());
                ////////////////////////////////////////////
                String hour_final=String.valueOf(cal.get(Calendar.HOUR_OF_DAY));
                String minute_final=String.valueOf(cal.get(Calendar.MINUTE));
                StringBuffer buffer_time=new StringBuffer();
                buffer_time.append(hour_final+":");
                buffer_time.append(minute_final);
                time_display.setText(buffer_time.toString());

                /////////////////////////////////////////////
                if(cal.compareTo(current) <= 0){
                    //The set Date/Time already passed
                    Toast.makeText(getApplicationContext(),
                            "Invalid Date/Time",
                            Toast.LENGTH_LONG).show();
                }else{
                    setAlarm(cal);
                }

            }});
    }
    private void setAlarm(Calendar targetCal) {
        pi_no= Utility.randomnumber();
        String aname=alarm_name.getText().toString();
        String medmsg=enter_message.getText().toString();
        String date=date_display.getText().toString();
        String time=time_display.getText().toString();


            if(everyday.isChecked())
        {
            every=0;
        }
        else
        {
            every=1;
        }
       // Toast.makeText(getBaseContext(), "Random number" +pi_no, Toast.LENGTH_LONG).show();
            if (everyday.isChecked()) {
                Toast.makeText(getApplicationContext(), "everyday alarm", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(getBaseContext(), AlarmReceiver.class);
                // PendingIntent pendingIntent=PendingIntent.getBroadcast(getBaseContext(),counter,intent,0);
                PendingIntent pendingIntent = PendingIntent.getBroadcast(getBaseContext(), pi_no, intent, 0);
                AlarmManager alarmManager_everyday = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                alarmManager_everyday.setRepeating(AlarmManager.RTC_WAKEUP, targetCal.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
            } else {
                Intent intent = new Intent(getBaseContext(), AlarmReceiver.class);
                // PendingIntent pi = PendingIntent.getBroadcast(getBaseContext(), counter, intent, 0);
                PendingIntent pendingIntent = PendingIntent.getBroadcast(getBaseContext(), pi_no, intent, 0);
                AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                alarmManager.set(AlarmManager.RTC_WAKEUP, targetCal.getTimeInMillis(), pendingIntent);
                // alarmManager.set(AlarmManager.RTC_WAKEUP, targetCal.getTimeInMillis(), pi);
            }
            info.setText("Alarm is set @ " + targetCal.getTime());
            String pinum = String.valueOf(pi_no);
            String fin = String.valueOf(every);
            long insres = helper.insertintoalarmtable(aname, medmsg, date, time, pinum, fin);
            if (insres != -1) {
                Toast.makeText(this, "Success in alarm", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Failure in alarm", Toast.LENGTH_SHORT).show();
            }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.alarm_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();
        switch(id)
        {

            case R.id.menu_alarm_save:
                                        if(alarm_name.length()!=0 && enter_message.length()!=0 && date_display.length()!=0 &&time_display.length()!=0) {
                                            Intent gotoalarmlist = new Intent(Alarm_Activity.this, Alarm_Information.class);
                                            startActivity(gotoalarmlist);
                                        }
                                        else
                                        {
                                            Toast.makeText(this, "Please enter the details before saving", Toast.LENGTH_SHORT).show();
                                        }
                                        break;

            case R.id.menu_home_alarm:
                                       Intent gohome=new Intent(Alarm_Activity.this,MainActivity.class);
                                       startActivity(gohome);
                                       break;
        }
        return super.onOptionsItemSelected(item);
    }

}
