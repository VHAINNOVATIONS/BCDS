'use strict';

angular.module('bcdssApp')
    .factory('ClaimFilterService', function ($http, DateUtils) {
        return {
        	filterClaims: function (filters) {

        		return $http.post('api/claims', {data: {claim: filters }})
                	.then(function (response) {
                		return response.data;
                	});
            }
        };
    });