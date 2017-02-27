package com.evensel.riderswyftr.deliveries;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import com.evensel.riderswyftr.R;

/**
 * Created by Prishan Maduka on 2/27/2017.
 */
public class DeliveryActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnFriendReceive,btnViewOrder;
    private Context context;
    private Dialog dialogFriend;

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

        btnFriendReceive.setOnClickListener(this);
        btnViewOrder.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        if(view.getId()==R.id.btnFriend){
            showFriendDialog(false,false,false,false);
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

    private void showFriendDialog(boolean signature,boolean nic,boolean nicImage,boolean friendPhoto){
        dialogFriend = new Dialog(context);
        dialogFriend.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogFriend.setContentView(R.layout.custom_friend_receieve_dialog);
        dialogFriend.show();

        Button btnClose = (Button)dialogFriend.findViewById(R.id.btnCancel);
        Button btnDone = (Button)dialogFriend.findViewById(R.id.btnDone);
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogFriend.dismiss();
            }
        });
        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogFriend.dismiss();
            }
        });
    }
}
