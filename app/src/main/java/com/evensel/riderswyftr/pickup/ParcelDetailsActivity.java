package com.evensel.riderswyftr.pickup;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.evensel.riderswyftr.R;
import com.evensel.riderswyftr.util.AppController;

/**
 * Created by Prishan Maduka on 2/25/2017.
 */
public class ParcelDetailsActivity extends AppCompatActivity {

    private TextView txtUserName;
    private Button btnScan;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.parcel_details_layout);

        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Parcel Details");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        txtUserName = (TextView)findViewById(R.id.txtRiderName);
        btnScan = (Button)findViewById(R.id.btnScan);
        if(AppController.getUserName()!=null && !AppController.getUserName().isEmpty()){
            txtUserName.setText(AppController.getUserName());
        }

        btnScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ParcelDetailsActivity.this,ScanActivity.class);
                intent.putExtra("ISSUCCESSFUL",false);
                startActivity(intent);
            }
        });
    }
}
