'use strict';

angular.module('bcdssApp')
    .controller('LoginController', //function ($rootScope, $scope, $state, $timeout, Auth) {
//        $scope.user = {};
//        $scope.errors = {};

//        //$scope.rememberMe = false;
//        $timeout(function (){angular.element('[ng-model="username"]').focus();});
//        $scope.login = function (event) {
//            event.preventDefault();
//            Auth.login({
//                username: $scope.username,
//                password: $scope.password,
//               // rememberMe: $scope.rememberMe
//            }).then(function () {
//                $scope.authenticationError = false;
//                if ($rootScope.previousStateName === 'register' || $rootScope.previousStateName === 'home') {
//                    $state.go('home');
//                } else {
//                    $rootScope.back();
//                }
//            }).catch(function () {
//                $scope.authenticationError = true;
//            });
//        };
//    });


        ['$scope', '$rootScope', '$location', 'AuthenticationService',
         function ($scope, $rootScope, $location, AuthenticationService) {
             // reset login status
             AuthenticationService.ClearCredentials();
      
             $scope.login = function () {
                 $scope.dataLoading = true;
                 AuthenticationService.Login($scope.username, $scope.password, function(response) {
                     if (response.success) {
                         AuthenticationService.SetCredentials($scope.username, $scope.password);
                         if ($scope.username == "admin") {
                             $location.path('/admin');
                         } else if ($scope.username == "modeler") {
                             $location.path('/modeler');
                         } else {
                             $location.path('/');
                         }
                     } else {
                         $scope.error = response.message;
                         $scope.dataLoading = false;
                     }
                 });
             };
         }]);
