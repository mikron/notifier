package com.sflpro.notifier.services.notification.impl.email;


import com.sflpro.notifier.services.template.TemplatingService;
import com.sflpro.notifier.spi.email.EmailTemplateContent;
import com.sflpro.notifier.spi.email.EmailTemplateContentResolver;

import java.util.Map;

/**
 * Created by Hayk Mkrtchyan.
 * Date: 6/19/19
 * Time: 6:06 PM
 */

class LocalEmailTemplateContentResolver implements EmailTemplateContentResolver {

    private static final String SUBJECT_SUFFIX = "_subject";
    private static final String BODY_SUFFIX = "_content";

    private final TemplatingService templatingService;

    LocalEmailTemplateContentResolver(final TemplatingService templatingService) {
        this.templatingService = templatingService;
    }

    @Override
    public EmailTemplateContent resolve(final String templateId, final Map<String, ?> variables) {
        final String subject = templatingService.getContentForTemplate(templateId + SUBJECT_SUFFIX, variables);
        final String body = templatingService.getContentForTemplate(templateId + BODY_SUFFIX, variables);
        return EmailTemplateContent.of(subject, body);
    }
}
