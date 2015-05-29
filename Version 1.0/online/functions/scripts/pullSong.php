<?php 
	// Given a sentiment (polarity), send all corresponding tweets to markov builder
	// to produce new midi song for jfugue midi player
	if(isset($_GET['polarity'])){
		include '../config.php';
		require '../../markov/markov.php';

		$connection = new mysqli(DB_HOST, DB_ADMIN, DB_PASSWORD, DB_NAME);

		$polarity = $_GET['polarity'];
		$order  = 2;
	    $length = $_GET['tweetLength'];
	    
		$songRequest = "SELECT songString FROM SongDB WHERE sentiment=\"" . $polarity . "\" ORDER BY songID DESC";
		$resultTweets = $connection->query($songRequest);
		$input = "";

		foreach($resultTweets AS $resultRow){
			$input .= $resultRow[songString] . " ";
		}

	    if ($input) $text = $input;

	    if(isset($text)) {
	        $markov_table = generate_markov_table($text, $order);
	        $markov = generate_markov_text($length, $markov_table, $order);

	        if (get_magic_quotes_gpc()) $markov = stripslashes($markov);
	        // remove leading spaces and then take only the first 140 elements of the array
	        $temp2 = preg_split('/\s+/', $markov);
			$songNotes = implode(" ", array_splice($temp2,140));
			
			if(strlen($songNotes) >= 10){
				$insertQuery = "INSERT INTO SongDB (songID, songString, sentiment) VALUES (DEFAULT, \"".$songNotes."\", \"" .$polarity."\")";
	        	$connection->query($insertQuery);
			}
	        
	    }
		$connection->close();
	}
	
?>