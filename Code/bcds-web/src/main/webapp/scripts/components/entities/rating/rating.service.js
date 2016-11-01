'use strict';

angular.module('bcdssApp')
    .factory('RatingService', function ($resource, DateUtils) {
        return $resource('api/ratings', {}, {
        	'query': {method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    return data;
                }}
        });
    });
