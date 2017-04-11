
package com.evensel.riderswyftr.util;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.ALWAYS)
@JsonPropertyOrder({
    "order_id",
    "store_name",
    "order_placed_time",
    "order_delivered_time",
    "delivered_location",
    "delivery_logitude",
    "delivery_lattitude"
})
public class Datum implements Parcelable {

    @JsonProperty("order_id")
    private Long orderId;
    @JsonProperty("store_name")
    private String storeName;
    @JsonProperty("order_placed_time")
    private String orderPlacedTime;
    @JsonProperty("order_delivered_time")
    private String orderDeliveredTime;
    @JsonProperty("delivered_location")
    private String deliveredLocation;
    @JsonProperty("delivery_logitude")
    private Long deliveryLogitude;
    @JsonProperty("delivery_lattitude")
    private Long deliveryLattitude;

    @JsonProperty("order_id")
    public Long getOrderId() {
        return orderId;
    }

    @JsonProperty("order_id")
    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    @JsonProperty("store_name")
    public String getStoreName() {
        return storeName;
    }

    @JsonProperty("store_name")
    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    @JsonProperty("order_placed_time")
    public String getOrderPlacedTime() {
        return orderPlacedTime;
    }

    @JsonProperty("order_placed_time")
    public void setOrderPlacedTime(String orderPlacedTime) {
        this.orderPlacedTime = orderPlacedTime;
    }

    @JsonProperty("order_delivered_time")
    public String getOrderDeliveredTime() {
        return orderDeliveredTime;
    }

    @JsonProperty("order_delivered_time")
    public void setOrderDeliveredTime(String orderDeliveredTime) {
        this.orderDeliveredTime = orderDeliveredTime;
    }

    @JsonProperty("delivered_location")
    public String getDeliveredLocation() {
        return deliveredLocation;
    }

    @JsonProperty("delivered_location")
    public void setDeliveredLocation(String deliveredLocation) {
        this.deliveredLocation = deliveredLocation;
    }

    @JsonProperty("delivery_logitude")
    public Long getDeliveryLogitude() {
        return deliveryLogitude;
    }

    @JsonProperty("delivery_logitude")
    public void setDeliveryLogitude(Long deliveryLogitude) {
        this.deliveryLogitude = deliveryLogitude;
    }

    @JsonProperty("delivery_lattitude")
    public Long getDeliveryLattitude() {
        return deliveryLattitude;
    }

    @JsonProperty("delivery_lattitude")
    public void setDeliveryLattitude(Long deliveryLattitude) {
        this.deliveryLattitude = deliveryLattitude;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.orderId);
        dest.writeString(this.storeName);
        dest.writeString(this.orderPlacedTime);
        dest.writeString(this.orderDeliveredTime);
        dest.writeString(this.deliveredLocation);
        dest.writeValue(this.deliveryLogitude);
        dest.writeValue(this.deliveryLattitude);
    }

    public Datum() {
    }

    protected Datum(Parcel in) {
        this.orderId = (Long) in.readValue(Long.class.getClassLoader());
        this.storeName = in.readString();
        this.orderPlacedTime = in.readString();
        this.orderDeliveredTime = in.readString();
        this.deliveredLocation = in.readString();
        this.deliveryLogitude = (Long) in.readValue(Long.class.getClassLoader());
        this.deliveryLattitude = (Long) in.readValue(Long.class.getClassLoader());
    }

    public static final Parcelable.Creator<Datum> CREATOR = new Parcelable.Creator<Datum>() {
        @Override
        public Datum createFromParcel(Parcel source) {
            return new Datum(source);
        }

        @Override
        public Datum[] newArray(int size) {
            return new Datum[size];
        }
    };
}
