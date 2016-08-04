'use strict';

angular.module('bcdssApp').controller('MainController',	function($rootScope, $scope, Principal, Auth) {
	Principal.identity().then(function(account) {
		$scope.account = account;
		$scope.userName = Principal.userName;
		$scope.userRole = Principal.userRole;
		$scope.isAuthenticated = Principal.isAuthenticated;
		});
	}
);
