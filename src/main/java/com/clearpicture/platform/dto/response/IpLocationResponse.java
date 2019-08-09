package com.clearpicture.platform.dto.response;


import lombok.Data;


/**
 * created by Raveen -  2019/07/19
 * <p>
 * IpLocationResponse - Common Service for All the location stuff
 */


@Data
public class IpLocationResponse {

    private String ipAddress;
    private String country;
    private String countryCode;
    private String city;
    private double latitude;
    private double longitude;

    public IpLocationResponse(String ipAddress, String country, String countryCode, String city, double latitude, double longitude) {
        this.ipAddress = ipAddress;
        this.country = country;
        this.countryCode = countryCode;
        this.city = city;
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
