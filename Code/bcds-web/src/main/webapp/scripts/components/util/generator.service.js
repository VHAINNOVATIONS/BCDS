'use strict';

angular.module('bcdssApp')
    .factory('Generator', function ($http) {
        return {
            getUUID: function () {
                return $http.get('generate/uuid', {});
            }
        };
    });
