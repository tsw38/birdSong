<?php
	//takes in an string and outputs a sanitized string removing bad inputs
	function normalizeInput($key){
		//remove urls
		$initialstep = preg_replace('/((http|https):\/\/t\.co\/)[a-zA-Z0-9]+/', '', $key);
		//remove usernames
		$step1 = preg_replace('/\@[a-zA-Z0-9]+/', '', $initialstep);
		//remove non engish letters
		$step2 = preg_replace('/[^a-zA-Z0-9\s\p{P}]/', '', $step1);
		//remove retweet reference
		$step3 = preg_replace('/(RT )/', '', $step2);
		//remove symbols except pound
		$step4 = preg_replace("/[^0-9a-zA-Z# ]/", "", $step3);	
		//remove leading spaces
		$step5 = trim($step4, " \t\n\r\0\x0B");
		$stripped = strip_tags(htmlentities($step5));

		//At this point it could be possible that the returned tweet is empty
		//only return strings that are more than just spaces
		if(strlen(preg_replace('/\s+/', '', $stripped)) > 10){
			return $stripped;
		}
		
		else return false;
	}
?>