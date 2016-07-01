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

import com.google.common.base.Function;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.support.PageFactory;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.openqa.selenium.support.ui.ExpectedConditions.presenceOfElementLocated;

/**
 * Simple abstract page for AppiumDriver with PageFactory support.
 */
public class AppsAbstractPage {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());

    private final AppiumDriver appiumDriver;

    public AppsAbstractPage(AppiumDriver appiumDriver) {
        this.appiumDriver = appiumDriver;
        PageFactory.initElements(appiumDriver, this);
    }

    protected final AppiumDriver getAppiumDriver() {
        return appiumDriver;
    }

    protected void waitFor(Function<? super WebDriver, ?> expectedCondition, int timeOutInSeconds, int sleepInMillis) {
        new WebDriverWait(getAppiumDriver(), timeOutInSeconds, sleepInMillis).until(expectedCondition);
    }

    protected void waitForPresenceOfElementLocated(int timeOutInSeconds, String id) {
        waitFor(presenceOfElementLocated(By.id(id)), timeOutInSeconds);
    }

    public void waitFor(Function<? super WebDriver, ?> expectedCondition, int timeOutInSeconds) {
        new WebDriverWait(getAppiumDriver(), timeOutInSeconds).until(expectedCondition);
    }

    public boolean isTextOnPage(String text) {
        try {
            waitFor(presenceOfElementLocated(By.name(text)), 2, 500 );
            return true;
        }
        catch (WebDriverException e) {
            return false;
        }

    }
}
