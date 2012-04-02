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
	public void should_retrieve_commits_as_JSON() {
		expect().body("stories", hasItems("TODO,1,sleep at night", "WIP,2,rest in front of the tv", "DONE,3,eat. a lot.")).when().get("/stories.json");
	}
}
