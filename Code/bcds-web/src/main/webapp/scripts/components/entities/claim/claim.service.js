'use strict';

angular.module('bcdssApp')
    .factory('ClaimService', function ($resource, DateUtils) {
        return $resource('api/claims/:id', {}, {
        	'query': {method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    return data;
                }},
            'processClaims':{ 
	                url: 'api/claims/process', 
	                method: 'POST', 
	                transformResponse: function (data) {
	                    data = angular.fromJson(data);
	                    return data;
	                }
	            }
        });
    });
