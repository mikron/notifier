package com.sflpro.notifier.api.facade.services.email.impl;

import com.sflpro.notifier.api.facade.services.AbstractNotificationServiceFacadeImpl;
import com.sflpro.notifier.api.facade.services.email.EmailNotificationServiceFacade;
import com.sflpro.notifier.api.model.common.result.ErrorResponseModel;
import com.sflpro.notifier.api.model.common.result.ResultResponseModel;
import com.sflpro.notifier.api.model.email.EmailNotificationModel;
import com.sflpro.notifier.api.model.email.request.CreateEmailNotificationRequest;
import com.sflpro.notifier.api.model.email.response.CreateEmailNotificationResponse;
import com.sflpro.notifier.db.entities.notification.NotificationProviderType;
import com.sflpro.notifier.db.entities.notification.email.EmailNotification;
import com.sflpro.notifier.services.notification.dto.email.EmailNotificationDto;
import com.sflpro.notifier.services.notification.dto.email.EmailNotificationPropertyDto;
import com.sflpro.notifier.services.notification.email.EmailNotificationService;
import com.sflpro.notifier.services.notification.event.sms.StartSendingNotificationEvent;
import com.sflpro.notifier.services.system.event.ApplicationEventDistributionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.stream.Collectors;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 1/12/16
 * Time: 7:34 PM
 */
@Component
public class EmailNotificationServiceFacadeImpl extends AbstractNotificationServiceFacadeImpl implements EmailNotificationServiceFacade {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmailNotificationServiceFacadeImpl.class);

    /* Dependencies */
    @Autowired
    private EmailNotificationService emailNotificationService;

    @Autowired
    private ApplicationEventDistributionService applicationEventDistributionService;

    /* Constructors */
    public EmailNotificationServiceFacadeImpl() {
        //default constructor
    }

    @Nonnull
    @Override
    public ResultResponseModel<CreateEmailNotificationResponse> createEmailNotification(@Nonnull final CreateEmailNotificationRequest request) {
        Assert.notNull(request, "Request model should not be null");
        LOGGER.debug("Creating email notification, request - {}", request);
        // Validate request
        final List<ErrorResponseModel> errors = request.validateRequiredFields();
        if (!errors.isEmpty()) {
            return new ResultResponseModel<>(errors);
        }
        // Create notification DTO
        final EmailNotificationDto emailNotificationDto = new EmailNotificationDto(request.getRecipientEmail(),
                request.getSenderEmail(), NotificationProviderType.SMTP_SERVER, request.getBody(),
                request.getSubject(), request.getClientIpAddress(), request.getTemplateName());
        final EmailNotification emailNotification;
        final List<EmailNotificationPropertyDto> emailNotificationPropertyDtos = request.getProperties().entrySet()
                .stream()
                .map(entry -> new EmailNotificationPropertyDto(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());
        emailNotification = emailNotificationService.createEmailNotification(emailNotificationDto, emailNotificationPropertyDtos);
        associateUserWithNotificationIfRequired(request.getUserUuId(), emailNotification);
        // Publish event
        applicationEventDistributionService.publishAsynchronousEvent(new StartSendingNotificationEvent(emailNotification.getId()));
        // Create response model
        final EmailNotificationModel emailNotificationModel = createEmailNotificationModel(emailNotification);
        final CreateEmailNotificationResponse response = new CreateEmailNotificationResponse(emailNotificationModel);
        LOGGER.debug("Successfully created response model for email request - {}, response - {}", request, response);
        return new ResultResponseModel<>(response);
    }

    /* Utility methods */
    private EmailNotificationModel createEmailNotificationModel(final EmailNotification emailNotification) {
        final EmailNotificationModel notificationModel = new EmailNotificationModel();
        setNotificationCommonProperties(notificationModel, emailNotification);
        notificationModel.setSenderEmail(emailNotification.getSenderEmail());
        notificationModel.setRecipientEmail(emailNotification.getRecipientEmail());
        return notificationModel;
    }
}