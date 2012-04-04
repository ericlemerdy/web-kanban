package api;

import com.google.common.collect.*;
import com.sun.jersey.api.container.httpserver.*;
import com.sun.net.httpserver.*;
import fr.*;
import org.codehaus.jettison.json.*;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.io.*;

@Path("/")
public class WebKanbanServer {

	private static AllStories allStories = new AllStories();

	@GET
	@Path("stories.json")
	@Produces(MediaType.APPLICATION_JSON)
	public Response stories() throws JSONException {
		return Response.ok(ImmutableMap.of("stories", allStories.list())).build();
	}

	@PUT
	@Path("story/{label}")
	@Produces({MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN})
	public Response addStory(@PathParam("label") String label) {
		if (allStories.exists(label)) {
			return Response.status(Response.Status.BAD_REQUEST).entity("this story already exists").build();
		}
		Story newStory = new Story("TODO", label);
		allStories.add(newStory);
		return Response.status(Response.Status.CREATED).entity(newStory).build();
	}

	@POST
	@Path("story/{label}/{state}")
	public Response changeStoryState(@PathParam("label") String label, @PathParam("state") String state) throws JSONException {
		if (!allStories.exists(label)) {
			return Response.status(Response.Status.BAD_REQUEST).entity("this story does not exists").build();
		}
		allStories.update(label, state);
		return Response.ok().build();
	}

	public static HttpServer start() throws IOException {
		HttpServer httpServer = HttpServerFactory.create("http://localhost:8080/");
		httpServer.start();
		return httpServer;
	}
}
