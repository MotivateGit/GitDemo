package stepDefinitions;

import java.io.IOException;

import io.cucumber.java.Before;

public class Hooks {

	@Before("@DeletePlace")
	public void beforeScenario() throws IOException {
		//write to code that will give you place id
		//execute this code only when place id is null

		StepDefinitions m = new StepDefinitions();
		if(StepDefinitions.place_id == null) {
			m.add_place_payload_with("Yokshita", "Tamil", "25, Bajani Koil St");
			m.user_calls_with_http_request("AddPlaceAPI", "POST");
			m.verify_place_id_is_created_and_it_mapped_using("Yokshita", "getPlaceAPI");
		}

	}


}
