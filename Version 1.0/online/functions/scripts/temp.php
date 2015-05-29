<!DOCTYPE html>
<html >
<head>   
	<link type="text/css" rel="stylesheet" href="../../stylesheets/style.css">
</head>
<body class="view">
	<header class="w">
		<h1>Clarity</h1>
		<p>Over the course of this past year, artist Tyler Williams has been exploring the topic of translation,
		 and in his current piece, has been focusing his attention on social media. Tyler finds that written language has become 
		 increasingly ambiguous, and as a form of communication, is far from precise. Through this project, 
		 the artist attempts to create live music to resolve the uncertainties of missunderstood status updates, tweets, texts and comments, because unlike
		 text, music is a language that conveys intention more universally.
		</p>
	</header>
	<div class="tweets">
	</div>


<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
<script type="text/javascript">
	$(function(){
		$.getJSON("temp.txt",function(data){

			$(".tweets").html("").append("<table>");
			$.each(data.tweets, function (key, value){
				$(".tweets").append("<tr><td>" + value.tweet + "</td></tr>");
			});
			$(".tweets").append("</table>");
		});
	});
</script>

</body>
</html>