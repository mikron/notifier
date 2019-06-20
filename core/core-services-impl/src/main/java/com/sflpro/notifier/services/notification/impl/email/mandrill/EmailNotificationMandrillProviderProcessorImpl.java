package com.sflpro.notifier.services.notification.impl.email.mandrill;

import com.sflpro.notifier.db.entities.NotificationProperty;
import com.sflpro.notifier.db.entities.notification.NotificationProviderType;
import com.sflpro.notifier.db.entities.notification.email.EmailNotification;
import com.sflpro.notifier.externalclients.email.mandrill.communicator.MandrillApiCommunicator;
import com.sflpro.notifier.externalclients.email.mandrill.model.request.SendEmailRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * User: Ruben Vardanyan
 * Company: SFL LLC
 * Date: 4/16/19
 * Time: 8:35 PM
 */
@Component
public class EmailNotificationMandrillProviderProcessorImpl implements EmailNotificationMandrillProviderProcessor {
    private static final Logger LOGGER = LoggerFactory.getLogger(EmailNotificationMandrillProviderProcessorImpl.class);

    @Autowired
    private MandrillApiCommunicator mandrillApiCommunicator;

    public EmailNotificationMandrillProviderProcessorImpl() {
        LOGGER.debug("Initializing email notification Mandrill provider processor impl");
    }

    @Override
    public boolean processEmailNotification(@Nonnull final EmailNotification emailNotification, @Nonnull final Map<String, String> secureProperties) {
        Assert.notNull(emailNotification, "Email notification should not be null");
        Assert.notNull(secureProperties, "Secure properties map should not be null");
        Assert.isTrue(NotificationProviderType.MANDRILL.equals(emailNotification.getProviderType()), "Email notification provider type should be MANDRILL");
        Assert.notNull(emailNotification.getTemplateName(), "Template name for Mandrill email provider should not be null");
        final SendEmailRequest emailRequest = createEmailRequest(emailNotification, secureProperties);
        final boolean result = mandrillApiCommunicator.sendEmailTemplate(emailRequest);
        LOGGER.debug("Successfully sent email message for third party email notification - {}", emailNotification);
        return result;
    }

    private static SendEmailRequest createEmailRequest(final EmailNotification emailNotification, final Map<String, String> secureProperties) {
        final Set<NotificationProperty> properties = emailNotification.getProperties();
        final Map<String, String> templateContent = new HashMap<>();
        if (!CollectionUtils.isEmpty(properties)) {
            properties.forEach(property -> templateContent.put(property.getPropertyKey(), property.getPropertyValue()));
        }
        if(!secureProperties.isEmpty()) {
            templateContent.putAll(secureProperties);
        }
        return new SendEmailRequest(emailNotification.getRecipientEmail(), emailNotification.getTemplateName(), templateContent);
    }
}
