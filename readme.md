Web-kanban
==========

![Web-kanban](https://raw.github.com/ericlemerdy/web-kanban/master/src/main/img/screenshot.png)

A simple javascript application to display draggable tickets on a web page.

Why another kanban app ?
------------------------
The motivation is to have a simple tool that one can host easily in an offline java environment. Unfortunately, there are [no plan](http://webapps.stackexchange.com/questions/20042/can-i-host-my-own-instance-of-trello#comment15397_20043) to sell an hosted version of trello. And [alternatives to trello](http://alternativeto.net/software/trello/) are all web-based products or non open-source.

We started from [Simple-kanban](http://www.simple-kanban.com/) since it does the job quite well.
After bummping versions of both jquery and the [jQuery List DragSort plugin](http://dragsort.codeplex.com/), [@seblm](http://github.com/seblm) realized that JQuery UI already have [the native feature](http://jqueryui.com/demos/sortable) to sort out elements of a page. And then we are significantly deriving from simple kanban...

How to use it ?
---------------
    mvn jetty:run

Where the data is stored ?
--------------------------
The data is stored in memory server side. It is not persisted when the server shutdown.

Can I use it for a team ?
-------------------------
This tool supports several web clients. There is a websocket implementation to support updates : any story modification is reflected on other clients.

This is not kanban
------------------
This version does not currently supports column creation. Only 3 columns are created by default.

The plan
--------
Look at the issues for the roadmap. Feel free to submit a feature or fork it.
