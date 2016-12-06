'use strict';

angular.module('bcdssApp')
    .factory('RatingService', function ($resource, DateUtils) {
        return $resource('api/results', {}, {
        	'query': {method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    return data;
                }},
            'findModelRatingResults': {
                url: 'api/modelRatingResults', 
                method: 'POST', 
                data = {
                        processId : 1
                },
                transformResponse: function (data) {
                    console.log(data);
                    data = angular.fromJson(data);
                    return data;
                }}
        });
    });
