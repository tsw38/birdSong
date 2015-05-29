<?php
	include '../config.php';
	$connection = new mysqli(DB_HOST, DB_ADMIN, DB_PASSWORD, DB_NAME);

	$weekDay = ["Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"];

	foreach($weekDay AS $day){
		$tweetsPerDayQuery = "SELECT DATE_FORMAT(timestamp, '%H') AS 'oclock', ROUND(count(timestamp)/COUNT(DISTINCT DATE_FORMAT(timestamp, '%Y:%M:%D'))) as 'TweetsPerHour' FROM tweets WHERE DAYNAME(timestamp) = \"$day\" GROUP BY DATE_FORMAT(timestamp, '%H')";
		$emptyArray= [];
		$returnedResults = $connection->query($tweetsPerDayQuery);
		$timeArray = array_pad($emptyArray,24,0);


		if($returnedResults->num_rows > 0){
			
			while($row = $returnedResults->fetch_assoc()){
				$hour = intval($row[oclock])+1;

				$timeArray[$hour] = $row[TweetsPerHour];

				//if the hour equals 23, then push it to the database
				//You can even delete the previous 24 hour tweets
			}
		}
		print("<br />");
		var_dump($timeArray);

		foreach($timeArray AS $hourT => $tweetNumber){

			if($hourT != 0){
				print("Time:$hourT,TweetsPerHour:$tweetNumber;");
			}
		}
		print("|");
	}

	

	

	$connection->close();
?>