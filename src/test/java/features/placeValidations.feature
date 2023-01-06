# Author: Bharathiraja as SDET Engineer


Feature: Validating Place API's

@AddPlace @Regression
Scenario Outline: Verify if place is being successfully added using AddPlaceAPI

Given Add place payload with "<name>" "<language>" "<address>"
When user calls "AddPlaceAPI" with "POST" http request
Then the API call got success with status code 200
And "status" in response body is "OK"
And "scope" in response body is "APP"
Then verify place_id is created and it mapped "<name>" using "getPlaceAPI"

Examples:
		| name 		| language 	| address 					|
		|	AAhouse	|	English		|	World Cross Center|
		#|	BBhouse	|	Tamil			|	Sea Cross Center	|
		#|	CChouse	|	Spanish		|	Ocean Cross Center|


@DeletePlace @Regression	
Scenario: Verify if Delete Place API is working

Given DeletePlace Payload
When user calls "deletePlaceAPI" with "POST" http request
Then the API call got success with status code 200
And "status" in response body is "OK"		
