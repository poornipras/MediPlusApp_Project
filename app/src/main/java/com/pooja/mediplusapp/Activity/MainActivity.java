package com.pooja.mediplusapp.Activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.pooja.mediplusapp.Adapter.MY_Adapter;
import com.pooja.mediplusapp.Database.DBhelper;
import com.pooja.mediplusapp.Dialog_box.Search_Dialog;
import com.pooja.mediplusapp.Model.MediData;
import com.pooja.mediplusapp.R;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements MY_Adapter.ClickInterface,GoogleApiClient.OnConnectionFailedListener,
        GoogleApiClient.ConnectionCallbacks{
    Toolbar toolbarmain;
    RecyclerView recyclerView;
    ArrayList<MediData> recyclerarraylist;
    DBhelper dBhelper;
    MY_Adapter my_adapter;
    FloatingActionButton fab;
    private GoogleApiClient mGoogleApiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbarmain= (Toolbar) findViewById(R.id.mainpage_toolbar);
        setSupportActionBar(toolbarmain);
        fab=(FloatingActionButton)findViewById(R.id.fab_main);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "fab", Toast.LENGTH_SHORT).show();
                Intent intentfab=new Intent(MainActivity.this,AddDrugDetails.class);
                startActivity(intentfab);
            }
        });

        recyclerView=(RecyclerView)findViewById(R.id.recycler_view_drug_details_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerarraylist=new ArrayList<MediData>();
        dBhelper=DBhelper.getInstance(this);
        recyclerarraylist=dBhelper.getallDrugnames();
        dBhelper=DBhelper.getInstance(this);
        my_adapter=new MY_Adapter(MainActivity.this,recyclerarraylist);
        recyclerView.setAdapter(my_adapter);
        my_adapter.setClickInterface(this);
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleApiClient=new GoogleApiClient.Builder(this).enableAutoManage(MainActivity.this/* FragmentActivity */,this/* OnConnectionFailedListener */).
                addApi(Auth.GOOGLE_SIGN_IN_API,gso).build();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.main_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();
        switch (id)
        {

            case R.id.alarm_mainpage:
                                         Intent alarmintent=new Intent(MainActivity.this,Alarm_Activity.class);
                                         startActivity(alarmintent);
                                         break;
            case R.id.search_for_medicines:
                                          Search_Dialog searchDialog=new Search_Dialog();
                                          searchDialog.show(getFragmentManager(),"Search_Dialog");
                                          break;

            case R.id.menu_shop:
                                           Intent gotoshop=new Intent(MainActivity.this,Shopping_Activity.class);
                                           startActivity(gotoshop);
                                          break;
            case R.id.menu_pharmacy:
                                            Intent gotopharmacy=new Intent(MainActivity.this,Pharmacy_details.class);
                                           startActivity(gotopharmacy);
                                          break;
            case R.id.logout:
                                         String Lmsg=getIntent().getStringExtra("Login");
                                          String Gmsg=getIntent().getStringExtra("Gmail");
                Toast.makeText(MainActivity.this, "Lmsg:"+Lmsg+"Gmail"+Gmsg, Toast.LENGTH_SHORT).show();
               if(Lmsg!=null) {
                   Toast.makeText(this, "Logged out", Toast.LENGTH_SHORT).show();
                   Intent gologin = new Intent(MainActivity.this, Login.class);
                   startActivity(gologin);
               }else
               {
                   signOut();
                   Intent gologin=new Intent(MainActivity.this,Login.class);
                   startActivity(gologin);
               }
                                          break;

        }
        return super.onOptionsItemSelected(item);
    }
    private void signOut() {
        Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(Status status) {
                        Toast.makeText(MainActivity.this, "Logged out of gmail", Toast.LENGTH_LONG).show();

                    }
                });
    }
     @Override
    public void onsingleClick(int position) {

        String mainname=recyclerarraylist.get(position).getName();
        Intent getdrugdata=new Intent(MainActivity.this,Drug_Data_Activity.class);
        getdrugdata.putExtra("Name",recyclerarraylist.get(position).getName());
        getdrugdata.putExtra("Description",recyclerarraylist.get(position).getDescription());
        getdrugdata.putExtra("Price",recyclerarraylist.get(position).getPrice());
        getdrugdata.putExtra("Category",recyclerarraylist.get(position).getCategory());
        getdrugdata.putExtra("Mediform",recyclerarraylist.get(position).getMedi_form());
        getdrugdata.putExtra("Instructions",recyclerarraylist.get(position).getInstructions());
        getdrugdata.putExtra("Id",recyclerarraylist.get(position).getId());
        startActivity(getdrugdata);
        Toast.makeText(MainActivity.this,"clicked:"+mainname,Toast.LENGTH_LONG).show();
    }

    @Override
    public void onLongclick(int position) {
        String mainname=recyclerarraylist.get(position).getName();
        int drugid=recyclerarraylist.get(position).getId();
        long delres=dBhelper.deletedrugfromTable(drugid);
        if(delres!=-1)
        {
            Toast.makeText(MainActivity.this,"Successfully Deleted :"+mainname,Toast.LENGTH_LONG).show();
        }
        else
        {
            Toast.makeText(MainActivity.this,"Something went wrong",Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        Toast.makeText(this, "Connected", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onConnectionSuspended(int i) {
        Toast.makeText(this, "Suspended", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show();
    }
}
