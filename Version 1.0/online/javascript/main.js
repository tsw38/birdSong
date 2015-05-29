$(function(){
	// on the lot page, change the background given a sentiment (mood)
	var changeBackground = function(mood){
		var randomBackground = 0;
		switch(mood){
			case "neutral":
			randomBackground = "neu" + (Math.round(Math.random(1,5)*5 + 1));
			$('body header').removeClass();
			break;

			case "negative":
			randomBackground = "neg" + (Math.round(Math.random(1,10)*10 + 1));
			$('body header').addClass("tooDark");
			break;

			case "positive":
			randomBackground = "pos" + (Math.round(Math.random(1,8)*10 + 1));
			$('body header').removeClass();
			break;
		}
		$("body").removeClass().addClass(randomBackground);
	}


	// Controller function calling on processing.php, database.php and lotScript.php
	// Processing sends search terms to Twitter to then pull current tweets about the search term
	// lotScript.php returns the tuple "tweet->sentiment" with a predefined limit as a JSON object
	var getSearchTermData = function (searchTerm){
		$.get("scripts/processing.php",{searchBy: searchTerm}, 
			function(data){
				$.getJSON("scripts/lotScript.php",function(data){
					var items = [];

					//update the LOT page with the new tweets from the script
					$(".tweets").html("").append("<table>");
						$.each(data.tweets, function (key, value){
							items.push(value);
							$(".tweets").append("<tr><td>" + value.tweet + "</td></tr>");
						});
					$(".tweets").append("</table>");

					// if there is a new tweet, generate a new song 
					if( ("\""+ items[0] + "\"") != $('tr:nth-of-type(1) td').text()){
						$('tr:nth-of-type(1) td').html("\"" + items[0] + "\"");
						//send the new tweet to be updated in the songDB
						changeBackground(items[1]);
						$.get("scripts/pullSong.php",{
							polarity : items[1],
							tweetLength : items[0].length*15
						});							
					}
				});
			}
		);
	}

	var runProcessing = function(){
		var initialCounter = 0;
		var tally = 0;
		var searchTerm = "";

		var getTrendingTopics = function(){
			$.get("testscripter.php", function (data){
				var searchTerm = data;
				document.title = searchTerm;
				getSearchTermData(searchTerm);
			});
		}

		// do the initial get request for the trending topic
		getTrendingTopics();

		//For the next 15 minutes do searches for the first trend
		var initialRun = function(){
			searchTerm = $("title").html();
			if(initialCounter < 131){
				initialCounter++;
				getSearchTermData(searchTerm);
			}
			else{
				clearInterval(initialRun);
			}
		};
		setInterval(initialRun, 7000);


		//new get new Trend for infinite loop to send to search
		window.setInterval(function(){
			getTrendingTopics();

			var counter = 0;
			var innerInterval = function (){
				searchTerm = $("title").html();
				if(counter < 132){
					counter++;
					getSearchTermData(searchTerm);
				}
				else{
					clearInterval(innerInterval);
				}
			};

			setInterval(innerInterval,7000);
		}, 930000);
	};


	//A wrapper function to ensure that the ajax code only runs on the LOT.php page
	var listOfTweetsPage = function(){
		if(window.location.pathname.search("lot.php") >= 0){
			runProcessing();
			var winHeight = window.outerHeight/2 + $(".tweetTable").height();
			$(".tweetTable").css({
				"margin-top": (winHeight)/2 + "px"
			});
		}
	}

	// an initializing function to ensure all methods are executed at the correct time
	var init = function(){
		listOfTweetsPage();	
	}

	init();
});