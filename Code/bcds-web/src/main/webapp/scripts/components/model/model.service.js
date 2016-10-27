'use strict';

angular.module('bcdssApp')
    .factory('ModelService', function ($resource, DateUtils) {
        return $resource('api/models', {}, {
        	'query': {method: 'GET', isArray: true},
            'post': {
                method: 'POST',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    return data;
                }}
        });
    });
