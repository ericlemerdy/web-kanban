package api;

import com.google.common.collect.ImmutableMap;
import com.sun.jersey.api.container.httpserver.HttpServerFactory;
import com.sun.net.httpserver.HttpServer;
import org.codehaus.jettison.json.JSONException;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.List;

import static com.google.common.collect.Lists.newArrayList;

@Path("/")
public class WebKanbanServer {

	private static List<Story> stories = newArrayList(
			new Story("TODO", "sleep at night"),
			new Story("WIP", "rest in front of the tv"),
			new Story("DONE", "eat. a lot."));

	@GET
	@Path("stories.json")
	@Produces(MediaType.APPLICATION_JSON)
	public Response stories() throws JSONException {
		return Response.ok(ImmutableMap.of("stories", stories)).build();
	}

	@PUT
	@Path("story/{label}")
	public Response story2(@PathParam("label") String label) throws JSONException {
		if (!containsStory(label)) {
			Story newStory = new Story("TODO", label);
			stories.add(newStory);
			return Response.status(Response.Status.CREATED).entity(newStory).build();
		}
		return Response.status(Response.Status.CONFLICT).build();

	}

	private boolean containsStory(String label) {
		for (Story story : stories) {
			if (story.label.equals(label)) {
				return true;
			}
		}
		return false;
	}

	public static HttpServer start() throws IOException {
		HttpServer httpServer = HttpServerFactory.create("http://localhost:8080/");
		httpServer.start();
		return httpServer;
	}
}
