var initial_states = {
    "states":{
        "DONE":"Done",
        "TODO":"Todo",
        "WIP":"Work in progress"
    },
    "states_order":[ "TODO", "WIP", "DONE" ]
};

var stories = [
    "TODO,1,sleep at night",
    "WIP,2,rest in front of the tv",
    "DONE,3,eat. a lot."
];

var init_board = function (stories) {
    var board = {};
    for (var i = 0, len = stories.length; i < len; i++) {
        var story = stories[i].split(',');
        var state = story[0];
        if (story.length == 3) {
            if (!board[state]) {
                board[state] = [];
            }
            board[state].push(story);
        }
    }
    return board;
};

var create_list = function (board, state) {
    var list = $('<ul class="state" id="' + state + '"></ul>');
    if (board[state]) {
        for (var i = 0, len = board[state].length; i < len; i++) {
            var story_element = $('<li class="box box_' + state + '">' + board[state][i][1] + ' ' + board[state][i][2] + '</li>');
            story_element.data("story", board[state][i]);
            list.append(story_element);
        }
    }
    return list;
};

var create_column = function (board, state, headline) {
    var state_column = $('<div class="dp10"></div>');
    state_column.append($('<div class="headline">' + headline + '</div>'));
    state_column.append(create_list(board, state));
    state_column.data("state", state);
    return state_column;
};

var create_board = function (app_data) {
    var table = $('<div id="board"></div>');
    var ids = "";
    for (j = 0; j < app_data.states_order.length; j++) {
        var state = app_data.states_order[j];
        ids += ((ids.length == 0) ? '' : ', ') + '#' + state;
        var state_column = create_column(app_data.board, state, app_data.states[state]);
        table.append(state_column);
    }
    $(function () {
        $(ids).sortable({
            connectWith:".state"
        }).disableSelection();
    });
    return table;
};

var init_and_create_board = function () {
    var app_data = { board:init_board(stories), states:initial_states.states, states_order:initial_states.states_order};
    $('#board-container').empty();
    $('#board-container').append(create_board(app_data));
}

init_and_create_board();
