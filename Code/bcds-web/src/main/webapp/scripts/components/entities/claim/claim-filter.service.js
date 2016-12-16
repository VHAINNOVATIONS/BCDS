'use strict';

angular.module('bcdssApp')
    .factory('ClaimFilterService', function ($http, DateUtils) {
        return {
        	filterClaims: function (filters) {
        		console.log(filters);
        		var data = {
        				establishedDate : (filters.dateType == "establishedDate"),
        				regionalOfficeNumber: filters.regionalOfficeOption,
        				contentionType: filters.contentionType,
        				fromDate: filters.fromDate,
        				toDate: filters.toDate
        		}
        		return $http.post('api/claims', data)
                	.then(function (response) {
                		return response.data;
                	});
            }
        };
    });