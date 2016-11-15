'use strict';

angular.module('bcdssApp')
    .factory('DDMService', function ($resource, DateUtils) {
        return $resource('api/ddms', {}, {
        	'query': {method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    return data;
                }}
        });
    });
