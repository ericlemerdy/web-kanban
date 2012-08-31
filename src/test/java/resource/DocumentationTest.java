package resource;

import static com.jayway.restassured.RestAssured.expect;
import static org.hamcrest.Matchers.equalTo;

import org.junit.Test;


public class DocumentationTest extends WebKanbanServerRunner {
	@Test
	public void should_retrieve_documentation_metadata() {
		expect().body("apiVersion", equalTo("1.0")).and() //
				.body("swaggerVersion", equalTo("1.0")).and() //
				.body("basePath", equalTo("localhOOt")).and() //
				.when().get("http://127.0.0.1:4242/resources.json");
	}

}
