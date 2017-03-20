package com.pooja.mediplusapp.Activity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.pooja.mediplusapp.Adapter.My_information_alarm_adapter;
import com.pooja.mediplusapp.AlarmReceiver.AlarmReceiver;
import com.pooja.mediplusapp.Database.DBhelper;
import com.pooja.mediplusapp.R;

import java.util.ArrayList;

public class Alarm_Information extends AppCompatActivity implements My_information_alarm_adapter.LongClickInteface{
RecyclerView recyclerView;
    Toolbar toolbar;
    String name,pi_no;
    int pi_num;
    int alarm_id;

    DBhelper dBhelper;
    ArrayList<com.pooja.mediplusapp.Model.Alarm_Information> mylist;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm__information);
        toolbar=(Toolbar)findViewById(R.id.toolbar_alarm_information);
        setSupportActionBar(toolbar);
        recyclerView= (RecyclerView) findViewById(R.id.recycler_info_alarm);
        dBhelper=DBhelper.getInstance(this);
        mylist=new ArrayList<com.pooja.mediplusapp.Model.Alarm_Information>();
        mylist=dBhelper.getallalarminfo();
        //Toast.makeText(this, "size:"+mylist.size(), Toast.LENGTH_SHORT).show();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        My_information_alarm_adapter myinfoadapter=new My_information_alarm_adapter(this,mylist);
        recyclerView.setAdapter(myinfoadapter);
        myinfoadapter.setLongClickInteface(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuinflator=getMenuInflater();
        menuinflator.inflate(R.menu.alarm_info_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onlongclicked(final int position) {
         name=mylist.get(position).getAl_name();
         pi_no=mylist.get(position).getAl_pending_no();
         alarm_id=mylist.get(position).getAl_id();
        Toast.makeText(this, "name:"+name, Toast.LENGTH_SHORT).show();
        pi_num=Integer.parseInt(pi_no);
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle("Delete");
        builder.setMessage("Are you sure you want to delete this alarm?");
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(Alarm_Information.this, "No", Toast.LENGTH_SHORT).show();
            }
        });
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                cancelAlarm(pi_num,name,alarm_id,position);
                Toast.makeText(Alarm_Information.this, "yes", Toast.LENGTH_SHORT).show();
                Intent gotoalarm=new Intent(Alarm_Information.this,MainActivity.class);
                startActivity(gotoalarm);
            }
        });
        builder.create();
        builder.show();

    }

    public void cancelAlarm(int pi_no,String name,int alarm_id,int position)
    {
        long delres=dBhelper.deletealarm(alarm_id);
        if(delres!=-1)
        {
            Intent intent = new Intent(getBaseContext(), AlarmReceiver.class);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(getBaseContext(), pi_no, intent, 0);
            AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
            alarmManager.cancel(pendingIntent);
            Toast.makeText(this, "Alarm "+name+" was cancelled", Toast.LENGTH_SHORT).show();

        }
        else
        {
            Toast.makeText(this, "Sorry!!!Something went wrong", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();
        switch(id)

        {
            case R.id.gotomainpage:
                Intent gohome=new Intent(Alarm_Information.this,MainActivity.class);
                startActivity(gohome);
                break;
            case R.id.menu_add_alarm:
                Intent gotoalarm=new Intent(Alarm_Information.this,Alarm_Activity.class);
                startActivity(gotoalarm);
                break;
            case R.id.menu_shopping_alarm:
                Intent gotoshopping=new Intent(Alarm_Information.this,Shopping_Activity.class);
                startActivity(gotoshopping);
                break;
        }

        return super.onOptionsItemSelected(item);
    }

}
