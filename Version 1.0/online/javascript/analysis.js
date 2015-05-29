$(function(){
	var timeArray =[];
	var seriesArray = [];

	//this creates a graph for the tweets per hour
	$.get('scripts/analysisScript.php', function(data){
		var daySplit = data.split("|").slice(0,-1);

		//for each day
		$.each(daySplit, function(index, value){
			var splitData = value.split(";").slice(0,-1);
			var avgTweetArray = [];

			//for each hour
			$.each(splitData, function(index, value){
				var time = value.split(",")[0].split(":")[1];

				if($.inArray(time, timeArray) == -1){
					timeArray.push(time);	
				}
				var avgTweet = value.split(",")[1].split(":")[1]; 
				avgTweetArray.push(parseInt(avgTweet));
			});
			seriesArray.push(avgTweetArray);
		});

		//series must be an array of arrays
		var data = {
			labels: timeArray,
			series: seriesArray
		};

		var options = {
			low:1,
			showArea: true,
			width:1400,
			height:500
		};

		var chart = $(".ct-chart");

	// 	var toolTip = chart.append('<div class="tooltip"></div>').find('.tooltip').hide();

	// 	chart.on('mouseenter', '.ct-point', function(){
	// 		var point = $(this),
	// 			value = point.attr('ct:value'),
	// 		toolTip.html(seriesName + "<br />" + value).show();
	// 	});

	// 	chart.on('mouseleave', '.ct-point', function(){
	// 		toolTip.hide();
	// 	});

	// 	chart.on('mousemove', function(event){
	// 		toolTip.css({
	// 			left: (event.offsetX || event.originalEvent.layerX) - toolTip.width() / 2 - 10,
	// 			top:  (event.offsetY || event.originalEvent.layerY) - toolTip.height() - 40
	// 		});

	// 	});


		new Chartist.Line(chart.selector, data, options);

	});

});