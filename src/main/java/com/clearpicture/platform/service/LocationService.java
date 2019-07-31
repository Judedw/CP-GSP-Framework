package com.clearpicture.platform.service;

import com.clearpicture.platform.dto.response.IpLocationResponse;

/**
 * created by Raveen -  2019/07/19
 * <p>
 * LocationService - Common Service for All the location stuff
 */


public interface LocationService {

    IpLocationResponse getIpLocation(String ipAddress);
}
