'use strict';

angular.module('bcdssApp')
    .factory('VeteranService', function ($resource, DateUtils) {
        return $resource('api/veterans', {}, {
        	'query': {method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    return data;
                }}
        });
    });
