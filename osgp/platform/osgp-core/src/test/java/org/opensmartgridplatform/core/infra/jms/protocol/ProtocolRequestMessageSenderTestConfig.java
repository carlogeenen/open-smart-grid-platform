/**
 * Copyright 2018 Smart Society Services B.V.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 */
package org.opensmartgridplatform.core.infra.jms.protocol;

import static org.mockito.Mockito.mock;

import org.opensmartgridplatform.core.infra.messaging.CoreLogItemRequestMessageSender;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.DependsOn;
import org.springframework.jms.core.JmsTemplate;

public class ProtocolRequestMessageSenderTestConfig {

    @Bean
    public int messageGroupCacheSize() {
        return 1024;
    }

    @Bean
    @DependsOn("getPowerUsageHistoryRequestTimeToLive")
    public ProtocolRequestMessageSender messageSender() {
        return new ProtocolRequestMessageSender();
    }

    @Bean
    public CoreLogItemRequestMessageSender coreLogItemRequestMessageSender() {
        return mock(CoreLogItemRequestMessageSender.class);
    }

    @Bean
    public JmsTemplate coreLogItemRequestsJmsTemplate() {
        return mock(JmsTemplate.class);
    }

    @Bean
    public ProtocolRequestMessageJmsTemplateFactory protocolRequestMessageJmsTemplateFactory() {
        return mock(ProtocolRequestMessageJmsTemplateFactory.class);
    }

    @Bean
    public Long getPowerUsageHistoryRequestTimeToLive() {
        return null;
    }
}
