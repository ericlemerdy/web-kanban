package repository;

import com.google.common.collect.*;
import model.*;
import websocket.*;

import java.util.*;

import static com.google.common.collect.Sets.*;

public class Clients {

	private static final Clients instance = new Clients();

	private Set<WebKanbanWebSocket> clients;

	private Clients() {
		clients = newHashSet();
	}

	public static Clients getInstance() {
		return instance;
	}

	public void registerOpenedWebSocket(WebKanbanWebSocket webKanbanWebSocket) {
		clients.add(webKanbanWebSocket);
	}

	public void unregisterClosedWebSocket(WebKanbanWebSocket webKanbanWebSocket) {
		clients.remove(webKanbanWebSocket);
	}

	public void notifyStoryAdded(Story story) {
		for (WebKanbanWebSocket webKanbanWebSocket : ImmutableSet.copyOf(clients)) {
			webKanbanWebSocket.storyAdded(story);
		}
	}

	public void notifyStoryUpdated(Story story) {
		for (WebKanbanWebSocket webKanbanWebSocket : ImmutableSet.copyOf(clients)) {
			webKanbanWebSocket.storyUpdated(story);
		}
	}

	public void notifyStoryDeleted(int storyId) {
		for (WebKanbanWebSocket webKanbanWebSocket : ImmutableSet.copyOf(clients)) {
			webKanbanWebSocket.storyDeleted(storyId);
		}
	}
}
