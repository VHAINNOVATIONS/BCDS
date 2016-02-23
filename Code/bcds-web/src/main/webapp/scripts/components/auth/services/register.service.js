'use strict';

angular.module('bcdsApp')
    .factory('Register', function ($resource) {
        return $resource('api/register', {}, {
        });
    });


