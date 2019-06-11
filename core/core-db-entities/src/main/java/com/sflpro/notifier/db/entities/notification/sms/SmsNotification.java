package com.sflpro.notifier.db.entities.notification.sms;

import com.sflpro.notifier.db.entities.notification.Notification;
import com.sflpro.notifier.db.entities.notification.NotificationType;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 3/21/15
 * Time: 12:48 PM
 */
@Entity
@DiscriminatorValue(value = "SMS")
@Table(name = "notification_sms")
public class SmsNotification extends Notification {
    private static final long serialVersionUID = -6341974220564915997L;

    /* Properties */
    @Column(name = "recipient_mobile_number", nullable = false)
    private String recipientMobileNumber;

    @Column(name = "template_name")
    private String templateName;

    @OneToMany(mappedBy = "smsNotification", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<SmsNotificationProperty> properties;

    /* Constructors */
    public SmsNotification() {
        initializeDefaults();
    }

    public SmsNotification(final boolean generateUuId) {
        super(generateUuId);
        initializeDefaults();
    }

    /* Properties getters and setters */
    public String getRecipientMobileNumber() {
        return recipientMobileNumber;
    }

    public void setRecipientMobileNumber(final String recipientMobileNumber) {
        this.recipientMobileNumber = recipientMobileNumber;
    }

    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(final String templateName) {
        this.templateName = templateName;
    }

    public Set<SmsNotificationProperty> getProperties() {
        return properties;
    }

    public void setProperties(final Set<SmsNotificationProperty> properties) {
        this.properties = properties;
    }

    /* Private utility methods */
    private void initializeDefaults() {
        setType(NotificationType.SMS);
        this.properties = new LinkedHashSet<>();
    }

    /* Equals, HashCode and ToString */
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SmsNotification)) {
            return false;
        }
        final SmsNotification that = (SmsNotification) o;
        final EqualsBuilder builder = new EqualsBuilder();
        builder.appendSuper(super.equals(that));
        builder.append(this.getRecipientMobileNumber(), that.getRecipientMobileNumber());
        builder.append(this.getTemplateName(), that.getTemplateName());
        return builder.isEquals();
    }

    @Override
    public int hashCode() {
        final HashCodeBuilder builder = new HashCodeBuilder();
        builder.appendSuper(super.hashCode());
        builder.append(this.getRecipientMobileNumber());
        builder.append(this.getTemplateName());
        return builder.build();
    }


    @Override
    public String toString() {
        final ToStringBuilder builder = new ToStringBuilder(this);
        builder.appendSuper(super.toString());
        builder.append("recipientMobileNumber", this.getRecipientMobileNumber());
        builder.append("templateName", this.getTemplateName());
        return builder.build();
    }
}
