'use strict';

angular.module('bcdsApp')
    .factory('Generator', function ($http) {
        return {
            getUUID: function () {
                return $http.get('generate/uuid', {});
            }
        };
    });
