var create_and_attach_story = function (story) {
    var storyElement = $('<li class="story"></li>');
    storyElement.addClass('story_' + story.state);
    storyElement.append(story.label);
    $('#' + story.state).append(storyElement);
    return storyElement;
};
var fetch_and_create_stories = function () {
    $.getJSON('api/stories.json', function (data) {
        for (var i in data.stories) {
            create_and_attach_story(data.stories[i]);
        }
    });
};
var add_story_button = function () {
    var addStory = $('#add-story');
    addStory.button();
    addStory.click(function () {
        $.ajax({
            url:'api/story/' + $('#add-story-text').val(),
            type:'PUT',
            dataType:'json',
            success:function (data) {
                var newStory = create_and_attach_story(data);
                newStory.show("drop");
            },
            error:function (data) {
                $('#error-message').html('<p>' + data.responseText + '</p>');
            }
        })
    });
}
var make_states_columns_sortable = function () {
    $('.state').sortable({
        connectWith:".state"
    }).disableSelection();
    $('.state').droppable({
        drop:function (event, ui) {
            console.log(ui);
            $.ajax({
                url:'api/story/' + $(event.srcElement).text() + '/' + this.id,
                type:'POST',
                error:function (data) {
                    $('#error-message').html('<p>' + data.responseText + '</p>');
                }
            })
        }
    });
};
$(function () {
    add_story_button();
    make_states_columns_sortable();
    fetch_and_create_stories();
});
