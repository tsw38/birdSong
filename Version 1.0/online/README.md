# Crawlr
----
The creator: [Tyler Scott](http://www.tylerscottwilliams.com)

----
## Usage
1. In order to make this project work, you need a config file inside of the functions folder that contains the connection to your database
2. Index page is used to start the collection of the search

----

## Libraries Used

[JQuery](http://jquery.com/),
[Chartist](http://gionkunz.github.io/chartist-js/),
[phpInsight](https://github.com/JWHennessey/phpInsight)

----

## UPDATES TO COME
* Adaptive Refresh Rate
* Take the running average of the time stamps to refresh more accurately
* ~~Average TPH (Tweets per hour) of a search term~~
* Password Protect the Submission form for tweets as well
* On a new search, truncate database
* On Creating a Search, if >50% of search terms are in the database then clear the database
* ~~Make the LOT auto refresh~~
* Only access processing page if a correct session has started
* ~~Create a dictionary for a search term~~

* Actively follow people who follow the most people
	`or people who tweet the most, or who people with the most followers`
* Take trending tweets and do a location search, then aquire people who are tweeting nearby as people who are local to the area.
	-will have to regularly check if people are still tweeting in the radius, if they are not, then delete them from the list
