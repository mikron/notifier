package com.sflpro.notifier.spi.email;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Map;
import java.util.Optional;

/**
 * Created by Hayk Mkrtchyan.
 * Date: 6/19/19
 * Time: 10:42 AM
 */
final class ImmutableTemplatedEmailMessage extends AbstractEmailMessage implements TemplatedEmailMessage {

    private final String templateId;
    private final Map<String, ?> variables;

    ImmutableTemplatedEmailMessage(final String from, final String to, final String templateId, final Map<String, ?> variables) {
        super(from, to);
        this.templateId = templateId;
        this.variables = variables;
    }

    ImmutableTemplatedEmailMessage(final String from, final String to, final String templateId, final String subject, final Map<String, ?> variables) {
        super(from, to, subject);
        this.templateId = templateId;
        this.variables = variables;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TemplatedEmailMessage)) {
            return false;
        }
        final TemplatedEmailMessage that = (TemplatedEmailMessage) o;
        return new EqualsBuilder()
                .appendSuper(super.equals(o))
                .append(templateId, that.templateId())
                .append(variables, that.variables())
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .appendSuper(super.hashCode())
                .append(templateId)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("templateId", templateId)
                .append("variables", variables)
                .toString();
    }

    @Override
    public String templateId() {
        return templateId;
    }

    @Override
    public Map<String, ?> variables() {
        return variables;
    }

    @Override
    public Optional<String> subject() {
        return Optional.ofNullable(getSubject());
    }
}
