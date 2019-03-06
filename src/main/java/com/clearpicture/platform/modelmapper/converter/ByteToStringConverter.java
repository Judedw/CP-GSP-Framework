package com.clearpicture.platform.modelmapper.converter;


import com.clearpicture.platform.exception.ComplexValidationException;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;

/**
 * ByteToStringConverter - Sprint 8 : Rise of The TruVerus
 * <p>
 * Created by Raveen on 2019/03/06.
 */
@Component
public class ByteToStringConverter implements Converter<byte[], String> {


    private static final String ATTR_PROFILEIMG = "profileImg";
    private static final String ATTR_PROMO_POSTER = "promoPoster";
    private static final String ATTR_POSTER = "poster";

    @Override
    public String convert(MappingContext<byte[], String> mappingContext) {

        try {
            if (mappingContext.getSource() != null) {
                if (mappingContext.getMapping() == null) {
                    return new String(mappingContext.getSource(), "UTF-8");
                }

                String propertyName = mappingContext.getMapping().getLastDestinationProperty().getName();
                if (("profileImg".equalsIgnoreCase(propertyName)) || "promoPoster".equalsIgnoreCase(propertyName)
                        || "poster".equalsIgnoreCase(propertyName) || "clientLogo".equalsIgnoreCase(propertyName)) {
                    return new String(mappingContext.getSource(), "UTF-8");
                }
                return mappingContext.getSource() == null ? null : new String(mappingContext.getSource(), "UTF-8");

            }

            return null;
        } catch (UnsupportedEncodingException unx) {
            throw new ComplexValidationException("converter", "byteToString.EncodeError");
        }

    }
}
