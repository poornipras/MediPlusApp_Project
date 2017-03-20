package com.pooja.mediplusapp.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.pooja.mediplusapp.Database.DBhelper;
import com.pooja.mediplusapp.R;
import com.pooja.mediplusapp.Util.Utility;

import java.util.ArrayList;

public class AddDrugDetails extends AppCompatActivity{
    Toolbar adddrug_toolbar;
    RadioGroup category;
    RadioButton radio_category_button;
    EditText name,desc,price;
    Spinner spinner_form_of_medi,spinner_instructions;
    DBhelper dBhelper;
    ArrayList<String> stringArrayList;
    String mediform_text,instruction_text;
    boolean exists;
    private AdView mAdView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_drug_details);

        //ads
        mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder()
                .build();
        mAdView.loadAd(adRequest);
        ///
        adddrug_toolbar=(Toolbar)findViewById(R.id.toolbar_adddrug_activity);
        setSupportActionBar(adddrug_toolbar);
        stringArrayList=new ArrayList<>();
        exists=false;
        dBhelper=DBhelper.getInstance(AddDrugDetails.this);
        name=(EditText)findViewById(R.id.enter_drugname);
        desc=(EditText)findViewById(R.id.enter_drugdesc);
        price=(EditText)findViewById(R.id.enter_drugprice);
        category=(RadioGroup)findViewById(R.id.radio_category);

        spinner_form_of_medi= (Spinner) findViewById(R.id.spinner_form_of_medication);
        ArrayAdapter spinnermedi_form_Adapter=ArrayAdapter.createFromResource(AddDrugDetails.this,R.array.spinner_form_of_medication,android.R.layout.simple_spinner_item);
        spinner_form_of_medi.setAdapter(spinnermedi_form_Adapter);
        spinner_form_of_medi.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                TextView selecteditemspinner = (TextView) view;
                mediform_text=selecteditemspinner.getText().toString();
                //Toast.makeText(AddDrugDetails.this, "Selected:" + mediform_text, Toast.LENGTH_LONG).show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spinner_instructions = (Spinner) findViewById(R.id.spinner_instructions);
        ArrayAdapter spinner_instructions_adapter=ArrayAdapter.createFromResource(AddDrugDetails.this,R.array.spinner_instructions,android.R.layout.simple_spinner_item);
        spinner_instructions.setAdapter(spinner_instructions_adapter);
        spinner_instructions.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                TextView instruction=(TextView)view;
                instruction_text=instruction.getText().toString();
                //Toast.makeText(AddDrugDetails.this,"Instruction"+instruction_text,Toast.LENGTH_LONG).show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
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
                                   String iname=name.getText().toString();
                                   String idesc=desc.getText().toString();
                                   String iprice=price.getText().toString();
                                   int selectedcatid=category.getCheckedRadioButtonId();
                                   radio_category_button=(RadioButton)findViewById(selectedcatid);
                                   String cat=radio_category_button.getText().toString();
                                          validate(iname,idesc,iprice,cat,mediform_text,instruction_text);
                                          break;
        }

        return super.onOptionsItemSelected(item);
    }

    public void validate(String iname,String idesc,String iprice,String cat,String mediform,String instructions)
    {
       // boolean isnumber=isnumber(iprice);
        boolean isnumber= Utility.isnumber(iprice);
        if(iname.isEmpty()==false)
        {
            if(idesc.isEmpty()==false || idesc.length()>5)
            {
                if(iprice.isEmpty()==false && isnumber!=false)
                {

                    Toast.makeText(getApplicationContext(),"All clear",Toast.LENGTH_LONG).show();

                    boolean check=checkDuplication(iname);
                    if(check==false)
                    {
                        Toast.makeText(getApplicationContext(),"New Entry",Toast.LENGTH_LONG).show();
                        getvalues(iname,idesc,iprice,cat,mediform,instructions);
                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(),"Could not add as data already exists",Toast.LENGTH_LONG).show();
                        name.setText("");
                        desc.setText("");
                        price.setText("");
                    }
                }else
                {
                    Toast.makeText(getApplicationContext(),"Enter valid Price",Toast.LENGTH_LONG).show();
                }

            }else
            {
                Toast.makeText(getApplicationContext(),"Description should be minimum 5 caracters",Toast.LENGTH_LONG).show();
            }

        }else
        {
            Toast.makeText(getApplicationContext(),"Enter Drug name",Toast.LENGTH_LONG).show();
        }

    }
    public void getvalues(String iname,String idesc,String iprice,String cat,String mediform,String instruction_text)
    {

        long result=dBhelper.insertintotable(iname,idesc,iprice,cat,mediform,instruction_text);
        if(result!=-1){
            int receivedid=dBhelper.getidfromDB(iname);
            Toast.makeText(this, "success", Toast.LENGTH_SHORT).show();
            Intent i=new Intent(AddDrugDetails.this,Drug_Data_Activity.class);
            i.putExtra("Name",iname);
            i.putExtra("Description",idesc);
            i.putExtra("Price",iprice);
            i.putExtra("Category",cat);
            i.putExtra("Id",receivedid);
            i.putExtra("Mediform",mediform);
            i.putExtra("Instructions",instruction_text);
            startActivity(i);}
        else
        {
            Toast.makeText(this, "something went wrong", Toast.LENGTH_SHORT).show();
        }

    }

    public boolean checkDuplication(String iname)
    {
        stringArrayList=dBhelper.getdrugdatasearch();
        for(int i=0;i<stringArrayList.size();i++)
        {
            if(iname.equalsIgnoreCase(stringArrayList.get(i).toString())) {
                exists=true;
                break;
            }
            exists=false;
        }
        return exists;
    }

    @Override
    protected void onResume() {
        if (mAdView != null) {
            mAdView.resume();
        }
        super.onResume();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onPause() {
        if (mAdView != null) {
            mAdView.pause();
        }
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        if (mAdView != null) {
            mAdView.destroy();
        }
        super.onDestroy();
    }
}
