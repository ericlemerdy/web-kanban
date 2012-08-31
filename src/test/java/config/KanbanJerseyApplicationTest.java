package config;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.assertions.MapAssert.entry;
import model.Story;

import org.junit.Test;

import resource.ApiListingResource;
import resource.WebKanbanServer;

import com.sun.jersey.api.core.DefaultResourceConfig;

public class KanbanJerseyApplicationTest {

	@Test
	public void should_not_scan_classpath() throws Exception {
		DefaultResourceConfig config = new KanbanJerseyApplication();
		assertThat(config.getExplicitRootResources()).isEmpty();
		assertThat(config.getClasses()).containsOnly( //
				WebKanbanServer.class, //
				ApiListingResource.class, //
				Story.class, //
				org.codehaus.jackson.jaxrs.JacksonJsonProvider.class, //
				org.codehaus.jackson.jaxrs.JsonParseExceptionMapper.class, //
				org.codehaus.jackson.jaxrs.JacksonJaxbJsonProvider.class, //
				org.codehaus.jackson.jaxrs.JsonMappingExceptionMapper.class);
		assertThat(config.getRootResourceClasses()).containsOnly(
				WebKanbanServer.class, ApiListingResource.class);
		assertThat(config.getFeatures()).includes(
				entry("com.sun.jersey.api.json.POJOMappingFeature", true));
	}
}
