package com.paysera.lib.wallet.entities.notification;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Vytautas Gimbutas <v.gimbutas@evp.lt>
 */
public class NotificationSubscriber {
    public static final String PRIVACY_LEVEL_HIGH = "high";
    public static final String PRIVACY_LEVEL_LOW = "low";

    public static final String STATUS_ACTIVE = "active";

    protected Integer id;
    protected String status = STATUS_ACTIVE;

    protected NotificationRecipient recipient;
    protected List<NotificationEvent> events = new ArrayList<>();
    protected String locale;
    protected String privacyLevel;
    protected String type;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public NotificationRecipient getRecipient() {
        return recipient;
    }

    public void setRecipient(NotificationRecipient recipient) {
        this.recipient = recipient;
    }

    public List<NotificationEvent> getEvents() {
        return events;
    }

    public void setEvents(List<NotificationEvent> events) {
        this.events.clear();
        this.events.addAll(events);
    }

    public void addEvent(NotificationEvent event) {
        this.events.add(event);
    }

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    public String getPrivacyLevel() {
        return privacyLevel;
    }

    public void setPrivacyLevel(String privacyLevel) {
        this.privacyLevel = privacyLevel;
    }

    public boolean isHighPrivacyLevel() {
        return this.getPrivacyLevel().equals(PRIVACY_LEVEL_HIGH);
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean hasEvent(NotificationEvent event) {
        for (NotificationEvent existingEvent : this.getEvents()) {
            if (existingEvent.equals(event)) {
                return true;
            }
        }

        return false;
    }

    @Override
    public boolean equals(Object object) {
        return
                object instanceof NotificationSubscriber
            && this.getPrivacyLevel().equals(((NotificationSubscriber) object).getPrivacyLevel())
            && this.getLocale().equals(((NotificationSubscriber) object).getLocale())
            && this.getRecipient().equals(((NotificationSubscriber) object).getRecipient())
            && this.getEvents().containsAll(((NotificationSubscriber) object).getEvents())
            && ((NotificationSubscriber) object).getEvents().containsAll(this.getEvents())
        ;
    }

    public NotificationEvent getGeneralNotificationEvent() {
        for (NotificationEvent event : events) {
            if (
                   event.eventName.equals(NotificationEvent.EVENT_NAME_ALERT)
                && event.objectName.equals(NotificationEvent.OBJECT_NAME_INFORMATION)
            ) {
                return event;
            }
        }
        return null;
    }

    public NotificationEvent getTransactionRequestCreatedEvent() {
        for (NotificationEvent event : events) {
            if (
                   event.eventName.equals(NotificationEvent.EVENT_NAME_CREATED)
                && event.objectName.equals(NotificationEvent.OBJECT_NAME_TRANSACTION_REQUEST)
            ) {
                return event;
            }
        }
        return null;
    }

    public NotificationEvent getTransactionRequestCanceledEvent() {
        for (NotificationEvent event : events) {
            if (
                event.eventName.equals(NotificationEvent.EVENT_NAME_CANCELED)
                    && event.objectName.equals(NotificationEvent.OBJECT_NAME_TRANSACTION_REQUEST)
            ) {
                return event;
            }
        }
        return null;
    }

    public NotificationEvent getTransactionRequestExpiredEvent() {
        for (NotificationEvent event : events) {
            if (
                event.eventName.equals(NotificationEvent.EVENT_NAME_EXPIRED)
                    && event.objectName.equals(NotificationEvent.OBJECT_NAME_TRANSACTION_REQUEST)
            ) {
                return event;
            }
        }
        return null;
    }

    public List<NotificationEvent> getIdentityDocumentEvents() {
        ArrayList<String> identityDocumentEvents = new ArrayList<>();
        identityDocumentEvents.add(NotificationEvent.EVENT_NAME_REVIEW_STATUS_VALID);
        identityDocumentEvents.add(NotificationEvent.EVENT_NAME_REVIEW_STATUS_DENIED);

        ArrayList<NotificationEvent> existingEvents = new ArrayList<>();
        for (NotificationEvent event : events) {
            if (
                identityDocumentEvents.contains(event.eventName) &&
                    event.objectName.equals(NotificationEvent.OBJECT_NAME_IDENTITY_DOCUMENT)
            ) {
                existingEvents.add(event);
            }
        }
        return existingEvents;
    }

    public NotificationEvent getConfirmationEvent() {
        for (NotificationEvent event : events) {
            if (
                event.eventName.equals(NotificationEvent.EVENT_NAME_CREATED)
                    && event.objectName.equals(NotificationEvent.OBJECT_NAME_CONFIRMATION)
            ) {
                return event;
            }
        }
        return null;
    }

    public NotificationEvent getUserSignUpEvent() {
        for (NotificationEvent event : events) {
            if (
                event.eventName.equals(NotificationEvent.EVENT_NAME_REGISTERED)
                    && event.objectName.equals(NotificationEvent.OBJECT_NAME_USER)
            ) {
                return event;
            }
        }
        return null;
    }

    public List<NotificationEvent> getWalletEvents() {
        List<NotificationEvent> walletEvents = new ArrayList<>();
        for (NotificationEvent event : events) {
            if (
                event.objectName.equals(NotificationEvent.OBJECT_NAME_PENDING_PAYMENT)
                    || event.objectName.equals(NotificationEvent.OBJECT_NAME_STATEMENT)
            ) {
                walletEvents.add(event);
            }
        }
        return walletEvents;
    }

    public NotificationEvent getCardTransactionSuccessfulEvent() {
        for (NotificationEvent event : events) {
            if (
                event.eventName.equals(NotificationEvent.EVENT_NAME_TRANSACTION_SUCCESSFUL)
                    && event.objectName.equals(NotificationEvent.OBJECT_NAME_CARD)
            ) {
                return event;
            }
        }
        return null;
    }

    public List<NotificationEvent> getRecurrenceTransferEvents() {
        ArrayList<String> recurringPaymentEvents = new ArrayList<>();
        recurringPaymentEvents.add(NotificationEvent.EVENT_NAME_DONE);
        recurringPaymentEvents.add(NotificationEvent.EVENT_NAME_FAILED);

        ArrayList<NotificationEvent> existingEvents = new ArrayList<>();
        for (NotificationEvent event : events) {
            if (
                recurringPaymentEvents.contains(event.eventName) &&
                    event.objectName.equals(NotificationEvent.OBJECT_NAME_RECURRENCE_TRANSFER)
            ) {
                existingEvents.add(event);
            }
        }
        return existingEvents;
    }

    public List<NotificationEvent> getSavingsAccountPaymentEvents() {
        ArrayList<String> savingsAccountPaymentEvents = new ArrayList<>();
        savingsAccountPaymentEvents.add(NotificationEvent.EVENT_NAME_FILLED);
        savingsAccountPaymentEvents.add(NotificationEvent.EVENT_NAME_WITHDREW);
        savingsAccountPaymentEvents.add(NotificationEvent.EVENT_NAME_FILL_MADE);

        ArrayList<NotificationEvent> existingEvents = new ArrayList<>();

        for (NotificationEvent event : events) {
            if (
                savingsAccountPaymentEvents.contains(event.eventName) &&
                    event.objectName.equals(NotificationEvent.OBJECT_NAME_SAVINGS_ACCOUNT_PAYMENT)
            ) {
                existingEvents.add(event);
            }
        }
        return existingEvents;
    }

    public List<NotificationEvent> getNewDevicesEvents() {
        ArrayList<String> newDevicesEvents = new ArrayList<>();
        newDevicesEvents.add(NotificationEvent.EVENT_NAME_ATTEMPT);
        newDevicesEvents.add(NotificationEvent.EVENT_NAME_REJECTED);

        ArrayList<NotificationEvent> existingEvents = new ArrayList<>();

        for (NotificationEvent event : events) {
            if (
                newDevicesEvents.contains(event.eventName) &&
                    event.objectName.equals(NotificationEvent.OBJECT_NAME_NEW_DEVICE)
            ) {
                existingEvents.add(event);
            }
        }
        return existingEvents;
    }
}