package com.sflpro.notifier.api.model.notification.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sflpro.notifier.api.model.common.result.ErrorResponseModel;
import com.sflpro.notifier.api.model.common.result.ErrorType;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Hayk Mkrtchyan.
 * Date: 7/1/19
 * Time: 4:11 PM
 */
public class AbstractTemplatableCreateNotificationRequest extends AbstractCreateNotificationRequest {

    @JsonProperty("templateName")
    private String templateName;

    @JsonProperty("properties")
    private Map<String, String> properties;

    @JsonProperty("secureProperties")
    private Map<String, String> secureProperties;

    /* Constructors */
    public AbstractTemplatableCreateNotificationRequest() {
        properties = new HashMap<>();
        secureProperties = new HashMap<>();
    }

    @Nonnull
    @Override
    public List<ErrorResponseModel> validateRequiredFields() {
        final List<ErrorResponseModel> errors = new ArrayList<>();
        if (StringUtils.isBlank(getBody()) && StringUtils.isBlank(getTemplateName())) {
            errors.add(new ErrorResponseModel(ErrorType.NOTIFICATION_BODY_MISSING));
        }
        return errors;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AbstractTemplatableCreateNotificationRequest)) {
            return false;
        }
        final AbstractTemplatableCreateNotificationRequest that = (AbstractTemplatableCreateNotificationRequest) o;
        return new EqualsBuilder()
                .appendSuper(super.equals(o))
                .append(templateName, that.templateName)
                .append(properties, that.properties)
                .append(secureProperties, that.secureProperties)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .appendSuper(super.hashCode())
                .append(templateName)
                .append(properties)
                .append(secureProperties)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("templateName", templateName)
                .append("properties", properties)
                .append("secureProperties", secureProperties)
                .toString();
    }

    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(final String templateName) {
        this.templateName = templateName;
    }

    public Map<String, String> getProperties() {
        return properties;
    }

    public void setProperties(final Map<String, String> properties) {
        this.properties = properties;
    }

    public Map<String, String> getSecureProperties() {
        return secureProperties;
    }

    public void setSecureProperties(final Map<String, String> secureProperties) {
        this.secureProperties = secureProperties;
    }
}
