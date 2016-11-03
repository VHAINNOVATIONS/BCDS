'use strict';

angular.module('bcdssApp')
    .factory('ClaimFilterService', function ($resource, DateUtils) {
        return $resource('api/claims/:filters', {}, {
        	'query': {method: 'GET', isArray: true},
           
        	'getFilteredClaims':{
        		method: 'GET',
        		transformResponse: function (data) {
                    data = angular.fromJson(data);
                    return data;
                }
        	}
        });
    });