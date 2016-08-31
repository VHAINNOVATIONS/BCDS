'use strict';

angular.module('bcdssApp').controller('AdminDashboardController', function($rootScope, $scope, $state, $stateParams, UserEditable) {
	$scope.editableUsers = [];
	
	$scope.loadAllUsers = function (){
		UserEditable.query(function(result){
			$scope.editableUsers = result;
			console.log($scope.editableUsers);
		})
	};
	$scope.loadAllUsers();
});