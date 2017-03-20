package com.pooja.mediplusapp.Activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.pooja.mediplusapp.R;

public class Drug_Data_Activity extends AppCompatActivity {

    Toolbar drugdata_toolbar;
    TextView ename,edesc,eprice,ecategory,eid,emediform,einstructions;
    String intentname,intentdesc,intentprice,intentCategory,intent_mediform,intent_instructions;
    int intentid;
    //Edit_Drug_Data editDrugData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drug__data_);
        drugdata_toolbar=(Toolbar)findViewById(R.id.toolbar_drug_data);

        ename=(TextView) findViewById(R.id.display_drug_name);
        edesc=(TextView) findViewById(R.id.display_drug_desc);
        eprice=(TextView) findViewById(R.id.display_drug_price);
        ecategory=(TextView)findViewById(R.id.txt_disp_category_selected);
        eid=(TextView)findViewById(R.id.id_drug_data);
        emediform=(TextView)findViewById(R.id.txt_disp_form_of_medication);
        einstructions=(TextView)findViewById(R.id.txt_disp_instructions);
        setSupportActionBar(drugdata_toolbar);

    }

    @Override
    protected void onResume() {
        super.onResume();
        intentname=getIntent().getStringExtra("Name");
        ename.setText(intentname);
        intentdesc=getIntent().getStringExtra("Description");
        edesc.setText(intentdesc);
        intentprice=getIntent().getStringExtra("Price");
        eprice.setText(intentprice);
        intentCategory=getIntent().getStringExtra("Category");
        if(intentCategory.equalsIgnoreCase("Generic"))
        {
           // Toast.makeText(getApplicationContext(),"gen",Toast.LENGTH_LONG).show();
            ecategory.setText("Generic");
        }
        else
        {
           // Toast.makeText(getApplicationContext(),"Spe",Toast.LENGTH_LONG).show();
            ecategory.setText("Specific");
        }
        intentid=getIntent().getIntExtra("Id",0);
        eid.setText(String.valueOf(intentid));

        intent_mediform=getIntent().getStringExtra("Mediform");
        emediform.setText(intent_mediform);
        intent_instructions=getIntent().getStringExtra("Instructions");
        einstructions.setText(intent_instructions);
        // Toast.makeText(getApplicationContext(),""+intent_mediform+intent_instructions,Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.drug_data,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();
        switch(id)
        {
            case R.id.menu_home:
                Intent in=new Intent(Drug_Data_Activity.this,MainActivity.class);
                startActivity(in);
               // ename.setText("");
                //edesc.setText("");
                //eprice.setText("");
                //ecategory.setText("");
                break;

            case R.id.menu_share:
                Intent intent = new Intent(android.content.Intent.ACTION_SEND);
                intent.setType("text/plain");
                String shareBodyText ="Medicine name:"+intentname+"\t purspose:"+intentdesc+"\n"+intentprice+"rs"+"\nCategory:"+intentCategory+"\nMedicine form:"+intent_mediform+"\n"+intent_instructions;
                intent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Medicine Information");
                intent.putExtra(android.content.Intent.EXTRA_TEXT, shareBodyText);
                startActivity(Intent.createChooser(intent, "Share via"));
                break;

            case R.id.menu_edit:
                datatoedit(intentname,intentdesc,intentprice,intentCategory,intentid,intent_mediform,intent_instructions);
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    public void datatoedit(String name,String desc,String price,String category,int id,String mediform,String instructions)
    {
        Intent intentedit=new Intent(Drug_Data_Activity.this,Edit_data.class);
        intentedit.putExtra("Name",name);
        intentedit.putExtra("Description",desc);
        intentedit.putExtra("Price",price);
        intentedit.putExtra("Category",category);
        intentedit.putExtra("Id",id);
        intentedit.putExtra("Mediform",mediform);
        intentedit.putExtra("Instructions",instructions);
        startActivity(intentedit);
    }
}
