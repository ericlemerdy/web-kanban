package repository;

import model.Story;

public class RepositoryAssertions {

  public static StoryAssert assertThat(final Story actual) {
    return new StoryAssert(actual);
  }

}
