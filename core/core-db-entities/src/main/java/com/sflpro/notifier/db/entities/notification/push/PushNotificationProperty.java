package com.sflpro.notifier.db.entities.notification.push;

import com.sflpro.notifier.db.entities.NotificationProperty;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.*;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 9/10/15
 * Time: 10:52 AM
 */
@Entity
@Table(name = "notification_push_property")
public class PushNotificationProperty extends NotificationProperty {

    private static final long serialVersionUID = 5488885427885340245L;

    /* Properties */
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "push_notification_id", nullable = false, unique = false)
    private PushNotification pushNotification;

    /* Constructors */
    public PushNotificationProperty() {
        super();
    }

    /* Properties getters and setters */
    public PushNotification getPushNotification() {
        return pushNotification;
    }

    public void setPushNotification(final PushNotification pushNotification) {
        this.pushNotification = pushNotification;
    }

    /* Equals, HashCode and ToString */
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PushNotificationProperty)) {
            return false;
        }
        final PushNotificationProperty that = (PushNotificationProperty) o;
        final EqualsBuilder builder = new EqualsBuilder();
        builder.appendSuper(super.equals(that));
        builder.append(getIdOrNull(this.getPushNotification()), getIdOrNull(that.getPushNotification()));
        return builder.isEquals();
    }

    @Override
    public int hashCode() {
        final HashCodeBuilder builder = new HashCodeBuilder();
        builder.appendSuper(super.hashCode());
        builder.append(getIdOrNull(this.getPushNotification()));
        return builder.build();
    }


    @Override
    public String toString() {
        final ToStringBuilder builder = new ToStringBuilder(this);
        builder.appendSuper(super.toString());
        builder.append("pushNotification", getIdOrNull(this.getPushNotification()));
        return builder.build();
    }
}
