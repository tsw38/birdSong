<!DOCTYPE html>
<html>
<head>
	<title>Art Thesis - Tyler</title>
	<link type="text/css" rel="stylesheet" href="stylesheets/style.css">
</head>
<body>
	<div class="wrapper">
	<div class="formContainer">
		<h1>TWITTER SEARCH CRAWLER</h1>

		
		<form method="GET" action="functions/lot.php" onsubmit="document.getElementById('timestamp').value=Date()">
			<label for="searchBy">Search Phrase</label>
			<input type="text" name="searchBy" placeholder="Please Input Search Term" id="searchBy">
			<br />
			<label for="TPM">Tweets Per Minute: </label>
			<select name="TPM" id="TPM">
				<option value="1">1</option>
				<option value="2">2</option>
				<option value="3">3</option>
				<option value="4">4</option>
				<option value="5">5</option>
				<option value="6">6</option>
				<option value="7">7</option>
				<option value="8">8</option>
				<option value="9">9</option>
				<option value="10">10</option>
				<option value="11">11</option>
				<option value="12">12</option>
			</select>
			<br />
			<input type="hidden" name="timestamp" id="timestamp" value="" />
			<input type="submit" name="submit" id="submitSearch" value="Submit">
			<input type="button" id="clear" value="Clear Database">
			<input type="hidden" name="running" value="true" />
		</form>
	</div>
</div>
</body>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
<script type="text/javascript" src="javascript/main.js"></script>
</html>