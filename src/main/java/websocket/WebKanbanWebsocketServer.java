package websocket;

import org.eclipse.jetty.websocket.*;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;

public class WebKanbanWebsocketServer extends HttpServlet {

	private WebSocketFactory webSocketFactory;

	@Override
	public void init() throws ServletException {
		webSocketFactory = new WebSocketFactory(new WebSocketFactory.Acceptor() {
			@Override
			public WebSocket doWebSocketConnect(HttpServletRequest request, String protocol) {
				return new WebSocket.OnTextMessage() {

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
				};
			}

			@Override
			public boolean checkOrigin(HttpServletRequest request, String origin) {
				return true;
			}

		});
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		if (webSocketFactory.acceptWebSocket(req, resp)) {
			return;
		}
		resp.sendError(HttpServletResponse.SC_SERVICE_UNAVAILABLE, "This url should be accessed with websocket.");
	}

}
