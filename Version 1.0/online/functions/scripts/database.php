<?php 
	require_once "../config.php";

	$connect = new mysqli(DB_HOST, DB_ADMIN, DB_PASSWORD, DB_NAME);
	$limitSize = 100;
	$count = 0;
	$request = $connect->query("SELECT * FROM tweets, analysis WHERE tweets.tweetID = analysis.tweetID ORDER BY timestamp DESC LIMIT " . $limitSize);
	
	//creating the JSON object for the Angular directive to manipulate

	while($row = $request->fetch_assoc()){
		print('{');
		print('"tweet":"' .$row[tweet] . '"');
		print(',');
		print('"sentiment":"' . $row[Sentiment] . '"');
		print('}');
		$count++;
	}
	$connect->close();
?>	