package repository;

import com.google.common.base.*;
import com.google.common.collect.*;
import model.*;

import java.util.*;

import static com.google.common.base.Preconditions.*;
import static com.google.common.base.Strings.*;
import static com.google.common.collect.Iterables.*;
import static com.google.common.collect.Lists.*;

public class AllStories {
	List<Story> stories = newArrayList(new Story("TODO", "sleep at night"), new Story("WIP", "rest in front of the tv"), new Story("DONE", "eat. a lot."));

	public List<Story> list() {
		return ImmutableList.copyOf(stories);
	}

	public void add(Story story) {
		checkNotNull(story, "Please provide a story to add.");
		checkArgument(!isNullOrEmpty(story.label), "Please provide a story label to add.");
		checkArgument(forName(story.label) == null, "The story '%s' already exists.", story.label);
		stories.add(story);
	}

	public void update(String label, String state) {
		checkArgument(!isNullOrEmpty(label), "Please provide a story label for update.");
		checkArgument(!isNullOrEmpty(label), "Please provide a story state for update.");
		checkArgument(forName(label) != null, "The story '%s' does not exists.", label);
		stories.remove(forName(label));
		add(new Story(state, label));
	}

	private Story forName(final String label) {
		try {
			return find(stories, new Predicate<Story>() {
				@Override
				public boolean apply(Story story) {
					return story.label.equals(label);
				}
			});
		} catch (NoSuchElementException e) {
			return null;
		}
	}
}
