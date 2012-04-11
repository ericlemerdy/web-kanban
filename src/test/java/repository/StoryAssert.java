package repository;

import model.Story;
import org.fest.assertions.GenericAssert;

import static org.fest.assertions.Assertions.assertThat;

public class StoryAssert extends GenericAssert<StoryAssert, Story> {

	protected StoryAssert(Class<StoryAssert> selfType, Story actualStory) {
		super(selfType, actualStory);
	}

	public StoryAssert isEqualTo(int expectedId, String expectedLabel, String expectedState) {
		isNotNull();
		assertThat(actual.id).isEqualTo(expectedId);
		assertThat(actual.label).isEqualTo(expectedLabel);
		assertThat(actual.state).isEqualTo(expectedState);
		return this;
	}

	public StoryAssert id(int expectedId) {
		isNotNull();
		assertThat(actual.id).isEqualTo(expectedId);
		return this;
	}

	public StoryAssert state(String expectedState) {
		isNotNull();
		assertThat(actual.state).isEqualTo(expectedState);
		return this;
	}

}
