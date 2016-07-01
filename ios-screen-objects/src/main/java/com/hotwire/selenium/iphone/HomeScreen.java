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

package com.hotwire.selenium.iphone;

import io.appium.java_client.AppiumDriver;

import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * Home Screen Object
 */

public class HomeScreen extends AppsAbstractPage {

    @FindBy(name = "Hotels")
    private WebElement hotelImg;

    @FindBy(name = "Cars")
    private WebElement carImg;

    @FindBy(name = "OK")
    private WebElement okBtn;

    public HomeScreen(AppiumDriver appiumDriver) {
        super(appiumDriver);
    }
    
    public void handlePushNotificationAlert() {
        try {
            String alertText = getAppiumDriver().switchTo().alert().getText();
            logger.info(alertText);
            if (alertText.contains("Would Like to Send You Notifications Notifications may include alerts, sounds, and icon badges. These can be configured in Settings.")) {
                logger.info("Push Notification alert raised : " + getAppiumDriver().switchTo().alert().getText());
                okBtn.click();
            }
        }
        catch (NoAlertPresentException ex) {
            logger.info("Push Notification alert did not pop up");
        }
    }
    
    public void navigateToHotels() {
        hotelImg.click();
    }

    public void navigateToCars() {
        carImg.click();
    }

}
