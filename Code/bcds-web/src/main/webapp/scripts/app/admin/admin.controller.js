'use strict';

angular.module('bcdssApp').controller('AdminDashboardController', function($rootScope, $scope, $state, $stateParams, UserRepository) {
	$scope.users = [];

	$scope.getUsers = function() {
		UserRepository.getAllUsers(function (results) {
			$scope.users = results;
		})
	}
	
	$scope.getUsers();

});