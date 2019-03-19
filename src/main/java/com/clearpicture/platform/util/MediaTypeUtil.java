package com.clearpicture.platform.util;


import org.springframework.http.MediaType;

import javax.servlet.ServletContext;

/**
 * MediaTypeUtil - Sprint 8 : Rise of The TruVerus
 * <p>
 * Created by Raveen on 2019/03/18.
 */

public class MediaTypeUtil {

    // abc.zip
    // abc.pdf,..
    public static MediaType getMediaTypeForFileName(ServletContext servletContext, String fileName) {
        // application/pdf
        // application/xml
        // image/gif, ...
        String mineType = servletContext.getMimeType(fileName);
        try {
            MediaType mediaType = MediaType.parseMediaType(mineType);
            return mediaType;
        } catch (Exception e) {
            return MediaType.APPLICATION_OCTET_STREAM;
        }
    }


}
