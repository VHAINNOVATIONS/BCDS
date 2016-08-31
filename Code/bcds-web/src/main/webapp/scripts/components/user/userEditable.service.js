'use strict';

angular.module('bcdssApp')
    .factory('UserEditable', function ($resource) {
        return $resource('api/allusers/:id', {}, {
                'query': {method: 'GET', isArray: true},
                'get': {
                    method: 'GET',
                    transformResponse: function (data) {
                        data = angular.fromJson(data);
                        return data;
                    }
                },
                'update': { method:'PUT' }
            });
        });
