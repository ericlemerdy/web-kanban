package repository;

import model.Story;
import org.fest.assertions.Condition;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.fest.assertions.Assertions.assertThat;

public class AllStoriesTest {

	private AllStories allStories;

	@Before
	public void createAllStories() {
		allStories = new AllStories();
	}

	@Test
	public void should_get_3_initial_stories() {
		final List<Story> stories = allStories.list();
		assertThat(stories).hasSize(3);
		RepositoryAssertions.assertThat(stories.get(0)).isEqualTo(1, "sleep at night", "TODO");
		RepositoryAssertions.assertThat(stories.get(1)).isEqualTo(2, "rest in front of the tv", "WIP");
		RepositoryAssertions.assertThat(stories.get(2)).isEqualTo(3, "eat. a lot.", "DONE");
	}

	@Test
	public void should_creates_a_new_story() {
		allStories.add("WIP", "new story");

		final List<Story> stories = allStories.list();
		assertThat(stories).hasSize(4);
		RepositoryAssertions.assertThat(stories.get(3)).isEqualTo(4, "new story", "WIP");
	}

	@Test
	public void should_update_a_new_story() {
		allStories.update(2, "DONE");

		final Story updatedStory = allStories.list().get(2);
		RepositoryAssertions.assertThat(updatedStory).state("DONE");

	}

}
