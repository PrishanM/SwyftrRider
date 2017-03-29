
package com.evensel.riderswyftr.util;

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
public class Datum {

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

}
