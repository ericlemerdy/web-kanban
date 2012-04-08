package websocket;

import model.*;
import org.eclipse.jetty.websocket.*;
import repository.*;

import java.io.*;

import static java.lang.String.*;

public class WebKanbanWebSocket implements WebSocket.OnTextMessage {

	private Connection connection;

	@Override
	public void onMessage(String data) {
		System.out.println(connection + " unexpected message:" + data);
	}

	@Override
	public void onOpen(Connection connection) {
		Clients.getInstance().add(this);
		this.connection = connection;
		System.out.println(connection + " connected");
	}

	@Override
	public void onClose(int closeCode, String message) {
		Clients.getInstance().remove(this);
		System.out.println(connection + " closed");
	}

	public void storyAdded(Story story) {
		String jsonString = "{'added':{'id':%d, 'label':'%s', 'state':'%s'}}".replaceAll("'", "\"");
		try {
			System.out.println(connection + " new story sent");
			connection.sendMessage(format(jsonString, story.id, story.label, story.state));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void storyUpdated(Story story) {
		String jsonString = "{'updated':{'id':%d, 'label':'%s', 'state':'%s'}}".replaceAll("'", "\"");
		try {
			System.out.println(connection + " story updated sent");
			connection.sendMessage(format(jsonString, story.id, story.label, story.state));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
