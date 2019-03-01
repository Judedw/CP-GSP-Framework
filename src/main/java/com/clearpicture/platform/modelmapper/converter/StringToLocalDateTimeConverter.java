package com.clearpicture.platform.modelmapper.converter;


import com.clearpicture.platform.enums.DateTimePattern;
import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;

/**
 * StringToLocalDateTimeConverter - Sprint 8 : Rise of The TruVerus
 * <p>
 * Created by Raveen on 2019/02/28.
 */
@Component
public class StringToLocalDateTimeConverter implements Converter<String, LocalDateTime> {

    private static final Logger LOGGER = LoggerFactory.getLogger(StringToLocalDateTimeConverter.class);

    @Override
    public LocalDateTime convert(MappingContext<String, LocalDateTime> context) {
        if (context.getSource() == null) {
            return null;
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DateTimePattern.STRICT_DATE_TIME.getPattern())
                .withResolverStyle(ResolverStyle.STRICT);
        return (context.getSource() == null ? null : LocalDateTime.parse(context.getSource(), formatter));
    }
}
