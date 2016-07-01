# BDD test automation for Mobile
Having a good automation framework is fundamental for a quality app. There are several tools available for mobile test automation but not any framework which ties them all together. Other challenge for any test automation is maintainability as test coverage increases for UI testing. This framework primarily solves these two problems for Mobile apps with some additional features like reporting & ability to test analytic requirements. 

This framework can be used to write tests which can be executed on different platforms like iOS, Android, Windows. BDD based approach allows us to test the application from the user perspective and writing tests in plain English language makes it easy to understand, maintain & serves as a documentation. It easily integrates with CI servers like Jenkins. Additionally, this framework allows the user to write tests around logging based analytics testing using Appium log scraping and Xpath matching. This testing uniquely gives the user the flexibility to feature test any analytics parameters logged for tracking, etc. 

Here are examples of how to start writing your Feature files

### Features
```
Feature: Name your feature file here    

    Background:
    Given The application is running

    @SMOKE
    Scenario: Test scenario description
    Given I would like to do something
```

This is a valid test case that should be saved as a .feature file.

### Step definitions
The next step is to write step definitions for each of these steps. Here's two of them:

```java
@Given("^The application is running$")
public void verifyAppLauch {
// Your setup code here
}

@Given("^I would like to do (situation1|situation2)$")
public void enterSituationCase(String matches) {
    if (matches.equalsIgnoreCase("situation1")) {
        // Your code to implement for case 'situation1'
    }
    else if (matches.equalsIgnoreCase("situation2")) {
        // Your code to implement for case 'situation2'
    }
}

```

These steps match regular expressions and return the step implementation for the captured string. The second step will capture the two situations for which you might have varied implementation calls.

### Screen Objects
We chose to go the screen object route for keeping our screen elements modular and flexible for upcoming changes. You would see WebElements defined like this for each screen:

```java
@FindBy(name = "NavBar")
private WebElement navigationTab;

@FindBy(name = "OK")
private WebElement okBtn;

```
### Analytics Testing

This framework includes provisions to include Analytics tracking parameter testing by maintaing a central XML where all the analytics tracking modules are added.  If your app calls to analytics tool(eg. Omniture, etc) are logged in Appium logs then  you can easily include the verification step in your feature file like this: 

```java
Scenario: Validate analytics parameters for a screen
Given I verify analytics for verticalName:screenName:elementName 

@Then("^I verify analytics for (.*)$")
public void verifyAnalytics(String analyticsParams) {
application.verifyAnalytics(analyticsParams);

```

## Usage

You can install this BDD framework in your current project by adding the following lines of xml to your pom.xml file:

```
<dependency>
    <groupId>com.hotwire.testing</groupId>
    <artifactId>ios-native-app</artifactId>
    <version>1.0.6</version>
</dependency>

```
Since we have created a profile for cucumber (which gives us flexibility to add more profiles if we want in future), maven should run with 'cucumber' profile to leverage this.

## Author

Sridhar Ramakrishnan, Neha Srivastava, Hima Gogineni

## License

This project is available under the Apache 2.0 License.
Copyright 2016 Expedia Inc.
