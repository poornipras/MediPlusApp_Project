package com.pooja.mediplusapp.Activity;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.ads.InterstitialAd;
import com.pooja.mediplusapp.Adapter.SimpleShoppingAdapter;
import com.pooja.mediplusapp.R;

public class Shopping_Activity extends AppCompatActivity{
RecyclerView recyclerView;
    Toolbar shop_toolbar;
    String[]newarraylist;
    EditText searchdata;
    Button search_google;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_);

        shop_toolbar=(Toolbar)findViewById(R.id.toolbar_shopping);
        setSupportActionBar(shop_toolbar);

        searchdata = (EditText) findViewById(R.id.edittext_shop_searchdata);
        search_google=(Button)findViewById(R.id.search_btn_google);

        recyclerView=(RecyclerView)findViewById(R.id.recycler_view_shopping);

        newarraylist=getResources().getStringArray(R.array.med_url);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        SimpleShoppingAdapter simpleShoppingAdapter=new SimpleShoppingAdapter(Shopping_Activity.this,newarraylist);
        recyclerView.setAdapter(simpleShoppingAdapter);

        search_google.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Thread mythread= new Thread(new Runnable() {
                    @Override
                    public void run() {

                        String query=searchdata.getText().toString();
                        Uri uri=Uri.parse("http://www.google.com/search?q="+query);
                        Intent i=new Intent(Intent.ACTION_VIEW,uri);
                        startActivity(i);
                    }
                });
                mythread.start();
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.shopping_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();
        switch(id)
        {
            case R.id.go_home:
                               Intent gohome=new Intent(Shopping_Activity.this,MainActivity.class);
                               startActivity(gohome);
                break;
            case R.id.menu_alarmlist:
                              Intent gotoalarmlist=new Intent(Shopping_Activity.this,Alarm_Information.class);
                              startActivity(gotoalarmlist);
                break;
        }
        return super.onOptionsItemSelected(item);
    }


}
