package com.evensel.riderswyftr.deliveries;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v4.util.LruCache;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.evensel.riderswyftr.R;
import com.evensel.swyftr.R;
import com.evensel.swyftr.util.AppController;
import com.evensel.swyftr.util.AppURL;
import com.evensel.swyftr.util.Constants;
import com.evensel.swyftr.util.Datum;
import com.evensel.swyftr.util.JsonRequestManager;
import com.evensel.swyftr.util.ResponseModel;
import com.mikhaellopez.circularimageview.CircularImageView;

import java.lang.ref.WeakReference;
import java.util.ArrayList;


/**
 * Created by Prishan Maduka on 8/9/2016.
 */
public class DeliveryHistoryRecycleAdapter extends  RecyclerView.Adapter<DeliveryHistoryRecycleAdapter.ImageViewHolder> {



    public DeliveryHistoryRecycleAdapter(){

    }

    @Override
    public ImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_grid_row, parent, false);

        return new ImageViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ImageViewHolder holder, final int position) {


    }


    @Override
    public int getItemCount() {
        return datumArrayList.size();
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder {
        public final ImageView imgFav,imgPlus,imgMinus,imgPromo;
        public final CircularImageView imgItem;
        public final EditText txtQuantity;
        public final TextView txtName,txtPrice,txtStocked;
        public ImageViewHolder(View itemView) {
            super(itemView);
            imgFav = (ImageView)itemView.findViewById(R.id.imgFav);
            imgPromo = (ImageView)itemView.findViewById(R.id.imgPromo);
            imgPlus = (ImageView)itemView.findViewById(R.id.imgPlus);
            imgMinus = (ImageView)itemView.findViewById(R.id.imgMinus);
            imgItem = (CircularImageView) itemView.findViewById(R.id.imgItem);
            txtStocked = (TextView)itemView.findViewById(R.id.txtStocked);
            txtName = (TextView) itemView.findViewById(R.id.txtName);
            txtPrice = (TextView) itemView.findViewById(R.id.txtPrice);
            txtQuantity = (EditText) itemView.findViewById(R.id.txtQuantity);
        }
    }




}
