'use strict';

angular.module('bcdssApp')
.factory('UserRole', function ($resource) {
    return $resource('api/authorities', {}, {
            'query': {method: 'GET', isArray: true}
        });
    });