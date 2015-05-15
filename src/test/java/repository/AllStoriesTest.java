package repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import model.Story;

import org.junit.Before;
import org.junit.Test;

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

	@Test(expected = IllegalArgumentException.class)
	public void should_not_add_a_story_with_empty_label() {
		try {
			allStories.add("DONE", "");
		} catch (IllegalArgumentException e) {
			assertThat(e).hasMessage("Please provide a story label to add.");
			throw e;
		}
	}

	@Test(expected = IllegalArgumentException.class)
	public void should_not_add_a_story_with_null_label() {
		try {
			allStories.add("DONE", null);
		} catch (IllegalArgumentException e) {
			assertThat(e).hasMessage("Please provide a story label to add.");
			throw e;
		}
	}

	@Test(expected = IllegalArgumentException.class)
	public void should_not_add_an_existing_story() {
		try {
			allStories.add("DONE", "sleep at night");
		} catch (IllegalArgumentException e) {
			assertThat(e).hasMessage("The story 'sleep at night' already exists.");
			throw e;
		}
	}

	@Test
	public void should_update_a_new_story() {
		allStories.update(2, "DONE");

		final Story updatedStory = allStories.list().get(2);
		RepositoryAssertions.assertThat(updatedStory).hasState("DONE");
	}

	@Test(expected = IllegalArgumentException.class)
	public void should_not_update_an_unkown_story() {
		try {
			allStories.update(6, "DONE");
		} catch (IllegalArgumentException e) {
			assertThat(e).hasMessage("The story #6 does not exists.");
			throw e;
		}
	}

	@Test
	public void should_delete_story() {
		allStories.delete(1);

		final List<Story> stories = allStories.list();
		assertThat(stories).hasSize(2);
		RepositoryAssertions.assertThat(stories.get(0)).hasId(2);
		RepositoryAssertions.assertThat(stories.get(1)).hasId(3);
	}

	@Test(expected = IllegalArgumentException.class)
	public void should_not_delete_unexisting_story() {
		try {
			allStories.delete(42);
		} catch (IllegalArgumentException e) {
			assertThat(e).hasMessage("The story #42 does not exists.");
			throw e;
		}
	}

	@Test
	public void should_increment_ids_after_create_even_after_a_story_has_been_deleted() {
		allStories.add("no state", "a story with id 4");
		allStories.delete(4);
		allStories.add("no state", "a story with id 5");

		assertThat(allStories.forName("a story with id 5").id).isEqualTo(5);
	}

}
