package config;

import static com.sun.jersey.api.json.JSONConfiguration.FEATURE_POJO_MAPPING;
import model.Story;
import resource.ApiListingResource;
import resource.WebKanbanServer;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.sun.jersey.api.core.ClassNamesResourceConfig;

public class KanbanJerseyApplication extends ClassNamesResourceConfig {
	public KanbanJerseyApplication() {
		super(WebKanbanServer.class, ApiListingResource.class);
		getClasses().addAll(ImmutableList.of( //
				Story.class, //
				org.codehaus.jackson.jaxrs.JacksonJsonProvider.class, //
				org.codehaus.jackson.jaxrs.JsonParseExceptionMapper.class, //
				org.codehaus.jackson.jaxrs.JacksonJaxbJsonProvider.class, //
				org.codehaus.jackson.jaxrs.JsonMappingExceptionMapper.class));
		getFeatures().putAll(ImmutableMap.of(FEATURE_POJO_MAPPING, true));
	}
}
