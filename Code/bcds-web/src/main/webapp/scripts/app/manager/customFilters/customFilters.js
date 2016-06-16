angular.module('bcdssApp').filter(
	'daysRemaining',
	function() {

	    return function(milliSecond) {
		var dayInfo = '';
		var dayRemaining = Math.round(milliSecond / 86400000);

		if (dayRemaining > 365) {
		    dayInfo = 'more than a year';
		} else if (dayRemaining == 365) {
		    dayInfo = 'a year';
		} else {
		    if (dayRemaining > 180) {
			dayInfo = 'more than 6 months';
		    } else if (dayRemaining == 180) {
			dayInfo = '6 months';
		    } else {
			if (dayRemaining > 90) {
			    dayInfo = 'more than 3 months';
			} else if (dayRemaining > 60) {
			    dayInfo = 'more than 2 months';
			} else if (dayRemaining > 30) {
			    dayInfo = 'more than a month';
			} else if (dayRemaining > 15) {
			    if (dayRemaining % 7 == 0) {
				dayInfo = Math.floor(dayRemaining / 7)
					.toString()
					+ ' weeks ';
			    } else if (dayRemaining % 7 == 1) {
				dayInfo = Math.floor(dayRemaining / 7)
					.toString()
					+ ' weeks '
					+ (dayRemaining % 7).toString()
					+ ' day';
			    } else {
				dayInfo = Math.floor(dayRemaining / 7)
					.toString()
					+ ' weeks '
					+ (dayRemaining % 7).toString()
					+ ' days';
			    }
			} else {
			    if (dayRemaining > 2) {
				dayInfo = dayRemaining.toString() + ' days';
			    } else if (dayRemaining == 2) {
				dayInfo = 'couple of days';
			    } else {
				if (dayRemaining == 1) {
				    dayInfo = 'Tomorrow';
				} else if (dayRemaining == 0) {
				    dayInfo = 'Today!';
				} else {
				    dayInfo = 'Expired!!!';
				}
			    }
			}
		    }
		}
		;
		return dayInfo;
	    };
	});
