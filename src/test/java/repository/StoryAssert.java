package repository;

import static org.assertj.core.api.Assertions.assertThat;
import model.Story;

import org.assertj.core.api.AbstractAssert;

public class StoryAssert extends AbstractAssert<StoryAssert, Story> {

  protected StoryAssert(Story actualStory) {
    super(actualStory, StoryAssert.class);
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
