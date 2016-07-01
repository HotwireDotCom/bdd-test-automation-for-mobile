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

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import com.hotwire.selenium.iphone.HomeScreen;
import cucumber.runtime.CucumberException;
import org.apache.commons.io.input.ReversedLinesFileReader;
import java.io.File;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.xpath.XPathFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Document;

import static org.fest.assertions.Assertions.assertThat;

/**
 * Application model iOS implementation.
 */
public class IosApplication extends AbstractApplication {

    @Override
    public void tearDown() {
        try {
            getAppiumDriver().runAppInBackground(2);
        }
        catch (Exception e) {
            throw new CucumberException("Failed to collect code coverage data!", e);
        }
    }

    @Override
    public void goToHotels() {
        HomeScreen homeScreenObj = new HomeScreen(getAppiumDriver());
        homeScreenObj.navigateToHotels();
    }

    @Override
    public void goToCars() {
        HomeScreen homeScreenObj = new HomeScreen(getAppiumDriver());
        homeScreenObj.navigateToCars();
    }    
    
    @Override
    public void verifyAnalytics(String analyticsParams){
    	String fileName = (System.getProperty("user.dir")).substring(0, System.getProperty("user.dir").lastIndexOf("/")) + "/appium.log";
    	logger.info("Analytics parameters from feature file : " + analyticsParams);
    	StringBuilder badParams = new StringBuilder("");
        

        try (ReversedLinesFileReader rlr = new ReversedLinesFileReader(new File(fileName) ))
        {
    	   	String analytics = getExpectedAnalytics(analyticsParams);
        	logger.info("Expected analytics values from xml file: " + analytics);
            String [] analyticsValuePairs = analytics.split(";");
            String sCurrentLine;
            boolean isAnalyticsPresent = false;
            while((sCurrentLine = rlr.readLine()) != null && !isAnalyticsPresent)
            {   
            	if (sCurrentLine.contains("<analytics_log>")) {
            		logger.info("Analytics log from appium log file : " + sCurrentLine.substring(sCurrentLine.indexOf("<analytics_log>"), sCurrentLine.indexOf("</analytics_log>")));
            		isAnalyticsPresent = sCurrentLine.contains(analytics);
                    for(String s : analyticsValuePairs) {
                    	isAnalyticsPresent = sCurrentLine.contains(s);
                        if (!isAnalyticsPresent) {
                        	badParams.append(s);
                            break;
                        }
                        if(!(badParams.toString().isEmpty()))
                        	break;
                    }
                    if(!(badParams.toString().isEmpty()))
                    	break;
                }  
            	if(!(badParams.toString().isEmpty()))
                	break;
            }
            assertThat(isAnalyticsPresent).as("Analytics "+ analyticsParams +"  with paramaters  "+ badParams +" isn't present or incorrect. Please check this situation").isTrue();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public String getExpectedAnalytics(String analyticsParams) throws Exception{
    	DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true); 
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse("vertical");
        XPathFactory xpathfactory = XPathFactory.newInstance();
        XPath xpath = xpathfactory.newXPath();
        
        String paramsToPath = "//"+(analyticsParams.replaceAll(":", "/"))+"/node()";
        XPathExpression expr = xpath.compile(paramsToPath);
        Object result = expr.evaluate(doc, XPathConstants.NODESET);
        NodeList nodes = (NodeList) result;
       
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < nodes.getLength(); i++) {
        	sb.append((nodes.item(i).getNodeName()+"="+nodes.item(i).getTextContent()).trim());           
        }  
        String expectedParams = sb.toString().replaceAll("#text=", ";");
    	
        return expectedParams;
    }
    
}
