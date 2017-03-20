package com.pooja.mediplusapp.Activity;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.util.Linkify;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.pooja.mediplusapp.Dialog_box.Register_dialog;
import com.pooja.mediplusapp.R;

import org.w3c.dom.Text;

public class Login extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener,GoogleApiClient.ConnectionCallbacks{
   EditText uname,pass;
   Button login_btn;
    TextView register;

    TextView forgotpassword;
    LinearLayout forgot_layout;
    EditText forgot_code;
    Button submit;
    TextView textregister;
    SignInButton signInButton;
    //private TextView mName;
   // private Button signOut;
    private GoogleApiClient mGoogleApiClient;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        uname=(EditText) findViewById(R.id.enter_username);
        pass=(EditText)findViewById(R.id.enter_password);
        login_btn=(Button)findViewById(R.id.login_button);
        forgot_code=(EditText)findViewById(R.id.enter_forgot_code);
        forgot_layout=(LinearLayout)findViewById(R.id.linear_forgot_password);
        submit=(Button)findViewById(R.id.btn_submit_forgot_code);
        textregister=(TextView)findViewById(R.id.text_register);

        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences preferences=getSharedPreferences("USERLOGININFO",MODE_PRIVATE);
                String unamepref=preferences.getString("USERNAME","No data");
                String passpref=preferences.getString("PASSWORD","No data");


                if(uname.getText().toString().isEmpty() && pass.getText().toString().isEmpty())
                {
                    Toast.makeText(Login.this, "Invalid data", Toast.LENGTH_SHORT).show();
                }
                    else {
                    if (uname.getText().toString().equals(unamepref) && pass.getText().toString().equals(passpref)) {
                        Toast.makeText(Login.this, "Login Successful", Toast.LENGTH_SHORT).show();
                        textregister.setVisibility(View.INVISIBLE);
                        Intent gotomain=new Intent(Login.this,MainActivity.class);
                        gotomain.putExtra("Login","Login");
                        startActivity(gotomain);

                    } else {
                        Toast.makeText(Login.this, "Login Unsuccessful", Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });
        register=(TextView)findViewById(R.id.text_register);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Login.this, "register", Toast.LENGTH_SHORT).show();
                Register_dialog register_dialog=new Register_dialog();
                register_dialog.show(getFragmentManager(),"Register_dialog");
            }
        });

        forgotpassword=(TextView)findViewById(R.id.forgot_password);
        forgotpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                forgot_layout.setVisibility(View.VISIBLE);
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences preferences=getSharedPreferences("USERLOGININFO",MODE_PRIVATE);
                String passpref=preferences.getString("PASSWORD","No data");
                String codepref=preferences.getString("CODE","No data");
                if(forgot_code.getText().toString().equals(codepref))
                {
                    Toast.makeText(Login.this, "Your password is:"+passpref, Toast.LENGTH_SHORT).show();
                }
            }
        });



        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleApiClient=new GoogleApiClient.Builder(this).enableAutoManage(this/* FragmentActivity */,this/* OnConnectionFailedListener */).
                addApi(Auth.GOOGLE_SIGN_IN_API,gso).build();

        signInButton = (SignInButton) findViewById(R.id.sign_in_button);
        signInButton.setSize(SignInButton.SIZE_STANDARD);

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signInToGmail();
            }
        });
    }

    private void signInToGmail(){
        Intent signInIntent= Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent,9000);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 9000) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }
    }

    private void handleSignInResult(GoogleSignInResult result) {
        if (result.isSuccess()) {
            // Signed in successfully, show authenticated UI.
            GoogleSignInAccount acct = result.getSignInAccount();
            Intent gotomain=new Intent(Login.this,MainActivity.class);
            gotomain.putExtra("Gmail","Gmail");
            startActivity(gotomain);



        } else {
            // Signed out, show unauthenticated UI.
            Toast.makeText(this, "Login Failed", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        Toast.makeText(this, "Connected", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onConnectionSuspended(int i) {
        Toast.makeText(this, "Suspended", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Toast.makeText(this, "Failed", Toast.LENGTH_LONG).show();
    }
}
