'use strict';

angular.module('bcdssApp')
    .factory('Register', function ($resource) {
        return $resource('api/register', {}, {
        });
    });


