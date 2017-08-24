package com.alliander.osgp.cucumber.platform.smartmetering.glue.steps.ws.smartmetering.smartmeteringbundle;

import static org.junit.Assert.assertTrue;

import java.util.Map;

import com.alliander.osgp.adapter.ws.schema.smartmetering.bundle.BundleRequest;
import com.alliander.osgp.adapter.ws.schema.smartmetering.bundle.ReadAlarmRegisterRequest;
import com.alliander.osgp.adapter.ws.schema.smartmetering.bundle.ReadAlarmRegisterResponse;
import com.alliander.osgp.adapter.ws.schema.smartmetering.common.Response;
import com.alliander.osgp.cucumber.core.ScenarioContext;
import com.alliander.osgp.cucumber.platform.smartmetering.PlatformSmartmeteringKeys;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;

public class BundledReadAlarmRegisterSteps extends BaseBundleSteps {

    @Given("^the bundle request contains a read alarm register action$")
    public void theBundleRequestContainsAReadAlarmRegisterAction() throws Throwable {

        final BundleRequest request = (BundleRequest) ScenarioContext.current()
                .get(PlatformSmartmeteringKeys.BUNDLE_REQUEST);

        final ReadAlarmRegisterRequest action = new ReadAlarmRegisterRequest();

        this.addActionToBundleRequest(request, action);
    }

    @Then("^the bundle response should contain a read alarm register response$")
    public void theBundleResponseShouldContainAReadAlarmRegisterResponse() throws Throwable {

        final Response response = this.getNextBundleResponse();

        assertTrue("Not a valid response", response instanceof ReadAlarmRegisterResponse);
    }

    @Then("^the bundle response should contain a read alarm register response with values$")
    public void theBundleResponseShouldContainAReadAlarmRegisterResponse(final Map<String, String> values)
            throws Throwable {

        final Response response = this.getNextBundleResponse();

        assertTrue("Not a valid response", response instanceof ReadAlarmRegisterResponse);
    }

}
