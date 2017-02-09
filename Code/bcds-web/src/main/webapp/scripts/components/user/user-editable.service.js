'use strict';

angular.module('bcdssApp')
    .factory('UserEditable', function ($http, $resource) {
        return {
            getAllUsers: function(){
                return $http.get('usercontrol/allusers')
                    .then(function (response) {
                        return angular.fromJson(response);
                });
            },

            saveUser: function(userData){
                var data = {
                    activated: userData.activated,
                    authorities: userData.authorities,
                    createdBy: userData.userId,
                    createdDate: new Date(),
                    email: userData.email,
                    firstName: userData.firstName,
                    login: userData.login,
                    password: userData.password
                }
                return $http.post('usercontrol/allusers', data)
                    .then(function (response) {
                        return angular.fromJson(response);
                });
            },

            updateUser: function(userData){
                var data = {
                    activated: userData.activated,
                    authorities: userData.authorities,
                    createdBy: userData.createdBy,
                    createdDate: userData.createdDate,
                    lastModifiedBy: userData.userId,
                    lastModifiedDate: new Date(),
                    email: userData.email,
                    firstName: userData.firstName,
                    id: userData.id,
                    login: userData.login,
                    password: userData.password
                }
                return $http.put('usercontrol/allusers', data)
                    .then(function (response) {
                        return angular.fromJson(response);
                });
            }
        };
    });
