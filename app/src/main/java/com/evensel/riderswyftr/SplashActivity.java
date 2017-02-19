package com.evensel.riderswyftr;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;

import com.evensel.riderswyftr.authentication.LoginActivity;
import com.evensel.riderswyftr.util.AppController;
import com.evensel.riderswyftr.util.AppURL;
import com.evensel.riderswyftr.util.Constants;
import com.evensel.riderswyftr.util.DetectApplicationFunctionsAvailability;
import com.evensel.riderswyftr.util.JsonRequestManager;
import com.evensel.riderswyftr.util.LoginResponse;

import java.util.ArrayList;
import java.util.List;

public class SplashActivity extends Activity {

    private Context context;
    private int locationServicesState=0;
    private int locationCoarseState=0;
    private int writeExternalStorage=0;
    private final List<String> permissionList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        context = this;

        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.LOLLIPOP_MR1) {
            startHandler();
        }else{
            locationServicesState = checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION); // Permission for location services
            locationCoarseState = checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION); // Permission for location services
            writeExternalStorage = checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE);

            if(locationServicesState!=PackageManager.PERMISSION_GRANTED){
                permissionList.add(Manifest.permission.ACCESS_FINE_LOCATION);
            }
            if(locationCoarseState!=PackageManager.PERMISSION_GRANTED){
                permissionList.add(Manifest.permission.ACCESS_COARSE_LOCATION);
            }
            if(writeExternalStorage!=PackageManager.PERMISSION_GRANTED){
                permissionList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
            }

            if (permissionList.size()>0){
                String[] permissionArray = new String[permissionList.size()];
                permissionList.toArray(permissionArray);
                int REQUEST_CODE = 1100;
                requestPermissions(permissionArray, REQUEST_CODE);
            }else{
                startHandler();
            }
        }
    }

    private void startHandler() {
        int SPLASH_TIMEOUT_TIME = 3000;
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                //Start next activity
                SharedPreferences sharedPref = getSharedPreferences(Constants.LOGIN_SHARED_PREF, Context.MODE_PRIVATE);
                String username = sharedPref.getString(Constants.LOGIN_SHARED_PREF_USERNAME, "");
                String password = sharedPref.getString(Constants.LOGIN_SHARED_PREF_PASSWORD, "");

                if(username!=null && !username.isEmpty() && password!=null && !password.isEmpty()){
                    DetectApplicationFunctionsAvailability.setmContext(context);
                    if(!DetectApplicationFunctionsAvailability.isConnected()){
                        AlertDialog alertDialog = new AlertDialog.Builder(context).create();
                        // Setting Dialog Message
                        alertDialog.setMessage(getResources().getString(R.string.no_network_error));
                        // Setting OK Button
                        alertDialog.setButton("Ok", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                            }
                        });
                        alertDialog.show();
                        //Notifications.showGeneralDialog(context,getResources().getString(R.string.no_network_error)).show();
                    }else{
                        JsonRequestManager.getInstance(context).loginUserRequest(AppURL.APPLICATION_BASE_URL+AppURL.USER_LOGIN_URL, username,password, requestCallback);
                    }

                }else{
                    navigateLogin();
                }


            }
        }, SPLASH_TIMEOUT_TIME);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case 1100:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    startHandler();
                } else {
                    // Permission Denied
                    System.exit(1);
                }
                break;
            default:
                System.exit(1);
                break;
        }
    }

    //Response callback for "User Login"
    private final JsonRequestManager.loginUser requestCallback = new JsonRequestManager.loginUser() {

        @Override
        public void onSuccess(LoginResponse model) {

            if(model.getStatus().equalsIgnoreCase("success")){
                SharedPreferences sharedPref =context.getSharedPreferences(Constants.LOGIN_SHARED_PREF, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putString(Constants.LOGIN_ACCESS_TOKEN, model.getToken());
                editor.commit();
                AppController.setProfilePreference(context,model.getDetails());

                Intent loggedIntent = new Intent(context, MainActivity.class);
                startActivity(loggedIntent);
                finish();
            }else{
                navigateLogin();
            }


        }

        @Override
        public void onError(LoginResponse model) {
            navigateLogin();
        }

        @Override
        public void onError(String status) {
            navigateLogin();
        }
    };

    private void navigateLogin(){
        Intent mainActivity = new Intent(SplashActivity.this, LoginActivity.class);
        startActivity(mainActivity);
        finish();
    }
}
