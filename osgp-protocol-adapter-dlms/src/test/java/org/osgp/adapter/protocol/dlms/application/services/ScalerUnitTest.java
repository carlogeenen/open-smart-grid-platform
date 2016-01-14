/**
 * Copyright 2015 Smart Society Services B.V.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 */
package org.osgp.adapter.protocol.dlms.application.services;

import com.alliander.osgp.dto.valueobjects.smartmetering.DlmsUnit;
import com.alliander.osgp.dto.valueobjects.smartmetering.ScalerUnit;
import org.junit.Assert;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.osgp.adapter.protocol.dlms.application.config.ApplicationContext;
import org.osgp.adapter.protocol.dlms.infra.messaging.DlmsDeviceMessageMetadata;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 *
 * @author dev
 */
@RunWith(SpringJUnit4ClassRunner.class)
@TestPropertySource("classpath:/test.properties")
@Category(Integration.class)
@ContextConfiguration(classes = { ApplicationContext.class })
public class ScalerUnitTest {

    @Autowired
    private InstallationService installationService;

    @Test
    public void testGetScalerUnit() throws Exception {
        DlmsDeviceMessageMetadata dlmsDeviceMessageMetadata = new DlmsDeviceMessageMetadata();
        dlmsDeviceMessageMetadata.setDeviceIdentification("E0004001515495114");

        ScalerUnit scalerUnitForEmeter = installationService.getScalerUnitForEmeter(dlmsDeviceMessageMetadata);

        Assert.assertEquals(scalerUnitForEmeter.getDlmsUnit(), DlmsUnit.wh);

    }

}
