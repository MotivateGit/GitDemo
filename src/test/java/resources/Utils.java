package resources;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Properties;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class Utils {
	public static RequestSpecification req;
	public RequestSpecification requestSpec() throws IOException {
		
		if(req == null) {
		PrintStream log = new PrintStream(new FileOutputStream("logging.txt"));		
		RequestSpecBuilder rsb = new RequestSpecBuilder();
		req = rsb.setBaseUri(getGlobalValue("baseUrl")).addQueryParam("key", "qaclick123").
				addFilter(RequestLoggingFilter.logRequestTo(log)).addFilter(ResponseLoggingFilter.logResponseTo(log)).
				setContentType(ContentType.JSON).build();
		return req;
		}
		return req;
	}
	
	public static String getGlobalValue(String key) throws IOException {
		Properties prop = new Properties();
		FileInputStream fis = new FileInputStream("C:\\Users\\Bharathiraja\\eclipse-workspace\\APIFramework\\src\\test\\java\\resources\\global.properties");
		prop.load(fis);	
		return prop.getProperty(key);
	}
	
	public static String getJsonPath(Response response,String key) throws IOException {
		String resp = response.asString();
		JsonPath jp = new JsonPath(resp);
		return jp.get(key).toString();
	
	}


}
