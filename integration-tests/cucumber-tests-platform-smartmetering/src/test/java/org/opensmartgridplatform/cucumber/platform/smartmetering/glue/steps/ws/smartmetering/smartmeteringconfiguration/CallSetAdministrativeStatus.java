package org.opensmartgridplatform.cucumber.platform.smartmetering.glue.steps.ws.smartmetering.smartmeteringconfiguration;

import java.util.HashMap;

import org.opensmartgridplatform.adapter.ws.schema.smartmetering.configuration.SetAdministrativeStatusAsyncResponse;
import org.opensmartgridplatform.adapter.ws.schema.smartmetering.configuration.SetAdministrativeStatusRequest;
import org.opensmartgridplatform.cucumber.core.ScenarioContext;
import org.opensmartgridplatform.cucumber.platform.smartmetering.PlatformSmartmeteringKeys;
import org.opensmartgridplatform.cucumber.platform.smartmetering.support.ws.smartmetering.configuration.SetAdministrativeStatusRequestFactory;
import org.opensmartgridplatform.cucumber.platform.smartmetering.support.ws.smartmetering.configuration.SmartMeteringConfigurationClient;
import org.opensmartgridplatform.shared.exceptionhandling.WebServiceSecurityException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class CallSetAdministrativeStatus {
    protected static final Logger LOGGER = LoggerFactory.getLogger(CallSetAdministrativeStatus.class);

    @Autowired
    private SmartMeteringConfigurationClient smartMeteringConfigurationClient;

    public boolean createRequest() throws WebServiceSecurityException {
        HashMap<String, String> requestData = new HashMap<String, String>();
        requestData.put("DeviceIdentification", "E0026000026406115");
        requestData.put("AdministrativeStatusType", "ON");
        requestData.put("Message", "test P1 message from CallSetAdministrativeStatus");

        final SetAdministrativeStatusRequest setAdministrativeStatusRequest = SetAdministrativeStatusRequestFactory
                .fromParameterMap(requestData);

        final SetAdministrativeStatusAsyncResponse setAdministrativeStatusAsyncResponse = this.smartMeteringConfigurationClient
                .setAdministrativeStatus(setAdministrativeStatusRequest);

        LOGGER.info("Set P1 message response is received {}", setAdministrativeStatusAsyncResponse);

        ScenarioContext.current().put(PlatformSmartmeteringKeys.KEY_CORRELATION_UID,
                setAdministrativeStatusAsyncResponse.getCorrelationUid());
        ScenarioContext.current().put(PlatformSmartmeteringKeys.DEVICE_IDENTIFICATION,
                setAdministrativeStatusRequest.getDeviceIdentification());
        return setAdministrativeStatusAsyncResponse != null;
    }
}
