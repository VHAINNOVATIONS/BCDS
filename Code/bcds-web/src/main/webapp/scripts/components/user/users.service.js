'use strict';

angular.module('bcdssApp')
    .factory('UsersService', function ($resource, DateUtils) {
        return $resource('api/users', {type: '@type', referenceId: '@referenceId'}, {
        	'query': {method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    return data;
                }}
        });
    });
