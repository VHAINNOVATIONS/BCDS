'use strict';

angular.module('bcdssApp').controller('UserEditorController',	function($rootScope, $state, $scope, editUser, UserEditable) {
	$scope.editUser = editUser;
	
	$scope.save = function () {
    	console.log($scope.editUser);
    	UserEditable.update($scope.editUser, onSaveFinished);
    };
    
    var onSaveFinished = function(){
    	$state.go('^', {reload:true});
    }
	
	$scope.cancel = function() {
        $state.go('^', {reload:true});
    };
    
    $scope.resetPassword = function(){
    	
    }
    
    
	}
);
