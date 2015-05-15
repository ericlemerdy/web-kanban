package websocket;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletHandler;
import org.eclipse.jetty.websocket.WebSocket;
import org.eclipse.jetty.websocket.WebSocketClient;
import org.eclipse.jetty.websocket.WebSocketClientFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.google.common.io.CharStreams;

public class WebSocketTest implements WebSocket.OnTextMessage {

	private Server server;

	private int port;

	@Before
	public void startWebServer() throws Exception {
		port = 4242;
		server = new Server(port);
		ServletHandler servletHandler = new ServletHandler();
		servletHandler.addServletWithMapping(WebKanbanWebSocketServlet.class, "/ws/*");
		server.setHandler(servletHandler);
		server.start();
	}

	@After
	public void stopWebServer() throws Exception {
		server.stop();
	}

	@Test
	public void should_refuse_http_GET_gracefully() throws Exception {
		HttpURLConnection urlConnection = (HttpURLConnection) new URL("http://localhost:" + port + "/ws")
				.openConnection();
		assertThat(urlConnection.getResponseCode()).isEqualTo(503);
		String errorContent = CharStreams.toString(new InputStreamReader(urlConnection.getErrorStream()));

		assertThat(errorContent).contains("This url should be accessed with websocket.");
	}

	@Test
	public void with_a_new_story_should_receive_a_message() throws Exception {
		WebSocketClientFactory webSocketClientFactory = new WebSocketClientFactory();
		webSocketClientFactory.start();
		try {
			WebSocketClient webSocketClient = webSocketClientFactory.newWebSocketClient();
			webSocketClient.setProtocol("kanban");
			webSocketClient.open(URI.create("ws://localhost:" + port + "/ws"), this).get();
		} finally {
			webSocketClientFactory.stop();
		}
	}

	public void onMessage(String data) {
		assertThat(data).isNotNull().isEqualTo("toto");
	}

	public void onOpen(Connection connection) {
		assertThat(connection.isOpen()).isTrue();
		assertThat(connection.getProtocol()).isEqualTo("kanban");
	}

	public void onClose(int closeCode, String message) {
		assertThat(closeCode).isNotNull().isEqualTo(1006);
		assertThat(message).isEqualTo("Connection refused");
	}
}
