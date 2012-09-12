package config;

import com.google.common.collect.ImmutableMap;
import com.sun.jersey.api.core.PackagesResourceConfig;

public class KanbanJerseyApplication extends PackagesResourceConfig {
	public KanbanJerseyApplication() {
		super("resource", "com.wordnik.swagger.jaxrs");
		getFeatures().putAll(
				ImmutableMap.of("com.sun.jersey.api.json.POJOMappingFeature",
						true));
	}
}
