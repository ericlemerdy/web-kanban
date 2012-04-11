package websocket;

import model.*;
import org.eclipse.jetty.websocket.*;
import repository.*;

import java.io.*;

import static java.lang.String.*;

public class WebKanbanWebSocket implements WebSocket.OnTextMessage {

	private Connection connection;
	private Clients clients;

	public WebKanbanWebSocket(Clients clients) {
		this.clients = clients;
	}

	@Override
	public void onMessage(String data) {
		// Don't care about client messages.
	}

	@Override
	public void onOpen(Connection connection) {
		this.connection = connection;
		clients.registerOpenedWebSocket(this);
	}

	@Override
	public void onClose(int closeCode, String message) {
		connection = null;
		clients.unregisterClosedWebSocket(this);
	}

	public void storyAdded(Story story) {
		String jsonString = "{'added':{'id':%d, 'label':'%s', 'state':'%s'}}".replace('\'', '"');
		try {
			connection.sendMessage(format(jsonString, story.id, story.label, story.state));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void storyUpdated(Story story) {
		String jsonString = "{'updated':{'id':%d, 'label':'%s', 'state':'%s'}}".replace('\'', '"');
		try {
			connection.sendMessage(format(jsonString, story.id, story.label, story.state));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void storyDeleted(int storyId) {
		String jsonString = "{'deleted':{'id':%d}}".replace('\'', '"');
		try {
			connection.sendMessage(format(jsonString, storyId));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
