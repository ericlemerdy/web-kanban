package resource;

import com.google.common.collect.*;
import com.sun.jersey.api.container.httpserver.*;
import com.sun.net.httpserver.*;
import config.*;
import model.*;
import org.codehaus.jettison.json.*;
import repository.*;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.io.*;

@Path("/")
public class WebKanbanServer {

	private static AllStories allStories = new AllStories();

	@GET
	@Path("stories.json")
	@Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
	public Response stories() throws JSONException {
		return Response.ok(ImmutableMap.of("stories", allStories.list())).build();
	}

	@PUT
	@Path("story/{label}")
	@Produces({MediaType.APPLICATION_JSON + "; charset=UTF-8", MediaType.TEXT_PLAIN + "; charset=UTF-8"})
	public Response addStory(@PathParam("label") String label) {
		try {
			Story newStory = allStories.add("TODO", label);
			return Response.status(Response.Status.CREATED).entity(newStory).build();
		} catch (Exception e) {
			return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
		}
	}

	@PUT
	@Path("story")
	@Produces(MediaType.TEXT_PLAIN + "; charset=UTF-8")
	public Response addStoryWithAnEmptyLabel() {
		return Response.status(Response.Status.BAD_REQUEST).entity("Please provide a story label to add.").build();
	}

	@POST
	@Path("story/{id}/{state}")
	@Produces(MediaType.TEXT_PLAIN + "; charset=UTF-8")
	public Response changeStoryState(@PathParam("id") int id, @PathParam("state") String state) {
		try {
			allStories.update(id, state);
			return Response.ok().build();
		} catch (Exception e) {
			return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
		}
	}

	public static HttpServer start() throws IOException {
		HttpServer httpServer = HttpServerFactory.create("http://localhost:8080/", new KanbanJerseyApplication());
		httpServer.start();
		return httpServer;
	}
}
