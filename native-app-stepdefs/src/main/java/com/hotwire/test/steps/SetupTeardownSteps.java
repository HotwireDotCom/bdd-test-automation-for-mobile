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

package com.hotwire.test.steps;

import com.hotwire.test.steps.application.IOSEnvironmentSettings;
import com.hotwire.test.steps.application.Application;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.en.Given;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.remote.Augmenter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

/**
 * Startup and shutdown hooks.
 */
public class SetupTeardownSteps extends AbstractSteps {

    @Autowired
    private Application application;

    @Autowired
    @Qualifier("environmentSettings")
    private IOSEnvironmentSettings settings;

    @Given("^the application is running$")
    public void setUp() {
        settings.setEnvironment();
    }

    @After
    public void tearDown(Scenario scenario) {
        if (scenario.isFailed()) {
            try {
                logger.info("Test failed, taking screenshot");
                byte[] screenshot = ((TakesScreenshot) new Augmenter().augment(getAppiumDriver()))
                        .getScreenshotAs(OutputType.BYTES);
                scenario.embed(screenshot, "image/png");
            }
            catch (WebDriverException wde) {
                System.err.println(wde.getMessage());
            }
            catch (ClassCastException cce) {
                cce.printStackTrace();
            }
        }
        application.tearDown();
    }
}
