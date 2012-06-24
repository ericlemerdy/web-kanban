package resource;

import static com.jayway.restassured.RestAssured.expect;
import static org.hamcrest.Matchers.endsWith;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.not;

import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.sun.net.httpserver.HttpServer;

public class WebKanbanServerTest {
	private HttpServer server;

	@Before
	public void startWebServer() throws IOException {
		server = WebKanbanServer.start();
	}

	@After
	public void stopWebServer() {
		server.stop(0);
	}

	@Test
	public void should_retrieve_stories_as_JSON() {
		expect().body("stories.id", hasItems(1, 2, 3)).and() //
				.body("stories.state", hasItems("TODO", "WIP", "DONE")).and() //
				.body("stories.label", hasItems("sleep at night", "rest in front of the tv", "eat. a lot.")) //
				.header("Content-Type", endsWith("; charset=UTF-8")) //
				.when().get("http://127.0.0.1:4242/stories.json");
	}

	@Test
	public void should_change_story_state() {
		expect().statusCode(200).when().post("http://127.0.0.1:4242/story/1/WIP").thenReturn();
		expect().body("stories.state", hasItems("WIP", "WIP", "DONE")).and() //
				.when().get("http://127.0.0.1:4242/stories.json");
	}

	@Test
	public void should_add_story() {
		expect().body("id", equalTo(4)).and() //
				.body("state", equalTo("TODO")).and() //
				.body("label", equalTo("junit")) //
				.header("Content-Type", endsWith("; charset=UTF-8")) //
				.when().put("http://127.0.0.1:4242/story/junit").thenReturn();
		expect().body("stories.label", hasItem("junit")) //
				.when().get("http://127.0.0.1:4242/stories.json");
	}

	@Test
	public void should_delete_story() {
		expect().statusCode(200).when().delete("http://127.0.0.1:4242/story/2").thenReturn();
		expect().body("stories.id", not(hasItem(2))).and() //
				.when().get("http://127.0.0.1:4242/stories.json");
	}

	@Test
	public void should_not_add_existing_story() {
		expect().statusCode(400).content(equalTo("The story 'sleep at night' already exists.")) //
				.header("Content-Type", endsWith("; charset=UTF-8")) //
				.when().put("http://127.0.0.1:4242/story/sleep at night").thenReturn();
	}

	@Test
	public void with_an_empty_label_should_not_add_story() {
		expect().statusCode(400).content(equalTo("Please provide a story label to add.")) //
				.header("Content-Type", endsWith("; charset=UTF-8")) //
				.when().put("http://127.0.0.1:4242/story/").thenReturn();
	}
}
