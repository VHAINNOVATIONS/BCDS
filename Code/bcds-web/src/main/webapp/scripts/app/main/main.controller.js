'use strict';

angular.module('bcdssApp').controller('MainController', function($rootScope, $scope, Principal, Auth) {
			Principal.identity().then(function(account) {
				$scope.account = account;
				$scope.isAuthenticated = Principal.isAuthenticated;
				console.log($scope.isAuthenticated);
			});
});
