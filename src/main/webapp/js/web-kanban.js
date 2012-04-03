var create_and_attach_story = function (story) {
    var storyElement = $('<li class="story"></li>');
    storyElement.addClass('story_' + story.state);
    storyElement.append(story.label);
    $('#' + story.state).append(storyElement);
    return storyElement;
};
var fetch_and_create_stories = function () {
    $.getJSON('http://localhost:8080/web-kanban/api/stories.json', function (data) {
        for (var i in data.stories) {
            create_and_attach_story(data.stories[i]);
        }
    });
};
var add_story_button = function () {
    var addStory = $('#add-story');
    addStory.button();
    addStory.click(function () {
        var newStory = create_and_attach_story({"state":"TODO", "label":$('#add-story-text').val()});
        newStory.show("drop");
    });
}
var make_states_columns_sortable = function () {
    $('.state').sortable({
        connectWith:".state"
    }).disableSelection();
};
$(function () {
    add_story_button();
    make_states_columns_sortable();
    fetch_and_create_stories();
});
