package api;

import com.google.common.collect.*;
import com.sun.jersey.api.container.httpserver.*;
import com.sun.net.httpserver.*;
import org.codehaus.jettison.json.*;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.io.*;
import java.util.*;

import static com.google.common.collect.Lists.*;

@Path("/")
public class WebKanbanServer {

	private static List<Story> stories = newArrayList(new Story("TODO", "sleep at night"), new Story("WIP", "rest in front of the tv"), new Story("DONE", "eat. a lot."));

	@GET
	@Path("stories.json")
	@Produces(MediaType.APPLICATION_JSON)
	public Response stories() throws JSONException {
		return Response.ok(ImmutableMap.of("stories", stories)).build();
	}

	@PUT
	@Path("story/{label}")
	@Produces({MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN})
	public Response addStory(@PathParam("label") String label) throws JSONException {
		if (getStory(label)) {
			return Response.status(Response.Status.BAD_REQUEST).entity("this story already exists").build();
		}
		Story newStory = new Story("TODO", label);
		stories.add(newStory);
		return Response.status(Response.Status.CREATED).entity(newStory).build();
	}

	private boolean getStory(String label) {
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
