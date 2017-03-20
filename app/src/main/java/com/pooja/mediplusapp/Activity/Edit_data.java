package com.pooja.mediplusapp.Activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.pooja.mediplusapp.Database.DBhelper;
import com.pooja.mediplusapp.R;
import com.pooja.mediplusapp.Util.Utility;

import java.util.ArrayList;

public class Edit_data extends AppCompatActivity {
    Toolbar edit_toolbar;
    EditText edit_name,edit_desc,edit_price;
    RadioGroup editcategory;
    RadioButton radio_generic,radio_specific;
    String getintentname,getintentdesc,getintentprice,getintentcategory,getintentmediform,getintentinstructions;
    int getintenteid;
    Spinner mediform_spinner,instructions_spinner;
    DBhelper dbhelper;
    String updatename,updatedesc,updateprice,updatecategory,updateMedi_form,update_instructions;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_data);
        edit_toolbar=(Toolbar)findViewById(R.id.toolbar_edit_page);
        setSupportActionBar(edit_toolbar);
        dbhelper=DBhelper.getInstance(this);
        edit_name=(EditText)findViewById(R.id.edit_drug_name);
        edit_desc=(EditText)findViewById(R.id.edit_drug_desc);
        edit_price=(EditText)findViewById(R.id.edit_drug_price);
        editcategory=(RadioGroup)findViewById(R.id.radiogroup_edit_catogory);
        radio_generic=(RadioButton)findViewById(R.id.edit_radio_generic);
        radio_specific=(RadioButton)findViewById(R.id.edit_radio_specific);
        mediform_spinner=(Spinner)findViewById(R.id.edit_spinner_mediform);
        ArrayAdapter spinnermedi_form_Adapter=ArrayAdapter.createFromResource(Edit_data.this,R.array.spinner_form_of_medication,android.R.layout.simple_spinner_item);
        mediform_spinner.setAdapter(spinnermedi_form_Adapter);

        instructions_spinner=(Spinner)findViewById(R.id.edit_information);
        ArrayAdapter spinner_instructions=ArrayAdapter.createFromResource(Edit_data.this,R.array.spinner_instructions,android.R.layout.simple_spinner_item);
        instructions_spinner.setAdapter(spinner_instructions);
        init();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.add_drug_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();
        switch(id)
        {
            case R.id.save_add_drug:
                validate(edit_name.getText().toString(),edit_desc.getText().toString(),edit_price.getText().toString());
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void init()
    {
        getintentname=getIntent().getStringExtra("Name");
        getintentdesc=getIntent().getStringExtra("Description");
        getintentprice=getIntent().getStringExtra("Price");
        getintentcategory=getIntent().getStringExtra("Category");
        getintentmediform=getIntent().getStringExtra("Mediform");
        getintentinstructions=getIntent().getStringExtra("Instructions");
        getintenteid=getIntent().getIntExtra("Id",0);
        edit_name.setText(getintentname);
        edit_desc.setText(getintentdesc);
        edit_price.setText(getintentprice);

        if(getintentcategory.equalsIgnoreCase("Generic"))
        {
            radio_generic.setChecked(true);
        }
        if(getintentcategory.equalsIgnoreCase("Specific"))
        {
            radio_specific.setChecked(true);
        }

        //to set value to spinner Medi form
        for(int i=0;i<mediform_spinner.getCount();i++)
        {
            if(getintentmediform.equals(mediform_spinner.getItemAtPosition(i)))
            {
                mediform_spinner.setSelection(i);
            }
        }

       for(int j=0;j<instructions_spinner.getCount();j++)
       {
           if(getintentinstructions.equals(instructions_spinner.getItemAtPosition(j)))
           {
               instructions_spinner.setSelection(j);
           }
       }
    }

    public void getupdatedvalues()
    {
        updatename= edit_name.getText().toString();
        updatedesc=edit_desc.getText().toString();
        updateprice=edit_price.getText().toString();
        updatecategory = ((RadioButton)findViewById(editcategory.getCheckedRadioButtonId())).getText().toString();
        updateMedi_form=mediform_spinner.getSelectedItem().toString();
        update_instructions=instructions_spinner.getSelectedItem().toString();


        long updateres=dbhelper.updateediteddrugdata(updatename,updatedesc,updateprice,updatecategory,updateMedi_form,update_instructions,getintenteid);
        if(updateres>-1)
        {
            Toast.makeText(this,"successfully updated",Toast.LENGTH_LONG).show();
            Intent gotoMain=new Intent(Edit_data.this,MainActivity.class);
            startActivity(gotoMain);
        }
        else
        {
            Toast.makeText(this,"Something went wrong",Toast.LENGTH_LONG).show();
        }

    }

    public void validate(String dname,String ddesc,String dprice)
    {
        boolean isnum= Utility.isnumber(dprice);
        if(dname.isEmpty()==false)
        {
            if(ddesc.isEmpty()==false || ddesc.length()>5)
            {
                if(dprice.isEmpty()==false && isnum==true)
                {
                    getupdatedvalues();

                }else
                {
                    Toast.makeText(this, "Enter valid price", Toast.LENGTH_SHORT).show();
                }
            }else{Toast.makeText(this, "Enter minimum 5 characters", Toast.LENGTH_SHORT).show();}
        }else
        {
            Toast.makeText(this, "Enter valid drug name", Toast.LENGTH_SHORT).show();
        }
    }

}


