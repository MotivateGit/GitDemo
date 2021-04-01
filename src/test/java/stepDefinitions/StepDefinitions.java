package stepDefinitions;

import static org.junit.Assert.*;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import pojo.AddPlace;
import pojo.Location;

import static io.restassured.RestAssured.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import resources.APIResources;
import resources.TestDataBuild;
import resources.Utils;

public class StepDefinitions extends Utils{
	RequestSpecification req;
	ResponseSpecification resSpec;
	 Response response;
	 static String place_id;
	
	TestDataBuild data = new TestDataBuild();
	
	/**
	 * @throws IOException 
	 * 
	 */
	@Given("Add place payload with {string} {string} {string}")
	public void add_place_payload_with(String name, String language, String address) throws IOException {
		req=given().spec(requestSpec())
				.body(data.addPlacePayload(name,language,address));

   
	}
	@When("user calls {string} with {string} http request")
	public void user_calls_with_http_request(String resource, String httpMethod) {
	    // Write code here that turns the phrase above into concrete actions
		APIResources resourceAPI = APIResources.valueOf(resource);
		System.out.println(resourceAPI.getResource());
		
		resSpec = new ResponseSpecBuilder().expectStatusCode(200).expectContentType(ContentType.JSON).build();
		
		if(httpMethod.equalsIgnoreCase("POST")) {			
		    response = req.when().post(resourceAPI.getResource());
		}else if(httpMethod.equalsIgnoreCase("GET")){
		    response = req.when().get(resourceAPI.getResource());
		}
					
	}
	@Then("the API call got success with status code {int}")
	public void the_api_call_got_success_with_status_code(Integer int1) {
	    // Write code here that turns the phrase above into concrete actions
	   assertEquals(200, response.getStatusCode());
	}
	@Then("{string} in response body is {string}")
	public void in_reponse_body_is(String keyValue, String expValue) throws IOException {
	    // Write code here that turns the phrase above into concrete actions
	   assertEquals(expValue, getJsonPath(response, keyValue));
		
	}

	@Then("verify place_id is created and it mapped {string} using {string}")
	public void verify_place_id_is_created_and_it_mapped_using(String expName, String resource) throws IOException {
	    // Write code here that turns the phrase above into concrete actions
	   place_id = getJsonPath(response, "place_id");
	   req = given().spec(requestSpec()).queryParam("place_id", place_id);
	   user_calls_with_http_request(resource, "GET");
	   String actName = getJsonPath(response, "name");
	   assertEquals(expName,actName);
	}
	
	@Given("DeletePlace Payload")
	public void delete_place_payload() throws IOException {
	    
		req = given().spec(requestSpec()).queryParam("key", "qaclick123").body(data.deletePlacePayload(place_id));
	}


}
