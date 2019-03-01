package com.clearpicture.platform.modelmapper.converter;


import com.clearpicture.platform.service.CryptoService;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * StringToLongConverter
 * Created by nuwan on 7/21/18.
 */
@Component
public class StringToLongConverter implements Converter<String, Long> {

    @Autowired
    private CryptoService cryptoService;
    private static final String ATTR_ID = "id";
    private static final String ATTR_VOTE_ID = "voteId";
    private static final String ATTR_PRODUCT_ID = "productId";
    private static final String ATTR_CLIENT_ID = "clientId";
    private static final String ATTR_SURVEY_ID = "surveyId";
    private static final String ATTR_INTERACTION_ID = "interactionId";
    private static final String ATTR_CREATED_USER_ID = "createdUserId";
    private static final String ATTR_MODIFIED_USER_ID = "lastModifiedUserId";

    public StringToLongConverter() {
    }

    public Long convert(MappingContext<String, Long> mappingContext) {
        if (StringUtils.isNotBlank((CharSequence) mappingContext.getSource())) {
            if (mappingContext.getMapping() == null) {
                return cryptoService.decryptEntityId((String) mappingContext.getSource());
            }

            String propertyName = mappingContext.getMapping().getLastDestinationProperty().getName();
            if (("id".equalsIgnoreCase(propertyName)) || ("voteId".equalsIgnoreCase(propertyName)) ||
                    ("productId".equalsIgnoreCase(propertyName)) || ("clientId".equalsIgnoreCase(propertyName)) ||
                    ("surveyId".equalsIgnoreCase(propertyName)) || ("interactionId".equalsIgnoreCase(propertyName))
                    || "createdUserId".equalsIgnoreCase(propertyName) || "lastModifiedUserId".equalsIgnoreCase(propertyName)) {
                return cryptoService.decryptEntityId((String) mappingContext.getSource());
            }
            return mappingContext.getSource() == null ? null : Long.valueOf((String) mappingContext.getSource());
        }

        return null;
    }
}
