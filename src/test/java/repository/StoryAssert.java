package repository;

import static org.fest.assertions.Assertions.assertThat;
import model.Story;

import org.fest.assertions.GenericAssert;

public class StoryAssert extends GenericAssert<StoryAssert, Story> {

  protected StoryAssert(Story actualStory) {
    super(StoryAssert.class, actualStory);
  }

  public StoryAssert isEqualTo(int expectedId, String expectedLabel, String expectedState) {
    isNotNull();
    assertThat(actual.id).isEqualTo(expectedId);
    assertThat(actual.label).isEqualTo(expectedLabel);
    assertThat(actual.state).isEqualTo(expectedState);
    return this;
  }

  public StoryAssert hasId(int expectedId) {
    isNotNull();
    assertThat(actual.id).isEqualTo(expectedId);
    return this;
  }

  public StoryAssert hasState(String expectedState) {
    isNotNull();
    assertThat(actual.state).isEqualTo(expectedState);
    return this;
  }

}
