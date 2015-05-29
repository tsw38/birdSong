$(function(){
	var init, polarity, trendsNow;

	polarity = function(){
		var tweetArray = [];
		$.get("php/polarity.php", function(data){
			tweetArray = data.split('|').slice(0,-1);
			
			var count = 0;
			$.each(tweetArray, function(){
				var tweet = this.split(",");
				if(count%2 == 0){
					$('.polarityOfTweets').append("<tr class='even'><td>"+tweet[0]+"</td><td>"+tweet[1]+"</td></td></tr>")
				} else {
					$('.polarityOfTweets').append("<tr class='odd'><td>"+tweet[0]+"</td><td>"+tweet[1]+"</td></td></tr>")
				}
				count++;
			});
		})

	}

	trendsNow = function(){
		var trends = [];
		$.get("php/alltime.php", function(data){
			trends = data.split("|").slice(0,-1);

			var count = 0;
			$.each(trends, function(){
				var trend = this.split(",");
				if(count%2 == 0){
					$('.allTime').append("<tr class='even'><td>"+trend[0]+"</td></tr>");
				} else {
					$('.allTime').append("<tr class='odd'><td>"+trend[0]+"</td></tr>");
				}
				count++;
			})
		})
	}

	init = function(){
		polarity();
		trendsNow();
	}

	init();
});