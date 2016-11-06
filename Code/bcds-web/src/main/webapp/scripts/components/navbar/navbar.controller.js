'use strict';

angular.module('bcdssApp')
    .controller('NavbarController', function ($scope, $location, $state, Auth, Principal) {
        $scope.isAuthenticated = Principal.isAuthenticated;
        $scope.$state = $state;
        $scope.userName = Principal.userName;
        
        $scope.logout = function () {
            Auth.logout();
            $state.go('home');
        };
    });
