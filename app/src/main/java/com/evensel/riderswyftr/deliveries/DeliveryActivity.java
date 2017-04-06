package com.evensel.riderswyftr.deliveries;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.evensel.riderswyftr.R;
import com.evensel.riderswyftr.util.AppController;
import com.evensel.riderswyftr.util.Constants;
import com.evensel.riderswyftr.util.Notifications;
import com.github.gcacace.signaturepad.views.SignaturePad;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Prishan Maduka on 2/27/2017.
 */
public class DeliveryActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnFriendReceive,btnViewOrder;
    private Context context;
    private Dialog dialogFriend;
    private Uri fileUri;

    private View layout;
    private LayoutInflater inflate;

    private static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 100;
    private static final int FRIEND_CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 110;
    public static final int MEDIA_TYPE_IMAGE = 1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.parcel_delivery_own_user_layout);

        context = this;

        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Scanner");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        btnFriendReceive = (Button)findViewById(R.id.btnFriend);
        btnViewOrder = (Button)findViewById(R.id.btnViewOrder);

        inflate = getLayoutInflater();
        layout = inflate.inflate(R.layout.custom_toast_layout,(ViewGroup)findViewById(R.id.toast_layout_root));

        btnFriendReceive.setOnClickListener(this);
        btnViewOrder.setOnClickListener(this);

        startHandler();
    }

    private void startHandler() {
        int SPLASH_TIMEOUT_TIME = 3000;
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage("Your inquiry has been successfully added");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                        paramDialogInterface.dismiss();
                        Intent intent = new Intent(DeliveryActivity.this,RateDeliveryActivity.class);
                        startActivity(intent);
                    }
                });

                builder.create().show();


            }
        }, SPLASH_TIMEOUT_TIME);
    }

    @Override
    public void onClick(View view) {

        if(view.getId()==R.id.btnFriend){
            showFriendDialog();
        }else{
            final Dialog dialogOrder = new Dialog(context);
            dialogOrder.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialogOrder.setContentView(R.layout.custom_order_dialog);
            dialogOrder.show();
            Button btnClose = (Button)dialogOrder.findViewById(R.id.btnClose);
            btnClose.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialogOrder.dismiss();
                }
            });
        }

    }

    private void showFriendDialog(){
        dialogFriend = new Dialog(context);
        dialogFriend.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogFriend.setContentView(R.layout.custom_friend_receieve_dialog);
        dialogFriend.show();

        LinearLayout layoutSignature = (LinearLayout)dialogFriend.findViewById(R.id.layoutSignature);
        LinearLayout layoutNicImage = (LinearLayout)dialogFriend.findViewById(R.id.layoutNicImage);
        LinearLayout layoutFriendImage = (LinearLayout)dialogFriend.findViewById(R.id.layoutFriendImage);

        ImageView imgSignature = (ImageView)dialogFriend.findViewById(R.id.imgSignatureCheck);
        ImageView imgNicImage = (ImageView)dialogFriend.findViewById(R.id.imgNicImgCheck);
        ImageView imgFriendImage = (ImageView)dialogFriend.findViewById(R.id.imgFriendImgCheck);
        final ImageView imgNic = (ImageView)dialogFriend.findViewById(R.id.imgNicCheck);

        final EditText edtNic = (EditText)dialogFriend.findViewById(R.id.edtNic);

        if(AppController.getSignature()!=null){
            edtNic.setEnabled(true);
        }else{
            edtNic.setEnabled(false);
        }

        edtNic.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(count==10){
                    imgNic.setImageResource(R.drawable.success_ico);
                    AppController.setNicNumber(edtNic.getText().toString());
                }else{
                    imgNic.setImageResource(R.drawable.onprocess_ico);
                    AppController.setNicNumber(null);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        if(AppController.getSignature()!=null){
            imgSignature.setImageResource(R.drawable.success_ico);
        }

        if(AppController.getNic()!=null){
            imgNicImage.setImageResource(R.drawable.success_ico);
        }

        if(AppController.getFriendImage()!=null){
            imgFriendImage.setImageResource(R.drawable.success_ico);
        }

        Button btnClose = (Button)dialogFriend.findViewById(R.id.btnCancel);
        Button btnDone = (Button)dialogFriend.findViewById(R.id.btnDone);
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogFriend.dismiss();
                AppController.setSignature(null);
                AppController.setNic(null);
                AppController.setFriendImage(null);
                AppController.setNicNumber(null);
            }
        });
        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(AppController.getSignature()!=null && AppController.getNicNumber()!=null && !AppController.getNicNumber().isEmpty()){
                    dialogFriend.dismiss();
                    final Dialog dialog2 = new Dialog(context);
                    dialog2.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog2.setContentView(R.layout.custom_dilivary_success_dialog);
                    dialog2.show();
                    Button btnRate = (Button)dialog2.findViewById(R.id.btnRate);
                    btnRate.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            AppController.setSignature(null);
                            AppController.setNic(null);
                            AppController.setFriendImage(null);
                            AppController.setNicNumber(null);
                            dialog2.dismiss();

                            finish();
                        }
                    });
                }else{
                    Notifications.showToastMessage(layout,context,"Signature and NIC are mandatory.").show();
                }

            }
        });
        layoutSignature.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogFriend.dismiss();
                showSignatureBox();
            }
        });

        layoutNicImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogFriend.dismiss();
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
                startActivityForResult(intent, CAMERA_CAPTURE_IMAGE_REQUEST_CODE);
            }
        });

        layoutFriendImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogFriend.dismiss();
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
                startActivityForResult(intent, FRIEND_CAMERA_CAPTURE_IMAGE_REQUEST_CODE);
            }
        });
    }

    private void showSignatureBox(){
        final Dialog signatureDialog = new Dialog(context);
        signatureDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        signatureDialog.setContentView(R.layout.custom_signature_pad_dialog);
        signatureDialog.show();
        Button btnBack = (Button)signatureDialog.findViewById(R.id.btnBack);
        Button btnContinue = (Button)signatureDialog.findViewById(R.id.btnContinue);
        final SignaturePad mSignaturePad = (SignaturePad)signatureDialog.findViewById(R.id.signature_pad);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signatureDialog.dismiss();
                showFriendDialog();
            }
        });

        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mSignaturePad.getSignatureBitmap()!=null){
                    AppController.setSignature(mSignaturePad.getSignatureBitmap());
                }
                signatureDialog.dismiss();
                showFriendDialog();
            }
        });


    }

    public Uri getOutputMediaFileUri(int type) {
        return Uri.fromFile(getOutputMediaFile(type));
    }

    /**
     * returning image / video
     */
    private static File getOutputMediaFile(int type) {

        // External sdcard location
        File mediaStorageDir = new File(
                Environment
                        .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                Constants.IMAGE_DIRECTORY_NAME);

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                return null;
            }
        }

        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",
                Locale.getDefault()).format(new Date());
        File mediaFile;
        if (type == MEDIA_TYPE_IMAGE) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator
                    + "IMG_" + timeStamp + ".jpg");
        }else {
            return null;
        }

        return mediaFile;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_CAPTURE_IMAGE_REQUEST_CODE) {
            if (resultCode == -1) {
                AppController.setNic(getImage(fileUri.getPath()));
            }
        }else if (requestCode == FRIEND_CAMERA_CAPTURE_IMAGE_REQUEST_CODE){
            if (resultCode == -1) {
                AppController.setFriendImage(getImage(fileUri.getPath()));
            }
        }

        showFriendDialog();
    }

    private Bitmap getImage(String path){
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 2;
        Bitmap realPhoto = BitmapFactory.decodeFile(path, options);
        return realPhoto;
    }
}
