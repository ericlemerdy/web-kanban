package repository;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Strings.isNullOrEmpty;
import static com.google.common.collect.Iterables.find;
import static com.google.common.collect.Lists.newArrayList;

import java.util.List;
import java.util.NoSuchElementException;

import model.Story;

import com.google.common.base.Predicate;
import com.google.common.collect.ImmutableList;

public class AllStories {
    private List<Story> stories = newArrayList( //
            new Story(1, "TODO", "sleep at night"), //
            new Story(2, "WIP", "rest in front of the tv"), //
            new Story(3, "DONE", "eat. a lot."));
    private Integer lastId = 3;

    public List<Story> list() {
        return ImmutableList.copyOf(stories);
    }

    public Story add(String state, String label) {
        checkArgument(!isNullOrEmpty(label), "Please provide a story label to add.");
        checkArgument(forName(label) == null, "The story '%s' already exists.", label);
        Story story = new Story(++lastId, state, label);
        stories.add(story);
        Clients.getInstance().notifyStoryAdded(story);
        return story;
    }

    public void update(final int id, final String state) {
        Story existingStory = forId(id);
        checkArgument(existingStory != null, "The story #%s does not exists.", id);
        stories.remove(existingStory);
        Story story = new Story(existingStory.id, state, existingStory.label);
        stories.add(story);
        Clients.getInstance().notifyStoryUpdated(story);
    }

    public void delete(int id) {
        Story existingStory = forId(id);
        checkArgument(existingStory != null, "The story #%s does not exists.", id);
        stories.remove(existingStory);
        Clients.getInstance().notifyStoryDeleted(id);
    }

    protected Story forName(final String label) {
        try {
            return find(stories, new Predicate<Story>() {
                public boolean apply(Story story) {
                    return story.label.equals(label);
                }
            });
        } catch (NoSuchElementException e) {
            return null;
        }
    }

    private Story forId(final int id) {
        try {
            return find(stories, new Predicate<Story>() {
                public boolean apply(Story story) {
                    return story.id == id;
                }
            });
        } catch (NoSuchElementException e) {
            return null;
        }
    }
}
