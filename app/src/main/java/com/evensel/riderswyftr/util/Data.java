
package com.evensel.riderswyftr.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.ALWAYS)
@JsonPropertyOrder({
    "pickup_contact_person",
    "pickup_address",
    "pickup_lon",
    "pickup_lat",
    "dropoff_address",
    "dropoff_lon",
    "dropoff_lat"
})
public class Data {

    @JsonProperty("pickup_contact_person")
    private String pickupContactPerson;
    @JsonProperty("pickup_address")
    private String pickupAddress;
    @JsonProperty("pickup_lon")
    private Double pickupLon;
    @JsonProperty("pickup_lat")
    private Double pickupLat;
    @JsonProperty("dropoff_address")
    private String dropoffAddress;
    @JsonProperty("dropoff_lon")
    private Double dropoffLon;
    @JsonProperty("dropoff_lat")
    private Double dropoffLat;

    @JsonProperty("pickup_contact_person")
    public String getPickupContactPerson() {
        return pickupContactPerson;
    }

    @JsonProperty("pickup_contact_person")
    public void setPickupContactPerson(String pickupContactPerson) {
        this.pickupContactPerson = pickupContactPerson;
    }

    @JsonProperty("pickup_address")
    public String getPickupAddress() {
        return pickupAddress;
    }

    @JsonProperty("pickup_address")
    public void setPickupAddress(String pickupAddress) {
        this.pickupAddress = pickupAddress;
    }

    @JsonProperty("pickup_lon")
    public Double getPickupLon() {
        return pickupLon;
    }

    @JsonProperty("pickup_lon")
    public void setPickupLon(Double pickupLon) {
        this.pickupLon = pickupLon;
    }

    @JsonProperty("pickup_lat")
    public Double getPickupLat() {
        return pickupLat;
    }

    @JsonProperty("pickup_lat")
    public void setPickupLat(Double pickupLat) {
        this.pickupLat = pickupLat;
    }

    @JsonProperty("dropoff_address")
    public String getDropoffAddress() {
        return dropoffAddress;
    }

    @JsonProperty("dropoff_address")
    public void setDropoffAddress(String dropoffAddress) {
        this.dropoffAddress = dropoffAddress;
    }

    @JsonProperty("dropoff_lon")
    public Double getDropoffLon() {
        return dropoffLon;
    }

    @JsonProperty("dropoff_lon")
    public void setDropoffLon(Double dropoffLon) {
        this.dropoffLon = dropoffLon;
    }

    @JsonProperty("dropoff_lat")
    public Double getDropoffLat() {
        return dropoffLat;
    }

    @JsonProperty("dropoff_lat")
    public void setDropoffLat(Double dropoffLat) {
        this.dropoffLat = dropoffLat;
    }

}
