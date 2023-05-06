package edu.obya.blueprint.customer.adapter.jpa;

import edu.obya.blueprint.customer.domain.model.CustomerId;
import org.springframework.util.StringUtils;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class CustomerIdAttributeConverter implements AttributeConverter<CustomerId, String> {
    @Override
    public String convertToDatabaseColumn(CustomerId attribute) {
        return attribute != null ? attribute.getId().toString() : null;
    }

    @Override
    public CustomerId convertToEntityAttribute(String dbData) {
        return StringUtils.hasText(dbData) ? CustomerId.parse(dbData) : null;
    }
}
