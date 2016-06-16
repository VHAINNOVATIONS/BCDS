'use strict';

angular.module('bcdssApp')
    .factory('Lookup', function ($resource, DateUtils) {
        return $resource('api/lookup', {type: '@type', referenceId: '@referenceId'}, {
            'query': { method: 'GET', isArray: true}
        });
    });
