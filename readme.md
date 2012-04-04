Web-kanban
==========

![Web-kanban](web-kanban/raw/master/src/main/img/screenshot.png)

A simple javascript application to display draggable tickets on a web page.

Why another kanban app ?
------------------------
The motivation is to have a simple tool that I can host easily in an offline-environment. Unfortunately, there are [no plan](http://webapps.stackexchange.com/questions/20042/can-i-host-my-own-instance-of-trello#comment15397_20043) to sell an hosted version of trello. And [alternatives to trello](http://alternativeto.net/software/trello/) are all web-based products or non open-source.

We started from [Simple-kanban](http://www.simple-kanban.com/) since it does the job quite well.
After bummping versions of both jquery and the [jQuery List DragSort plugin](http://dragsort.codeplex.com/), @seblm realized that JQuery UI already have [the native feature](http://jqueryui.com/demos/sortable) to sort out elements of a page. And then we are significantly deriving from simple kanban...

How to use it ?
---------------
    mvn jetty:run

Where the data is stored ?
--------------------------
The data is still not stored at all... At least, only in the DOM.

Can I use it for a team ?
-------------------------
This tool is not ready for team use since all changes are local to your navigator.

The plan
--------
Look at the issues for the roadmap. Feel free to submit a feature or fork it.
