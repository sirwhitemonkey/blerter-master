@TestFeature
Feature: Health post/put/get/delete information

	Background:
		Given Validate service
		
	Scenario: Post health
		When Validate service
		And Clear
		Then Post health
	
	Scenario: Get health
		When Validate service
		And Set health: 99
		Then Unknown health
		And Set health: 0
		Then Valid health
	
	Scenario: Update health
		When Validate service
		And Set health: 0
		And Modify health name: test
		Then Validate health name: test	
	
	Scenario: Delete health
		When Validate service
		And Set health: 0
		Then Delete health