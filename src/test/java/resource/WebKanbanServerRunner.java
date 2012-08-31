package resource;

import java.io.IOException;

import org.junit.After;
import org.junit.Before;

import com.sun.net.httpserver.HttpServer;

public class WebKanbanServerRunner {

	private HttpServer server;

	public WebKanbanServerRunner() {
		super();
	}

	@Before
	public void startWebServer() throws IOException {
		server = WebKanbanServer.start();
	}

	@After
	public void stopWebServer() {
		server.stop(0);
	}

}