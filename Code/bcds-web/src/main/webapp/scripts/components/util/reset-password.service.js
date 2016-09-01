'use strict';

angular.module('bcdssApp')
    .factory('ResetPassword', function ($resource, DateUtils) {
        return $resource('passwordUtility/resetPassword/:login', {}, {
        	'get': {
                method: 'GET',
                isArray: true,
                transformResponse: function (data) {
                	data = angular.fromJson(data);
                    return data;
                }}
        });
    });
