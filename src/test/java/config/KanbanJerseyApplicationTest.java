package config;

import com.sun.jersey.api.core.*;
import model.*;
import org.junit.*;
import resource.*;

import static org.fest.assertions.Assertions.*;
import static org.fest.assertions.MapAssert.*;

public class KanbanJerseyApplicationTest {

	@Test
	public void should_not_scan_classpath() throws Exception {
		DefaultResourceConfig config = new KanbanJerseyApplication();
		assertThat(config.getExplicitRootResources()).isEmpty();
		assertThat(config.getClasses()).containsOnly(WebKanbanServer.class, Story.class, org.codehaus.jackson.jaxrs.JacksonJsonProvider.class, org.codehaus.jackson.jaxrs.JsonParseExceptionMapper.class, org.codehaus.jackson.jaxrs.JacksonJaxbJsonProvider.class, org.codehaus.jackson.jaxrs.JsonMappingExceptionMapper.class);
		assertThat(config.getRootResourceClasses()).containsOnly(WebKanbanServer.class);
		assertThat(config.getFeatures()).includes(entry("com.sun.jersey.api.json.POJOMappingFeature", true));
	}
}
