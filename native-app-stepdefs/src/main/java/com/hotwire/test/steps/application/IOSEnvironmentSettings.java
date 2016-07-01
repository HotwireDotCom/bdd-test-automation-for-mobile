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

import com.hotwire.selenium.iphone.HomeScreen;

import io.appium.java_client.AppiumDriver;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.html5.Location;

/**
 * Settings for IOS.
 */
public class IOSEnvironmentSettings {

    private AppiumDriver appiumDriver;

    public void setAppiumDriver(AppiumDriver appiumDriver) {
        this.appiumDriver = appiumDriver;
    }

    public void setEnvironment() {
        appiumDriver.manage().timeouts().implicitlyWait(40, TimeUnit.SECONDS);
        Location loc = new Location(37.795255, -122.403561, 39);
        appiumDriver.setLocation(loc);
        HomeScreen homeScreenObj = new HomeScreen(appiumDriver);
        homeScreenObj.handlePushNotificationAlert();
    }
}
