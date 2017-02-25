package com.evensel.riderswyftr.pickup;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.evensel.riderswyftr.R;
import com.evensel.riderswyftr.util.AppController;

import java.util.Date;

/**
 * Created by Prishan Maduka on 2/25/2017.
 */
public class ScanActivity extends AppCompatActivity{

    private ImageView imgScanImage;
    private Context context;
    private boolean isSuccessful = false;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scan_parcels_layout);

        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Scan");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        context = this;
        isSuccessful = getIntent().getBooleanExtra("ISSUCCESSFUL",false);

        imgScanImage = (ImageView)findViewById(R.id.imgScan);
        imgScanImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(context,ScanCameraActivity.class);
                startActivity(intent);
                finish();
            }
        });

        if(isSuccessful){
            Date dt = new Date();
            int hours = dt.getHours();
            int minutes = dt.getMinutes();
            int seconds = dt.getSeconds();
            String curTime = hours + ":" + minutes + ":" + seconds;
            final Dialog dialog1 = new Dialog(context);
            dialog1.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog1.setContentView(R.layout.custom_success_pickup_dialog);
            dialog1.show();
            Button btnOk = (Button)dialog1.findViewById(R.id.btnContinue);
            TextView pickupLocation = (TextView)dialog1.findViewById(R.id.txtLocation);
            TextView pickedTime = (TextView)dialog1.findViewById(R.id.txtTime);
            TextView contact = (TextView)dialog1.findViewById(R.id.txtCustomer);
            pickupLocation.setText("Picked up from "+AppController.getCurrentDetails().getPickupAddress());
            contact.setText("Customer : "+AppController.getCurrentDetails().getPickupContactPerson());
            pickedTime.setText("At "+curTime);
            btnOk.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog1.dismiss();
                }
            });
        }

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(context,ParcelDetailsActivity.class);
        context.startActivity(intent);
        finish();
    }
}
