@iPhone @REGRESSION 

Feature: Hotel Apps

        Background:
        Given the application is running
        
		@SMOKE
        Scenario: Search for Hotel
        Given I would like to book a Hotel
        
        @ANALYTICS
        Scenario: Validate analytics parameters for hotel search
        Given I would like to book a Hotel
        Then I verify analytics for verticalName:screenName:elementName 
        