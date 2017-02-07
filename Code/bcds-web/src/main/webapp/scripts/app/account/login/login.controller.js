'use strict';

angular.module('bcdssApp')
    .controller('LoginController', function ($rootScope, $scope, $state, $timeout, Auth) {
        $scope.user = {};
        $scope.errors = {};

        $scope.rememberMe = false;
        $timeout(function (){angular.element('[ng-model="username"]').focus();});
        $scope.login = function (event) {
            event.preventDefault();
            $scope.form.$invalid = false;
            Auth.login({
                username: $scope.username,
                password: $scope.password,
                rememberMe: $scope.rememberMe
            }).then(function () {
                $scope.authenticationError = false;
                //if ($rootScope.previousStateName === 'register' || $rootScope.previousStateName === 'home') {
                    $state.go('home');
               // } else {
               //     $rootScope.back();
               // }
            }).catch(function () {
            	console.log("Invalid Username and Password!!!\nPlease verify and try again...");
                $scope.authenticationError = true;
                $scope.form.$invalid = true;
                event.preventDefault();
            });
        };
    });
