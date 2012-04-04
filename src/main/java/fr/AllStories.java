package fr;

import api.*;
import com.google.common.collect.*;

import java.util.*;

import static com.google.common.collect.Lists.*;

public class AllStories {
	List<Story> stories = newArrayList(new Story("TODO", "sleep at night"), new Story("WIP", "rest in front of the tv"), new Story("DONE", "eat. a lot."));

	public List<Story> list() {
		return ImmutableList.copyOf(stories);
	}

	public void add(Story story) {
		stories.add(story);
	}

	public Story forName(String label) {
		for (Story story : stories) {
			if (story.label.equals(label)) {
				return story;
			}
		}
		return null;
	}

	public boolean exists(String label) {
		return forName(label) != null;
	}

	public void update(String label, String state) {
		stories.remove(forName(label));
		stories.add(new Story(state, label));
	}
}
