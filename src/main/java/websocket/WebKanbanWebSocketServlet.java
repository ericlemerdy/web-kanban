package websocket;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.websocket.WebSocket;
import org.eclipse.jetty.websocket.WebSocketFactory;

import repository.Clients;

public class WebKanbanWebSocketServlet extends HttpServlet {

	private static final long serialVersionUID = -1718482262350524618L;

	private WebSocketFactory webSocketFactory;

	@Override
	public void init() throws ServletException {
		webSocketFactory = new WebSocketFactory(new WebSocketFactory.Acceptor() {
			public WebSocket doWebSocketConnect(HttpServletRequest request, String protocol) {
				return new WebKanbanWebSocket(Clients.getInstance());
			}

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
