package com.evensel.riderswyftr.authentication;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.evensel.riderswyftr.MainActivity;
import com.evensel.riderswyftr.R;
import com.evensel.riderswyftr.util.AppController;
import com.evensel.riderswyftr.util.AppURL;
import com.evensel.riderswyftr.util.DetectApplicationFunctionsAvailability;
import com.evensel.riderswyftr.util.JsonRequestManager;
import com.evensel.riderswyftr.util.LoginResponse;
import com.evensel.riderswyftr.util.Notifications;
import com.evensel.riderswyftr.util.ValidatorUtil;

/**
 * Created by Prishan Maduka on 2/11/2017.
 */
public class LoginActivity extends Activity implements View.OnClickListener {

    private TextView txtForgotPassword;
    private EditText txtUserName,txtPassword;
    private Button btnLogin;
    private LayoutInflater inflate;
    private View layout;
    private ProgressDialog progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Initializing UI components
        txtForgotPassword = (TextView)findViewById(R.id.txtForgotPassword);
        txtUserName = (EditText)findViewById(R.id.txtUserName);
        txtPassword = (EditText)findViewById(R.id.txtPassword);
        btnLogin = (Button)findViewById(R.id.btnLogin);
        inflate = getLayoutInflater();
        layout = inflate.inflate(R.layout.custom_toast_layout,(ViewGroup) findViewById(R.id.toast_layout_root));

        //Assigning click listeners
        txtForgotPassword.setOnClickListener(this);
        btnLogin.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.txtForgotPassword){
            Intent intent = new Intent(LoginActivity.this,ForgotPasswordActivity.class);
            startActivity(intent);
        }else if(view.getId()==R.id.btnLogin){
            if(txtUserName.getText().toString().isEmpty()){
                Notifications.showToastMessage(layout,getApplicationContext(),"Sorry!!! Username cannot be empty.").show();
            }else if(!ValidatorUtil.isValidEmailAddress(txtUserName.getText().toString())){
                Notifications.showToastMessage(layout,getApplicationContext(),"Sorry!!! Invalid email address.").show();
            }else if(txtPassword.getText().toString().isEmpty()){
                Notifications.showToastMessage(layout,getApplicationContext(),"Sorry!!! Password cannot be empty.").show();
            }else{
                String message = ValidatorUtil.isValidPassword(txtPassword.getText().toString());
                if(!message.equalsIgnoreCase("Success")){
                    Notifications.showToastMessage(layout,getApplicationContext(),message).show();
                }else{
                    DetectApplicationFunctionsAvailability.setmContext(LoginActivity.this);
                    if(!DetectApplicationFunctionsAvailability.isConnected()){
                        Notifications.showGeneralDialog(LoginActivity.this,getResources().getString(R.string.no_network_error)).show();
                    }else{
                        progress = ProgressDialog.show(LoginActivity.this, null,
                                "Authenticating...", true);
                        JsonRequestManager.getInstance(LoginActivity.this).loginUserRequest(AppURL.APPLICATION_BASE_URL+AppURL.USER_LOGIN_URL, txtUserName.getText().toString(),txtPassword.getText().toString(), requestCallback);


                    }

                }
            }
        }
    }

    //Response callback for "User Login"
    private final JsonRequestManager.loginUser requestCallback = new JsonRequestManager.loginUser() {

        @Override
        public void onSuccess(LoginResponse model) {

            if(progress!=null)
                progress.dismiss();

            if(model.getStatus().equalsIgnoreCase("success")){
                AppController.setLoginPreference(LoginActivity.this,txtUserName.getText().toString(),txtPassword.getText().toString(),model.getToken());
                AppController.setProfilePreference(LoginActivity.this,model.getDetails());
                logUser();
            }else{
                Notifications.showToastMessage(layout,getApplicationContext(),model.getMessage()).show();
            }


        }

        @Override
        public void onError(LoginResponse model) {
            if(progress!=null)
                progress.dismiss();
            Notifications.showToastMessage(layout,getApplicationContext(),model.getMessage()).show();
        }

        @Override
        public void onError(String status) {
            if(progress!=null)
                progress.dismiss();
            Notifications.showToastMessage(layout,getApplicationContext(),status).show();
        }
    };

    private void logUser(){
        Intent loggedIntent = new Intent(this, MainActivity.class);
        startActivity(loggedIntent);
        finish();
    }


}
