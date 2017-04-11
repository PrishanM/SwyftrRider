package com.evensel.riderswyftr.deliveries;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.evensel.riderswyftr.R;
import com.evensel.riderswyftr.util.Datum;

import java.util.ArrayList;


/**
 * Created by Prishan Maduka on 8/9/2016.
 */
public class DeliveryHistoryRecycleAdapter extends  RecyclerView.Adapter<DeliveryHistoryRecycleAdapter.ImageViewHolder> {


    private Context context;
    private ArrayList<Datum> datumArrayList;

    public DeliveryHistoryRecycleAdapter(Context context,ArrayList<Datum> datumArrayList){
        this.context=context;
        this.datumArrayList=datumArrayList;
    }

    @Override
    public ImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_grid_row, parent, false);

        return new ImageViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ImageViewHolder holder, final int position) {

        holder.txtOrderId.setText("Order "+String.valueOf(datumArrayList.get(position).getOrderId()));
        holder.txtPrice.setText("20000 LKR");
        holder.txtDelivery.setText(datumArrayList.get(position).getDeliveredLocation());
        holder.txtDeliveryDate.setText(datumArrayList.get(position).getOrderDeliveredTime());

    }


    @Override
    public int getItemCount() {
        return datumArrayList.size();
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder {
        public final TextView txtOrderId,txtPrice,txtPickup, txtPickupDate,txtDelivery,txtDeliveryDate;
        public ImageViewHolder(View itemView) {
            super(itemView);
            txtOrderId = (TextView)itemView.findViewById(R.id.txtOrder);
            txtPrice = (TextView)itemView.findViewById(R.id.txtTotal);
            txtPickup = (TextView)itemView.findViewById(R.id.txtPickup);
            txtPickupDate = (TextView)itemView.findViewById(R.id.txtPickupDate);
            txtDelivery = (TextView)itemView.findViewById(R.id.txtDelivery);
            txtDeliveryDate = (TextView) itemView.findViewById(R.id.txtDeliveryDate);
        }
    }




}
