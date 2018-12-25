package com.clearpicture.platform.dto.response;

import lombok.Data;

@Data
public class GeneralAuthResponse {

    private String access_token;

    private String refresh_token;

    private String user_id;

    private String redirectToOnboarding;
}
