<?php
	if(isset($_POST['dataStuff'])){
		include 'config.php';
		$connection = new mysqli(DB_HOST, DB_ADMIN, DB_PASSWORD, DB_NAME);
		$userPassword = $_POST['dataStuff'];

		//pull the database password
		$pullPassword = "SELECT * FROM authPasswords";
		$pullPasswordResult = $connection->query($pullPassword)->fetch_row()[0];

		//password = kappadeltarho
		if($pullPasswordResult == crypt($userPassword, '3vfKbw3C5:@vNJ@$Z:"hBYj.c;Ng-z,#')){
			//start a session
			session_start();
			$_SESSION['time'] = new DateTime();
			print_r($_SESSION);
		}
		elseif(!isset($userPassword) || empty($userPassword) || strlen($userPassword) <= 1){
			$successOrFail = "Please Enter A Password";
		}
		else{
			$successOrFail = "Failed to provide correct password";
		}
		$connection->close();
	}
	print $successOrFail;
?>