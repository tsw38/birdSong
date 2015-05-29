<?php
if (PHP_SAPI != 'cli') {
	echo "<pre>";
}

$strings = array(
	1 => 'Person Shoutout to bo28 Greatest hitter in college baseball history #DaddyHacks #SpeedKills #OutTheMud #DontGetTired httpt',
	2 => 'Sexy College Girls',
	3 => 'Its clear that I picked the wrong city to go to college in  transfers',
	4 => '35 of billionaires never finished college',
	5 => 'She is seemingly very agressive',
	6 => 'Marie was enthusiastic about the upcoming trip. Her brother was also passionate about her leaving - he would finally have the house for himself.',
	7 => 'To be or not to be?',
);

require_once __DIR__ . '/../autoload.php';


$sentiment = new \PHPInsight\Sentiment();


foreach ($strings as $string) {

	// calculations:
	$scores = $sentiment->score($string);
	$class = $sentiment->categorise($string);

	// output:
	echo "String: $string\n";
	echo "Sentiment: $class";
	echo "\n\n";
}
