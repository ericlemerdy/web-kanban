package websocket;

import model.*;
import org.eclipse.jetty.websocket.*;
import org.junit.*;
import org.junit.runner.*;
import org.mockito.*;
import org.mockito.runners.*;
import repository.*;

import java.io.*;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class WebKanbanWebSocketTest {

	@Mock WebSocket.Connection connection;

	@Mock Clients clients;

	@Test
	public void when_a_connection_is_opened_should_register_to_the_client_list() {
		WebKanbanWebSocket webSocket = new WebKanbanWebSocket(clients);

		webSocket.onOpen(connection);

		verify(clients).registerOpenedWebSocket(webSocket);
	}

	@Test
	public void when_a_connection_is_closed_should_unregister_to_the_client_list() {
		WebKanbanWebSocket webSocket = new WebKanbanWebSocket(clients);

		webSocket.onOpen(connection);
		webSocket.onClose(0, "foo");

		verify(clients).unregisterClosedWebSocket(webSocket);
	}

	@Test
	public void when_a_story_is_added_should_send_message_through_the_connection() throws IOException {
		WebKanbanWebSocket webSocket = new WebKanbanWebSocket(clients);

		webSocket.onOpen(connection);
		Story story = new Story(0, "foo", "bar");
		webSocket.storyAdded(story);

		verify(connection).sendMessage("{'added':{'id':0, 'label':'bar', 'state':'foo'}}".replace('\'', '"'));
	}

	@Test
	public void when_a_story_is_updated_should_send_message_through_the_connection() throws IOException {
		WebKanbanWebSocket webSocket = new WebKanbanWebSocket(clients);

		webSocket.onOpen(connection);
		webSocket.storyUpdated(new Story(0, "foo", "bar"));

		verify(connection).sendMessage("{'updated':{'id':0, 'label':'bar', 'state':'foo'}}".replace('\'', '"'));
	}

}
