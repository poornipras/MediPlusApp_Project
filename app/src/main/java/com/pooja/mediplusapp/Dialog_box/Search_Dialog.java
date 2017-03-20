package com.pooja.mediplusapp.Dialog_box;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.pooja.mediplusapp.Activity.Drug_Data_Activity;
import com.pooja.mediplusapp.Database.DBhelper;
import com.pooja.mediplusapp.Model.MediData;
import com.pooja.mediplusapp.R;

import java.util.ArrayList;

/**
 * Created by Pooja on 2/27/2017.
 */

public class Search_Dialog extends DialogFragment {
    AutoCompleteTextView drugname;
    Button ok,cancel;
    ArrayList<MediData> mediarrlist;
    DBhelper dbhelper;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        LayoutInflater inflater=getActivity().getLayoutInflater();
        View view=inflater.inflate(R.layout.search_dialog,null);
        builder.setView(view);
        builder.setTitle("Search");
        builder.setIcon(R.drawable.ic_action_name_search);
        init(view);
        Dialog dialog=builder.create();
        dialog.getWindow().getAttributes().windowAnimations=R.style.DialogAnimation;
        setCancelable(false);
        return dialog;
    }

    private void init(View view) {
        drugname=(AutoCompleteTextView) view.findViewById(R.id.autoCompleteTextView);
        ok=(Button)view.findViewById(R.id.button_ok);
        cancel=(Button)view.findViewById(R.id.button_cancel);
        dbhelper=DBhelper.getInstance(getActivity());
        mediarrlist=new ArrayList<MediData>();
        loaddatatoautotext();
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String searchname=drugname.getText().toString();
                mediarrlist=dbhelper.displayDrugdetails(searchname);
                //////
              //  int getidfor_display_from_db=dbhelper.getidfromDB(searchname);

              if(mediarrlist.isEmpty())
                {
                    Toast.makeText(getActivity(), "Sorry drugname not found", Toast.LENGTH_SHORT).show();
                    drugname.setText("");
                    dismiss();
                }

                else {
                    Toast.makeText(getActivity(), "drugname found", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(getActivity(), Drug_Data_Activity.class);
                    i.putExtra("Name", mediarrlist.get(0).getName());
                    i.putExtra("Description", mediarrlist.get(0).getDescription());
                    i.putExtra("Price", mediarrlist.get(0).getPrice());
                    i.putExtra("Id", mediarrlist.get(0).getId());
                    i.putExtra("Category", mediarrlist.get(0).getCategory());
                    i.putExtra("Mediform",mediarrlist.get(0).getMedi_form());
                    i.putExtra("Instructions",mediarrlist.get(0).getInstructions());
                    startActivity(i);
                     }
                    mediarrlist.clear();
                    dismiss();
                }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        Toast.makeText(getActivity(),"Cancel was clicked",Toast.LENGTH_LONG).show();
        dismiss();

    }
});
    }

    public  void loaddatatoautotext()
    {
        ArrayList<String> autonames=new ArrayList<>();
        autonames=dbhelper.getdrugdatasearch();
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,autonames);
        drugname.setThreshold(1);
        drugname.setAdapter(adapter);
    }
}
