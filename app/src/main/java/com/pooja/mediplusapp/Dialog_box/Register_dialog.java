package com.pooja.mediplusapp.Dialog_box;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.pooja.mediplusapp.R;

/**
 * Created by Pooja on 3/18/2017.
 */

public class Register_dialog extends DialogFragment {
    EditText username,pass,code;
    Button btn_register,cancel;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        LayoutInflater inflater=getActivity().getLayoutInflater();
        View view=inflater.inflate(R.layout.register_dialog,null);
        builder.setView(view);
        init(view);
        builder.setTitle("Register New User");
        Dialog dialog=builder.create();
        setCancelable(false);
        return dialog;
    }
    public void init(View view)
    {
        username=(EditText)view.findViewById(R.id.register_username);
        pass=(EditText)view.findViewById(R.id.register_password);
        code=(EditText)view.findViewById(R.id.register_code);

        btn_register=(Button)view.findViewById(R.id.register_btn);
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getActivity(),"U:"+uname, Toast.LENGTH_SHORT).show();
                if(username.getText().toString()!=null && pass.getText().toString()!=null&& code.getText().toString()!=null){
                SharedPreferences shaPreferences=getActivity().getSharedPreferences("USERLOGININFO",Context.MODE_PRIVATE);
                SharedPreferences.Editor editor=shaPreferences.edit();
                editor.putString("USERNAME",username.getText().toString());
                editor.putString("PASSWORD",pass.getText().toString());
                editor.putString("CODE",code.getText().toString());
                editor.commit();
                    Toast.makeText(getActivity(), "Successfully stored the values", Toast.LENGTH_SHORT).show();
                    dismiss();
                }else
                {
                    Toast.makeText(getActivity(), "Please enter valid data to register", Toast.LENGTH_SHORT).show();
                }
            }

        });
        cancel=(Button)view.findViewById(R.id.button_cancel_register);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();

            }
        });
    }

}
