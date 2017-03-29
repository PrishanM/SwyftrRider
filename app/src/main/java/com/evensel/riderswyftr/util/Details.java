
package com.evensel.riderswyftr.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.List;

@JsonInclude(JsonInclude.Include.ALWAYS)
@JsonPropertyOrder({
    "name",
    "email",
    "phone_no",
    "location",
    "home_address",
    "office_address",
    "profile_image",
    "total",
    "per_page",
    "current_page",
    "last_page",
    "next_page_url",
    "prev_page_url",
    "from",
    "to",
    "data"
})
public class Details {

    @JsonProperty("name")
    private String name;
    @JsonProperty("email")
    private String email;
    @JsonProperty("phone_no")
    private String phoneNo;
    @JsonProperty("location")
    private String location;
    @JsonProperty("home_address")
    private String homeAddress;
    @JsonProperty("office_address")
    private String officeAddress;
    @JsonProperty("profile_image")
    private String profileImage;

    @JsonProperty("total")
    private Long total;
    @JsonProperty("per_page")
    private String perPage;
    @JsonProperty("current_page")
    private Long currentPage;
    @JsonProperty("last_page")
    private Long lastPage;
    @JsonProperty("next_page_url")
    private Object nextPageUrl;
    @JsonProperty("prev_page_url")
    private Object prevPageUrl;
    @JsonProperty("from")
    private Long from;
    @JsonProperty("to")
    private Long to;
    @JsonProperty("data")
    private List<Datum> data = null;

    @JsonProperty("total")
    public Long getTotal() {
        return total;
    }

    @JsonProperty("total")
    public void setTotal(Long total) {
        this.total = total;
    }

    @JsonProperty("per_page")
    public String getPerPage() {
        return perPage;
    }

    @JsonProperty("per_page")
    public void setPerPage(String perPage) {
        this.perPage = perPage;
    }

    @JsonProperty("current_page")
    public Long getCurrentPage() {
        return currentPage;
    }

    @JsonProperty("current_page")
    public void setCurrentPage(Long currentPage) {
        this.currentPage = currentPage;
    }

    @JsonProperty("last_page")
    public Long getLastPage() {
        return lastPage;
    }

    @JsonProperty("last_page")
    public void setLastPage(Long lastPage) {
        this.lastPage = lastPage;
    }

    @JsonProperty("next_page_url")
    public Object getNextPageUrl() {
        return nextPageUrl;
    }

    @JsonProperty("next_page_url")
    public void setNextPageUrl(Object nextPageUrl) {
        this.nextPageUrl = nextPageUrl;
    }

    @JsonProperty("prev_page_url")
    public Object getPrevPageUrl() {
        return prevPageUrl;
    }

    @JsonProperty("prev_page_url")
    public void setPrevPageUrl(Object prevPageUrl) {
        this.prevPageUrl = prevPageUrl;
    }

    @JsonProperty("from")
    public Long getFrom() {
        return from;
    }

    @JsonProperty("from")
    public void setFrom(Long from) {
        this.from = from;
    }

    @JsonProperty("to")
    public Long getTo() {
        return to;
    }

    @JsonProperty("to")
    public void setTo(Long to) {
        this.to = to;
    }

    @JsonProperty("data")
    public List<Datum> getData() {
        return data;
    }

    @JsonProperty("data")
    public void setData(List<Datum> data) {
        this.data = data;
    }

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty("email")
    public String getEmail() {
        return email;
    }

    @JsonProperty("email")
    public void setEmail(String email) {
        this.email = email;
    }

    @JsonProperty("phone_no")
    public String getPhoneNo() {
        return phoneNo;
    }

    @JsonProperty("phone_no")
    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    @JsonProperty("location")
    public String getLocation() {
        return location;
    }

    @JsonProperty("location")
    public void setLocation(String location) {
        this.location = location;
    }

    @JsonProperty("home_address")
    public String getHomeAddress() {
        return homeAddress;
    }

    @JsonProperty("home_address")
    public void setHomeAddress(String homeAddress) {
        this.homeAddress = homeAddress;
    }

    @JsonProperty("office_address")
    public String getOfficeAddress() {
        return officeAddress;
    }

    @JsonProperty("office_address")
    public void setOfficeAddress(String officeAddress) {
        this.officeAddress = officeAddress;
    }

    @JsonProperty("profile_image")
    public String getProfileImage() {
        return profileImage;
    }

    @JsonProperty("profile_image")
    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

}
