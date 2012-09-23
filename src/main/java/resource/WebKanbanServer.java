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
import repository.AllStories;

import com.google.common.collect.ImmutableMap;
import com.sun.jersey.api.container.httpserver.HttpServerFactory;
import com.sun.net.httpserver.HttpServer;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import com.wordnik.swagger.jaxrs.JavaHelp;

import config.KanbanJerseyApplication;

@Path(value = "/")
@Api(value = "/", description = "Stories")
@Produces({ MediaType.APPLICATION_JSON })
public class WebKanbanServer extends JavaHelp {

	private static AllStories allStories = new AllStories();

	@GET
	@Path(value = "/stories.json")
	@ApiOperation(value = "List all stories", notes = "Add extra notes here", responseClass = "model.Story", httpMethod = "GET", multiValueResponse = true)
	public Response stories() {
		return Response.ok(ImmutableMap.of("stories", allStories.list()))
				.build();
	}

	@PUT
	@Path(value = "story/{label}")
	@ApiOperation(value = "Add story", notes = "Add extra notes here", responseClass = "model.Story")
	public Response addStory(
			@PathParam("label") @ApiParam(value = "Label to set") String label) {
		try {
			Story newStory = allStories.add("TODO", label);
			return Response.status(Response.Status.CREATED).entity(newStory)
					.build();
		} catch (Exception e) {
			return Response.status(Response.Status.BAD_REQUEST)
					.entity(e.getMessage()).build();
		}
	}

	@PUT
	@ApiOperation(value = "Add story", notes = "Add extra notes here", responseClass = "model.Story")
	@Path(value = "story")
	public Response addStoryWithAnEmptyLabel() {
		return Response.status(Response.Status.BAD_REQUEST)
				.entity("Please provide a story label to add.").build();
	}

	@POST
	@ApiOperation(value = "Add story", notes = "Add extra notes here", responseClass = "model.Story")
	@Path(value = "story/{id}/{state}")
	public Response changeStoryState(
			//
			@PathParam("id") @ApiParam(value = "id of story to change state") int id,
			@PathParam("state") @ApiParam(value = "New state to set") String state) {
		try {
			allStories.update(id, state);
			return Response.ok().build();
		} catch (Exception e) {
			return Response.status(Response.Status.BAD_REQUEST)
					.entity(e.getMessage()).build();
		}
	}

	@DELETE
	@ApiOperation(value = "Delete story", notes = "Add extra notes here", responseClass = "model.Story")
	@Path(value = "story/{id}")
	public Response deleteStory(
			@PathParam("id") @ApiParam(value = "The story id to delete") int id) {
		try {
			allStories.delete(id);
			return Response.ok().build();
		} catch (IllegalArgumentException e) {
			return Response.status(Response.Status.BAD_REQUEST)
					.entity(e.getMessage()).build();
		}
	}

	public static HttpServer start() throws IOException {
		allStories = new AllStories();
		HttpServer httpServer = HttpServerFactory.create(
				"http://127.0.0.1:4242/", new KanbanJerseyApplication());
		httpServer.start();
		return httpServer;
	}
}
