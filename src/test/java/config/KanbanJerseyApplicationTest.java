package config;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.assertions.MapAssert.entry;

import org.junit.Test;

import resource.WebKanbanServer;

import com.sun.jersey.api.core.DefaultResourceConfig;

public class KanbanJerseyApplicationTest {

	@Test
	public void should_not_scan_classpath() throws Exception {
		DefaultResourceConfig config = new KanbanJerseyApplication();
		assertThat(config.getExplicitRootResources()).isEmpty();
		assertThat(config.getClasses()).containsOnly( //
				com.wordnik.swagger.jaxrs.ApiListingResourceJSON.class, //
				com.wordnik.swagger.jaxrs.ApiListingResourceXML.class, //
				com.wordnik.swagger.jaxrs.ApiHelpMessageBodyWriter.class, //
				resource.WebKanbanServer.class);
		assertThat(config.getRootResourceClasses()).containsOnly(
				WebKanbanServer.class, //
				com.wordnik.swagger.jaxrs.ApiListingResourceJSON.class, //
				com.wordnik.swagger.jaxrs.ApiListingResourceXML.class);
		assertThat(config.getFeatures()).includes(
						entry(com.sun.jersey.api.json.JSONConfiguration.FEATURE_POJO_MAPPING,
								true));
	}
}
