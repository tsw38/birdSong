<?php 
	include '../config.php';
	include '../normalize.php';
	
	$connection = new mysqli(DB_HOST, DB_ADMIN, DB_PASSWORD, DB_NAME);

	$grabTweets = "SELECT * FROM tweets, analysis WHERE tweets.tweetID = analysis.tweetID ORDER BY timestamp DESC LIMIT 32";
	$resultTweets = $connection->query($grabTweets);
	$rowNum = 0;
	print("{\"tweets\": [");
	
	// found twitter bug where enters a really old "dummy" tweet at random, so I want to make sure to not include this in the view
	// returns a JSON object of tweet -> sentiment for a predefined number of tweets
	foreach($resultTweets AS $resultRow){
		if(intval($resultRow[timestamp]) >= 2015){
			$connection->query("DELETE FROM tweets WHERE tweet=$resultRow[tweet]");
			print("{\"tweet\":" . $resultRow[tweet] . '",');
			print("\"sentiment\":" . $resultRow[sentiment] . '"');
			$rowNum++;
			if($rowNum < $resultTweets->num_rows){
				print(",");
			}
		print('}');
		}
	}
	print("]}");

	$connection->close();
?>