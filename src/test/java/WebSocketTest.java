import org.eclipse.jetty.websocket.*;
import org.junit.*;

import java.net.*;

import static org.fest.assertions.Assertions.*;

public class WebSocketTest {

	@Test
	public void with_a_new_story_should_receive_a_message() throws Exception {

		WebSocketClientFactory webSocketClientFactory = new WebSocketClientFactory();
		webSocketClientFactory.start();
		try {
			WebSocketClient webSocketClient = webSocketClientFactory.newWebSocketClient();
			webSocketClient.open(URI.create("ws://localhost:8080/ws/"), new WebSocket.OnTextMessage() {
				@Override
				public void onMessage(String data) {
					assertThat(data).isNotNull().isEqualTo("");
				}

				@Override
				public void onOpen(Connection connection) {
					assertThat(connection).isNotNull();
				}

				@Override
				public void onClose(int closeCode, String message) {
					assertThat(closeCode).isEqualTo(1);
					assertThat(message).isEqualTo("");
				}
			});
		} finally {
			webSocketClientFactory.stop();
		}
	}
}
