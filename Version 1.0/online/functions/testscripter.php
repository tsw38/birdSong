<?php
	require 'config.php';
	require 'normalize.php';
	include '../oauth-php/library/OAuthStore.php';
	include '../oauth-php/library/OAuthRequester.php';

		$connection = new mysqli(DB_HOST, DB_ADMIN, DB_PASSWORD, DB_NAME);

		$resultArray = $connection->query("SELECT * FROM cation");
		$usersArray = array();

		$connection->query("TRUNCATE TABLE trends");
		
		if($resultArray->num_rows > 0){
			while($row = $resultArray->fetch_assoc()){
				$usersArray["$row[conKey]"] = $row[conSec]; 
			}

			$user2Key = array_search(next($usersArray),$usersArray);
			$user2Sec = $usersArray[$user2Key];

			$options = array('consumer_key' => $user2Key, 'consumer_secret' => $user2Sec);
			OAuthStore::instance("2Leg", $options);


			$locationsArray = array("NewYork" => 2459115,"Washington" => 2514815, "LA" => 2442047, "Chicago" => 2379574,
				"Houston" => 2424766,"Philadelphia" => 2471217,"Austin" => 2357536,"Indianapolis" => 2427032,
				"Nashville" => 2457170,"Atlanta" => 2357024,"Portland" => 2475687,"Boston" => 2367105,"Seattle" => 2490383, "Tampa" => 2503863);

			foreach($locationsArray AS $city){
				$url = "https://api.twitter.com/1.1/trends/place.json?id=$city";

				$method = "GET";
				$params = null;

				try{
				 	// trying to obtain the json object
					$request = new OAuthRequester($url, $method, $params);

					//result will be a array with ('code'=>int, 'headers'=>array(), 'body'=>string)
					$response = $request -> doRequest();
					$content = $response['body'];

					//now I need to parse this as a json object and pull just the tweet
					$jsonObj = json_decode($content, true);


					foreach($jsonObj[0][trends] AS $ind){

						$nonSkippingID = $connection->query("SELECT trendID FROM `trends` ORDER BY trendID DESC LIMIT 1");

						if($nonSkippingID->num_rows > 0){
							$num = intval($nonSkippingID->fetch_assoc()[trendID]);
						}
						else{
							$num = 0;
						}
						$trend = $ind[name];

						$inDatabase = $connection->query("SELECT usageCount FROM `trends` WHERE trend = \"$trend\"");
						//if the entry exists in the database, then just increment the usage of the entry by 1
						if($inDatabase->num_rows > 0){
							//only update the usageCount if lastUpdated != startDate && oldCity != newCity
							$usageCount = intval($inDatabase->fetch_assoc()[usageCount]);
							$connection->query("UPDATE `trends` SET `usageCount`= $usageCount+1, `lastUpdated`= ADDTIME(NOW(), '0 1:0:0') WHERE `trend` = \"$trend\"");
						}
						//else it does not exist, so then insert it
						else{
							$INSERTQUERY = "INSERT INTO trends (trendID, trend, usageCount, startDate, lastUpdated) 
											VALUES ($num+1, \"$trend\", 1, ADDTIME(NOW(), '0 1:0:0'), ADDTIME(NOW(), '0 1:0:0'))";
							$permanentQuery = "INSERT INTO permanentTrends (trend, datePopularized) VALUES (\"$trend\", ADDTIME(NOW(), '0 1:0:0'))";
							$connection->query($INSERTQUERY);
							$connection->query($permanentQuery);
						}

					}					
				}
				catch(OAuthException2 $e){
					var_dump($e);
				}
			}
		}
		$topTrend = $connection->query("SELECT trend FROM `trends` WHERE usageCount = ( SELECT MAX(usageCount) FROM `trends`) LIMIT 1");
		
		print($topTrend->fetch_assoc()[trend]);

		$connection->close();

?>