package com.clearpicture.platform.modelmapper.converter;


import org.apache.commons.lang3.StringUtils;
import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;
import org.springframework.stereotype.Component;

/**
 * StringToByteConverter - Sprint 8 : Rise of The TruVerus
 * <p>
 * Created by Raveen on 2019/03/05.
 */
@Component
public class StringToByteConverter implements Converter<String, byte[]> {

    private static final String ATTR_PROFILEIMG = "profileImg";
    private static final String ATTR_PROMO_POSTER = "promoPoster";
    private static final String ATTR_POSTER = "poster";
    private static final String ATTR_CLIENT_LOGO = "clientLogo";


    @Override
    public byte[] convert(MappingContext<String, byte[]> mappingContext) {

        if (StringUtils.isNotBlank((CharSequence) mappingContext.getSource())) {
            if (mappingContext.getMapping() == null) {
                return mappingContext.getSource().getBytes();
            }

            String propertyName = mappingContext.getMapping().getLastDestinationProperty().getName();
            if (("profileImg".equalsIgnoreCase(propertyName)) || "promoPoster".equalsIgnoreCase(propertyName)
                    || "poster".equalsIgnoreCase(propertyName) || "clientLogo".equalsIgnoreCase(propertyName)) {
                return mappingContext.getSource().getBytes();
            }
            return mappingContext.getSource() == null ? null : mappingContext.getSource().getBytes();

        }

        return null;
    }
}
