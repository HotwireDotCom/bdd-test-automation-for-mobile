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

package com.hotwire.test.common;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.ios.IOSDriver;

import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.URL;

/**
 * AppiumDriver factory class.
 */
public class AppiumDriverFactory {

    private URL remoteAddress;
    private DesiredCapabilities desiredCapabilities;

    public void setRemoteAddress(URL remoteAddress) {
        this.remoteAddress = remoteAddress;
    }

    public void setDesiredCapabilities(DesiredCapabilities desiredCapabilities) {
        this.desiredCapabilities = desiredCapabilities;
    }

    public AppiumDriver getAppiumDriver() {
        return new IOSDriver(remoteAddress, desiredCapabilities);
    }
}
