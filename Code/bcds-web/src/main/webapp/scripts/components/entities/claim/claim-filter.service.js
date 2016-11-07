'use strict';

angular.module('bcdssApp')
    .factory('ClaimFilterService', function ($resource, DateUtils) {
        return $resource('api/claims', {}, {
        	'query': {method: 'GET', isArray: true},
           
        	'getFilteredClaims':{
        		method: 'POST',
        		transformResponse: function (data) {
                    data = angular.fromJson(data);
                    return data;
                }
        	}
        });
    });