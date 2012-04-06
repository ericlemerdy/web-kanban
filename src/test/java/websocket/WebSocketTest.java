package websocket;

import com.google.common.io.*;
import org.eclipse.jetty.server.*;
import org.eclipse.jetty.servlet.*;
import org.eclipse.jetty.websocket.*;
import org.junit.*;

import java.io.*;
import java.net.*;

import static org.fest.assertions.Assertions.*;

public class WebSocketTest implements WebSocket.OnTextMessage {

	private Server server;

	@Before
	public void startWebServer() throws Exception {
		server = new Server(8080);
		ServletHandler servletHandler = new ServletHandler();
		servletHandler.addServletWithMapping(WebKanbanWebsocketServer.class, "/ws/*");
		server.setHandler(servletHandler);
		server.start();
	}

	@After
	public void stopWebServer() throws Exception {
		server.stop();
	}

	@Test
	public void should_refuse_http_GET_gracefully() throws Exception {
		URL url = new URL("http://localhost:8080/ws");
		HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
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
			webSocketClient.open(URI.create("ws://localhost:8080/ws"), this).get();
		} finally {
			webSocketClientFactory.stop();
		}
	}

	@Override
	public void onMessage(String data) {
		assertThat(data).isNotNull().isEqualTo("toto");
	}

	@Override
	public void onOpen(Connection connection) {
		assertThat(connection.isOpen()).isTrue();
		assertThat(connection.getProtocol()).isEqualTo("kanban");
		try {
			connection.sendMessage("{'label': 'nouvelle story !', 'state': 'DONE'}".replaceAll("'", "\""));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onClose(int closeCode, String message) {
		assertThat(closeCode).isNotNull().isEqualTo(1006);
		assertThat(message).isEqualTo("Connection refused");
	}
}
