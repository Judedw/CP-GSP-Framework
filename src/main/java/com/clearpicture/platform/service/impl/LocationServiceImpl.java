package com.clearpicture.platform.service.impl;


import com.clearpicture.platform.dto.response.IpLocationResponse;
import com.clearpicture.platform.exception.ComplexValidationException;
import com.clearpicture.platform.service.LocationService;
import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.model.CityResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import software.amazon.ion.IonException;

import java.io.File;
import java.io.InputStream;
import java.net.InetAddress;

/**
 * created by Raveen -  2019/07/19
 * <p>
 * LocationServiceImpl - Common Service for All the location stuff
 */

@Service
public class LocationServiceImpl implements LocationService {

    @Autowired
    private ResourceLoader resourceLoader;


    @Override
    public IpLocationResponse getIpLocation(String ipAddress) {

        File dbFile = null;
        try {

            Resource resource = resourceLoader.getResource("classpath:GeoLite2-City.mmdb");
            // Resource resource = new ClassPathResource("GeoLite2-City.mmdb");
            InputStream input = resource.getInputStream();
            dbFile = resource.getFile();
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new ComplexValidationException("location", "issueInFileLoad");
        }

        try {
            DatabaseReader dbReader = new DatabaseReader.Builder(dbFile).build();

            InetAddress ipAddr = InetAddress.getByName(ipAddress);
            CityResponse response = dbReader.city(ipAddr);

            String countryName = response.getCountry().getName();
            String cityName = response.getCity().getName();
            String postal = response.getPostal().getCode();
            String state = response.getLeastSpecificSubdivision().getName();
            double latitude = response.getLocation().getLatitude();
            double longitude = response.getLocation().getLongitude();
            
            IpLocationResponse locationResponse = new IpLocationResponse(ipAddress, countryName, cityName, latitude, longitude);

            return locationResponse;
        } catch (Exception ioe) {
            ioe.printStackTrace();
            throw new ComplexValidationException("location", "issueInDbRead");
        }


    }
}
