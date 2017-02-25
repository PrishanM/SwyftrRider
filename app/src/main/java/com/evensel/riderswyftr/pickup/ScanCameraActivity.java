package com.evensel.riderswyftr.pickup;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.evensel.riderswyftr.R;
import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

/**
 * Created by Prishan Maduka on 2/25/2017.
 */
public class ScanCameraActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler{

    private ZXingScannerView mScannerView;
    private Context context;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scan_camera_layout);

        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Scan");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        context = this;
        mScannerView = new ZXingScannerView(context);   // Programmatically initialize the scanner view
        setContentView(mScannerView);

        mScannerView.setResultHandler((ZXingScannerView.ResultHandler) context); // Register ourselves as a handler for scan results.
        mScannerView.startCamera();
    }

    @Override
    public void handleResult(Result result) {
        Log.e("handler", result.getText()); // Prints scan results
        Log.e("handler", result.getBarcodeFormat().toString()); // Prints the scan format (qrcode)

        mScannerView.stopCamera();

        Intent intent = new Intent(context,ScanActivity.class);
        intent.putExtra("ISSUCCESSFUL",true);
        startActivity(intent);
        finish();
        // show the scanner result into dialog box.

    }

    @Override
    protected void onPause() {
        super.onPause();
        mScannerView.stopCamera();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(context,ScanActivity.class);
        intent.putExtra("ISSUCCESSFUL",false);
        startActivity(intent);
        finish();
    }
}
