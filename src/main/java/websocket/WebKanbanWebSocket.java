package websocket;

import org.eclipse.jetty.websocket.*;

public class WebKanbanWebSocket implements WebSocket.OnTextMessage {

	@Override
	public void onMessage(String data) {
		System.out.println("message received:" + data);
	}

	@Override
	public void onOpen(Connection connection) {
		System.out.println("client connected");
	}

	@Override
	public void onClose(int closeCode, String message) {
		System.out.println("client closed");
	}
}
