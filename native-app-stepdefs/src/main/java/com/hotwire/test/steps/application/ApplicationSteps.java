/*
 * Copyright 2016 Expedia Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.hotwire.test.steps.application;

import com.hotwire.test.steps.AbstractSteps;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Application steps class.
 */
public class ApplicationSteps extends AbstractSteps {

    @Autowired
    private Application application;

    @Then("^I see home screen$")
    public void verifyHomeScreen() {
        application.verifyHomeScreen();
    }

    @Given("^I would like to book a (Hotel|Car)$")
    public void enterHotelsOrCars(String hotelsOrCars) {
        if (hotelsOrCars.equalsIgnoreCase("Hotel")) {
            application.goToHotels();
        }
        else if (hotelsOrCars.equalsIgnoreCase("Car")) {
            application.goToCars();
        }
    }
    
    @Then("^I verify analytics for (.*)$")
    public void verifyAnalytics(String analyticsParams) {
    	application.verifyAnalytics(analyticsParams);
    }
}
