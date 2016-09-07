'use strict';

angular.module('bcdssApp').controller('UserEditorController',	function($rootScope, $state, $scope, editUser, UserEditable, ResetPassword) {
	$scope.editUser = editUser;

	$scope.formatCreatedDate = function(date) {
		var date = new Date(date);
            return ('0' + (date.getMonth()+1)).slice(-2) + '/' +
                ('0' + date.getDate()).slice(-2) + '/'
                + date.getFullYear();
	};

	$scope.resetPassword = function() {
		ResetPassword.get({login: $scope.editUser.login}, function(result){
			$scope.editUser.password = result[0];
		});
	};
	
	$scope.save = function () {
    	console.log($scope.editUser);
    	UserEditable.update($scope.editUser, onSaveFinished);
    };
    
    var onSaveFinished = function(){
    	$state.go('^', {reload:true});
    }
	
	$scope.cancel = function() {
        $state.go('^', {reload:false});
    };
});
