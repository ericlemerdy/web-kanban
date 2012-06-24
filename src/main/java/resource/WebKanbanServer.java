package resource;

import java.io.IOException;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import model.Story;

import org.codehaus.jettison.json.JSONException;

import repository.AllStories;

import com.google.common.collect.ImmutableMap;
import com.sun.jersey.api.container.httpserver.HttpServerFactory;
import com.sun.net.httpserver.HttpServer;

import config.KanbanJerseyApplication;

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
	@Produces({ MediaType.APPLICATION_JSON + "; charset=UTF-8", MediaType.TEXT_PLAIN + "; charset=UTF-8" })
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

	@DELETE
	@Path("story/{id}")
	@Produces(MediaType.TEXT_PLAIN + "; charset=UTF-8")
	public Response deleteStory(@PathParam("id") int id) {
		try {
			allStories.delete(id);
			return Response.ok().build();
		} catch (IllegalArgumentException e) {
			return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
		}
	}

	public static HttpServer start() throws IOException {
		allStories = new AllStories();
		HttpServer httpServer = HttpServerFactory.create("http://127.0.0.1:4242/", new KanbanJerseyApplication());
		httpServer.start();
		return httpServer;
	}
}
