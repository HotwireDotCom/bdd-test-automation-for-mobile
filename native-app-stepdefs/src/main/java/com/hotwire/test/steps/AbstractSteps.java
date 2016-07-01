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

import io.appium.java_client.AppiumDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

/**
 * Abstract Steps class.
 * Provides Spring context configuration.
 */
@ContextConfiguration(
        locations = {
                "classpath*:cucumber/runtime/java/spring/cucumber-glue.xml",
                "classpath:cucumber.xml",
        }
)
public abstract class AbstractSteps {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());

    @Autowired
    private AppiumDriver appiumDriver;

    protected AppiumDriver getAppiumDriver() {
        return appiumDriver;
    }
}
