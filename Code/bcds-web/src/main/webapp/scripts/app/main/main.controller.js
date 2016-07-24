'use strict';

angular.module('bcdssApp').controller('MainController',	function($rootScope, $scope, Principal, Auth) {
	Principal.identity().then(function(account) {
		$scope.account = account;
		$scope.isAuthenticated = fales; //Principal.isAuthenticated;
		if ($scope.username == 'admin') {
			$scope.isAuthenticated = { true: $scope.username === 'admin' && $scope.password === 'admin' };
        }
        if ($scope.username == 'rater') {
        	$scope.isAuthenticated = { true: $scope.username === 'rater' && $scope.password === 'rater' };
        }
        if ($scope.username == 'modeler') {
        	$scope.isAuthenticated = { true: $scope.username === 'modeler' && $scope.password === 'modeler' };
        }

        if (!$scope.isAuthenticated) {
            window.alert("Incorrect ID and/or password... \nTry Again with correct username and password.");
        callback(response);
		}
	});
});
