<?php
	require '../config.php';
	require '../normalize.php';
	require '../../phpInsight-master/autoload.php';
	include '../../oauth-php/library/OAuthStore.php';
	include '../../oauth-php/library/OAuthRequester.php';

	/* Given a search term, this script queries Twitter search API (utilizing OAuth and a Sentiment Analyzer)
	 * removes unneccessary text in a tweet and then processes it for Sentiment to stores it in a database
	 */

	if(isset($_GET['searchBy']) && !empty($_GET['searchBy'])){
		$searchBy = urlencode($_GET['searchBy']);
		$connection = new mysqli(DB_HOST, DB_ADMIN, DB_PASSWORD, DB_NAME);
		$options2 = array('consumer_key' => "ECMXxfbBXmaQJ96u7XFh65KQN", 'consumer_secret' => "4q3bVO8we47wOflcndJ77yaqciob6u4CLsIejtfO21lgg2yv5i");
		OAuthStore::instance("2Leg", $options2);

		$url = "https://api.twitter.com/1.1/search/tweets.json?q=$searchBy&count=40&lang=en";
		$method = "GET";
		$params = null;

		try{
			// try to obtain the jSON object, the response from the OAuth Authentication 
			// will be an array as: ('code'=>int, 'headers'=>array(), 'body'=>string)..Only the body matters to me
			// Then parse the body as a json object and pull just the tweet and timestamp
			// create a call to the Sentiment class object to be used by every tweet that was received
			$request = new OAuthRequester($url, $method, $params);
			$response = $request -> doRequest();
			$content = $response['body'];
			$jsonObj = json_decode($content, true);
			$sentiment = new \PHPInsight\Sentiment();


			// for each tweet, remove unnessessary content by Normalizing,
			// then parse the datetime twitter response into MYSQL appropriate datetime
			// for optimization, only analyze tweets that are inserted into the database
			foreach($jsonObj[statuses] AS $tweet){
				$normalTweet = normalizeInput($tweet[text]);
				$creationDate = date('Y-m-d H:i:s', strtotime($tweet[created_at]));
				
				// this is not optimal. Would be better to use last_row_inserted function, but does not consider 
				// repeated key that were not inserted, so the alternative is to get the most current ID from the database
				// Dont really need this, but considering how many tweets are being processed, I dont want to reach the upperbound on database
				$nonSkippingID = $connection->query("SELECT * FROM `tweets` ORDER BY tweetID DESC LIMIT 1");
				if($nonSkippingID->num_rows > 0){
					$tweetID = intval($nonSkippingID->fetch_assoc()[tweetID])+1;
				} else {
					$tweetID = 1;
				}

				$pushQuery = "INSERT INTO tweets (tweetID, tweet, timestamp) VALUES ($tweetID,'$normalTweet', '$creationDate')";
				$returnedValue = $connection->query($pushQuery);
				
				if($returnedValue){
					$sentimentPolarity = $sentiment->categorise($normalTweet);
					$analysisQuery = "INSERT INTO analysis (tweetID, Sentiment) VALUES($tweetID,'$sentimentPolarity')";
					$connection->query($analysisQuery);
				}
			}
		}
		catch(OAuthException2 $e){
			var_dump($e);
		}
		$connection->close();
	}
?>