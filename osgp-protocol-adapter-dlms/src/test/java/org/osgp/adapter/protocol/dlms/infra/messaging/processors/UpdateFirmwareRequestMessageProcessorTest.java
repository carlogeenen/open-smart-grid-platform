/**
 * Copyright 2017 Smart Society Services B.V.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 */
package org.osgp.adapter.protocol.dlms.infra.messaging.processors;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import javax.jms.JMSException;
import javax.jms.ObjectMessage;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.osgp.adapter.protocol.dlms.application.services.DomainHelperService;
import org.osgp.adapter.protocol.dlms.application.services.FirmwareService;
import org.osgp.adapter.protocol.dlms.domain.entities.DlmsDevice;
import org.osgp.adapter.protocol.dlms.domain.factories.DlmsConnectionFactory;
import org.osgp.adapter.protocol.dlms.domain.factories.DlmsConnectionHolder;
import org.osgp.adapter.protocol.dlms.exceptions.OsgpExceptionConverter;
import org.osgp.adapter.protocol.dlms.exceptions.ProtocolAdapterException;
import org.osgp.adapter.protocol.dlms.infra.messaging.DeviceResponseMessageSender;
import org.osgp.adapter.protocol.dlms.infra.messaging.DlmsLogItemRequestMessageSender;
import org.osgp.adapter.protocol.dlms.infra.messaging.DlmsMessageListener;
import org.osgp.adapter.protocol.dlms.infra.messaging.RetryHeaderFactory;
import org.osgp.adapter.protocol.dlms.infra.messaging.requests.to.core.OsgpRequestMessageSender;

import com.alliander.osgp.shared.exceptionhandling.FunctionalException;
import com.alliander.osgp.shared.exceptionhandling.TechnicalException;
import com.alliander.osgp.shared.infra.jms.MessageMetadata;
import com.alliander.osgp.shared.infra.jms.ObjectMessageBuilder;
import com.alliander.osgp.shared.infra.jms.RequestMessage;

public class UpdateFirmwareRequestMessageProcessorTest {

    @Mock
    protected DeviceResponseMessageSender responseMessageSender;

    @Mock
    protected DlmsLogItemRequestMessageSender dlmsLogItemRequestMessageSender;

    @Mock
    protected OsgpExceptionConverter osgpExceptionConverter;

    @Mock
    private RetryHeaderFactory retryHeaderFactory;

    @Mock
    private FirmwareService firmwareService;

    @Mock
    private OsgpRequestMessageSender osgpRequestMessageSender;

    @Mock
    private DomainHelperService domainHelperService;

    @Mock
    private DlmsConnectionFactory dlmsConnectionFactory;

    @Mock
    private DlmsConnectionHolder dlmsConnectionHolderMock;

    @Mock
    private DlmsMessageListener messageListenerMock;

    @Mock
    private DlmsDevice dlmsDeviceMock;

    @InjectMocks
    private UpdateFirmwareRequestMessageProcessor processor;

    @Before
    public void setup() throws FunctionalException, ProtocolAdapterException, TechnicalException {
        MockitoAnnotations.initMocks(this);

        when(this.domainHelperService.findDlmsDevice(any(MessageMetadata.class))).thenReturn(this.dlmsDeviceMock);
        when(this.dlmsConnectionFactory.getConnection(this.dlmsDeviceMock, null))
                .thenReturn(this.dlmsConnectionHolderMock);
        when(this.dlmsConnectionHolderMock.getDlmsMessageListener()).thenReturn(this.messageListenerMock);
    }

    @Test
    public void processMessageShouldSendFirmwareFileRequestWhenFirmwareFileNotAvailable() throws JMSException {
        // Arrange
        final String firmwareIdentification = "unavailable";
        final ObjectMessage message = new ObjectMessageBuilder().withObject(firmwareIdentification).build();
        when(this.firmwareService.isFirmwareFileAvailable(firmwareIdentification)).thenReturn(false);

        // Act
        this.processor.processMessage(message);

        // Assert
        verify(this.osgpRequestMessageSender, times(1)).send(any(RequestMessage.class), any(String.class),
                any(MessageMetadata.class));
    }

    @Test
    public void processMessageShouldNotSendFirmwareFileRequestWhenFirmwareFileAvailable() throws JMSException {
        // Arrange
        final String firmwareIdentification = "unavailable";
        final ObjectMessage message = new ObjectMessageBuilder().withObject(firmwareIdentification).build();
        when(this.firmwareService.isFirmwareFileAvailable(firmwareIdentification)).thenReturn(true);

        // Act
        this.processor.processMessage(message);

        // Assert
        verify(this.osgpRequestMessageSender, never()).send(any(RequestMessage.class), any(String.class),
                any(MessageMetadata.class));
    }

    @Test
    public void processMessageShouldUpdateFirmwareWhenFirmwareFileAvailable()
            throws JMSException, ProtocolAdapterException, FunctionalException, TechnicalException {
        // Arrange
        final String firmwareIdentification = "available";
        final ObjectMessage message = new ObjectMessageBuilder().withObject(firmwareIdentification).build();
        when(this.firmwareService.isFirmwareFileAvailable(firmwareIdentification)).thenReturn(true);

        // Act
        this.processor.processMessage(message);

        // Assert
        verify(this.firmwareService, times(1)).updateFirmware(this.dlmsConnectionHolderMock, this.dlmsDeviceMock,
                firmwareIdentification);
    }

    @Test
    public void processMessageShouldNotUpdateFirmwareWhenFirmwareFileNotAvailable()
            throws JMSException, ProtocolAdapterException, FunctionalException, TechnicalException {
        // Arrange
        final String firmwareIdentification = "unavailable";
        final ObjectMessage message = new ObjectMessageBuilder().withObject(firmwareIdentification).build();
        when(this.firmwareService.isFirmwareFileAvailable(firmwareIdentification)).thenReturn(false);

        // Act
        this.processor.processMessage(message);

        // Assert
        verify(this.firmwareService, times(0)).updateFirmware(this.dlmsConnectionHolderMock, this.dlmsDeviceMock,
                firmwareIdentification);
    }
}
