'use strict';

angular.module('bcdssApp').controller('UserEditorController',	function($rootScope, $state, $scope, editUser, UserEditable, ResetPassword, UserRole) {
	$scope.editUser = editUser;
	$scope.userroles = [];
	
	$scope.loadRoles = function () {
		UserRole.query(function(result){$scope.userroles = result})
	};
	$scope.loadRoles();

	$scope.formatDate = function(date) {
		var date = new Date(date);
            return ('0' + (date.getMonth()+1)).slice(-2) + '/' +
                ('0' + date.getDate()).slice(-2) + '/'
                + date.getFullYear();
	};

	$scope.resetPassword = function() {
		ResetPassword.get({login: $scope.editUser.login}, function(result){
			$scope.editUser.password = result[0];
			$('#passwordResetDialog').modal('show');
		});
	};
	
	$scope.save = function () {
		if ($scope.editUser.id != null) {
			UserEditable.update($scope.editUser, onSaveFinished, onUnsuccess);
		} else {
			UserEditable.save($scope.editUser, onSaveFinished, onUnsuccess);
		}
    };
    
    var onSaveFinished = function(){
    	console.log('>>>successful');
    	$state.go('^', {reload:true});
    }
    
    var onUnsuccess = function(){
    	console.log('###not successful');
    	$state.go('^', {reload:true});
    }
	
	$scope.cancel = function() {
        $state.go('^', {reload:false});
    };
});
