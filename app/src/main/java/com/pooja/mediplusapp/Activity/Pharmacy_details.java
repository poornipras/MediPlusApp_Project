package com.pooja.mediplusapp.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
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

import com.pooja.mediplusapp.Adapter.My_Pharma_Adapter;
import com.pooja.mediplusapp.Database.DBhelper;
import com.pooja.mediplusapp.Dialog_box.Pharmacy_details_dialog;
import com.pooja.mediplusapp.Model.Pharma_model;
import com.pooja.mediplusapp.R;

import java.util.ArrayList;

public class Pharmacy_details extends AppCompatActivity implements My_Pharma_Adapter.ClickInterfacePharma{
FloatingActionButton fab_pharma;
    RecyclerView recyclerView;
    Toolbar toolbar;
    DBhelper helper;
    String number;
    ArrayList<Pharma_model> arraypharmalist;
    My_Pharma_Adapter my_pharma_adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pharmacy_details);
        toolbar=(Toolbar)findViewById(R.id.toolbar_pharmacy);
        setSupportActionBar(toolbar);
        fab_pharma=(FloatingActionButton)findViewById(R.id.fab_pharma);
        fab_pharma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Pharmacy_details_dialog pharmacy_details_dialog=new Pharmacy_details_dialog();
                pharmacy_details_dialog.show(getFragmentManager(),"Pharma_FAB");
            }
        });
        recyclerView=(RecyclerView)findViewById(R.id.recycler_pharma);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        arraypharmalist=new ArrayList<Pharma_model>();
        helper=DBhelper.getInstance(this);
        arraypharmalist=helper.getallpharmadetails();
        my_pharma_adapter=new My_Pharma_Adapter(Pharmacy_details.this,arraypharmalist);
        recyclerView.setAdapter(my_pharma_adapter);
        my_pharma_adapter.setClickintefacepharma(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.pharma_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();
        switch(id)
        {
            case R.id.menu_pharma_gohome:
                Intent gohome=new Intent(Pharmacy_details.this,MainActivity.class);
                startActivity(gohome);
                break;

            case R.id.menu_pharma_shopping:
                Intent goshopping=new Intent(Pharmacy_details.this,Shopping_Activity.class);
                startActivity(goshopping);
                break;
            case R.id.delete_all:
                long delall=helper.deleteallpharma();
                if(delall !=-1)
                {
                    Toast.makeText(this, "Successfully deleted all items", Toast.LENGTH_SHORT).show();
                    my_pharma_adapter.deleteallphar();

                }
                else
                {
                    Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show();
                }
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onsingleclicknumber(int position) {
        number=arraypharmalist.get(position).getPharma_number();
        final AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle("Alert");
        builder.setMessage("Do you want to make a call?");
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                Uri numberuri= Uri.parse("tel:"+number);
                Intent dialintent=new Intent(Intent.ACTION_DIAL,numberuri);
                startActivity(dialintent);
                dialog.dismiss();
            }
        });
        builder.create();
        builder.show();
    }

    @Override
    public void onLongclick(int position) {
       String name=arraypharmalist.get(position).getPharma_name();
        int id=arraypharmalist.get(position).getPharma_id();

        long del=helper.deletepharmadetail(id);
        if(del!=-1)
        {
            Toast.makeText(this, "Successfully deleted"+name, Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show();
        }

    }
}
