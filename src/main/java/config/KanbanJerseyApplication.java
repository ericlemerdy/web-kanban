package config;

import com.google.common.collect.*;
import com.sun.jersey.api.core.*;
import model.*;
import resource.*;

import static com.sun.jersey.api.json.JSONConfiguration.*;

public class KanbanJerseyApplication extends ClassNamesResourceConfig {
	public KanbanJerseyApplication() {
		super(WebKanbanServer.class.getName());
		getClasses().addAll(ImmutableList.of(Story.class, org.codehaus.jackson.jaxrs.JacksonJsonProvider.class, org.codehaus.jackson.jaxrs.JsonParseExceptionMapper.class, org.codehaus.jackson.jaxrs.JacksonJaxbJsonProvider.class, org.codehaus.jackson.jaxrs.JsonMappingExceptionMapper.class));
		getFeatures().putAll(ImmutableMap.of(FEATURE_POJO_MAPPING, true));
	}
}
