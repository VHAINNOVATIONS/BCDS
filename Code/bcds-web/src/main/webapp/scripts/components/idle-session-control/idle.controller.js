'use strict';

angular.module('bcdssApp').controller('IdleController', function ($scope, Keepalive, Idle, $uibModal, $state, Auth, Principal) {
	
	Idle.watch();
	
	function closeModals() {
        if ($scope.warning) {
          $scope.warning.close();
          $scope.warning = null;
        }

        if ($scope.timeout) {
          $scope.timeout.close();
          $scope.timeout = null;
        }
      };

      $scope.$on('IdleStart', function() {
    	  closeModals();
    	  $scope.warning = $uibModal.open({
          templateUrl: 'scripts/components/idle-session-control/warning-dialog.html',
          windowClass: 'modal-danger'
        });
      });

      $scope.$on('IdleEnd', function() {
        closeModals();
      });
      
      $scope.$on('IdleTimeout', function() {
    	  Auth.logout();
    	  closeModals();
      }); 
});


