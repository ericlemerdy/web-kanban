package resource;

import com.sun.net.httpserver.*;
import org.junit.*;

import java.io.*;

import static com.jayway.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class WebKanbanServerTest {
	private HttpServer server;

	@Before
	public void startWebServer() throws IOException {
		server = WebKanbanServer.start();
	}

	@After
	public void stopWebServer() {
		server.stop(1);
	}

	@Test
	public void should_retrieve_stories_as_JSON_and_change_state_and_add_story_and_delete_story() {
		expect().body("stories.id", hasItems(1, 2, 3)).and() //
				.body("stories.state", hasItems("TODO", "WIP", "DONE")).and() //
				.body("stories.label", hasItems("sleep at night", "rest in front of the tv", "eat. a lot.")) //
				.header("Content-Type", endsWith("; charset=UTF-8")) //
				.when().get("/stories.json");

		expect().statusCode(200).when().post("/story/1/WIP").thenReturn();
		expect().body("stories.state", hasItems("WIP", "WIP", "DONE")).and() //
				.when().get("/stories.json");

		expect().body("id", equalTo(4)).and() //
				.body("state", equalTo("TODO")).and() //
				.body("label", equalTo("junit")) //
				.header("Content-Type", endsWith("; charset=UTF-8")) //
				.when().put("/story/junit").thenReturn();
		expect().body("stories.label", hasItem("junit")) //
				.when().get("/stories.json");

		expect().statusCode(200).when().delete("/story/4").thenReturn();
		expect().body("stories.id", not(hasItem(4))).and() //
				.when().get("/stories.json");
	}

	@Test
	public void should_not_add_existing_story() {
		expect().statusCode(400).content(equalTo("The story 'sleep at night' already exists.")) //
				.header("Content-Type", endsWith("; charset=UTF-8")) //
				.when().put("/story/sleep at night").thenReturn();
	}

	@Test
	public void with_an_empty_label_should_not_add_story() {
		expect().statusCode(400).content(equalTo("Please provide a story label to add.")) //
				.header("Content-Type", endsWith("; charset=UTF-8")) //
				.when().put("/story/").thenReturn();
	}
}
