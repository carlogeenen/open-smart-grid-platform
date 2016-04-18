/**
 * Copyright 2014-2016 Smart Society Services B.V.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 */
package com.alliander.osgp.adapter.protocol.iec61850.infra.messaging.processors;

import javax.jms.JMSException;
import javax.jms.ObjectMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.alliander.osgp.adapter.protocol.iec61850.infra.messaging.DeviceRequestMessageProcessor;
import com.alliander.osgp.adapter.protocol.iec61850.infra.messaging.DeviceRequestMessageType;
import com.alliander.osgp.shared.exceptionhandling.ComponentType;
import com.alliander.osgp.shared.exceptionhandling.NotSupportedException;
import com.alliander.osgp.shared.infra.jms.Constants;

/**
 * Class for processing public lighting resume schedule request messages
 */
@Component("iec61850PublicLightingResumeScheduleRequestMessageProcessor")
public class PublicLightingResumeScheduleRequestMessageProcessor extends DeviceRequestMessageProcessor {
    /**
     * Logger for this class
     */
    private static final Logger LOGGER = LoggerFactory
            .getLogger(PublicLightingResumeScheduleRequestMessageProcessor.class);

    public PublicLightingResumeScheduleRequestMessageProcessor() {
        super(DeviceRequestMessageType.RESUME_SCHEDULE);
    }

    @Override
    public void processMessage(final ObjectMessage message) {
        LOGGER.debug("Processing public lighting resume schedule request message");

        String correlationUid = null;
        String domain = null;
        String domainVersion = null;
        String messageType = null;
        String organisationIdentification = null;
        String deviceIdentification = null;
        String ipAddress = null;

        try {
            correlationUid = message.getJMSCorrelationID();
            domain = message.getStringProperty(Constants.DOMAIN);
            domainVersion = message.getStringProperty(Constants.DOMAIN_VERSION);
            messageType = message.getJMSType();
            organisationIdentification = message.getStringProperty(Constants.ORGANISATION_IDENTIFICATION);
            deviceIdentification = message.getStringProperty(Constants.DEVICE_IDENTIFICATION);
            ipAddress = message.getStringProperty(Constants.IP_ADDRESS);
        } catch (final JMSException e) {
            LOGGER.error("UNRECOVERABLE ERROR, unable to read ObjectMessage instance, giving up.", e);
            LOGGER.debug("correlationUid: {}", correlationUid);
            LOGGER.debug("domain: {}", domain);
            LOGGER.debug("domainVersion: {}", domainVersion);
            LOGGER.debug("messageType: {}", messageType);
            LOGGER.debug("organisationIdentification: {}", organisationIdentification);
            LOGGER.debug("deviceIdentification: {}", deviceIdentification);
            LOGGER.debug("ipAddress: {}", ipAddress);
            return;
        }

        LOGGER.info("Calling DeviceService function: {} for domain: {} {}", messageType, domain, domainVersion);

        this.handleExpectedError(new NotSupportedException(ComponentType.PROTOCOL_IEC61850,
                "ResumeSchedule is not supported"), correlationUid, organisationIdentification, deviceIdentification,
                domain, domainVersion, messageType);
    }

}
