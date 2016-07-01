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

import com.hotwire.test.common.AppiumDriverAwareModel;
import cucumber.api.PendingException;
import org.openqa.selenium.Alert;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Application model implementation template.
 */
public class AbstractApplication extends AppiumDriverAwareModel implements Application {

    @Override
    public void tearDown() {
    }

    @Override
    public void setGeoLocation() {
        throw new PendingException();
    }

    @Override
    public void verifyHomeScreen() {
        throw new PendingException();
    }

    protected void acceptAlert() {
        WebDriverWait wait = new WebDriverWait(getAppiumDriver(), 2);
        wait.until(ExpectedConditions.alertIsPresent());
        Alert alert = getAppiumDriver().switchTo().alert();
        alert.accept();
    }
    
    @Override
    public void goToHotels() {
    	throw new PendingException();
    }
    
    @Override
    public void goToCars() {
    	throw new PendingException();
    }
    
    @Override
    public void verifyAnalytics(String analyticsParams){
    	throw new PendingException();
    }

}
